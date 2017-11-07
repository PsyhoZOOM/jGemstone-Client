package net.yuvideo.jgemstone.client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Users;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 4/10/17.
 */
public class KorisnikFirmaController implements Initializable {
    public Button bSnimiFirma;
    public Client client;
    public int userID;
    public TextField tNazivFime;
    public TextField tKodBanke;
    public TextField tPIB;
    public TextField tMaticniBroj;
    public TextField tTekuciRacun;
    public CheckBox chkIsFirma;
    URL location;
    ResourceBundle resources;
    public Users user;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;


    }

    public void bSnimiFirmu(ActionEvent actionEvent) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "setUserFirma");
        jsonObject.put("userID", user.getId());
        jsonObject.put("firma", chkIsFirma.isSelected());
        jsonObject.put("nazivFirme", tNazivFime.getText());
        jsonObject.put("kodBanke", tKodBanke.getText());
        jsonObject.put("pib", tPIB.getText());
        jsonObject.put("maticniBroj", tMaticniBroj.getText());
        jsonObject.put("tekuciRacun", tTekuciRacun.getText());
        jsonObject = client.send_object(jsonObject);

        if (jsonObject.has("ERROR")) {
            AlertUser.error("GRESKA", jsonObject.getString("ERROR"));
        } else {
            AlertUser.info("Firma snimljena", String.format("Firma %s je snmiljena", tNazivFime.getText()));
        }

    }


    public void setData() {
        JSONObject jObj = new JSONObject();
        jObj.put("action", "getUserFirma");
        jObj.put("userID", user.getId());
        jObj = client.send_object(jObj);

        if (jObj.has("ERROR")) {
            AlertUser.error("GREKSA", jObj.getString("ERROR"));
            return;
        }

        chkIsFirma.setSelected(true);
        if (jObj.has("nazivFirme"))
            tNazivFime.setText(jObj.getString("nazivFirme"));


        if (jObj.has("kodBanke"))
        tKodBanke.setText(jObj.getString("kodBanke"));

        if (jObj.has("pib"))
            tPIB.setText(jObj.getString("pib"));

        if (jObj.has("maticniBroj"))
            tMaticniBroj.setText(jObj.getString("maticniBroj"));

        if (jObj.has("tekuciRacun"))
            tTekuciRacun.setText(jObj.getString("tekuciRacun"));


    }
}
