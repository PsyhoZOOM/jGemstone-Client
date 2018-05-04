package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.BoxPaket;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

/**
 * Created by zoom on 2/23/17.
 */
public class BoxPaketController implements Initializable {

  public Button bSnimiBox;
  public TableView<BoxPaket> tblBox;
  public TableColumn cNaziv;
  public TableColumn cDTV;
  public TableColumn cNET;
  public TableColumn cIPTV;
  public TableColumn cFiksna;
  public Client client;
  @FXML
  TableColumn<BoxPaket, Double> cCena;
  @FXML
  TableColumn<BoxPaket, Double> cPDV;
  @FXML
  TableColumn<BoxPaket, Double> cCenaPDV;
  DecimalFormat df = new DecimalFormat("#.00");
  SpinnerValueFactory.DoubleSpinnerValueFactory spinnerValueFactoryCena = new SpinnerValueFactory.DoubleSpinnerValueFactory(
      0.00, Double.MAX_VALUE, 0.00);
  SpinnerValueFactory.DoubleSpinnerValueFactory spinnerValueFactoryPDV = new SpinnerValueFactory.DoubleSpinnerValueFactory(
      0.00, Double.MAX_VALUE, 0.00);
  private URL location;
  private ResourceBundle resource;
  private JSONObject jObj;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resource = resource;

    cNaziv.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("naziv"));
    cDTV.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("DTV_naziv"));
    cNET.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("NET_naziv"));
    cIPTV.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("IPTV_naziv"));
    cFiksna.setCellValueFactory(new PropertyValueFactory<BoxPaket, String>("FIKSNA_naziv"));
    cCena.setCellValueFactory(new PropertyValueFactory<BoxPaket, Double>("cena"));
    cPDV.setCellValueFactory(new PropertyValueFactory<BoxPaket, Double>("pdv"));
    cCenaPDV.setCellValueFactory(new PropertyValueFactory<BoxPaket, Double>("cenaPDV"));

    cCena
        .setCellFactory(new Callback<TableColumn<BoxPaket, Double>, TableCell<BoxPaket, Double>>() {
          @Override
          public TableCell<BoxPaket, Double> call(TableColumn<BoxPaket, Double> param) {
            return new TableCell<BoxPaket, Double>() {
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

    cPDV.setCellFactory(new Callback<TableColumn<BoxPaket, Double>, TableCell<BoxPaket, Double>>() {
      @Override
      public TableCell<BoxPaket, Double> call(TableColumn<BoxPaket, Double> param) {
        return new TableCell<BoxPaket, Double>() {
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

    cCenaPDV
        .setCellFactory(new Callback<TableColumn<BoxPaket, Double>, TableCell<BoxPaket, Double>>() {
          @Override
          public TableCell<BoxPaket, Double> call(TableColumn<BoxPaket, Double> param) {
            return new TableCell<BoxPaket, Double>() {
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


  public void set_data() {
    ObservableList tablebox = FXCollections.observableArrayList(get_paketBox());
    tblBox.setItems(tablebox);
  }


  private ArrayList<BoxPaket> get_paketBox() {

    jObj = new JSONObject();
    jObj.put("action", "get_paket_box");
    jObj = client.send_object(jObj);

    BoxPaket paketBox;
    ArrayList<BoxPaket> paketBoxesArr = new ArrayList<>();
    JSONObject paketObj;

    for (int i = 0; i < jObj.length(); i++) {
      paketBox = new BoxPaket();
      paketObj = (JSONObject) jObj.get(String.valueOf(i));
      paketBox.setId(paketObj.getInt("id"));
      paketBox.setNaziv(paketObj.getString("naziv"));
      if (paketObj.has("DTV_naziv")) {
        paketBox.setDTV(paketObj.getInt("DTV_id"));
        paketBox.setDTV_naziv(paketObj.getString("DTV_naziv"));
      }
      if (paketObj.has("NET_id")) {
        paketBox.setNET(paketObj.getInt("NET_id"));
        paketBox.setNET_naziv(paketObj.getString("NET_naziv"));
      }
      if (paketObj.has("IPTV_id")) {
        paketBox.setIPTV(paketObj.getInt("IPTV_id"));
        paketBox.setIPTV_naziv(paketObj.getString("IPTV_naziv"));
      }
      if (paketObj.has("FIX_id")) {
        paketBox.setFIKSNA(paketObj.getInt("FIX_id"));
        paketBox.setFIKSNA_naziv(paketObj.getString("FIX_naziv"));
      }
      paketBox.setCena(paketObj.getDouble("cena"));
      paketBox.setPdv(paketObj.getDouble("pdv"));
      paketBox.setCenaPDV(paketObj.getDouble("cena") + valueToPercent
          .getDiffValue(paketObj.getDouble("cena"), paketObj.getDouble("pdv")));
      paketBoxesArr.add(paketBox);
    }
    return paketBoxesArr;

  }


  @FXML
  private void izmeniBox() {
    if (tblBox.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.warrning("GRESKA", "Naziv Box Paketa ili Cena ne mogu biti prazna");
      return;
    }
    BoxPaket paket = tblBox.getSelectionModel().getSelectedItem();

    NewInterface paketBoxEditInterface = new NewInterface("fxml/BoxPaketEdit.fxml",
        "BOX Paket izmena " + paket.getNaziv(), resource);
    BoxPaketEditController paketBoxEditController = paketBoxEditInterface.getLoader()
        .getController();

    paketBoxEditController.client = client;
    paketBoxEditController.editPaket = true;
    paketBoxEditController.boxPaket = paket;
    paketBoxEditController.set_data();

    paketBoxEditInterface.getStage()
        .showAndWait();
    set_data();
  }

  @FXML
  private void novBox() {
    NewInterface paketBoxNewInterface = new NewInterface("fxml/BoxPaketEdit.fxml", "BOX Paket",
        resource);
    BoxPaketEditController paketBoxNewController = paketBoxNewInterface.getLoader().getController();
    paketBoxNewController.client = client;
    paketBoxNewController.editPaket = false;
    paketBoxNewController.set_data();
    paketBoxNewInterface.getStage().showAndWait();
    set_data();
  }

}
