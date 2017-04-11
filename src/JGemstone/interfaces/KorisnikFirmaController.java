package JGemstone.interfaces;

import JGemstone.classes.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 4/10/17.
 */
public class KorisnikFirmaController implements Initializable {
    public TextField tNazivFime;
    public TextField tKontaktOsoba;
    public TextField tKodBanke;
    public TextField tPIB;
    public TextField tMaticniBroj;
    public TextField tTekuciRacun;
    public TextField tBrojFakture;
    public Button bSnimiFirma;
    public Client client;
    public int userID;
    URL location;
    ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;


    }
}
