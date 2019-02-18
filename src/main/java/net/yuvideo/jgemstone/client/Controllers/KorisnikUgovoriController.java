package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.UserData;
import net.yuvideo.jgemstone.client.classes.ugovori_types;
import org.json.JSONObject;

/**
 * Created by zoom on 2/9/17.
 */
public class KorisnikUgovoriController implements Initializable {

  public TextField tBrUgovora;
  public ComboBox cmbTamplate;
  public DatePicker dtpTrajanjeOd;
  public DatePicker dtpTrajanjeDo;
  public TextArea tOPis;
  public Button bDodaj;
  public TableView<ugovori_types> tblUgovori;
  public TableColumn cBr;
  public TableColumn cNaziv;
  public TableColumn cTrajanje;
  public TableColumn cOd;
  public TableColumn cDo;
  public TableColumn cOpis;
  public Button bObrisi;
  public Button bIzmeni;
  private Client client;
  public int userID;
  public Button stampaUgovora;
  public UserData user;
  int ugovorNo = 0;
  private URL location;
  private ResourceBundle resources;
  private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private JSONObject jObj;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    dtpTrajanjeOd.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        if (object == null) {
          return "";
        }
        return dateTimeFormatter.format(object);
      }

      @Override
      public LocalDate fromString(String string) {
        if (string == null || string.trim().isEmpty()) {
          return null;
        }
        return LocalDate.parse(string, dateTimeFormatter);
      }
    });

    dtpTrajanjeDo.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        if (object == null) {
          return "";
        }
        return dateTimeFormatter.format(object);
      }

      @Override
      public LocalDate fromString(String string) {
        if (string == null || string.trim().isEmpty()) {
          return null;
        }
        return LocalDate.parse(string, dateTimeFormatter);
      }
    });

    cBr.setCellValueFactory(new PropertyValueFactory<ugovori_types, Integer>("br"));
    cNaziv.setCellValueFactory(new PropertyValueFactory<ugovori_types, String>("Naziv"));
    cOd.setCellValueFactory(new PropertyValueFactory<ugovori_types, String>("pocetakUgovora"));
    cDo.setCellValueFactory(new PropertyValueFactory<ugovori_types, String>("krajUgovora"));
    cOpis.setCellValueFactory(new PropertyValueFactory<ugovori_types, String>("Komentar"));

  }

  public void setUgovorNo() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "getNextFreeUgovorID");
    jsonObject.put("userID", userID);
    jsonObject = client.send_object(jsonObject);
    this.ugovorNo = jsonObject.getInt("NO_UGOVORA");
    jsonObject.put("action", "get_user_data");
    jsonObject.put("userId", userID);
    jsonObject = client.send_object(jsonObject);

    user.setJbroj(jsonObject.getString("jBroj"));
    user.setjMesto(jsonObject.getString("jMesto"));
    user.setjAdresa(jsonObject.getString("jAdresa"));
    String formatedNoUgovora = String.format("%s-%d", user.getJbroj(), ugovorNo);
    tBrUgovora.setText(formatedNoUgovora);

  }

  public void set_data() {
    setUgovorNo();
    ObservableList dataUgovoryTypes = FXCollections.observableArrayList(get_ugovori_types());
    cmbTamplate.setItems(dataUgovoryTypes);

    ObservableList tableUgovori = FXCollections.observableArrayList(get_ugovori_usera());
    tblUgovori.setItems(tableUgovori);

  }

  private ArrayList<ugovori_types> get_ugovori_usera() {
    jObj = new JSONObject();
    jObj.put("action", "get_ugovori_user");
    jObj.put("userID", userID);

    jObj = client.send_object(jObj);

    ArrayList<ugovori_types> ugovoriArr = new ArrayList();
    JSONObject ugovori;
    ugovori_types ugovor;

    for (int i = 0; i < jObj.length(); i++) {
      ugovori = (JSONObject) jObj.get(String.valueOf(i));
      ugovor = new ugovori_types();
      ugovor.setId(ugovori.getInt("id"));
      ugovor.setBr(ugovori.getString("brojUgovora"));
      ugovor.setNaziv(ugovori.getString("naziv"));
      ugovor.setVrsta(ugovori.getString("vrsta"));
      ugovor.setTextUgovora(ugovori.getString("textUgovora"));
      ugovor.setKomentar(ugovori.getString("komentar"));
      ugovor.setPocetakUgovora(ugovori.getString("pocetakUgovora"));
      ugovor.setKrajUgovora(ugovori.getString("krajUgovora"));
      ugovor.setUserID(ugovori.getInt("userID"));
      ugovor.setServiceID(ugovori.getInt("serviceID"));
      ugovoriArr.add(ugovor);
    }

    return ugovoriArr;
  }

  private ArrayList<ugovori_types> get_ugovori_types() {
    jObj = new JSONObject();
    jObj.put("action", "get_ugovori");

    jObj = client.send_object(jObj);

    ArrayList<ugovori_types> ugovoriArr = new ArrayList();
    ugovori_types ugovor;
    JSONObject ugovori;

    for (int i = 0; i < jObj.length(); i++) {
      ugovor = new ugovori_types();
      ugovori = (JSONObject) jObj.get(String.valueOf(i));
      ugovor.setId(ugovori.getInt("idUgovora"));
      ugovor.setNaziv(ugovori.getString("nazivUgovora"));
      ugovor.setTextUgovora(ugovori.getString("textUgovora"));
      ugovoriArr.add(ugovor);
    }
    return ugovoriArr;
  }

  public void addNewUgovorShow(ActionEvent actionEvent) {

    if (tBrUgovora.getText().isEmpty()) {
      AlertUser.error("GRESKA", "Nedostaje broj ugovora!");
      return;
    }

    jObj = new JSONObject();
    jObj.put("action", "check_brUgovora_busy");
    jObj.put("brojUgovora", tBrUgovora.getText());
    jObj = client.send_object(jObj);
    if (jObj.has("ERROR")) {
      AlertUser.error("GRESKA", jObj.getString("ERROR"));
      return;
    }

    ugovori_types ugovor = (ugovori_types) cmbTamplate.getValue();
    ugovor.setPocetakUgovora(dtpTrajanjeOd.getValue().format(dateTimeFormatter));
    ugovor.setKrajUgovora(dtpTrajanjeDo.getValue().format(dateTimeFormatter));
    ugovor.setBr(tBrUgovora.getText());
    ugovor.setKomentar(tOPis.getText());
    ugovor.setUserID(userID);

    LocalDate start_ugovor = LocalDate
        .parse(ugovor.getPocetakUgovora(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    LocalDate stop_ugovor = LocalDate
        .parse(ugovor.getKrajUgovora(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    ugovor.setTrajanje(String.valueOf(stop_ugovor.compareTo(start_ugovor)));

    NewInterface ugovoriEditInterface = new NewInterface("fxml/KorisnikUgovorEdit.fxml",
        "Nov Ugovor", resources);
    KorisnikUgovorEditController korisnikUgovorEditController = ugovoriEditInterface.getLoader()
        .getController();
    korisnikUgovorEditController.ugovor = ugovor;
    korisnikUgovorEditController.setClient(this.client);
    korisnikUgovorEditController.replaceCode = true;
    UserData user = new UserData(client, userID);
    korisnikUgovorEditController.user = user;
    korisnikUgovorEditController.setData();

    ugovoriEditInterface.getStage().showAndWait();

    //cleaning a forms
    tOPis.setText("");
    tBrUgovora.setText("");
    cmbTamplate.getItems().removeAll();
    cDo.setText("");
    cOd.setText("");

    set_data();


  }

  public void izmeniUgovor(ActionEvent actionEvent) {
    ugovori_types ugovor = (ugovori_types) tblUgovori.getSelectionModel().getSelectedItem();
    NewInterface ugovoriEditInterface = new NewInterface("fxml/KorisnikUgovorEdit.fxml",
        "Izmena Ugovora", resources);
    KorisnikUgovorEditController korisnikUgovorEditController = ugovoriEditInterface.getLoader()
        .getController();
    korisnikUgovorEditController.ugovor = ugovor;
    korisnikUgovorEditController.editUgovor = true;
    korisnikUgovorEditController.setClient(this.client);
    korisnikUgovorEditController.replaceCode = false;
    UserData user = new UserData(client, userID);
    korisnikUgovorEditController.user = user;
    korisnikUgovorEditController.setData();
    ugovoriEditInterface.getStage().showAndWait();
    set_data();
  }

  public void stampajUgovor(ActionEvent actionEvent) {
    NewInterface ugovorStampaInterface = new NewInterface("fxml/UgovorStampa.fxml",
        "Stampa Ugovora", resources);
    UgovorStampaController ugovorStampaController = ugovorStampaInterface.getLoader()
        .getController();
    ugovorStampaController.setClient(this.client);
    ugovorStampaController.ugovor = (ugovori_types) tblUgovori.getSelectionModel()
        .getSelectedItem();
    ugovorStampaController.show_data();
    ugovorStampaInterface.getStage().showAndWait();


  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void izbrisiUgovor(ActionEvent actionEvent) {
    if (tblUgovori.getSelectionModel().getFocusedIndex() == -1) {
      AlertUser.info("NIJE IZABRAN UGOVOR ZA BRISANJE", "Izaberite ugovor za brisanje");
      return;
    }
    ugovori_types selectedItem = tblUgovori.getSelectionModel().getSelectedItem();
    JSONObject object = new JSONObject();
    object.put("action", "deleteUserUgovor");
    object.put("id", selectedItem.getId());
    object.put("brojUgovora", selectedItem.getBr());
    object.put("userID", selectedItem.getUserID());
    System.out.println(object.toString());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    } else {
      AlertUser.info("INFO", "Ugovor je izbrisan");
      set_data();
    }

  }
}
