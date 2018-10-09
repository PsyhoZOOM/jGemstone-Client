package net.yuvideo.jgemstone.client.Controllers.BOX;

import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import net.yuvideo.jgemstone.client.Controllers.DTV.DTVEditController;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.ServicesUser;

public class BOXEditController implements Initializable {

  public JFXTabPane tabBOX;
  private URL location;
  private ResourceBundle resources;
  private Client client;
  private ServicesUser service;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void setService(ServicesUser service) {
    this.service = service;
  }

  public void initData() {
    if (!service.getIdDTVCard().isEmpty()) {
      createTabDTV();
    }
    if (!service.getGroupName().isEmpty()) {
      createTabNET();
    }
  }

  private void createTabNET() {
    Tab tabNET = new Tab("NET");
    tabBOX.getTabs().add(tabNET);
  }

  private void createTabDTV() {
    Tab tabDTV = new Tab("DTV");
    FXMLLoader loader = new FXMLLoader(
        ClassLoader.getSystemResource("fxml/Dtv/DTVEditService.fxml"), resources);
    try {
      loader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    DTVEditController dtvEditController = loader.getController();
    dtvEditController.setService(this.service);
    dtvEditController.setClient(this.client);
    dtvEditController.initData();

    tabDTV.setContent(dtvEditController.getVboxMain());

    tabBOX.getTabs().add(tabDTV);

  }


}
