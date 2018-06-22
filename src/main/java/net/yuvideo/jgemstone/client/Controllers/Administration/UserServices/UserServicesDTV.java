package net.yuvideo.jgemstone.client.Controllers.Administration.UserServices;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.ServicesUser;
import org.json.JSONObject;

public class UserServicesDTV implements Initializable {

  public Label lNazivPaketa;
  public Label lPaketType;
  public JFXDatePicker dtpEndDate;
  public JFXButton bIzmeniEndDate;
  public VBox vBoxMain;
  public JFXTextField tDTVCard;
  private URL location;
  private ResourceBundle resources;
  private Client client;
  private ServicesUser service;

  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private int idService;
  private String DTVCardCurrent;
  private JFXSnackbar snackBar;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    dtpEndDate.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        return object.format(dtf);
      }

      @Override
      public LocalDate fromString(String string) {
        LocalDate localDate = LocalDate.parse(string, dtf);
        return localDate;
      }
    });
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void initData() {
    JSONObject object = new JSONObject();
    object.put("action", "getServiceDetail");
    object.put("serviceID", this.service.getId());
    object = client.send_object(object);
    lNazivPaketa.setText(object.getString("nazivPaketa"));
    lPaketType.setText(object.getString("paketType"));
    tDTVCard.setText(object.getString("identification"));
    this.DTVCardCurrent = object.getString("identification");
    this.idService = object.getInt("id");
    LocalDate endDate = LocalDate
        .parse(object.getString("endDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    dtpEndDate.setValue(endDate);

  }

  public VBox getBoxMain() {
    return vBoxMain;
  }

  public void setService(ServicesUser service) {
    this.service = service;
  }

  public void changeDTV(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "changeServiceDTVCard");
    object.put("serviceID", this.idService);
    object.put("DTVCardCurrent", this.DTVCardCurrent);
    object.put("DTVCardNew", tDTVCard.getText());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    snackBar
        .show(String.format("DTV kartica %s je izmenjena u %s", DTVCardCurrent, tDTVCard.getText()),
            3000);
    this.DTVCardCurrent = tDTVCard.getText();

  }

  public void changeEndDate(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "changeServiceDTVEndDate");
    object.put("serviceID", this.idService);
    object.put("endDate", dtpEndDate.getValue());
    object.put("DTVCard", Integer.valueOf(this.DTVCardCurrent));
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    snackBar.show(String.format("Datum isteka kartice %s je promenjen", DTVCardCurrent), 3000);
  }

  public void setSnackBar(JFXSnackbar snackBar) {
    this.snackBar = snackBar;
  }
}
