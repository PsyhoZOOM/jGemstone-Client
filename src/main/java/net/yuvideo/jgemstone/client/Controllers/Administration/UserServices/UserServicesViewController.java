package net.yuvideo.jgemstone.client.Controllers.Administration.UserServices;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTreeView;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.Services;
import net.yuvideo.jgemstone.client.classes.ServicesUser;

public class UserServicesViewController implements Initializable {

  public JFXTabPane tabServices;
  @FXML
  private JFXTreeView<ServicesUser> trViewServices;
  @FXML
  private AnchorPane mainPane;


  private JFXSnackbar jfxSnackbar;


  private URL location;
  private ResourceBundle resources;
  private int userID;
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
    jfxSnackbar = new JFXSnackbar(mainPane);

    trViewServices.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getClickCount() == 2) {
          showService(trViewServices.getSelectionModel().getSelectedItem().getValue());
        }
      }
    });

    trViewServices.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
          showService(trViewServices.getSelectionModel().getSelectedItem().getValue());
        }
      }
    });

  }

  private void showService(ServicesUser selectedItem) {
    //we do not need options if paketType contain BOX
    if (selectedItem.getPaketType().contains("BOX")) {
      return;
    }

    if (selectedItem.getPaketType().contains("DTV")) {
      showDTV(selectedItem, "DTV");
    } else if (selectedItem.getPaketType().contains("NET")) {
      showNET(selectedItem, "NET");
    } else if (selectedItem.getPaketType().contains("IPTV")) {
      showIPTV(selectedItem, "IPTV");
    } else if (selectedItem.getPaketType().contains("BOX")) {
      showBOX(selectedItem, "BOX");
    }
  }

  private void showBOX(ServicesUser selectedItem, String box) {
  }

  private void showIPTV(ServicesUser selectedItem, String iptv) {
    FXMLLoader fxmlLoader = null;
    fxmlLoader = new FXMLLoader(
        ClassLoader.getSystemResource("fxml/Administration/UserServices/UserServicesIPTV.fxml"));
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    UserServicesIPTV iptvInterfaceController = fxmlLoader.getController();
    iptvInterfaceController.setClient(this.client);
    iptvInterfaceController.setService(selectedItem);
    iptvInterfaceController.setSnackBar(this.jfxSnackbar);
    iptvInterfaceController.initData();
    Tab tabIPTV = new Tab(String.format("%s - (%s)", selectedItem.getNaziv(), iptv));
    tabIPTV.setContent(iptvInterfaceController.getBoxMain());
    tabServices.getTabs().add(tabIPTV);
    tabServices.getSelectionModel().select(tabIPTV);
  }

  private void showNET(ServicesUser selectedItem, String net) {
    FXMLLoader fxmlLoader = null;
    fxmlLoader = new FXMLLoader(
        ClassLoader.getSystemResource("fxml/Administration/UserServices/UserServicesNET.fxml"));
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    UserServicesNET netInterfaceController = fxmlLoader.getController();
    netInterfaceController.setClient(this.client);
    netInterfaceController.setService(selectedItem);
    netInterfaceController.setSnackBar(this.jfxSnackbar);
    netInterfaceController.initData();
    Tab tabNET = new Tab(String.format("%s - (%s)", selectedItem.getNaziv(), net));
    tabNET.setContent(netInterfaceController.getBoxMain());
    tabServices.getTabs().add(tabNET);
    tabServices.getSelectionModel().select(tabNET);
  }

  private void showDTV(ServicesUser selectedItem, String dtv) {
    FXMLLoader fxmlLoader = null;
    fxmlLoader = new FXMLLoader(
        ClassLoader.getSystemResource("fxml/Administration/UserServices/UserServicesDTV.fxml"));
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    UserServicesDTV dtvInterfaceController = fxmlLoader.getController();
    dtvInterfaceController.setClient(this.client);
    dtvInterfaceController.setService(selectedItem);
    dtvInterfaceController.setSnackBar(this.jfxSnackbar);
    dtvInterfaceController.initData();
    Tab tabDTV = new Tab(String.format("%s - (%s)", selectedItem.getNaziv(), dtv));
    tabDTV.setContent(dtvInterfaceController.getBoxMain());
    tabServices.getTabs().add(tabDTV);
    tabServices.getSelectionModel().select(tabDTV);
  }


  public void init() {
    ServicesUser servicesUser = new ServicesUser();
    ArrayList<ServicesUser> userServiceArr = servicesUser.getUserServiceArr(getUserID(), client);

    TreeItem<ServicesUser> root = new TreeItem("SERVISI");


    for (ServicesUser userService : userServiceArr) {
      root.getChildren().add(initTreeItems(userService));
    }

    trViewServices.setRoot(root);
    trViewServices.setShowRoot(false);




  }


  private TreeItem<ServicesUser> initTreeItems(ServicesUser service) {
    TreeItem<ServicesUser> serviceItem = new TreeItem<>(service);
    if (service.getBox()) {

      for (ServicesUser subService : service.getBoxServices()) {
        TreeItem<ServicesUser> serviceSubItem = new TreeItem<>(subService);
        serviceSubItem.setValue(subService);
        serviceItem.getChildren().add(serviceSubItem);

      }
    }

    return serviceItem;

  }


  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public void setClient(Client client) {
    this.client = client;
  }

}
