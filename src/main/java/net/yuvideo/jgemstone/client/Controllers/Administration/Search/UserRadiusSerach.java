package net.yuvideo.jgemstone.client.Controllers.Administration.Search;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import net.yuvideo.jgemstone.client.classes.Client;

public class UserRadiusSerach implements Initializable {

  private URL location;
  private ResourceBundle resources;
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
