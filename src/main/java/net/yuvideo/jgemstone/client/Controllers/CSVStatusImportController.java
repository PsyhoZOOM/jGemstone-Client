package net.yuvideo.jgemstone.client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.Client;

import java.net.URL;
import java.util.ResourceBundle;

public class CSVStatusImportController implements Initializable {
    public Label lImportCSV;
    public ProgressBar pProgress;
    public Button bClose;
    private URL location;
    private ResourceBundle resources;
    private Client client;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
        bClose.setDisable(true);
    }

    public void CSVStatusImportController(Client client) {
        this.client = client;
    }

    public void zatvori(ActionEvent actionEvent) {
        Stage stage = (Stage) bClose.getScene().getWindow();
        stage.close();
    }

}
