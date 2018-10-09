package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.DigitalniTVPaket;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

/**
 * Created by zoom on 2/2/17.
 */
public class DigitalniTVPaketController implements Initializable {

  public TableColumn cDodatak;
  public TableColumn cDodatnaKartica;
  private Client client;
  public TableView<DigitalniTVPaket> tblDTV;
  public TableColumn cNaziv;
  public TableColumn cPaket;
  public TableColumn cCena;
  public TableColumn cOpis;
  public Button bNov;
  public Button bEdit;
  public TableColumn cPDV;
  public TableColumn cCenaPDV;
  private final DecimalFormat df = new DecimalFormat("#,###,###,##0.00");
  URL location;
  ResourceBundle resources;

  JSONObject jObj;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    cNaziv.setCellValueFactory(new PropertyValueFactory<DigitalniTVPaket, String>("naziv"));
    cCena.setCellValueFactory(new PropertyValueFactory<DigitalniTVPaket, Double>("cena"));
    cPaket.setCellValueFactory(new PropertyValueFactory<DigitalniTVPaket, Integer>("paketID"));
    cOpis.setCellValueFactory(new PropertyValueFactory<DigitalniTVPaket, String>("opis"));
    cPDV.setCellValueFactory(new PropertyValueFactory<DigitalniTVPaket, Double>("pdv"));
    cCenaPDV.setCellValueFactory(new PropertyValueFactory<DigitalniTVPaket, Double>("cenaPDV"));
    cDodatak.setCellValueFactory(new PropertyValueFactory<DigitalniTVPaket, Boolean>("dodatak"));
    cDodatnaKartica
        .setCellValueFactory(new PropertyValueFactory<DigitalniTVPaket, Boolean>("dodatnaKartica"));

    cDodatak.setCellFactory(param -> new TableCell<DigitalniTVPaket, Boolean>() {
      @Override
      protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText("");
          setGraphic(null);
        } else {
          CheckBox cb = new CheckBox();
          cb.setDisable(true);
          cb.setSelected(item);
          setGraphic(cb);
        }
      }
    });

    cDodatnaKartica.setCellFactory(param -> new TableCell<DigitalniTVPaket, Boolean>() {
      @Override
      protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText("");
          setGraphic(null);
        } else {
          CheckBox cb = new CheckBox();
          cb.setDisable(true);
          cb.setSelected(item);
          setGraphic(cb);
        }
      }
    });

    cCena.setCellFactory(tc -> new TableCell<DigitalniTVPaket, Double>() {
      @Override
      protected void updateItem(Double dbl, boolean bool) {
        super.updateItem(dbl, bool);
        if (bool) {
          setText("");
        } else {
          setText(df.format(dbl));
        }
      }
    });

    cPDV.setCellFactory(tc -> new TableCell<DigitalniTVPaket, Double>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText("");
        } else {
          setText(df.format(item));
        }
      }
    });

    cCenaPDV.setCellFactory(tc -> new TableCell<DigitalniTVPaket, Double>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText("");
        } else {
          setText(df.format(item));
        }
      }
    });

  }

  public void showData() {
    ObservableList data = FXCollections.observableArrayList(get_paket());
    tblDTV.setItems(data);


  }

  private ArrayList<DigitalniTVPaket> get_paket() {
    jObj = new JSONObject();
    jObj.put("action", "getDigitalTVPaketi");
    jObj.put("showAddons", true);

    jObj = client.send_object(jObj);

    DigitalniTVPaket dtvPak;
    ArrayList<DigitalniTVPaket> dtvPakArr = new ArrayList();
    JSONObject dtvPakObj;

    for (int i = 0; i < jObj.length(); i++) {
      dtvPak = new DigitalniTVPaket();
      dtvPakObj = (JSONObject) jObj.get(String.valueOf(i));
      double cena;
      double pdv;
      double cenapdv;

      cena = dtvPakObj.getDouble("cena");
      pdv = dtvPakObj.getDouble("pdv");
      cenapdv = cena + valueToPercent.getPDVOfValue(cena, pdv);

      dtvPak.setId(dtvPakObj.getInt("id"));
      dtvPak.setNaziv(dtvPakObj.getString("naziv"));
      dtvPak.setDodatak(dtvPakObj.getBoolean("dodatak"));
      dtvPak.setDodatnaKartica(dtvPakObj.getBoolean("dodatnaKartica"));
      dtvPak.setCena(cena);
      dtvPak.setPaketID(dtvPakObj.getInt("idPaket"));
      dtvPak.setOpis(dtvPakObj.getString("opis"));
      dtvPak.setPdv(pdv);
      dtvPak.setCenaPDV(cenapdv);

      dtvPakArr.add(dtvPak);
    }

    return dtvPakArr;


  }


  public void nov(ActionEvent actionEvent) {
    NewInterface novDtvInterface = new NewInterface("fxml/DigitalnaTVPaketEdit.fxml",
        "Nov Digitalni TV Paket", resources);
    DigitalniTVPaketEditController novDtvController = novDtvInterface.getLoader().getController();
    novDtvController.setClient(this.client);
    novDtvInterface.getStage().showAndWait();
    showData();

  }

  public void edit(ActionEvent actionEvent) {
    NewInterface editDtvInterface = new NewInterface("fxml/DigitalnaTVPaketEdit.fxml",
        "Izmena Digitalnog TV Paketa", resources);
    DigitalniTVPaketEditController editDtvController = editDtvInterface.getLoader().getController();
    editDtvController.setClient(this.client);
    editDtvController.edit = true;
    editDtvController.dtvPaket = (DigitalniTVPaket) tblDTV.getSelectionModel().getSelectedItem();
    editDtvController.show_data();
    editDtvInterface.getStage().showAndWait();
    showData();
  }

  public void izbrisiPaket(ActionEvent actionEvent) {
    if (tblDTV.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }
    JSONObject obj = new JSONObject();
    obj.put("action", "delete_dtv_paket");
    obj.put("id", tblDTV.getSelectionModel().getSelectedItem().getId());
    obj = client.send_object(obj);
    if (obj.has("ERROR")) {
      AlertUser.error("GRESKA", obj.getString("ERROR"));
    } else {
      AlertUser.info("PAKET IZBRISAN", String.format("Paket %s je izbrisan!",
          tblDTV.getSelectionModel().getSelectedItem().getNaziv()));
      tblDTV.getItems().remove(tblDTV.getSelectionModel().getSelectedItem());
    }
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
