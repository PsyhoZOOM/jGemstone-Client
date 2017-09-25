package net.yuvideo.jgemstone.client.Controllers;

import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.IPTVPaketi;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 8/15/17.
 */
public class IPTVPaketiEditController implements Initializable {
    public TextField tNaziv;
    public TextField tCena;
    public Button bSnimi;
    public TextArea tOpis;
    public ComboBox<IPTVPaketi> cmbIPTVPakets;
    public Client client;
    public IPTVPaketi paket;
    public int paketEditID;
    public boolean edit;
    public TextField tPrekoracenjeIPTV;
    Stage stage;
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
        this.edit = false;

        cmbIPTVPakets.setConverter(new StringConverter<IPTVPaketi>() {
            @Override
            public String toString(IPTVPaketi object) {
                return object.getName();
            }

            @Override
            public IPTVPaketi fromString(String string) {
                IPTVPaketi paketi = new IPTVPaketi();
                paketi.setName(string);
                return paketi;
            }
        });

        cmbIPTVPakets.valueProperty().addListener(new ChangeListener<IPTVPaketi>() {
            @Override
            public void changed(ObservableValue<? extends IPTVPaketi> observable, IPTVPaketi oldValue, IPTVPaketi newValue) {
                tNaziv.setText(newValue.getName());
                tNaziv.setDisable(true);
                System.out.println(newValue.getIptv_id());
            }
        });
    }

    public void setData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "getIPTVPakets");
        jsonObject = client.send_object(jsonObject);

        ArrayList<IPTVPaketi> iptvPaketiArrayList = new ArrayList<>();
        JSONObject iptvPaketiObj;


        for (int i = 0; i < jsonObject.length(); i++) {
            iptvPaketiObj = (JSONObject) jsonObject.get(String.valueOf(i));
            IPTVPaketi pakets = new IPTVPaketi();
            pakets.setName(iptvPaketiObj.getString("name"));
            pakets.setExternal_id(iptvPaketiObj.getString("external_id"));
            pakets.setIptv_id(iptvPaketiObj.getInt("id"));
            if (edit) {
                if (iptvPaketiObj.getInt("id") == paket.getIptv_id()) {
                    pakets.setPrekoracenje(paket.getPrekoracenje());
                }
            }
            if (edit)
                iptvPaketiArrayList.add(paket);
            iptvPaketiArrayList.add(pakets);
        }

        ObservableList<IPTVPaketi> items = FXCollections.observableArrayList(iptvPaketiArrayList);
        cmbIPTVPakets.setItems(items);
        if (edit) {
            cmbIPTVPakets.setValue(paket);
            tPrekoracenjeIPTV.setText(String.valueOf(paket.getPrekoracenje()));
            tCena.setText(String.valueOf(paket.getCena()));
            tNaziv.setText(paket.getName());
        }
    }

    public void setItemsEdit() {
        setData();
        this.tNaziv.setText(paket.getName());
        this.tCena.setText(String.valueOf(paket.getCena()));
        for (IPTVPaketi pak : cmbIPTVPakets.getItems()) {
            if (pak.getExternal_id() == paket.getExternal_id()) {
                cmbIPTVPakets.setValue(pak);
            }
        }
        tPrekoracenjeIPTV.setText(String.valueOf(paket.getPrekoracenje()));
        this.tOpis.setText(paket.getDescription());


    }

    public void snimiPaket(ActionEvent actionEvent) {
        //ako nije izabran ni jedan paket izlazimo iz metode
        if (cmbIPTVPakets.getSelectionModel().getSelectedIndex() == -1)
            return;

        if (edit) {
            editIPTVPaket();
        } else {
            snimiNovIPTVPaket();
        }
    }

    private void editIPTVPaket() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "snimiEditIPTVPaket");
        jsonObject.put("id", paketEditID);
        jsonObject.put("external_id", cmbIPTVPakets.getValue().getExternal_id());
        jsonObject.put("iptv_id", cmbIPTVPakets.getValue().getIptv_id());
        jsonObject.put("name", cmbIPTVPakets.getValue().getName());
        jsonObject.put("cena", tCena.getText());
        jsonObject.put("opis", tOpis.getText());
        jsonObject.put("prekoracenje", tPrekoracenjeIPTV.getText());

        jsonObject = client.send_object(jsonObject);

        if (jsonObject.has("Error")) {
            AlertUser.error("GRESKA", jsonObject.getString("Error"));
        } else {
            AlertUser.info("IZMENE IZVRSENE", "PaketIzmenjen");
            stage = (Stage) bSnimi.getScene().getWindow();
            stage.close();
        }
    }

    private void snimiNovIPTVPaket() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "snimiNovIPTVPaket");
        jsonObject.put("external_id", cmbIPTVPakets.getValue().getExternal_id());
        jsonObject.put("iptv_id", cmbIPTVPakets.getValue().getIptv_id());
        try {
            jsonObject.put("cena", Double.valueOf(tCena.getText()));
        } catch (NumberFormatException e) {
            AlertUser.error("GRESKA", "Pogresan unos cene");
        }
        jsonObject.put("nazivPaketa", cmbIPTVPakets.getValue().getName());
        jsonObject.put("opis", tOpis.getText());
        jsonObject.put("prekoracenje", Integer.parseInt(tPrekoracenjeIPTV.getText()));

        jsonObject = client.send_object(jsonObject);
        if (jsonObject.has("Error")) {
            AlertUser.error("Greska", jsonObject.getString("Error"));
        } else {
            AlertUser.info("Paket kreiran", String.format("Paket %s je uspesno kreiran", tNaziv.getText()));
            stage = (Stage) bSnimi.getScene().getWindow();
            stage.close();
        }
    }


}
