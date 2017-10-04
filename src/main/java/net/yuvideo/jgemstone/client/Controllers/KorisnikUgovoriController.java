package net.yuvideo.jgemstone.client.Controllers;

import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.ugovori_types;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import org.json.JSONObject;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zoom on 2/9/17.
 */
public class KorisnikUgovoriController implements Initializable {
    public TextField tBrUgovora;
    public ComboBox cmbTamplate;
    public DatePicker dtpTrajanjeOd;
    public DatePicker dtpTrajanjeDo;
    public TextArea tOPis;
    public Button bDodaj;
    public TableView tblUgovori;
    public TableColumn cBr;
    public TableColumn cNaziv;
    public TableColumn cTrajanje;
    public TableColumn cOd;
    public TableColumn cDo;
    public TableColumn cOpis;
    public Button bObrisi;
    public Button bIzmeni;
    public Client client;
    public int userID;
    public Button stampaUgovora;
    private URL location;
    private ResourceBundle resources;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
    private JSONObject jObj;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        dtpTrajanjeOd.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                if (object == null)
                    return "";
                return dateTimeFormatter.format(object);
            }

            @Override
            public LocalDate fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(string, dateTimeFormatter);
            }
        });

        dtpTrajanjeDo.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                if (object == null)
                    return "";
                return dateTimeFormatter.format(object);
            }

            @Override
            public LocalDate fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(string, dateTimeFormatter);
            }
        });

        cBr.setCellValueFactory(new PropertyValueFactory<ugovori_types, Integer>("br"));
        cNaziv.setCellValueFactory(new PropertyValueFactory<ugovori_types, String>("Naziv"));
        cOd.setCellValueFactory(new PropertyValueFactory<ugovori_types, String>("pocetakUgovora"));
        cDo.setCellValueFactory(new PropertyValueFactory<ugovori_types, String>("krajUgovora"));
        cOpis.setCellValueFactory(new PropertyValueFactory<ugovori_types, String>("Komentar"));


    }

    public void set_data() {
        ObservableList dataUgovoryTypes = FXCollections.observableArrayList(get_ugovori_types());
        cmbTamplate.setItems(dataUgovoryTypes);

        ObservableList tableUgovori = FXCollections.observableArrayList(get_ugovori_usera());
        tblUgovori.setItems(tableUgovori);

    }

    private ArrayList<ugovori_types> get_ugovori_usera() {
        jObj = new JSONObject();
        jObj.put("action", "get_ugovori_user");
        jObj.put("userID", userID);

        jObj = client.send_object(jObj);

        ArrayList<ugovori_types> ugovoriArr = new ArrayList();
        JSONObject ugovori;
        ugovori_types ugovor;

        for (int i = 0; i < jObj.length(); i++) {
            ugovori = (JSONObject) jObj.get(String.valueOf(i));
            ugovor = new ugovori_types();
            ugovor.setId(ugovori.getInt("id"));
            ugovor.setBr(ugovori.getString("brojUgovora"));
            ugovor.setNaziv(ugovori.getString("naziv"));
            ugovor.setVrsta(ugovori.getString("vrsta"));
            ugovor.setTextUgovora(ugovori.getString("textUgovora"));
            ugovor.setKomentar(ugovori.getString("komentar"));
            ugovor.setPocetakUgovora(ugovori.getString("pocetakUgovora"));
            ugovor.setKrajUgovora(ugovori.getString("krajUgovora"));
            ugovor.setUserID(ugovori.getInt("userID"));
            ugovor.setServiceID(ugovori.getInt("serviceID"));
            ugovoriArr.add(ugovor);
        }

        return ugovoriArr;
    }

    private ArrayList<ugovori_types> get_ugovori_types() {
        jObj = new JSONObject();
        jObj.put("action", "get_ugovori");

        jObj = client.send_object(jObj);

        ArrayList<ugovori_types> ugovoriArr = new ArrayList();
        ugovori_types ugovor;
        JSONObject ugovori;

        for (int i = 0; i < jObj.length(); i++) {
            ugovor = new ugovori_types();
            ugovori = (JSONObject) jObj.get(String.valueOf(i));
            ugovor.setId(ugovori.getInt("idUgovora"));
            ugovor.setNaziv(ugovori.getString("nazivUgovora"));
            ugovor.setTextUgovora(ugovori.getString("textUgovora"));
            ugovoriArr.add(ugovor);
        }
        return ugovoriArr;
    }

    public void addNewUgovorShow(ActionEvent actionEvent) {

        jObj = new JSONObject();
        jObj.put("action", "check_brUgovora_busy");
        jObj.put("brojUgovora", tBrUgovora.getText());
        jObj = client.send_object(jObj);
        if(jObj.has("ERROR")){
            AlertUser.error("GRESKA", jObj.getString("ERROR"));
            return;
        }

        ugovori_types ugovor = (ugovori_types) cmbTamplate.getValue();
        ugovor.setPocetakUgovora(dtpTrajanjeOd.getValue().format(dateTimeFormatter));
        ugovor.setKrajUgovora(dtpTrajanjeDo.getValue().format(dateTimeFormatter));
        ugovor.setBr(tBrUgovora.getText());
        ugovor.setKomentar(tOPis.getText());
        ugovor.setUserID(userID);


        NewInterface ugovoriEditInterface = new NewInterface("fxml/KorisnikUgovorEdit.fxml", "Nov Ugovor", resources);
        KorisnikUgovorEditController korisnikUgovorEditController = ugovoriEditInterface.getLoader().getController();
        korisnikUgovorEditController.ugovor = ugovor;
        korisnikUgovorEditController.client = client;
        korisnikUgovorEditController.setData();

        ugovoriEditInterface.getStage().showAndWait();


        //cleaning a forms
        tOPis.setText("");
        tBrUgovora.setText("");
        cmbTamplate.getItems().removeAll();
        cDo.setText("");
        cOd.setText("");


        set_data();


    }

    public void izmeniUgovor(ActionEvent actionEvent) {
        ugovori_types ugovor = (ugovori_types) tblUgovori.getSelectionModel().getSelectedItem();
        NewInterface ugovoriEditInterface = new NewInterface("fxml/KorisnikUgovorEdit.fxml", "Izmena Ugovora", resources);
        KorisnikUgovorEditController korisnikUgovorEditController = ugovoriEditInterface.getLoader().getController();
        korisnikUgovorEditController.ugovor = ugovor;
        korisnikUgovorEditController.editUgovor = true;
        korisnikUgovorEditController.client = client;
        korisnikUgovorEditController.setData();
        ugovoriEditInterface.getStage().showAndWait();
        set_data();
    }

    public void stampajUgovor(ActionEvent actionEvent) {
        NewInterface ugovorStampaInterface = new NewInterface("fxml/UgovorStampa.fxml", "Stampa Ugovora", resources);
        UgovorStampaController ugovorStampaController = ugovorStampaInterface.getLoader().getController();
        ugovorStampaController.client = client;
        ugovorStampaController.ugovor = (ugovori_types) tblUgovori.getSelectionModel().getSelectedItem();
        ugovorStampaController.show_data();
        ugovorStampaInterface.getStage().showAndWait();


    }
}
