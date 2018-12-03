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
import javafx.scene.control.MultipleSelectionModel;
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.GroupOpers;
import net.yuvideo.jgemstone.client.classes.Operaters;
import org.json.JSONObject;

public class GroupEdit implements Initializable {

  public JFXListView<GroupOpers> lsGrupe;
  public JFXButton bDelete;
  public JFXListView lsOperateriGrupe;
  public JFXListView<Operaters> lsOperateri;
  private URL location;
  private ResourceBundle resources;
  private Client client;

  private ArrayList<GroupOpers> groupOpersArrayList = new ArrayList<>();
  private ArrayList<Operaters> operatersArrayList;

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
            if (item == null || empty) {
              setText("");
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
            System.out.println(newValue.getGroupName());
            for (Operaters op : newValue.getOperatersArrayList()) {
              System.out.println(op.getIme());
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

  }

  public void setData() {
    JSONObject object = new JSONObject();
    object.put("action", "getGroupOpers");
    object = client.send_object(object);
    Operaters operaters = new Operaters();
    operaters.initData(this.client);
    this.operatersArrayList = operaters.getOperaters();
    setGroupe(object);
    setAvailableOpers(operatersArrayList);

  }

  private void setGroupe(JSONObject object) {
    ArrayList<GroupOpers> groupsArrayList = new ArrayList();
    for (int i = 0; i < object.length(); i++) {
      JSONObject grupa = object.getJSONObject(String.valueOf(i));
      JSONObject operaters = object.getJSONObject(String.valueOf(i)).getJSONObject("operaters");
      GroupOpers groupOpers = new GroupOpers();
      groupOpers.setId(grupa.getInt("id"));
      groupOpers.setGroupName(grupa.getString("groupName"));
      groupsArrayList.add(groupOpers);
      for (int z = 0; z < operaters.length(); z++) {
        JSONObject opers = operaters.getJSONObject(String.valueOf(z));
        for (Operaters op : operatersArrayList) {
          System.out.println(String.format("oper: %d, op: %d",
              operaters.getJSONObject(String.valueOf(z)).getInt("operID"), op.getId()));
          if (opers.getInt("operID") == op.getId()) {
            operatersArrayList.remove(op);
          }
        }

      }
      groupOpers.setOperatersArrayList(operatersArrayList);
    }
    ObservableList observableList = FXCollections.observableArrayList(groupsArrayList);
    lsGrupe.setItems(observableList);


  }

  public void setAvailableOpers(ArrayList<Operaters> oper) {
    ObservableList list = FXCollections.observableArrayList(operatersArrayList);
    lsOperateri.setItems(list);
  }

  public void deleteGroup(ActionEvent actionEvent) {
  }

  public void bPermissions(ActionEvent actionEvent) {
  }

  public void bNovaGrupa(ActionEvent actionEvent) {
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

}
