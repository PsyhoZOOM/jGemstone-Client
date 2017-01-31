package JGemstone.interfaces;

import JGemstone.classes.Client;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 1/31/17.
 */
public class OperaterDozvoleController implements Initializable {
    public Client client;
    ResourceBundle resource;
    URL location;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resource = resources;

    }


}
