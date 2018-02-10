package net.yuvideo.jgemstone.client.Controllers;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.Client;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 4/7/17.
 */
public class InternetMainController implements Initializable {
    public Client client;
    public Stage stage;
    private ResourceBundle resources;
    private URL location;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
    }
}