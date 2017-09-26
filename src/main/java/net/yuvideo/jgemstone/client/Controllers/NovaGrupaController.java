package net.yuvideo.jgemstone.client.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Groups;
import net.yuvideo.jgemstone.client.classes.messageS;

/**
 * Created by zoom on 9/1/16.
 */
public class NovaGrupaController implements Initializable {
    public Button bClose;
    public TextField tNaziv;
    public TextField tCena;
    public TextArea tOpis;
    public CheckBox chPrepaid;
    Client client;
    String GroupName;
    Groups group;
    //JSON
    JSONObject jObj;
    private messageS mess;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) bClose.getScene().getWindow();
                stage.close();

            }
        });
    }

    public void snimiNovaGrupa(ActionEvent actionEvent) {
        int selected = 0;

        if (chPrepaid.isSelected()) {
            selected = 1;
        } else {
            selected = 0;
        }

        jObj = new JSONObject();
        jObj.put("action", "save_group");
        jObj.put("groupName", tNaziv.getText());
        jObj.put("cena", tCena.getText());
        jObj.put("prepaid", Integer.valueOf(selected));
        jObj.put("opis", tOpis.getText());

        jObj = client.send_object(jObj);

    }
}
