package net.yuvideo.jgemstone.client.classes;

import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.net.SocketFactory;
import javax.net.ssl.*;
import java.io.*;
import java.net.InetAddress;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Created by zoom on 8/8/16.
 */
public class Client {

	public int portNumber;
	public InetAddress inetAddress;
	public String RemoteHost;
	public String status_login;
	public Label status_conn = new Label();
	//Socket socket;
	SSLSocket socket;
	SocketFactory ssf = SSLSocketFactory.getDefault();
	InputStreamReader Isr;
	OutputStreamWriter Osw;
	BufferedReader Bfr;
	BufferedWriter Bfw;
	messageS checkLive = new messageS();
	messageS mess = new messageS();
	Logger LOGGER = LogManager.getLogger("CLIENT");
	//JSON
	JSONObject jObj = new JSONObject();
	private db_connection db_conn = new db_connection();
	private Settings local_settings;
	private Boolean isConnected = false;
	private Boolean manualLogin = false;

	public JSONObject send_object(JSONObject rObj) {

		try {

			if (Osw == null) {
				try {
					//for json
					//Osw = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
					Osw = new OutputStreamWriter(socket.getOutputStream(), "UTF-8");
					Bfw = new BufferedWriter(Osw);
				} catch (IOException e) {
					e.printStackTrace();

				}
			}

//unencripted
			Bfw.write(rObj.toString());
			Bfw.newLine();
			Bfw.flush();
			rObj = new JSONObject();
			rObj = get_object();
			status_conn.setText("Konektovan");
		} catch (IOException e) {
			//System.out.println(e.getMessage());
			LOGGER.error("CANT SEND OBJECT" + e.getMessage());
			isConnected = false;
			status_conn.setText("Diskonektovan");
		} catch (Exception e) {
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
				LOGGER.error("ERROR AT InputStreamReader: " + e.getMessage());
				isConnected = false;
			}
		}

		try {

//unencripted
			jObj = new JSONObject(Bfr.readLine());

		} catch (IOException e1) {
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
		db_conn.close_db();
		manualLogin = true;

	}

	public void main_run() {
		if (!manualLogin) {
			db_conn.init_database();
			local_settings = db_conn.local_settings;
			db_conn.close_db();
		} else {

		}
		RemoteHost = local_settings.getREMOTE_HOST();
		portNumber = local_settings.getREMOTE_PORT();

		jObj.put("action", "login");
		jObj.put("username", local_settings.getLocalUser());
		jObj.put("password", local_settings.getLocalPassword());

		try {
			//Crypted

			KeyStore clientKeys = KeyStore.getInstance("JKS");
//            clientKeys.load(new FileInputStream("src/main/resources/ssl/plainclient.jks"), "jgemstone".toCharArray());
			clientKeys.load(ClassLoader.getSystemResourceAsStream("ssl/plainclient.jks"), "jgemstone".toCharArray());
			KeyManagerFactory clientKeyManager = KeyManagerFactory.getInstance("SunX509");
			clientKeyManager.init(clientKeys, "jgemstone".toCharArray());
			KeyStore serverPub = KeyStore.getInstance("JKS");
//            serverPub.load(new FileInputStream("src/main/resources/ssl/serverpub.jks"), "jgemstone".toCharArray());
			serverPub.load(ClassLoader.getSystemResourceAsStream("ssl/serverpub.jks"), "jgemstone".toCharArray());

			TrustManagerFactory trustManager = TrustManagerFactory.getInstance("SunX509");
			trustManager.init(serverPub);
			SSLContext ssl = SSLContext.getInstance("TLS");
			ssl.init(clientKeyManager.getKeyManagers(), trustManager.getTrustManagers(), SecureRandom.getInstance("SHA1PRNG"));
			socket = (SSLSocket) ssl.getSocketFactory().createSocket(InetAddress.getByName(RemoteHost), portNumber);
			socket.startHandshake();

//OLD NON CRYPT
			//socket = new Socket(InetAddress.getByName(RemoteHost), portNumber);
			//socket = ssf.createSocket(InetAddress.getByName(RemoteHost), portNumber);
			// end of NON CRYPT
			login_to_server(jObj);

		} catch (IOException e) {
			LOGGER.error("CANT CONNECT TO HOST " + e.getMessage());
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
			} else if (jObj.getString("Message").equals("LOGIN_FAILED")) {
				status_login = "Pogrešno korisničko ime ili lozinka";
				isConnected = false;
			}
		} else {
			LOGGER.info("ERROR IN CONNECTION (no return Message)");
		}

	}

	public Boolean get_connection_state() {
		return isConnected;
	}

}
