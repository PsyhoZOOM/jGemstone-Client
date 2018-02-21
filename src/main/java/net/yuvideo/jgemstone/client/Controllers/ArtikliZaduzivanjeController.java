package net.yuvideo.jgemstone.client.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
    public TableView<Magacin> tblMagacin;
    public TextField tMagaccinSearch;
    public Button bTraziMagacin;
    public TextField tKorisnikSearch;
    public TableColumn cMagNaziv;
    public TableColumn cMagOpis;
    public TableColumn cUserID;
    public TableColumn cUserIme;
    public TableColumn cUserAdresa;
    public TableColumn cUserMesto;
    public TableView<Users> tblUsers;
    public Client client;
    public Artikli artikal;
    public Label lProizvodjac;
    public TextField tKolicina;
    public Button bZaduziMagacin;
    public TextArea tOpis;
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


        tblMagacin.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Magacin>() {
            @Override
            public void changed(ObservableValue<? extends Magacin> observable, Magacin oldValue, Magacin newValue) {
                if (artikal.getIdMagacin() == newValue.getId()) {
                    bZaduziMagacin.setDisable(true);
                } else {
                    bZaduziMagacin.setDisable(false);
                }
            }
        });
    }


    public void setData() {
        if (artikal.getKolicina() <= 1) {
            tKolicina.setDisable(true);
        }
        lNaziv.setText(artikal.getNaziv());
        lPSerijski.setText(artikal.getProizvodjac());
        lModel.setText(artikal.getModel());
        lSerijski.setText(artikal.getSerijski());
        lPSerijski.setText(artikal.getPon());
        lMac.setText(artikal.getMac());
        lOpis.setText(artikal.getOpis());
        lDobavljac.setText(artikal.getDobavljac());
        lBrDoikumenta.setText(artikal.getBrDok());
        lNabavnaCena.setText(String.valueOf(artikal.getNabavnaCena()));
        lKolicina.setText(String.valueOf(artikal.getKolicina()));
        lMagacin.setText(getNazivMagacina(artikal.getIdMagacin()));
        tKolicina.setText(String.valueOf(artikal.getKolicina()));

    }

    private String getNazivMagacina(int idMagacin) {
        Magacin magacin = new Magacin();
        magacin = magacin.getMagacin(idMagacin, client);
        return magacin.getNaziv();
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

    public void zaduziMagacin(ActionEvent actionEvent) {
        if (tblMagacin.getSelectionModel().getSelectedIndex() == -1) {
            AlertUser.warrning("GRESKA", "Izaberite magacin za zaduživanje!");
            return;
        }
        Magacin mag = tblMagacin.getSelectionModel().getSelectedItem();
        JSONObject object = new JSONObject();
        object.put("action", "zaduziMagacinArtikal");
        object.put("artikalID", artikal.getId());
        object.put("destMagID", mag.getId());
        object.put("sourceMagID", artikal.getIdMagacin());
        object.put("isUser", false);
        object.put("kolicina", Integer.valueOf(tKolicina.getText()));
        object.put("uniqueID", artikal.getUniqueID());
        object.put("opis", tOpis.getText());

        object = client.send_object(object);

        if (object.has("ERROR")) {
            AlertUser.error("GRESKA", object.getString("ERROR"));
        } else {
            AlertUser.info("INFO", String.format("%s je zaduzen sa %s %s", mag.getNaziv(), tKolicina.getText(), artikal.getNaziv()));
            Stage stage = (Stage) bTraziMagacin.getScene().getWindow();
            stage.close();
        }
    }

    public void zaduziKorisnika(ActionEvent actionEvent) {
        if (tblUsers.getSelectionModel().getSelectedIndex() == -1) {
            AlertUser.warrning("GRESKA", "Izaberite korisnika za zaduživanje!");
            return;
        }
        Users user = tblUsers.getSelectionModel().getSelectedItem();
        JSONObject object = new JSONObject();
        object.put("action", "zaduziUserArtikal");
        object.put("artikalID", artikal.getId());
        object.put("destUserID", user.getId());
        object.put("sourceMagID", artikal.getIdMagacin());
        object.put("kolicina", Integer.valueOf(tKolicina.getText()));
        object.put("isUser", true);
        object.put("uniqueID", artikal.getUniqueID());
        object.put("opis", tOpis.getText());

        object = client.send_object(object);

        if (object.has("ERROR")) {
            AlertUser.error("GRESKA", object.getString("ERROR"));
        } else {
            AlertUser.info("INFO", String.format("%s je zaduzen sa %s %s", user.getIme(), tKolicina.getText(), artikal.getNaziv()));
            Stage stage = (Stage) bTraziMagacin.getScene().getWindow();
            stage.close();
        }

    }
}
