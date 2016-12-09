package JGemstone.interfaces;

import JGemstone.classes.*;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

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
    public TextField tUserName;
    public TextField tFullName;
    public TextField tAdresa;
    public TextField tMesto;
    public TextField tPostBr;
    public TextField tTelMob;
    public TextField tTelFix;
    public TextField tBrLk;
    public TextField tJMBG;
    public TextArea tOstalo;
    public Client client;
 //   public ListView<Services> lAvServices;
 //   public ListView<Services> lActiveServices;
    public Button bAddService;
    public Button bRemoveService;
    public TextField tAdresaRacun;
    public TextField tAdresaKoriscenja;
    public TextField tJbroj;
    public Tab tabKorisnickiPodaci;
    public TabPane tabPaneKorisnickiPodaci;
    public Button bUpdateUser;
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
    public Tab tabFakture;
    public JFXListView<Services> lAvServices;
    public JFXListView<Services> lActiveServices;

    private messageS mess;
    private Stage stage;

    private Services service;
    private StringConverter<LocalDate> dateConverter;

    private Alert alert;

    Logger LOGGER = LogManager.getLogger("EDIT_USERS");

    DateTimeFormatter simpleDateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    //JSON
    JSONObject jObj;
    JSONObject jObjAvService;
    JSONObject jObjAcService;


    private ObservableList itemsAv = FXCollections.observableArrayList();
    private ObservableList itemsAc = FXCollections.observableArrayList();

    //UGOVORI
    ObservableList<ugovori_types> ugovori_typesObservableList;
    ObservableList<ugovori_types> ugovori_user_typesObeservableList;

    private ArrayList<ugovori_types> ugovori_typesArrayList;
    private ArrayList<ugovori_types> ugovori_user_typesArrayList;

    private JSONObject ugovoriObj;
    private JSONObject uugovoriObjUser;
    private ugovori_types ugovori;

    //UGOVOR EDIT
    private String resourceFXML;


    private ResourceBundle resource;
    private URL location;
    public int userID;

    public EditKorisnikController() {
    }


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


        tdDatumRodjenja.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                if (object == null || object.toString().trim().isEmpty()) {
                    return "01-01-1700";
                }

                return simpleDateFormatter.format(object);
            }

            @Override
            public LocalDate fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return LocalDate.parse("01-01-1700", simpleDateFormatter);
                }
                return LocalDate.parse(string, simpleDateFormatter);
            }
        });


        cbNaziv.setCellFactory(new Callback<ListView<ugovori_types>, ListCell<ugovori_types>>() {
            @Override
            public ListCell<ugovori_types> call(ListView<ugovori_types> param) {
                return new ListCell<ugovori_types>(){
                    @Override
                    protected  void updateItem(ugovori_types ugovor, boolean empty){
                        super.updateItem(ugovor, empty);
                        if(!empty){
                            setText(ugovor.getNaziv());
                        }else{
                            setText("");
                        }
                    }
                };

            }


        });


        tBrUg.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("[0-9]*")){
                    tBrUg.setText(oldValue);
                }
            }
        });

        tblUgv.setEditable(true);
        cid.setCellValueFactory(new PropertyValueFactory<ugovori_types, Integer>("id"));
        cBr.setCellValueFactory(new PropertyValueFactory<ugovori_types, Integer>("br"));
        cOd.setCellValueFactory(new PropertyValueFactory<ugovori_types, String>("pocetakUgovora"));
        cDo.setCellValueFactory(new PropertyValueFactory<ugovori_types, String>("krajUgovora"));
        cuKomentar.setCellValueFactory(new PropertyValueFactory<ugovori_types, String>("Komentar"));
        cNaziv.setCellValueFactory(new PropertyValueFactory<ugovori_types,String>("Naziv"));
    }

    public void show_usr() {
        jObj = new JSONObject();
        jObj.put("action", "get_user_data");
        jObj.put("userId", userID);

        jObj = client.send_object(jObj);

        tUserName.setText(jObj.getString("userName"));
        if (jObj.has("fullName"))
            tFullName.setText(jObj.getString("fullName"));
        //       tDatumRodjenja.setText(jObj.getString("datumRodjenja"));
        //LocalDate localDate = LocalDate.parse(jObj.getString("datumRodjenja"), simpleDatePickerFormater);
        if (jObj.has("datumRodjenja")) {
            if (!jObj.getString("datumRodjenja").isEmpty()) {
                tdDatumRodjenja.setValue(LocalDate.parse(jObj.getString("datumRodjenja"), simpleDateFormatter));
            } else {
                tdDatumRodjenja.setValue(LocalDate.parse("01-01-1700", simpleDateFormatter));
            }
        } else {
            tdDatumRodjenja.setValue(LocalDate.parse("01-01-1700", simpleDateFormatter));
        }
        if (jObj.has("adresa"))
            tAdresa.setText(jObj.getString("adresa"));
        if (jObj.has("mesto"))
            tMesto.setText(jObj.getString("mesto"));
        if (jObj.has("postBr"))
            tPostBr.setText(jObj.getString("postBr"));
        if (jObj.has("telMob"))
            tTelMob.setText(jObj.getString("telMob"));
        if (jObj.has("telFix"))
            tTelFix.setText(jObj.getString("telFix"));
        if (jObj.has("brLk"))
            tBrLk.setText(jObj.getString("brLk"));
        if (jObj.has("JMBG"))
            tJMBG.setText(jObj.getString("JMBG"));
        if (jObj.has("adresaRacuna"))
            tAdresaRacun.setText(jObj.getString("adresaRacuna"));
        if (jObj.has("adresaKoriscenja"))
            tAdresaKoriscenja.setText(jObj.getString("adresaKoriscenja"));
        if (jObj.has("ostalo"))
            tOstalo.setText(jObj.getString("ostalo"));
        if (jObj.has("komentar"))
            taKomentar.setText(jObj.getString("komentar"));
        if (jObj.has("jBroj"))
            tJbroj.setText(String.valueOf(jObj.getInt("jBroj")));


        jObj = new JSONObject();
        jObj.put("action", "get_firma");
        jObj.put("userId", userID);

        jObj= client.send_object(jObj);

        if(!jObj.has("Message")){
            set_firma(jObj);
        }
        show_services();
        set_items();


    }

    private void set_firma(JSONObject jObj) {
        if(jObj.has("nazivFirme"))
        tNazivFime.setText(jObj.getString("nazivFirme"));
        if(jObj.has("kontaktOsoba"))
        tKontaktOsoba.setText(jObj.getString("kontaktOsoba"));
        if(jObj.has("kodBanke"))
        tKodBanke.setText(jObj.getString("kodBanke"));
        if(jObj.has("pib"))
        tPIB.setText(jObj.getString("pib"));
        if(jObj.has("maticniBrFirme"))
        tMaticniBroj.setText(jObj.getString("maticniBrFirme"));
        if(jObj.has("brTekuciRacun"))
        tTekuciRacun.setText(jObj.getString("brTekuciRacun"));
        if(jObj.has("brojFakture")){
            tBrojFakture.setText(jObj.getString("brojFakture"));
            tBrojFakture.setEditable(false);
        }else{
            tBrojFakture.setEditable(true);
            tabFakture.setDisable(true);
        }

    }


    public void show_services() {


        ArrayList<Services> servicesArrayListAv = new ArrayList<Services>();
        ArrayList<Services> servicesArrayListAc = new ArrayList<Services>();

        lActiveServices.getItems().clear();
        lAvServices.getItems().clear();



        jObj = new JSONObject();
        jObj.put("action", "get_services");
        jObj.put("serviceName", "");

        jObj = client.send_object(jObj);

        jObjAvService = new JSONObject();
        service = new Services();
        for (int i = 0; i < jObj.length(); i++) {
            jObjAvService = (JSONObject) jObj.get(String.valueOf(i));
            service = new Services();
            service.setId(jObjAvService.getInt("id"));
            service.setNaziv(jObjAvService.getString("naziv"));
            service.setCena((double) jObjAvService.getInt("cena"));
            service.setOpis(jObjAvService.getString("opis"));
            itemsAv.add(service);
        }

        lAvServices.setItems(itemsAv);


        lAvServices.setCellFactory(new Callback<ListView<Services>, ListCell<Services>>() {
            @Override
            public ListCell<Services> call(ListView<Services> param) {
                ListCell<Services> cell = new ListCell<Services>() {
                    @Override
                    protected void updateItem(Services s, boolean bln) {
                        super.updateItem(s, bln);
                        if (s != null) {
                            setGraphic(new Label(s.getNaziv()));
                        }
                    }
                };
                return cell;
            }
        });

        jObj = new JSONObject();
        jObj.put("action", "get_user_services");
        jObj.put("userID", userID);

        jObj = client.send_object(jObj);


        jObjAcService = new JSONObject();
        service = new Services();
        LOGGER.info("recive: "+jObj);
        LOGGER.info("object size"+jObj.length());
        for (int i = 0; i < jObj.length(); i++) {
            jObjAcService = (JSONObject) jObj.get(String.valueOf(i));
            service = new Services();
            service.setId(jObjAcService.getInt("id"));
            service.setService_id(jObjAcService.getInt("serviceId"));
            service.setNaziv(jObjAcService.getString("naziv"));
            service.setCena(jObjAcService.getDouble("cena"));
            service.setOpis(jObjAcService.getString("opis"));
            itemsAc.add(service);
        }


        lActiveServices.setItems(itemsAc);

        lActiveServices.setCellFactory(new Callback<ListView<Services>, ListCell<Services>>() {
            @Override
            public ListCell<Services> call(ListView<Services> param) {
                ListCell<Services> cell = new ListCell<Services>() {
                    @Override
                    protected void updateItem(Services s, boolean bln) {
                        super.updateItem(s, bln);
                        if (s != null) {
                            setText(s.getNaziv());
                        }
                    }
                };
                return cell;
            }
        });

    }


    public void addService(ActionEvent actionEvent) {


        int id = lAvServices.getSelectionModel().getSelectedItem().getId();

        jObj = new JSONObject();
        jObj.put("action", "add_service_to_user");
        jObj.put("id", id);
        jObj.put("userID", userID);


        jObj = client.send_object(jObj);

        LOGGER.info(jObj.get("message"));
        show_services();

    }

    public void removeService(ActionEvent actionEvent) {
        int id = lActiveServices.getSelectionModel().getSelectedItem().getId();


        jObj = new JSONObject();
        jObj.put("action", "delete_service_user");
        jObj.put("id", id);
        jObj.put("userID", userID);

        client.send_object(jObj);
        show_services();


    }

    public void UpdateUser(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "update_user");
        jObj.put("userName", tUserName.getText());
        jObj.put("userID", userID);
        jObj.put("fullName", tFullName.getText());
        jObj.put("datumRodjenja", tdDatumRodjenja.getValue().format(simpleDateFormatter));
        jObj.put("adresa", tAdresa.getText());
        jObj.put("mesto", tMesto.getText());
        jObj.put("postBr", tPostBr.getText());
        jObj.put("telMobilni", tTelMob.getText());
        jObj.put("telFixni", tTelFix.getText());
        jObj.put("brLk", tBrLk.getText());
        jObj.put("JMBG", tJMBG.getText());
        jObj.put("adresaKoriscenja", tAdresaKoriscenja.getText());
        jObj.put("adresaRacuna", tAdresaRacun.getText());
        jObj.put("ostalo", tOstalo.getText());
        jObj.put("komentar", taKomentar.getText());
        jObj.put("jBroj", tJbroj.getText());


        jObj = client.send_object(jObj);
        LOGGER.info(jObj.get("message"));

        if (jObj.get("message").equals("USER_EXIST")) {
            alert = new Alert(Alert.AlertType.WARNING, "Korisnik sa istim brojem postoji", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("GREŠKA!");
            alert.initOwner(stage);
            alert.showAndWait();
        }
    }

    public void bAddUgovor(ActionEvent actionEvent) {
        if(tBrUg.getText().isEmpty()){
            alert_empty_items();
            return;
        }
        if(cbNaziv.getSelectionModel().getSelectedItem().getNaziv().isEmpty()){
            alert_empty_items();
            return;
        }
        if(diOd.getValue().toString().isEmpty()){
            alert_empty_items();
            return;
        }
        if(tiDo.getValue().toString().isEmpty()){
            alert_empty_items();
            return;
        }

        jObj = new JSONObject();
        jObj.put("action", "get_single_ugovor");
        jObj.put("idUgovora", cbNaziv.getValue().getId());

        jObj = client.send_object(jObj);


        ugovori_types ugovoriTypes = new ugovori_types();

        ugovoriTypes.setId(jObj.getInt("idUgovora"));
        ugovoriTypes.setNaziv(jObj.getString("nazivUgovora"));
        ugovoriTypes.setTextUgovora(jObj.getString("textUgovora"));




        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2017-01-01",formater);
        tiDo.setValue(date);
        LOGGER.info("DATE:" + tiDo.getValue().toString());

        resourceFXML = "/JGemstone/resources/fxml/UgovoriEdit.fxml";
        NewInterface ugovorEditInterface = new NewInterface(0,0, resourceFXML, "Ugovor "+ugovoriTypes.getNaziv(), resource);
        UgovoriEditController ugovorEditController = ugovorEditInterface.getLoader().getController();
        ugovorEditController.client = client;
        ugovorEditController.htmlUgovor.setHtmlText(ugovoriTypes.getTextUgovora());
        ugovorEditInterface.getStage().showAndWait();


        jObj = new JSONObject();
        jObj.put("action", "add_user_ugovor");
        jObj.put("nazivUgovora", ugovoriTypes.getNaziv());
        jObj.put("textUgovora", ugovorEditController.htmlUgovor.getHtmlText());
        jObj.put("odDate", diOd.getValue().toString());
        jObj.put("doDate", tiDo.getValue().toString());
        jObj.put("userID", userID);
        jObj.put("brUgovora", tBrUg.getText());
        jObj.put("komentar", tUgKomentar.getText());



        jObj = client.send_object(jObj);
        LOGGER.info(jObj.get("Message"));
        set_items();





    }

    private void alert_empty_items() {
            alert = new Alert(Alert.AlertType.WARNING, "Polja ne mogu biti prazna ili broj ugovora ne moze biti nista osim brojeva", ButtonType.OK);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("GREŠKA!");
            alert.initOwner(stage);
            alert.showAndWait();
    }


    private void set_items() {

        //UGOVORI
        jObj = new JSONObject();
        jObj.put("action", "get_ugovori");
        jObj = client.send_object(jObj);

        ugovori_typesArrayList = new ArrayList();

        for (int i = 0; i < jObj.length(); i++) {
            ugovoriObj = (JSONObject) jObj.get(String.valueOf(i));
            ugovori = new ugovori_types();
            ugovori.setId(ugovoriObj.getInt("idUgovora"));
            ugovori.setBr(i + 1);
            ugovori.setNaziv(ugovoriObj.getString("nazivUgovora"));
            ugovori.setTextUgovora(ugovoriObj.getString("textUgovora"));
            ugovori_typesArrayList.add(ugovori);
        }

        ugovori_typesObservableList = FXCollections.observableArrayList(ugovori_typesArrayList);
        tblUgv.setItems(ugovori_typesObservableList);
        cbNaziv.setItems(ugovori_typesObservableList);
        cbNaziv.getSelectionModel().selectFirst();

        //UGOVORI USER
        jObj = new JSONObject();
        jObj.put("action", "get_ugovori_user");
        jObj.put("userID", userID);
        jObj = client.send_object(jObj);

        ugovori_user_typesArrayList = new ArrayList();

        for (int i=0; i<jObj.length();i++){
            uugovoriObjUser = (JSONObject) jObj.get(String.valueOf(i));
            ugovori = new ugovori_types();
            ugovori.setId(uugovoriObjUser.getInt("idUgovora"));
            ugovori.setNaziv(uugovoriObjUser.getString("nazivUgovora"));
            ugovori.setPocetakUgovora(uugovoriObjUser.getString("pocetakUgovora"));
            ugovori.setKrajUgovora(uugovoriObjUser.getString("krajUgovora"));
            ugovori.setKomentar(uugovoriObjUser.getString("komentar"));
            ugovori_user_typesArrayList.add(ugovori);
        }
        ugovori_user_typesObeservableList = FXCollections.observableArrayList(ugovori_user_typesArrayList);
        tblUgv.setItems(ugovori_user_typesObeservableList);




    }

    public void pregledUgovora(ActionEvent actionEvent) {
        ugovori_types ugovor = (ugovori_types) tblUgv.getSelectionModel().getSelectedItem();
        if(ugovor == null){
            LOGGER.info("nothing selected");
            return;
        }

        resourceFXML = "/JGemstone/resources/fxml/UgovoriPreview.fxml";
        NewInterface ugovoriPreviewInterface = new NewInterface(0,0, resourceFXML, "Izmena i štampa ugovora", resource);
        UgovoriPreviewController ugovoriPreviewController =  ugovoriPreviewInterface.getLoader().getController();

        jObj= new JSONObject();
        jObj.put("action", "get_ugovor_single_user");
        jObj.put("idUgovora", ugovor.getId());

        jObj = client.send_object(jObj);


        ugovoriPreviewController.htmlEditUgovor.setHtmlText(jObj.getString("textUgovora"));
        ugovoriPreviewInterface.getStage().showAndWait();

        jObj = new JSONObject();
        jObj.put("action", "update_ugovor");
        jObj.put("idUgovora", ugovor.getId());
        jObj.put("textUgovora", ugovoriPreviewController.htmlEditUgovor.getHtmlText());

        LOGGER.info(jObj);
        jObj = client.send_object(jObj);


        LOGGER.info(jObj.getString("Message"));

    }

    public void snimiFirma(ActionEvent actionEvent) {
        jObj = new JSONObject();

        jObj.put("action", "save_firma");
        jObj.put("nazivFirme", tNazivFime.getText());
        jObj.put("kontaktOsoba", tKontaktOsoba.getText());
        jObj.put("kodBanke", tKodBanke.getText());
        jObj.put("pib", tPIB.getText());
        jObj.put("maticniBrFirme",tMaticniBroj.getText());
        jObj.put("brTekuciRacun", tTekuciRacun.getText());
        jObj.put("userID", userID);
        jObj.put("brojFakture", tBrojFakture.getText());

        jObj = client.send_object(jObj);
        LOGGER.info(jObj.getString("Message"));
        show_usr();

    }
}
