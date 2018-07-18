package net.yuvideo.jgemstone.client.Controllers.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.cell.PropertyValueFactory;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NetworkDevices;
import net.yuvideo.jgemstone.client.classes.Settings;
import org.json.JSONObject;

public class Devices implements Initializable {

  private static final int OSTLAO = 0;
  private static final int MIKROTIK = 1;
  public JFXTextField tNaziv;
  public JFXComboBox cmbType;
  public JFXTextField tUserName;
  public JFXPasswordField tPass;
  public JFXTextField tIpHost;
  public JFXTextField tHostName;
  public JFXTextField tURL;
  public JFXTextArea tOpis;
  public JFXButton bSnimi;
  public TableView<NetworkDevices> tblDevices;
  public TableColumn<NetworkDevices, String> cNaziv;
  public TableColumn<NetworkDevices, String> cVrsta;
  public TableColumn<NetworkDevices, String> cIP;
  public TableColumn<NetworkDevices, String> cHostName;
  public TableColumn<NetworkDevices, String> cUserName;
  public TableColumn<NetworkDevices, String> cURL;
  public TableColumn<NetworkDevices, String> cOpis;
  public JFXButton bOsvezi;
  public CheckBox chkNas;
  public JFXComboBox cmbAccessType;
  public Settings LocalSettings;
  private URL location;
  private ResourceBundle resources;
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    cNaziv.setCellValueFactory(new PropertyValueFactory<NetworkDevices, String>("name"));
    cVrsta.setCellValueFactory(new PropertyValueFactory<NetworkDevices, String>("type"));
    cIP.setCellValueFactory(new PropertyValueFactory<NetworkDevices, String>("ip"));
    cHostName.setCellValueFactory(new PropertyValueFactory<NetworkDevices, String>("hostName"));
    cUserName.setCellValueFactory(new PropertyValueFactory<NetworkDevices, String>("userName"));
    cURL.setCellValueFactory(new PropertyValueFactory<NetworkDevices, String>("url"));
    cOpis.setCellValueFactory(new PropertyValueFactory<NetworkDevices, String>("opis"));

    cmbType.getItems().add(0, new String());
    cmbType.getItems().add(1, "Mikrotik");
    cmbType.getItems().add(2, "Ostalo");
    cmbType.getSelectionModel().select(0);

    cmbAccessType.getItems().add(0, "");
    cmbAccessType.getItems().add(1, "API");
    cmbAccessType.getItems().add(2, "SNMP");
    cmbAccessType.getSelectionModel().select(0);

    chkNas.setDisable(true);

    cmbType.valueProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (newValue.toString().trim().equals("Mikrotik")) {
          chkNas.setDisable(false);
        } else {
          chkNas.setDisable(true);
        }
      }
    });

    tblDevices.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<NetworkDevices>() {
          @Override
          public void changed(ObservableValue<? extends NetworkDevices> observable,
              NetworkDevices oldValue,
              NetworkDevices newValue) {
            if (newValue == null) {
              return;
            }

            tNaziv.setText(newValue.getName());
            tUserName.setText(newValue.getUserName());
            tIpHost.setText(newValue.getIp());
            tURL.setText(newValue.getUrl());
            tOpis.setText(newValue.getOpis());
            tHostName.setText(newValue.getHostName());
            tPass.setText(newValue.getPass());

            if (newValue.isNas()) {
              chkNas.setSelected(true);
            } else {
              chkNas.setSelected(false);
            }
            switch (newValue.getType()) {
              case "Mikrotik": {
                cmbType.getSelectionModel().select(1);
                cmbAccessType.getSelectionModel().select(getAccessType(newValue.getAccessType()));

                break;
              }
              case "Ostalo": {
                cmbType.getSelectionModel().select(2);
                cmbAccessType.getSelectionModel().select(getAccessType(newValue.getAccessType()));
                break;
              }
              default: {
                cmbType.getSelectionModel().select(0);
              }

            }

          }
        });


  }

  private int getAccessType(String accessType) {
    switch (accessType) {
      case "API":
        return 1;

      case "SNMP":
        return 2;
      default:
        return 0;
    }
  }

  public void setItems() {
    NetworkDevices networkDevices = new NetworkDevices(this.client);
    networkDevices.initDevices();
    ObservableList netDevs = FXCollections.observableArrayList(networkDevices.getNetworkDevices());
    tblDevices.setItems(netDevs);

  }


  public void saveDevice(ActionEvent actionEvent) {
    if (tNaziv.getText().trim().isEmpty()) {
      return;
    }
    JSONObject obj = new JSONObject();
    obj.put("action", "addNetworkDevice");
    obj.put("name", tNaziv.getText());
    obj.put("ip", tIpHost.getText());
    obj.put("hostName", tHostName.getText());
    obj.put("type", cmbType.getValue());
    obj.put("userName", tUserName.getText());
    obj.put("pass", tPass.getText());
    obj.put("url", tURL.getText());
    obj.put("opis", tOpis.getText());
    obj.put("nas", chkNas.isSelected());
    obj.put("accessType", cmbAccessType.getValue());
    obj = client.send_object(obj);
    if (obj.has("ERROR")) {
      AlertUser.error("GRESKA", obj.getString("ERROR"));
    } else {
      AlertUser
          .info("Uređaj je snimljen", String.format("Uređaj %s je snimljen", tNaziv.getText()));
    }
  }

  public void refresh(ActionEvent actionEvent) {
    setItems();
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void editDevice(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "editNetworkDevice");
    object.put("name", tNaziv.getText());
    object.put("ip", tIpHost.getText());
    object.put("hostName", tHostName.getText());
    object.put("type", cmbType.getValue());
    object.put("userName", tUserName.getText());
    object.put("pass", tPass.getText());
    object.put("url", tURL.getText());
    object.put("opis", tOpis.getText());
    object.put("nas", chkNas.isSelected());
    object.put("accessType", cmbAccessType.getValue());
    object.put("id", tblDevices.getSelectionModel().getSelectedItem().getId());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    AlertUser.info("INFO", "Uređaj je snimljen!");

  }
}
