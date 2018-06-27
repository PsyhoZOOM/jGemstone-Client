package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.Adrese;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Mesta;
import net.yuvideo.jgemstone.client.classes.Users;
import org.json.JSONObject;


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
  private Client client;
  public int userEditID;
  public Button bSnimi;
  public ComboBox<Mesta> cmbMestoUsluge;
  public ComboBox<Adrese> cmbAdresaUsluge;
  public TextField tMestoRacuna;
  public TextField tAdresaRacuna;
  public TextField tAdresaUslugeBroj;
  public CheckBox chkFirma;
  public TextField tNazivFirme;
  public TextField tKontaktOsoba;
  public TextField tKodBanke;
  public TextField tPIB;
  public TextField tMaticniBroj;
  public TextField tFAX;
  public TextField tAdresaFirme;
  public TextField tKontaktOsobaTel;
  public TextField tTekuciRacun;
  public TextField tEmail;
  public TextField tMestoFirme;
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
      public void changed(ObservableValue<? extends Mesta> observable, Mesta oldValue,
          Mesta newValue) {
        ObservableList adrese = FXCollections
            .observableArrayList(getAdrese((cmbMestoUsluge.getValue().getBrojMesta())));

        cmbAdresaUsluge.getItems().clear();
        cmbAdresaUsluge.getItems().removeAll();
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
      public void changed(ObservableValue<? extends Adrese> observable, Adrese oldValue,
          Adrese newValue) {
        String formatedUserJBROJ = String.format("%05d", userData.getId());
        if (!cmbAdresaUsluge.getItems().isEmpty()) {
          lUserID.setText(
              cmbAdresaUsluge.getValue().getBrojMesta() + cmbAdresaUsluge.getValue().getBrojAdrese()
                  + formatedUserJBROJ);
        } else {
          lUserID.setText(
              String.valueOf(userData.getjMesto() + userData.getjAdresa() + formatedUserJBROJ));
        }
      }
    });

    tTelFix.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (newValue.matches("\\d*")) {
          tTelMob.setText(newValue.replaceAll("[^\\d]", "").trim());

        }
      }
    });
    tTelMob.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (!newValue.matches("\\d*")) {
          tTelMob.setText(newValue.replaceAll("[^\\d]", "").trim());
        }
      }
    });

  }


  public void setData() {
    //default data fileds

    //label userID sa brojem mesta i adrese
    if (!cmbAdresaUsluge.getItems().isEmpty()) {
      lUserID.setText(userData.getJbroj());
    }

    ObservableList mesta = FXCollections.observableArrayList(getMesta());
    cmbMestoUsluge.setItems(mesta);

    Users user = getUserData(userEditID);
    setUserDataFields(user);
    lUserID.setText(user.getJbroj());

    if (user.isFirma()) {
      chkFirma.setSelected(true);
    }

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

  private ArrayList<Adrese> getAdrese(String brojMesta) {
    ArrayList<Adrese> adreseArr = new ArrayList();
    Adrese adresa;
    JSONObject adresaObj;

    jObj = new JSONObject();
    jObj.put("action", "getAdrese");
    if (brojMesta != null) {
      jObj.put("brojMesta", brojMesta);
    } else {
      jObj.put("brojMesta", "00");
    }

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
    userData.setAdresaRacuna(jObj.getString("adresaRacuna"));
    userData.setMestoRacuna(jObj.getString("mestoRacuna"));
    userData.setAdresaUsluge(jObj.getString("adresaUsluge"));
    userData.setMestoUsluge(jObj.getString("mestoUsluge"));
    userData.setKomentar(jObj.getString("komentar"));
    userData.setjMesto(jObj.getString("jMesto"));
    userData.setjAdresa(jObj.getString("jAdresa"));
    userData.setjAdresaBroj(jObj.getString("jAdresaBroj"));
    userData.setJbroj(jObj.getString("jBroj"));
    userData.setEmail(jObj.getString("email"));

    //FIRMA
    userData.setFirma(jObj.getBoolean("firma"));
    userData.setNazivFirme(jObj.getString("nazivFirme"));
    userData.setKontaktOsoba(jObj.getString("kontaktOsoba"));
    userData.setTelKontaktOsobe(jObj.getString("kontaktOsobaTel"));
    userData.setKodBanke(jObj.getString("kodBanke"));
    userData.setTekuciRacuna(jObj.getString("tekuciRacun"));
    userData.setPIB(jObj.getString("PIB"));
    userData.setMaticniBroj(jObj.getString("maticniBroj"));
    userData.setFax(jObj.getString("fax"));
    userData.setAdresaFirme(jObj.getString("adresaFirme"));
    userData.setMestoFirme(jObj.getString("mestoFirme"));

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
    jObj.put("brojMesta", user.getjMesto());
    jObj.put("brojAdrese", user.getjAdresa());

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
    if (!user.getDatum_rodjenja().trim().isEmpty())
      tdDatumRodjenja.setValue(LocalDate.parse(user.getDatum_rodjenja(), dateBirthFormat));
    tBrLk.setText(user.getBr_lk());
    tJMBG.setText(user.getJMBG());
    tMesto.setText(user.getMesto());
    tAdresa.setText(user.getAdresa());
    tPostBr.setText(user.getPostanski_broj());
    tTelMob.setText(user.getMobilni());
    tTelFix.setText(user.getFiksni());
    tMestoFirme.setText(user.getMestoFirme());

    cmbMestoUsluge.setValue(userMesto);
    cmbAdresaUsluge.setValue(userAdresa);

    tAdresaUslugeBroj.setText(user.getjAdresaBroj());

    tMestoRacuna.setText(user.getMestoRacuna());
    tAdresaRacuna.setText(user.getAdresaRacuna());
    taKomentar.setText(user.getKomentar());

    tEmail.setText(user.getEmail());

    //FIRMA
    chkFirma.setSelected(user.isFirma());
    tNazivFirme.setText(user.getNazivFirme());
    tKontaktOsoba.setText(user.getKontaktOsoba());
    tKontaktOsobaTel.setText(user.getTelKontaktOsobe());
    tKodBanke.setText(user.getKodBanke());
    tTekuciRacun.setText(user.getTekuciRacuna());
    tPIB.setText(user.getPIB());
    tMaticniBroj.setText(user.getMaticniBroj());
    tFAX.setText(user.getFax());
    tAdresaFirme.setText(user.getAdresaFirme());
    tMestoFirme.setText(user.getMestoFirme());


  }

  private void updateUserData() {
    jObj = new JSONObject();

    jObj.put("action", "update_user");

    if (cmbMestoUsluge.getValue().getBrojMesta() == null
        || cmbAdresaUsluge.getValue().getBrojAdrese() == null) {
      AlertUser.warrning("Greska", "Mesto racuna ili adrese ne mogu biti prazni");
      return;
    }
    String userJBRoj = String.format("%05d", userEditID);

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
    jObj.put("jAdresa", String.valueOf(cmbAdresaUsluge.getValue().getBrojAdrese()));
    jObj.put("jMesto", String.valueOf(cmbMestoUsluge.getValue().getBrojMesta()));
    jObj.put("komentar", taKomentar.getText().trim());
    jObj.put("jBroj",
        cmbMestoUsluge.getValue().getBrojMesta() + cmbAdresaUsluge.getValue().getBrojAdrese()
            + userJBRoj);

    //FIRMA
    jObj.put("firma", chkFirma.isSelected());
    jObj.put("nazivFirme", tNazivFirme.getText());
    jObj.put("kontaktOsoba", tKontaktOsoba.getText());
    jObj.put("kontaktOsobaTel", tKontaktOsobaTel.getText());
    jObj.put("kodBanke", tKodBanke.getText());
    jObj.put("tekuciRacun", tTekuciRacun.getText());
    jObj.put("PIB", tPIB.getText());
    jObj.put("maticniBroj", tMaticniBroj.getText());
    jObj.put("fax", tFAX.getText());
    jObj.put("adresaFirme", tAdresaFirme.getText());
    jObj.put("mestoFirme", tMestoFirme.getText());
    jObj.put("email", tEmail.getText());

    jObj = client.send_object(jObj);

    if (jObj.has("Error")) {
      AlertUser.warrning("GRESKA", jObj.getString("Error"));
    } else {
      AlertUser.info("Informacija", "Korisnik izmene snimljene!");
    }


  }


  public void bSaveUser(ActionEvent actionEvent) {
    updateUserData();
    userData = getUserData(userData.getId());


  }

  public void copyToAdresaRacun(ActionEvent actionEvent) {
    tAdresaRacuna.setText(tAdresa.getText());
    tMestoRacuna.setText(String.format("%s %s", tPostBr.getText(), tMesto.getText()));
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
