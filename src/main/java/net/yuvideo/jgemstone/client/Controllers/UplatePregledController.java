package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Uplate;
import net.yuvideo.jgemstone.client.classes.Users;
import org.json.JSONObject;

public class UplatePregledController implements Initializable {

  public TableView<Uplate> tblUplate;
  public TableColumn<Uplate, String> cDatumUplate;
  public TableColumn<Uplate, String> cOperater;
  public TableColumn<Uplate, String> cMestoUplate;
  public TableColumn<Uplate, String> cNazivUsluge;
  public TableColumn<Uplate, Double> cUplaceno;
  public TableColumn cZaMesec;
  public Users user;
  public Client client;
  private URL location;
  private ResourceBundle resources;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    cDatumUplate.setCellValueFactory(new PropertyValueFactory<>("datumUplate"));
    cOperater.setCellValueFactory(new PropertyValueFactory<>("operater"));
    cMestoUplate.setCellValueFactory(new PropertyValueFactory<>("mestoUplate"));
    cNazivUsluge.setCellValueFactory(new PropertyValueFactory<>("nazivPaket"));
    cUplaceno.setCellValueFactory(new PropertyValueFactory<>("uplaceno"));
    cZaMesec.setCellValueFactory(new PropertyValueFactory<>("zaMesec"));


  }

  public void setData() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "getUserUplate");
    jsonObject.put("userID", user.getId());

    jsonObject = client.send_object(jsonObject);
    ArrayList<Uplate> uplateArrayList = new ArrayList<>();
    for (int i = 0; i < jsonObject.length(); i++) {
      JSONObject uplataObj = jsonObject.getJSONObject(String.valueOf(i));
      Uplate uplate = new Uplate();
      uplate.setId(uplataObj.getInt("id"));
      uplate.setUplaceno(uplataObj.getDouble("uplaceno"));
      uplate.setMestoUplate(uplataObj.getString("mestoUplate"));
      uplate.setDatumUplate(uplataObj.getString("datumUplate"));
      uplate.setNazivPaket(uplataObj.getString("nazivServisa"));
      uplate.setOperater(uplataObj.getString("operater"));
      uplate.setZaMesec(uplataObj.getString("zaMesec"));
      uplateArrayList.add(uplate);

    }

    if (jsonObject.has("ERROR")) {
      AlertUser.error("GRESKA", jsonObject.getString("ERROR"));
      return;
    }
    tblUplate.setItems(FXCollections.observableArrayList(uplateArrayList));


  }
}
