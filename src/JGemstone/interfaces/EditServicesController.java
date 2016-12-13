package JGemstone.interfaces;

import JGemstone.classes.Client;
import JGemstone.classes.Groups;
import JGemstone.classes.Services;
import JGemstone.classes.messageS;
import com.jfoenix.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.Notifications;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
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
    public JFXComboBox<Groups> cmbInternet;
    public JFXCheckBox cbIPTV;
    public JFXComboBox cmbIPTV;
    public JFXCheckBox cbDTV;
    public JFXComboBox cmbDTV;
    public JFXCheckBox cbVOIP;
    public JFXComboBox cmbVOIP;
    public JFXButton bActivateInternet;
    Logger LOGGER = LogManager.getLogger("EDIT_SERVICE");
    //JSON
    JSONObject jObj;
    private messageS mes;
    private Services services;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbInternet.setCellFactory(new Callback<ListView<Groups>, ListCell<Groups>>() {
            public ListCell<Groups> call(ListView<Groups> param) {
                return new ListCell<Groups>() {
                    @Override
                    protected void updateItem(Groups grupa, boolean empty) {
                        super.updateItem(grupa, empty);
                        if (!empty) {
                            setText(grupa.getGroupName());
                        } else {
                            setText("");
                        }

                    }
                };
            }
        });





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


    private JSONObject show_services() {


        jObj = new JSONObject();

        jObj.put("action", "get_services_data");
        jObj.put("serviceId", serviceID);

        jObj = client.send_object(jObj);
        return jObj;
    }

    public void show_data() {
        jObj = show_services();
        tNaziv.setText(jObj.getString("naziv"));
        tCena.setText(jObj.getString("cena"));
        tOpis.setText(jObj.getString("opis"));

        ObservableList<Groups> internet_groups = FXCollections.observableArrayList(get_internet_groups());
        cmbInternet.getItems().addAll(internet_groups);
        cmbInternet.setEditable(false);


    }


    private ArrayList<Groups> get_internet_groups() {
        JSONObject jgrupa;
        ArrayList<Groups> grupe = new ArrayList<Groups>();
        Groups grupa;
        jObj = new JSONObject();

        jObj.put("action", "getInternetGroups");

        jObj = client.send_object(jObj);

        if (jObj.has("Message")) {
            Notifications.create()
                    .title("GREŠKA")
                    .text("NE POSTOJE GRUPE")
                    .hideAfter(Duration.seconds(3.0))
                    .showError();
            return null;
        }
        LOGGER.info("GROUP LENG: " + jObj.length());
        for (int i = 0; i < jObj.length(); i++) {
            jgrupa = (JSONObject) jObj.get(String.valueOf(i));
            grupa = new Groups();
            grupa.setId(jgrupa.getInt("id"));
            grupa.setGroupName(jgrupa.getString("groupName"));
            grupa.setBr(i);
            grupa.setPrepaid(jgrupa.getInt("prepaid"));
            grupa.setOpis(jgrupa.getString("opis"));
            grupa.setCena(jgrupa.getDouble("cena"));
            grupe.add(grupa);
        }

        LOGGER.info("GRUPE ARR: " + grupe);
        return grupe;


    }

    public void activateInternet(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "activate_internet");

        //jObj = client.send_object(jObj);

    }


}
