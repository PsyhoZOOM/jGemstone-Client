package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Magacin;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.Settings;
import org.json.JSONObject;

/**
 * Created by PsyhoZOOM@gmail.com on 2/9/18.
 */
public class MagacinPreviewController implements Initializable {

  public TableView<Magacin> tblMagacini;
  public TableColumn<Magacin, Integer> cID;
  public TableColumn<Magacin, String> cNaziv;
  public TableColumn<Magacin, String> cOpis;
  public TableColumn<Magacin, Boolean> cGlavni;
  public Button bNov;
  public Button bIzmena;
  public Button bIzbrisi;




  private URL location;
  private ResourceBundle resources;
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    cID.setCellValueFactory(new PropertyValueFactory<>("id"));
    cNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
    cOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
    cGlavni.setCellValueFactory(new PropertyValueFactory<>("glavniMagacin"));


  }

  public void showData() {
    Magacin magacin = new Magacin();
    ObservableList tMagacin = FXCollections.observableArrayList(magacin.getMagaciniArr(client));
    tblMagacini.setItems(tMagacin);

  }

  public void nov(ActionEvent actionEvent) {
    NewInterface newMagacin = new NewInterface("fxml/MagacinEdit.fxml", "Nov Magacin",
        this.resources);
    MagacinEditController magacinEditController = newMagacin.getLoader().getController();
    magacinEditController.setClient(this.client);
    magacinEditController.edit = false;
    newMagacin.getStage().showAndWait();
    showData();

  }

  public void izmena(ActionEvent actionEvent) {
    NewInterface newMagacin = new NewInterface("fxml/MagacinEdit.fxml", "Nov Magacin",
        this.resources);
    MagacinEditController magacinEditController = newMagacin.getLoader().getController();
    magacinEditController.setClient(this.client);
    magacinEditController.edit = true;
    magacinEditController.setData(tblMagacini.getSelectionModel().getSelectedItem());
    newMagacin.getStage().showAndWait();
    showData();
  }

  public void izbrisi(ActionEvent actionEvent) {
    if (tblMagacini.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }

    if (AlertUser.yesNo("BRISANJE MAGACINA",
        String.format(
            "Svi Artikli iz magacina %s ce biti obrisani!!! \n Da li ste sigurni da želite da izbrišete magacin?",
            tblMagacini.getSelectionModel().getSelectedItem().getNaziv()))) {
      return;
    }

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "deleteMagacin");
    jsonObject.put("id", tblMagacini.getSelectionModel().getSelectedItem().getId());
    jsonObject = client.send_object(jsonObject);

    if (jsonObject.has("ERROR")) {
      AlertUser.error("GRESKA", jsonObject.getString("ERROR"));
    } else {
      AlertUser.info("MAGACIN OBRISAN", String.format("Magacin %s je obrisan!",
          tblMagacini.getSelectionModel().getSelectedItem().getNaziv()));
    }

    showData();


  }

  public void setClient(Client client) {
    this.client = client;
  }
}
