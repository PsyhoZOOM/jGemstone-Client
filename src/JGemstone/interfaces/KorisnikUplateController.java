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
import javafx.util.StringConverter;
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
    public DatePicker dtpDatumUplate;
    public Spinner spnUplaceno;
    public Label lUkupnoUplaceno;
    public Label lUkupanDug;
    public Label lZaUplatu;
    public ComboBox<UplateMesta> cmbMestoUplate;
    public DatePicker dtpDatumOd;
    public DatePicker dtpDatumDo;
    public DatePicker dtpDatumZaNaplatu;
    public TextField tNazivUsluge;
    public Spinner cmbCena;
    public Button bZaduzi;
    public Button bUplati;
    public TableColumn cZaMesec;
    public Button bUplatiServis;
    public Client client;
    public Users user;
    DecimalFormat df = new DecimalFormat("#,##0.00");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    DateTimeFormatter formatterBack = DateTimeFormatter.ofPattern("yyyy-MM-dd");
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
                cmbZaUplatu.getEditor().setText(df.format(newValue.getDug()));
                if (newValue.getDug() == 0) {
                    bUplatiServis.setDisable(true);
                } else {
                    cmbZaUplatu.setDisable(false);
                    bUplatiServis.setDisable(false);
                }
            }
        });


        cmbZaUplatu.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, Double.MAX_VALUE));
        cmbZaUplatu.setEditable(true);


        dtpDatumUplate.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                if (object == null) {
                    return "";
                } else {
                    return formatter.format(object);
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                } else {
                    return LocalDate.parse(string, formatter);
                }
            }
        });
        dtpDatumUplate.setValue(LocalDate.now());


        spnUplaceno.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, Double.MAX_VALUE));
        spnUplaceno.getValueFactory().setConverter(new StringConverter<Double>() {

            @Override
            public String toString(Double object) {
                if (object == null) {
                    return "0.00";
                }

                return df.format(object);
            }

            @Override
            public Double fromString(String string) {
                try {


                    if (string == null) {
                        return 0.00;
                    }

                    string.trim();

                    if (string.length() < 1) {
                        return null;
                    }

                    return df.parse(string).doubleValue();
                } catch (ParseException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        cDatumUplate.setCellValueFactory(new PropertyValueFactory<Uplate, String>("datumUplate"));
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
        cMestoUplate.setCellValueFactory(new PropertyValueFactory<Uplate, String>("mestoUplate"));
        cOperater.setCellValueFactory(new PropertyValueFactory<Uplate, String>("operater"));


    }

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) bClose.getScene().getWindow();
        stage.close();
    }


    public void show_data() {
        ObservableList dataZaduzenja = FXCollections.observableArrayList(get_Zaduzenja());
        tblZaduzenja.setItems(dataZaduzenja);

        ObservableList mestaUplate = FXCollections.observableArrayList(getMestaUplate());
        cmbMestoUplate.setItems(mestaUplate);

        ObservableList dataUplate = FXCollections.observableArrayList(getUserUplate());
        tblUplate.setItems(dataUplate);


        Double ukupno = 0.00;
        ukupno = zaUplatu - ukupno_uplaceno;
        lUkupanDug.setText(df.format(ukupno));


    }

    private ArrayList<UplateMesta> getMestaUplate() {
        ArrayList<UplateMesta> mestaUplate = new ArrayList();


        UplateMesta uplate = new UplateMesta();
        uplate.setMestoUplate("Keš");
        uplate.setMesto(0);
        mestaUplate.add(uplate);

        uplate = new UplateMesta();
        uplate.setMestoUplate("Faktura");
        uplate.setMesto(1);
        mestaUplate.add(uplate);

        uplate = new UplateMesta();
        uplate.setMestoUplate("Pošta");
        uplate.setMesto(2);
        mestaUplate.add(uplate);

        uplate = new UplateMesta();
        uplate.setMestoUplate("Čekovi");
        uplate.setMesto(3);
        mestaUplate.add(uplate);

        return mestaUplate;

    }


    ArrayList<Uplate> get_Zaduzenja() {
        zaUplatu = 0.00;
        jObj = new JSONObject();
        jObj.put("action", "get_zaduzenja_user");
        jObj.put("userID", user.getId());

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
            uplata.setUplaceno(uplataObj.getDouble("uplaceno"));
            uplata.setDatumUplate(uplataObj.getString("datumUplate"));
            uplata.setDug(uplataObj.getDouble("dug"));
            zaUplatu += uplataObj.getDouble("dug");
            uplata.setOperater(uplataObj.getString("operater"));
            uplata.setZaMesec(uplataObj.getString("zaMesec"));
            uplate.add(uplata);

        }
        lZaUplatu.setText(df.format(zaUplatu));
        return uplate;
    }


    public void ShowZaduzenja(ActionEvent actionEvent) {

    }


    public void uplatiUplatu(ActionEvent actionEvent) {
        if (dtpDatumUplate.getValue() == null || spnUplaceno.getValue() == null || cmbMestoUplate.getValue() == null) {
            AlertUser.error("GREŠKA", "Polja datum uplate, uplaćeno i mesto uplate ne mogu biti prazna");
            return;
        }
        Uplate uplata = new Uplate();
        LocalDate localdate = dtpDatumUplate.getValue();

        uplata.setCena((Double) spnUplaceno.getValue());
        uplata.setDatumUplate(dtpDatumUplate.getValue().format(formatterBack));
        uplata.setMestoUplate(String.valueOf(cmbMestoUplate.getValue()));

        jObj = new JSONObject();
        jObj.put("action", "new_uplata");
        jObj.put("userID", user.getId());
        jObj.put("datumUplate", dtpDatumUplate.getValue().format(formatterBack));
        jObj.put("uplaceno", Double.valueOf(spnUplaceno.getEditor().getText()));
        jObj.put("mesto", cmbMestoUplate.getValue().getMestoUplate());
        jObj = client.send_object(jObj);

        if (jObj.has("Error")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));
        } else {
            AlertUser.info("UPLATA", "UPLATA JE IZVRŠENA");
        }

        show_data();

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
        try {
            Number numb = df.parse(cmbZaUplatu.getEditor().getText());
            double uplaceno = numb.doubleValue();
            jObj.put("uplaceno", uplaceno);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
}




