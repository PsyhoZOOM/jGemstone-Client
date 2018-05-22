package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.InternetPaketi;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

/**
 * Created by zoom on 1/31/17.
 */
public class InternetPaketEditController implements Initializable {

  public TextField tNaziv;
  public TextField tBrzina;
  public TextArea tOpis;
  public Button bSnimi;
  public Client client;
  public boolean edit = false;
  public int idRad;
  public int idPaket;
  public InternetPaketi paket;
  public Spinner spnCena;
  public Spinner spnPDV;
  public Label lCenaNet;
  public Spinner spnIdleTimeout;
  private ResourceBundle resource;
  private URL location;
  private JSONObject jObj;
  private SpinnerValueFactory.DoubleSpinnerValueFactory doubleSpinnerValueFactoryCena = new SpinnerValueFactory.DoubleSpinnerValueFactory(
      0.00, Double.MAX_VALUE, 0.00);
  private SpinnerValueFactory.DoubleSpinnerValueFactory doubleSpinnerValueFactoryPDV = new SpinnerValueFactory.DoubleSpinnerValueFactory(
      0.00, Double.MAX_VALUE, 0.00);

  private DecimalFormat df = new DecimalFormat("###,###,###,##0.00");
  private double _CENA;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.resource = resources;
    this.location = location;

    spnPDV.setValueFactory(doubleSpinnerValueFactoryPDV);
    spnCena.setValueFactory(doubleSpinnerValueFactoryCena);

    spnCena.getEditor().textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        setCenaSaPDV();
      }
    });

    spnPDV.getEditor().textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        setCenaSaPDV();
      }
    });
    spnIdleTimeout.getEditor().setText("600");

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
    jObj.put("cena", _CENA);
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
    jObj.put("cena", _CENA);
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
    Stage stage = (Stage) bSnimi.getScene().getWindow();
    stage.close();
  }

  public void show_data() {
    tNaziv.setEditable(false);
    tNaziv.setText(paket.getNaziv());
    tBrzina.setText(paket.getBrzina());
    spnCena.getEditor().setText(String
        .valueOf(paket.getCena() + valueToPercent.getPDVOfValue(paket.getCena(), paket.getPdv())));
    spnIdleTimeout.getEditor().setText(paket.getIdleTimeout());
    spnPDV.getEditor().setText(String.valueOf(paket.getPdv()));
    tOpis.setText(paket.getOpis());
    setCenaSaPDV();

  }

  private void setCenaSaPDV() {
    Double cena = Double.valueOf(spnCena.getEditor().getText());
    Double pdv = Double.valueOf(spnPDV.getEditor().getText());
    _CENA = cena - valueToPercent.getPDVOfSum(cena, pdv);
    lCenaNet.setText(df.format(_CENA));

  }
}
