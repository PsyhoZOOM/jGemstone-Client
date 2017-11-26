package net.yuvideo.jgemstone.client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.OstaleUsluge;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 11/26/17.
 */
public class OstaleUslugeEditController implements Initializable {
    public TextField tNaziv;
    public TextField tCena;
    public TextField tPDV;
    public TextArea tKomentar;
    public Button bSnimi;
    public boolean edit;
    public Client client;
    public OstaleUsluge ostaleUsluge;
    private URL localtion;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.localtion = location;
        this.resources = resources;
    }

    public void setData() {
        this.tNaziv.setText(ostaleUsluge.getNazivUsluge());
        this.tCena.setText(String.valueOf(ostaleUsluge.getCena()));
        this.tPDV.setText(String.valueOf(ostaleUsluge.getPdv()));
        this.tKomentar.setText(ostaleUsluge.getKomentar());


    }

    public void saveUslugu(ActionEvent actionEvent) {
        JSONObject jsonObject = new JSONObject();
        if (edit) {
            jsonObject.put("action", "updateOstaleUslugu");
        } else {
            jsonObject.put("action", "snimiOstaleUslugu");
        }
        jsonObject.put("naziv", tNaziv.getText());
        jsonObject.put("cena", tCena.getText());
        jsonObject.put("pdv", tPDV.getText());
        jsonObject.put("komentar", tKomentar.getText());
        if (ostaleUsluge != null) {
            jsonObject.put("id", ostaleUsluge.getId());
        }
        jsonObject = client.send_object(jsonObject);
        if (jsonObject.has("ERROR")) {
            AlertUser.error("GRESKA", jsonObject.getString("ERROR"));
        } else {
            AlertUser.info("USLUGA SNIMLJENA", String.format("Usluga %s je izmenjena", tNaziv.getText()));
        }


    }

}
