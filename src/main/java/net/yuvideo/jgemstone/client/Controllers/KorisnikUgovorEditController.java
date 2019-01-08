package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.print.Paper;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.UserData;
import net.yuvideo.jgemstone.client.classes.ugovori_types;
import org.json.JSONObject;

/**
 * Created by zoom on 2/9/17.
 */
public class KorisnikUgovorEditController implements Initializable {

  public WebView webV;
  public HTMLEditor htmlUgovor;
  public Button bSnimi;
  public Label lBrUgovora;
  public Label lNazivUgovora;
  public Label lOd;
  public Label lDo;
  public Label lOpis;
  public boolean editUgovor = false;
  public ugovori_types ugovor;
  private Client client;
  public Label lTrajanje;
  public UserData user;
  public boolean replaceCode;
  private URL location;
  private ResourceBundle resources;
  private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private DateTimeFormatter dateTimeFormatterSQL = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private JSONObject jObj = new JSONObject();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
    //   htmlUgovor.setPadding(new Insets(0.75, 0.75, 0.75, 0.75));
    htmlUgovor.setStyle("-fx-text-wrap: true;");

  }


  public void setData() {
    htmlUgovor.setHtmlText(ugovor.getTextUgovora());
    //ne zelimo da zamenimo html text postojecem ugovoru (mozda je vec rucno menjan)
    if (replaceCode) {
      replace_codes();
    }

    webV.getEngine().loadContent(htmlUgovor.getHtmlText());
    user.updateData();
    lBrUgovora.setText(String.valueOf(ugovor.getBr()));
    lNazivUgovora.setText(ugovor.getNaziv());
    lOd.setText(ugovor.getPocetakUgovora());
    lDo.setText(ugovor.getKrajUgovora());
    lOpis.setText(ugovor.getKomentar());
    lTrajanje.setText(String.valueOf(ugovor.getTrajanje()));

  }

  private void replace_codes() {

    htmlUgovor.setHtmlText(htmlUgovor.getHtmlText()
        .replace("{%broj_ugovora}", ugovor.getBr())
        .replace("{%adresa_LK}", user.getAdresa())
        .replace("{%broj_LK}", user.getBr_lk())
        .replace("{%datum_zaklj_ugovora}",
            LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
        .replace("{%tel_fix}", user.getFiksni())
        .replace("{%tel_mob}", user.getMobilni())
        .replace("{%fax}", user.getFax())
        .replace("{%adresa_za_prijem_racuna}",
            String.format("%s %s", user.getAdresaRacuna(), user.getMestoRacuna()))
        .replace("{%adresa_koriscenja_usluge}", String
            .format("%s %s %s", user.getAdresaUsluge(), user.getjAdresaBroj(),
                user.getMestoUsluge()))
        .replace("{%period_ugovora}", String.valueOf(ugovor.getTrajanje() + String
            .format(" (%s)", getGodinaSlova(Integer.parseInt(ugovor.getTrajanje())))))
        .replace("{%datum}", LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
        .replace("{%PIB}", user.getPIB())
        .replace("{%JMBG}", user.getJMBG())
        .replace("{%maticni}", user.getMaticniBroj())
        .replace("{%kod_banke}", user.getKodBanke())
        .replace("{%ime_prezime_zastupnik}", user.getIme())
        .replace("{%mesto_LK}", user.getMesto())
        .replace("{%broj_racuna}", user.getTekuciRacuna())
        .replace("{%naziv_firme}", user.getNazivFirme())
        .replace("{%kontakt_osoba}", user.getKontaktOsoba()));

  }


  private String getGodinaSlova(int ugTrajanje) {
    String godina = "";
    switch (ugTrajanje) {
      case 1:
        godina = "jedna";
        break;
      case 2:
        godina = "dve";
        break;
      case 3:
        godina = "tri";
        break;
      case 4:
        godina = "četri";
        break;
      case 5:
        godina = "pet";
        break;
      case 6:
        godina = "šest";
        break;

    }
    return godina;
  }


  public void saveUgovor(ActionEvent actionEvent) {
    if (editUgovor) {
      updateUgovor();
    } else {
      saveNewUgovor();
    }
  }

  private void updateUgovor() {
    jObj = new JSONObject();
    jObj.put("action", "update_user_ugovor");

    jObj.put("id", ugovor.getId());
    jObj.put("textUgovora", htmlUgovor.getHtmlText());

    jObj = client.send_object(jObj);

    if (jObj.has("Error")) {
      AlertUser.error("GRESKA", jObj.getString("Error"));
    } else {
      AlertUser.info("INFO", "PROMENE UGOVORA SU USPESNO ISVRSENE");
    }
  }

  private void saveNewUgovor() {
    jObj = new JSONObject();
    jObj.put("action", "save_user_ugovor");

    jObj.put("naziv", ugovor.getNaziv());
    jObj.put("textUgovora", htmlUgovor.getHtmlText());
    jObj.put("komentar", ugovor.getKomentar());
    jObj.put("pocetakUgovora", LocalDate.parse(ugovor.getPocetakUgovora(), dateTimeFormatter));
    jObj.put("krajUgovora", LocalDate.parse(ugovor.getKrajUgovora(), dateTimeFormatter));
    jObj.put("userID", ugovor.getUserID());
    jObj.put("brojUgovora", ugovor.getBr());
    jObj.put("trajanjeUgovora", ugovor.getTrajanje());

    jObj = client.send_object(jObj);

    if (jObj.has("ERROR")) {
      AlertUser.error("GRESKA", jObj.getString("ERROR"));
    } else {
      AlertUser.info("INFO", "UGOVOR JE SNIMLJEN");
    }
    Stage stage = (Stage) bSnimi.getScene().getWindow();

    stage.close();
  }

  private void close() {
    Stage stage = (Stage) bSnimi.getScene().getWindow();
    stage.close();
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void setPreviewWebV(ActionEvent actionEvent) {
    webV.setMaxSize(Paper.A4.getWidth() - 78, Paper.A4.getHeight() - 78);
    webV.getEngine().loadContent(htmlUgovor.getHtmlText());
  }
}
