package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Printing.PrintRacun;
import org.json.JSONObject;

public class MesecniObracun implements Initializable {

  public JFXDatePicker dtpZaMesec;
  private URL location;
  private ResourceBundle resources;
  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
    dtpZaMesec.setValue(LocalDate.now().minusMonths(1));

    dtpZaMesec.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        return object.format(dtf);
      }

      @Override
      public LocalDate fromString(String string) {
        LocalDate date = LocalDate.parse(string, dtf);
        return date;
      }
    });
  }


  public void obracunaj(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "obracunZaMesec");
    object.put("zaMesec", dtpZaMesec.getValue().format(dtf));
    object = client.send_object(object);

    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    } else {
      AlertUser.info("INFO", String
          .format("Obračun završen. Ukupno za mesec %s: %f", dtpZaMesec.getValue().format(dtf),
              object.getDouble("ukupno")));
    }

  }


  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
