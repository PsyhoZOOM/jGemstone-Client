package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.CyrToLatin;
import net.yuvideo.jgemstone.client.classes.SmartCardReader;
import net.yuvideo.jgemstone.client.classes.Users;
import net.yuvideo.jgemstone.client.classes.messageS;
import org.json.JSONObject;

/**
 * Created by zoom on 8/8/16.
 */
public class NovKorisnikController implements Initializable {

  public Button bClose;
  public TextField tFullName;
  public JFXDatePicker tDatumRodjenja;
  public TextField tPostBr;
  public TextField tBrLk;
  public TextField tJMBG;
  public TextField tFiksni;
  public TextField tMobilni;
  public TextArea tKomentar;
  public Button bReadCardReader;
  public ImageView imgUserPhoto;
  public Users user;
  public boolean user_saved;
  private boolean editUser = false;
  public TextField tMesto;
  public TextField tAdresa;
  public int freeID;
  public Button bSave;
  SmartCardReader sr;
  //JSON
  JSONObject jObj;
  DateTimeFormatter dateTimeFormatterRodjen = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private Stage stage;
  private Client client;
  private messageS mess;
  private Alert alert;
  @FXML
  private ComboBox<CardTerminal> cmbCardReader;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    tDatumRodjenja.setValue(LocalDate.now());
    bSave.setDisable(true);

    bClose.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        stage = (Stage) bClose.getScene().getWindow();
        stage.close();
      }
    });

    tDatumRodjenja.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        return object.format(dateTimeFormatterRodjen);
      }

      @Override
      public LocalDate fromString(String string) {
        LocalDate date = LocalDate.parse(string, dateTimeFormatterRodjen);
        return date;
      }
    });

    bReadCardReader.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        Image img1 = new Image(ClassLoader.getSystemResource("images/logo.png").toString());
        imgUserPhoto.setImage(img1);
        if (cmbCardReader.getValue() == null) {
          AlertUser.error("GRESKA", "NIJE PRONADJEN ČITAČ KARTICA");
          return;
        }
        try {
          cmbCardReader.getValue().waitForCardPresent(10000);
        } catch (CardException e) {
          e.printStackTrace();
        }
        try {
          if (!cmbCardReader.getValue().isCardPresent()) {
            AlertUser.info("NEMA KARTICE", String.format("Molim Vas ubacite karticu u uređaj: %s",
                cmbCardReader.getValue().getName()));
            return;
          }
        } catch (CardException e) {
          e.printStackTrace();
        }

        sr.scan(cmbCardReader.getValue());
        Users user = sr.getUserData();
        tFullName.setText(CyrToLatin.CirilicaToLatinica(user.getIme()));
        tJMBG.setText(user.getJMBG());
        tBrLk.setText(user.getBr_lk());
        tDatumRodjenja.setValue(
            LocalDate.parse(user.getDatum_rodjenja(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        tMesto.setText(user.getMesto());
        tAdresa.setText(user.getAdresa());
        Image img = SwingFXUtils.toFXImage(sr.toBufferedImage(), null);
        imgUserPhoto.setImage(img);


      }
    });

    tFullName.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (tFullName.getText().trim().isEmpty()) {
          bSave.setDisable(true);
        } else {
          bSave.setDisable(false);
        }
      }
    });

  }

  public void setClient(Client client) {
    System.setProperty("sun.security.smartcardio.library", "libpcsclite.so.1");
    List<CardTerminal> terminals = getTerminals();
    if (terminals != null) {
      cmbCardReader.getItems().addAll(terminals);
    }
    this.client = client;

  }

  public void bSaveUser(ActionEvent actionEvent) {

    String formatedUserJbroj = String.format("%05d", freeID);
    jObj = new JSONObject();
    jObj.put("action", "new_user");
    jObj.put("editUser", editUser);

    jObj.put("freeID", freeID);

    jObj.put("fullName", tFullName.getText());
    jObj.put("datumRodjenja", tDatumRodjenja.getValue().format(dateTimeFormatterRodjen));
    jObj.put("postBr", tPostBr.getText());
    jObj.put("mesto", tMesto.getText());
    jObj.put("brLk", tBrLk.getText());
    jObj.put("JMBG", tJMBG.getText());
    jObj.put("adresa", tAdresa.getText());
    jObj.put("komentar", tKomentar.getText());
    jObj.put("telFiksni", tFiksni.getText());
    jObj.put("telMobilni", tMobilni.getText());
    jObj.put("jBroj", formatedUserJbroj);

    jObj = client.send_object(jObj);

    if (jObj.has("ERROR")) {

      user_saved = false;

      AlertUser.error("Greska", "Korisnik nije napravljne \n" + jObj.getString("ERROR"));
      return;
    } else if (jObj.get("Message").equals("user_saved")) {
      if (editUser == true) {

        AlertUser.info("Informacija", "Korisnik je snimljen");

        user_saved = true;
        user = new Users();
        user.setId(jObj.getInt("userID"));
        Stage stage = (Stage) bClose.getScene().getWindow();
        stage.close();
      } else {
        editUser = true;
      }
    }


  }

  private List<CardTerminal> getTerminals() {
    sr = new SmartCardReader();
    List<CardTerminal> terminals = sr.getTerminals();

    return terminals;
  }


  public void setData() {
    JSONObject objMesta = new JSONObject();
    JSONObject objAdrese = new JSONObject();
    objMesta.put("action", "getMesta");
    objAdrese.put("action", "getAllAdrese");
    ////TODO autokomplete mesta and adrese
    bSaveUser(null);
    editUser = true;

  }
}
