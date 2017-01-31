package JGemstone.interfaces;

import JGemstone.classes.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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


    public Button bClose;
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




    Logger LOGGER = LogManager.getLogger("EDIT_USERS");
    DateTimeFormatter simpleDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    //JSON
    JSONObject jObj;
    JSONObject jObjAvService;
    JSONObject jObjAcService;
    //UGOVORI
    ObservableList<ugovori_types> ugovori_typesObservableList;
    ObservableList<ugovori_types> ugovori_user_typesObeservableList;
    ToggleGroup toogleOpremaGroup = new ToggleGroup();
    DateTimeFormatter dateTimeFormater = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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
        bClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage = (Stage) bClose.getScene().getWindow();
                stage.close();
            }
        });




    }


    public void loadKorisnikData() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/EditKorisnikPodaci.fxml"), resource);

        try {
            anchroKorsinikPodaci.getChildren().clear();
            anchroKorsinikPodaci.getChildren().add(fxmlLoader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }

        EditKorisnikPodaciController editKorisnikPodaciController = fxmlLoader.getController();
        editKorisnikPodaciController.client = client;
        editKorisnikPodaciController.userEditID = userID;
        editKorisnikPodaciController.setData();

    }


}


