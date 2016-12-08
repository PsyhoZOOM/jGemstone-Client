package JGemstone.interfaces;

import JGemstone.classes.Client;
import JGemstone.classes.Users;
import JGemstone.classes.messageS;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 8/8/16.
 */
public class NovKorisnikController implements Initializable {
    public Button bClose;
    public GridPane gridPane;
    public TextField tUserName;
    public TextField tFullName;
    public TextField tDatumRodjenja;
    public TextField tPostBr;
    public TextField tMesto;
    public TextField tBrLk;
    public TextField tJMBG;
    public TextField tAdresaRacuna;
    public TextField tAdresa;
    public TextField tFiksni;
    public TextField tMobilni;
    public TextArea tKomentar;
    public TextField tBroj;

    private Stage stage;
    private Client client;
    public Users user;
    private messageS mess;
    private Alert alert;




    Logger LOGGER = LogManager.getLogger("NEW_USER");

    //JSON
    JSONObject jObj;


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
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void bSaveUser(ActionEvent actionEvent) throws IOException {
        jObj = new JSONObject();
        jObj.put("action", "new_user");

        jObj.put("userName", tUserName.getText());
        jObj.put("fullName", tFullName.getText());
        jObj.put("datumRodjenja", tDatumRodjenja.getText());
        jObj.put("postBr", tPostBr.getText());
        jObj.put("mesto", tMesto.getText());
        jObj.put("brLk", tBrLk.getText());
        jObj.put("JMBG", tJMBG.getText());
        jObj.put("adresaRacuna", tAdresaRacuna.getText());
        jObj.put("adresa", tAdresa.getText());
        jObj.put("komentar", tKomentar.getText());
        jObj.put("telFiksni", tFiksni.getText());
        jObj.put("telMobilni", tMobilni.getText());
        jObj.put("jbroj",tBroj.getText());



        jObj = client.send_object(jObj);

        if (jObj.get("message").equals("user_exist") || jObj.getString("message").equals("user_no_exist")) {

            alert = new Alert(Alert.AlertType.WARNING, "Korisnik postoji", ButtonType.OK);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("GREŠKA!");
            alert.initOwner(stage);
            alert.showAndWait();
        }
        if (jObj.get("message").equals("user_saved")) {
            alert = new Alert(Alert.AlertType.INFORMATION, "Korisnik snimljen", ButtonType.OK);
            alert.setTitle("Informacija");
            alert.setHeaderText("Novi korisnik je snimljen!");
            alert.initOwner(stage);
            alert.showAndWait();
        }

        jObj = client.send_object(null);
        user = new Users();
        user.setId(jObj.getInt("id"));
        user.setUsername(jObj.getString("userName"));

    }
}
