package net.yuvideo.jgemstone.client.Controllers;

import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.InternetPaketi;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 1/31/17.
 */
public class InternetPaketEditController implements Initializable {
    public TextField tNaziv;
    public TextField tBrzina;
    public TextField tCena;
    public TextField tTimeOver;
    public TextArea tOpis;
    public Button bSnimi;
    public Button bClose;
    public TextField tIdleTimout;
    public Client client;
    public boolean edit = false;
    public int idRad;
    public int idPaket;
    public InternetPaketi paket;
    private ResourceBundle resource;
    private URL location;
    private Logger LOGGER = LogManager.getLogger("INTERNET_PAKET_EDIT");
    private JSONObject jObj;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;
        this.location = location;


    }

    public void savePaket(ActionEvent actionEvent) {
        if (edit) {
            snimiUpdate();
        } else {
            snimiNov();
        }
    }

    private void snimiNov() {
        jObj = new JSONObject();
        jObj.put("action", "snimi_internet_paket");
        jObj.put("naziv", tNaziv.getText());
        jObj.put("brzina", tBrzina.getText());
        jObj.put("cena", Double.valueOf(tCena.getText()));
        jObj.put("prekoracenje", Integer.valueOf(tTimeOver.getText()));
        jObj.put("opis", tOpis.getText());
        jObj.put("idleTimeout", tIdleTimout.getText());

        jObj = client.send_object(jObj);

        if (jObj.has("Message")) {
            AlertUser.info("PAKET SNIMLJEN", "Paket je snimljen");
            close(null);
        } else if (jObj.has("Error")) {
            AlertUser.error("GRESKA", "Paket nije uspešno snimljen: " + jObj.get("Message"));
        }


    }

    private void snimiUpdate() {
        jObj = new JSONObject();
        jObj.put("action", "update_internet_paket");
        jObj.put("idPaket", paket.getId());
        jObj.put("naziv", paket.getNaziv());
        jObj.put("brzina", tBrzina.getText());
        jObj.put("cena", Double.valueOf(tCena.getText()));
        jObj.put("prekoracenje", Integer.valueOf(tTimeOver.getText()));
        jObj.put("idleTimeout", tIdleTimout.getText());
        jObj.put("opis", tOpis.getText());
        jObj = client.send_object(jObj);

        if (jObj.has("Message")) {
            AlertUser.info("PAKET JE IZMENJEN", "Izmene snimljene");
            close(null);
        } else if (jObj.has("Error")) {
            AlertUser.error("GRESKA", "Greska: " + jObj.get("Error"));
        }

    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) bClose.getScene().getWindow();
        stage.close();
    }

    public void show_data() {
        tNaziv.setEditable(false);
        tNaziv.setText(paket.getNaziv());
        tBrzina.setText(paket.getBrzina());
        tCena.setText(String.valueOf(paket.getCena()));
        tTimeOver.setText(String.valueOf(paket.getPrekoracenje()));
        tIdleTimout.setText(paket.getIdleTimeout());
        tOpis.setText(paket.getOpis());


    }
}
