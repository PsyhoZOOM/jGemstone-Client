package net.yuvideo.jgemstone.client.classes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;
import org.json.JSONObject;

/**
 * Created by zoom on 8/8/16.
 */
public class Client {

  public int portNumber;
  public String RemoteHost;
  public LongProperty result = new SimpleLongProperty();
  public IntegerProperty ss = new SimpleIntegerProperty();
  public IntegerProperty rs = new SimpleIntegerProperty();

  //Socket socket;
  SSLSocket socket;
  InputStreamReader Isr;
  OutputStreamWriter Osw;
  BufferedReader Bfr;
  BufferedWriter Bfw;

  //JSON
  JSONObject jObj = new JSONObject();
  private String userName;
  private String password;
  private Settings local_settings;
  public String status_login;
  boolean connected = false;
  private boolean newClient = true;
  private static String C_VERSION = "0.100";

  public Client(Settings local_settings) {
    this.local_settings = local_settings;
    this.password = local_settings.getLocalPassword();
    this.userName = local_settings.getLocalUser();
  }

  public Client(Settings local_settings, boolean newClient) {
    this.newClient = newClient;
    this.local_settings = local_settings;
    this.password = local_settings.getLocalPassword();
    this.userName = local_settings.getLocalUser();
  }

  public JSONObject send_object(JSONObject rObj) {
    rObj.put("userNameLogin", this.userName);
    rObj.put("userPassLogin", this.password);
    rObj.put("C_VERSION", this.C_VERSION);
    if (newClient) {
      main_run();
    } else {
      rObj.put("keepAlive", true);
    }

    try {

      try {
        if (Osw == null || Bfw == null) {
          Osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
          Bfw = new BufferedWriter(Osw);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }

      try {
        Bfw.write(rObj.toString());

        //send bytes
        ss.setValue(rObj.toString().getBytes().length);
        Bfw.newLine();
        Bfw.flush();

        //latency
        long localPING = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli();
        rObj = getjObj();

        //recieve bytes
        rs.setValue(rObj.toString().getBytes().length);

        long remotePONG = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli();

        result.setValue(remotePONG - localPING);

      } catch (IOException e) {
        AlertUser.error("GRESKA", e.getMessage());
        e.printStackTrace();
      }


    } catch (Exception e) {
      e.printStackTrace();
    }
    return rObj;
  }

  public JSONObject getjObj() {
    try {
      if (Isr == null || Bfr == null) {
        Isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
        Bfr = new BufferedReader(Isr);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      jObj = new JSONObject(Bfr.readLine());
      rs.setValue(jObj.toString().getBytes().length);
    } catch (IOException e1) {
      e1.printStackTrace();
    } catch (NullPointerException e2) {
      e2.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (newClient)
      close();

    return jObj;
  }


  public void close() {
    try {
      Osw.close();
      Bfw.close();
      socket.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public void main_run() {
    userName = local_settings.getLocalUser();
    password = local_settings.getLocalPassword();

    RemoteHost = local_settings.getREMOTE_HOST();
    portNumber = local_settings.getREMOTE_PORT();

    try {
      //Crypted
      KeyStore clientKeys = KeyStore.getInstance("JKS");
      clientKeys.load(ClassLoader.getSystemResourceAsStream("ssl/plainclient.jks"),
          "jgemstone".toCharArray());
      KeyManagerFactory clientKeyManager = KeyManagerFactory.getInstance("SunX509");
      clientKeyManager.init(clientKeys, "jgemstone".toCharArray());
      KeyStore serverPub = KeyStore.getInstance("JKS");
      serverPub.load(ClassLoader.getSystemResourceAsStream("ssl/serverpub.jks"),
          "jgemstone".toCharArray());

      TrustManagerFactory trustManager = TrustManagerFactory.getInstance("SunX509");
      trustManager.init(serverPub);
      SSLContext ssl = SSLContext.getInstance("TLS");
      ssl.init(clientKeyManager.getKeyManagers(), trustManager.getTrustManagers(),
          SecureRandom.getInstance("SHA1PRNG"));
      socket = (SSLSocket) ssl.getSocketFactory()
          .createSocket(InetAddress.getByName(RemoteHost), portNumber);
      socket.startHandshake();

//OLD NON CRYPT
      //     login_to_server(jObj);

    } catch (IOException e) {
      e.printStackTrace();
      AlertUser.error("GRESKA", e.getMessage());
      try {
        if (!socket.isClosed()) {
          socket.close();
        }
      } catch (IOException e1) {
        e1.printStackTrace();
      }
    } catch (KeyStoreException e) {
      e.printStackTrace();
    } catch (CertificateException e) {
      e.printStackTrace();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (UnrecoverableKeyException e) {
      e.printStackTrace();
    } catch (KeyManagementException e) {
      e.printStackTrace();
    }

  }

  public void login_to_server() {
    jObj = new JSONObject();
    jObj.put("action", "login");
    jObj.put("username", userName);
    jObj.put("password", password);

    jObj = send_object(jObj);
    if (jObj.has("Message")) {
      if (jObj.getString("Message").equals("LOGIN_OK")) {
        connected = true;
      } else if (jObj.getString("Message").equals("LOGIN_FAILED")) {
        connected = false;
        AlertUser.error("GRESKA", "Pogrešno korisničko ime ili lozinka!");
        status_login = "Pogrešno korisničko ime ili lozinka";
      }else if(jObj.getString("Message").equals("WRONG_VERSION")){
        connected = false;
        AlertUser.error("GRESKA!", "OVA VERZIJA KLIJENTA NIJE KOMPATIBILNA");

      } else {
        connected = false;
        AlertUser.error("GRESKA", jObj.getString("Message"));
      }
    }
  }

  public boolean isConnected() {
    return connected;
  }

  public void setConnected(boolean connected) {
    this.connected = connected;
  }


  public Settings getLocal_settings() {
    return local_settings;
  }

  public void setLocal_settings(Settings local_settings) {
    this.local_settings = local_settings;
  }
}
