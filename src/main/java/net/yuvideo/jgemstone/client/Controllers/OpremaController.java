package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Oprema;
import org.controlsfx.control.Notifications;
import org.json.JSONObject;


/**
 * Created by zoom on 1/13/17.
 */
public class OpremaController implements Initializable {

  public TextField tNaziv;
  public TextField tModel;
  public Button bSnimi;
  public TableView tblOprema;
  public TableColumn colNaziv;
  public TableColumn colModel;
  public Button bIzbrisi;
  public Button bClose;
  public Client client;
  private ResourceBundle resource;
  private URL location;
  private JSONObject jObj;
  private Oprema oprema;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.resource = resources;
    this.location = location;

    colModel.setCellValueFactory(new PropertyValueFactory<Oprema, String>("model"));
    colNaziv.setCellValueFactory(new PropertyValueFactory<Oprema, String>("naziv"));
  }

  public void snimiData(ActionEvent actionEvent) {
    if (tModel.getText().isEmpty() || tNaziv.getText().isEmpty()) {
      Notifications.create()
          .title("Upozorenje")
          .text("Naziv ili Model polja ne mogu biti prazna")
          .position(Pos.BOTTOM_RIGHT)
          .hideAfter(Duration.seconds(6))
          .showWarning();
      return;
    }
    jObj = new JSONObject();
    jObj.put("action", "ADD_OPREMA");
    jObj.put("naziv", tNaziv.getText());
    jObj.put("model", tModel.getText());

    jObj = client.send_object(jObj);

    refresh_table();
  }

  public void deleteData(ActionEvent actionEvent) {
    if (tblOprema.getSelectionModel().getSelectedIndex() == -1) {
      Notifications.create()
          .title("Upozorenje")
          .text("Nije izabran nijedan podatak za brisanje!")
          .hideAfter(Duration.seconds(6))
          .position(Pos.BOTTOM_RIGHT)
          .showWarning();
      return;
    }
    oprema = (Oprema) tblOprema.getSelectionModel().getSelectedItem();
    jObj = new JSONObject();
    jObj.put("action", "DEL_OPREMA");
    jObj.put("id", oprema.getId());

    jObj = client.send_object(jObj);

    refresh_table();

  }

  public void closeWin(ActionEvent actionEvent) {
    Stage stage = (Stage) bClose.getScene().getWindow();
    stage.close();

  }


  public void refresh_table() {
    ObservableList<Oprema> oprema = FXCollections.observableArrayList(get_oprema());
    tblOprema.setItems(oprema);
  }


  private ArrayList<Oprema> get_oprema() {
    jObj = new JSONObject();
    jObj.put("action", "GET_OPREMA");

    jObj = client.send_object(jObj);
    if (jObj.has("Message")) {
      return null;
    }

    ArrayList<Oprema> opremeArr = new ArrayList<>();
    JSONObject opremJob;
    for (int i = 0; i < jObj.length(); i++) {
      opremJob = (JSONObject) jObj.get(String.valueOf(i));
      oprema = new Oprema();
      oprema.setId(opremJob.getInt("id"));
      oprema.setNaziv(opremJob.getString("naziv"));
      oprema.setModel(opremJob.getString("model"));
      opremeArr.add(oprema);
    }
    return opremeArr;
  }


}
