package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import org.json.JSONObject;

/**
 * Created by PsyhoZOOM@gmail.com on 7/19/17.
 */
public class FiksnaMesecniObracuni implements Initializable {

  public Button bObracunaj;
  public Label lMessage;
  public DatePicker dtpObracunZaMesec;
  private Client client;
  private URL location;
  private ResourceBundle resources;
  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
  private DateTimeFormatter dtfNomrmal = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    lMessage.setText(null);
    dtpObracunZaMesec.setEditable(false);
    dtpObracunZaMesec.setConverter(new StringConverter<LocalDate>() {
      public String toString(LocalDate object) {
        if (object == null) {
          return null;
        } else {
          return object.format(dtf);
        }
      }

      public LocalDate fromString(String string) {
        String date = LocalDate.now().format(dtf);
        LocalDate localDate = LocalDate.parse(date);
        return null;
      }
    });

    dtpObracunZaMesec.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        check_if_obracun_postoji(dtpObracunZaMesec.getValue().format(dtf));
      }
    });

    dtpObracunZaMesec
        .setValue(LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()));


  }

  public void obracunaj(ActionEvent actionEvent) {
    JSONObject jObj = new JSONObject();
    String zaMesec = LocalDate.parse(dtpObracunZaMesec.getValue().toString()).format(dtf);
    jObj.put("action", "obracunaj_FIX_za_mesec");

    jObj.put("zaMesec", zaMesec);
    jObj = client.send_object(jObj);

    if (jObj.has("Error")) {
      AlertUser.error("GRESKA", jObj.getString("Error"));
    } else {
      AlertUser.info("OBRACUN", String.format("Obracun za mesec %s je zavrsen", zaMesec));
    }

    bObracunaj.setDisable(true);

  }

  public void check_if_obracun_postoji(String zaMesec) {
    JSONObject jObj = new JSONObject();
    jObj.put("action", "check_fix_obracun");
    jObj.put("zaMesec", zaMesec);
    jObj = client.send_object(jObj);

    if (jObj.getBoolean("exist")) {
      bObracunaj.setDisable(true);
      lMessage.setText(
          String.format("Obracun za mesec %s postoji.", dtpObracunZaMesec.getValue().format(dtf)));
    } else {
      bObracunaj.setDisable(false);
      lMessage.setText(null);
    }

  }
}
