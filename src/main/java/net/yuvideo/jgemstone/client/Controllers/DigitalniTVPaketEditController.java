package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
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
import net.yuvideo.jgemstone.client.classes.digitalniTVPaket;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

/**
 * Created by zoom on 2/2/17.
 */
public class DigitalniTVPaketEditController implements Initializable {

  public Button bClose;
  public TextField tNaziv;
  public TextField tPaketID;
  public TextArea tOpis;
  public Button bSnimi;
  public Client client;
  public digitalniTVPaket dtvPaket;
  public Spinner spnCena;
  public Spinner spnPDV;
  public Label lCenaPaketa;
  ResourceBundle resources;
  URL location;
  boolean edit = false;
  JSONObject jObj;
  Logger LOGGER = Logger.getLogger("DTV_EDIT");

  SpinnerValueFactory.DoubleSpinnerValueFactory dblSngFactoryCena = new SpinnerValueFactory.DoubleSpinnerValueFactory(
      0.00, Double.MAX_VALUE, 0.00);
  SpinnerValueFactory.DoubleSpinnerValueFactory dblSpnFactoryPDV = new SpinnerValueFactory.DoubleSpinnerValueFactory(
      0.00, Double.MAX_VALUE, 0.00);



  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    spnPDV.setValueFactory(dblSpnFactoryPDV);
    spnCena.setValueFactory(dblSngFactoryCena);

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

  public void close(ActionEvent actionEvent) {
    Stage stage = (Stage) bSnimi.getScene().getWindow();
    stage.close();
  }

  public void snimi(ActionEvent actionEvent) {
    if (edit) {
      editSnimi();
    } else {
      novSnimi();
    }
  }

  private void novSnimi() {
    jObj = new JSONObject();
    jObj.put("action", "add_dtv_paket");
    jObj.put("naziv", tNaziv.getText());
    jObj.put("cena", Double.valueOf(lCenaPaketa.getText()));
    jObj.put("pdv", Double.valueOf(spnPDV.getEditor().getText()));
    jObj.put("opis", tOpis.getText());
    jObj.put("idPaket", Integer.valueOf(tPaketID.getText()));

    jObj = client.send_object(jObj);

    LOGGER.info(jObj.getString("Message"));

    if (jObj.getString("Message").equals("DTV_PAKET_SAVED")) {
      AlertUser.info("DTV Paket", "DTV Paket je kreiran");
      close(null);
    } else {
      AlertUser.error("GRESKA!", jObj.getString("ERROR"));
    }


  }

  private void editSnimi() {
    jObj = new JSONObject();
    jObj.put("action", "edit_dtv_paket");
    jObj.put("id", dtvPaket.getId());
    jObj.put("naziv", tNaziv.getText());
    jObj.put("cena", Double.valueOf(lCenaPaketa.getText()));
    jObj.put("pdv", Double.valueOf(spnPDV.getEditor().getText()));
    jObj.put("opis", tOpis.getText());
    jObj.put("idPaket", Integer.valueOf(tPaketID.getText()));

    jObj = client.send_object(jObj);

    if (jObj.has("Error")) {
      AlertUser.error("GRESKA", jObj.getString("ERROR"));

    } else {
      AlertUser.info("INFORMACIJA", "Izmene snimljene");
      close(null);
    }

  }

  public void show_data() {
    tNaziv.setText(dtvPaket.getNaziv());
    spnCena.getEditor().setText(String.valueOf(dtvPaket.getCena()));
    spnPDV.getEditor().setText(String.valueOf(dtvPaket.getPdv()));
    tOpis.setText(dtvPaket.getOpis());
    tPaketID.setText(String.valueOf(dtvPaket.getPaketID()));
    setCenaPDV();


  }


  private void setCenaPDV() {
    Double cena = Double.valueOf(spnCena.getEditor().getText());
    Double pdv = Double.valueOf(spnPDV.getEditor().getText());
    Double value = valueToPercent.getPDVOfSum(cena, pdv);

    lCenaPaketa.setText(String.valueOf(cena - value));
  }


}
