package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.IPTVPaketi;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

/**
 * Created by PsyhoZOOM@gmail.com on 8/15/17.
 */
public class IPTVPaketiEditController implements Initializable {

  public TextField tNaziv;
  public Button bSnimi;
  public TextArea tOpis;
  public ComboBox<IPTVPaketi> cmbIPTVPakets;
  public Client client;
  public IPTVPaketi paket;
  public int paketEditID;
  public boolean edit;
  public Spinner spnCena;
  public Spinner spnPDV;
  Stage stage;
  SpinnerValueFactory.DoubleSpinnerValueFactory doubleSpinnerValueFactoryCena = new SpinnerValueFactory.DoubleSpinnerValueFactory(
      0.00, Double.MAX_VALUE, 0.00);
  SpinnerValueFactory.DoubleSpinnerValueFactory doubleSpinnerValueFactoryPDV = new SpinnerValueFactory.DoubleSpinnerValueFactory(
      0.00, Double.MAX_VALUE, 0.00);
  private URL location;
  private ResourceBundle resources;
  @FXML
  private Label lCenaPaketa;

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
      public void changed(ObservableValue<? extends IPTVPaketi> observable, IPTVPaketi oldValue,
          IPTVPaketi newValue) {
        tNaziv.setText(newValue.getName());
        tNaziv.setDisable(true);
      }
    });

    spnPDV.setValueFactory(doubleSpinnerValueFactoryPDV);
    spnCena.setValueFactory(doubleSpinnerValueFactoryCena);

    spnCena.getEditor().textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        setCenaPDV();
      }
    });

    spnPDV.getEditor().textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        setCenaPDV();
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

      iptvPaketiArrayList.add(pakets);
    }

    ObservableList<IPTVPaketi> items = FXCollections.observableArrayList(iptvPaketiArrayList);
    cmbIPTVPakets.setItems(items);
    if (edit) {
      for (IPTVPaketi paketi : cmbIPTVPakets.getItems()) {
        System.out.println("paket.getId()=" + paket.getId() + "paketi.getId()=" + paketi.getId());
        if (paket.getIptv_id() == paketi.getIptv_id()) {
          cmbIPTVPakets.getSelectionModel().select(paketi);
          cmbIPTVPakets.setValue(paketi);
          break;
        }
      }
      spnCena.getEditor().setText(String.valueOf(paket.getCena()));
      spnPDV.getEditor().setText(String.valueOf(paket.getPdv()));
      tNaziv.setText(paket.getName());
      tOpis.setText(paket.getDescription());
    }
  }

  public void setItemsEdit() {
    setData();


  }

  public void snimiPaket(ActionEvent actionEvent) {
    //ako nije izabran ni jedan paket izlazimo iz metode
    if (cmbIPTVPakets.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.error("GRESKA", "PAKET NIJE IZABRAN");
      return;
    }

    if (edit) {
      editIPTVPaket();
    } else {
      snimiNovIPTVPaket();
    }
  }

  private void editIPTVPaket() {
    System.out
        .println("SELECTEDPAKET: " + cmbIPTVPakets.getSelectionModel().getSelectedItem().getName());
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "snimiEditIPTVPaket");
    jsonObject.put("id", paketEditID);
    jsonObject.put("external_id", cmbIPTVPakets.getValue().getExternal_id());
    jsonObject.put("iptv_id", cmbIPTVPakets.getValue().getIptv_id());
    jsonObject.put("name", cmbIPTVPakets.getValue().getName());
    jsonObject.put("cena", Double.valueOf(lCenaPaketa.getText()));
    jsonObject.put("opis", tOpis.getText());
    jsonObject.put("pdv", spnPDV.getEditor().getText());

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
      jsonObject.put("cena", Double.valueOf(lCenaPaketa.getText()));
    } catch (NumberFormatException e) {
      AlertUser.error("GRESKA", "Pogresan unos cene");
    }

    jsonObject.put("nazivPaketa", cmbIPTVPakets.getValue().getName());
    jsonObject.put("opis", tOpis.getText());
    jsonObject.put("pdv", spnPDV.getEditor().getText());

    jsonObject = client.send_object(jsonObject);

    if (jsonObject.has("Error")) {
      AlertUser.error("Greska", jsonObject.getString("Error"));
    } else {
      AlertUser
          .info("Paket kreiran", String.format("Paket %s je uspesno kreiran", tNaziv.getText()));
      stage = (Stage) bSnimi.getScene().getWindow();
      stage.close();
    }
  }

  private void setCenaPDV() {
    double cena = Double.valueOf(spnCena.getEditor().getText());
    double pdv = Double.valueOf(spnPDV.getEditor().getText());
    double value = valueToPercent.getPDVOfSum(cena, pdv);
    lCenaPaketa.setText(String.valueOf(cena - value));
  }


}
