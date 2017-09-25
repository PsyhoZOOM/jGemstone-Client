package net.yuvideo.jgemstone.client.classes;

import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

/**
 * Created by zoom on 12/11/16.
 */
public class checkAlive implements Runnable {
    public Boolean Running = true;
    public Label istatusConn;
    JSONObject jPingOBj;
    JSONObject jPongResponse;
    Logger LOGGER = LogManager.getLogger("CHECK_PING");
    private Client client;

    public checkAlive(Client client) {
        this.client = client;
    }


    public void check_connection() {
        jPingOBj = new JSONObject();
        jPingOBj.put("action", "PING");
        jPongResponse = client.send_object(jPingOBj);
        LOGGER.info(jPongResponse);
        if (jPongResponse.has("Message")) {
            istatusConn.setText("Connected");
        } else {
            istatusConn.setText("Disconnected");
            istatusConn.setText("Connecting");
            client.close();
            client.main_run();
        }

    }


    @Override
    public void run() {

        while (true) {

            try {
                if (client.get_connection_state() == null) client.main_run();
                check_connection();
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                LOGGER.info("ERROR CONNECTION: " + e.getMessage());
            }
        }

    }
}
