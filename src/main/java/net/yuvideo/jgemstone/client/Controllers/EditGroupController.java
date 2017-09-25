package net.yuvideo.jgemstone.client.Controllers;

import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Groups;
import net.yuvideo.jgemstone.client.classes.messageS;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by zoom on 9/1/16.
 */
public class EditGroupController implements Initializable {
    public TextField tNaziv;
    public TextField tCena;
    public CheckBox cPrepaid;
    public Client client;

    public Button bClose;
    public Button bSave;
    public Logger LOGGER;
    private messageS mess;
    private JSONObject jObj = new JSONObject();
    private Groups group;
    private int groupId;

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


    public void show_group(int groupId) {
        this.groupId = groupId;
        jObj.put("action", "get_group_data");
        jObj.put("groupId", groupId);

        jObj = client.send_object(jObj);

        tNaziv.setText(jObj.get("groupName").toString());
        tCena.setText(jObj.get("cena").toString());

        if (jObj.getInt("prepaid") == 1) {
            cPrepaid.setSelected(true);
        } else {
            cPrepaid.setSelected(false);
        }
    }


    public void saveGroup(ActionEvent actionEvent) {
        int prepaid;
        if (cPrepaid.isSelected()) {
            prepaid = 1;
        } else prepaid = 0;
        jObj = new JSONObject();
        jObj.put("action", "save_group_data");
        jObj.put("groupId", groupId);
        jObj.put("groupName", tNaziv.getText());
        jObj.put("cena", tCena.getText());
        jObj.put("prepaid", prepaid);
        jObj.put("opis", "");

        jObj = client.send_object(jObj);

    }

    public void deleteGroup(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "delete_group");
        jObj.put("groupID", groupId);
        jObj = client.send_object(jObj);
        LOGGER.log(Level.INFO, jObj.get("message").toString());
    }
}
