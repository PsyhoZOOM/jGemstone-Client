package net.yuvideo.jgemstone.client.Controllers.Administration.Search;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.UsersOnline;
import org.json.JSONObject;

public class UserOnlineSearch implements Initializable {

  public JFXTreeTableView treeTableUsers;
  public AnchorPane anchorMain;
  private Client client;
  JFXTreeTableColumn<UsersOnline, String> cIdentification;
  JFXTreeTableColumn<UsersOnline, String> cIme;
  JFXTreeTableColumn<UsersOnline, String> cAdresa;
  JFXTreeTableColumn<UsersOnline, String> cMesto;
  JFXTreeTableColumn<UsersOnline, Boolean> cOnline;
  @FXML
  private JFXTextField tUserSearch;
  private URL location;
  private ResourceBundle resources;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    cIdentification.setCellValueFactory(
        new TreeItemPropertyValueFactory<UsersOnline, String>("identification"));
    cIme.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("ime"));
    cAdresa
        .setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("adresaUsluge"));
    cMesto
        .setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, String>("mestoUsluge"));
    cOnline.setCellValueFactory(new TreeItemPropertyValueFactory<UsersOnline, Boolean>("online"));

    treeTableUsers.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<TreeItem<UsersOnline>>() {
          @Override
          public void changed(ObservableValue<? extends TreeItem<UsersOnline>> observable,
              TreeItem<UsersOnline> oldValue, TreeItem<UsersOnline> newValue) {
            showTab(newValue);
          }
        });
  }

  private void showTab(TreeItem<UsersOnline> newValue) {
  }

  public void searchUser(ActionEvent actionEvent) {
    if (tUserSearch.getText().length() < 4) {
      return;
    }

    ArrayList<UsersOnline> users = getUser(tUserSearch.getText().trim());
    TreeItem<UsersOnline> root = new TreeItem<>();

    for (UsersOnline user : users) {

      root.getChildren().add(new TreeItem<UsersOnline>(user));
    }
    root.setExpanded(true);
    treeTableUsers.setRoot(root);
    treeTableUsers.setShowRoot(false);

  }

  private ArrayList<UsersOnline> getUser(String trim) {
    JSONObject obj = new JSONObject();
    obj.put("action", "getOnlineUsers");
    obj = client.send_object(obj);

    if (obj.length() < 1) {
      return null;
    }

    ArrayList<UsersOnline> onlineArrayList = new ArrayList<>();
    for (int i = 0; i < obj.length(); i++) {
      JSONObject ob = obj.getJSONObject(String.valueOf(i));
      UsersOnline usersOnline = new UsersOnline();
      usersOnline.setIdentification(ob.getString("identification"));
      usersOnline.setIme(ob.getString("ime"));
      usersOnline.setAdresaUsluge(ob.getString("adresaUsluge"));
      usersOnline.setMestoUsluge(ob.getString("mestoUsluge"));
      usersOnline.setOnline(ob.getBoolean("online"));
      onlineArrayList.add(usersOnline);
    }
    return onlineArrayList;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}

