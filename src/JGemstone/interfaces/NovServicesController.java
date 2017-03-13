package JGemstone.interfaces;

import JGemstone.classes.Client;
import JGemstone.classes.Services;
import JGemstone.classes.messageS;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 9/2/16.
 */
public class NovServicesController implements Initializable {
    public Button bClose;
    public Button bSnimi;
    public TextField tNaziv;
    public TextField tCena;
    public TextField tOpis;

    public Client client;
    Logger LOGGER = LogManager.getLogger("NEW_SERVICES");
    //JSON
    JSONObject jObj;
    private Services service;
    private messageS mes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void closeServices(ActionEvent actionEvent) {
        Stage stage = (Stage) bClose.getScene().getWindow();
        stage.close();
    }

    public void snimiServices(ActionEvent actionEvent) {
        service = new Services();
        service.setNaziv(tNaziv.getText());
        service.setCena(Double.valueOf(tCena.getText()));
        service.setOpis(tOpis.getText());

        mes = new messageS();
        mes.setAction("new_service");
        mes.setServices(service);

        jObj = new JSONObject();
        jObj.put("action", "new_service");
        jObj.put("naziv", tNaziv.getText());
        jObj.put("cena", Double.valueOf(tCena.getText()));
        jObj.put("opis", tOpis.getText());

        jObj = client.send_object(jObj);

        LOGGER.info(jObj.get("message"));

        closeServices(null);

    }
}
