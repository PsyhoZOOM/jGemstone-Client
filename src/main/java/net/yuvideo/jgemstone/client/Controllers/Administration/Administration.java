package net.yuvideo.jgemstone.client.Controllers.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javax.jws.soap.SOAPBinding.Use;
import net.yuvideo.jgemstone.client.Controllers.Administration.Search.UserOnlineSearch;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.UsersOnline;
import org.json.JSONObject;

public class Administration implements Initializable {


  public JFXProgressBar prgBarUpdate;
  public Label lNumberOnline;
  public JFXTabPane tabCenter;
  private Client client;


  private int counter = 0;
  private boolean loop = true;
  private ResourceBundle resources;
  private URL location;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
    updateOnlineUsersSum();
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
    JSONObject object = new JSONObject();
    object.put("action", "getMikrotikOnlineUsersSum");
    object = client.send_object(object);
    String onlineSum = object.getString("usersStatus");
    counter = 0;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        lNumberOnline.setText(onlineSum);
      }
    });


  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void updateUsers(ActionEvent actionEvent) {
    updateOnlineUsers();
  }

  public void showOnlineUsers(ActionEvent actionEvent) {
    JFXTreeTableView<UsersOnline> tblOnlineUser = new JFXTreeTableView();

    JFXTreeTableColumn<UsersOnline, String> interfaceName = new JFXTreeTableColumn<>("IME");
    interfaceName.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("interfaceName"));

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

    tblOnlineUser.getColumns().addAll(userName, upTime, MAC, nasNAME, nasIP);


    JSONObject object = new JSONObject();
    object.put("action", "getOnlineUsers");
    object = client.send_object(object);
    System.out.println(object);
    ArrayList<UsersOnline> onlineArrayList = new ArrayList<>();
    for(int i =0; i < object.length(); i++){
      JSONObject onlineU = object.getJSONObject(String.valueOf(i));
      UsersOnline uOnlie  = new UsersOnline();
      uOnlie.setInterfaceName(onlineU.getString("interfaceName"));
      uOnlie.setService(onlineU.getString("service"));
      uOnlie.setUptime(onlineU.getString("uptime"));
      uOnlie.setUserName(onlineU.getString("user"));
      uOnlie.setMac(onlineU.getString("remoteMAC"));
      uOnlie.setNasIP(onlineU.getString("nasIP"));
      uOnlie.setNASName(onlineU.getString("nasName"));
      onlineArrayList.add(uOnlie);
    }

    TreeItem<UsersOnline> root = new RecursiveTreeItem<UsersOnline>(FXCollections.observableArrayList(onlineArrayList), RecursiveTreeObject::getChildren);
    tblOnlineUser.setRoot(root);
    tblOnlineUser.setShowRoot(false);

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
                    usersOnlineTreeItem.getValue().getNASName().contains(search);

          }
        });
      }
    });

    VBox vBox = new VBox();
    vBox.getChildren().addAll(userSearch, tblOnlineUser);



    Tab onlineTab = new Tab("ONLINE KORISNICI");
   onlineTab.setContent(vBox);
   VBox.setVgrow(tblOnlineUser, Priority.ALWAYS);

    tabCenter.getTabs().add(onlineTab);

  }

  public void stopUpdate() {
    this.loop = false;
  }

  public void showSearchUsers(ActionEvent actionEvent) {
  }

  public void showIzvestajPotrosnje(ActionEvent actionEvent) {
  }

  public void showWifiFinder(ActionEvent actionEvent) {
  }
}
