package net.yuvideo.jgemstone.client.Controllers.Administration.DTV;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import org.json.JSONObject;

public class CASEditController implements Initializable {

  public JFXTreeTableView<CAS> tblCASView;
  public JFXButton bDelete;
  public JFXButton bNov;
  public Client client;
  private URL location;
  private ResourceBundle resource;
  private JFXTreeTableColumn<CAS, Integer> code;
  private JFXTreeTableColumn<CAS, Integer> paketID;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resource = resources;
    tblCASView.setEditable(true);
  }

  private void createTable() {
    code = new JFXTreeTableColumn<CAS, Integer>("CODE");
    code.setEditable(true);
    code.setCellValueFactory(new TreeItemPropertyValueFactory<CAS, Integer>("code"));
    code.setOnEditCommit(new EventHandler<CellEditEvent<CAS, Integer>>() {
      @Override
      public void handle(CellEditEvent<CAS, Integer> event) {
        System.out.println(String.format("EDITED: id:%d, oldValue: %d, newValue: %d",
            event.getRowValue().getValue().getId(),
            event.getOldValue(), event.getNewValue()));
        JSONObject object = new JSONObject();
        object.put("action", "updateCASCode");
        object.put("id", event.getRowValue().getValue().getId());
        object.put("code", event.getNewValue());
        object = client.send_object(object);
        if (object.has("ERROR")) {
          AlertUser.error("GRESKA", object.getString("ERROR"));
          event.getRowValue().getValue().setCode(event.getOldValue());
        }

      }
    });
    code.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new StringConverter<Integer>() {
      @Override
      public String toString(Integer obj) {
        return String.valueOf(obj);
      }

      @Override
      public Integer fromString(String string) {
        return Integer.valueOf(string);
      }
    }));

    paketID = new JFXTreeTableColumn<CAS, Integer>("PAKET ID");
    paketID.setEditable(true);
    paketID.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(
        new StringConverter<Integer>() {
          @Override
          public String toString(Integer object) {
            return String.valueOf(object);
          }

          @Override
          public Integer fromString(String string) {
            return Integer.valueOf(string);
          }
        }));
    paketID.setOnEditCommit(new EventHandler<CellEditEvent<CAS, Integer>>() {
      @Override
      public void handle(CellEditEvent<CAS, Integer> event) {
        JSONObject object = new JSONObject();
        object.put("action", "updateCASPaketID");
        object.put("id", event.getRowValue().getValue().getId());
        object.put("paketID", event.getNewValue());
        object = client.send_object(object);
        if (object.has("ERROR")) {
          AlertUser.error("GRESKA", object.getString("ERROR"));
          event.getRowValue().getValue().setPaketID(event.getOldValue());
        }

      }
    });
    paketID.setCellValueFactory(new TreeItemPropertyValueFactory<CAS, Integer>("paketID"));

    tblCASView.getColumns().addAll(code, paketID);
    tblCASView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

    setData();


  }


  private void setData() {
    CAS cas = new CAS();
    ArrayList<CAS> casArrayList = cas.getCasArrayList(client);

    TreeItem<CAS> root = new RecursiveTreeItem<>(FXCollections.observableArrayList(casArrayList),
        RecursiveTreeObject::getChildren);
    tblCASView.setRoot(root);
    tblCASView.setShowRoot(false);


  }


  public void setClient(Client client) {
    this.client = client;
  }

  public void init() {
    createTable();
  }

  public void deleteCAS(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "deleteCAS");
    object.put("id", tblCASView.getSelectionModel().getSelectedItem().getValue().getId());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    }
    setData();
  }

  public void novCas(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "addCAS");
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    }
    setData();
  }
}
