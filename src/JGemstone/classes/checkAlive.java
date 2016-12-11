package JGemstone.classes;

import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.sql.Time;

/**
 * Created by zoom on 12/11/16.
 */
public class checkAlive implements Runnable {
    public Label lStatusConnection;
    Time latency;
    Boolean connected = false;
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
        //lStatusConnection.setText("Povezan sa serverom");
//lStatusConnection.setText("Diskonektovan! Rekonektujem se..");
        connected = jPongResponse.getString("Message").equals("PONG");

    }


    @Override
    public void run() {

        while (true) {
            check_connection();
            LOGGER.info("CONNECTION STATE: " + connected);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
