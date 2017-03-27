package JGemstone.classes;

import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by zoom on 8/8/16.
 */
public class Client {
    public int portNumber;
    public InetAddress inetAddress;
    public String RemoteHost;
    public String status_login;
    public Label status_conn = new Label();
    Socket socket;
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


        }
        return rObj;
    }

    private JSONObject get_object() {

        if (Isr == null) {
            try {

                Isr = new InputStreamReader(socket.getInputStream());
                Bfr = new BufferedReader(Isr);
            } catch (IOException e) {
                LOGGER.error("ERROR AT InputStreamReader: " + e.getMessage());
                isConnected = false;
            }
        }

        try {
            jObj = new JSONObject(Bfr.readLine());
        } catch (IOException e1) {
            LOGGER.info("E1_MESSAGE" + e1.getMessage());
            isConnected = false;
        } catch (NullPointerException e2) {
            LOGGER.info("E2_MESSAGE" + e2.getMessage());
            isConnected = false;
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
        mess.setAction("login");

        jObj.put("action", "login");
        jObj.put("username", local_settings.getLocalUser());
        jObj.put("password", local_settings.getLocalPassword());

        try {

            socket = new Socket(InetAddress.getByName(RemoteHost), portNumber);
            //socket = ssf.createSocket(InetAddress.getByName(RemoteHost), portNumber);
            login_to_server();

        } catch (IOException e) {
            LOGGER.error("CANT CONNECT TO HOST " + e.getMessage());
            isConnected = false;
            status_login = e.getMessage().toString();
            try {
                if (!socket.isClosed())
                    socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }

    private void login_to_server() {

        if (Osw == null) {
            try {
                //for json
                //Osw = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
                Osw = new OutputStreamWriter(socket.getOutputStream());
                Bfw = new BufferedWriter(Osw);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        try {
            //new json
            Bfw.write(jObj.toString());
            Bfw.newLine();
            Bfw.flush();
        } catch (IOException e) {
            LOGGER.info("NOOO");
            e.printStackTrace();

        }
        jObj = get_object();
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
