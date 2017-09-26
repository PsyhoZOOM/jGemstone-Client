package net.yuvideo.jgemstone.client.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.json.JSONObject;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import net.yuvideo.jgemstone.client.classes.Adrese;
import net.yuvideo.jgemstone.client.classes.AlertUser;

import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Mesta;
import net.yuvideo.jgemstone.client.classes.NotifyUser;
import net.yuvideo.jgemstone.client.classes.Users;


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
    public Label lUserID;
    public TextArea taKomentar;
    public Client client;
    public int userEditID;
    public Button bSnimi;
    public ComboBox<Mesta> cmbMestoUsluge;
    public ComboBox<Adrese> cmbAdresaUsluge;
    public TextField tMestoRacuna;
    public TextField tAdresaRacuna;
    public TextField tAdresaUslugeBroj;
    private ResourceBundle resource;
    private URL location;
    private JSONObject jObj;


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


        cmbMestoUsluge.setCellFactory(lv -> new ListCell<Mesta>() {
            public void updateItem(Mesta mesto, boolean bool) {
                super.updateItem(mesto, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(mesto.getNazivMesta() + ", " + mesto.getBrojMesta());
                }
            }
        });

        cmbMestoUsluge.valueProperty().addListener(new ChangeListener<Mesta>() {
            @Override
            public void changed(ObservableValue<? extends Mesta> observable, Mesta oldValue, Mesta newValue) {
                ObservableList adrese = FXCollections.observableArrayList(getAdrese((cmbMestoUsluge.getValue().getId())));

                cmbAdresaUsluge.getItems().clear();
                cmbAdresaUsluge.setItems(adrese);
            }
        });

        cmbAdresaUsluge.setCellFactory(lv -> new ListCell<Adrese>() {
            public void updateItem(Adrese adresa, boolean bool) {
                super.updateItem(adresa, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(adresa.getNazivAdrese() + ", " + adresa.getBrojAdrese());
                }
            }
        });
        cmbAdresaUsluge.valueProperty().addListener(new ChangeListener<Adrese>() {
            @Override
            public void changed(ObservableValue<? extends Adrese> observable, Adrese oldValue, Adrese newValue) {
                if (!cmbAdresaUsluge.getItems().isEmpty()) {
                    lUserID.setText(cmbAdresaUsluge.getValue().getBrojMesta() + cmbAdresaUsluge.getValue().getBrojAdrese() + userData.getId());
                } else {
                    lUserID.setText(String.valueOf(userData.getjMesto() + userData.getjAdresa() + userData.getId()));
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
        if (!cmbAdresaUsluge.getItems().isEmpty())
            lUserID.setText(userData.getJbroj());

        ObservableList mesta = FXCollections.observableArrayList(getMesta());
        cmbMestoUsluge.setItems(mesta);

        Users user = getUserData(userEditID);
        setUserDataFields(user);
        lUserID.setText(user.getJbroj());
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
        userData.setJbroj(jObj.getString("jBroj"));

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
        jObj.put("idMesta", user.getjAdresa());


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

        cmbMestoUsluge.setValue(userMesto);
        cmbAdresaUsluge.setValue(userAdresa);
        tAdresaUslugeBroj.setText(user.getjAdresaBroj());


        tMestoRacuna.setText(user.getMesto_usluge());
        tAdresaRacuna.setText(user.getAdresa_usluge());
        taKomentar.setText(user.getKomentar());


    }

    private void updateUserData() {
        jObj = new JSONObject();


        jObj.put("action", "update_user");

        if (cmbMestoUsluge.getValue().getBrojMesta() == null || cmbAdresaUsluge.getValue().getBrojAdrese() == null) {
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
        jObj.put("adresaRacuna", tAdresaRacuna.getText().trim());
        jObj.put("mestoRacuna", tMestoRacuna.getText().trim());
        jObj.put("jAdresaBroj", tAdresaUslugeBroj.getText().trim());
        jObj.put("jAdresa", String.valueOf(cmbAdresaUsluge.getValue().getId()));
        jObj.put("jMesto", String.valueOf(cmbMestoUsluge.getValue().getId()));
        jObj.put("komentar", taKomentar.getText().trim());
        jObj.put("jBroj", cmbMestoUsluge.getValue().getBrojMesta() + cmbAdresaUsluge.getValue().getBrojAdrese() + userData.getId());


        jObj = client.send_object(jObj);


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
