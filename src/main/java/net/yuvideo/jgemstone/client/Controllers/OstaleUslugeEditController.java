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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.OstaleUsluge;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

/**
 * Created by PsyhoZOOM@gmail.com on 11/26/17.
 */
public class OstaleUslugeEditController implements Initializable {

  public TextField tNaziv;
  public TextField tCena;
  public TextField tPDV;
  public TextArea tKomentar;
  public Button bSnimi;
  public boolean edit;
  private Client client;
  public OstaleUsluge ostaleUsluge;
  public Label lCenaPaketa;
  private URL localtion;
  private ResourceBundle resources;
  double _CENA;
  private DecimalFormat df = new DecimalFormat("###,###,###,##0.00");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.localtion = location;
    this.resources = resources;

    tPDV.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        setCenaPDV();
      }
    });

    tCena.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        setCenaPDV();
      }
    });
  }

  public void setData() {
    this.tNaziv.setText(ostaleUsluge.getNazivUsluge());
    this.tCena.setText(String.valueOf(ostaleUsluge.getCena() + valueToPercent
        .getPDVOfValue(ostaleUsluge.getCena(), ostaleUsluge.getPdv())));
    this.tPDV.setText(String.valueOf(ostaleUsluge.getPdv()));
    this.tKomentar.setText(ostaleUsluge.getKomentar());


  }

  private void setCenaPDV() {
    Double cena = Double.valueOf(tCena.getText());
    Double pdv = Double.valueOf(tPDV.getText());
    _CENA = cena - valueToPercent.getPDVOfSum(cena, pdv);
    lCenaPaketa.setText(df.format(_CENA));
  }

  public void saveUslugu(ActionEvent actionEvent) {
    JSONObject jsonObject = new JSONObject();
    if (edit) {
      jsonObject.put("action", "updateOstaleUslugu");
    } else {
      jsonObject.put("action", "snimiOstaleUslugu");
    }
    jsonObject.put("naziv", tNaziv.getText());
    jsonObject.put("cena", _CENA);
    jsonObject.put("pdv", tPDV.getText());
    jsonObject.put("opis", tKomentar.getText());
    if (ostaleUsluge != null) {
      jsonObject.put("id", ostaleUsluge.getId());
    }
    jsonObject = client.send_object(jsonObject);
    if (jsonObject.has("ERROR")) {
      AlertUser.error("GRESKA", jsonObject.getString("ERROR"));
    } else {
      AlertUser.info("USLUGA SNIMLJENA", String.format("Usluga %s je izmenjena", tNaziv.getText()));
    }

    Stage stage = (Stage) bSnimi.getScene().getWindow();
    stage.close();


  }

  public void setClient(Client client) {
    this.client = client;
  }
}
