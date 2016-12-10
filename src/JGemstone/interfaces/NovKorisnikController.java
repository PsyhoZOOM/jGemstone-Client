package JGemstone.interfaces;

import JGemstone.classes.Client;
import JGemstone.classes.Users;
import JGemstone.classes.messageS;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.controlsfx.control.Notifications;
import org.json.JSONObject;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by zoom on 8/8/16.
 */
public class NovKorisnikController implements Initializable {
    public Button bClose;
    public GridPane gridPane;
    public TextField tUserName;
    public TextField tFullName;
    public JFXDatePicker tDatumRodjenja;
    public TextField tPostBr;
    public TextField tMesto;
    public TextField tBrLk;
    public TextField tJMBG;
    public TextField tAdresaRacuna;
    public JFXTextField tAdresa;
    public TextField tFiksni;
    public TextField tMobilni;
    public TextArea tKomentar;
    public TextField tBroj;
    public JFXSnackbar snackMessage;
    public HBox hboxTop;
    public Pane paneTop;
    public JFXTextField tAdresaKoriscenja;
    public Users user;
    public boolean user_saved;
    public String userName;
    Logger LOGGER = LogManager.getLogger("NEW_USER");
    //JSON
    JSONObject jObj;
    DateTimeFormatter dateTimeFormatterRodjen  = DateTimeFormatter.ofPattern("dd-MM-yyyy");
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
        gridPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().toString().equals("ESCAPE")) {
                    stage = (Stage) gridPane.getScene().getWindow();
                    stage.close();
                }
            }
        });
        snackMessage.registerSnackbarContainer(gridPane);

    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void bSaveUser(ActionEvent actionEvent)  {

        jObj = new JSONObject();
        jObj.put("action", "new_user");

        jObj.put("userName", tUserName.getText());
        jObj.put("fullName", tFullName.getText());
        jObj.put("datumRodjenja", tDatumRodjenja.getValue().format(dateTimeFormatterRodjen));
        jObj.put("postBr", tPostBr.getText());
        jObj.put("mesto", tMesto.getText());
        jObj.put("brLk", tBrLk.getText());
        jObj.put("JMBG", tJMBG.getText());
        jObj.put("adresaRacuna", tAdresaRacuna.getText());
        jObj.put("adresa", tAdresa.getText());
        jObj.put("adresaKoriscenja", tAdresaKoriscenja.getText());
        jObj.put("komentar", tKomentar.getText());
        jObj.put("telFiksni", tFiksni.getText());
        jObj.put("telMobilni", tMobilni.getText());
        jObj.put("jbroj",tBroj.getText());
        jObj.put("komentar", tKomentar.getText());



        jObj = client.send_object(jObj);

        if (jObj.get("Message").equals("user_exist") || jObj.getString("Message").equals("user_no_exist")) {

            user_saved = false;
            snackMessage.show("Korisnik postoji!", 10000);
            Notifications.create()
                    .title("Greška")
                    .text("Korisnik postoji!")
                    .hideAfter(Duration.seconds(3.0))
                    .position(Pos.BOTTOM_RIGHT)
                    .showError();
        }else if (jObj.get("Message").equals("user_saved")){
            Notifications.create()
                    .title("Informacija")
                    .text("Korisnik je snimljen")
                    .hideAfter(Duration.seconds(3.0))
                    .position(Pos.BOTTOM_RIGHT)
                    .showInformation();

            user_saved = true;
            userName = tUserName.getText();
            Stage  stage = (Stage) bClose.getScene().getWindow();
            stage.close();
        }else{
            Notifications.create()
                    .title("Greška")
                    .text("Error: " + jObj.getString("Message"))
                    .hideAfter(Duration.seconds(6.0))
                    .position(Pos.BOTTOM_RIGHT)
                    .showError();
        }


    }
}
