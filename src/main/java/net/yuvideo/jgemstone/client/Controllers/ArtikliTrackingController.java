package net.yuvideo.jgemstone.client.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.yuvideo.jgemstone.client.classes.ArtikliTracking;
import net.yuvideo.jgemstone.client.classes.Client;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 2/14/18.
 */
public class ArtikliTrackingController implements Initializable {
    public TableView<ArtikliTracking> tblArtTracking;
    public TableColumn<ArtikliTracking, String> cArtikal;
    public TableColumn<ArtikliTracking, Integer> cKol;
    public TableColumn<ArtikliTracking, String> cIzMagacina;
    public TableColumn<ArtikliTracking, String> cUmagacin;
    public TableColumn<ArtikliTracking, String> cDatum;
    public TableColumn<ArtikliTracking, String> cOperater;
    public TableColumn<ArtikliTracking, String> cOpis;
    public Client client;
    public int artID;
    public int magID;
    public Label lInfo;
    private URL location;
    private ResourceBundle resources;
    public int uniqueID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        cArtikal.setCellValueFactory(new PropertyValueFactory<>("artikal"));
        cKol.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        cIzMagacina.setCellValueFactory(new PropertyValueFactory<>("source"));
        cUmagacin.setCellValueFactory(new PropertyValueFactory<>("dest"));
        cDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        cOperater.setCellValueFactory(new PropertyValueFactory<>("operater"));
        cOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));

        tblArtTracking.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ArtikliTracking>() {
            @Override
            public void changed(ObservableValue<? extends ArtikliTracking> observable, ArtikliTracking oldValue, ArtikliTracking newValue) {
                lInfo.setText(newValue.getOpis());
            }
        });

    }


    public void setData() {
        ArtikliTracking artikliTracking = new ArtikliTracking();
        artikliTracking.initArtikle(client, artID, magID, uniqueID);
        tblArtTracking.setItems(FXCollections.observableArrayList(artikliTracking.getArtikliTrackingArrayList()));


    }


}
