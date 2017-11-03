package net.yuvideo.jgemstone.client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.Users;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 10/31/17.
 */
public class FakturePrikazController implements Initializable {
    public Client client;
    public Users user;
    public Button bPrikaziFakturu;
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
    }

    public void prikaziFakturu(ActionEvent event) {
        NewInterface faktureInterface = new NewInterface("fxml/Fakture.fxml", "FAKTURE: " + user.getIme(), resources);
        FaktureController faktureController = faktureInterface.getLoader().getController();
        faktureController.client = this.client;
        faktureController.userData = user;
        faktureInterface.getStage().showAndWait();

    }
}
