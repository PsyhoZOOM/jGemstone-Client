package JGemstone.interfaces;

import JGemstone.classes.Client;
import JGemstone.classes.NewInterface;
import JGemstone.classes.Users;
import JGemstone.classes.messageS;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.stage.WindowEvent;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static javafx.scene.control.ButtonType.OK;

/**
 * Created by zoom on 8/3/16.
 */
public class KorisniciController implements Initializable {

    public TableView tUsers;
    public TableColumn cId;
    public TableColumn cUserName;
    public TableColumn cFullName;
    public TableColumn cAddress;
    public TableColumn cPlace;
    public TableColumn cBr;
    public JFXTextField tUserSearch;
    public JFXButton bUplate;
    public JFXButton bFakture;
    public MenuItem cmIzmeni;
    public MenuItem cmIzbrisi;


    @FXML
    JFXButton bNovKorisnik;
    public Button bUserSearch;

    public ArrayList<Users> users;
    public Users user;
    public Client client = new Client();
    private int getSelectedId;
    private ResourceBundle resources;
    String resoruceFXML;
    Parent root;
    Scene scene;
    Stage stage;
    FXMLLoader loader;
    private messageS mess;

    Logger LOGGER = LogManager.getLogger("USERS");

    //JSON
    JSONObject jObj;
    JSONObject jUser;


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

    }

    public void set_table_users(String username) {

        tUsers.setEditable(true);

        cBr.setCellValueFactory(new PropertyValueFactory<Users, Integer>("br"));
        cId.setCellValueFactory(new PropertyValueFactory<Users, Integer>("id"));
        cFullName.setCellValueFactory(new PropertyValueFactory<Users, Integer>("ime"));
        cUserName.setCellValueFactory(new PropertyValueFactory<Users, String>("username"));
        cAddress.setCellValueFactory(new PropertyValueFactory<Users, String>("adresa"));
        cPlace.setCellValueFactory(new PropertyValueFactory<Users, String>("mesto"));

        show_table(username);
    }

    private void show_table(String username) {
        ObservableList<Users> data = FXCollections.observableArrayList(get_user_table_list(username));
        tUsers.setItems(data);
    }


    private ArrayList<Users> get_user_table_list(String search_user) {

        jObj = new JSONObject();
        jObj.put("action", "get_users");
        jObj.put("username", search_user);

        jObj = client.send_object(jObj);
        users = new ArrayList<>();

        for (int i = 0; i < jObj.length(); i++) {
            jUser = (JSONObject) jObj.get(String.valueOf(i));
            user = new Users();
            user.setId(jUser.getInt("id"));
            user.setBr(i);
            if (jUser.has("ime"))
                user.setIme(jUser.getString("ime"));
            if (jUser.has("username"))
                user.setUsername(jUser.getString("username"));
            if (jUser.has("mesto"))
                user.setMesto(jUser.getString("mesto"));
            if (jUser.has("adresa"))
                user.setAdresa(jUser.getString("adresa"));
            if (jUser.has("adresaRacuna"))
                user.setAdresa_za_naplatu(jUser.getString("adresaRacuna"));
            if (jUser.has("adresaKoriscenja"))
                user.setAdresa_koriscenja(jUser.getString("adresaKoriscenja"));
            if (jUser.has("brLk"))
                user.setBr_lk(jUser.getString("brLk"));
            if (jUser.has("datumRodjenja"))
                user.setDatum_rodjenja(jUser.getString("datumRodjenja"));
            if (jUser.has("telFixni"))
                user.setFiksni(jUser.getString("telFixni"));
            if (jUser.has("telMobilni"))
                user.setMobilni(jUser.getString("telMobilni"));
            if (jUser.has("JMBG"))
                user.setJMBG(jUser.getString("JMBG"));
            if (jUser.has("ostalo"))
                user.setOstalo(jUser.getString("ostalo"));
            if (jUser.has("kometar"))
                user.setKomentar(jUser.getString("komentar"));
            if (jUser.has("postBr"))
                user.setPostanski_broj(jUser.getString("postBr"));
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


        Users user = (Users) tUsers.getSelectionModel().getSelectedItem();


        jObj = new JSONObject();
        jObj.put("action", "delete_user");
        jObj.put("userId", user.getId());
        jObj.put("userName", user.getUsername());

        jObj = client.send_object(jObj);
        LOGGER.info(jObj.get("message"));
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
        Users user = (Users) tUsers.getSelectionModel().getSelectedItem();
        int userId = ((Users) tUsers.getSelectionModel().getSelectedItem()).getId();
        EditUser(userId);

    }


    public void EditUser(int UserID) {
        resoruceFXML = "/JGemstone/resources/fxml/EditKorisnik.fxml";
        NewInterface editKorisnikInterface = new NewInterface(800, 600, resoruceFXML, "Izmena korisnik", resources);
        EditKorisnikController editUserController = editKorisnikInterface.getLoader().getController();
        editUserController.client = client;
        editUserController.userID = UserID;
        editUserController.show_usr();

        editKorisnikInterface.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                show_table("");
            }
        });
        editUserController.bClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                show_table(tUserSearch.getText());
                editKorisnikInterface.getStage().close();
            }
        });
        editKorisnikInterface.getStage().showAndWait();
        show_table(tUserSearch.getText());
    }


    public void newUser(ActionEvent actionEvent) {
        resoruceFXML = "/JGemstone/resources/fxml/NovKorisnik.fxml";
        NewInterface novKorisnik = new NewInterface(500, 650, resoruceFXML, "Nov Korisnik", resources);
        NovKorisnikController novKorisnikController = novKorisnik.getLoader().getController();


        novKorisnikController.setClient(client);
        novKorisnik.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                tUserSearch.setText(novKorisnikController.user.getUsername());
                bUserSearchAction(null);
            }
        });

        novKorisnik.getStage().showAndWait();
        show_table("");
        if (novKorisnikController.user_saved) {
            tUsers.getSelectionModel().selectLast();
            bEditUser(null);
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
        Users user = (Users) tUsers.getSelectionModel().getSelectedItem();

        resoruceFXML = "/JGemstone/resources/fxml/KorisnikUplate.fxml";
        final NewInterface uplateKorisnik = new NewInterface(800, 600, resoruceFXML, "Uplate", resources);
        KorisnikUplateController uplateKorisnikController = uplateKorisnik.getLoader().getController();
        uplateKorisnikController.setClient(client);
        uplateKorisnikController.resource = resources;


        uplateKorisnikController.setUserName(user.getUsername());
        uplateKorisnikController.set_table_uplate(user.getUsername());

        uplateKorisnikController.bClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                uplateKorisnik.getStage().close();
                show_table(tUserSearch.getText());

            }

        });
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
        Users user = (Users) tUsers.getSelectionModel().getSelectedItem();

        resoruceFXML = "/JGemstone/resources/fxml/Fakture.fxml";
        NewInterface faktureInterface = new NewInterface(800, 600, resoruceFXML, "Fakture", resources);
        FaktureController faktureController = faktureInterface.getLoader().getController();
        faktureController.client = client;
        faktureController.userData = user;
        faktureController.resource = resources;
        faktureController.set_table();
        faktureInterface.getStage().showAndWait();
    }
}
