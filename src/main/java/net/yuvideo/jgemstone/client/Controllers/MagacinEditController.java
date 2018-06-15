package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Magacin;
import org.json.JSONObject;

/**
 * Created by PsyhoZOOM@gmail.com on 2/9/18.
 */
public class MagacinEditController implements Initializable {

  public TextField tNaziv;
  public TextArea taOpis;
  public boolean edit = false;
  private Client client;
  public CheckBox chkGlavni;
  private URL location;
  private ResourceBundle resource;
  private int magacinID;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resource = resources;
  }


  public void setData(Magacin magacin) {
    tNaziv.setText(magacin.getNaziv());
    taOpis.setText(magacin.getOpis());
    chkGlavni.setSelected(magacin.isGlavniMagacin());
    this.magacinID = magacin.getId();


  }


  public void snimiMagacin(ActionEvent actionEvent) {
    JSONObject obj = new JSONObject();
    if (edit) {
      obj.put("action", "editMagacin");
    } else {
      obj.put("action", "novMagacin");
    }

    obj.put("id", magacinID);
    obj.put("naziv", tNaziv.getText());
    obj.put("opis", taOpis.getText());
    obj.put("glavniMagacin", chkGlavni.isSelected());

    obj = client.send_object(obj);
    if (obj.has("ERROR")) {
      AlertUser.error("GRESKA", obj.getString("ERROR"));
    } else {
      AlertUser.info("INFO", String.format("Magacin %s je snimljen", tNaziv.getText()));
    }
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
