package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import net.yuvideo.jgemstone.client.classes.Adrese;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Mesta;
import org.json.JSONObject;

/**
 * Created by zoom on 1/10/17.
 */
public class MestaController implements Initializable {

  public Client client;

  public TextField tMestoNaziv;
  public TextField tMestoBroj;
  public Button bAddMesto;
  public TableView tblMesto;
  public TableColumn colMesto;
  public TableColumn colMestoBroj;
  public Button bMestoDelete;
  public Button bMestoRefresh;
  public TextField tAdresaNaziv;
  public TextField tAdresaBroj;
  public Button bAddAdress;
  public TableView tblAdrese;
  public TableColumn colAdresa;
  public TableColumn colAdresaBroj;
  public Button bAdresaDelete;
  public Button bAdresaRefresh;
  private JSONObject jObj;
  private Mesta mesta;
  private Adrese adrese;
  private ArrayList<Mesta> arrMesta;
  private ArrayList<Adrese> arrAdrese;
  private Alert alert;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    colAdresa.setCellValueFactory(new PropertyValueFactory<Adrese, String>("nazivAdrese"));
    colAdresaBroj.setCellValueFactory(new PropertyValueFactory<Adrese, Integer>("brojAdrese"));
    colMesto.setCellValueFactory(new PropertyValueFactory<Mesta, String>("nazivMesta"));
    colMestoBroj.setCellValueFactory(new PropertyValueFactory<Mesta, Integer>("brojMesta"));

    tblMesto.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
      @Override
      public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        if (tblMesto.getSelectionModel().getSelectedIndex() != -1) {
          osveziAdresu(null);
        }
      }
    });


  }

  @FXML
  private void dodajMesto(ActionEvent actionEvent) {
    jObj = new JSONObject();
    jObj.put("action", "addMesto");
    jObj.put("nazivMesta", tMestoNaziv.getText());
    jObj.put("brojMesta", tMestoBroj.getText());

    jObj = client.send_object(jObj);
    osveziMesto(null);
  }


  public void izbrisiMesto(ActionEvent actionEvent) {
    if (tblMesto.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.warrning("Greska", "Nije izabrano mesto za brisanje");
      return;
    }

    ButtonType bYes = new ButtonType("Da", ButtonBar.ButtonData.YES);
    ButtonType bNo = new ButtonType("Ne", ButtonBar.ButtonData.NO);

    alert = new Alert(Alert.AlertType.CONFIRMATION, "Izbrisano mesto ce takodje izbrisati " +
        "sve ulice vezane za to mesto", bYes, bNo);

    alert.setTitle("Upozorenje");
    alert.setHeaderText(
        "Da li ste sigurni da zelite da izbrisete mesto i ulice vezane za to mesto?");
    alert.initOwner(bMestoDelete.getScene().getWindow());
    alert.showAndWait();

    //If UserData select no then return from this method
    //else continue to delete
    if (alert.getResult() == bNo) {
      return;
    }

    mesta = (Mesta) tblMesto.getSelectionModel().getSelectedItem();
    jObj = new JSONObject();
    jObj.put("action", "DEL_MESTO");
    jObj.put("idMesta", mesta.getId());
    jObj = client.send_object(jObj);

    if (jObj.getString("Message").equals("MESTO_DELETED")) {
      osveziMesto(null);
    }
  }

  public void osveziMesto(ActionEvent actionEvent) {
    ObservableList<Mesta> observableListMesta = FXCollections.observableArrayList(get_mesta());
    tblMesto.setItems(observableListMesta);
  }

  private ArrayList<Mesta> get_mesta() {
    jObj = new JSONObject();
    jObj.put("action", "getMesta");

    jObj = client.send_object(jObj);

    arrMesta = new ArrayList<Mesta>();
    JSONObject jsonMesta;

    for (int i = 0; i < jObj.length(); i++) {
      jsonMesta = (JSONObject) jObj.get(String.valueOf(i));
      mesta = new Mesta();
      mesta.setId(jsonMesta.getInt("id"));
      mesta.setBrojMesta(jsonMesta.getString("brojMesta"));
      mesta.setNazivMesta(jsonMesta.getString("nazivMesta"));
      arrMesta.add(mesta);
    }

    return arrMesta;

  }

  private ArrayList<Adrese> get_adrese(String brojMesta) {
    jObj = new JSONObject();
    jObj.put("action", "getAdrese");
    jObj.put("brojMesta", brojMesta);

    jObj = client.send_object(jObj);

    arrAdrese = new ArrayList<Adrese>();
    JSONObject jsonAdrese;

    for (int i = 0; i < jObj.length(); i++) {
      jsonAdrese = (JSONObject) jObj.get(String.valueOf(i));
      adrese = new Adrese();
      adrese.setId(jsonAdrese.getInt("id"));
      adrese.setNazivAdrese(jsonAdrese.getString("nazivAdrese"));
      adrese.setBrojAdrese(jsonAdrese.getString("brojAdrese"));
      adrese.setIdMesta(jsonAdrese.getInt("idMesta"));
      adrese.setNazivMesta(jsonAdrese.getString("nazivMesta"));
      adrese.setBrojMesta(jsonAdrese.getString("brojMesta"));
      arrAdrese.add(adrese);
    }
    return arrAdrese;
  }

  public void dodajAdresu(ActionEvent actionEvent) {

    if (tblMesto.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.warrning("GRESKA", "MOrate izabrati mesto kome pripada adresa!");
      return;
    }

    Mesta mesto = (Mesta) tblMesto.getSelectionModel().getSelectedItem();

    jObj = new JSONObject();
    jObj.put("action", "addAdresa");
    jObj.put("nazivAdrese", tAdresaNaziv.getText());
    jObj.put("brojAdrese", tAdresaBroj.getText());
    jObj.put("brojMesta", mesto.getBrojMesta());
    jObj.put("idMesta", mesto.getId());
    jObj.put("nazivMesta", mesto.getNazivMesta());

    jObj = client.send_object(jObj);

    osveziAdresu(null);

  }

  public void izbrisiAdresu(ActionEvent actionEvent) {

    if (tblAdrese.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.warrning("GRESKA", "Morate izabrati adresa za brisanje");
      return;
    }

    if (!AlertUser.yesNo("BRISANJE ADERSE", "Da li ste sigurni da želite da izbrišete adresu?")) {
      return;
    }

    Adrese addrese = (Adrese) tblAdrese.getSelectionModel().getSelectedItem();

    jObj = new JSONObject();
    jObj.put("action", "delAdresa");
    jObj.put("id", addrese.getId());

    jObj = client.send_object(jObj);
    if (jObj.has("Message")) {
      if (jObj.getString("Message").equals("ADRESS_DELETED")) {
        osveziAdresu(null);
      }
    }

  }

  public void osveziAdresu(ActionEvent actionEvent) {
    if (tblMesto.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.warrning("GRESKA", "Morate izabrati mesto za prikaz adresa");
      return;
    }

    mesta = (Mesta) tblMesto.getSelectionModel().getSelectedItem();

    ObservableList<Adrese> observableListAdrese = FXCollections
        .observableArrayList(get_adrese(mesta.getBrojMesta()));
    tblAdrese.setItems(observableListAdrese);
  }

}
