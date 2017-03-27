package JGemstone.interfaces;

import JGemstone.classes.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zoom on 9/7/16.
 */
public class KorisnikUplateController implements Initializable {
    public Button bClose;
    public Button bShowZaduzenja;
    public TableView<Uplate> tblZaduzenja;
    public TableColumn cDatumZaduzenja;
    public TableColumn cNazivServisa;
    public TableColumn cCena;
    public TableColumn cPopust;
    public TableColumn cZaUplatu;
    public Spinner cmbZaUplatu;
    public TableView<Uplate> tblUplate;
    public TableColumn cDatumUplate;
    public TableColumn cUplaceno;
    public TableColumn cMestoUplate;
    public TableColumn cOperater;
    public Button bDeleteUplata;
    public Label lUkupnoUplaceno;
    public Label lUkupanDug;
    public Label lZaUplatu;
    public DatePicker dtpDatumOd;
    public DatePicker dtpDatumDo;
    public DatePicker dtpDatumZaNaplatu;
    public TextField tCena;
    public Button bZaduzi;
    public TableColumn cZaMesec;
    public Button bUplatiServis;
    public Client client;
    public Users user;
    public Label lStatusDatumIsteka;
    public Label lStatusZaduzenOd;
    public Label lStatusUplatioOper;
    public Label lStatusDatumUplate;
    public TableColumn cUplateUplaceno;
    public Label lDugZaduzenja;
    public TextField tNazivUsluge;
    public Button bZaduziCustomService;
    DecimalFormat df = new DecimalFormat("#,##0.00");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter formatterBack = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter formatMonthYear = DateTimeFormatter.ofPattern("yyyy-MM");
    private ResourceBundle resource;
    private URL url;
    private Logger LOGGER = LogManager.getLogger("USER_PAYMENTS");
    private JSONObject jObj;
    private Double zaUplatu = 0.00;
    private Double ukupno_uplaceno = 0.00;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;
        this.url = url;


        cDatumZaduzenja.setCellValueFactory(new PropertyValueFactory<Uplate, String>("datumZaduzenja"));
        cNazivServisa.setCellValueFactory(new PropertyValueFactory<Uplate, String>("nazivPaket"));
        cCena.setCellValueFactory(new PropertyValueFactory<Uplate, Double>("cena"));
        cPopust.setCellValueFactory(new PropertyValueFactory<Uplate, Double>("popust"));
        cZaUplatu.setCellValueFactory(new PropertyValueFactory<Uplate, Double>("dug"));
        cZaMesec.setCellValueFactory(new PropertyValueFactory<Uplate, String>("zaMesec"));
        cUplaceno.setCellValueFactory(new PropertyValueFactory<Uplate, Double>("uplaceno"));
        cUplaceno.setCellFactory(tc -> new TableCell<Uplate, Double>() {
            protected void updateItem(Double uplaceno, boolean bool) {
                super.updateItem(uplaceno, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(df.format(uplaceno));
                }
            }
        });

        cCena.setCellFactory(tc -> new TableCell<Uplate, Double>() {
            protected void updateItem(Double uplata, boolean bool) {
                super.updateItem(uplata, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(df.format(uplata));
                }
            }
        });

        cPopust.setCellFactory(tc -> new TableCell<Uplate, Double>() {
            protected void updateItem(Double uplata, boolean bool) {
                super.updateItem(uplata, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(df.format(uplata) + "%");
                }
            }
        });

        cZaUplatu.setCellFactory(tc -> new TableCell<Uplate, Double>() {
            protected void updateItem(Double zaUplatu, boolean bool) {
                super.updateItem(zaUplatu, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(df.format(zaUplatu));
                }
            }
        });


        tblZaduzenja.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Uplate>() {
            @Override
            public void changed(ObservableValue<? extends Uplate> observable, Uplate oldValue, Uplate newValue) {
                if (tblZaduzenja.getSelectionModel().getSelectedIndex() == -1) {
                    return;
                }

                Double uplaceno = 0.00;
                Double dug = 0.00;
                uplaceno = tblZaduzenja.getSelectionModel().getSelectedItem().getUplaceno();

                //BUTTON DISABLE UPLATE
                cmbZaUplatu.getEditor().setText(df.format(newValue.getDug() - uplaceno));
                if (newValue.getDug() == 0) {
                    bUplatiServis.setDisable(true);
                } else {
                    cmbZaUplatu.setDisable(false);
                    bUplatiServis.setDisable(false);
                }

                //STATUS UPLATE
                Uplate uplate = tblZaduzenja.getSelectionModel().getSelectedItem();
                if (uplate.getDatumUplate().equals("1000-01-01 00:00:00.0")) {
                    lStatusDatumUplate.setText("--");
                } else {
                    lStatusDatumUplate.setText(uplate.getDatumUplate());
                }
                lStatusUplatioOper.setText(uplate.getOperater());
                lStatusZaduzenOd.setText(uplate.getZaduzenOd());
                lStatusDatumIsteka.setText(get_datum_isteka_servicsa(uplate.getId_ServiceUser()));

                //disable uplate if cena i zaduzenje je isto
                uplaceno = uplate.getUplaceno();
                dug = uplate.getDug();
                if (uplaceno.equals(dug)) {
                    bUplatiServis.setDisable(true);
                    cmbZaUplatu.setDisable(true);
                } else {
                    bUplatiServis.setDisable(false);
                    cmbZaUplatu.setDisable(false);
                }

            }
        });


        cmbZaUplatu.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, Double.MAX_VALUE));
        cmbZaUplatu.setEditable(true);


        cDatumUplate.setCellValueFactory(new PropertyValueFactory<Uplate, String>("datumUplate"));
        cUplateUplaceno.setCellValueFactory(new PropertyValueFactory<Uplate, Double>("uplaceno"));
        cUplateUplaceno.setCellFactory(tc -> new TableCell<Uplate, Double>() {
            protected void updateItem(Double uplaceno, boolean bool) {
                super.updateItem(uplaceno, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(df.format(uplaceno));
                }
            }
        });
        cMestoUplate.setCellValueFactory(new PropertyValueFactory<Uplate, String>("mestoUplate"));
        cOperater.setCellValueFactory(new PropertyValueFactory<Uplate, String>("operater"));

        dtpDatumZaNaplatu.setValue(LocalDate.now());


    }

    private String get_datum_isteka_servicsa(int id) {
        jObj = new JSONObject();
        jObj.put("action", "get_datum_isteka_servisa");
        jObj.put("serviceID", id);

        jObj = client.send_object(jObj);


        if (jObj.has("datumIsteka")) {
            return jObj.getString("datumIsteka");
        } else {
            return "--";
        }


    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) bClose.getScene().getWindow();
        stage.close();
    }


    public void show_data() {
        ObservableList dataZaduzenja = FXCollections.observableArrayList(get_Zaduzenja());
        tblZaduzenja.setItems(dataZaduzenja);


        ObservableList dataUplate = FXCollections.observableArrayList(getUserUplate());
        tblUplate.setItems(dataUplate);



        Double ukupno = 0.00;
        ukupno = zaUplatu - ukupno_uplaceno;
        lUkupanDug.setText(df.format(ukupno));


    }




    ArrayList<Uplate> get_Zaduzenja() {
        zaUplatu = 0.00;
        jObj = new JSONObject();
        jObj.put("action", "get_zaduzenja_user");
        jObj.put("userID", user.getId());
        Double dug = 0.00;

        jObj = client.send_object(jObj);

        ArrayList<Uplate> uplate = new ArrayList();


        if (jObj.has("Error")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));
            return uplate;
        }

        Uplate uplata;
        JSONObject uplataObj;

        for (int i = 0; i < jObj.length(); i++) {
            uplataObj = (JSONObject) jObj.get(String.valueOf(i));
            uplata = new Uplate();
            uplata.setId(uplataObj.getInt("id"));
            uplata.setId_ServiceUser(uplataObj.getInt("id_ServiceUser"));
            uplata.setId_service(uplataObj.getInt("id_service"));
            uplata.setNazivPaket(uplataObj.getString("nazivPaketa"));
            uplata.setDatumZaduzenja(uplataObj.getString("datumZaduzenja"));
            uplata.setUserID(uplataObj.getInt("userID"));
            uplata.setPopust(uplataObj.getDouble("popust"));
            uplata.setPaketType(uplataObj.getString("paketType"));
            uplata.setCena(uplataObj.getDouble("cena"));
            uplata.setDatumUplate(uplataObj.getString("datumUplate"));
            uplata.setDug(uplataObj.getDouble("dug"));
            zaUplatu += uplataObj.getDouble("dug");
            dug = dug + uplataObj.getDouble("dug");
            uplata.setUplaceno(uplataObj.getDouble("uplaceno"));
            dug = dug - uplataObj.getDouble("uplaceno");
            uplata.setOperater(uplataObj.getString("operater"));
            uplata.setZaMesec(uplataObj.getString("zaMesec"));
            uplata.setZaduzenOd(uplataObj.getString("zaduzenOd"));
            uplate.add(uplata);

        }
        lZaUplatu.setText(df.format(zaUplatu));
        lDugZaduzenja.setText(df.format(dug));
        return uplate;
    }




    private ArrayList<Uplate> getUserUplate() {
        ukupno_uplaceno = 0.00;
        jObj = new JSONObject();
        jObj.put("action", "get_uplate_user");
        jObj.put("userID", user.getId());

        jObj = client.send_object(jObj);

        Uplate uplate;
        ArrayList<Uplate> uplateArr = new ArrayList();
        JSONObject uplateObj;

        for (int i = 0; i < jObj.length(); i++) {
            uplate = new Uplate();
            uplateObj = (JSONObject) jObj.get(String.valueOf(i));
            uplate.setUplaceno(uplateObj.getDouble("uplaceno"));
            ukupno_uplaceno += uplateObj.getDouble("uplaceno");
            uplate.setMestoUplate(uplateObj.getString("mesto"));
            uplate.setOperater(uplateObj.getString("operater"));
            uplate.setDatumUplate(uplateObj.getString("datumUplate"));
            uplate.setId(uplateObj.getInt("id"));
            uplateArr.add(uplate);
        }

        lUkupnoUplaceno.setText(df.format(ukupno_uplaceno));
        return uplateArr;

    }


    public void deleteUplata(ActionEvent actionEvent) {
        Uplate uplata = tblUplate.getSelectionModel().getSelectedItem();
        jObj = new JSONObject();
        jObj.put("action", "DELETE_UPLATA");
        jObj.put("uplataID", uplata.getId());
        jObj = client.send_object(jObj);

        if (jObj.has("Error")) {
            AlertUser.error("UPLATA NIJE UZVRSENA", jObj.getString("Error"));
            return;
        } else {
            AlertUser.info("UPLATA IZBRISANA", "Uplata je izbrisana");
        }
        show_data();
    }

    public void uplatiServis(ActionEvent actionEvent) {

        if (tblZaduzenja.getSelectionModel().getSelectedIndex() == -1) {
            AlertUser.error("GRESKA", "Nije izabran servis");
            return;
        }

        Uplate uplata = tblZaduzenja.getSelectionModel().getSelectedItem();
        jObj = new JSONObject();

        jObj.put("action", "uplata_servisa");
        jObj.put("userID", user.getId());
        jObj.put("id", uplata.getId());
        Number numbUplacenoNov = null;
        try {
            numbUplacenoNov = df.parse(cmbZaUplatu.getEditor().getText());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Number numbZauplatu = uplata.getDug();
        Number numbUplaceno = uplata.getUplaceno();

        Double ukupnoUplaceno = numbUplaceno.doubleValue() + numbUplacenoNov.doubleValue();

        LOGGER.info("ukupnouplaceno: " + ukupnoUplaceno + " uplaceno: " + numbUplaceno + " zaUplatu: " + numbZauplatu + " uplacenoNov: " + numbUplacenoNov);

        if (ukupnoUplaceno > numbZauplatu.doubleValue()) {
            AlertUser.error("SUMA NIJE JEDNAKA ZA DUGOM", "Uplata ne može biti veca od zaduženja");
            return;
        }
        jObj.put("uplaceno", ukupnoUplaceno);
        jObj.put("dug", uplata.getDug());
        jObj.put("paketType", uplata.getPaketType());
        jObj.put("userServiceID", uplata.getId_ServiceUser());
        jObj.put("zaMesec", uplata.getZaMesec());

        jObj = client.send_object(jObj);

        if (jObj.has("Error")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));
        } else {
            AlertUser.info("UPLACENO", "Uplata izvrsena");
        }

        show_data();


    }


    public void zaduziCustomService(ActionEvent actionEvent) {

        if (tNazivUsluge.getText().isEmpty() || dtpDatumZaNaplatu.getEditor().getText().isEmpty()) {
            AlertUser.warrning("POLJA SU PRAZNA", "Datum i naziv ne smeju biti prazni");
            return;
        }


        jObj = new JSONObject();

        LocalDate datumZaMesec = dtpDatumZaNaplatu.getValue();


        jObj.put("action", "zaduzi_servis_manual");
        jObj.put("nazivPaketa", tNazivUsluge.getText());
        jObj.put("userID", user.getId());
        jObj.put("cena", Double.valueOf(tCena.getText()));
        jObj.put("uplaceno", false);
        jObj.put("zaMesec", formatMonthYear.format(datumZaMesec));


        jObj = client.send_object(jObj);
        if (jObj.has("Error")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));
        } else {
            AlertUser.info("KORISNIK ZADUZEN", "Korisnik zaduzenje izvrseno");
        }


        show_data();
    }


    public void ShowZaduzenja(ActionEvent actionEvent) {
    }

    public void zaduziUsluguUnapred(ActionEvent actionEvent) {
        NewInterface uslugaZaduzivanjeInterface = new NewInterface("/JGemstone/resources/fxml/UslugaZaduzivanje.fxml", "Zaduzivanje usluge", resource);
        UslugeZaduzivenjeController uslugeZaduzivenjeController = uslugaZaduzivanjeInterface.getLoader().getController();
        uslugeZaduzivenjeController.client = client;
        uslugeZaduzivenjeController.user = user;
        uslugeZaduzivenjeController.show_data();
        uslugaZaduzivanjeInterface.getStage().showAndWait();
    }
}




