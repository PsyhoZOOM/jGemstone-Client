package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.OstaleUsluge;
import org.json.JSONObject;

public class OstaleUslugeController implements Initializable {

  public Button bNov;
  public Button bIzmeni;
  public TableColumn<OstaleUsluge, String> cNazivUsluge;
  public TableColumn<OstaleUsluge, Double> cPDV;
  public TableColumn<OstaleUsluge, Double> cCena;
  public TableView<OstaleUsluge> tblOstaleUsluge;
  public TableColumn<OstaleUsluge, String> cOpis;
  public TableColumn<OstaleUsluge, Double> cCenaPDV;
  private Client client;
  private ResourceBundle resources;
  private URL location;
  private final DecimalFormat df = new DecimalFormat("#,###,###,##0.00");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.resources = resources;
    this.location = location;

    cNazivUsluge.setCellValueFactory(new PropertyValueFactory<>("nazivUsluge"));
    cPDV.setCellValueFactory(new PropertyValueFactory<>("pdv"));
    cCena.setCellValueFactory(new PropertyValueFactory<>("cena"));
    cOpis.setCellValueFactory(new PropertyValueFactory<>("komentar"));
    cCenaPDV.setCellValueFactory(new PropertyValueFactory<>("cenaPDV"));

    cPDV.setCellFactory(
        new Callback<TableColumn<OstaleUsluge, Double>, TableCell<OstaleUsluge, Double>>() {
          @Override
          public TableCell<OstaleUsluge, Double> call(TableColumn<OstaleUsluge, Double> param) {
            return new TableCell<OstaleUsluge, Double>() {
              @Override
              protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setText("");
                } else {
                  setText(df.format(item));
                }
              }
            };
          }
        });

    cCena.setCellFactory(
        new Callback<TableColumn<OstaleUsluge, Double>, TableCell<OstaleUsluge, Double>>() {
          @Override
          public TableCell<OstaleUsluge, Double> call(TableColumn<OstaleUsluge, Double> param) {
            return new TableCell<OstaleUsluge, Double>() {
              @Override
              protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setText("");
                } else {
                  setText(df.format(item));
                }
              }
            };
          }
        });

    cCenaPDV.setCellFactory(
        new Callback<TableColumn<OstaleUsluge, Double>, TableCell<OstaleUsluge, Double>>() {
          @Override
          public TableCell<OstaleUsluge, Double> call(TableColumn<OstaleUsluge, Double> param) {
            return new TableCell<OstaleUsluge, Double>() {
              @Override
              protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setText("");
                } else {
                  setText(df.format(item));
                }
              }
            };
          }
        });



  }


  public void setData() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "getOstaleUslugeData");
    jsonObject = client.send_object(jsonObject);

    ArrayList<OstaleUsluge> ostaleUslugesArr = new ArrayList<>();
    for (int i = 0; i < jsonObject.length(); i++) {
      JSONObject obj = jsonObject.getJSONObject(String.valueOf(i));
      OstaleUsluge ostaleUsluge = new OstaleUsluge();
      ostaleUsluge.setId(obj.getInt("id"));
      ostaleUsluge.setNazivUsluge(obj.getString("naziv"));
      ostaleUsluge.setCena(obj.getDouble("cena"));
      ostaleUsluge.setCenaPDV(obj.getDouble("cenaPDV"));
      ostaleUsluge.setPdv(obj.getDouble("pdv"));
      ostaleUsluge.setKomentar(obj.getString("opis"));
      ostaleUslugesArr.add(ostaleUsluge);

    }

    ObservableList<OstaleUsluge> observableList = FXCollections
        .observableArrayList(ostaleUslugesArr);
    tblOstaleUsluge.setItems(observableList);

  }


  public void showNov(ActionEvent actionEvent) {
    NewInterface novServiceInterface = new NewInterface("fxml/OstaleUslugeEdit.fxml", "Nova usluga",
        resources);
    OstaleUslugeEditController novServiceController = novServiceInterface.getLoader()
        .getController();
    novServiceController.edit = false;
    novServiceController.setClient(new Client(client.getLocal_settings()));
    novServiceInterface.getStage().showAndWait();
    setData();
  }

  public void showEdit(ActionEvent actionEvent) {
    NewInterface editInTerface = new NewInterface("fxml/OstaleUslugeEdit.fxml", "Izmena usluge",
        resources);
    OstaleUslugeEditController editServiceController = editInTerface.getLoader().getController();
    editServiceController.setClient(new Client(client.getLocal_settings()));
    editServiceController.edit = true;
    editServiceController.ostaleUsluge = tblOstaleUsluge.getSelectionModel().getSelectedItem();
    editServiceController.setData();
    editInTerface.getStage().showAndWait();
    setData();
  }

  public void deletePaket(ActionEvent actionEvent) {
    if (tblOstaleUsluge.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }
    if (!AlertUser.yesNo("BRISANJE PAKETA", "Da li ste sigurni da želite da izbrištete?")) {
      return;
    }

    JSONObject obj = new JSONObject();
    obj.put("action", "delete_OstaleUsluge_paket");
    obj.put("id", tblOstaleUsluge.getSelectionModel().getSelectedItem().getId());
    obj = client.send_object(obj);
    if (obj.has("ERROR")) {
      AlertUser.error("GRESKA", obj.getString("ERROR"));
    } else {
      AlertUser.info("PAKET IZBRISAN", String.format("Paket %s je izbrisan!",
          tblOstaleUsluge.getSelectionModel().getSelectedItem().getNazivUsluge()));
      tblOstaleUsluge.getItems().remove(tblOstaleUsluge.getSelectionModel().getSelectedItem());
    }

  }

  public void setClient(Client client) {
    this.client = client;
  }
}
