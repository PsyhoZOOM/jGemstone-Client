package JGemstone.interfaces;

import JGemstone.classes.Client;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by zoom on 11/16/16.
 */
public class UgovoriEditController implements Initializable{
    public Client client;
    public ResourceBundle resource;
    public HTMLEditor htmlUgovor;
    public Button bSnimi;
    public Button bClose;

    private Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;
        bClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage = (Stage) bClose.getScene().getWindow();
                stage.close();

            }
        });


    }

    public void snimiUgovor(ActionEvent actionEvent) {

    }
}
