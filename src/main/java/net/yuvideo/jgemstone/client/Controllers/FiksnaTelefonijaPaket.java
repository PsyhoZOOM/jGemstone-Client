package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.nio.file.Path;
import java.text.DecimalFormat;
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
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.FiksnaPaketi;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

/**
 * Created by PsyhoZOOM@gmail.com on 5/24/17.
 */
public class FiksnaTelefonijaPaket implements Initializable {

  public TableView<FiksnaPaketi> tblPaketi;
  public TableColumn cNaziv;
  public TableColumn cPretplata;
  public TableColumn cPDV;
  public Button bDelete;
  public Button bNov;
  public Button bSnimi;
  public Button bOsvezi;
  private Client client;
  @FXML
  TableColumn cCenaPDV;
  private ResourceBundle resources;
  private URL location;
  private JSONObject jObj;
  private static DecimalFormat df = new DecimalFormat("#,###,###,##0.00");


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.resources = resources;
    this.location = location;

    cNaziv.setCellValueFactory(new PropertyValueFactory<FiksnaPaketi, String>("naziv"));
    cPDV.setCellValueFactory(new PropertyValueFactory<FiksnaPaketi, Double>("pdv"));
    cPretplata.setCellValueFactory(new PropertyValueFactory<FiksnaPaketi, Double>("pretplata"));
    cCenaPDV.setCellValueFactory(new PropertyValueFactory<FiksnaPaketi, Double>("cenaPDV"));

    cPDV.setCellFactory(
        new Callback<TableColumn<FiksnaPaketi, Double>, TableCell<FiksnaPaketi, Double>>() {
          @Override
          public TableCell<FiksnaPaketi, Double> call(TableColumn<FiksnaPaketi, Double> param) {
            return new TableCell<FiksnaPaketi, Double>() {
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

    cPretplata.setCellFactory(
        new Callback<TableColumn<FiksnaPaketi, Double>, TableCell<FiksnaPaketi, Double>>() {
          @Override
          public TableCell<FiksnaPaketi, Double> call(TableColumn<FiksnaPaketi, Double> param) {
            return new TableCell<FiksnaPaketi, Double>() {
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
        new Callback<TableColumn<FiksnaPaketi, Double>, TableCell<FiksnaPaketi, Double>>() {
          @Override
          public TableCell<FiksnaPaketi, Double> call(TableColumn<FiksnaPaketi, Double> param) {
            return new TableCell<FiksnaPaketi, Double>() {
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

  public void osveziTable(ActionEvent actionEvent) {
    setTable();
  }

  public void setTable() {
    jObj = new JSONObject();
    jObj.put("action", "show_fixTel_paketi");
    jObj = client.send_object(jObj);

    ArrayList<FiksnaPaketi> paketis = new ArrayList<>();
    FiksnaPaketi paketi;
    JSONObject paketiObj;
    for (int i = 0; i < jObj.length(); i++) {
      paketi = new FiksnaPaketi();
      paketiObj = (JSONObject) jObj.get(String.valueOf(i));
      paketi.setId(paketiObj.getInt("id"));
      paketi.setNaziv(paketiObj.getString("naziv"));
      paketi.setPretplata(paketiObj.getDouble("pretplata"));
      paketi.setBesplatniMinutiFiksna(paketiObj.getInt("besplatniMinutiFiksna"));
      paketi.setPdv(paketiObj.getDouble("pdv"));
      paketi.setCenaPDV(paketiObj.getDouble("pretplata") + valueToPercent
          .getPDVOfValue(paketiObj.getDouble("pretplata"), paketiObj.getDouble("pdv")));
      paketis.add(paketi);
    }

    ObservableList data = FXCollections.observableArrayList(paketis);
    tblPaketi.setItems(data);
  }

  @FXML
  private void editPaket() {
    if (tblPaketi.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.error("GRESKA", "Nije izabran paket za izmenu");
    }
    FiksnaPaketi paket = tblPaketi.getSelectionModel().getSelectedItem();
    NewInterface editFiksPaketInterface = new NewInterface("fxml/FiksnaTelefonijaPaketEdit.fxml",
        "Izmene paketa " + paket.getNaziv(), resources);
    FiksnaTelefonijaPaketEditController ediPaketFiksna = editFiksPaketInterface.getLoader()
        .getController();
    ediPaketFiksna.setClient(this.client);
    ediPaketFiksna.edit = true;
    ediPaketFiksna.paketEdit = paket;
    ediPaketFiksna.set_data();
    editFiksPaketInterface.getStage().showAndWait();
    setTable();
  }

  @FXML
  private void newPaket() {
    NewInterface editInterface = new NewInterface("fxml/FiksnaTelefonijaPaketEdit.fxml",
        "Nov Paket Fiksne telefonije", resources);
    FiksnaTelefonijaPaketEditController paketController = editInterface.getLoader().getController();
    paketController.setClient(this.client);
    paketController.edit = false;
    editInterface.getStage().showAndWait();
    setTable();
  }

  public void deletePaket(ActionEvent actionEvent) {
    if (tblPaketi.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }

    JSONObject obj = new JSONObject();
    obj.put("action", "delete_fiksna_paket");
    obj.put("id", tblPaketi.getSelectionModel().getSelectedItem().getId());
    obj = client.send_object(obj);
    if (obj.has("ERROR")) {
      AlertUser.error("GRESKA", obj.getString("ERROR"));
    } else {
      AlertUser.info("PAKET IZBRISAN", String.format("Paket %s je izbrisan",
          tblPaketi.getSelectionModel().getSelectedItem().getNaziv()));
      tblPaketi.getItems().remove(tblPaketi.getSelectionModel().getSelectedItem());
    }
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
