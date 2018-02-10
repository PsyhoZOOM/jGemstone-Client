package net.yuvideo.jgemstone.client.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.yuvideo.jgemstone.client.classes.*;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 2/10/18.
 */
public class ArtikliZaduzivanjeController implements Initializable {
    public Label lOpis;
    public Label lNaziv;
    public Label lModel;
    public Label lSerijski;
    public Label lPSerijski;
    public Label lMac;
    public Label lDobavljac;
    public Label lBrDoikumenta;
    public Label lNabavnaCena;
    public Label lKolicina;
    public Label lMagacin;
    public TableView tblMagacin;
    public TextField tMagaccinSearch;
    public Button bTraziMagacin;
    public TextField tKorisnikSearch;
    public TableColumn cMagNaziv;
    public TableColumn cMagOpis;
    public TableColumn cUserID;
    public TableColumn cUserIme;
    public TableColumn cUserAdresa;
    public TableColumn cUserMesto;
    public TableView tblUsers;
    public Client client;
    public Artikli artikal;
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
        cMagNaziv.setCellValueFactory(new PropertyValueFactory<Magacin, String>("naziv"));
        cMagOpis.setCellValueFactory(new PropertyValueFactory<Magacin, String>("opis"));

        cUserID.setCellValueFactory(new PropertyValueFactory<Users, String>("jbroj"));
        cUserIme.setCellValueFactory(new PropertyValueFactory<Users, String>("ime"));
        cUserAdresa.setCellValueFactory(new PropertyValueFactory<Users, String>("adresa"));
        cUserMesto.setCellValueFactory(new PropertyValueFactory<Users, String>("mesto"));
    }


    public void setData() {
        lNaziv.setText(artikal.getNaziv());
        lModel.setText(artikal.getModel());
        lSerijski.setText(artikal.getSerijski());
        lPSerijski.setText(artikal.getPserijski());
        lMac.setText(artikal.getMac());
        lOpis.setText(artikal.getOpis());
        lDobavljac.setText(artikal.getDobavljac());
        lBrDoikumenta.setText(artikal.getBrDok());
        lNabavnaCena.setText(String.valueOf(artikal.getNabavnaCena()));
        lKolicina.setText(String.valueOf(artikal.getKolicina()));
        lMagacin.setText(getNazivMagacina(artikal.getIdMagacin()));

    }

    private String getNazivMagacina(int idMagacin) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "getMagacin");
        jsonObject.put("idMagacin", idMagacin);
        jsonObject = client.send_object(jsonObject);
        if (jsonObject.has("ERROR")) {
            AlertUser.error("GRESKA", jsonObject.getString("ERROR"));
            return null;
        }

        return jsonObject.getString("naziv");

    }

    public void bTraziMagacin(ActionEvent actionEvent) {
        Magacin magacin = new Magacin();
        ObservableList magacini = FXCollections.observableArrayList(magacin.getMagacin(tMagaccinSearch.getText(), this.client));
        tblMagacin.setItems(magacini);
    }

    public void bTraziUser(ActionEvent actionEvent) {
        Users user = new Users();
        ObservableList users = FXCollections.observableArrayList(user.getUsers(client, tKorisnikSearch.getText()));
        tblUsers.setItems(users);


    }
}
