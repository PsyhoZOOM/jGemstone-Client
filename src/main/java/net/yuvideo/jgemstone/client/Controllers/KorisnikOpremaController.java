package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Artikli;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Magacin;
import net.yuvideo.jgemstone.client.classes.UserData;
import org.json.JSONObject;

/**
 * Created by PsyhoZOOM@gmail.com on 2/15/18.
 */
public class KorisnikOpremaController implements Initializable {

  public TableView<Artikli> tblUserOprema;
  public TableColumn<Artikli, String> cNaziv;
  public TableColumn<Artikli, String> cProzivodjac;
  public TableColumn<Artikli, String> cModel;
  public TableColumn<Artikli, String> cSerijski;
  public TableColumn<Artikli, String> cMac;
  public TableColumn<Artikli, String> cPON;
  public TableColumn<Artikli, String> cOpis;
  public Label lInfo;
  public JFXButton bRazduzi;
  private Client client;
  public UserData user;
  private URL location;
  private ResourceBundle resources;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    cNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
    cProzivodjac.setCellValueFactory(new PropertyValueFactory<>("proizvodjac"));
    cModel.setCellValueFactory(new PropertyValueFactory<>("model"));
    cSerijski.setCellValueFactory(new PropertyValueFactory<>("serijski"));
    cMac.setCellValueFactory(new PropertyValueFactory<>("mac"));
    cPON.setCellValueFactory(new PropertyValueFactory<>("pon"));
    cOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));

    tblUserOprema.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<Artikli>() {
          @Override
          public void changed(ObservableValue<? extends Artikli> observable, Artikli oldValue,
              Artikli newValue) {
            lInfo.setText(String.format("Naziv: %s \nDatum zaduženja: %s \nOpis: %s",
                newValue.getNaziv(), newValue.getDatum(), newValue.getOpis()));
          }
        });
  }


  public void setData() {
    UserData userData = new UserData(client, user.getId());
    tblUserOprema.setItems(FXCollections.observableArrayList(userData.getUserOprema()));
  }


  public void setClient(Client client) {
    this.client = client;
  }

  public void razduziArtikal(ActionEvent actionEvent) {
    if (tblUserOprema.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }

    int artkalID = tblUserOprema.getSelectionModel().getSelectedItem().getId();
    int magacinID = 0;
    Magacin magacini = new Magacin();
    ArrayList<Magacin> magaciniArr = magacini.getMagaciniArr(client);
    for (Magacin magacin : magaciniArr) {
      if (magacin.isGlavniMagacin()) {
        magacinID = magacin.getId();
      }
    }
    if (magacinID == 0) {
      AlertUser.error("GRESKA", "Ne mogu da pronadjem Glavni Magacin");
      return;
    }

    JSONObject object = new JSONObject();
    object.put("action", "razduziUserArtikal");
    object.put("artikalID", artkalID);
    object.put("magacinID", magacinID);
    object.put("userID", user.getId());
    object.put("komentar", "RAZDUZENJE");

    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    } else {
      setData();
    }


  }
}
