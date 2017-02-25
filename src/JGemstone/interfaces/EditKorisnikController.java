package JGemstone.interfaces;

import JGemstone.classes.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zoom on 8/29/16.
 */
public class EditKorisnikController implements Initializable {


    public TextField tFullName;
    public TextField tPostBr;
    public TextField tTelMob;
    public TextField tTelFix;
    public TextField tBrLk;
    public TextField tJMBG;
    public Label lUserID;
    public Client client;
    public Button bAddService;
    public Button bRemoveService;
    public Tab tabKorisnickiPodaci;
    public Button bAdd;
    public ComboBox<ugovori_types> cbNaziv;
    public TableView tblUgv;
    public TextField tBrUg;
    public DatePicker diOd;
    public DatePicker tiDo;
    public TextArea taKomentar;
    public DatePicker tdDatumRodjenja;
    public TextArea tUgKomentar;
    public TableColumn cid;
    public TableColumn cBr;
    public TableColumn cNaziv;
    public TableColumn cOd;
    public TableColumn cDo;
    public TableColumn cuKomentar;
    public Button bSnimiFirma;
    public TextField tNazivFime;
    public TextField tKontaktOsoba;
    public TextField tKodBanke;
    public TextField tPIB;
    public TextField tMaticniBroj;
    public TextField tTekuciRacun;
    public TextField tBrojFakture;
    public JFXListView<Services> lAvServices;
    public JFXListView<Services> lActiveServices;
    public int userID;
    public ComboBox cmbNazivOpreme;
    public ComboBox cmbModelOpreme;
    public JFXTextField tSerijskiOpreme;
    public JFXTextField tMACOpreme;
    public JFXRadioButton radNaplata;
    public JFXRadioButton radRentiranje;
    public JFXRadioButton radKorisnikOprema;
    public Tab tabOprema;
    public TabPane tabKorisnikEdit;
    public JFXButton bAddOprema;
    public TableView tblUserOprema;
    public TableColumn colUserOpremaNaziv;
    public TableColumn colUserOpremaModel;
    public TableColumn colUserOpremaSerial;
    public TableColumn colUserOpremaMac;
    public TableColumn colUserOpremaNaplata;
    public TextField tServicePopust;
    public ComboBox<Adrese> cmbAdresa;
    public ComboBox<Mesta> cmbMesto;
    public ComboBox<Mesta> cmbMestoRacun;
    public ComboBox<Adrese> cmbAdresaRacun;
    public ComboBox<Adrese> cmbAdresaUsluge;
    public ComboBox<Mesta> cmbMestoUsluge;
    public Spinner spnAdresaUslugaBroj;
    public Spinner spnAdresaRacunBroj;
    public Spinner spnAdresaBroj;


    //TABS
    public AnchorPane anchroKorsinikPodaci;
    public AnchorPane anchorKorisnikUsluge;
    public AnchorPane anchorKorisnikUgovori;
    public Tab tabKorisnikUsluge;


    Logger LOGGER = LogManager.getLogger("EDIT_USERS");
    //JSON
    JSONObject jObj;
    JSONObject jObjAvService;
    JSONObject jObjAcService;
    //UGOVORI
    ObservableList<ugovori_types> ugovori_typesObservableList;
    ObservableList<ugovori_types> ugovori_user_typesObeservableList;
    ToggleGroup toogleOpremaGroup = new ToggleGroup();
    DateTimeFormatter dateTimeFormater = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    KorisnikPodaciController KorisnikPodaciController;
    KorisnikUslugeController korisnikUslugeController;
    KorisnikUgovoriController korisnikUgovoriController;
    private messageS mess;
    private Stage stage;
    private Services service;
    private StringConverter<LocalDate> dateConverter;
    private Alert alert;
    private ObservableList itemsAv = FXCollections.observableArrayList();
    private ObservableList itemsAc = FXCollections.observableArrayList();
    private ArrayList<ugovori_types> ugovori_typesArrayList;
    private ArrayList<ugovori_types> ugovori_user_typesArrayList;
    private JSONObject ugovoriObj;
    private JSONObject uugovoriObjUser;
    private ugovori_types ugovori;
    //UGOVOR EDIT
    private String resourceFXML;
    private ResourceBundle resource;
    private URL location;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;
        this.location = location;


    }


    public void loadKorisnikData() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/KorisnikPodaci.fxml"), resource);

        try {
            anchroKorsinikPodaci.getChildren().clear();
            anchroKorsinikPodaci.getChildren().add(fxmlLoader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }

        KorisnikPodaciController = fxmlLoader.getController();
        KorisnikPodaciController.client = client;
        KorisnikPodaciController.userEditID = userID;
        KorisnikPodaciController.setData();

    }

    public void loadKorisnikServices() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/KorisnikUsluge.fxml"), resource);


        try {
            anchorKorisnikUsluge.getChildren().clear();
            anchorKorisnikUsluge.getChildren().add(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        korisnikUslugeController = fxmlLoader.getController();
        korisnikUslugeController.client = client;
        korisnikUslugeController.userID = userID;
        korisnikUslugeController.setData();


    }

    public void loadKorisnikUgovori() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/KorisnikUgovori.fxml"), resource);

        try {
            anchorKorisnikUgovori.getChildren().clear();
            anchorKorisnikUgovori.getChildren().add(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        korisnikUgovoriController = fxmlLoader.getController();
        korisnikUgovoriController.client = client;
        korisnikUgovoriController.userID = userID;
        korisnikUgovoriController.set_data();


    }


    public void refreshUgovori(Event event) {

        korisnikUslugeController.refreshUgovori();
    }
}


