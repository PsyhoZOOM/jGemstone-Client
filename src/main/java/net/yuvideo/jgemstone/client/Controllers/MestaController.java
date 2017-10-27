package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import net.yuvideo.jgemstone.client.classes.Adrese;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Mesta;
import org.controlsfx.control.Notifications;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by zoom on 1/10/17.
 */
public class MestaController implements Initializable {
    public Client client;

    public JFXTextField tMestoNaziv;
    public JFXTextField tMestoBroj;
    public JFXButton bAddMesto;
    public TableView tblMesto;
    public TableColumn colMesto;
    public TableColumn colMestoBroj;
    public JFXButton bMestoDelete;
    public JFXButton bMestoRefresh;
    public JFXTextField tAdresaNaziv;
    public JFXTextField tAdresaBroj;
    public JFXButton bAddAdress;
    public TableView tblAdrese;
    public TableColumn colAdresa;
    public TableColumn colAdresaBroj;
    public JFXButton bAdresaDelete;
    public JFXButton bAdresaRefresh;
    private JSONObject jObj;
    private Mesta mesta;
    private Adrese adrese;
    private ArrayList<Mesta> arrMesta;
    private ArrayList<Adrese> arrAdrese;
    private Alert alert;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colAdresa.setCellValueFactory(new PropertyValueFactory<Adrese, String>("nazivAdrese"));
        colAdresaBroj.setCellValueFactory(new PropertyValueFactory<Adrese, Integer>("brojAdrese"));
        colMesto.setCellValueFactory(new PropertyValueFactory<Mesta, String>("nazivMesta"));
        colMestoBroj.setCellValueFactory(new PropertyValueFactory<Mesta, Integer>("brojMesta"));


        tblMesto.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (tblMesto.getSelectionModel().getSelectedIndex() != -1) {
                    osveziAdresu(null);
                }
            }
        });


    }

    @FXML
    private void dodajMesto(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "addMesto");
        jObj.put("nazivMesta", tMestoNaziv.getText());
        jObj.put("brojMesta", tMestoBroj.getText());

        jObj = client.send_object(jObj);
        osveziMesto(null);
    }


    public void izbrisiMesto(ActionEvent actionEvent) {
        if (tblMesto.getSelectionModel().getSelectedIndex() == -1) {
            Notifications.create()
                    .title("Greska")
                    .text("Nije izabrano mesto za brisanje")
                    .hideAfter(Duration.seconds(6))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        }


        ButtonType bYes = new ButtonType("Da", ButtonBar.ButtonData.YES);
        ButtonType bNo = new ButtonType("Ne", ButtonBar.ButtonData.NO);

        alert = new Alert(Alert.AlertType.CONFIRMATION, "Izbrisano mesto ce takodje izbrisati " +
                "sve ulice vezane za to mesto", bYes, bNo);

        alert.setTitle("Upozorenje");
        alert.setHeaderText("Da li ste sigurni da zelite da izbrisete mesto i ulice vezane za to mesto?");
        alert.initOwner(bMestoDelete.getScene().getWindow());
        alert.showAndWait();

        //If user select no then return from this method
        //else continue to delete
        if (alert.getResult() == bNo) {
            return;
        }

        mesta = (Mesta) tblMesto.getSelectionModel().getSelectedItem();
        jObj = new JSONObject();
        jObj.put("action", "DEL_MESTO");
        jObj.put("idMesta", mesta.getId());
        jObj = client.send_object(jObj);

        if (jObj.getString("Message").equals("MESTO_DELETED")) {
            osveziMesto(null);
        }
    }

    public void osveziMesto(ActionEvent actionEvent) {
        ObservableList<Mesta> observableListMesta = FXCollections.observableArrayList(get_mesta());
        tblMesto.setItems(observableListMesta);
    }

    private ArrayList<Mesta> get_mesta() {
        jObj = new JSONObject();
        jObj.put("action", "getMesta");

        jObj = client.send_object(jObj);

        arrMesta = new ArrayList<Mesta>();
        JSONObject jsonMesta;

        for (int i = 0; i < jObj.length(); i++) {
            jsonMesta = (JSONObject) jObj.get(String.valueOf(i));
            mesta = new Mesta();
            mesta.setId(jsonMesta.getInt("id"));
            mesta.setBrojMesta(jsonMesta.getString("brojMesta"));
            mesta.setNazivMesta(jsonMesta.getString("nazivMesta"));
            arrMesta.add(mesta);
        }

        return arrMesta;

    }

    private ArrayList<Adrese> get_adrese(int idMesta) {
        jObj = new JSONObject();
        jObj.put("action", "getAdrese");
        jObj.put("idMesta", idMesta);

        jObj = client.send_object(jObj);

        arrAdrese = new ArrayList<Adrese>();
        JSONObject jsonAdrese;

        for (int i = 0; i < jObj.length(); i++) {
            jsonAdrese = (JSONObject) jObj.get(String.valueOf(i));
            adrese = new Adrese();
            adrese.setId(jsonAdrese.getInt("id"));
            adrese.setNazivAdrese(jsonAdrese.getString("nazivAdrese"));
            adrese.setBrojAdrese(jsonAdrese.getString("brojAdrese"));
            adrese.setIdMesta(jsonAdrese.getInt("idMesta"));
            adrese.setNazivMesta(jsonAdrese.getString("nazivMesta"));
            adrese.setBrojMesta(jsonAdrese.getString("brojMesta"));
            arrAdrese.add(adrese);
        }
        return arrAdrese;
    }

    public void dodajAdresu(ActionEvent actionEvent) {

        if (tblMesto.getSelectionModel().getSelectedIndex() == -1) {
            Notifications.create()
                    .title("Greska")
                    .text("Morate izabrati mesto kome pripada adresa!")
                    .hideAfter(Duration.seconds(6))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        }


        Mesta mesto = (Mesta) tblMesto.getSelectionModel().getSelectedItem();

        jObj = new JSONObject();
        jObj.put("action", "addAdresa");
        jObj.put("nazivAdrese", tAdresaNaziv.getText());
        jObj.put("brojAdrese", tAdresaBroj.getText());
        jObj.put("brojMesta", mesto.getBrojMesta());
        jObj.put("idMesta", mesto.getId());
        jObj.put("nazivMesta", mesto.getNazivMesta());

        jObj = client.send_object(jObj);

        osveziAdresu(null);

    }

    public void izbrisiAdresu(ActionEvent actionEvent) {

        if (tblAdrese.getSelectionModel().getSelectedIndex() == -1) {
            Notifications.create()
                    .title("Greska")
                    .text("Morate izabrati adresu za brisanje!")
                    .hideAfter(Duration.seconds(6))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        }


        Optional<ButtonType> areYouSure = AlertUser.yesNo("BRISANJE ADERSE", "Da li ste sigurni da želite da izbrišete adresu?");
        if (AlertUser.NE == areYouSure.get())
            return;

        Adrese addrese = (Adrese) tblAdrese.getSelectionModel().getSelectedItem();

        jObj = new JSONObject();
        jObj.put("action", "delAdresa");
        jObj.put("id", addrese.getId());

        jObj = client.send_object(jObj);
        if (jObj.has("Message")) {
            if (jObj.getString("Message").equals("ADRESS_DELETED")) {
                osveziAdresu(null);
            }
        }

    }

    public void osveziAdresu(ActionEvent actionEvent) {
        if (tblMesto.getSelectionModel().getSelectedIndex() == -1) {
            Notifications.create()
                    .title("Greska")
                    .text("Morate izabrati mesto za prikaz adresa!")
                    .hideAfter(Duration.seconds(6))
                    .position(Pos.BOTTOM_RIGHT)
                    .showWarning();
            return;
        }

        mesta = (Mesta) tblMesto.getSelectionModel().getSelectedItem();

        ObservableList<Adrese> observableListAdrese = FXCollections.observableArrayList(get_adrese(mesta.getId()));
        tblAdrese.setItems(observableListAdrese);
    }

}
