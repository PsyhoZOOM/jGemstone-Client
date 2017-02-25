package JGemstone.interfaces;

import JGemstone.classes.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zoom on 2/23/17.
 */
public class BoxPaketController implements Initializable {
    public TextField tNazivPaketa;
    public ComboBox<digitalniTVPaket> cmbDTVNaziv;
    public ComboBox<InternetPaketi> cmbInternetNaziv;
    public ComboBox cmbNazivIPTV;
    public ComboBox cmbNazivFiksnaTel;
    public TextField tCenaBox;
    public Button bSnimiBox;
    public TableView<BoxPaket> tblBox;
    public TableColumn cNaziv;
    public TableColumn cDTV;
    public TableColumn cNET;
    public TableColumn cIPTV;
    public TableColumn cFiksna;

    public Client client;
    private URL location;
    private ResourceBundle resource;
    private JSONObject jObj;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resource = resource;

        cNaziv.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("naziv"));
        cDTV.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("DTV_naziv"));
        cNET.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("NET_naziv"));
        cIPTV.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("IPTV_naziv"));
        cFiksna.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("FIKSNA_naziv"));


    }

    public void set_data() {
        ObservableList tablebox = FXCollections.observableArrayList(get_paketBox());
        tblBox.setItems(tablebox);

        ObservableList internetpaket = FXCollections.observableArrayList(get_internet_paketi());
        cmbInternetNaziv.setItems(internetpaket);

        ObservableList dtvpaket = FXCollections.observableArrayList(get_dtv_paketi());
        cmbDTVNaziv.setItems(dtvpaket);


    }


    private ArrayList<digitalniTVPaket> get_dtv_paketi() {
        jObj = new JSONObject();
        jObj.put("action", "getDigitalTVPaketi");

        jObj = client.send_object(jObj);

        digitalniTVPaket digitalniTVPaket;
        ArrayList<digitalniTVPaket> digitalniTVPaketArrayList = new ArrayList<>();
        JSONObject digitalniObj;

        for (int i = 0; i < jObj.length(); i++) {
            digitalniTVPaket = new digitalniTVPaket();
            digitalniObj = (JSONObject) jObj.get(String.valueOf(i));
            digitalniTVPaket.setId(digitalniObj.getInt("id"));
            digitalniTVPaket.setNaziv(digitalniObj.getString("naziv"));
            digitalniTVPaket.setCena(digitalniObj.getDouble("cena"));
            digitalniTVPaket.setPaketID(digitalniObj.getInt("idPaket"));
            digitalniTVPaket.setOpis(digitalniObj.getString("opis"));
            digitalniTVPaket.setId(digitalniObj.getInt("prekoracenje"));
            digitalniTVPaketArrayList.add(digitalniTVPaket);

        }
        return digitalniTVPaketArrayList;
    }


    private ArrayList<InternetPaketi> get_internet_paketi() {
        jObj = new JSONObject();
        jObj.put("action", "get_internet_paketi");

        jObj = client.send_object(jObj);

        InternetPaketi internetPaketi;
        ArrayList<InternetPaketi> internetPaketisArray = new ArrayList<>();
        JSONObject internetObj;

        for (int i = 0; i < jObj.length(); i++) {
            internetPaketi = new InternetPaketi();
            internetObj = (JSONObject) jObj.get(String.valueOf(i));
            internetPaketi.setId(internetObj.getInt("id"));
            internetPaketi.setNaziv(internetObj.getString("naziv"));
            internetPaketi.setBrzina(internetObj.getString("brzina"));
            internetPaketi.setPrekoracenje(internetObj.getInt("prekoracenje"));
            internetPaketi.setIdleTimeout(internetObj.getString("idleTimeout"));
            internetPaketisArray.add(internetPaketi);
        }

        return internetPaketisArray;
    }


    private ArrayList<BoxPaket> get_paketBox() {

        jObj = new JSONObject();
        jObj.put("action", "get_paket_box");
        jObj = client.send_object(jObj);

        BoxPaket paketBox;
        ArrayList<BoxPaket> paketBoxesArr = new ArrayList<>();
        JSONObject paketObj;

        for (int i = 0; i < jObj.length(); i++) {
            paketBox = new BoxPaket();
            paketObj = (JSONObject) jObj.get(String.valueOf(i));
            paketBox.setId(paketObj.getInt("id"));
            paketBox.setNaziv(paketObj.getString("naziv"));
            if (paketObj.has("DTV_naziv")) {
                paketBox.setDTV(paketObj.getInt("DTV_id"));
                paketBox.setDTV_naziv(paketObj.getString("DTV_naziv"));
            }
            if (paketObj.has("NET_id")) {
                paketBox.setNET(paketObj.getInt("NET_id"));
                paketBox.setNET_naziv(paketObj.getString("NET_naziv"));
            }
            if (paketObj.has("IPTV_id")) {
                paketBox.setIPTV(paketObj.getInt("IPTV_id"));
                paketBox.setIPTV_naziv(paketObj.getString("IPTV_naziv"));
            }
            if (paketObj.has("TEL_id")) {
                paketBox.setFIKSNA(paketObj.getInt("TEL_id"));
                paketBox.setFIKSNA_naziv(paketObj.getString("TEL_naziv"));
            }
            paketBox.setCena(paketObj.getDouble("cena"));
            paketBoxesArr.add(paketBox);
        }
        return paketBoxesArr;

    }


    public void saveBox(ActionEvent actionEvent) {
        if (tNazivPaketa.getText().isEmpty() || tCenaBox.getText().isEmpty()) {
            AlertUser.warrning("GRESKA", "Naziv Box Paketa ili Cena ne mogu biti prazna");
            return;
        }

        jObj = new JSONObject();
        jObj.put("action", "save_Box_Paket");


        jObj.put("naziv", tNazivPaketa.getText());

        if (cmbDTVNaziv.getValue() != null) {
            jObj.put("DTV_id", cmbDTVNaziv.getValue().getId());
            jObj.put("DTV_naziv", cmbDTVNaziv.getValue().getNaziv());
        }
        if (cmbInternetNaziv.getValue() != null) {
            jObj.put("NET_id", cmbInternetNaziv.getValue().getId());
            jObj.put("NET_naziv", cmbInternetNaziv.getValue().getNaziv());
        }
        if (cmbNazivIPTV.getValue() != null) {
            jObj.put("IPTV_id", cmbNazivIPTV.getValue());
            jObj.put("IPTV_naziv", cmbNazivIPTV.getValue());
        }
        if (cmbNazivFiksnaTel.getValue() != null) {
            jObj.put("TEL_id", cmbNazivFiksnaTel.getValue());
            jObj.put("TEL_naziv", cmbNazivFiksnaTel.getValue());
        }
        jObj.put("cena", tCenaBox.getText());

        jObj = client.send_object(jObj);
        if (jObj.has("Error")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));
            return;
        }

        set_data();
        tNazivPaketa.clear();
        tCenaBox.clear();

    }
}
