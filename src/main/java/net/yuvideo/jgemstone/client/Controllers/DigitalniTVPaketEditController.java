package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.DigitalniTVPaket;
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
  public CheckBox chkDodatak;
  public CheckBox chkDodatnaKartica;
  private Client client;
  public DigitalniTVPaket dtvPaket;
  public Spinner spnCena;
  public Spinner spnPDV;
  public Label lCenaPaketa;
  ResourceBundle resources;
  URL location;
  boolean edit = false;
  JSONObject jObj;
  Logger LOGGER = Logger.getLogger("DTV_EDIT");
  DecimalFormat df = new DecimalFormat("###,###,###,##0.00");
  private double _CENA;

  SpinnerValueFactory.DoubleSpinnerValueFactory dblSngFactoryCena = new SpinnerValueFactory.DoubleSpinnerValueFactory(
      0.00, Double.MAX_VALUE, 0.00);
  SpinnerValueFactory.DoubleSpinnerValueFactory dblSpnFactoryPDV = new SpinnerValueFactory.DoubleSpinnerValueFactory(
      0.00, Double.MAX_VALUE, 0.00);



  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    tPaketID.setText("0");
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
    jObj.put("cena", _CENA);
    jObj.put("pdv", Double.valueOf(spnPDV.getEditor().getText()));
    jObj.put("opis", tOpis.getText());
    jObj.put("idPaket", Integer.valueOf(tPaketID.getText()));
    jObj.put("dodatak", chkDodatak.isSelected());
    jObj.put("dodatnaKartica", chkDodatnaKartica.isSelected());

    jObj = client.send_object(jObj);

    if (jObj.has("ERROR")) {
      AlertUser.error("GRESKA!", jObj.getString("ERROR"));
      return;

    }
      AlertUser.info("DTV Paket", "DTV Paket je kreiran");
      close(null);


  }

  private void editSnimi() {
    jObj = new JSONObject();
    jObj.put("action", "edit_dtv_paket");
    jObj.put("id", dtvPaket.getId());
    jObj.put("naziv", tNaziv.getText());
    jObj.put("cena", _CENA);
    jObj.put("pdv", Double.valueOf(spnPDV.getEditor().getText()));
    jObj.put("opis", tOpis.getText());
    jObj.put("idPaket", Integer.valueOf(tPaketID.getText()));
    jObj.put("dodatak", chkDodatak.isSelected());
    jObj.put("dodatnaKartica", chkDodatnaKartica.isSelected());

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
    spnCena.getEditor().setText(String.valueOf(
        dtvPaket.getCena() + valueToPercent.getPDVOfValue(dtvPaket.getCena(), dtvPaket.getPdv())));
    spnPDV.getEditor().setText(String.valueOf(dtvPaket.getPdv()));
    tOpis.setText(dtvPaket.getOpis());
    tPaketID.setText(String.valueOf(dtvPaket.getPaketID()));
    chkDodatak.setSelected(dtvPaket.isDodatak());
    chkDodatnaKartica.setSelected(dtvPaket.isDodatnaKartica());

    setCenaPDV();


  }


  private void setCenaPDV() {
    double cena = Double.valueOf(spnCena.getEditor().getText());
    double pdv = Double.valueOf(spnPDV.getEditor().getText());
    double value = valueToPercent.getPDVOfSum(cena, pdv);
    _CENA = cena - value;

    lCenaPaketa.setText(df.format(_CENA));
  }


  public void setClient(Client client) {
    this.client = client;
  }
}
