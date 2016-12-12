package JGemstone.interfaces;

import JGemstone.Main;
import JGemstone.classes.Client;
import JGemstone.classes.Services;
import JGemstone.classes.messageS;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 9/2/16.
 */
public class EditServicesController implements Initializable {
    public Button bClose;
    public Button bSnimi;
    public JFXTextField tNaziv;
    public JFXTextField tCena;
    public JFXTextArea tOpis;

    public Client client;
    public int serviceID;
    public JFXCheckBox cbInternet;
    public JFXComboBox cmbInternet;
    public JFXCheckBox cbIPTV;
    public JFXComboBox cmbIPTV;
    public JFXCheckBox cbDTV;
    public JFXComboBox cmbDTV;
    public JFXCheckBox cbVOIP;
    public JFXComboBox cmbVOIP;
    Logger LOGGER = Main.LOGGER;
    //JSON
    JSONObject jObj;
    private messageS mes;
    private Services services;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void closeServices(ActionEvent actionEvent) {
        Stage stage = (Stage) bClose.getScene().getWindow();
        stage.close();
    }

    public void snimiServices(ActionEvent actionEvent) {

        jObj = new JSONObject();
        jObj.put("action", "save_service");
        jObj.put("naziv", tNaziv.getText());
        jObj.put("cena", Double.valueOf(tCena.getText()));
        jObj.put("opis", tOpis.getText());
        jObj.put("serviceId", serviceID);

        jObj = client.send_object(jObj);

        LOGGER.info(jObj.get("message"));
        closeServices(null);


    }


    public void show_services(){


        jObj = new JSONObject();

        jObj.put("action", "get_services_data");
        jObj.put("serviceId", serviceID);

        jObj = client.send_object(jObj);

        tNaziv.setText(jObj.getString("naziv"));
        tCena.setText(jObj.getString("cena"));
        tOpis.setText(jObj.getString("opis"));

    }
}
