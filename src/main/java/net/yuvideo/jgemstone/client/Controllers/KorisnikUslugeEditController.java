package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.ServicesUser;
import org.json.JSONObject;

public class KorisnikUslugeEditController implements Initializable {

  public Label lNazivUsluge;
  public TextField tIdentification;
  public DatePicker dtpDatumIsteka;
  public TextField tPopust;
  public CheckBox cmbObracun;
  DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
  private URL location;
  private ResourceBundle resource;
  private ServicesUser servicesUser;
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resource = resources;

    dtpDatumIsteka.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        if (object == null) {
          return "";
        }
        return object.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
      }

      @Override
      public LocalDate fromString(String string) {
        LocalDate date = LocalDate.parse(df.format(string));
        return date;
      }
    });
  }


  public void setData() {
    System.out
        .println("ID: " + servicesUser.getId() + " PAKET TYPE:" + servicesUser.getPaketType());
    lNazivUsluge.setText(servicesUser.getNazivPaketa());
    if (servicesUser.getPaketType().equals("IPTV") || servicesUser.getPaketType()
        .equals("LINKED_IPTV")) {
      tIdentification.setText(servicesUser.getIPTV_MAC());
    } else if (servicesUser.getPaketType().equals("DTV") || servicesUser.getPaketType()
        .equals("LINKED_DTV")) {
      tIdentification.setText(servicesUser.getIdDTVCard());
    } else {
      tIdentification.setEditable(false);
    }
    tPopust.setText(String.valueOf(servicesUser.getPopust()));
    if (servicesUser.getObracun()) {
      cmbObracun.setSelected(true);
    } else {
      cmbObracun.setSelected(false);
    }
    System.out.println(servicesUser.getEndDate());
    if (servicesUser.getEndDate() != null) {
      LocalDate date = LocalDate
          .parse(servicesUser.getEndDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
      dtpDatumIsteka.setValue(date);
    } else {
      dtpDatumIsteka.setEditable(false);
    }

    if (servicesUser.getPaketType().equals("BOX")) {
      tIdentification.setEditable(false);
      dtpDatumIsteka.setEditable(false);
      dtpDatumIsteka.getEditor().setEditable(false);
      dtpDatumIsteka.getEditor().setDisable(true);
      dtpDatumIsteka.setDisable(true);
      dtpDatumIsteka.hide();
    } else {
      if (servicesUser.getPaketType().contains("LINKED")) {
        dtpDatumIsteka.setEditable(false);
        dtpDatumIsteka.setDisable(true);
        dtpDatumIsteka.getEditor().setEditable(false);
        dtpDatumIsteka.getEditor().setDisable(true);
        dtpDatumIsteka.hide();
        tPopust.setEditable(false);
        tPopust.setDisable(true);
        cmbObracun.setDisable(true);
      }
    }


  }

  public void saveServis(ActionEvent actionEvent) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "updateService");
    if (servicesUser.getPaketType().contains("DTV")) {
      jsonObject.put("idDTVCard", tIdentification.getText());
      jsonObject.put("old_idDTVCard", servicesUser.getIdDTVCard());
    } else if (servicesUser.getPaketType().contains("IPTV")) {
      jsonObject.put("IPTV_MAC", tIdentification.getText());
      jsonObject.put("old_IPTV_MAC", servicesUser.getIPTV_MAC());
    } else {
      return;
    }

    jsonObject.put("id", servicesUser.getId());
    jsonObject.put("paketType", servicesUser.getPaketType());

    jsonObject = client.send_object(jsonObject);
    if (jsonObject.has("ERROR")) {
      AlertUser.error("GRESKA", jsonObject.getString("ERROR"));
    }

  }


  public ServicesUser getServicesUser() {
    return servicesUser;
  }

  public void setServicesUser(ServicesUser servicesUser) {
    this.servicesUser = servicesUser;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
