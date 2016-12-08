package JGemstone.classes;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.net.SocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Created by zoom on 8/8/16.
 */
public class Client {
    public int portNumber;
    Socket socket;
    SocketFactory ssf = SSLSocketFactory.getDefault();
    public InetAddress inetAddress;
    public String RemoteHost;

    InputStreamReader Isr;
    OutputStreamWriter Osw;

    BufferedReader Bfr;
    BufferedWriter Bfw;

    private db_connection db_conn = new db_connection();
    private Settings local_settings;
    private Boolean isConnected;

    messageS checkLive = new messageS();
    messageS mess = new messageS();

    Logger LOGGER = LogManager.getLogger("CLIENT");

    //JSON
    JSONObject jObj = new JSONObject();

    public JSONObject send_object(JSONObject rObj) {
        try {
            Bfw.write(rObj.toString());
            Bfw.newLine();
            Bfw.flush();
            rObj = new JSONObject();
            rObj = get_object();


        } catch (IOException e) {
            //System.out.println(e.getMessage());
            LOGGER.log(Level.ERROR, e.getMessage());
            System.exit(0);
        }
        return rObj;
    }

    private JSONObject get_object() {

        if (Isr == null) {
            try {

                Isr = new InputStreamReader(socket.getInputStream());
                Bfr = new BufferedReader(Isr);
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
                isConnected =false;
            }
        }

        try {

                jObj = new JSONObject(Bfr.readLine());
            } catch (IOException e1) {
            e1.printStackTrace();
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

    public void main_run()  {
        db_conn.init_database();
        local_settings = db_conn.local_settings;
        db_conn.close_db();
        RemoteHost = local_settings.getREMOTE_HOST();
        portNumber = local_settings.getREMOTE_PORT();
        mess.setAction("login");
        mess.setUsername(local_settings.getLocalUser());
        mess.setPassword(local_settings.getLocalPassword());

        jObj.put("action", "login");
        jObj.put("username", local_settings.getLocalUser());
        jObj.put("password", local_settings.getLocalPassword());

        try {
            socket = new Socket(InetAddress.getByName(RemoteHost), portNumber);
            login_to_server();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void login_to_server() {


        if (Osw == null) {
            try {
                //for json
                Osw = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
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
            e.printStackTrace();
        }
        jObj = get_object();
        if (jObj.has("Message")) {
            LOGGER.info(jObj.getString("Message"));
        }else{
            LOGGER.info("ERROR IN CONNECTION (no return Message)");
        }

    }

}
