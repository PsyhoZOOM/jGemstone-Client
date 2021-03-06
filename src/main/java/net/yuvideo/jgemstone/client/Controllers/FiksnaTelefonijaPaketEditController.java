/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.FiksnaPaketi;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

/**
 * FXML Controller class
 *
 * @author PsyhoZOOM
 */
public class FiksnaTelefonijaPaketEditController implements Initializable {

  private Client client;
  public FiksnaPaketi paketEdit;
  public boolean edit;
  SpinnerValueFactory.DoubleSpinnerValueFactory spnPDVFac
      = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0);
  SpinnerValueFactory.DoubleSpinnerValueFactory spnCenaFac
      = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0);
  SpinnerValueFactory.IntegerSpinnerValueFactory spnBesMinFac = new IntegerSpinnerValueFactory(0,
      Integer.MAX_VALUE, 0);
  private URL url;
  private ResourceBundle resourceBoundle;
  @FXML
  private Spinner spnPretplata;
  @FXML
  private Spinner spnPDV;
  @FXML
  private Spinner spnBesplatniMinuti;
  @FXML
  private TextField tNaziv;
  @FXML
  private Label lCenaPaketa;
  private JSONObject jObj;
  private DecimalFormat df = new DecimalFormat("###,###,###,##0.00");
  private double _CENA;

  /**
   * Initializes the controller class.
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    this.url = url;
    this.resourceBoundle = rb;

    spnPretplata.setValueFactory(spnCenaFac);
    spnPDV.setValueFactory(spnPDVFac);
    spnBesplatniMinuti.setValueFactory(spnBesMinFac);

    spnPretplata.getEditor().textProperty().addListener(new ChangeListener<String>() {
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

  }

  @FXML
  private void savePaket() {
    if (edit) {
      snimiEdit();
    } else {
      snimiNov();
    }
  }

  public void snimiNov() {
    jObj = new JSONObject();
    jObj.put("action", "add_fixTel_paket");
    jObj.put("naziv", tNaziv.getText());
    jObj.put("pretplata", _CENA);
    jObj.put("pdv", Double.valueOf(spnPDV.getEditor().getText()));
    jObj.put("besplatniMinutiFiksna", Integer.valueOf(spnBesplatniMinuti.getEditor().getText()));
    client.send_object(jObj);
    if (jObj.has("Error")) {
      AlertUser.error("GRESKA", jObj.getString("Error"));
    } else {
      AlertUser.info("PAKET SNIMLJEN", String.format("Paket %s je snimljen", tNaziv.getText()));
    }
    Stage stage = (Stage) lCenaPaketa.getScene().getWindow();
    stage.close();

  }

  public void snimiEdit() {
    jObj = new JSONObject();
    jObj.put("action", "edit_fixTel_paket");

    jObj.put("naziv", tNaziv.getText());
    jObj.put("pdv", Double.valueOf(spnPDV.getEditor().getText()));
    jObj.put("pretplata", _CENA);
    jObj.put("besplatniMinutiFiksna", Integer.valueOf(spnBesplatniMinuti.getEditor().getText()));
    jObj.put("id", paketEdit.getId());

    jObj = client.send_object(jObj);

    if (jObj.has("Error")) {
      AlertUser.error("GRESKA", jObj.getString("Error"));
    } else {
      AlertUser.info("INFO", String.format("Paket %s je izmenjen", tNaziv.getText()));
    }

    Stage stage = (Stage) lCenaPaketa.getScene().getWindow();
    stage.close();

  }


  private void setCenaSaPDV() {
    Double cena = Double.valueOf(spnPretplata.getEditor().getText());
    Double pdv = Double.valueOf(spnPDV.getEditor().getText());
    _CENA = cena - valueToPercent.getPDVOfSum(cena, pdv);
    lCenaPaketa.setText(df.format(_CENA));
  }

  void set_data() {
    tNaziv.setText(paketEdit.getNaziv());
    spnBesplatniMinuti.getEditor().setText(String.valueOf(paketEdit.getBesplatniMinutiFiksna()));
    spnPDV.getEditor().setText(String.valueOf(paketEdit.getPdv()));
    spnPretplata.getEditor().setText(String.valueOf(
        paketEdit.getPretplata() + valueToPercent
            .getPDVOfValue(paketEdit.getPretplata(), paketEdit.getPdv())));
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
