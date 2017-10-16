package net.yuvideo.jgemstone.client.Controllers;

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
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.*;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * Created by zoom on 9/7/16.
 */
public class KorisnikUplateController implements Initializable {
    public Button bClose;
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
    public Label lUkupnoUplaceno;
    public Label lUkupanDug;
    public Label lZaUplatu;
    public DatePicker dtpDatumZaNaplatu;
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
    public Button bZaduziCustomService;
    public ComboBox<ServicesUser> cmbUserServices;
    public TextField tNazivUslugeCustom;
    public DatePicker dtpDatumZaNaplatuCustom;
    public TextField tCenaCustom;
    public TextField tRate;
    public CheckBox chkSveUplate;
    DecimalFormat df = new DecimalFormat("#,##0.00");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter formatterBack = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter formatMonthYear = DateTimeFormatter.ofPattern("yyyy-MM");
    private ResourceBundle resource;
    private URL url;
    private JSONObject jObj;
    private Double zaUplatu = 0.00;
    private Double ukupno_uplaceno = 0.00;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;
        this.url = url;

        dtpDatumZaNaplatu.setValue(LocalDate.now());
        dtpDatumZaNaplatuCustom.setValue(LocalDate.now());
        tRate.setText("0");
        tCenaCustom.setText("0.00");

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
                    TableRow<Uplate> currentRow = getTableRow();
                    Uplate uplate = currentRow.getItem();
                    if (uplate == null) {
                        return;
                    }
                    setText(df.format(uplaceno));
                    if (uplate.getUplaceno() == 0) {
                        currentRow.setStyle("-fx-background-color: red");
                    } else if (uplate.getUplaceno() > 0 && uplate.getUplaceno() < uplate.getDug()) {
                        currentRow.setStyle("-fx-background-color: darkorange");
                    } else if (uplate.getUplaceno() == uplate.getDug()) {
                        currentRow.setStyle("-fx-background-color: darkgreen");
                    }
                }

                tblZaduzenja.refresh();
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
                } else if (uplate.getDatumUplate() != null) {
                    lStatusDatumUplate.setText(uplate.getDatumUplate());
                } else {
                    lStatusDatumUplate.setText("--");
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


        cmbUserServices.setConverter(new StringConverter<ServicesUser>() {
            @Override
            public String toString(ServicesUser object) {
                return object.getNazivPaketa();
            }

            @Override
            public ServicesUser fromString(String string) {
                ServicesUser sUser = new ServicesUser();
                sUser.setNazivPaketa(string);
                return null;
            }
        });


        tRate.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[0-9]*")) {
                    tRate.setText(oldValue);
                    return;
                }
                try {
                    double meseciRate = Double.parseDouble(newValue);
                    tRate.setText(newValue);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    tRate.setText(oldValue);
                }
            }
        });


        tCenaCustom.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    Double.parseDouble(newValue);
                    tCenaCustom.setText(newValue);
                } catch (Exception e) {
                    tCenaCustom.setText(oldValue);
                }
            }
        });

        chkSveUplate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                show_data();
            }
        });

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
        tblZaduzenja.refresh();
        tblZaduzenja.setItems(dataZaduzenja);


        ObservableList dataUplate = FXCollections.observableArrayList(getUserUplate());
        tblZaduzenja.refresh();
        tblUplate.setItems(dataUplate);

        ObservableList<ServicesUser> userServices = FXCollections.observableArrayList(get_user_services());
        cmbUserServices.setItems(userServices);


        Double ukupno = 0.00;
        ukupno = zaUplatu - ukupno_uplaceno;
        lUkupanDug.setText(df.format(ukupno));


    }


    ArrayList<Uplate> get_Zaduzenja() {
        zaUplatu = 0.00;
        jObj = new JSONObject();
        jObj.put("action", "get_zaduzenja_user");
        jObj.put("userID", user.getId());
        jObj.put("sveUplate", chkSveUplate.isSelected());
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
            uplata.setSkipProduzenje(uplataObj.getBoolean("skipProduzenje"));
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
	    jObj.put("id_ServiceUser", uplata.getId_ServiceUser());
        jObj.put("paketType", uplata.getPaketType());
        jObj.put("skipProduzenje", uplata.isSkipProduzenje());
        Double numbUplacenoNov = null;
        Double numbZauplatu = uplata.getDug();
        Double numbUplaceno = uplata.getUplaceno();
        try {
            numbUplacenoNov = df.parse(cmbZaUplatu.getEditor().getText()).doubleValue();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Double ukupnoUplaceno = numbUplaceno.doubleValue() + numbUplacenoNov.doubleValue();


        if (ukupnoUplaceno > numbZauplatu) {
            AlertUser.error("SUMA NIJE JEDNAKA SA DUGOM", "Uplata ne može biti veca od zaduženja");
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

        if (tNazivUslugeCustom.getText().isEmpty() || dtpDatumZaNaplatuCustom.getEditor().getText().isEmpty()) {
            AlertUser.warrning("POLJA SU PRAZNA", "Datum i naziv ne smeju biti prazni");
            return;
        }


        jObj = new JSONObject();

        LocalDate datumZaMesec = dtpDatumZaNaplatuCustom.getValue();
        Calendar cal = Calendar.getInstance();
        cal.set(dtpDatumZaNaplatuCustom.getValue().getYear(), dtpDatumZaNaplatuCustom.getValue().getMonthValue(), dtpDatumZaNaplatuCustom.getValue().getDayOfMonth());
        cal.set(Calendar.DAY_OF_MONTH, 1);


        jObj.put("action", "zaduzi_servis_manual");
        jObj.put("nazivPaketa", tNazivUslugeCustom.getText());
        jObj.put("cena", tCenaCustom.getText());
        jObj.put("uplaceno", false);
        jObj.put("zaMesec", formatMonthYear.format(datumZaMesec));
        jObj.put("paketType", "CUSTOM");
        jObj.put("userID", user.getId());
        jObj.put("rate", Integer.valueOf(tRate.getText()));


        jObj = client.send_object(jObj);
        if (jObj.has("Error")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));
        } else {
            AlertUser.info("KORISNIK ZADUZEN", "Korisnik zaduzenje izvrseno");
        }


        show_data();
    }



    public ArrayList<ServicesUser> get_user_services() {
        jObj = new JSONObject();
        jObj.put("action", "get_user_services");
        jObj.put("userID", user.getId());
        jObj = client.send_object(jObj);

        JSONObject userServiceObj;
        ArrayList<ServicesUser> userServiceArr = new ArrayList();
        ServicesUser userService;

        for (int i = 0; i < jObj.length(); i++) {
            userServiceObj = (JSONObject) jObj.get(String.valueOf(i));
            userService = new ServicesUser();
            userService.setId(userServiceObj.getInt("id"));
            userService.setBrUgovora(userServiceObj.getString("brojUgovora"));
            userService.setCena(userServiceObj.getDouble("cena"));
            userService.setPopust(userServiceObj.getDouble("popust"));
            userService.setOperater(userServiceObj.getString("operName"));
            userService.setDatum(userServiceObj.getString("date_added"));
            userService.setNazivPaketa(userServiceObj.getString("nazivPaketa"));
            userService.setAktivan(userServiceObj.getBoolean("aktivan"));
            if (userServiceObj.has("idUniqueName"))
                userService.setIdDTVCard(userServiceObj.getString("idUniqueName"));
            userService.setObracun(userServiceObj.getBoolean("obracun"));
            userService.setAktivan(userServiceObj.getBoolean("aktivan"));
            userService.setProduzenje(userServiceObj.getInt("produzenje"));
            if (userServiceObj.has("id_service"))
                userService.setId_Service(userServiceObj.getInt("id_service"));
            if (userServiceObj.has("BOX_service"))
                userService.setBox(userServiceObj.getBoolean("BOX_service"));
            if (userServiceObj.has("box_id"))
                userService.setBox_id(userServiceObj.getInt("box_id"));
            userService.setPaketType(userServiceObj.getString("paketType"));
            userService.setLinkedService(userServiceObj.getBoolean("linkedService"));
            userService.setNewService(userServiceObj.getBoolean("newService"));
            userService.setDTVPaketID(userServiceObj.getInt("DTVPaketID"));
            userService.setPopust(userServiceObj.getDouble("popust"));
            userServiceArr.add(userService);
        }

        return userServiceArr;
    }

    public void zaduziUsluguUnapred(ActionEvent actionEvent) {
        if (!cmbUserServices.getValue().getAktivan()) {
            AlertUser.warrning("GRESKA", "Usluga nije aktivirana");
            return;
        }
        jObj = new JSONObject();
        jObj.put("action", "zaduzi_uslugu");
        jObj.put("userID", user.getId());
        jObj.put("paketType", cmbUserServices.getValue().getPaketType());
        jObj.put("id_ServiceUser", cmbUserServices.getValue().getId());
        jObj.put("id_service", cmbUserServices.getValue().getId_Service());
        jObj.put("nazivPaketa", cmbUserServices.getValue().getNazivPaketa());
        jObj.put("popust", cmbUserServices.getValue().getPopust());
        jObj.put("cena", cmbUserServices.getValue().getCena());
        jObj.put("zaMesec", formatMonthYear.format(dtpDatumZaNaplatu.getValue()));

        jObj = client.send_object(jObj);

        if (jObj.has("Error")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));
        } else {
            AlertUser.info("USLUGA ZADUZENA", jObj.getString("Message"));
        }

        show_data();


    }
}




