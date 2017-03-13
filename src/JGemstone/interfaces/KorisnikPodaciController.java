package JGemstone.interfaces;

import JGemstone.classes.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
 * Created by zoom on 1/29/17.
 */
public class KorisnikPodaciController implements Initializable {
    public TextField tFullName;
    public DatePicker tdDatumRodjenja;
    public TextField tBrLk;
    public TextField tJMBG;
    public TextField tAdresa;
    public TextField tMesto;
    public TextField tPostBr;
    public TextField tTelMob;
    public TextField tTelFix;
    public ComboBox<Mesta> cmbMestoRacun;
    public ComboBox<Adrese> cmbAdresaRacun;
    public Label lUserID;
    public TextArea taKomentar;
    public TextField tAdresaUsluge;
    public TextField tMestoUsluge;
    public TextField tAdresaRacunBroj;
    public Client client;
    public int userEditID;
    public Button bSnimi;
    private ResourceBundle resource;
    private URL location;
    private JSONObject jObj;

    private Logger LOGGER = LogManager.getLogger("EDIT_KORISNIK_PODACI");

    private Users userData;
    private DateTimeFormatter dateBirthFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resource = resources;

        //INIT
        tdDatumRodjenja.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate object) {
                if (object == null) {
                    return "00-00-0000";
                }
                return dateBirthFormat.format(object);
            }

            @Override
            public LocalDate fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(string, dateBirthFormat);
            }
        });


        cmbMestoRacun.setCellFactory(lv -> new ListCell<Mesta>() {
            public void updateItem(Mesta mesto, boolean bool) {
                super.updateItem(mesto, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(mesto.getNazivMesta() + ", " + mesto.getBrojMesta());
                }
            }
        });

        cmbMestoRacun.valueProperty().addListener(new ChangeListener<Mesta>() {
            @Override
            public void changed(ObservableValue<? extends Mesta> observable, Mesta oldValue, Mesta newValue) {
                ObservableList adrese = FXCollections.observableArrayList(getAdrese((cmbMestoRacun.getValue().getId())));

                if (!adrese.isEmpty()) {
                    cmbAdresaRacun.setItems(adrese);
                } else {
                    cmbAdresaRacun.getItems().clear();
                }
            }
        });

        cmbAdresaRacun.setCellFactory(lv -> new ListCell<Adrese>() {
            public void updateItem(Adrese adresa, boolean bool) {
                super.updateItem(adresa, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(adresa.getNazivAdrese() + ", " + adresa.getBrojAdrese());
                }
            }
        });
        cmbAdresaRacun.valueProperty().addListener(new ChangeListener<Adrese>() {
            @Override
            public void changed(ObservableValue<? extends Adrese> observable, Adrese oldValue, Adrese newValue) {
                if (!cmbAdresaRacun.getItems().isEmpty()) {
                    lUserID.setText(cmbMestoRacun.getValue().getBrojMesta() + cmbAdresaRacun.getValue().getBrojAdrese() + userData.getjBroj());
                } else {
                    lUserID.setText(String.valueOf(userData.getjBroj()));
                }
            }
        });

        tTelFix.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("[0-9]*")) {
                    tTelFix.setText(newValue);
                }
            }
        });
        tTelMob.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("[0-9]*")) {
                    tTelMob.setText(newValue);
                }
            }
        });
    }


    public void setData() {
        //default data fileds


        //label userID sa brojem mesta i adrese
        if (!cmbAdresaRacun.getItems().isEmpty())
            lUserID.setText(cmbMestoRacun.getValue().getBrojMesta() + cmbAdresaRacun.getValue().getBrojAdrese() + userData.getjBroj());

        ObservableList mesta = FXCollections.observableArrayList(getMesta());
        cmbMestoRacun.setItems(mesta);


        Users user = getUserData(userEditID);
        setUserDataFields(user);
    }

    private ArrayList<Mesta> getMesta() {
        ArrayList<Mesta> mestaArr = new ArrayList();
        Mesta mesto;
        JSONObject mestoObj;

        jObj = new JSONObject();
        jObj.put("action", "getMesta");

        jObj = client.send_object(jObj);


        for (int i = 0; i < jObj.length(); i++) {
            mesto = new Mesta();
            mestoObj = (JSONObject) jObj.get(String.valueOf(i));
            mesto.setId(mestoObj.getInt("id"));
            mesto.setNazivMesta(mestoObj.getString("nazivMesta"));
            mesto.setBrojMesta(mestoObj.getString("brojMesta"));
            mestaArr.add(mesto);
        }

        return mestaArr;
    }

    private ArrayList<Adrese> getAdrese(int mestoID) {
        ArrayList<Adrese> adreseArr = new ArrayList();
        Adrese adresa;
        JSONObject adresaObj;

        jObj = new JSONObject();
        jObj.put("action", "getAdrese");
        jObj.put("idMesta", mestoID);

        jObj = client.send_object(jObj);

        for (int i = 0; i < jObj.length(); i++) {
            adresaObj = (JSONObject) jObj.get(String.valueOf(i));
            adresa = new Adrese();
            adresa.setId(adresaObj.getInt("id"));
            adresa.setNazivAdrese(adresaObj.getString("nazivAdrese"));
            adresa.setBrojAdrese(adresaObj.getString("brojAdrese"));
            adresa.setIdMesta(adresaObj.getInt("idMesta"));
            adresa.setBrojMesta(adresaObj.getString("brojMesta"));
            adresa.setNazivMesta(adresaObj.getString("nazivMesta"));
            adreseArr.add(adresa);
        }

        return adreseArr;
    }

    private Users getUserData(int userID) {
        jObj = new JSONObject();
        jObj.put("action", "get_user_data");
        jObj.put("userId", userID);

        jObj = client.send_object(jObj);

        userData = new Users();

        userData.setId(jObj.getInt("id"));
        userData.setIme(jObj.getString("fullName"));
        userData.setDatum_rodjenja(jObj.getString("datumRodjenja"));
        userData.setAdresa(jObj.getString("adresa"));
        userData.setMesto(jObj.getString("mesto"));
        userData.setPostanski_broj(jObj.getString("postBr"));
        userData.setFiksni(jObj.getString("telFix"));
        userData.setMobilni(jObj.getString("telMob"));
        userData.setJMBG(jObj.getString("JMBG"));
        userData.setBr_lk(jObj.getString("brLk"));
        userData.setAdresa_usluge(jObj.getString("adresaUsluge"));
        userData.setMesto_usluge(jObj.getString("mestoUsluge"));
        userData.setjAdresa(jObj.getString("jAdresa"));
        userData.setjAdresaBroj(jObj.getString("jAdresaBroj"));
        userData.setKomentar(jObj.getString("komentar"));
        userData.setjMesto(jObj.getString("jMesto"));
        userData.setjAdresa(jObj.getString("jAdresa"));
        userData.setjAdresaBroj(jObj.getString("jAdresaBroj"));
        userData.setjBroj(jObj.getString("jBroj"));

        return userData;
    }

    private void setUserDataFields(Users user) {

        Mesta userMesto = new Mesta();
        Adrese userAdresa = new Adrese();

        jObj = new JSONObject();

        jObj.put("action", "getMesto");
        jObj.put("broj", user.getjMesto());

        jObj = client.send_object(jObj);

        if (!jObj.has("Message")) {
            userMesto.setId(jObj.getInt("id"));
            userMesto.setNazivMesta(jObj.getString("nazivMesta"));
            userMesto.setBrojMesta(jObj.getString("brojMesta"));
        }

        jObj = new JSONObject();

        jObj.put("action", "getAdresa");
        jObj.put("broj", user.getjAdresa());


        jObj = client.send_object(jObj);

        if (!jObj.has("Message")) {
            userAdresa.setId(jObj.getInt("id"));
            userAdresa.setNazivAdrese(jObj.getString("nazivAdrese"));
            userAdresa.setBrojAdrese(jObj.getString("brojAdrese"));
            userAdresa.setIdMesta(jObj.getInt("idMesta"));
            userAdresa.setBrojMesta(jObj.getString("brojMesta"));
            userAdresa.setNazivMesta(jObj.getString("nazivMesta"));
        }

        tFullName.setText(user.getIme());
        tdDatumRodjenja.setValue(LocalDate.parse(user.getDatum_rodjenja(), dateBirthFormat));
        tBrLk.setText(user.getBr_lk());
        tJMBG.setText(user.getJMBG());
        tMesto.setText(user.getMesto());
        tAdresa.setText(user.getAdresa());
        tPostBr.setText(user.getPostanski_broj());
        tTelMob.setText(user.getMobilni());
        tTelFix.setText(user.getFiksni());

        cmbMestoRacun.setValue(userMesto);
        cmbAdresaRacun.setValue(userAdresa);

        tAdresaRacunBroj.setText(user.getjAdresaBroj());

        tMestoUsluge.setText(user.getMesto_usluge());
        tAdresaUsluge.setText(user.getAdresa_usluge());
        taKomentar.setText(user.getKomentar());


    }

    private void updateUserData() {
        jObj = new JSONObject();


        jObj.put("action", "update_user");

        if (cmbMestoRacun.getValue().getBrojMesta() == null || cmbAdresaRacun.getValue().getBrojAdrese() == null) {
            AlertUser.warrning("Greska", "Mesto racuna ili adrese ne mogu biti prazni");
            return;
        }


        jObj.put("userID", userEditID);
        jObj.put("fullName", tFullName.getText().trim());
        jObj.put("datumRodjenja", tdDatumRodjenja.getValue().format(dateBirthFormat).toString().trim());
        jObj.put("adresa", tAdresa.getText().trim());
        jObj.put("mesto", tMesto.getText().trim());
        jObj.put("postBr", tPostBr.getText().trim());
        jObj.put("telFiksni", tTelFix.getText().trim());
        jObj.put("telMobilni", tTelMob.getText().trim());
        jObj.put("brLk", tBrLk.getText().trim());
        jObj.put("JMBG", tJMBG.getText().trim());
        jObj.put("adresaUsluge", tAdresaUsluge.getText().trim());
        jObj.put("mestoUsluge", tMestoUsluge.getText().trim());
        jObj.put("jAdresaBroj", tAdresaRacunBroj.getText().trim());
        jObj.put("jAdresa", cmbAdresaRacun.getValue().getBrojAdrese());
        jObj.put("jMesto", cmbMestoRacun.getValue().getBrojMesta());
        jObj.put("jBroj", lUserID.getText().trim());
        jObj.put("komentar", taKomentar.getText().trim());


        jObj = client.send_object(jObj);

        LOGGER.info(jObj.getString("Message"));

        if (jObj.has("Error")) {
            NotifyUser.NotifyUser("Greška", jObj.getString("Error"), 3);
        } else {
            AlertUser.info("Informacija", "Korisnik izmene snimljene!");
        }


    }


    public void bSaveUser(ActionEvent actionEvent) {
        updateUserData();

    }
}
