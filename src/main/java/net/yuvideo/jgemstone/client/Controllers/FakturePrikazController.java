package net.yuvideo.jgemstone.client.Controllers;

import javafx.fxml.Initializable;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Users;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 10/31/17.
 */
public class FakturePrikazController implements Initializable {
    public Client client;
    public Users user;
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
    }
}
