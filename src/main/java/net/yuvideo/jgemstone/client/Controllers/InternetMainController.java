package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.JFXTreeView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
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
import javafx.scene.control.TablePosition;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.UsersOnline;
import org.json.JSONObject;

/**
 * Created by zoom on 4/7/17.
 */
public class InternetMainController implements Initializable {

  public Client client;
  public Stage stage;
  public JFXButton bOsveziOnline;
  public JFXTreeView<UsersOnline> treSearch;
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


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;


  }

  private void setUserInfoData(TreeItem<UsersOnline> userData) {
    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        JSONObject object = new JSONObject();
        object.put("action", "getUsersOnlineStat");
        object.put("interfaceName", userData.getValue().getIdentification());
        object.put("nasIP", userData.getValue().getNasIP());
        object.put("ip", userData.getValue().getIp());

        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            JFXSnackbar snackbar = new JFXSnackbar(bPane);
            snackbar.show("Loading user data..", 5000);

          }
        });
        object = client.send_object(object);

        JSONObject finalObject1 = object;
        Platform.runLater(new Runnable() {
          @Override
          public void run() {

            ltxByte.setText(finalObject1.getString("txByte"));
            lrxByte.setText(finalObject1.getString("rxByte"));
            lLinkUp.setText(finalObject1.getString("linkUp"));
            ltxError.setText(finalObject1.getString("txError"));
            lrxError.setText(finalObject1.getString("rxError"));
            lInterfaceName.setText(finalObject1.getString("name"));
            lNASIP.setText(finalObject1.getString("NAS"));
            Image img = new Image(ClassLoader.getSystemResourceAsStream("icons/green-light.png"));
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
  }


  public void refreshOnline(ActionEvent actionEvent) {

    JFXSnackbar snackbar = new JFXSnackbar(bPane);

    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        JSONObject object = new JSONObject();
        object.put("action", "getOnlineUsers");
        object = client.send_object(object);
        JSONObject finalObject = object;
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
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

            setUserInfoData(newValue);


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
    JFXTreeTableColumn<UsersOnline, String> cName = new JFXTreeTableColumn<>("Name");
    JFXTreeTableColumn<UsersOnline, String> cMac = new JFXTreeTableColumn<>("MAC");
    JFXTreeTableColumn<UsersOnline, String> cIP = new JFXTreeTableColumn<>("IP");
    JFXTreeTableColumn<UsersOnline, String> cUptime = new JFXTreeTableColumn<>("UpTime");
    JFXTreeTableColumn<UsersOnline, String> cService = new JFXTreeTableColumn<>("Service");
    JFXTreeTableColumn<UsersOnline, String> cSessionID = new JFXTreeTableColumn<>("Session ID");

    cBroj.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, Integer>("broj"));
    cName.setCellValueFactory(
        new TreeItemPropertyValueFactory<UsersOnline, String>("identification"));
    cIP.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("ip"));
    cMac.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("mac"));
    cUptime.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("uptime"));
    cService.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("service"));
    cSessionID
        .setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("sessionID"));

    tblOnlineUSers.getColumns().addAll(cBroj, cName, cMac, cIP, cUptime, cService, cSessionID);
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
}
