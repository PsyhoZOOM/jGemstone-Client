package net.yuvideo.jgemstone.client.Controllers.Administration.UserServices;

import com.jfoenix.controls.JFXTabPane;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.VBox;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Services;
import net.yuvideo.jgemstone.client.classes.ServicesUser;

public class TabUserServices implements Initializable {

  public JFXTabPane tabPaneServices;
  private URL location;
  private ResourceBundle resources;
  private int userID;
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

  }

  public void init() {
    ServicesUser servicesUser = new ServicesUser();
    ArrayList<ServicesUser> userServiceArr = servicesUser.getUserServiceArr(getUserID(), client);
    for (ServicesUser userService : userServiceArr) {
      tabPaneServices.getTabs().add(initTab(userService));
    }
  }


  private Tab initTab(ServicesUser service) {
    Label lab = new Label(service.getNazivPaketa());
    Tab tabService = new Tab(service.getNaziv());
    if (service.getBox()) {

      JFXTabPane tabSub = new JFXTabPane();
      for (ServicesUser subService : service.getBoxServices()) {
        Tab tabSubService = new Tab(subService.getNaziv());
        VBox suVbox = new VBox();
        Label usLab = new Label(subService.getNazivPaketa());
        suVbox.getChildren().add(usLab);
        tabSubService.setContent(suVbox);
        tabSub.getTabs().add(tabSubService);
      }
      tabService.setContent(tabSub);
    } else {
      VBox vBox = new VBox();
      vBox.getChildren().add(lab);
      tabService.setContent(vBox);
    }
    return tabService;

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
