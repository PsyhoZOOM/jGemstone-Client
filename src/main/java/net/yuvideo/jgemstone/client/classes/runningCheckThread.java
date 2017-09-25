package net.yuvideo.jgemstone.client.classes;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by zoom on 12/3/16.
 */
public class runningCheckThread implements Runnable {

    public boolean Running;
    boolean connected = false;
    private Client client;
    private Logger LOGGER = LogManager.getLogger("CLIENT_THREAD");

    public runningCheckThread(Client client) {
        this.client = client;
    }

    public Client get_client() {
        return this.client;
    }


    @Override
    public void run() {
        client.main_run();
        while (Running) {

            if (client.socket.isClosed()) {
                LOGGER.info("connecting");
                connected = false;
                client.close();
                client.main_run();
            } else {
                LOGGER.info("COnnected");
                connected = true;

            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Running = false;
                e.printStackTrace();
            }
        }
    }
}
