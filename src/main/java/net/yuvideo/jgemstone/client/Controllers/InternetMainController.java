package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.Client;

/**
 * Created by zoom on 4/7/17.
 */
public class InternetMainController implements Initializable {

  public Client client;
  public Stage stage;
  public TreeView tTreeViewMain;
  private ResourceBundle resources;
  private URL location;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;



  }


}
