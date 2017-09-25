package net.yuvideo.jgemstone.client.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import net.yuvideo.jgemstone.client.classes.Adrese;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Mesta;
import net.yuvideo.jgemstone.client.classes.Users;
import net.yuvideo.jgemstone.client.classes.messageS;

/**
 * Created by zoom on 8/8/16.
 */
public class NovKorisnikController implements Initializable {
    public Button bClose;
    public TextField tFullName;
    public DatePicker tDatumRodjenja;
    public TextField tPostBr;
    public TextField tBrLk;
    public TextField tJMBG;
    public TextField tFiksni;
    public TextField tMobilni;
    public TextArea tKomentar;
    public ComboBox<Mesta> cmbMesto;
    public ComboBox<Adrese> cmbAdresa;
    public Users user;
    public boolean user_saved;
    Logger LOGGER = LogManager.getLogger("NEW_USER");
    //JSON
    JSONObject jObj;
    DateTimeFormatter dateTimeFormatterRodjen = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private Stage stage;
    private Client client;
    private messageS mess;
    private Alert alert;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tDatumRodjenja.setValue(LocalDate.now());

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

    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void bSaveUser(ActionEvent actionEvent) {

        jObj = new JSONObject();
        jObj.put("action", "new_user");

        jObj.put("fullName", tFullName.getText());
        jObj.put("datumRodjenja", tDatumRodjenja.getValue().format(dateTimeFormatterRodjen));
        jObj.put("postBr", tPostBr.getText());
        jObj.put("mesto", cmbMesto.getEditor().getText());
        jObj.put("brLk", tBrLk.getText());
        jObj.put("JMBG", tJMBG.getText());
        jObj.put("adresa", cmbAdresa.getEditor().getText());
        jObj.put("komentar", tKomentar.getText());
        jObj.put("telFiksni", tFiksni.getText());
        jObj.put("telMobilni", tMobilni.getText());


        jObj = client.send_object(jObj);

        if (jObj.get("Message").equals("ERROR")) {

            user_saved = false;

            AlertUser.error("Greska", "Korisnik nije napravljne \n" + jObj.getString("ERROR_MESSAGE"));
        } else if (jObj.get("Message").equals("user_saved")) {

            AlertUser.info("Informacija", "Korisnik je snimljen");

            user_saved = true;
            user = new Users();
            user.setId(jObj.getInt("userID"));
            Stage stage = (Stage) bClose.getScene().getWindow();
            stage.close();
        }


    }
}
