package JGemstone.interfaces;

import JGemstone.classes.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

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

        bClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage = (Stage) bClose.getScene().getWindow();
                stage.close();
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
