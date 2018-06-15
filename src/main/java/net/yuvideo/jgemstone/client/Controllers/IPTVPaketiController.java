package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import net.yuvideo.jgemstone.client.classes.IPTVPaketi;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

/**
 * Created by PsyhoZOOM@gmail.com on 8/15/17.
 */
public class IPTVPaketiController implements Initializable {

  public Button bIzbrisi;
  public Button bIzmeni;
  public Button bNov;
  public TableView<IPTVPaketi> tblPaketiIPTV;
  public TableColumn<IPTVPaketi, String> cNaziv;
  public TableColumn<IPTVPaketi, String> cOpis;
  public TableColumn<IPTVPaketi, Integer> cID;
  public TableColumn<IPTVPaketi, Integer> cExternalID;
  public TableColumn<IPTVPaketi, Double> cCena;
  public TableColumn<IPTVPaketi, Integer> cIPTVID;
  private Client client;
  @FXML
  private TableColumn<IPTVPaketi, Double> cPDV;
  private URL location;
  private ResourceBundle resources;
  @FXML
  private TableColumn<IPTVPaketi, Double> cCenaPDV;

  private final DecimalFormat df = new DecimalFormat("#,###,###,##0.00");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    cNaziv.setCellValueFactory(new PropertyValueFactory<IPTVPaketi, String>("name"));
    cOpis.setCellValueFactory(new PropertyValueFactory<IPTVPaketi, String>("description"));
    cID.setCellValueFactory(new PropertyValueFactory<IPTVPaketi, Integer>("id"));
    cExternalID.setCellValueFactory(new PropertyValueFactory<IPTVPaketi, Integer>("external_id"));
    cCena.setCellValueFactory(new PropertyValueFactory<IPTVPaketi, Double>("cena"));
    cIPTVID.setCellValueFactory(new PropertyValueFactory<IPTVPaketi, Integer>("iptv_id"));
    cPDV.setCellValueFactory(new PropertyValueFactory<IPTVPaketi, Double>("pdv"));
    cCenaPDV.setCellValueFactory(new PropertyValueFactory<IPTVPaketi, Double>("cenaPDV"));

    cCena.setCellFactory(
        new Callback<TableColumn<IPTVPaketi, Double>, TableCell<IPTVPaketi, Double>>() {
          @Override
          public TableCell<IPTVPaketi, Double> call(TableColumn<IPTVPaketi, Double> param) {
            return new TableCell<IPTVPaketi, Double>() {
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

    cPDV.setCellFactory(
        new Callback<TableColumn<IPTVPaketi, Double>, TableCell<IPTVPaketi, Double>>() {
          @Override
          public TableCell<IPTVPaketi, Double> call(TableColumn<IPTVPaketi, Double> param) {
            return new TableCell<IPTVPaketi, Double>() {
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
        new Callback<TableColumn<IPTVPaketi, Double>, TableCell<IPTVPaketi, Double>>() {
          @Override
          public TableCell<IPTVPaketi, Double> call(TableColumn<IPTVPaketi, Double> param) {
            return new TableCell<IPTVPaketi, Double>() {
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

    tblPaketiIPTV.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<IPTVPaketi>() {
          @Override
          public void changed(ObservableValue<? extends IPTVPaketi> observable, IPTVPaketi oldValue,
              IPTVPaketi newValue) {
            if (tblPaketiIPTV.getSelectionModel().getSelectedIndex() == -1) {
              return;
            }
            if (newValue.getId() == 0) {
              bIzmeni.setDisable(true);
            }
          }
        });

  }

  public void izmeniIPTVPaket(ActionEvent actionEvent) {
    String resourceFXML = "fxml/IPTVPaketEdit.fxml";
    NewInterface iptvPaketEditInterface = new NewInterface(resourceFXML, "Izmena IPTV Paketa",
        resources);
    IPTVPaketiEditController iptvPaketEditController = iptvPaketEditInterface.getLoader()
        .getController();

    iptvPaketEditController.setClient(new Client(client.getLocal_settings()));
    iptvPaketEditController.edit = true;
    iptvPaketEditController.paket = tblPaketiIPTV.getSelectionModel().getSelectedItem();
    iptvPaketEditController.paketEditID = tblPaketiIPTV.getSelectionModel().getSelectedItem()
        .getId();
    iptvPaketEditController.setItemsEdit();
    iptvPaketEditInterface.getStage().showAndWait();
    showPaketiTable();


  }

  public void novIPTVPaket(ActionEvent actionEvent) {
    String resourceFXML = "fxml/IPTVPaketEdit.fxml";
    NewInterface iptvPaketNewInterface = new NewInterface(resourceFXML, "Nov IPTV Paket",
        resources);
    IPTVPaketiEditController iptvPaketiNewControoler = iptvPaketNewInterface.getLoader()
        .getController();
    iptvPaketiNewControoler.setClient(new Client(client.getLocal_settings()));
    iptvPaketiNewControoler.edit = false;
    iptvPaketiNewControoler.setData();
    iptvPaketNewInterface.getStage().showAndWait();
    showPaketiTable();

  }

  public void showPaketiTable() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "getIPTVDataLocal");
    jsonObject = client.send_object(jsonObject);

    ArrayList<IPTVPaketi> iptvPaketiArrayList = new ArrayList<>();


    for (int i = 0; i < jsonObject.length(); i++) {
      JSONObject pakobj = (JSONObject) jsonObject.get(String.valueOf(i));
      IPTVPaketi paketi = new IPTVPaketi();
      paketi.setName(pakobj.getString("name"));
      paketi.setId(pakobj.getInt("id"));
      paketi.setExternal_id(pakobj.getString("external_id"));
      paketi.setCena(pakobj.getDouble("cena"));
      paketi.setDescription(pakobj.getString("opis"));
      paketi.setIptv_id(pakobj.getInt("IPTV_id"));
      paketi.setPdv(pakobj.getDouble("pdv"));
      Double cenaPDV = valueToPercent.getPDVOfValue(pakobj.getDouble("cena"),
          pakobj.getDouble("pdv"));
      paketi.setCenaPDV(Double.valueOf(cenaPDV + pakobj.getDouble("cena")));

      iptvPaketiArrayList.add(paketi);

    }
    ObservableList paketi = FXCollections.observableArrayList(iptvPaketiArrayList);
    tblPaketiIPTV.setItems(paketi);
  }

  private IPTVPaketi getPaketLocalData(int external_id) {
    IPTVPaketi paket;
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "getIPTVData");
    jsonObject.put("external_id", external_id);
    jsonObject = client.send_object(jsonObject);
    paket = new IPTVPaketi();

    //ako ne postoji u bazi preskociti;
    if (!jsonObject.has("id")) {
      return paket;
    }
    paket.setId(jsonObject.getInt("id"));
    paket.setName(jsonObject.getString("name"));
    paket.setIptv_id(jsonObject.getInt("IPTV_id"));
    paket.setExternal_id(jsonObject.getString("external_id"));
    paket.setDescription(jsonObject.getString("opis"));
    paket.setCena(jsonObject.getDouble("cena"));

    return paket;
  }


  public void deletePaket(ActionEvent actionEvent) {
    if (tblPaketiIPTV.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }

    JSONObject obj = new JSONObject();
    obj.put("action", "delete_IPTV_paket");
    obj.put("id", tblPaketiIPTV.getSelectionModel().getSelectedItem().getId());
    obj = client.send_object(obj);
    if (obj.has("ERROR")) {
      AlertUser.error("GRESKA", obj.getString("ERROR"));
    } else {
      AlertUser.info("PAKET IZBRISA", String.format("Paket %s je izbrisan!",
          tblPaketiIPTV.getSelectionModel().getSelectedItem().getName()));
      tblPaketiIPTV.getItems().remove(tblPaketiIPTV.getSelectionModel().getSelectedItem());
    }

  }
}
