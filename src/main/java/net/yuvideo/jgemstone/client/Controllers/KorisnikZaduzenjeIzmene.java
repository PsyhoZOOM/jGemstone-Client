package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.Controllers.Zaduzenja.MesecnaZaduzenja;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

public class KorisnikZaduzenjeIzmene implements Initializable {

  public TextField tUkupno;
  public TextField tPDV;
  public TextField tCena;
  public Button bIzmeni;
  private URL location;
  private ResourceBundle resources;
  private Client client;
  private MesecnaZaduzenja mesecnoZaduzenje;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    tCena.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        convert();
      }
    });

    tPDV.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        convert();
      }
    });

    tUkupno.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        convert();
      }
    });
  }

  private void convert() {
    if (tCena.getText().isEmpty() || tPDV.getText().isEmpty() || tUkupno.getText().isEmpty()) {
      return;
    }
    double cena = Double.valueOf(tCena.getText());
    double pdv = Double.valueOf(tPDV.getText());
    double ukupno = Double.valueOf(tUkupno.getText());

    cena = ukupno - valueToPercent.getPDVOfSum(ukupno, pdv);
    tCena.setText(String.valueOf(cena));

  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void setMesecnoZaduzenje(MesecnaZaduzenja mesecnoZaduzenje) {
    this.mesecnoZaduzenje = mesecnoZaduzenje;
    tCena.setText(String.valueOf(mesecnoZaduzenje.getCena()));
    tPDV.setText(String.valueOf(mesecnoZaduzenje.getPdv()));
    tUkupno.setText(String.valueOf(mesecnoZaduzenje.getDug()));
  }

  public void izmeniCenu(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "IzmeniKorisnikZaduzenje");
    object.put("id", mesecnoZaduzenje.getId());
    object.put("cena", Double.valueOf(tCena.getText()));
    object.put("pdv", Double.valueOf(tPDV.getText()));
    object.put("dug", Double.valueOf(tUkupno.getText()));

    object = client.send_object(object);

    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    } else {
      AlertUser.info("IZMENA USPESNA", "Izmena zaduzenja uspešna");
      Stage stage = (Stage) bIzmeni.getScene().getWindow();
      stage.close();
    }

  }
}
