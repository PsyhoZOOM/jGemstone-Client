package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.IntegerTextFieldEditorBuilder;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTablePosition;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.classes.Administration.RadiusUsers;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.BytesTo_KB_MB_GB_TB;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Settings;
import net.yuvideo.jgemstone.client.classes.UsersOnline;
import org.json.JSONObject;

/**
 * Created by zoom on 4/7/17.
 */
public class InternetMainController implements Initializable {

  public Settings LocalSettings;
  public Stage stage;
  public JFXButton bOsveziOnline;
  public JFXTreeView<String> treSearch;
  public Label lOnlineUsers;
  public JFXTabPane tabMiddle;
  public BorderPane bPane;
  public Label lLinkUp;
  public Label ltxByte;
  public Label lrxByte;
  public Label ltxError;
  public Label lrxError;
  public ImageView imgOnline;
  public Label lInterfaceName;
  public Label lNASIP;
  private ResourceBundle resources;
  private URL location;
  private Client client;
  private JFXSnackbar snackbar;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;


  }

  private void setUserInfoData(UsersOnline userData) {
    Task<Void> task = new Task<Void>() {

      @Override
      protected Void call() throws Exception {
        JSONObject object = new JSONObject();
        object.put("action", "getUsersOnlineStat");
        object.put("interfaceName", userData.getIdentification());
        object.put("nasIP", userData.getNasIP());
        object.put("ip", userData.getIp());

        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            JFXSnackbar snackbar = new JFXSnackbar(bPane);
            snackbar.show("Loading user data..", 5000);

          }
        });
        Client clientO = new Client(client.getLocal_settings());
        object = clientO.send_object(object);

        JSONObject finalObject1 = object;
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            Image img;
            if (finalObject1.has("ERROR") || finalObject1.has("USER_OFFLINE")) {
              snackbar = new JFXSnackbar(bPane);
              if (finalObject1.has("ERROR")) {
                snackbar.show(finalObject1.getString("ERROR"), 5000);
              }
              img = new Image(ClassLoader.getSystemResourceAsStream("icons/red-light.png"));
              imgOnline.setImage(img);
              ltxByte.setText("");
              lrxByte.setText("");
              lLinkUp.setText("");
              ltxError.setText("");
              lrxError.setText("");
              if (finalObject1.has("USER_OFFLINE")) {
                lInterfaceName.setText(userData.getIdentification() + " oFFlInE");
              } else {
                lInterfaceName
                    .setText(userData.getIdentification() + "\n" + finalObject1.getString("ERROR"));
              }
              lNASIP.setText("");
              return;
            }

            String tx = BytesTo_KB_MB_GB_TB
                .getFormatedString(Long.parseLong(finalObject1.getString("txByte")));
            String rx = BytesTo_KB_MB_GB_TB
                .getFormatedString(Long.parseLong(finalObject1.getString("rxByte")));

            ltxByte.setText(String.format("%s (%s)", finalObject1.getString("txByte"), tx));
            lrxByte.setText(String.format("%s (%s)", finalObject1.getString("rxByte"), rx));
            lLinkUp.setText(finalObject1.getString("linkUp"));
            ltxError.setText(finalObject1.getString("txError"));
            lrxError.setText(finalObject1.getString("rxError"));
            lInterfaceName.setText(finalObject1.getString("name"));
            lNASIP.setText(finalObject1.getString("NAS"));
            img = new Image(ClassLoader.getSystemResourceAsStream("icons/green-light.png"));
            imgOnline.setImage(img);

          }
        });

        return null;
      }
    };
    new Thread(task).start();


  }

  public void setItems() {
    TreeItem internet = new TreeItem("Internet");
    TreeItem iptv = new TreeItem("IPTv");
    TreeItem dtv = new TreeItem("DTV");
    TreeItem root = new TreeItem("");
    root.getChildren().addAll(internet, iptv, dtv);
    root.setExpanded(true);
    treSearch.setRoot(root);
    treSearch.setShowRoot(false);

    treSearch.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getClickCount() == 2) {
          //TO-DO
          if (treSearch.getSelectionModel().getSelectedItem().getValue().equals("Internet")) {
            ;
          }
        }

      }
    });

  }


  private void showTabInternetSearch() {
    Tab tabInternet = new Tab("Pretraga korisicko ime");
    VBox vBox = new VBox();
    HBox hBox = new HBox();
    JFXTreeTableView<RadiusUsers> tblRadiusUser = new JFXTreeTableView();
    JFXButton bSearchRadius = new JFXButton("Traži");
    JFXTextField tSerachBox = new JFXTextField();
    JFXTreeTableColumn<RadiusUsers, Integer> cBr = new JFXTreeTableColumn<>("br");
    JFXTreeTableColumn<RadiusUsers, Integer> cID = new JFXTreeTableColumn<>("ID");
    JFXTreeTableColumn<RadiusUsers, String> cUsername = new JFXTreeTableColumn<>("USERNAME");
    JFXTreeTableColumn cAction = new JFXTreeTableColumn("AKCIJE");
    Callback<TreeTableColumn<RadiusUsers, String>, TableCell<RadiusUsers, String>> cellFactor =
        new Callback<TreeTableColumn<RadiusUsers, String>, TableCell<RadiusUsers, String>>() {
          @Override
          public TableCell<RadiusUsers, String> call(TreeTableColumn<RadiusUsers, String> param) {
            final TableCell<RadiusUsers, String> cell = new TableCell<RadiusUsers, String>() {
              final JFXButton butAct = new JFXButton("Prikaži Online");

              @Override
              protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setGraphic(null);
                  setText(null);
                } else {
                  butAct.setOnAction(event -> {
                    RadiusUsers user = tblRadiusUser.getSelectionModel().getSelectedItem()
                        .getValue();
                    showTabInternetSearch();
                  });
                }
              }
            };
            return null;
          }
        };

    cBr.setCellValueFactory(new TreeItemPropertyValueFactory<>("br"));
    cBr.setMinWidth(20);
    cBr.setPrefWidth(40);
    cBr.setMaxWidth(40);
    cID.setMinWidth(20);
    cID.setPrefWidth(40);
    cID.setMaxWidth(40);
    cID.setCellValueFactory(new TreeItemPropertyValueFactory<>("id"));
    cUsername
        .setCellValueFactory(new TreeItemPropertyValueFactory<RadiusUsers, String>("username"));
    tblRadiusUser.getColumns().addAll(cBr, cID, cUsername);
    tblRadiusUser.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

    tSerachBox.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        tblRadiusUser.setPredicate(new Predicate<TreeItem<RadiusUsers>>() {
          @Override
          public boolean test(TreeItem<RadiusUsers> radiusUsersTreeItem) {
            boolean flag = radiusUsersTreeItem.getValue().getUsername().contains(newValue);
            return flag;
          }
        });
      }
    });

    bSearchRadius.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        showRadReplyUsers(tblRadiusUser, tSerachBox.getText());
      }
    });

    hBox.getChildren().addAll(tSerachBox, bSearchRadius);
    vBox.getChildren().addAll(hBox, tblRadiusUser);

    tabInternet.setContent(vBox);
    tabMiddle.getTabs().add(tabInternet);
    tabMiddle.getSelectionModel().select(tabInternet);
  }

  private void showRadReplyUsers(JFXTreeTableView<RadiusUsers> tblRadiusUser, String userSearch) {
    JSONObject object = new JSONObject();
    object.put("action", "getRadReplyUsers");
    object.put("userSearch", userSearch);
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    TreeItem<RadiusUsers> root;
    ArrayList<RadiusUsers> radiusUsers = new ArrayList<>();
    for (int i = 0; i < object.length(); i++) {
      JSONObject user = object.getJSONObject(String.valueOf(i));
      RadiusUsers radUser = new RadiusUsers();
      radUser.setBr(i + 1);
      radUser.setId(user.getInt("id"));
      radUser.setAttribute(user.getString("attribute"));
      radUser.setUsername(user.getString("username"));
      radUser.setValue(user.getString("value"));
      radiusUsers.add(radUser);
    }
    ObservableList<RadiusUsers> userOb = FXCollections.observableArrayList(radiusUsers);
    root = new RecursiveTreeItem<>(userOb, RecursiveTreeObject::getChildren);
    for (RadiusUsers user : radiusUsers) {
      TreeItem<RadiusUsers> treeItem = new TreeItem<>(user);
      root.getChildren().add(treeItem);
    }
    tblRadiusUser.setRoot(root);
    tblRadiusUser.setShowRoot(false);

  }


  public void refreshOnline(ActionEvent actionEvent) {

    JFXSnackbar snackbar = new JFXSnackbar(bPane);

    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        JSONObject object = new JSONObject();
        object.put("action", "getOnlineUsers");
        Client clientO = new Client(client.getLocal_settings());
        object = clientO.send_object(object);
        JSONObject finalObject = object;
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            if (finalObject.has("ERROR")) {
              JFXSnackbar snackbar1 = new JFXSnackbar();
              snackbar1.show(finalObject.getString("ERROR"), 10);

            }
            setTable(finalObject);

          }
        });
        return null;
      }
    };
    new Thread(task).start();

    snackbar.show("Loading users", 3000);

  }

  private void setTable(JSONObject object) {
    lOnlineUsers.setText(String.valueOf(object.length()));
    JFXTreeTableView<UsersOnline> tblOnlineUSers = new JFXTreeTableView<>();

    tblOnlineUSers.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<TreeItem<UsersOnline>>() {
          @Override
          public void changed(ObservableValue<? extends TreeItem<UsersOnline>> observable,
              TreeItem<UsersOnline> oldValue, TreeItem<UsersOnline> newValue) {

            setUserInfoData(newValue.getValue());


          }
        });
    tblOnlineUSers.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
          if (tblOnlineUSers.getSelectionModel().getSelectedIndex() == -1) {
            return;
          }

          TreeTablePosition pos = tblOnlineUSers.getSelectionModel().getSelectedCells().get(0);
          int row = pos.getRow();
          TreeTableColumn col = pos.getTableColumn();
          if (col == null) {
            return;
          }
          String s = col.getCellObservableValue(row).getValue().toString();
          Clipboard clipboard = Clipboard.getSystemClipboard();
          ClipboardContent clipboardContent = new ClipboardContent();
          clipboardContent.putString(s);
          clipboard.setContent(clipboardContent);
          JFXSnackbar snackbar = new JFXSnackbar(bPane);
          snackbar.show(String.format("%s je koprian u klipboardu", s), 1000);
        }
      }
    });

    JFXTreeTableColumn<UsersOnline, Integer> cBroj = new JFXTreeTableColumn<>("br.");
    JFXTreeTableColumn<UsersOnline, String> cName = new JFXTreeTableColumn<>("Korisničko ime");
    JFXTreeTableColumn<UsersOnline, String> cMac = new JFXTreeTableColumn<>("MAC");
    JFXTreeTableColumn<UsersOnline, String> cIP = new JFXTreeTableColumn<>("IP");
    JFXTreeTableColumn<UsersOnline, String> cNASName = new JFXTreeTableColumn<>("NAS");
    JFXTreeTableColumn<UsersOnline, String> cUptime = new JFXTreeTableColumn<>("UpTime");
    JFXTreeTableColumn<UsersOnline, String> cService = new JFXTreeTableColumn<>("Service");
    JFXTreeTableColumn<UsersOnline, String> cSessionID = new JFXTreeTableColumn<>("Session ID");

    cBroj.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, Integer>("broj"));
    cName.setCellValueFactory(
        new TreeItemPropertyValueFactory<UsersOnline, String>("identification"));
    cIP.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("ip"));
    cNASName.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("NASName"));

    cMac.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("mac"));
    cUptime.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("uptime"));
    cService.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("service"));
    cSessionID
        .setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("sessionID"));

    tblOnlineUSers.getColumns()
        .addAll(cBroj, cName, cMac, cIP, cNASName, cUptime, cService, cSessionID);
    TreeItem<UsersOnline> root;
    ObservableList<UsersOnline> users = FXCollections.observableArrayList();
    for (int i = 0; i < object.length(); i++) {
      UsersOnline usersOnline = new UsersOnline();
      usersOnline.setOnline(true);
      usersOnline.setBroj(i);
      usersOnline.setIme(object.getJSONObject(String.valueOf(i)).getString("name"));
      usersOnline.setIdentification(object.getJSONObject(String.valueOf(i)).getString("name"));
      usersOnline.setMac(object.getJSONObject(String.valueOf(i)).getString("MAC"));
      usersOnline.setUptime(object.getJSONObject(String.valueOf(i)).getString("uptime"));
      usersOnline.setService(object.getJSONObject(String.valueOf(i)).getString("service"));
      usersOnline.setIp(object.getJSONObject(String.valueOf(i)).getString("ip"));
      usersOnline.setNASName(object.getJSONObject(String.valueOf(i)).getString("NASName"));
      usersOnline.setNasIP(object.getJSONObject(String.valueOf(i)).getString("nasIP"));
      usersOnline.setSessionID(object.getJSONObject(String.valueOf(i)).getString("sessionID"));
      users.add(usersOnline);
    }

    root = new RecursiveTreeItem<>(users, RecursiveTreeObject::getChildren);
    for (UsersOnline us : users) {
      TreeItem<UsersOnline> treeItem = new TreeItem<>(us);
      root.getChildren().add(treeItem);
    }
    tblOnlineUSers.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    tblOnlineUSers.setTableMenuButtonVisible(true);

    tblOnlineUSers.setRoot(root);
    tblOnlineUSers.setShowRoot(false);
    tabMiddle.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);

    Tab tabUsers = new Tab("Online Users");
    JFXTextField userSerach = new JFXTextField();
    userSerach.setLabelFloat(true);
    userSerach.textProperty().addListener((observable, oldvalue, newValue) -> {
      tblOnlineUSers.setPredicate(new Predicate<TreeItem<UsersOnline>>() {
        @Override
        public boolean test(TreeItem<UsersOnline> usersOnlineTreeItem) {
          tblOnlineUSers.getSelectionModel().clearSelection();
          boolean flag = usersOnlineTreeItem.getValue().getIdentification().contains(newValue) ||
              usersOnlineTreeItem.getValue().getIp().contains(newValue) ||
              usersOnlineTreeItem.getValue().getMac().contains(newValue) ||
              usersOnlineTreeItem.getValue().getService().contains(newValue);
          return flag;
        }
      });
    });
    VBox vBox = new VBox(20);
    vBox.getChildren().add(userSerach);
    userSerach.setPromptText("Traži korisnika");
    userSerach.setPadding(new Insets(20, 5, 5, 5));

    vBox.getChildren().add(tblOnlineUSers);
    VBox.setVgrow(tblOnlineUSers, Priority.ALWAYS);
    vBox.setFillWidth(true);

    tabUsers.setContent(vBox);
    tabMiddle.getTabs().add(tabUsers);
    tabMiddle.getSelectionModel().select(tabUsers);


  }

  public void customCMD(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    String cmd = "/interface/monitor-traffic interface='<pppoe-zlatnik>' once return tx-bits-per-second,rx-bits-per-second";
    object.put("action", "customMTAPICommand");
    object.put("cmd", cmd);
    object.put("nasIP", "10.1.20.2");
    object.put("user", "apiUser");
    object.put("pass", "apiPass");
    object = client.send_object(object);
    for (int i = 0; i < object.length(); i++) {
      System.out.println(object.getJSONObject(String.valueOf(i)));
    }
  }

  public void setClient(Client client) {
    this.client = new Client(client.getLocal_settings());
  }
}
