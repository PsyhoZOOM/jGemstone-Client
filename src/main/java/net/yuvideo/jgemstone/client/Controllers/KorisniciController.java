package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.Users;
import net.yuvideo.jgemstone.client.classes.messageS;
import org.json.JSONObject;

/**
 * Created by zoom on 8/3/16.
 */
public class KorisniciController implements Initializable {

  public TableView<Users> tUsers;
  public TableColumn cJBroj;
  public TableColumn cFullName;
  public TableColumn cAddress;
  public TableColumn cPlace;
  public TableColumn cAdressUsluge;
  public TextField tUserSearch;
  public MenuItem cmIzmeni;
  public MenuItem cmIzbrisi;
  public MenuItem cmServisi;
  public Button bUserSearch;
  public TableColumn<Users, Double> cDug;
  public MenuItem cmUplate;
  public TableColumn cFIrma;
  public Button bFilteri;
  public TableColumn cDatumRodjenja;
  public TableColumn cEmail;
  public TableColumn cMestoUsluge;
  public TableColumn cTelFiksni;
  public TableColumn cTelMob;
  public TableColumn cDatumKreiranja;
  public CheckBox chkNaprednaPretraga;
  @FXML
  Button bNovKorisnik;
  FXMLLoader resoruceFXML;
  Parent root;
  Scene scene;
  Stage stage;
  FXMLLoader loader;

  //JSON
  JSONObject jObj;
  private Client client;
  private int getSelectedId;
  private ResourceBundle resources;
  private messageS mess;
  private DecimalFormat df = new DecimalFormat("###,###,###,##0.00");
  FilteriSearch filteriSearchController;
  NewInterface filteriInterface;
  private String advancedSearch;
  private URL location;

  @Override
  public void initialize(URL location, final ResourceBundle resources) {
    this.resources = resources;
    this.location = location;
    tUsers.getItems().removeAll();
    tUsers.getItems().clear();


    tUserSearch.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
          show_table(tUserSearch.getText());
        }
      }
    });
    cJBroj.setCellValueFactory(new PropertyValueFactory<Users, String>("jbroj"));
    cFullName.setCellValueFactory(new PropertyValueFactory<Users, String>("ime"));
    cFIrma.setCellValueFactory(new PropertyValueFactory<Users, String>("nazivFirme"));
    cAddress.setCellValueFactory(new PropertyValueFactory<Users, String>("adresa"));
    cPlace.setCellValueFactory(new PropertyValueFactory<Users, String>("mesto"));
    cAdressUsluge.setCellValueFactory(new PropertyValueFactory<Users, String>("adresaUsluge"));
    cDatumRodjenja.setCellValueFactory(new PropertyValueFactory<Users, String>("datum_rodjenja"));
    cDatumKreiranja.setCellValueFactory(new PropertyValueFactory<Users, String>("datumKreiranja"));
    cEmail.setCellValueFactory(new PropertyValueFactory<Users, String>("email"));
    cMestoUsluge.setCellValueFactory(new PropertyValueFactory<Users, String>("mestoUsluge"));
    cTelFiksni.setCellValueFactory(new PropertyValueFactory<Users, String>("fiksni"));
    cTelMob.setCellValueFactory(new PropertyValueFactory<Users, String>("mobilni"));
    cDug.setCellValueFactory(new PropertyValueFactory<Users, Double>("dug"));

    tUsers.setRowFactory(tv -> {
      TableRow<Users> row = new TableRow<Users>();
      row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (!row.isEmpty())) {
          showUplate(null);
        }
      });
      return row;
    });


    cDug.setCellFactory(new Callback<TableColumn<Users, Double>, TableCell<Users, Double>>() {
      @Override
      public TableCell<Users, Double> call(TableColumn<Users, Double> param) {
        return new TableCell<Users, Double>(){
          @Override
          protected void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);
            if(empty || item == null){
              setText(null);
            }else{
              setText(df.format(item));
            }
          }
        };
      }
    });


  }

  private void show_table(String username) {
    if (!chkNaprednaPretraga.isSelected()) {
      if (username.trim().length() < 4) {
        return;
      }
    }
    tUsers.getItems().clear();
    ObservableList data = FXCollections.observableArrayList(get_user_table_list(username));
    if (data.size() > 0)
      tUsers.setItems(data);
  }


  private ArrayList<Users> get_user_table_list(String search_user) {

    jObj = new JSONObject();
    jObj.put("action", "get_users");
    jObj.put("username", search_user);
    if (chkNaprednaPretraga.isSelected()) {
      jObj.put("advancedSearch", this.advancedSearch);
    }


    jObj = client.send_object(jObj);
    ArrayList<Users> users = new ArrayList();

    for (int i = 0; i < jObj.length(); i++) {
      JSONObject jUser = (JSONObject) jObj.get(String.valueOf(i));
      Users user = new Users();
      user.setId(jUser.getInt("id"));
      user.setjMesto(jUser.getString("jMesto"));
      user.setjAdresa(jUser.getString("jAdresa"));
      user.setjAdresaBroj(jUser.getString("jAdresaBroj"));
      user.setIme(jUser.getString("fullName"));
      user.setMesto(jUser.getString("mesto"));
      user.setAdresa(jUser.getString("adresa"));
      user.setAdresaUsluge(jUser.getString("adresaUsluge"));
      user.setMestoUsluge(jUser.getString("mestoUsluge"));
      user.setMestoRacuna(jUser.getString("mestoRacuna"));
      user.setAdresaRacuna(jUser.getString("adresaRacuna"));
      user.setBr_lk(jUser.getString("brLk"));
      user.setDatum_rodjenja(jUser.getString("datumRodjenja"));
      user.setFiksni(jUser.getString("telFixni"));
      user.setMobilni(jUser.getString("telMobilni"));
      user.setJMBG(jUser.getString("JMBG"));
      user.setKomentar(jUser.getString("komentar"));
      user.setPostanski_broj(jUser.getString("postBr"));
      user.setJbroj(jUser.getString("jBroj"));
      user.setFirma(jUser.getBoolean("firma"));
      if (!jUser.getString("jBroj").isEmpty()) {
        user.setBr(jUser.getString("jBroj"));
      }
      user.setNazivFirme(jUser.getString("nazivFirme"));
      user.setKontaktOsoba(jUser.getString("kontaktOsoba"));
      user.setKodBanke(jUser.getString("kodBanke"));
      user.setPIB(jUser.getString("PIB"));
      user.setMaticniBroj(jUser.getString("maticniBroj"));
      user.setTekuciRacuna(jUser.getString("tekuciRacun"));
      user.setFax(jUser.getString("fax"));
      user.setAdresaFirme(jUser.getString("adresaFirme"));
      user.setDatumKreiranja(jUser.getString("datumKreiranja"));
      user.setEmail(jUser.getString("email"));
      user.setDug(jUser.getDouble("dug"));

      users.add(user);
    }


    return users;
  }


  public void bUserSearchAction(ActionEvent actionEvent) {
    show_table(tUserSearch.getText());
  }


  public void mIzmenKorisnika(ActionEvent actionEvent) {
    bEditUser(null);
  }

  public void mIzbrisiKorisnika(ActionEvent actionEvent) {
    if (tUsers.getSelectionModel().getSelectedIndex() == -1) {
      //Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + "no UserData selected" + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
      AlertUser.warrning("UPOZORENJE", "Nije izabran ni jedan korisnik!");
      return;
    } else {

      boolean potvrda_brisanja = AlertUser
          .yesNo("POTVRDA BRISANJA", "Da li ste sigurni da želite da izbrišete koriznika? \n"
              + " i svi podaci, servisi, uplate, oprema, fakture.. ko je pripadaju korisniku? ");
      if (!potvrda_brisanja) {
        return;
      }
    }

    Users user = tUsers.getSelectionModel().getSelectedItem();

    jObj = new JSONObject();
    jObj.put("action", "delete_user");
    jObj.put("userId", user.getId());

    jObj = client.send_object(jObj);
    show_table("");
    if (jObj.has("ERROR")) {
      AlertUser.error("GRESKA", jObj.getString("ERROR"));
    } else {
      AlertUser.info("KORISNIK OBRISAN", String.format("Korisnik %s je obrisan!", user.getIme()));
      tUsers.getItems().remove(user);
    }

  }

  public void bEditUser(ActionEvent actionEvent) {
    if (tUsers.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.warrning("NIJE IZABRAN KORISNIK", "Izaberite korisnika za izmene!");
      return;
    }
    Users user = tUsers.getSelectionModel().getSelectedItem();
    int userId = user.getId();
    EditUser(userId);

  }


  public void EditUser(int UserID) {
    NewInterface editKorisnikInterface = new NewInterface("fxml/EditKorisnik.fxml",
        "Izmena korisnik", resources);
    EditKorisnikController editUserController = editKorisnikInterface.getLoader().getController();

    editUserController.setClient(this.client);
    editUserController.userEdit = tUsers.getSelectionModel().getSelectedItem();
    editUserController.userID = UserID;
    editUserController.loadKorisnikData();
    editUserController.loadKorisnikServices();
    editUserController.loadKorisnikUgovori();
    editUserController.loadKorisnikOprema();
    editUserController.loadKorisnikUplate();

    editKorisnikInterface.getStage().showAndWait();
    //   stage.setScene(editKorisnikInterface.getScene());


  }


  public void newUser(ActionEvent actionEvent) {
    //treba na free id iz mysql baze da ne bi bilo rupa :) (klin se klinom izbija ali rupa ostaje ;))
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "get_next_free_ID");
    jsonObject = client.send_object(jsonObject);

    NewInterface novKorisnik = new NewInterface("fxml/NovKorisnik.fxml", "Nov Korisnik", resources);
    NovKorisnikController novKorisnikController = novKorisnik.getLoader().getController();

    novKorisnikController.setClient(client);
    novKorisnikController.freeID = jsonObject.getInt("freeID");
    novKorisnikController.setData();

    novKorisnik.getStage().showAndWait();

    if (novKorisnikController.user != null) {
      tUserSearch.setText(String.valueOf(novKorisnikController.user.getId()));
    }
    if (novKorisnikController.user_saved) {
      EditUser(novKorisnikController.user.getId());
      show_table(null);
      tUsers.getSelectionModel().select(novKorisnikController.user);
    }

  }


  public void showUplate(ActionEvent actionEvent) {
    if (tUsers.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.warrning("UPOZORENJE", "Nije izabran ni jedan korisnik za uplate!");
      return;
    }
    Users user = tUsers.getSelectionModel().getSelectedItem();


  }



  public void mUplate(ActionEvent event) {
    showUplate(null);
  }

  public void showFiltere(ActionEvent actionEvent) {
    if (filteriInterface == null)
      filteriInterface = new NewInterface("fxml/FilteriSearch.fxml", "Filteri Pretrage",
          this.resources, false);
    if (filteriSearchController == null) {
      filteriSearchController = filteriInterface.getLoader().getController();
    }
    filteriInterface.getStage().showAndWait();
    this.advancedSearch = filteriSearchController.query;


  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public void setClient(Client client) {
    this.client = client;
  }

}
