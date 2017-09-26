package net.yuvideo.jgemstone.client.Controllers;

import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Users;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

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
    public AnchorPane anchorKorisnikFirma;
    public AnchorPane anchorKorisnikOprema;
    public Tab tabKorisnikUsluge;
    public Client client;
    Logger LOGGER = Logger.getLogger("EDIT_USERS");
    JSONObject jObj;
    KorisnikPodaciController KorisnikPodaciController;
    KorisnikUslugeController korisnikUslugeController;
    KorisnikUgovoriController korisnikUgovoriController;
    KorisnikFirmaController korisnikFirmaController;
    private ResourceBundle resource;
    private URL location;
    public Users userEdit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;
        this.location = location;


    }


    public void loadKorisnikData() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("fxml/KorisnikPodaci.fxml"), resource);

        try {
            anchroKorsinikPodaci.getChildren().clear();
            anchroKorsinikPodaci.getChildren().add(fxmlLoader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }

        KorisnikPodaciController = fxmlLoader.getController();
        KorisnikPodaciController.client = client;
        KorisnikPodaciController.userEditID = userID;
        KorisnikPodaciController.setData();

    }

    public void loadKorisnikServices() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("fxml/KorisnikUsluge.fxml"), resource);


        try {
            anchorKorisnikUsluge.getChildren().clear();
            anchorKorisnikUsluge.getChildren().add(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        korisnikUslugeController = fxmlLoader.getController();
        korisnikUslugeController.client = client;
        korisnikUslugeController.userID = userID;
        korisnikUslugeController.userEdit = userEdit;
        korisnikUslugeController.setData();


    }

    public void loadKorisnikUgovori() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("fxml/KorisnikUgovori.fxml"), resource);

        try {
            anchorKorisnikUgovori.getChildren().clear();
            anchorKorisnikUgovori.getChildren().add(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        korisnikUgovoriController = fxmlLoader.getController();
        korisnikUgovoriController.client = client;
        korisnikUgovoriController.userID = userID;
        korisnikUgovoriController.set_data();


    }

    public void loadKorisnikFirma() {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("fxml/KorisnikFirma.fxml"), resource);

        anchorKorisnikFirma.getChildren().clear();
        try {
            anchorKorisnikFirma.getChildren().add(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        korisnikFirmaController = fxmlLoader.getController();
        korisnikFirmaController.client = this.client;
        korisnikFirmaController.userID = userID;
    }


    public void refreshUgovori(Event event) {

        korisnikUslugeController.refreshUgovori();
    }
}


