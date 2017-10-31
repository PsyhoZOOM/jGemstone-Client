package net.yuvideo.jgemstone.client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import net.yuvideo.jgemstone.client.classes.Client;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 4/10/17.
 */
public class KorisnikFirmaController implements Initializable {
    public Button bSnimiFirma;
    public Client client;
    public int userID;
    public TextField tNazivFime;
    public TextField tKontaktOsoba;
    public TextField tKodBanke;
    public TextField tPIB;
    public TextField tMaticniBroj;
    public TextField tTekuciRacun;
    URL location;
    ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;


    }

    public void bSnimiFirmu(ActionEvent actionEvent) {
    }
}
