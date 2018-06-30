package net.yuvideo.jgemstone.client.Controllers.Administration.UserServices;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.ServicesUser;
import net.yuvideo.jgemstone.client.classes.md5_digiest;
import org.json.JSONObject;

public class UserServicesNET implements Initializable {

  public VBox vBoxMain;
  public JFXTextField tUserName;
  public JFXPasswordField tPass;
  public JFXDatePicker dtpEndDate;
  public JFXTextField tUpDown;
  public JFXTextField tIP;
  public JFXTextField tIpPool;
  public JFXTextField tFilterID;
  public JFXTextArea tKomentar;
  public JFXButton changeComment;
  public Label lNazivPaketa;
  public Label lVrstaPaketa;
  public JFXCheckBox chkReject;
  public Label lServiceEndDate;
  public JFXTextField tMaxConn;
  public VBox vBoxStatus;
  public JFXButton bRefres;
  private URL location;
  private ResourceBundle resources;
  private Client client;
  private ServicesUser service;
  private JFXSnackbar snackBar;

  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private DateTimeFormatter dtfRad = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
    vBoxMain.setVisible(true);

    dtpEndDate.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {

        if (object == null) {
          return null;
        }
        return object.format(dtf);
      }

      @Override
      public LocalDate fromString(String string) {
        LocalDate date = LocalDate.parse(string, dtfRad);
        return date;
      }
    });

  }


  public void setClient(Client client) {
    this.client = client;
  }

  public void initData() {
    JSONObject object = new JSONObject();
    object.put("action", "getRadiusServiceData");
    object.put("username", service.getUserName());
    object.put("serviceID", service.getId());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    lNazivPaketa.setText(object.getString("nazivPaketa"));
    lVrstaPaketa.setText(object.getString("paketType"));
    tUserName.setText(service.getUserName());

    if (object.has("WISPR-Session-Terminate-Time")) {
      String WISPRTermTime = object.getString("WISPR-Session-Terminate-Time");
      dtpEndDate.setValue(LocalDate.parse(WISPRTermTime, dtfRad));
    } else {
      dtpEndDate.setValue(LocalDate.parse(object.getString("endDateService"), dtf));
    }
    if (object.has("Simultaneous-Use")) {
      tMaxConn.setText(object.getString("Simultaneous-Use"));
    }
    lServiceEndDate.setText(object.getString("endDateService"));
    if (object.has("Mikrotik-Rate-Limit")) {
      tUpDown.setText(object.getString("Mikrotik-Rate-Limit"));
    }
    if (object.has("Framed-IP-Address")) {
      tIP.setText(object.getString("Framed-IP-Address"));
    }
    if (object.has("Framed-Pool")) {
      tIpPool.setText(object.getString("Framed-Pool"));
    }
    if (object.has("Filter-Id")) {
      tFilterID.setText(object.getString("Filter-Id"));
    }
    if (object.has("Auth-Type")) {
      if (object.getString("Auth-Type").equals("Reject")) {
        chkReject.setSelected(true);
      } else {
        chkReject.setSelected(false);
      }
    }
    tKomentar.setText(service.getKomentar());


  }


  public void setService(ServicesUser service) {
    this.service = service;
  }

  public void setSnackBar(JFXSnackbar snackBar) {
    this.snackBar = snackBar;
  }

  public Node getBoxMain() {
    return this.vBoxMain;
  }

  public void changeRadiusData(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "changeRadiusData");
    object.put("username", tUserName.getText());
    object.put("endDate", dtpEndDate.getValue().toString());
    object.put("IPAddress", tIP.getText());
    object.put("pool", tIpPool.getText());
    object.put("filterID", tFilterID.getText());
    object.put("komentar", tKomentar.getText());
    object.put("serviceID", service.getId());
    object.put("Mikrotik-Rate-Limit", tUpDown.getText());
    object.put("Simultaneous-Use", tMaxConn.getText());
    object.put("Reject", chkReject.isSelected());

    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    snackBar.show("Radius izmene snimljene", 3000);
    initData();

  }

  public void izmeniPass(ActionEvent actionEvent) {
    tPass.setText(tPass.getText().trim());
    if (tPass.getText().isEmpty()) {
      AlertUser.info("Upozorenje", "Upišite password!");
      return;
    }
    JSONObject object = new JSONObject();
    md5_digiest md5 = new md5_digiest(tPass.getText());

    object.put("action", "changeRadiusPass");
    object.put("username", service.getUserName());
    object.put("pass", md5.get_hash());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    } else {
      snackBar.show(
          String.format("Password za korisnicko ime: %s je promenjeno!", service.getUserName()),
          3000);
    }
  }

  public void showStatus(ActionEvent actionEvent) {
  }
}
