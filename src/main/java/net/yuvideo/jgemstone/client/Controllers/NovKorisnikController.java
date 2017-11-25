package net.yuvideo.jgemstone.client.Controllers;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.*;
import org.json.JSONObject;

import javax.smartcardio.CardTerminal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    public Button bReadCardReader;
    public ImageView imgUserPhoto;
    public Users user;
    public boolean user_saved;
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
    public int freeID;

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


        bReadCardReader.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Image img1 = new Image(ClassLoader.getSystemResource("images/YuVideoLogo.png").toString());
                imgUserPhoto.setImage(img1);
                sr.scan(cmbCardReader.getValue());
                Users user = sr.getUserData();
                tFullName.setText(CyrToLatin.CirilicaToLatinica(user.getIme()));
                tJMBG.setText(user.getJMBG());
                tBrLk.setText(user.getBr_lk());
                tDatumRodjenja.setValue(LocalDate.parse(user.getDatum_rodjenja(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                cmbMesto.getEditor().setText(user.getMesto());
                cmbAdresa.getEditor().setText(user.getAdresa());
                Image img = SwingFXUtils.toFXImage(sr.toBufferedImage(), null);
                imgUserPhoto.setImage(img);


            }
        });

    }

    public void setClient(Client client) {
        List<CardTerminal> terminals = getTerminals();
        if (terminals != null) cmbCardReader.getItems().addAll(terminals);
        this.client = client;

    }

    public void bSaveUser(ActionEvent actionEvent) {

        String formatedUserJbroj = String.format("%05d", freeID);
        jObj = new JSONObject();
        jObj.put("action", "new_user");

        jObj.put("freeID", freeID);

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
        jObj.put("jBroj", formatedUserJbroj);


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

    private List<CardTerminal> getTerminals() {
        sr = new SmartCardReader();
        List<CardTerminal> terminals = sr.getTerminals();

        return terminals;
    }
}
