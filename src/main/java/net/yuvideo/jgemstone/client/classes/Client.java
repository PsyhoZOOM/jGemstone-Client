package net.yuvideo.jgemstone.client.classes;

import org.json.JSONObject;

import javax.net.SocketFactory;
import javax.net.ssl.*;
import java.io.*;
import java.net.InetAddress;
import java.security.*;
import java.security.cert.CertificateException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.Logger;

/**
 * Created by zoom on 8/8/16.
 */
public class Client {

    public int portNumber;
    public InetAddress inetAddress;
    public String RemoteHost;
    public boolean alive = false;
    public String status_login;
    //Socket socket;
    SSLSocket socket;
    SocketFactory ssf = SSLSocketFactory.getDefault();
    InputStreamReader Isr;
    OutputStreamWriter Osw;
    BufferedReader Bfr;
    BufferedWriter Bfw;
    messageS checkLive = new messageS();
    messageS mess = new messageS();
    Logger LOGGER = Logger.getLogger("CLIENT");
    //JSON
    JSONObject jObj = new JSONObject();
    private String userName;
    private String password;
    private db_connection db_conn = new db_connection();
    private Settings local_settings;
    private Boolean isConnected = false;
    private Boolean manualLogin = false;
    private boolean runOnce = true;
    private long result;


    public JSONObject send_object(JSONObject rObj) {

        try {

            if (Osw == null) {
                try {
                    Osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
                    Bfw = new BufferedWriter(Osw);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            Bfw.write(rObj.toString());
            Bfw.newLine();
            Bfw.flush();
            rObj = new JSONObject();
            rObj = get_object();
        } catch (IOException e) {
            e.printStackTrace();
            status_login = e.getMessage();
            isConnected = false;
        } catch (Exception e) {
            status_login = e.getMessage();
            e.printStackTrace();
        }
        return rObj;
    }

    private JSONObject get_object() {
        if (Isr == null) {
            try {
                Isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
                Bfr = new BufferedReader(Isr);
            } catch (IOException e) {
                e.printStackTrace();
                isConnected = false;
            }
        }

        try {
            jObj = new JSONObject(Bfr.readLine());
        } catch (IOException e1) {
            status_login = e1.getMessage();
            LOGGER.info("E1_MESSAGE" + e1.getMessage());
            isConnected = false;
        } catch (NullPointerException e2) {
            LOGGER.info("E2_MESSAGE" + e2.getMessage());
            isConnected = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public void user_pass_manual(String username, String password) {
        db_conn.init_database();
        md5_digiest md5 = new md5_digiest(password);
        local_settings = db_conn.local_settings;
        local_settings.setLocalUser(username);
        local_settings.setLocalPassword(md5.get_hash());
        this.userName = local_settings.getLocalUser();
        this.password = local_settings.getLocalPassword();
        db_conn.close_db();
        manualLogin = true;
    }

    public void main_run() {
        if (!manualLogin) {
//			db_conn.init_database();
//			local_settings = db_conn.local_settings;
            userName = local_settings.getLocalUser();
            password = local_settings.getLocalPassword();
            db_conn.close_db();
        }

        RemoteHost = local_settings.getREMOTE_HOST();
        portNumber = local_settings.getREMOTE_PORT();

        jObj.put("action", "login");
        jObj.put("username", userName);
        jObj.put("password", password);

        try {
            //Crypted
            KeyStore clientKeys = KeyStore.getInstance("JKS");
            clientKeys.load(ClassLoader.getSystemResourceAsStream("ssl/plainclient.jks"), "jgemstone".toCharArray());
            KeyManagerFactory clientKeyManager = KeyManagerFactory.getInstance("SunX509");
            clientKeyManager.init(clientKeys, "jgemstone".toCharArray());
            KeyStore serverPub = KeyStore.getInstance("JKS");
            serverPub.load(ClassLoader.getSystemResourceAsStream("ssl/serverpub.jks"), "jgemstone".toCharArray());

            TrustManagerFactory trustManager = TrustManagerFactory.getInstance("SunX509");
            trustManager.init(serverPub);
            SSLContext ssl = SSLContext.getInstance("TLS");
            ssl.init(clientKeyManager.getKeyManagers(), trustManager.getTrustManagers(), SecureRandom.getInstance("SHA1PRNG"));
            socket = (SSLSocket) ssl.getSocketFactory().createSocket(InetAddress.getByName(RemoteHost), portNumber);
            socket.startHandshake();

//OLD NON CRYPT
            login_to_server(jObj);

        } catch (IOException e) {
            e.printStackTrace();
            isConnected = false;
            status_login = e.getMessage().toString();
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

    private void login_to_server(JSONObject jObjLogin) {
        jObj = new JSONObject();
        System.out.println(jObjLogin);
        jObj = send_object(jObjLogin);
        if (jObj.has("Message")) {
            LOGGER.info("MESSAGE: " + jObj.getString("Message"));
            if (jObj.getString("Message").equals("LOGIN_OK")) {
                status_login = "Uspesno logovanje";
                isConnected = true;
                //check ping
                //if (runOnce) checkAlive();
                runOnce = false;
            } else if (jObj.getString("Message").equals("LOGIN_FAILED")) {
                status_login = "Pogrešno korisničko ime ili lozinka";
                isConnected = false;
            }
        } else {
            LOGGER.info("ERROR IN CONNECTION (no return Message)");
        }
    }

    private void checkAlive() {
        new Thread(() -> {
            JSONObject pingCheck = new JSONObject();

            while (true) {
                pingCheck.put("action", "checkPing");
                long localPING = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                pingCheck = send_object(pingCheck);
                if (pingCheck.has("PONG") && isConnected) {
                    long remotePONG = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

                    result = remotePONG - localPING;
                    System.out.println(String.format("Konektovan lat: %dms", result));
                } else {
                    System.out.println("Diskonektovan");
                    result = -1;
                }
                try {
                    if (isConnected) {
                        Thread.sleep(1000);
                    } else {
                        Thread.sleep(10000);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public String checkLatency() {
        if (result == -1) {
            return status_login;
        } else return String.format("%dms", result);
    }

    public Boolean get_connection_state() {
        return isConnected;
    }

}
