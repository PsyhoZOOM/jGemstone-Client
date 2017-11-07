package net.yuvideo.jgemstone.client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.InternetPaketi;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 1/31/17.
 */
public class InternetPaketEditController implements Initializable {
    public TextField tNaziv;
    public TextField tBrzina;
    public TextArea tOpis;
    public Button bSnimi;
    public Button bClose;
    public Client client;
    public boolean edit = false;
    public int idRad;
    public int idPaket;
    public InternetPaketi paket;
    public Spinner spnCena;
    public Spinner spnPDV;
    public Spinner spnIdleTimeout;
    private ResourceBundle resource;
    private URL location;
    private JSONObject jObj;

    private SpinnerValueFactory.DoubleSpinnerValueFactory doubleSpinnerValueFactoryCena = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, Double.MAX_VALUE, 0.00);
    private SpinnerValueFactory.DoubleSpinnerValueFactory doubleSpinnerValueFactoryPDV = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, Double.MAX_VALUE, 0.00);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;
        this.location = location;

        spnPDV.setValueFactory(doubleSpinnerValueFactoryPDV);
        spnCena.setValueFactory(doubleSpinnerValueFactoryCena);

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
        jObj.put("cena", Double.valueOf(spnCena.getEditor().getText()));
        jObj.put("pdv", Double.valueOf(spnPDV.getEditor().getText()));
        jObj.put("opis", tOpis.getText());
        jObj.put("idleTimeout", spnIdleTimeout.getEditor().getText());

        jObj = client.send_object(jObj);

        if (jObj.has("Message")) {
            AlertUser.info("PAKET SNIMLJEN", "Paket je snimljen");
            close(null);
        } else if (jObj.has("ERROR")) {
            AlertUser.error("GRESKA", "Paket nije uspešno snimljen: " + jObj.get("Message"));
        }


    }

    private void snimiUpdate() {
        jObj = new JSONObject();
        jObj.put("action", "update_internet_paket");
        jObj.put("idPaket", paket.getId());
        jObj.put("naziv", paket.getNaziv());
        jObj.put("brzina", tBrzina.getText());
        jObj.put("cena", Double.valueOf(spnCena.getEditor().getText()));
        jObj.put("idleTimeout", spnIdleTimeout.getEditor().getText());
        jObj.put("pdv", Double.valueOf(spnPDV.getEditor().getText()));
        jObj.put("opis", tOpis.getText());
        jObj = client.send_object(jObj);

        if (jObj.has("Message")) {
            AlertUser.info("PAKET JE IZMENJEN", "Izmene snimljene");
            close(null);
        } else if (jObj.has("ERROR")) {
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
        spnCena.getEditor().setText(String.valueOf(paket.getCena()));
        spnIdleTimeout.getEditor().setText(paket.getIdleTimeout());
        spnPDV.getEditor().setText("0.00");
        tOpis.setText(paket.getOpis());


    }
}
