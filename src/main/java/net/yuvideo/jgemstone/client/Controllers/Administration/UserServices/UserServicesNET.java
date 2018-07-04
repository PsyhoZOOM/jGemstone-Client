package net.yuvideo.jgemstone.client.Controllers.Administration.UserServices;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.Controllers.Administration.Search.UserOnlineSearch;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.ServicesUser;
import net.yuvideo.jgemstone.client.classes.UsersOnline;
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
  public JFXButton bIzmeniPass;
  public JFXButton bIzmeni;
  public ImageView imgOnline;
  public Label lNAS;
  public Label lInterface;
  public Label lIP;
  public Label lMAC;
  public Label lCalledID;
  public Label lLinkUP;
  public TextField tSpeed;
  public JFXButton bChangeSpeed;
  public JFXButton bPotrosnja;
  public JFXButton bPING;
  public JFXButton bMonitor;
  public JFXComboBox<UsersOnline> cmbUsers;
  private URL location;
  private ResourceBundle resources;
  private Client client;
  private ServicesUser service;
  private JFXSnackbar snackBar;

  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private DateTimeFormatter dtfRad = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

  private Image imgOn = new Image(
      ClassLoader.getSystemResource("icons/green-light.png").toString());
  private Image imgOff = new Image(ClassLoader.getSystemResource("icons/red-light.png").toString());

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

    cmbUsers.setConverter(new StringConverter<UsersOnline>() {
      @Override
      public String toString(UsersOnline object) {
        return String
            .format("%s<%s> NAS: %s<%s>", object.getUserName(), object.getIp(), object.getNASName(),
                object.getNasIP());
      }

      @Override
      public UsersOnline fromString(String string) {
        return cmbUsers.getSelectionModel().getSelectedItem();
      }
    });

    cmbUsers.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<UsersOnline>() {
          @Override
          public void changed(ObservableValue<? extends UsersOnline> observable,
              UsersOnline oldValue,
              UsersOnline newValue) {
            System.out.println(newValue.getUserName());
            setUserDataStatus(newValue);
          }
        });


  }

  private void setUserDataStatus(UsersOnline userData) {
    lNAS.setText(String.format("%s <%s>", userData.getNASName(), userData.getNasIP()));
    lIP.setText(userData.getIp());
    lLinkUP.setText(userData.getUptime());
    lMAC.setText(userData.getMac());
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

    //DESNO
    object = new JSONObject();
    object.put("action", "checkUsersOnline");
    object.put("username", service.getUserName());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    for (String key : object.keySet()) {
      JSONObject userObj = new JSONObject();
      UsersOnline userOnline = new UsersOnline();
      userObj = object.getJSONObject(key);
      userOnline.setUserName(userObj.getString("userName"));
      userOnline.setService(userObj.getString("service"));
      userOnline.setMac(userObj.getString("callerID"));
      userOnline.setIp(userObj.getString("address"));
      userOnline.setUptime(userObj.getString("uptime"));
      userOnline.setSessionID(userObj.getString("sessionID"));
      userOnline.setNasIP(userObj.getString("NASIP"));
      userOnline.setNASName(userObj.getString("NASName"));
      userOnline.setIdentification(userObj.getString("identification"));
      cmbUsers.getItems().add(userOnline);
    }
    if (cmbUsers.getItems().size() > 0) {
      imgOnline.setImage(imgOn);
      cmbUsers.getSelectionModel().select(0);
    } else {
      imgOnline.setImage(imgOff);
      bRefres.setDisable(true);
    }



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
    System.out.println(cmbUsers.getItems().size());

    System.out.println(cmbUsers.getValue().getUserName());


  }

  public void changeSpeed(ActionEvent actionEvent) {
  }

  public void showPotrosnja(ActionEvent actionEvent) {
  }

  public void showPing(ActionEvent actionEvent) {
  }

  public void showBWMonitor(ActionEvent actionEvent) {
  }
}
