package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.UserData;
import net.yuvideo.jgemstone.client.classes.Users;
import org.json.JSONObject;

/**
 * Created by zoom on 8/29/16.
 */
public class EditKorisnikController implements Initializable {


  public Tab tabKorisnickiPodaci;
  public int userID;
  //TABS
  public AnchorPane anchroKorsinikPodaci;
  public AnchorPane anchorKorisnikUsluge;
  public AnchorPane anchorKorisnikUgovori;
  public AnchorPane anchorKorisnikOprema;
  public Tab tabKorisnikUsluge;
  public Tab tabUplate;
  public JFXTabPane tabKorisnikEdit;
  private Client client;
  public Users userEdit;
  Logger LOGGER = Logger.getLogger("EDIT_USERS");
  JSONObject jObj;
  KorisnikPodaciController KorisnikPodaciController;
  KorisnikUslugeController korisnikUslugeController;
  KorisnikUgovoriController korisnikUgovoriController;
  KorisnikOpremaController korisnikOpremaController;
  private ResourceBundle resource;
  private URL location;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.resource = resources;
    this.location = location;


  }




  public void loadKorisnikData() {
    FXMLLoader fxmlLoader = new FXMLLoader(
        ClassLoader.getSystemResource("fxml/KorisnikPodaci.fxml"), resource);

    try {
      anchroKorsinikPodaci.getChildren().clear();
      anchroKorsinikPodaci.getChildren().add(fxmlLoader.load());

    } catch (IOException e) {
      e.printStackTrace();
    }

    KorisnikPodaciController = fxmlLoader.getController();
    KorisnikPodaciController.setClient(this.client);
    KorisnikPodaciController.userEditID = userID;
    KorisnikPodaciController.setData();


  }

  public void loadKorisnikServices() {
    FXMLLoader fxmlLoader = new FXMLLoader(
        ClassLoader.getSystemResource("fxml/KorisnikUsluge.fxml"), resource);

    try {
      anchorKorisnikUsluge.getChildren().clear();
      anchorKorisnikUsluge.getChildren().add(fxmlLoader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }

    korisnikUslugeController = fxmlLoader.getController();
    korisnikUslugeController.setClient(this.client);
    korisnikUslugeController.userID = userID;
    korisnikUslugeController.userEdit = userEdit;
    korisnikUslugeController.setData();


  }

  public void loadKorisnikUgovori() {
    FXMLLoader fxmlLoader = new FXMLLoader(
        ClassLoader.getSystemResource("fxml/KorisnikUgovori.fxml"), resource);

    try {
      anchorKorisnikUgovori.getChildren().clear();
      anchorKorisnikUgovori.getChildren().add(fxmlLoader.load());
      anchorKorisnikUgovori.autosize();
    } catch (IOException e) {
      e.printStackTrace();
    }

    korisnikUgovoriController = fxmlLoader.getController();
    korisnikUgovoriController.setClient(this.client);
    korisnikUgovoriController.userID = userID;
    korisnikUgovoriController.user = new UserData(client, userID);
    korisnikUgovoriController.set_data();


  }

  public void loadKorisnikOprema() {
    FXMLLoader fxmlLoader = new FXMLLoader(
        ClassLoader.getSystemResource("fxml/KorisnikOprema.fxml"), resource);

    try {
      anchorKorisnikOprema.getChildren().clear();
      anchorKorisnikOprema.getChildren().add(fxmlLoader.load());
      anchorKorisnikOprema.autosize();
    } catch (IOException e) {
      e.printStackTrace();
    }

    korisnikOpremaController = fxmlLoader.getController();
    korisnikOpremaController.setClient(this.client);
    korisnikOpremaController.user = new UserData(client, userID);
    korisnikOpremaController.setData();

  }


  public void loadKorisnikUplate() {
    FXMLLoader fxmlLoader = null;
    fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("fxml/KorisnikUplate.fxml"),
        resource);

    try {
      tabUplate.setContent(fxmlLoader.load());
      tabUplate.getContent().autosize();
    } catch (IOException e) {
      e.printStackTrace();
    }
    KorisnikUplateController korisnikUplateController = fxmlLoader.getController();
    korisnikUplateController.setClient(client);
    korisnikUplateController.setUserID(userID);
    korisnikUplateController.initData();
    if (!userEdit.getJbroj().isEmpty()) {
      tabKorisnikEdit.getSelectionModel().select(tabUplate);
    }

  }


  public void refreshUgovori(Event event) {

    korisnikUslugeController.refreshUgovori();
    korisnikUgovoriController.set_data();
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void refreshUplate(Event event) {
  }
}


