/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.BoxPaket;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.FiksnaPaketi;
import net.yuvideo.jgemstone.client.classes.IPTVPaketi;
import net.yuvideo.jgemstone.client.classes.InternetPaketi;
import net.yuvideo.jgemstone.client.classes.DigitalniTVPaket;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author PsyhoZOOM
 */
public class BoxPaketEditController implements Initializable {

  private Client client;
  public boolean editPaket;
  public BoxPaket boxPaket;
  @FXML
  TextField tNazivPaketa;
  @FXML
  ComboBox<DigitalniTVPaket> cmbDTV;
  @FXML
  ComboBox<InternetPaketi> cmbInternet;
  @FXML
  ComboBox<IPTVPaketi> cmbIPTV;
  @FXML
  ComboBox<FiksnaPaketi> cmbFiks;
  @FXML
  Label lCenaPaketa;
  @FXML
  Spinner spnPDV;
  @FXML
  Spinner spnCena;
  @FXML
  Button bSnimi;
  SpinnerValueFactory.DoubleSpinnerValueFactory spinnerValueFactoryCena =
      new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0.00);
  SpinnerValueFactory.DoubleSpinnerValueFactory spinnerValueFactoryPDV =
      new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0.00);
  private URL url;
  private ResourceBundle rb;
  private JSONObject jObj;
  DecimalFormat df = new DecimalFormat("###,###,###,##0.00");
  private double _CENA;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    this.url = url;
    this.rb = rb;

    cmbDTV.setConverter(new StringConverter<DigitalniTVPaket>() {
      @Override
      public String toString(DigitalniTVPaket object) {
        return object.getNaziv();
      }

      @Override
      public DigitalniTVPaket fromString(String string) {
        DigitalniTVPaket pak = new DigitalniTVPaket();
        pak.setNaziv(string);
        return pak;
      }
    });

    cmbFiks.setConverter(new StringConverter<FiksnaPaketi>() {
      @Override
      public String toString(FiksnaPaketi object) {
        return object.getNaziv();
      }

      @Override
      public FiksnaPaketi fromString(String string) {
        FiksnaPaketi pak = new FiksnaPaketi();
        pak.setNaziv(string);
        return pak;
      }
    });

    cmbIPTV.setConverter(new StringConverter<IPTVPaketi>() {
      @Override
      public String toString(IPTVPaketi object) {
        return object.getName();
      }

      @Override
      public IPTVPaketi fromString(String string) {
        IPTVPaketi pak = new IPTVPaketi();
        pak.setName(string);
        return pak;
      }
    });

    cmbInternet.setConverter(new StringConverter<InternetPaketi>() {
      @Override
      public String toString(InternetPaketi object) {
        return object.getNaziv();
      }

      @Override
      public InternetPaketi fromString(String string) {
        InternetPaketi pak = new InternetPaketi();
        pak.setNaziv(string);
        return pak;
      }
    });

    spnPDV.setValueFactory(spinnerValueFactoryCena);
    spnCena.setValueFactory(spinnerValueFactoryPDV);

    spnPDV.getEditor().textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        setCenaPDV();
      }
    });

    spnCena.getEditor().textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        setCenaPDV();
      }
    });

  }


  public void set_data() {

    ObservableList internetpaket = FXCollections.observableArrayList(get_internet_paketi());
    cmbInternet.setItems(internetpaket);

    ObservableList dtvpaket = FXCollections.observableArrayList(get_dtv_paketi());
    cmbDTV.setItems(dtvpaket);

    ObservableList fixPaket = FXCollections.observableArrayList(get_fiksna_paketi());
    cmbFiks.setItems(fixPaket);

    ObservableList iptvPaket = FXCollections.observableArrayList(get_IPTVPaketi());
    cmbIPTV.setItems(iptvPaket);

    if (editPaket) {
      tNazivPaketa.setText(boxPaket.getNaziv());
      spnCena.getEditor().setText(String.valueOf(boxPaket.getCena() + valueToPercent
          .getPDVOfValue(boxPaket.getCena(), boxPaket.getPdv())));
      spnPDV.getEditor().setText(String.valueOf(boxPaket.getPdv()));
      for (InternetPaketi iPaket : cmbInternet.getItems()) {
        if (boxPaket.getNET() == iPaket.getId()) {
          cmbInternet.getSelectionModel().select(iPaket);
          cmbInternet.setValue(iPaket);
        }
      }

      for (DigitalniTVPaket dPaket : cmbDTV.getItems()) {
        if (boxPaket.getDTV() == dPaket.getId()) {
          cmbDTV.getSelectionModel().select(dPaket);
          cmbDTV.setValue(dPaket);
        }
      }

      for (FiksnaPaketi fPaket : cmbFiks.getItems()) {
        if (boxPaket.getFIKSNA() == fPaket.getId()) {
          cmbFiks.getSelectionModel().select(fPaket);
          cmbFiks.setValue(fPaket);
        }
      }

      for (IPTVPaketi itvPaket : cmbIPTV.getItems()) {
        if (boxPaket.getIPTV() == itvPaket.getId()) {
          cmbIPTV.getSelectionModel().select(itvPaket);
          cmbIPTV.setValue(itvPaket);
        }
      }

    }


  }


  private ArrayList<DigitalniTVPaket> get_dtv_paketi() {
    jObj = new JSONObject();
    jObj.put("action", "getDigitalTVPaketi");

    jObj = client.send_object(jObj);

    DigitalniTVPaket digitalniTVPaket;
    ArrayList<DigitalniTVPaket> digitalniTVPaketArrayList = new ArrayList<>();
    JSONObject digitalniObj;

    for (int i = 0; i < jObj.length(); i++) {
      digitalniTVPaket = new DigitalniTVPaket();
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

  private void setCenaPDV() {
    double cena = Double.valueOf(spnCena.getEditor().getText());
    double pdv = Double.valueOf(spnPDV.getEditor().getText());
    double value = valueToPercent.getPDVOfSum(cena, pdv);
    _CENA = cena - value;
    lCenaPaketa.setText(df.format(_CENA));
  }


  @FXML
  public void saveBox(ActionEvent actionEvent) {

    jObj = new JSONObject();
    if (editPaket) {
      jObj.put("action", "update_Box_Paket");
      jObj.put("boxID", boxPaket.getId());
    } else {
      jObj.put("action", "save_Box_Paket");
    }
    jObj.put("naziv", tNazivPaketa.getText());

    if (cmbDTV.getValue() != null) {
      jObj.put("DTV_id", cmbDTV.getValue().getId());
      jObj.put("DTV_naziv", cmbDTV.getValue().getNaziv());
    }
    if (cmbInternet.getValue() != null) {
      jObj.put("NET_id", cmbInternet.getValue().getId());
      jObj.put("NET_naziv", cmbInternet.getValue().getNaziv());
    }
    if (cmbIPTV.getValue() != null) {
      jObj.put("IPTV_id", cmbIPTV.getValue().getId());
      jObj.put("IPTV_naziv", cmbIPTV.getValue().getName());
      cmbIPTV.getValue().getId();
      cmbIPTV.getValue().getName();
    }
    if (cmbFiks.getValue() != null) {
      jObj.put("FIX_id", cmbFiks.getValue().getId());
      jObj.put("FIX_naziv", cmbFiks.getValue().getNaziv());
    }
    jObj.put("cena", _CENA);
    jObj.put("pdv", Double.valueOf(spnPDV.getEditor().getText()));

    jObj = client.send_object(jObj);
    if (jObj.has("ERROR")) {
      AlertUser.error("GRESKA", jObj.getString("ERROR"));
      return;
    }

    Stage window = (Stage) bSnimi.getScene().getWindow();
    window.close();

  }

  public void setClient(Client client) {
    this.client = client;
  }
}
