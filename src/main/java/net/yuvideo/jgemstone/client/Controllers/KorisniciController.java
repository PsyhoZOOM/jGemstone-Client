package net.yuvideo.jgemstone.client.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.Users;
import net.yuvideo.jgemstone.client.classes.messageS;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.control.ButtonType.OK;

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
    public Button bUplate;
    public Button bFakture;
    public MenuItem cmIzmeni;
    public MenuItem cmIzbrisi;
    public Button bUserSearch;
    public Client client;
    public TableColumn cDug;
    public MenuItem cmUplate;
    @FXML
    Button bNovKorisnik;
    FXMLLoader resoruceFXML;
    Parent root;
    Scene scene;
    Stage stage;
    FXMLLoader loader;

    //JSON
    JSONObject jObj;
    private int getSelectedId;
    private ResourceBundle resources;
    private messageS mess;

    @Override
    public void initialize(URL location, final ResourceBundle resources) {
        this.resources = resources;
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
        cAddress.setCellValueFactory(new PropertyValueFactory<Users, String>("adresa"));
        cPlace.setCellValueFactory(new PropertyValueFactory<Users, String>("mesto"));
        cAdressUsluge.setCellValueFactory(new PropertyValueFactory<Users, String>("adresa_usluge"));
        cDug.setCellValueFactory(new PropertyValueFactory<Users, Double>("dug"));
        tUsers.setRowFactory(tv -> {
            TableRow<Users> row = new TableRow<Users>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Users rowData = row.getItem();
                    bEditUser(null);
                }
            });
            return row;
        });


        tUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Users>() {
            @Override
            public void changed(ObservableValue<? extends Users> observable, Users oldValue, Users newValue) {
                if (newValue == null)
                    return;
                //ako korisnik nema firmau disable fakture;
                if (newValue.isFirma()) {
                    bFakture.setDisable(false);
                } else {
                    bFakture.setDisable(true);
                }
            }
        });


    }


    private void show_table(String username) {
        ObservableList data = FXCollections.observableArrayList(get_user_table_list(username));
        tUsers.setItems(data);
    }


    private ArrayList<Users> get_user_table_list(String search_user) {

        jObj = new JSONObject();
        jObj.put("action", "get_users");
        jObj.put("username", search_user);

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
            user.setAdresa_usluge(getNazivAdresa(user.getjAdresa()));
            user.setMesto_usluge(jUser.getString("mestoUsluge"));
            user.setBr_lk(jUser.getString("brLk"));
            user.setDatum_rodjenja(jUser.getString("datumRodjenja"));
            user.setFiksni(jUser.getString("telFixni"));
            user.setMobilni(jUser.getString("telMobilni"));
            user.setJMBG(jUser.getString("JMBG"));
            user.setKomentar(jUser.getString("komentar"));
            user.setPostanski_broj(jUser.getString("postBr"));
            user.setJbroj(jUser.getString("jBroj"));
            user.setFirma(jUser.getBoolean("firma"));
            user.setDug(jUser.getDouble("dug"));
            if (!jUser.getString("jBroj").isEmpty())
                user.setBr(Integer.valueOf(jUser.getString("jBroj")));
            if (user.isFirma()) {
                user.setNazivFirme(jUser.getString("nazivFirme"));
                user.setKontaktOsoba(jUser.getString("kontaktOsoba"));
                user.setKodBanke(jUser.getString("kodBanke"));
                user.setPIB(jUser.getString("PIB"));
                user.setMaticniBroj(jUser.getString("maticniBroj"));
                user.setTekuciRacuna(jUser.getString("tekuciRacun"));
                user.setFax(jUser.getString("fax"));
                user.setAdresaFirme(jUser.getString("adresaFirme"));
            }

            users.add(user);
        }

        return users;
    }


    private String getNazivAdresa(String jAdresa) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "getAdresa");
        jsonObject.put("idMesta", Integer.valueOf(jAdresa));

        jsonObject = client.send_object(jsonObject);
        if (jsonObject.has("nazivAdrese")) {
            return jsonObject.get("nazivAdrese").toString();
        } else {
            return "";
        }

    }

    public void bUserSearchAction(ActionEvent actionEvent) {
        show_table(tUserSearch.getText());
    }


    public void mIzmenKorisnika(ActionEvent actionEvent) {
        bEditUser(null);
    }

    public void mIzbrisiKorisnika(ActionEvent actionEvent) {
        Alert alert;
        if (tUsers.getSelectionModel().getSelectedIndex() == -1) {
            //Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + "no user selected" + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert = new Alert(Alert.AlertType.WARNING, "Nije izabran ni jedan korisnik", ButtonType.OK);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("GREŠKA!");
            alert.initOwner(stage);
            alert.showAndWait();
            System.out.println(alert.getButtonTypes());
            if (alert.getResult() == ButtonType.YES) {
                //do stuff
            }
            return;
        } else {
            ButtonType bYES = new ButtonType("Da", ButtonBar.ButtonData.YES);
            ButtonType bNO = new ButtonType("NE", ButtonBar.ButtonData.NO);

            alert = new Alert(Alert.AlertType.CONFIRMATION, "Da li ste sigurni da zelite da izbrišete korisnika", bYES, bNO);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("Brisanje korisnika!");
            alert.initOwner(stage);
            alert.showAndWait();
            System.out.println(alert.getButtonTypes());
            if (alert.getResult() == bNO) {
                return;
            }
        }

        Users user = tUsers.getSelectionModel().getSelectedItem();

        jObj = new JSONObject();
        jObj.put("action", "delete_user");
        jObj.put("userId", user.getId());

        jObj = client.send_object(jObj);
        show_table("");

    }

    public void bEditUser(ActionEvent actionEvent) {
        if (tUsers.getSelectionModel().getSelectedIndex() == -1) {
            //Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + "no user selected" + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nije izabran ni jedan korisnik za izmenu", OK);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("GREŠKA!");
            alert.initOwner(stage);
            alert.showAndWait();
            System.out.println(alert.getButtonTypes());
            if (alert.getResult() == ButtonType.YES) {
                //do stuff
            }
            return;
        }
        Users user = tUsers.getSelectionModel().getSelectedItem();
        int userId = user.getId();
        EditUser(userId);
        show_table(tUserSearch.getText());

    }


    public void EditUser(int UserID) {
        NewInterface editKorisnikInterface = new NewInterface("fxml/EditKorisnik.fxml", "Izmena korisnik", resources);
        EditKorisnikController editUserController = editKorisnikInterface.getLoader().getController();

        editUserController.client = client;
        editUserController.userEdit = tUsers.getSelectionModel().getSelectedItem();
        editUserController.userID = UserID;
        editUserController.loadKorisnikData();
        editUserController.loadKorisnikServices();
        editUserController.loadKorisnikUgovori();
        editUserController.loadKorisnikFirma();

        editKorisnikInterface.getStage().showAndWait();

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

        if (novKorisnikController.user != null)
            tUserSearch.setText(String.valueOf(novKorisnikController.user.getId()));
        if (novKorisnikController.user_saved) {
            EditUser(novKorisnikController.user.getId());
            show_table(null);
            tUsers.getSelectionModel().select(novKorisnikController.user);
        }

    }


    public void showUplate(ActionEvent actionEvent) {
        if (tUsers.getSelectionModel().getSelectedIndex() == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nije izabran ni jedan korisnik za uplate", OK);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("GREŠKA!");
            alert.initOwner(stage);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                //do stuff
            }
            return;
        }
        Users user = tUsers.getSelectionModel().getSelectedItem();

        final NewInterface uplateKorisnik = new NewInterface("fxml/KorisnikUplate.fxml", "Uplate", resources);
        KorisnikUplateController uplateKorisnikController = uplateKorisnik.getLoader().getController();
        uplateKorisnikController.client = client;
        uplateKorisnikController.user = user;
        uplateKorisnikController.show_data();
        uplateKorisnik.getStage().showAndWait();

    }


    public void showFakture(ActionEvent actionEvent) {
        if (tUsers.getSelectionModel().getSelectedIndex() == -1) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nije izabran ni jedan korisnik za fakture", OK);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("GREŠKA!");
            alert.initOwner(stage);
            alert.showAndWait();
            return;
        }
        Users user = tUsers.getSelectionModel().getSelectedItem();

        NewInterface faktureInterface = new NewInterface("fxml/FakturePrikaz.fxml", "Fakture", resources);
        FakturePrikazController fakturePrikazController = faktureInterface.getLoader().getController();
        fakturePrikazController.client = client;
        fakturePrikazController.user = user;
        fakturePrikazController.set_data();
        faktureInterface.getStage().showAndWait();
    }

    public void mUplate(ActionEvent event) {
        showUplate(null);
    }
}
