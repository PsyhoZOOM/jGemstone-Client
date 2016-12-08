package JGemstone.classes;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by zoom on 12/3/16.
 */
public class runningCheckThread implements  Runnable {

    private Client client;
    boolean connected = false;
    private Logger LOGGER = LogManager.getLogger("CLIENT_THREAD");

    public runningCheckThread(Client client) {
        this.client = client;
    }

    public Client get_client(){
        return this.client;
    }




    @Override
    public void run() {
        client.main_run();
        while (true){

            if(client.socket.isClosed()){
                LOGGER.info("connecting");
                connected = false;
                client.close();
                client.main_run();
            }else{
                LOGGER.info("COnnected");
                connected = true;

            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
