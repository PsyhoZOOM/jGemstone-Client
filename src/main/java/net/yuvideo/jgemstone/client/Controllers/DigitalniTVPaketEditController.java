package net.yuvideo.jgemstone.client.Controllers;

import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.digitalniTVPaket;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Created by zoom on 2/2/17.
 */
public class DigitalniTVPaketEditController implements Initializable {
    public Button bClose;
    public TextField tNaziv;
    public TextField tPaketID;
    public TextField tCena;
    public TextArea tOpis;
    public Button bSnimi;
    public Client client;
    public digitalniTVPaket dtvPaket;
    ResourceBundle resources;
    URL location;
    boolean edit = false;
    JSONObject jObj;
    Logger LOGGER = Logger.getLogger("DTV_EDIT");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) bClose.getScene().getWindow();
        stage.close();
    }

    public void snimi(ActionEvent actionEvent) {
        if (edit) {
            editSnimi();
        } else {
            novSnimi();
        }
    }

    private void novSnimi() {
        jObj = new JSONObject();
        jObj.put("action", "add_dtv_paket");
        jObj.put("naziv", tNaziv.getText());
        jObj.put("cena", Double.valueOf(tCena.getText()));
        jObj.put("opis", tOpis.getText());
        jObj.put("idPaket", Integer.valueOf(tPaketID.getText()));

        jObj = client.send_object(jObj);

        LOGGER.info(jObj.getString("Message"));

        if (jObj.getString("Message").equals("DTV_PAKET_SAVED")) {
            AlertUser.info("DTV Paket", "DTV Paket je kreiran");
            close(null);
        } else {
            AlertUser.error("GRESKA!", jObj.getString("Error"));
        }


    }

    private void editSnimi() {
        jObj = new JSONObject();
        jObj.put("action", "edit_dtv_paket");
        jObj.put("id", dtvPaket.getId());
        jObj.put("naziv", tNaziv.getText());
        jObj.put("cena", Double.valueOf(tCena.getText()));
        jObj.put("opis", tOpis.getText());
        jObj.put("idPaket", Integer.valueOf(tPaketID.getText()));


        jObj = client.send_object(jObj);


        if (jObj.has("Error")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));

        } else {
            AlertUser.info("INFORMACIJA", "Izmene snimljene");
            close(null);
        }

    }

    public void show_data() {
        tNaziv.setText(dtvPaket.getNaziv());
        tCena.setText(String.valueOf(dtvPaket.getCena()));
        tOpis.setText(dtvPaket.getOpis());
        tPaketID.setText(String.valueOf(dtvPaket.getPaketID()));


    }
}
