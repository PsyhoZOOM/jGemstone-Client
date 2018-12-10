package net.yuvideo.jgemstone.client.Controllers.Administration.Groups;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import org.json.JSONObject;

public class GroupNew implements Initializable {


  public JFXTextField tNazivGrupe;
  private URL location;
  private ResourceBundle resources;
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

  }


  public void addGroup(ActionEvent actionEvent) {
    if (tNazivGrupe.getText().trim().isEmpty()) {
      return;
    }
    JSONObject object = new JSONObject();
    object.put("action", "addGroup");
    object.put("groupName", tNazivGrupe.getText());
    object = client.send_object(object);

    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    } else {
      AlertUser.info("INFO", String.format("Grupa %s je dodata", tNazivGrupe.getText()));
    }

    Stage window = (Stage) tNazivGrupe.getScene().getWindow();
    window.close();


  }

  public void setClient(Client client) {
    this.client = client;
  }
}
