package net.yuvideo.jgemstone.client.Controllers.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXButton.ButtonType;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javax.swing.text.TableView;
import net.yuvideo.jgemstone.client.Controllers.Administration.DTV.DTVKartice;
import net.yuvideo.jgemstone.client.Controllers.Administration.Search.UsersSearch;
import net.yuvideo.jgemstone.client.Controllers.Administration.TrafficReport.TrafficReport;
import net.yuvideo.jgemstone.client.Controllers.Administration.UserServices.UserServicesNET;
import net.yuvideo.jgemstone.client.Controllers.Administration.WiFi.WifiFinder;
import net.yuvideo.jgemstone.client.classes.Administration.Radius.RadiusOnlineLog;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.Services;
import net.yuvideo.jgemstone.client.classes.ServicesUser;
import net.yuvideo.jgemstone.client.classes.UsersOnline;
import org.json.JSONObject;

public class Administration implements Initializable {


  public JFXProgressBar prgBarUpdate;
  public Label lNumberOnline;
  public JFXTabPane tabCenter;
  public JFXTreeView trMenu;
  public JFXButton bRefresh;
  private Client client;


  private int counter = 0;
  private boolean loop = true;
  private ResourceBundle resources;
  private URL location;

  private boolean onlineUserVisible = false;
  private boolean netWIFIFinderVisible = false;
  private boolean onlineLOGVisible = false;
  private boolean izvestajPotrosnjeVisible = false;
  private boolean pretragaKorisnikaVisible = false;
  private boolean dtvPretragaVisible = false;

  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    setTreeItems();
    updateOnlineUsersSum();
  }

  private void setTreeItems() {
    TreeItem root = new TreeItem("MENU");
    //NET ADMINISTRATION
    TreeItem rootNET = new TreeItem("NET");
    TreeItem netSearchUsers = new TreeItem("Pretraga korisnika");
    TreeItem netOnlineUsers = new TreeItem("Online korisnici");
    TreeItem netOnlineLog = new TreeItem("Online LOG");
    TreeItem netPotrosnja = new TreeItem("Izvestaj potrosnje");
    TreeItem netWifiFinder = new TreeItem("WIFI Finder");
    rootNET.getChildren()
        .addAll(netSearchUsers, netOnlineUsers, netOnlineLog, netPotrosnja, netWifiFinder);

    //DTV ADMINISTRATION
    TreeItem rootDTV = new TreeItem("DTV");
    TreeItem dtvPretraga = new TreeItem("Pretraga kartica");
    rootDTV.getChildren().addAll(dtvPretraga);

    TreeItem rootIPTV = new TreeItem("IPTV");
    TreeItem rootFIX = new TreeItem("FIKSNA TELEFONIJA");
    root.getChildren().addAll(rootNET, rootDTV, rootIPTV, rootFIX);
    trMenu.setShowRoot(false);

    trMenu.setRoot(root);

    trMenu.setOnMouseClicked(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        if (event.getClickCount() == 2) {
          //NET
          if (trMenu.getSelectionModel().getSelectedItem().equals(netOnlineUsers)) {
            if (!onlineUserVisible) {
              showOnlineUsers();
            }
            onlineUserVisible = true;
          }
          if (trMenu.getSelectionModel().getSelectedItem().equals(netOnlineLog)) {
            if (!onlineLOGVisible) {
              showOnlineLOG();
            }
            onlineLOGVisible = true;
          }
          if (trMenu.getSelectionModel().getSelectedItem().equals(netSearchUsers)) {
            if (!pretragaKorisnikaVisible) {
              showSearchUsers();
            }
            pretragaKorisnikaVisible = true;
          }
          if (trMenu.getSelectionModel().getSelectedItem().equals(netWifiFinder)) {
            if (!netWIFIFinderVisible) {
              showNetWifiFinder();
            }
            netWIFIFinderVisible = true;
          }
          if (trMenu.getSelectionModel().getSelectedItem().equals(netPotrosnja)) {
            if (!izvestajPotrosnjeVisible) {
              showIzvestajPotrosnje();
            }
            izvestajPotrosnjeVisible = true;
          }
          //DTV
          if (trMenu.getSelectionModel().getSelectedItem().equals(dtvPretraga)) {
            if (!dtvPretragaVisible) {
              showDtvPretraga();
            }
            dtvPretragaVisible = true;
          }

        }
      }
    });


  }

  private void showDtvPretraga() {
    JFXTreeTableView<DTVKartice> tblDTVkartice = new JFXTreeTableView();
    tblDTVkartice.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    JFXTreeTableColumn<DTVKartice, String> cUserID = new JFXTreeTableColumn<>("USER ID");
    cUserID.setCellValueFactory(new TreeItemPropertyValueFactory<DTVKartice, String>("jbroj"));

    JFXTreeTableColumn<DTVKartice, Integer> cIDKartica = new JFXTreeTableColumn<>("KARTICA ID");
    cIDKartica
        .setCellValueFactory(new TreeItemPropertyValueFactory<DTVKartice, Integer>("idKartica"));

    JFXTreeTableColumn<DTVKartice, Integer> cPaketID = new JFXTreeTableColumn<>("PAKET ID");
    cPaketID.setCellValueFactory(new TreeItemPropertyValueFactory<DTVKartice, Integer>("paketID"));

    JFXTreeTableColumn<DTVKartice, String> cEndDate = new JFXTreeTableColumn<>("DATUM ISTEKA");
    cEndDate.setCellValueFactory(new TreeItemPropertyValueFactory<DTVKartice, String>("endDate"));

    JFXTreeTableColumn<DTVKartice, String> cAdresaUsluge = new JFXTreeTableColumn<DTVKartice, String>(
        "ADRESA");
    cAdresaUsluge
        .setCellValueFactory(new TreeItemPropertyValueFactory<DTVKartice, String>("adresaUsluge"));
    JFXTreeTableColumn<DTVKartice, String> cIme = new JFXTreeTableColumn<DTVKartice, String>("IME");
    cIme.setCellValueFactory(new TreeItemPropertyValueFactory<DTVKartice, String>("ime"));

    tblDTVkartice.getColumns().addAll(cUserID, cIme, cAdresaUsluge, cIDKartica, cPaketID, cEndDate);

    JFXTextField tSearchDTVKartice = new JFXTextField();
    tSearchDTVKartice.setLabelFloat(true);
    tSearchDTVKartice.setPromptText("Pretraga");

    JFXButton bRefresh = new JFXButton("Osvezi");

    bRefresh.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Thread threads = new Thread() {
          @Override
          public synchronized void start() {
            super.start();
            Platform.runLater(new Runnable() {
              @Override
              public void run() {

                bRefresh.setDisable(true);
                bRefresh.setText("Osvezavam...");
              }
            });
            DTVKartice dtvKartice = new DTVKartice();
            dtvKartice.setClient(client);
            TreeItem<DTVKartice> root = new RecursiveTreeItem<>(
                FXCollections.observableArrayList(dtvKartice.getDtvKarticeArrayList()),
                RecursiveTreeObject::getChildren);
            tblDTVkartice.setRoot(root);
            tblDTVkartice.setShowRoot(false);
            Platform.runLater(new Runnable() {
              @Override
              public void run() {
                bRefresh.setDisable(false);
                bRefresh.setText("Osvezi");
              }
            });
          }
        };
        threads.start();

      }
    });

    tSearchDTVKartice.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        String search = tSearchDTVKartice.getText();
        tblDTVkartice.setPredicate(new Predicate<TreeItem<DTVKartice>>() {
          @Override
          public boolean test(TreeItem<DTVKartice> dtvKarticeTreeItem) {
            return String.valueOf(dtvKarticeTreeItem.getValue().getIdKartica()).contains(search) ||
                String.valueOf(dtvKarticeTreeItem.getValue().getUserID()).contains(search) ||
                String.valueOf(dtvKarticeTreeItem.getValue().getPaketID()).contains(search) ||
                dtvKarticeTreeItem.getValue().getEndDate().contains(search) ||
                dtvKarticeTreeItem.getValue().getIme().contains(search) ||
                dtvKarticeTreeItem.getValue().getAdresaUsluge().contains(search);
          }
        });
      }
    });

    Tab tabDTVKartice = new Tab("DTV Kartice");
    tabDTVKartice.setOnCloseRequest(new EventHandler<Event>() {
      @Override
      public void handle(Event event) {
        dtvPretragaVisible = false;
      }
    });
    VBox vBox = new VBox();
    HBox hBox = new HBox();

    vBox.setSpacing(5);
    vBox.setPadding(new Insets(20, 5, 5, 5));

    hBox.setSpacing(5);

    hBox.getChildren().addAll(tSearchDTVKartice, bRefresh);
    vBox.getChildren().addAll(hBox, tblDTVkartice);
    tabDTVKartice.setContent(vBox);
    VBox.setVgrow(tblDTVkartice, Priority.ALWAYS);
    tabCenter.getTabs().add(tabDTVKartice);
    tabCenter.getSelectionModel().select(tabDTVKartice);

  }

  private void showIzvestajPotrosnje() {
    JFXTreeTableView<TrafficReport> tblTrafficReport = new JFXTreeTableView<>();

    JFXTreeTableColumn<TrafficReport, String> cUserName = new JFXTreeTableColumn<>(
        "KORISNIČKO IME");
    cUserName
        .setCellValueFactory(new TreeItemPropertyValueFactory<TrafficReport, String>("username"));
    JFXTreeTableColumn<TrafficReport, String> cNasIP = new JFXTreeTableColumn<>("NAS IP");
    cNasIP.setCellValueFactory(new TreeItemPropertyValueFactory<TrafficReport, String>("nasIP"));
    JFXTreeTableColumn<TrafficReport, String> cStart = new JFXTreeTableColumn<>("START VREME");
    cStart
        .setCellValueFactory(new TreeItemPropertyValueFactory<TrafficReport, String>("startTime"));
    JFXTreeTableColumn<TrafficReport, String> cStop = new JFXTreeTableColumn<>("STOP VRENE");
    cStop.setCellValueFactory(new TreeItemPropertyValueFactory<TrafficReport, String>("stopTime"));
    JFXTreeTableColumn<TrafficReport, String> cOnlineTime = new JFXTreeTableColumn<>(
        "ONLINE VREME");
    cOnlineTime
        .setCellValueFactory(new TreeItemPropertyValueFactory<TrafficReport, String>("onlineTime"));
    JFXTreeTableColumn<TrafficReport, Long> cInputOctets = new JFXTreeTableColumn<>("INPUT OCTETS");
    cInputOctets
        .setCellValueFactory(new TreeItemPropertyValueFactory<TrafficReport, Long>("inputOctets"));
    JFXTreeTableColumn<TrafficReport, Long> cOutputOctets = new JFXTreeTableColumn<>(
        "OUTPUT OCTETS");
    cOutputOctets
        .setCellValueFactory(new TreeItemPropertyValueFactory<TrafficReport, Long>("outputOctets"));
    JFXTreeTableColumn<TrafficReport, String> cService = new JFXTreeTableColumn<>("SERVICE");
    cService
        .setCellValueFactory(new TreeItemPropertyValueFactory<TrafficReport, String>("service"));
    JFXTreeTableColumn<TrafficReport, String> cCallingStationID = new JFXTreeTableColumn<>("MAC");
    cCallingStationID.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, String>("callingStationID"));
    JFXTreeTableColumn<TrafficReport, String> cIPAddress = new JFXTreeTableColumn<>("IP ADDRESSA");
    cIPAddress
        .setCellValueFactory(new TreeItemPropertyValueFactory<TrafficReport, String>("ipAddress"));
    JFXTreeTableColumn<TrafficReport, String> cTerminateCause = new JFXTreeTableColumn<>("RAZLOG");
    cTerminateCause.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, String>("terminateCause"));

    tblTrafficReport.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    tblTrafficReport.getColumns()
        .addAll(cUserName, cNasIP, cService, cStart, cStop, cOnlineTime, cInputOctets,
            cOutputOctets, cCallingStationID, cIPAddress, cTerminateCause);

    JFXTextField tSearch = new JFXTextField();
    tSearch.setPromptText("Pretraga");
    tSearch.setLabelFloat(true);


    JFXDatePicker dateStart = new JFXDatePicker();
    dateStart.setPromptText("Start vreme");
    dateStart.setValue(LocalDate.now().minusMonths(1).withDayOfMonth(1));
    dateStart.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        if (object == null) {
          object = LocalDate.now();
        }
        return object.format(dtf);
      }

      @Override
      public LocalDate fromString(String string) {
        LocalDate date;
        if (string == null) {
          date = LocalDate.parse(string, dtf);
          date = date.withDayOfMonth(1);
          date = date.minusMonths(1);
        } else {
          date = LocalDate.parse(string, dtf);
        }

        return date;
      }
    });
    JFXDatePicker dateStop = new JFXDatePicker();
    dateStop.setValue(LocalDate.now());
    dateStop.setPromptText("Stop vreme");
    dateStop.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        if (object == null) {
          object = LocalDate.now();
        }
        return object.format(dtf);
      }

      @Override
      public LocalDate fromString(String string) {
        LocalDate date;
        if (string == null) {
          date = LocalDate.parse(LocalDate.now().format(dtf), dtf);
        } else {
          date = LocalDate.parse(string, dtf);
        }

        return date;
      }
    });

    JFXButton bSearch = new JFXButton("Pretraga");
    bSearch.setButtonType(ButtonType.RAISED);

    bSearch.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        JSONObject object = new JSONObject();
        object.put("action", "getTrafficReports");
        object.put("startTime", dateStart.getValue().format(dtf));
        object.put("stopTime", dateStop.getValue().format(dtf));
        object = client.send_object(object);

        if (object.has("ERROR")) {
          AlertUser.error("GRESKA", object.getString("ERROR"));
          return;
        }

        ArrayList<TrafficReport> trafficReportArrayList = new ArrayList<>();
        for (int i = 0; i < object.length(); i++) {
          JSONObject data = object.getJSONObject(String.valueOf(i));
          TrafficReport report = new TrafficReport();
          report.setId(data.getInt("id"));
          report.setUsername(data.getString("username"));
          report.setNasIP(data.getString("nasIP"));
          report.setStartTime(data.getString("startTime"));
          report.setStopTime(data.getString("stopTime"));
          report.setOnlineTime(data.getString("onlineTime"));
          report.setInputOctets(data.getLong("inputOctets"));
          report.setOutputOctets(data.getLong("outputOctets"));
          report.setService(data.getString("service"));
          report.setCallingStationID(data.getString("callingStationID"));
          report.setIpAddress(data.getString("ipAddress"));
          report.setTerminateCause(data.getString("terminateCause"));
          trafficReportArrayList.add(report);
        }
        TreeItem<TrafficReport> root = new RecursiveTreeItem<>(
            FXCollections.observableArrayList(trafficReportArrayList),
            RecursiveTreeObject::getChildren);
        tblTrafficReport.setRoot(root);
        tblTrafficReport.setShowRoot(false);
      }
    });

    tSearch.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        String search = tSearch.getText();
        tblTrafficReport.setPredicate(new Predicate<TreeItem<TrafficReport>>() {
          @Override
          public boolean test(TreeItem<TrafficReport> trafficReportTreeItem) {
            return
                trafficReportTreeItem.getValue().getIpAddress().contains(search) ||
                    trafficReportTreeItem.getValue().getUsername().contains(search) ||
                    trafficReportTreeItem.getValue().getCallingStationID().contains(search) ||
                    trafficReportTreeItem.getValue().getNasIP().contains(search) ||
                    trafficReportTreeItem.getValue().getService().contains(search) ||
                    trafficReportTreeItem.getValue().getIpAddress().contains(search) ||
                    trafficReportTreeItem.getValue().getTerminateCause().contains(search);
          }
        });
      }
    });

    HBox hBox = new HBox();
    hBox.getChildren().addAll(tSearch, dateStart, dateStop, bSearch);
    hBox.setSpacing(5);

    VBox vBox = new VBox();
    vBox.getChildren().addAll(hBox, tblTrafficReport);
    vBox.setSpacing(5);
    vBox.setPadding(new Insets(20, 5, 0, 5));

    Tab tabIzvestajPotrosnje = new Tab("IZVEŠTAJ POTROŠNJE");
    tabIzvestajPotrosnje.setContent(vBox);

    VBox.setVgrow(tblTrafficReport, Priority.ALWAYS);

    tabIzvestajPotrosnje.setOnCloseRequest(new EventHandler<Event>() {
      @Override
      public void handle(Event event) {
        izvestajPotrosnjeVisible = false;
      }
    });

    tabCenter.getTabs().add(tabIzvestajPotrosnje);
    tabCenter.getSelectionModel().select(tabIzvestajPotrosnje);


  }

  private void showNetWifiFinder() {
    JFXTreeTableView<WifiFinder> tblWifiFinder = new JFXTreeTableView<>();
    JFXTreeTableColumn<WifiFinder, String> cMac = new JFXTreeTableColumn<>("MAC");
    cMac.setCellValueFactory(new TreeItemPropertyValueFactory<WifiFinder, String>("mac"));
    JFXTreeTableColumn<WifiFinder, String> cIP = new JFXTreeTableColumn<>("AP IP");
    cIP.setCellValueFactory(new TreeItemPropertyValueFactory<WifiFinder, String>("hsIP"));
    JFXTreeTableColumn<WifiFinder, String> cNaziv = new JFXTreeTableColumn<>("AP HOST");
    cNaziv.setCellValueFactory(new TreeItemPropertyValueFactory<WifiFinder, String>("hsName"));
    JFXTreeTableColumn<WifiFinder, String> cSignal = new JFXTreeTableColumn<>("SIGNAL");
    cSignal.setCellValueFactory(new TreeItemPropertyValueFactory<WifiFinder, String>("signal"));
    JFXTreeTableColumn<WifiFinder, String> cLastSeen = new JFXTreeTableColumn<>("VREME");
    cLastSeen
        .setCellValueFactory(new TreeItemPropertyValueFactory<WifiFinder, String>("lastTimeSeen"));

    tblWifiFinder.getColumns().addAll(cMac, cIP, cNaziv, cSignal, cLastSeen);

    tblWifiFinder.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

    JFXTextField tSearch = new JFXTextField();
    tSearch.setLabelFloat(true);
    tSearch.setPromptText("Pretraga");

    JFXButton bSearch = new JFXButton("Pretraga");
    bSearch.setButtonType(ButtonType.RAISED);

    bSearch.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        JSONObject object = new JSONObject();
        object.put("action", "getWifiSignalData");
        object = client.send_object(object);
        if (object.has("ERROR")) {
          AlertUser.error("GRESKA", object.getString("ERROR"));
          return;
        }
        ArrayList<WifiFinder> wifiFinderArrayList = new ArrayList<>();
        for (int i = 0; i < object.length(); i++) {
          JSONObject wiData;
          wiData = object.getJSONObject(String.valueOf(i));
          WifiFinder wifiFinder = new WifiFinder();
          wifiFinder.setId(wiData.getInt("id"));
          wifiFinder.setMac(wiData.getString("mac"));
          wifiFinder.setHsIP(wiData.getString("hsIP"));
          wifiFinder.setSignal(wiData.getString("signal"));
          wifiFinder.setHsName(wiData.getString("hsName"));
          wifiFinder.setLastTimeSeen(wiData.getString("lastTimeSeen"));
          wifiFinder.setLastTimeUpdated(wiData.getString("lastUpdated"));
          wifiFinderArrayList.add(wifiFinder);
        }

        TreeItem<WifiFinder> root = new RecursiveTreeItem<>(
            FXCollections.observableArrayList(wifiFinderArrayList),
            RecursiveTreeObject::getChildren);

        tblWifiFinder.setRoot(root);
        tblWifiFinder.setShowRoot(false);

        tSearch.textProperty().addListener(new ChangeListener<String>() {
          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue,
              String newValue) {
            String search = tSearch.getText();
            tblWifiFinder.setPredicate(new Predicate<TreeItem<WifiFinder>>() {
              @Override
              public boolean test(TreeItem<WifiFinder> wifiFinderTreeItem) {
                return
                    wifiFinderTreeItem.getValue().getHsIP().contains(search) ||
                        wifiFinderTreeItem.getValue().getHsName().contains(search) ||
                        wifiFinderTreeItem.getValue().getMac().contains(search) ||
                        wifiFinderTreeItem.getValue().getLastTimeUpdated().contains(search) ||
                        wifiFinderTreeItem.getValue().getLastTimeSeen().contains(search);
              }
            });
          }
        });
      }
    });

    HBox hbox = new HBox();
    hbox.getChildren().addAll(tSearch, bSearch);

    VBox vBox = new VBox();
    vBox.getChildren().addAll(hbox, tblWifiFinder);
    vBox.setSpacing(5);
    vBox.setPadding(new Insets(20, 5, 0, 5));

    Tab tabWifiFinder = new Tab("WIFI Finder");
    tabWifiFinder.setContent(vBox);
    VBox.setVgrow(tblWifiFinder, Priority.ALWAYS);

    tabWifiFinder.setOnCloseRequest(new EventHandler<Event>() {
      @Override
      public void handle(Event event) {
        netWIFIFinderVisible = false;
      }
    });

    tabCenter.getTabs().add(tabWifiFinder);
    tabCenter.getSelectionModel().select(tabWifiFinder);

  }

  private void showSearchUsers() {
    JFXTreeTableView<UsersSearch> tableUserSearch = new JFXTreeTableView<UsersSearch>();

    //menu
    MenuItem connectionInfo = new MenuItem("Konekcija");
    connectionInfo.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (tableUserSearch.getSelectionModel().getSelectedIndex() == -1) {
          return;
        }
        showConnectionInfoWindow(
            tableUserSearch.getSelectionModel().getSelectedItem().getValue().getId(),
            tableUserSearch.getSelectionModel().getSelectedItem().getValue().getUsername());
      }
    });
    ContextMenu cmenu = new ContextMenu();
    cmenu.getItems().add(connectionInfo);
    tableUserSearch.setContextMenu(cmenu);

    JFXTreeTableColumn<UsersSearch, String> cUserID = new JFXTreeTableColumn("USER ID");
    cUserID.setCellValueFactory(new TreeItemPropertyValueFactory<UsersSearch, String>("jbroj"));
    JFXTreeTableColumn<UsersSearch, String> cIme = new JFXTreeTableColumn<>("IME");
    cIme.setCellValueFactory(new TreeItemPropertyValueFactory<UsersSearch, String>("ime"));
    JFXTreeTableColumn<UsersSearch, String> cUserName = new JFXTreeTableColumn<>("KORISNIČKO IME");
    cUserName
        .setCellValueFactory(new TreeItemPropertyValueFactory<UsersSearch, String>("username"));
    JFXTreeTableColumn<UsersSearch, String> cGroupName = new JFXTreeTableColumn<>("GRUPA");
    cGroupName
        .setCellValueFactory(new TreeItemPropertyValueFactory<UsersSearch, String>("groupName"));

    JFXTreeTableColumn<UsersSearch, String> cEndDate = new JFXTreeTableColumn<>("DATUM ISTEKA");
    cEndDate.setCellValueFactory(new TreeItemPropertyValueFactory<UsersSearch, String>("endDate"));
    JFXTreeTableColumn<UsersSearch, String> cMestoUsluge = new JFXTreeTableColumn<>(
        "ADRESA USLUGE");
    cMestoUsluge
        .setCellValueFactory(new TreeItemPropertyValueFactory<UsersSearch, String>("adresaUsluge"));

    JFXTreeTableColumn<UsersSearch, HBox> cCheckOnline = new JFXTreeTableColumn<>(
        "ONLINE STATUS");
    cCheckOnline
        .setCellValueFactory(new TreeItemPropertyValueFactory<UsersSearch, HBox>("statusBox"));

    tableUserSearch.getColumns()
        .addAll(cUserID, cIme, cUserName, cGroupName, cMestoUsluge, cEndDate, cCheckOnline);
    tableUserSearch.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

    VBox vBox = new VBox();
    vBox.setPadding(new Insets(20, 5, 0, 5));
    vBox.setSpacing(5);

    HBox hBox = new HBox();
    hBox.setSpacing(5);

    JFXTextField tSearch = new JFXTextField();
    tSearch.setPromptText("Pretraga korisnika");
    tSearch.setLabelFloat(true);

    JFXButton bRefresh = new JFXButton("Osveži");
    bRefresh.setButtonType(ButtonType.RAISED);

    bRefresh.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        JSONObject object = new JSONObject();
        object.put("action", "searchRadiusUsers");
        object = client.send_object(object);
        if (object.has("ERROR")) {
          AlertUser.error("GRESKA", object.getString("ERROR"));
          return;
        }

        ArrayList<UsersSearch> usersSearchArrayList = new ArrayList<>();
        for (int i = 0; i < object.length(); i++) {
          JSONObject onlinObj = new JSONObject();
          UsersSearch usersSearch = new UsersSearch();
          onlinObj = object.getJSONObject(String.valueOf(i));
          usersSearch.setJbroj(onlinObj.getString("jBroj"));
          usersSearch.setId(onlinObj.getInt("id"));
          usersSearch.setAdresaUsluge(String.format("%s %s", onlinObj.getString("jAdresaNaziv"),
              onlinObj.getString("jMestoNaziv")));
          usersSearch.setEndDate(onlinObj.getString("endDate"));
          usersSearch.setUsername(onlinObj.getString("username"));
          usersSearch.setGroupName(onlinObj.getString("groupName"));
          usersSearch.setIme(onlinObj.getString("ime"));
          usersSearch.setStatusBox(getImageStatus(onlinObj.getString("username")));

          usersSearchArrayList.add(usersSearch);
        }

        TreeItem<UsersSearch> root = new RecursiveTreeItem<>(
            FXCollections.observableArrayList(usersSearchArrayList),
            RecursiveTreeObject::getChildren);
        tableUserSearch.setRoot(root);
        tableUserSearch.setShowRoot(false);

        tSearch.textProperty().addListener(new ChangeListener<String>() {
          @Override
          public void changed(ObservableValue<? extends String> observable, String oldValue,
              String newValue) {
            String search = tSearch.getText();
            tableUserSearch.setPredicate(new Predicate<TreeItem<UsersSearch>>() {
              @Override
              public boolean test(TreeItem<UsersSearch> usersSearchTreeItem) {
                return usersSearchTreeItem.getValue().getAdresaUsluge().contains(search) ||
                    usersSearchTreeItem.getValue().getEndDate().contains(search) ||
                    usersSearchTreeItem.getValue().getJbroj().contains(search) ||
                    usersSearchTreeItem.getValue().getIme().contains(search) ||
                    usersSearchTreeItem.getValue().getUsername().contains(search);
              }
            });
          }
        });
      }
    });

    hBox.getChildren().addAll(tSearch, bRefresh);

    vBox.getChildren().addAll(hBox, tableUserSearch);
    VBox.setVgrow(tableUserSearch, Priority.ALWAYS);

    Tab tabSearchUsers = new Tab("PRETRAGA KORISNIKA");
    tabSearchUsers.setContent(vBox);
    tabSearchUsers.setOnCloseRequest(new EventHandler<Event>() {
      @Override
      public void handle(Event event) {
        pretragaKorisnikaVisible = false;
      }
    });
    tabCenter.getTabs().add(tabSearchUsers);
    tabCenter.getSelectionModel().select(tabSearchUsers);
  }

  private void showConnectionInfoWindow(int userID, String username) {
    NewInterface connectionInfoInterface = new NewInterface(
        "fxml/Administration/UserServices/UserServicesNET.fxml", "Korisnik conneciton info",
        resources, true, false);
    UserServicesNET userServicesNETController = connectionInfoInterface.getLoader().getController();

    userServicesNETController.setClient(client);

    ServicesUser servicesUser = new ServicesUser();
    ServicesUser srvUserData = null;
    ArrayList<ServicesUser> userServiceArr = servicesUser.getUserServiceArr(userID, client);
    for (ServicesUser srvUser : userServiceArr) {
      if (srvUser.getUserName().equals(username)) {
        srvUserData = srvUser;
        break;
      }
    }
    if (srvUserData == null) {
      return;
    }
    userServicesNETController.setService(srvUserData);
    userServicesNETController.initData();
    connectionInfoInterface.getStage().showAndWait();
  }

  private void showOnlineLOG() {

    Tab tabRadiusOnline = new Tab("RADIUS ONLINE LOG");
    JFXTreeTableView<RadiusOnlineLog> tableRadiusOnlineLog = new JFXTreeTableView();

    JFXTreeTableColumn<RadiusOnlineLog, String> cUsername = new JFXTreeTableColumn<>("USERNAME");
    cUsername
        .setCellValueFactory(new TreeItemPropertyValueFactory<RadiusOnlineLog, String>("username"));
    JFXTreeTableColumn<RadiusOnlineLog, String> cAuthDate = new JFXTreeTableColumn<>(
        "DATUM LOGOVANJA");
    cAuthDate
        .setCellValueFactory(new TreeItemPropertyValueFactory<RadiusOnlineLog, String>("authdate"));
    JFXTreeTableColumn<RadiusOnlineLog, String> cReply = new JFXTreeTableColumn<>("STATUS");
    cReply.setCellValueFactory(new TreeItemPropertyValueFactory<RadiusOnlineLog, String>("reply"));
    cReply.setCellFactory(
        new Callback<TreeTableColumn<RadiusOnlineLog, String>, TreeTableCell<RadiusOnlineLog, String>>() {
          @Override
          public TreeTableCell<RadiusOnlineLog, String> call(
              TreeTableColumn<RadiusOnlineLog, String> param) {
            return new TreeTableCell<RadiusOnlineLog, String>() {
              @Override
              protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setText("");
                } else {
                  setText(item);
                  if (item.equals("Korisnik ulogovan")) {
                    setStyle("-fx-background-color: lightgreen");
                  } else {
                    setStyle("-fx-background-color: red");
                  }
                }
              }
            };
          }
        });
    JFXTreeTableColumn<RadiusOnlineLog, String> cMAC = new JFXTreeTableColumn<>("MAC");
    cMAC.setCellValueFactory(new TreeItemPropertyValueFactory<RadiusOnlineLog, String>("clientid"));
    JFXTreeTableColumn<RadiusOnlineLog, String> cNAS = new JFXTreeTableColumn<RadiusOnlineLog, String>(
        "NAS");
    cNAS.setCellValueFactory(new TreeItemPropertyValueFactory<RadiusOnlineLog, String>("nas"));

    tableRadiusOnlineLog.getColumns().addAll(cUsername, cAuthDate, cReply, cMAC, cNAS);

    JFXTextField tSearch = new JFXTextField();
    tSearch.setPromptText("Pretraga");
    tSearch.setLabelFloat(true);

    JFXTextField tRowCount = new JFXTextField("100");
    tRowCount.setPromptText("Broj redova");

    JFXButton bSearch = new JFXButton("Prikaži");
    bSearch.setButtonType(ButtonType.RAISED);
    bSearch.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        JSONObject object = new JSONObject();

        object.put("action", "getRadiusOnlineLOG");
        object.put("rowCount", Integer.valueOf(tRowCount.getText()));
        object = client.send_object(object);
        if (object.has("ERROR")) {
          AlertUser.error("GRESKA", object.getString("ERROR"));
          return;
        }
        ArrayList<RadiusOnlineLog> radiusOnlineLogs = new ArrayList<>();
        for (int i = 0; i < object.length(); i++) {
          JSONObject radiusLog = object.getJSONObject(String.valueOf(i));
          RadiusOnlineLog radiusOnlineLog = new RadiusOnlineLog();
          radiusOnlineLog.setId(radiusLog.getInt("id"));
          radiusOnlineLog.setUsername(radiusLog.getString("username"));
          radiusOnlineLog.setAuthdate(radiusLog.getString("authdate"));
          radiusOnlineLog.setReply(radiusLog.getString("reply"));
          radiusOnlineLog.setNas(radiusLog.getString("nas"));
          radiusOnlineLog.setClientid(radiusLog.getString("clientid"));
          radiusOnlineLogs.add(radiusOnlineLog);
        }

        TreeItem<RadiusOnlineLog> root = new RecursiveTreeItem<>(
            FXCollections.observableArrayList(radiusOnlineLogs), RecursiveTreeObject::getChildren);
        tableRadiusOnlineLog.setShowRoot(false);
        tableRadiusOnlineLog.setRoot(root);

      }
    });

    tSearch.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        String search = tSearch.getText();
        tableRadiusOnlineLog.setPredicate(new Predicate<TreeItem<RadiusOnlineLog>>() {
          @Override
          public boolean test(TreeItem<RadiusOnlineLog> radiusOnlineLogTreeItem) {
            return radiusOnlineLogTreeItem.getValue().getClientid().contains(search) ||
                radiusOnlineLogTreeItem.getValue().getNas().contains(search) ||
                radiusOnlineLogTreeItem.getValue().getClientid().contains(search) ||
                radiusOnlineLogTreeItem.getValue().getUsername().contains(search) ||
                radiusOnlineLogTreeItem.getValue().getAuthdate().contains(search) ||
                radiusOnlineLogTreeItem.getValue().getReply().contains(search);
          }
        });
      }
    });

    VBox vBox = new VBox();
    vBox.setPadding(new Insets(20, 5, 0, 5));
    VBox.setVgrow(tableRadiusOnlineLog, Priority.ALWAYS);

    HBox hBox = new HBox();
    hBox.setSpacing(5);
    hBox.getChildren().addAll(tSearch, tRowCount, bSearch);

    vBox.getChildren().addAll(hBox, tableRadiusOnlineLog);

    tableRadiusOnlineLog.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    tabRadiusOnline.setContent(vBox);
    tabRadiusOnline.setOnCloseRequest(new EventHandler<Event>() {
      @Override
      public void handle(Event event) {
        onlineLOGVisible = false;
      }
    });
    tabCenter.getTabs().add(tabRadiusOnline);
    tabCenter.getSelectionModel().select(tabRadiusOnline);


  }


  private void updateOnlineUsersSum() {
    Task task = new Task() {
      @Override
      protected Object call() throws Exception {
        while (loop) {
          updateProgress(counter, 100);
          counter++;
          if (counter == 100) {
            counter = 0;
            updateOnlineUsers();
          }
          Thread.sleep(1000);
        }
        return null;
      }
    };

    prgBarUpdate.progressProperty().bind(task.progressProperty());
    Thread thread = new Thread(task);
    thread.start();

  }

  private void updateOnlineUsers() {

    Thread thread = new Thread(() -> {
      JSONObject object = new JSONObject();
      object.put("action", "getOnlineUsersCount");
      object = client.send_object(object);
      int onlineSum = object.getInt("count");
      counter = 0;
      Platform.runLater(new Runnable() {
        @Override
        public void run() {
          lNumberOnline.setText(String.valueOf(onlineSum));
          bRefresh.setDisable(false);
        }
      });
    });
    thread.start();




  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void updateUsers(ActionEvent actionEvent) {
    bRefresh.setDisable(true);

    updateOnlineUsers();
  }

  public void showOnlineUsers() {
    JFXTreeTableView<UsersOnline> tblOnlineUser = new JFXTreeTableView();
    tblOnlineUser.setShowRoot(false);

    JFXTreeTableColumn<UsersOnline, Integer> userID = new JFXTreeTableColumn<>("USER ID");
    userID.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, Integer>("userID"));

    JFXTreeTableColumn<UsersOnline, String> interfaceName = new JFXTreeTableColumn<>(
        "IME INTERFEJSA");
    interfaceName.setCellValueFactory(
        new TreeItemPropertyValueFactory<UsersOnline, String>("interfaceName"));

    JFXTreeTableColumn<UsersOnline, String> service = new JFXTreeTableColumn<>("SERVICE");
    service.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("service"));

    JFXTreeTableColumn<UsersOnline, String> userName = new JFXTreeTableColumn<>("USERNAME");
    userName.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("userName"));

    JFXTreeTableColumn<UsersOnline, String> upTime = new JFXTreeTableColumn<>("ONLINE VREME");
    upTime.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("uptime"));

    JFXTreeTableColumn<UsersOnline, String> MAC = new JFXTreeTableColumn<>("MAC");
    MAC.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("mac"));

    JFXTreeTableColumn<UsersOnline, String> nasIP = new JFXTreeTableColumn<>("NAS IP");
    nasIP.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("nasIP"));

    JFXTreeTableColumn<UsersOnline, String> nasNAME = new JFXTreeTableColumn<>("NAS");
    nasNAME.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("NASName"));

    JFXTreeTableColumn<UsersOnline, String> mestoUsluge = new JFXTreeTableColumn<>("MESTO USLUGE");
    mestoUsluge
        .setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("mestoUsluge"));

    tblOnlineUser.getColumns()
        .addAll(userID, userName, mestoUsluge, upTime, interfaceName, MAC, service, nasNAME, nasIP);

    JFXButton bRefreshOnline = new JFXButton("Osveži");

    bRefreshOnline.setOnAction(new EventHandler<ActionEvent>() {


      @Override
      public void handle(ActionEvent event) {

        Thread thread = new Thread(() -> {
          Platform.runLater(new Runnable() {
            @Override
            public void run() {

              getOnlineUsers(tblOnlineUser, bRefreshOnline);
            }
          });
        });
        thread.start();


      }

    });

    tblOnlineUser.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

    JFXTextField userSearch = new JFXTextField();
    userSearch.setLabelFloat(true);
    userSearch.setPromptText("Pretraga korisnika online");
    userSearch.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        String search = userSearch.getText();
        tblOnlineUser.setPredicate(new Predicate<TreeItem<UsersOnline>>() {
          @Override
          public boolean test(TreeItem<UsersOnline> usersOnlineTreeItem) {
            return
                usersOnlineTreeItem.getValue().getUserName().contains(search) ||
                    usersOnlineTreeItem.getValue().getMac().contains(search) ||
                    usersOnlineTreeItem.getValue().getInterfaceName().contains(search) ||
                    usersOnlineTreeItem.getValue().getNasIP().contains(search) ||
                    usersOnlineTreeItem.getValue().getMestoUsluge().contains(search) ||
                    usersOnlineTreeItem.getValue().getNASName().contains(search);

          }
        });
      }
    });

    VBox vBox = new VBox();
    vBox.setSpacing(5);

    HBox hBox = new HBox();
    hBox.getChildren().addAll(userSearch, bRefreshOnline);
    hBox.setSpacing(5);
    hBox.setPadding(new Insets(20, 5, 5, 5));

    vBox.getChildren().addAll(hBox, tblOnlineUser);

    Tab onlineTab = new Tab("ONLINE KORISNICI");
    onlineTab.setContent(vBox);
    VBox.setVgrow(tblOnlineUser, Priority.ALWAYS);

    onlineTab.setOnCloseRequest(new EventHandler<Event>() {
      @Override
      public void handle(Event event) {
        onlineUserVisible = false;
      }
    });
    tabCenter.getTabs().add(onlineTab);

    tabCenter.getSelectionModel().select(onlineTab);

  }

  private void getOnlineUsers(
      JFXTreeTableView<UsersOnline> tblOnlineUser, JFXButton bRefreshOnline) {

    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        bRefreshOnline.setDisable(true);
        bRefreshOnline.setText("Učitavam..");
      }
    });

    JSONObject object = new JSONObject();
    object.put("action", "getOnlineUsers");
    object = client.send_object(object);
    ArrayList<UsersOnline> onlineArrayList = new ArrayList<>();
    for (int i = 0; i < object.length(); i++) {
      JSONObject onlineU = object.getJSONObject(String.valueOf(i));
      UsersOnline uOnlie = new UsersOnline();
      uOnlie.setInterfaceName(onlineU.getString("interfaceName"));
      if (onlineU.has("service")) {
        uOnlie.setService(onlineU.getString("service"));
      }
      uOnlie.setUptime(onlineU.getString("uptime"));
      uOnlie.setUserName(onlineU.getString("user"));
      uOnlie.setMac(onlineU.getString("remoteMAC"));
      uOnlie.setNasIP(onlineU.getString("nasIP"));
      uOnlie.setNASName(onlineU.getString("nasName"));
      uOnlie.setUserID(onlineU.getInt("userID"));
      String mestoUslg = "";
      if (onlineU.getJSONObject("userData").has("jMestoNaziv")) {
        mestoUslg = onlineU.getJSONObject("userData").getString("jMestoNaziv");
      }
      uOnlie.setMestoUsluge(mestoUslg);
      onlineArrayList.add(uOnlie);
    }

    TreeItem<UsersOnline> root = new RecursiveTreeItem<UsersOnline>(
        FXCollections.observableArrayList(onlineArrayList), RecursiveTreeObject::getChildren);

    Platform.runLater(new Runnable() {
      @Override
      public void run() {

        tblOnlineUser.setRoot(root);
        bRefreshOnline.setText("Osveži");
        bRefreshOnline.setDisable(false);

      }
    });


  }

  public void stopUpdate() {
    this.loop = false;
  }


  private HBox getImageStatus(String userName) {
    JFXButton checkOnlineButton = new JFXButton("Status");
    checkOnlineButton.setButtonType(ButtonType.RAISED);
    ImageView imageView = new ImageView();
    Image imgOnlineOn = new Image(
        ClassLoader.getSystemResourceAsStream("icons/green-light.png"), 20.0, 20.0,
        true, true);
    Image imgOnlineOff = new Image(
        ClassLoader.getSystemResourceAsStream("icons/red-light.png"), 20.0, 20.0,
        true, true);
    HBox hBox = new HBox(checkOnlineButton, imageView);
    hBox.setAlignment(Pos.CENTER);
    checkOnlineButton.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

        Thread thread = new Thread() {
          @Override
          public void run() {
            super.run();
            checkOnlineButton.setDisable(true);
            JSONObject checkObjOnline = new JSONObject();
            checkObjOnline.put("action", "checkUsersOnline");
            checkObjOnline.put("username", userName);
            checkObjOnline = client.send_object(checkObjOnline);
            if (checkObjOnline.getBoolean("userOnline")) {
              Platform.runLater(new Runnable() {
                @Override
                public void run() {
                  imageView.setImage(imgOnlineOn);
                  checkOnlineButton.setDisable(false);
                }
              });
            } else {
              Platform.runLater(new Runnable() {
                @Override
                public void run() {
                  imageView.setImage(imgOnlineOff);
                  checkOnlineButton.setDisable(false);
                }
              });
            }
          }
        };
        thread.start();

      }
    });
    return hBox;
  }

}
