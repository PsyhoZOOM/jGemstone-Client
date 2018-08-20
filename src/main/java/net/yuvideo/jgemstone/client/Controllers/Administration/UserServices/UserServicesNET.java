package net.yuvideo.jgemstone.client.Controllers.Administration.UserServices;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.Controllers.Administration.Radius.TrafficReport;
import net.yuvideo.jgemstone.client.Controllers.Administration.TrafficReport.MtBwMonitor;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.BytesTo_KB_MB_GB_TB;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
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
  public Label lOnlineTime;
  public Label lTxBytes;
  public Label lRxBytes;
  public AnchorPane mainPane;
  public JFXTextField tPretraga;
  public JFXDatePicker dtpStart;
  public JFXDatePicker dtpStop;
  public JFXButton bTrazi;
  public JFXTreeTableView tblUserTrafficReport;
  public StackPane stackPane;
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

    dtpStop.setPromptText("Datum end");
    dtpStart.setPromptText("Datum start");
    dtpStart.setValue(LocalDate.now().minusMonths(1).withDayOfMonth(1));
    dtpStop.setValue(LocalDate.now());

    dtpStart.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        String date;
        if (object == null) {
          date = LocalDate.now().minusMonths(1).withDayOfMonth(1).format(dtf);
        } else {
          date = object.format(dtf);
        }
        return date;
      }

      @Override
      public LocalDate fromString(String string) {
        LocalDate date;
        if (string == null) {
          date = LocalDate.parse(LocalDate.now().minusMonths(1).withDayOfMonth(1).format(dtf), dtf);
        } else {
          date = LocalDate.parse(string, dtf);
        }
        return date;
      }
    });

    dtpStop.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        String date;
        if (object == null) {
          date = LocalDate.now().format(dtf);
        } else {
          date = object.format(dtf);
        }
        return date;
      }

      @Override
      public LocalDate fromString(String string) {
        LocalDate date;
        if (string == null) {
          date = LocalDate.parse(LocalDate.now().format(dtf));
        } else {
          date = LocalDate.parse(string, dtf);
        }
        return date;
      }
    });

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

    //TABLE TRAFFIC REPORT
    JFXTreeTableColumn<TrafficReport, String> cUserName = new JFXTreeTableColumn<>(
        "Korisnicko ime");
    JFXTreeTableColumn<TrafficReport, String> cIPAddress = new JFXTreeTableColumn<>("IP Adresa");
    JFXTreeTableColumn<TrafficReport, String> cMACAdresa = new JFXTreeTableColumn<>("MAC Adresa");
    JFXTreeTableColumn<TrafficReport, String> cNASIPAddress = new JFXTreeTableColumn<>(
        "NAS IP Adresa");
    JFXTreeTableColumn<TrafficReport, String> cService = new JFXTreeTableColumn<>("Service");
    JFXTreeTableColumn<TrafficReport, Long> cDownload = new JFXTreeTableColumn<>("Download");
    JFXTreeTableColumn<TrafficReport, Long> cUplaod = new JFXTreeTableColumn<>("Upload");
    JFXTreeTableColumn<TrafficReport, Integer> cOnlineTime = new JFXTreeTableColumn<>(
        "Online Vreme");
    JFXTreeTableColumn<TrafficReport, String> cStartTime = new JFXTreeTableColumn<>("Start Vreme");
    JFXTreeTableColumn<TrafficReport, String> cStopTime = new JFXTreeTableColumn<>("Stop Vreme");

    cUserName
        .setCellValueFactory(new TreeItemPropertyValueFactory<TrafficReport, String>("username"));
    cIPAddress.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, String>("framedipaddress"));
    cMACAdresa.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, String>("callingstationid"));
    cNASIPAddress.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, String>("nasipaddress"));
    cService.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, String>("calledstationid"));
    cDownload.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, Long>("acctinputoctets"));
    cUplaod.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, Long>("acctoutputoctets"));
    cOnlineTime.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, Integer>("acctsessiontime"));
    cStartTime.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, String>("acctstarttime"));
    cStopTime.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, String>("acctstoptime"));

    tblUserTrafficReport.getColumns()
        .addAll(cUserName, cIPAddress, cMACAdresa, cNASIPAddress, cService, cDownload, cUplaod,
            cOnlineTime,
            cStartTime, cStopTime);
    tblUserTrafficReport.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

    tPretraga.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        tblUserTrafficReport.setPredicate(new Predicate<TreeItem<TrafficReport>>() {
          @Override
          public boolean test(TreeItem<TrafficReport> trafficReportTreeItem) {
            tblUserTrafficReport.getSelectionModel().clearSelection();
            TrafficReport value = trafficReportTreeItem.getValue();
            boolean flag = value.getUsername().contains(newValue) || value.getFramedipaddress()
                .contains(newValue) ||
                value.getCallingstationid().contains(newValue) || value.getCalledstationid()
                .contains(newValue) ||
                value.getConnectinfo_start().contains(newValue) || value.getConnectinfo_stop()
                .contains(newValue);
            return flag;
          }
        });
      }
    });

    snackBar = new JFXSnackbar(mainPane);


  }

  private void setUserDataStatus(UsersOnline userData) {
    lNAS.setText(String.format("%s <%s>", userData.getNASName(), userData.getNasIP()));
    lIP.setText(userData.getIp());
    lLinkUP.setText(userData.getUptime());
    lMAC.setText(userData.getMac());
    lCalledID.setText(getCalledID(userData.getIp(), userData.getSessionID()));
    String tx = BytesTo_KB_MB_GB_TB
        .getFormatedString(Long.parseLong(userData.getTxByte()));
    String rx = BytesTo_KB_MB_GB_TB
        .getFormatedString(Long.parseLong(userData.getRxByte()));

    lTxBytes.setText(String.format("%s (%s)", userData.getTxByte(), tx));
    lRxBytes.setText(String.format("%s (%s)", userData.getRxByte(), rx));
    lLinkUP.setText(userData.getLinkUP());
    lOnlineTime.setText(userData.getUptime());
    lInterface.setText(userData.getInterfaceName());
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

    //USER STATUS ONLINE
    object = new JSONObject();
    object.put("action", "checkUsersOnline");
    object.put("username", service.getUserName());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    if (!object.getBoolean("userOnline")) {
      imgOnline.setImage(imgOff);
      return;
    }

    cmbUsers.getItems().removeAll();
    cmbUsers.getItems().clear();

    if (object.has("userOnlineStatus")) {
      for (String key : object.getJSONObject("userOnlineStatus").keySet()) {
        JSONObject userStatus = object.getJSONObject("userOnlineStatus").getJSONObject(key);
        UsersOnline userOnline = new UsersOnline();
        userOnline.setInterfaceName(userStatus.getString("interfaceName"));
        userOnline.setNasIP(userStatus.getString("nasIP"));
        userOnline.setNASName(userStatus.getString("nasName"));
        userOnline.setLinkUP(userStatus.getString("lastLinkUp"));
        userOnline.setRxByte(userStatus.getString("rxBytes"));
        userOnline.setTxByte(userStatus.getString("txBytes"));
        userOnline.setIp(userStatus.getString("ipAddress"));
        userOnline.setUserName(userStatus.getString("user"));
        userOnline.setService(userStatus.getString("service"));
        userOnline.setMac(userStatus.getString("MAC"));
        userOnline.setUptime(userStatus.getString("upTime"));
        userOnline.setSessionID(userStatus.getString("sessionID"));

        cmbUsers.getItems().add(userOnline);
      }
    }

    if (cmbUsers.getItems().size() > 0) {
      imgOnline.setImage(imgOn);
      cmbUsers.getSelectionModel().select(0);
    } else {
      imgOnline.setImage(imgOff);
    }



  }

  private String getCalledID(String address, String sessionID) {
    JSONObject object = new JSONObject();
    object.put("action", "getCalledID");
    object.put("IP", address);
    object.put("sessionID", sessionID);
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return null;
    }
    return object.getString("calledID");
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

    snackBar.show("Radius izmene snimljene", 5000);
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
    initData();


  }

  public void changeSpeed(ActionEvent actionEvent) {
  }


  public void showPing(ActionEvent actionEvent) {
    JFXDialogLayout contetn = new JFXDialogLayout();
    contetn.setHeading(new Text(String.format("PING: %s", cmbUsers.getValue().getIp())));

    Text pingRespone = new Text();

    JFXButton bPing = new JFXButton("PING");
    bPing.buttonTypeProperty().set(ButtonType.RAISED);

    bPing.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        JSONObject object = new JSONObject();
        object.put("action", "pingAddressMT");
        object.put("nasIP", cmbUsers.getValue().getNasIP());
        object.put("ipAddress", cmbUsers.getValue().getIp());
        object = client.send_object(object);
        pingRespone.setText(object.toString());

      }
    });
    contetn.setBody(pingRespone);
    JFXDialog dialog = new JFXDialog(stackPane, contetn, DialogTransition.LEFT, true);

    JFXButton bCloseDialog = new JFXButton("ZATVORI");
    bCloseDialog.buttonTypeProperty().set(ButtonType.RAISED);
    bCloseDialog.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        dialog.close();
      }
    });

    contetn.setActions(bCloseDialog, bPing);

    dialog.show();



  }

  public void showBWMonitor(ActionEvent actionEvent) {
    NewInterface bwMonit = new NewInterface("fxml/Administration/TrafficReport/MtBwMonitor.fxml",
        "INTERFACE MONITOR", resources, true, true);
    MtBwMonitor bwMonitController = bwMonit.getLoader().getController();
    bwMonitController.setClient(client);
    bwMonitController
        .initData(cmbUsers.getValue().getNasIP(), cmbUsers.getValue().getInterfaceName());
    bwMonit.getStage().showAndWait();

  }


  public void showUserTrafficReport(ActionEvent actionEvent) {

    TrafficReport trafficReport = new TrafficReport();
    trafficReport.setClient(client);
    ArrayList<TrafficReport> trafficReportArrayList = trafficReport
        .getTrafficReportArrayList(cmbUsers.getValue().getUserName(),
            dtpStart.getValue().toString(), dtpStop.getValue().toString());

    ObservableList<TrafficReport> trafficReportObservableList = FXCollections
        .observableList(trafficReportArrayList);
    TreeItem<TrafficReport> root = new RecursiveTreeItem<>(trafficReportObservableList,
        RecursiveTreeObject::getChildren);

    tblUserTrafficReport.setShowRoot(false);
    tblUserTrafficReport.setRoot(root);

    tblUserTrafficReport.getRoot().setExpanded(true);

  }
}
