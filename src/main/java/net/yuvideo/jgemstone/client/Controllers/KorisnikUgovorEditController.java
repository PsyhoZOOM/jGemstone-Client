package net.yuvideo.jgemstone.client.Controllers;

import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.ugovori_types;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by zoom on 2/9/17.
 */
public class KorisnikUgovorEditController implements Initializable {
    public HTMLEditor htmlUgovor;
    public Button bSnimi;
    public Label lBrUgovora;
    public Label lNazivUgovora;
    public Label lOd;
    public Label lDo;
    public Label lOpis;
    public boolean editUgovor = false;
    public ugovori_types ugovor;
    public Client client;
    private URL location;
    private ResourceBundle resources;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private DateTimeFormatter dateTimeFormatterSQL = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private JSONObject jObj = new JSONObject();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
    }


    public void setData() {
        lBrUgovora.setText(String.valueOf(ugovor.getBr()));
        lNazivUgovora.setText(ugovor.getNaziv());
        lOd.setText(ugovor.getPocetakUgovora());
        lDo.setText(ugovor.getKrajUgovora());
        lOpis.setText(ugovor.getKomentar());
        htmlUgovor.setHtmlText(ugovor.getTextUgovora());
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
}
