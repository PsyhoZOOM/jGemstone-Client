package net.yuvideo.jgemstone.client.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.yuvideo.jgemstone.client.classes.*;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 10/31/17.
 */
public class FakturePrikazController implements Initializable {

    private final DecimalFormat df = new DecimalFormat("0.00");
    public Client client;
    public Users user;
    public Button bPrikaziFakturu;
    @FXML
    TableView<Fakture> tblFakture;
    @FXML
    TableColumn<Fakture, Integer> cBr;
    @FXML
    TableColumn<Fakture, String> cDatum;
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        cBr.setCellValueFactory(new PropertyValueFactory<>("brFakture"));
        cDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));

    }

    public void prikaziFakturu(ActionEvent event) {
        NewInterface faktureInterface = new NewInterface("fxml/Fakture.fxml", "FAKTURE: " + user.getIme(), resources);
        FaktureController faktureController = faktureInterface.getLoader().getController();
        faktureController.client = this.client;
        faktureController.userData = user;
        faktureController.faktura = tblFakture.getSelectionModel().getSelectedItem();
        faktureController.setData();
        faktureInterface.getStage().showAndWait();

    }

    public void set_data() {
        ObservableList<Fakture> data = FXCollections.observableArrayList(getFaktureData());
        tblFakture.setItems(data);

    }

    private ArrayList<Fakture> getFaktureData() {
        JSONObject jObj = new JSONObject();
        jObj.put("action", "get_uniqueFakture");
        jObj.put("userID", user.getId());
        jObj = client.send_object(jObj);

        if (jObj.has("ERROR")) {
            AlertUser.error("GRESKA", jObj.getString("ERROR"));
            return null;
        }

        Fakture fakture;
        ArrayList<Fakture> faktureArr = new ArrayList<>();

        for (int i = 0; i < jObj.length(); i++) {
            JSONObject faktureObj = (JSONObject) jObj.get(String.valueOf(i));
            fakture = new Fakture();
            fakture.setId(faktureObj.getInt("id"));
            fakture.setBrFakture(faktureObj.getInt("br"));
            fakture.setNaziv(faktureObj.getString("naziv"));
            fakture.setJedMere(faktureObj.getString("jedMere"));
            fakture.setKolicina(faktureObj.getInt("kolicina"));
            double cenaBezPDV = faktureObj.getDouble("cenaBezPDV");
            double pdv = faktureObj.getDouble("pdv");
            double cenaSaPDV = cenaBezPDV + valueToPercent.getDiffValue(cenaBezPDV, pdv);
            fakture.setVrednostBezPDV(cenaBezPDV);
            fakture.setIznosPDV(pdv);
            fakture.setVrednostSaPDV(cenaSaPDV);
            fakture.setOperater(faktureObj.getString("operater"));
            fakture.setUserId(faktureObj.getInt("userID"));
            fakture.setDatum(faktureObj.getString("datum"));
            fakture.setGodina(faktureObj.getString("godina"));
            fakture.setMesec(faktureObj.getString("mesec"));
            faktureArr.add(fakture);
        }

        return faktureArr;

    }
}
