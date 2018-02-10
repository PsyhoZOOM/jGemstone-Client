package net.yuvideo.jgemstone.client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 2/10/18.
 */
public class ArtikliZaduzivanjeMagUserController implements Initializable {
    public TextField tTrazi;
    public TableView tblArtikli;
    public TableColumn cNaziv;
    public TableColumn cModel;
    public TableColumn cSerijski;
    public TableColumn cMac;
    public TableColumn cPSerijski;
    public TableColumn cDobavljac;
    public TableColumn cBrDokumenta;
    public TableColumn cNabavnaCena;
    public TableColumn cDatum;
    public TableColumn cJMere;
    public TableColumn cKolicina;
    public TableColumn cOpis;
    public MenuItem mInfo;
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
    }

    public void setData() {

    }

    public void showInfo(ActionEvent actionEvent) {
    }

    public void searchArtikl(ActionEvent actionEvent) {
    }

    public void zaduzi(ActionEvent actionEvent) {
    }

    public void zaduziNov(ActionEvent actionEvent) {
    }
}
