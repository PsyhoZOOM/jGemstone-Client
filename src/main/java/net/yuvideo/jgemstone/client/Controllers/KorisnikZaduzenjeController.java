package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

public class KorisnikZaduzenjeController implements Initializable {

  public TextField tNaziv;
  public TextField tCena;
  public TextField tPdv;
  public Spinner<Integer> spnBrojRata;
  public JFXDatePicker dtpZaMesec;
  public Label lUkupno;
  public Button bZaduzi;
  public Spinner<Integer> spnKolicina;
  private URL location;
  private ResourceBundle resources;

  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
  SpinnerValueFactory<Integer> spinnerValueFactory = new IntegerSpinnerValueFactory(1,
      Integer.MAX_VALUE, 1, 1);
  SpinnerValueFactory<Integer> spinnerValueFactory2 = new IntegerSpinnerValueFactory(1,
      Integer.MAX_VALUE, 1, 1);
  private Double cenaBezPdv;
  private int userID;
  private Client client;
  private double cenaBezPdvBezKolicne;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    spnBrojRata.setValueFactory(spinnerValueFactory);
    spnKolicina.setValueFactory(spinnerValueFactory2);

    dtpZaMesec.setValue(LocalDate.now().withDayOfMonth(1));
    dtpZaMesec.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        return object.format(dtf);
      }

      @Override
      public LocalDate fromString(String string) {
        LocalDate parse = LocalDate.parse(string, dtf);
        return parse;
      }
    });

    tCena.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        setPrice();
      }
    });

    tCena.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        setPrice();
      }
    });

    spnBrojRata.getEditor().textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        setPrice();
      }
    });

    spnKolicina.getEditor().textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        setPrice();
      }
    });

    tCena.setText("0.00");
  }

  private void setPrice() {
    if (tCena.getText().trim().isEmpty() || tPdv.getText().trim().isEmpty()) {
      return;
    }
    Double cena = Double.valueOf(tCena.getText().trim());
    Double pdv = Double.valueOf(tPdv.getText().trim());
    Double pdvOfSum = valueToPercent.getPDVOfSum(cena * spnKolicina.getValue(), pdv);
    int kolicina = spnKolicina.getValue();

    this.cenaBezPdv = ((cena * kolicina) - pdvOfSum);
    this.cenaBezPdvBezKolicne = cena - pdvOfSum;
    String ukupnoSaRatamaIPDV = String
        .format("Cena: %f\nCena+PDV: %f\nMesečna rata: %f", cenaBezPdv, cenaBezPdv + pdvOfSum,
            (cenaBezPdv + pdvOfSum) / spnBrojRata.getValue());
    lUkupno.setText(ukupnoSaRatamaIPDV);


  }

  public void zaduziKorisnika(ActionEvent actionEvent) {
    if (checkInputIsError()) {
      return;
    }
    int rate = spnBrojRata.getValue();
    Double pdv = Double.parseDouble(tPdv.getText().trim());
    JSONObject object = new JSONObject();
    object.put("action", "zaduziKorisnikaCustom");
    object.put("naziv", tNaziv.getText().trim());
    object.put("cena", Double.valueOf(tCena.getText().trim()));
    object.put("pdv", pdv);
    object.put("brojRata", rate);
    object.put("zaMesec", dtpZaMesec.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM")));
    object.put("userID", this.userID);
    object.put("kolicina", spnKolicina.getValue());
    System.out.println(object.toString());

    object = client.send_object(object);
    if (object.has("ERROR")) {
      alertUser(object.getString("ERROR"));
    } else {
      AlertUser.info("USPEŠNO ZADUŽENJE", "Korisnik je uspešno zadužen!");
    }
    Stage stage = (Stage) bZaduzi.getScene().getWindow();
    stage.close();
  }

  private boolean checkInputIsError() {
    boolean isError = false;

    if (tNaziv.getText().trim().isEmpty()) {
      isError = true;
      alertUser("Naziv ne može biti prazno!");
    }
    try {
      Double.parseDouble(tCena.getText().trim());
    } catch (NumberFormatException e) {
      isError = true;
      alertUser("Format cene nije u redu. Pokušajte sa formatom npr.: 1200.00");
    }

    try {
      Double.parseDouble(tPdv.getText().trim());

    } catch (NumberFormatException e) {
      isError = true;
      alertUser("Format PDV-a nije u redu. Pokušajte sa formatom npr.: 20.00");
    }

    if (dtpZaMesec.getEditor().getText().trim().isEmpty()) {
      isError = true;
      alertUser("Mesec zaduženja nije ispravan");
    } else {

    }

    return isError;

  }

  private void alertUser(String s) {
    AlertUser.error("GRESKA", s);
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
