package net.yuvideo.jgemstone.client.Controllers.Administration.Groups;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXClippedPane;
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
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.GroupOpers;
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

  private Operaters operaters = new Operaters();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    lsGrupe.setCellFactory(new Callback<ListView<GroupOpers>, ListCell<GroupOpers>>() {
      @Override
      public ListCell<GroupOpers> call(ListView<GroupOpers> param) {
        return new ListCell<GroupOpers>(){
          @Override
          protected void updateItem(GroupOpers item, boolean empty) {
            super.updateItem(item, empty);
            if(empty || item == null){
              setText(null);
            }else{
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
            setOperaterGroupList(newValue.getGroupName());
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
        return new ListCell<Operaters>(){
          @Override
          protected void updateItem(Operaters item, boolean empty) {
            super.updateItem(item, empty);
            if(empty || item == null){
              setText(null);
            }else{
              setText(item.getUsername());
            }
          }
        };
      }
    });

  }

  private void setOperaterGroupList(String groupName) {
    JSONObject object = new JSONObject();
    object.put("action", "getGroupOperaters");
    object.put("groupName", groupName );
    object = client.send_object(object);

    ArrayList<Operaters> operatersArrayList = new ArrayList<>();


    for (int i =0; i < object.length(); i++){
      Operaters opers = new Operaters();
      JSONObject oper = object.getJSONObject(String.valueOf(i));
      opers.setId(oper.getInt("id"));
      opers.setUsername(oper.getString("username"));
      operatersArrayList.add(opers);
    }

    ObservableList op = FXCollections.observableArrayList(operatersArrayList);
    lsOperateriGrupe.setItems(op);



  }

  private void removeAvOper(Operaters opers) {
    ObservableList<Operaters> items = lsOperateri.getItems();
        for (Operaters ps : lsOperateri.getItems()) {
          if(ps.getId() == opers.getId()) {
            items.remove(ps);
          }
        }
        lsOperateri.setItems(items);
  }


  public void setData() {
    JSONObject object = new JSONObject();
    object.put("action", "getGroupOpers");
    object = client.send_object(object);
    operaters.initData(this.client);
    setGroupe(object);
    setAvailableOpers(operaters.getOperaters());


  }

  private void setGroupe(JSONObject object) {
    ArrayList<GroupOpers> groupOpersArrayList = new ArrayList();
    for (int i =0; i < object.length(); i++){
      JSONObject group =  object.getJSONObject(String.valueOf(i));
      GroupOpers groupOpers = new GroupOpers();
      groupOpers.setId(group.getInt("id"));
      groupOpers.setGroupName(group.getString("groupName"));
      for (int z=0; z<group.getJSONObject("operaters").length(); z++){
        JSONObject op = group.getJSONObject("operaters");
        for(int y=0; y<op.length();y++) {
          Operaters operaters = new Operaters();
          JSONObject op1 = op.getJSONObject(String.valueOf(y));
          operaters.setId(op1.getInt("operID"));
          operaters.setUsername(op1.getString("operName"));
          groupOpers.getOperatersArrayList().add(operaters);
        }
      }
      groupOpersArrayList.add(groupOpers);
    }

    ObservableList grupeObLs = FXCollections.observableArrayList(groupOpersArrayList);
    lsGrupe.setItems(grupeObLs);


  }

  public void setAvailableOpers(ArrayList<Operaters> oper) {
    ObservableList opers = FXCollections.observableArrayList(oper);
    lsOperateri.setItems(opers);
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

  public void addOperToGroup(ActionEvent actionEvent) {
    if(lsOperateri.getSelectionModel().getSelectedIndex() == -1)
      return;
    if(lsGrupe.getSelectionModel().getSelectedIndex() == -1)
      return;

    Operaters selectedOper = lsOperateri.getSelectionModel().getSelectedItem();
    GroupOpers selectedGroip = lsGrupe.getSelectionModel().getSelectedItem();


    JSONObject object = new JSONObject();
    object.put("action", "addOperToGroup");
    object.put("groupID", selectedGroip.getId());
    object.put("groupName", selectedGroip.getGroupName());
    object.put("operID", selectedOper.getId());

    object = client.send_object(object);
    if(object.has("ERROR"))
      AlertUser.error("GRESKA", object.getString("ERROR"));

    setData();




  }

  public void removeOperFromGroup(ActionEvent actionEvent) {
  }
}
