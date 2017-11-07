package net.yuvideo.jgemstone.client.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.*;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zoom on 2/23/17.
 */
public class BoxPaketController implements Initializable {
    public TextField tNazivPaketa;
    public ComboBox<digitalniTVPaket> cmbDTVNaziv;
    public ComboBox<InternetPaketi> cmbInternetNaziv;
    public ComboBox<IPTVPaketi> cmbNazivIPTV;
    public ComboBox<FiksnaPaketi> cmbNazivFiksnaTel;
    public Button bSnimiBox;
    public TableView<BoxPaket> tblBox;
    public TableColumn cNaziv;
    public TableColumn cDTV;
    public TableColumn cNET;
    public TableColumn cIPTV;
    public TableColumn cFiksna;

    public Client client;
    public Spinner spnCena;
    public Spinner spnPDV;
    private URL location;
    private ResourceBundle resource;
    private JSONObject jObj;

    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resource = resource;

        cNaziv.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("naziv"));
        cDTV.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("DTV_naziv"));
        cNET.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("NET_naziv"));
        cIPTV.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("IPTV_naziv"));
        cFiksna.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("FIKSNA_naziv"));


        cmbNazivFiksnaTel.setConverter(new StringConverter<FiksnaPaketi>() {
            @Override
            public String toString(FiksnaPaketi object) {
                return object.getNaziv();
            }

            @Override
            public FiksnaPaketi fromString(String string) {
                FiksnaPaketi fiksnaPaketi = new FiksnaPaketi();
                fiksnaPaketi.setNaziv(string);
                return fiksnaPaketi;
            }
        });


        cmbNazivIPTV.setConverter(new StringConverter<IPTVPaketi>() {
            @Override
            public String toString(IPTVPaketi object) {
                return object.getName();
            }

            @Override
            public IPTVPaketi fromString(String string) {
                IPTVPaketi paket = new IPTVPaketi();
                paket.setName(string);
                return null;
            }
        });


        cmbDTVNaziv.setConverter(new StringConverter<digitalniTVPaket>() {
            @Override
            public String toString(digitalniTVPaket object) {
                return object.getNaziv();
            }

            @Override
            public digitalniTVPaket fromString(String string) {
                digitalniTVPaket paket = new digitalniTVPaket();
                paket.setNaziv(string);
                return paket;
            }
        });


        SpinnerValueFactory.DoubleSpinnerValueFactory spinnerValueFactoryCena = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, Double.MAX_VALUE, 1.00);
        SpinnerValueFactory.DoubleSpinnerValueFactory spinnerValueFactoryPDV = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, Double.MAX_VALUE, 1.00);
        spnPDV.setValueFactory(spinnerValueFactoryCena);
        spnCena.setValueFactory(spinnerValueFactoryPDV);

        spnPDV.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    double pdv = (double) df.parse(newValue);
                    spnPDV.getEditor().setText(String.valueOf(pdv));
                } catch (ParseException e) {
                    spnPDV.getEditor().setText(oldValue);
                    e.printStackTrace();
                }
            }
        });


    }

    public void set_data() {
        ObservableList tablebox = FXCollections.observableArrayList(get_paketBox());
        tblBox.setItems(tablebox);

        ObservableList internetpaket = FXCollections.observableArrayList(get_internet_paketi());
        cmbInternetNaziv.setItems(internetpaket);

        ObservableList dtvpaket = FXCollections.observableArrayList(get_dtv_paketi());
        cmbDTVNaziv.setItems(dtvpaket);

        ObservableList fixPaket = FXCollections.observableArrayList(get_fiksna_paketi());
        cmbNazivFiksnaTel.setItems(fixPaket);

        ObservableList iptvPaket = FXCollections.observableArrayList(get_IPTVPaketi());
        cmbNazivIPTV.setItems(iptvPaket);


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
            digitalniTVPaket.setPdv(digitalniObj.getDouble("pdv"));
            digitalniTVPaket.setPaketID(digitalniObj.getInt("id"));
            digitalniTVPaket.setOpis(digitalniObj.getString("opis"));
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
            internetPaketi.setIdleTimeout(internetObj.getString("idleTimeout"));
            internetPaketi.setCena(internetObj.getDouble("cena"));
            internetPaketi.setPdv(internetObj.getDouble("pdv"));
            internetPaketi.setOpis(internetObj.getString("opis"));
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
            if (paketObj.has("FIX_id")) {
                paketBox.setFIKSNA(paketObj.getInt("FIX_id"));
                paketBox.setFIKSNA_naziv(paketObj.getString("FIX_naziv"));
            }
            paketBox.setCena(paketObj.getDouble("cena"));
            paketBox.setPdv(paketObj.getDouble("pdv"));
            paketBoxesArr.add(paketBox);
        }
        return paketBoxesArr;

    }

    private ArrayList<IPTVPaketi> get_IPTVPaketi() {
        jObj = new JSONObject();
        jObj.put("action", "get_paket_IPTV");
        jObj = client.send_object(jObj);

        IPTVPaketi iptvPaketi;
        ArrayList<IPTVPaketi> iptvPaketiArrayList = new ArrayList<>();
        JSONObject iptvJsonObject;

        for (int i = 0; i < jObj.length(); i++) {
            iptvJsonObject = (JSONObject) jObj.get(String.valueOf(i));
            iptvPaketi = new IPTVPaketi();
            iptvPaketi.setId(iptvJsonObject.getInt("id"));
            iptvPaketi.setName(iptvJsonObject.getString("name"));
            iptvPaketi.setExternal_id(iptvJsonObject.getString("external_id"));
            iptvPaketi.setCena(iptvJsonObject.getDouble("cena"));
            iptvPaketi.setDescription(iptvJsonObject.getString("opis"));
            iptvPaketi.setPdv(iptvJsonObject.getDouble("pdv"));
            iptvPaketiArrayList.add(iptvPaketi);
        }

        return iptvPaketiArrayList;
    }

    private ArrayList<FiksnaPaketi> get_fiksna_paketi() {
        jObj = new JSONObject();
        jObj.put("action", "show_fixTel_paketi");

        jObj = client.send_object(jObj);

        FiksnaPaketi fiksnaPaketi;
        ArrayList<FiksnaPaketi> fiksnaPaketiArrayList = new ArrayList<>();
        JSONObject fiksnaObj;

        for (int i = 0; i < jObj.length(); i++) {
            fiksnaPaketi = new FiksnaPaketi();
            fiksnaObj = (JSONObject) jObj.get(String.valueOf(i));
            fiksnaPaketi.setId(fiksnaObj.getInt("id"));
            fiksnaPaketi.setNaziv(fiksnaObj.getString("naziv"));
            fiksnaPaketi.setPretplata(fiksnaObj.getDouble("pretplata"));
            fiksnaPaketi.setPdv(fiksnaObj.getDouble("pdv"));
            fiksnaPaketiArrayList.add(fiksnaPaketi);
        }

        return fiksnaPaketiArrayList;


    }


    public void saveBox(ActionEvent actionEvent) {
        if (tNazivPaketa.getText().isEmpty() || spnCena.getEditor().getText().isEmpty()) {
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
            jObj.put("IPTV_id", cmbNazivIPTV.getValue().getId());
            jObj.put("IPTV_naziv", cmbNazivIPTV.getValue().getName());
            cmbNazivIPTV.getValue().getId();
            cmbNazivIPTV.getValue().getName();
        }
        if (cmbNazivFiksnaTel.getValue() != null) {
            jObj.put("FIX_id", cmbNazivFiksnaTel.getValue().getId());
            jObj.put("FIX_naziv", cmbNazivFiksnaTel.getValue().getNaziv());
        }
        jObj.put("cena", spnCena.getEditor().getText());
        jObj.put("pdv", spnPDV.getEditor().getText());


        jObj = client.send_object(jObj);
        if (jObj.has("Error")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));
            return;
        }

        set_data();
        tNazivPaketa.clear();
        spnCena.getEditor().setText("0.00");
        spnPDV.getEditor().setText("20.0");

    }
}
