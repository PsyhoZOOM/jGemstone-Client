package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.Controllers.Zaduzenja.MesecnaZaduzenja;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import org.json.JSONObject;

public class ZaduzenjaController implements Initializable {

  public JFXListView<MesecnaZaduzenja> listMesec;
  public JFXTreeTableView<MesecnaZaduzenja> tblZaduzenja;
  public Button bNovoZaduzenje;
  private URL location;
  private ResourceBundle resources;
  private Client client;
  private int userID;

  private DecimalFormat df = new DecimalFormat("###,##0.00");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    listMesec.setCellFactory(
        new Callback<ListView<MesecnaZaduzenja>, ListCell<MesecnaZaduzenja>>() {
          @Override
          public ListCell<MesecnaZaduzenja> call(ListView<MesecnaZaduzenja> param) {
            return new ListCell<MesecnaZaduzenja>() {
              @Override
              protected void updateItem(MesecnaZaduzenja item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                  setText("");
                } else {
                  alignmentProperty().set(Pos.CENTER_RIGHT);
                  setText(
                      String.format("%s: %s din.", item.getZaMesec(), df.format(item.getDug())));
                }
              }
            };
          }
        });

    listMesec.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        showDataZaMesec();
      }
    });
    listMesec.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        showDataZaMesec();
      }
    });

    //TABLE
    JFXTreeTableColumn<MesecnaZaduzenja, String> cDatum = new JFXTreeTableColumn<>("DATUM");
    JFXTreeTableColumn<MesecnaZaduzenja, Double> cCena = new JFXTreeTableColumn<>("CENA");
    JFXTreeTableColumn<MesecnaZaduzenja, Integer> cKolicina = new JFXTreeTableColumn<>("KOLIČINA");
    JFXTreeTableColumn<MesecnaZaduzenja, Double> cPDV = new JFXTreeTableColumn<>("PDV");
    JFXTreeTableColumn<MesecnaZaduzenja, Double> cPopust = new JFXTreeTableColumn<>("POPUST");
    JFXTreeTableColumn<MesecnaZaduzenja, String> cNaziv = new JFXTreeTableColumn<>("NAZIV");
    JFXTreeTableColumn<MesecnaZaduzenja, String> cZaduzenOd = new JFXTreeTableColumn<>("OPERATER");
    JFXTreeTableColumn<MesecnaZaduzenja, Double> cDug = new JFXTreeTableColumn<>("DUG");

    cDatum.setCellValueFactory(new TreeItemPropertyValueFactory<MesecnaZaduzenja, String>("datum"));
    cCena.setCellValueFactory(new TreeItemPropertyValueFactory<MesecnaZaduzenja, Double>("cena"));
    cKolicina.setCellValueFactory(
        new TreeItemPropertyValueFactory<MesecnaZaduzenja, Integer>("kolicina"));
    cPDV.setCellValueFactory(new TreeItemPropertyValueFactory<MesecnaZaduzenja, Double>("pdv"));
    cPopust
        .setCellValueFactory(new TreeItemPropertyValueFactory<MesecnaZaduzenja, Double>("popust"));
    cNaziv.setCellValueFactory(new TreeItemPropertyValueFactory<MesecnaZaduzenja, String>("naziv"));
    cZaduzenOd.setCellValueFactory(
        new TreeItemPropertyValueFactory<MesecnaZaduzenja, String>("zaduzenOd"));
    cDug.setCellValueFactory(new TreeItemPropertyValueFactory<MesecnaZaduzenja, Double>("dug"));

    tblZaduzenja.getColumns()
        .addAll(cDatum, cNaziv, cCena, cKolicina, cPDV, cPopust, cZaduzenOd, cDug);
    tblZaduzenja.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

  }

  private void showDataZaMesec() {
    JSONObject object = new JSONObject();
    object.put("action", "getUserMesecnaZaduzenjaZaMesec");
    object.put("userID", userID);
    object.put("zaMesec", listMesec.getSelectionModel().getSelectedItem().getZaMesec());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    ArrayList<MesecnaZaduzenja> mesecnaZaduzenjaArrayList = new ArrayList<>();

    for (int i = 0; i < object.length(); i++) {
      MesecnaZaduzenja mesecnaZaduzenja = new MesecnaZaduzenja();
      JSONObject data = new JSONObject();
      data = object.getJSONObject(String.valueOf(i));
      mesecnaZaduzenja.setId(data.getInt("id"));
      mesecnaZaduzenja.setDatum(data.getString("datum"));
      mesecnaZaduzenja.setCena(data.getDouble("cena"));
      mesecnaZaduzenja.setKolicina(data.getInt("kolicina"));
      mesecnaZaduzenja.setPdv(data.getDouble("pdv"));
      mesecnaZaduzenja.setPopust(data.getDouble("popust"));
      mesecnaZaduzenja.setNaziv(data.getString("naziv"));
      mesecnaZaduzenja.setOpis(data.getString("opis"));
      mesecnaZaduzenja.setZaduzenOd(data.getString("zaduzenOd"));
      mesecnaZaduzenja.setUserID(data.getInt("userID"));
      mesecnaZaduzenja.setPaketType(data.getString("paketType"));
      mesecnaZaduzenja.setDug(data.getDouble("dug"));
      mesecnaZaduzenja.setZaMesec(data.getString("zaMesec"));
      mesecnaZaduzenjaArrayList.add(mesecnaZaduzenja);
    }

    ObservableList mesecnaZaduzenjaObservableList = FXCollections
        .observableList(mesecnaZaduzenjaArrayList);

    TreeItem<MesecnaZaduzenja> root = new RecursiveTreeItem<MesecnaZaduzenja>(
        mesecnaZaduzenjaObservableList,
        RecursiveTreeObject::getChildren);
    root.setExpanded(true);

    tblZaduzenja.setShowRoot(false);
    tblZaduzenja.setRoot(root);


  }

  public void initData() {
    JSONObject object = new JSONObject();
    object.put("action", "getUserMesecnaZaduzenja");
    object.put("userID", getUserID());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    ArrayList<MesecnaZaduzenja> mesecnaZaduzenjaArrayList = new ArrayList<>();

    for (int i = 0; i < object.length(); i++) {
      JSONObject data = object.getJSONObject(String.valueOf(i));
      MesecnaZaduzenja mesecnaZaduzenja = new MesecnaZaduzenja();
      mesecnaZaduzenja.setZaMesec(data.getString("zaMesec"));
      mesecnaZaduzenja.setDug(data.getDouble("dug"));
      mesecnaZaduzenjaArrayList.add(mesecnaZaduzenja);
    }

    TreeItem<MesecnaZaduzenja> rootItem = new TreeItem("MESEČNA ZADUŽENJA");
    ObservableList meseci = FXCollections.observableArrayList(mesecnaZaduzenjaArrayList);

    listMesec.setItems(meseci);


  }

  public void setData() {

  }


  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public void showNovoZaduzenje(ActionEvent actionEvent) {
    NewInterface newInterface = new NewInterface("fxml/KorisnikZaduzenje.fxml",
        "NOVO ZADUŽENJE KORISNIKA", resources, true);
    KorisnikZaduzenjeController korisnikZaduzenjeController = newInterface.getLoader()
        .getController();
    korisnikZaduzenjeController.setClient(this.client);
    korisnikZaduzenjeController.setUserID(getUserID());
    newInterface.getStage().showAndWait();
    initData();
  }

  public void showIzmenaZaduzenja(ActionEvent actionEvent) {
    if (tblZaduzenja.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.info("INFO", "IZABERITE ZADUŽENJE ZA IZMENE");
      return;
    }
    NewInterface newInterface = new NewInterface("fxml/KorisnikZaduzenjeIzmene.fxml",
        "IZMENAS ZADUŽENJA", resources, true);
    KorisnikZaduzenjeIzmene korisnikZaduzenjeIzmeneController = newInterface.getLoader()
        .getController();
    korisnikZaduzenjeIzmeneController.setClient(this.client);
    MesecnaZaduzenja mesecnoZaduzenje = tblZaduzenja.getSelectionModel().getSelectedItem()
        .getValue();
    korisnikZaduzenjeIzmeneController.setMesecnoZaduzenje(mesecnoZaduzenje);
    newInterface.getStage().showAndWait();
    showDataZaMesec();
  }
}
