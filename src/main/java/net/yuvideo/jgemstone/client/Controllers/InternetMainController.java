package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
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
  public JFXButton bOsveziOnline;
  public JFXTreeView treSearch;
  private ResourceBundle resources;
  private URL location;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;




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
  }
}
