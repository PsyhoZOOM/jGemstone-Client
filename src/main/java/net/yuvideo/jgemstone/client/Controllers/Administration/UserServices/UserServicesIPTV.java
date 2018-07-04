package net.yuvideo.jgemstone.client.Controllers.Administration.UserServices;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javax.swing.text.DateFormatter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.ServicesUser;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserServicesIPTV implements Initializable {

  public JFXTextField tIme;
  public JFXTextField tUserName;
  public JFXPasswordField tPass;
  public JFXButton bChangePass;
  public JFXTextField tMac;
  public JFXTextArea tComment;
  public JFXCheckBox chkAktivan;
  public JFXButton bSnimi;
  public ImageView imgOnline;
  public JFXDatePicker dtpEndDate;
  public Label lAccId;
  public Label lIP;
  public Label lVersion;
  public Label lSTBSN;
  public Label lLastLogin;
  public Label lSTBType;
  public JFXButton bIzmeniEndDate;
  private URL location;
  private ResourceBundle resources;
  private Client client;
  private JFXSnackbar snackBar;
  private ServicesUser service;
  private Image imgOn = new Image(
      ClassLoader.getSystemResource("icons/green-light.png").toString());
  private Image imgOff = new Image(ClassLoader.getSystemResource("icons/red-light.png").toString());
  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");


  @FXML
  private VBox boxMain;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    dtpEndDate.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        String date = object.format(dtf);
        return date;
      }

      @Override
      public LocalDate fromString(String string) {
        LocalDate date = LocalDate
            .parse(string, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return date;
      }
    });
  }

  public void initData() {
    JSONObject object = new JSONObject();
    System.out.println("SERVICE" + service.getIPTV_MAC());
    object.put("action", "get_iptv_mac_info");
    object.put("MAC", service.getIPTV_MAC());
    object = client.send_object(object);
    if (object.has("ERROR") || object.getString("status").equals("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));

      return;
    }

    JSONObject accData = new JSONObject();

    for (String key : object.keySet()) {
      JSONArray arr = object.getJSONArray("results");
      accData = arr.getJSONObject(0);
      System.out.println(arr);
      System.out.println(accData);
    }

    LocalDate enDate = LocalDate
        .parse(accData.getString("end_date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    dtpEndDate.setValue(enDate);
    lAccId.setText(accData.getString("account_number"));
    tMac.setText(accData.getString("stb_mac"));
    tUserName.setText(accData.getString("login"));
    tIme.setText(accData.getString("full_name"));
    lSTBSN.setText(accData.getString("stb_sn"));
    boolean online = Boolean.parseBoolean(accData.getString("online"));
    if (online) {
      imgOnline.setImage(imgOn);
    } else {
      imgOnline.setImage(imgOff);
    }
    tComment.setText(accData.getString("comment"));
    lLastLogin.setText(accData.getString("last_active"));
    if (accData.getInt("status") == 1) {
      chkAktivan.setSelected(true);
    } else {
      chkAktivan.setSelected(false);
    }


  }


  public void setClient(Client client) {
    this.client = client;
  }

  public void setService(ServicesUser service) {
    this.service = service;
  }

  public void setSnackBar(JFXSnackbar snackBar) {
    this.snackBar = snackBar;
  }

  public Node getBoxMain() {
    return boxMain;
  }

  public void changePass(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "change_iptv_pass");
    object.put("MAC", tMac.getText());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    } else {
      AlertUser
          .info("INFORMACIJA", String.format("Password za stb %s je promenjen", tMac.getText()));
    }

  }

  public void saveAll(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "save_iptv_acc_data");
    object.put("accountID", lAccId.getText());
    object.put("MAC_CHANGE", tMac.getText());
    object.put("MAC", service.getIPTV_MAC());
    object.put("fullName", tIme.getText());
    object.put("comment", tComment.getText());
    object.put("login", tUserName.getText());
    if (chkAktivan.isSelected()) {
      object.put("status", 1);
    } else {
      object.put("status", 0);
    }
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    } else {
      AlertUser.info("INFORMACIJA", String.format("Izmene za %s su snimljene", tMac.getText()));
    }
  }

  public void changeEndDate(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "changeIPTVEndDate");
    object.put("MAC", tMac.getText());
    object.put("endDate", dtpEndDate.getValue().format(dtf));
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    } else {
      AlertUser
          .info("INFORMACIJA", String.format("Datum isteka STB-a %s je promenjen", tMac.getText()));
    }
  }
}
