package net.yuvideo.jgemstone.client.Controllers.Administration.Groups;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.GroupOpers;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.Operaters;
import org.json.JSONObject;

public class GroupEdit implements Initializable {

  public JFXListView<GroupOpers> lsGrupe;
  public JFXButton bDelete;
  public JFXListView<Operaters> lsOperateriGrupe;
  public JFXListView<Operaters> lsOperateri;
  private URL location;
  private ResourceBundle resources;
  private Client client;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    lsGrupe.setCellFactory(new Callback<ListView<GroupOpers>, ListCell<GroupOpers>>() {
      @Override
      public ListCell<GroupOpers> call(ListView<GroupOpers> param) {
        return new ListCell<GroupOpers>() {
          @Override
          protected void updateItem(GroupOpers item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
              setText(null);
            } else {
              setText(item.getGroupName());
            }
          }
        };
      }
    });

    lsGrupe.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<GroupOpers>() {
          @Override
          public void changed(ObservableValue<? extends GroupOpers> observable, GroupOpers oldValue,
              GroupOpers newValue) {
            if (newValue != null) {
              setOperatersGroup(newValue.getId());
            }
          }
        });

    lsOperateri.setCellFactory(new Callback<ListView<Operaters>, ListCell<Operaters>>() {
      @Override
      public ListCell<Operaters> call(ListView<Operaters> param) {
        return new ListCell<Operaters>() {
          @Override
          protected void updateItem(Operaters item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
              setText("");
            } else {
              setText(item.getUsername());

            }
          }
        };
      }
    });

    lsOperateriGrupe.setCellFactory(new Callback<ListView<Operaters>, ListCell<Operaters>>() {
      @Override
      public ListCell<Operaters> call(ListView<Operaters> param) {
        return new ListCell<Operaters>() {
          @Override
          protected void updateItem(Operaters item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
              setText(null);
            } else {
              setText(item.getUsername());
            }
          }
        };
      }
    });

  }


  public void setData() {
    JSONObject object = new JSONObject();
    object.put("action", "getGroups");
    object = client.send_object(object);
    setGroupe(object);
    setAvailableOpers();

  }

  private void setGroupe(JSONObject object) {
    ArrayList<GroupOpers> groupOpersArrayList = new ArrayList();
    for (int i = 0; i < object.length(); i++) {
      JSONObject group = object.getJSONObject(String.valueOf(i));
      GroupOpers groupOpers = new GroupOpers();
      groupOpers.setId(group.getInt("id"));
      groupOpers.setGroupName(group.getString("groupName"));
      groupOpersArrayList.add(groupOpers);
    }

    ObservableList grupeObLs = FXCollections.observableArrayList(groupOpersArrayList);
    lsGrupe.setItems(grupeObLs);
  }

  public void setOperatersGroup(int groupID) {
    lsOperateriGrupe.getItems().removeAll();
    lsOperateriGrupe.getItems().clear();
    JSONObject object = new JSONObject();
    object.put("action", "getGroupOperaters");
    object.put("groupID", groupID);
    object = client.send_object(object);
    if (object.length() == 0) {
      return;
    }
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    ArrayList<Operaters> groupOperaters = new ArrayList<>();

    for (int i = 0; i < object.length(); i++) {
      JSONObject operO = object.getJSONObject(String.valueOf(i));
      Operaters oper = new Operaters();
      oper.setId(operO.getInt("id"));
      oper.setUsername(operO.getString("username"));
      groupOperaters.add(oper);
    }

    ObservableList listOperaters = FXCollections.observableArrayList(groupOperaters);
    lsOperateriGrupe.setItems(listOperaters);


  }

  public void setAvailableOpers() {
    JSONObject object = new JSONObject();
    object.put("action", "getAvOpers");
    object = client.send_object(object);
    System.out.println(object.toString());
    ArrayList<Operaters> operaters = new ArrayList<>();
    for (int i = 0; i < object.length(); i++) {
      JSONObject oper = object.getJSONObject(String.valueOf(i));
      Operaters operO = new Operaters();
      operO.setId(oper.getInt("id"));
      operO.setUsername(oper.getString("username"));
      operaters.add(operO);
    }

    ObservableList observableListOpers = FXCollections.observableArrayList(operaters);
    lsOperateri.setItems(observableListOpers);


  }

  public void deleteGroup(ActionEvent actionEvent) {
  }

  public void bPermissions(ActionEvent actionEvent) {
  }

  public void bNovaGrupa(ActionEvent actionEvent) {
    NewInterface newGroupInterface = new NewInterface("fxml/Administration/Groups/groupNew.fxml",
        "Nova grupa", this.resources);
    GroupNew groupNew = newGroupInterface.getLoader().getController();
    groupNew.setClient(this.client);
    newGroupInterface.getStage().showAndWait();
    setData();

  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void addOperToGroup(ActionEvent actionEvent) {
    if (lsOperateri.getSelectionModel().getSelectedIndex() == -1)
      return;
    if (lsGrupe.getSelectionModel().getSelectedIndex() == -1)
      return;

    Operaters selectedOper = lsOperateri.getSelectionModel().getSelectedItem();
    GroupOpers selectedGroip = lsGrupe.getSelectionModel().getSelectedItem();

    JSONObject object = new JSONObject();
    object.put("action", "addOperToGroup");
    object.put("groupID", selectedGroip.getId());
    object.put("operID", selectedOper.getId());

    object = client.send_object(object);
    if (object.has("ERROR"))
      AlertUser.error("GRESKA", object.getString("ERROR"));

    setData();

    lsGrupe.getSelectionModel().select(selectedGroip);


  }

  public void removeOperFromGroup(ActionEvent actionEvent) {
    if (lsOperateriGrupe.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }
    JSONObject object = new JSONObject();
    int operID = lsOperateriGrupe.getSelectionModel().getSelectedItem().getId();
    object.put("action", "removeOperFromGroup");
    object.put("operID", operID);
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    }
    lsOperateriGrupe.getItems().removeAll(lsOperateriGrupe.getSelectionModel().getSelectedItem());

    setData();
  }
}
