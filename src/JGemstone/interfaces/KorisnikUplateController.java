package JGemstone.interfaces;

import JGemstone.classes.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

/**
 * Created by zoom on 9/7/16.
 */
public class KorisnikUplateController implements Initializable {
    public Button bClose;
    public ResourceBundle resource;
    public Client client;
    DecimalFormat df = new DecimalFormat("#,##0.00");
    private Logger LOGGER = LogManager.getLogger("USER_PAYMENTS");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;
    }
}




