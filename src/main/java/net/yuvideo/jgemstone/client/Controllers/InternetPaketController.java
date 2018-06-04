package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.InternetPaketi;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

/**
 * Created by zoom on 1/31/17.
 */
public class InternetPaketController implements Initializable {

  public TableView<InternetPaketi> tblInternetPaket;
  public TableColumn cNaziv;
  public TableColumn cBrzina;
  public TableColumn cCena;
  public TableColumn cOpis;
  public Button bClose;
  public TableColumn cIdleTimeout;
  public Client client;
  @FXML
  TableColumn cPDV;
  @FXML
  TableColumn cCenaPDV;
  private ResourceBundle resource;
  private URL location;
  private JSONObject jObj;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.resource = resources;
    this.location = location;

    cNaziv.setCellValueFactory(new PropertyValueFactory<InternetPaketi, String>("naziv"));
    cBrzina.setCellValueFactory(new PropertyValueFactory<InternetPaketi, String>("brzina"));
    cCena.setCellValueFactory(new PropertyValueFactory<InternetPaketi, Double>("cena"));
    cOpis.setCellValueFactory(new PropertyValueFactory<InternetPaketi, String>("opis"));
    cIdleTimeout
        .setCellValueFactory(new PropertyValueFactory<InternetPaketi, String>("idleTimeout"));
    cPDV.setCellValueFactory(new PropertyValueFactory<InternetPaketi, Double>("pdv"));
    cCenaPDV.setCellValueFactory(new PropertyValueFactory<InternetPaketi, Double>("cenaPDV"));

    cCena.setCellFactory(tc -> new TableCell<InternetPaketi, Double>() {
      @Override
      protected void updateItem(Double dbl, boolean bool) {
        super.updateItem(dbl, bool);
        if (bool) {
          setText(null);
        } else {
        }
      }
    });

    cCenaPDV.setCellFactory(tc -> new TableCell<InternetPaketi, Double>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText("");
        } else {
          setText(String.valueOf(item));
        }
      }

    });

    cPDV.setCellFactory(tc -> new TableCell<InternetPaketi, Double>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText("");
        } else {
          setText(String.valueOf(item));
        }
      }

    });

  }

  public void pNovAction(ActionEvent actionEvent) {
    NewInterface internetPaketEditInterface = new NewInterface("fxml/InternetPaketEdit.fxml",
        "Nov Internet Paket", resource);
    InternetPaketEditController internetPaketEditController = internetPaketEditInterface.getLoader()
        .getController();
    internetPaketEditController.client = this.client;
    internetPaketEditController.edit = false;
    internetPaketEditInterface.getStage().showAndWait();
    showData();
  }

  public void bPromeni(ActionEvent actionEvent) {

    if (tblInternetPaket.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.info("UPOZORENJE", "Nije izabran paket!");
      return;
    }
    InternetPaketi paket = (InternetPaketi) tblInternetPaket.getSelectionModel().getSelectedItem();

    NewInterface internetPaketEditInterface = new NewInterface("fxml/InternetPaketEdit.fxml",
        "Izmena Internet Paketa", resource);
    InternetPaketEditController internetPaketEditController = internetPaketEditInterface.getLoader()
        .getController();
    internetPaketEditController.client = this.client;
    internetPaketEditController.edit = true;
    internetPaketEditController.paket = paket;
    internetPaketEditController.show_data();
    internetPaketEditInterface.getStage().showAndWait();
    showData();
  }

  public void close(ActionEvent actionEvent) {
    Stage stage = (Stage) bClose.getScene().getWindow();
    stage.close();
  }

  public void showData() {
    ObservableList pakets = FXCollections.observableArrayList(getInternetPakete());
    tblInternetPaket.setItems(pakets);

  }

  private ArrayList<InternetPaketi> getInternetPakete() {
    ArrayList<InternetPaketi> paketiArr = new ArrayList();
    JSONObject paketObj;
    InternetPaketi paket;

    jObj = new JSONObject();
    jObj.put("action", "get_internet_paketi");

    jObj = client.send_object(jObj);

    for (int i = 0; i < jObj.length(); i++) {
      paket = new InternetPaketi();
      paketObj = (JSONObject) jObj.get(String.valueOf(i));
      paket.setId(paketObj.getInt("id"));
      paket.setNaziv(paketObj.getString("naziv"));
      paket.setCena(paketObj.getDouble("cena"));
      paket.setBrzina(paketObj.getString("brzina"));
      paket.setOpis(paketObj.getString("opis"));
      paket.setPdv(paketObj.getDouble("pdv"));
      paket.setCenaPDV(
          paketObj.getDouble("cena") + valueToPercent.getPDVOfValue(paketObj.getDouble("cena"),
              paketObj.getDouble("pdv")));
      paket.setIdleTimeout(paketObj.getString("idleTimeout"));
      paketiArr.add(paket);
    }

    return paketiArr;

  }

  public void izbrisiPaket(ActionEvent actionEvent) {
    if (tblInternetPaket.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }

    JSONObject obj = new JSONObject();
    obj.put("action", "delete_internet_paket");
    obj.put("id", tblInternetPaket.getSelectionModel().getSelectedItem().getId());
    obj = client.send_object(obj);
    if (obj.has("ERROR")) {
      AlertUser.error("GRESKA", obj.getString("ERROR"));
    } else {
      AlertUser.info("PAKET IZBRISAN", String.format("Paket %s je izbrisan!",
          tblInternetPaket.getSelectionModel().getSelectedItem().getNaziv()));
      tblInternetPaket.getItems().remove(tblInternetPaket.getSelectionModel().getSelectedItem());
    }

  }
}
