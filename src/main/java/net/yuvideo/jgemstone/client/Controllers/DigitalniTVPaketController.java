package net.yuvideo.jgemstone.client.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.digitalniTVPaket;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zoom on 2/2/17.
 */
public class DigitalniTVPaketController implements Initializable {
    public Client client;
    public TableView tblDTV;
    public TableColumn cNaziv;
    public TableColumn cPaket;
    public TableColumn cCena;
    public TableColumn cOpis;
    public Button bNov;
    public Button bEdit;
    public TableColumn cPDV;
    public TableColumn cCenaPDV;
    URL location;
    ResourceBundle resources;

    JSONObject jObj;

    DecimalFormat df = new DecimalFormat("#,##0.00");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        cNaziv.setCellValueFactory(new PropertyValueFactory<digitalniTVPaket, String>("naziv"));
        cCena.setCellValueFactory(new PropertyValueFactory<digitalniTVPaket, Double>("cena"));
        cPaket.setCellValueFactory(new PropertyValueFactory<digitalniTVPaket, Integer>("paketID"));
        cOpis.setCellValueFactory(new PropertyValueFactory<digitalniTVPaket, String>("opis"));
        cPDV.setCellValueFactory(new PropertyValueFactory<digitalniTVPaket, Double>("pdv"));
        cCenaPDV.setCellValueFactory(new PropertyValueFactory<digitalniTVPaket, Double>("cenaPDV"));

        cCena.setCellFactory(tc -> new TableCell<digitalniTVPaket, Double>() {
            @Override
            protected void updateItem(Double dbl, boolean bool) {
                super.updateItem(dbl, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(df.format(dbl));
                }
            }
        });

        cPDV.setCellFactory(tc -> new TableCell<digitalniTVPaket, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(df.format(item));
                }
            }
        });

        cCenaPDV.setCellFactory(tc -> new TableCell<digitalniTVPaket, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(df.format(item));
                }
            }
        });

    }

    public void showData() {
        ObservableList data = FXCollections.observableArrayList(get_paket());
        tblDTV.setItems(data);


    }

    private ArrayList<digitalniTVPaket> get_paket() {
        jObj = new JSONObject();
        jObj.put("action", "getDigitalTVPaketi");

        jObj = client.send_object(jObj);

        digitalniTVPaket dtvPak;
        ArrayList<digitalniTVPaket> dtvPakArr = new ArrayList();
        JSONObject dtvPakObj;


        for (int i = 0; i < jObj.length(); i++) {
            dtvPak = new digitalniTVPaket();
            dtvPakObj = (JSONObject) jObj.get(String.valueOf(i));
            double cena;
            double pdv;
            double cenapdv;

            cena = dtvPakObj.getDouble("cena");
            pdv = dtvPakObj.getDouble("pdv");
            cenapdv = cena + valueToPercent.getDiffValue(cena, pdv);

            dtvPak.setId(dtvPakObj.getInt("id"));
            dtvPak.setNaziv(dtvPakObj.getString("naziv"));
            dtvPak.setCena(cena);
            dtvPak.setPaketID(dtvPakObj.getInt("idPaket"));
            dtvPak.setOpis(dtvPakObj.getString("opis"));
            dtvPak.setPdv(pdv);
            dtvPak.setCenaPDV(cenapdv);

            dtvPakArr.add(dtvPak);
        }

        return dtvPakArr;


    }


    public void nov(ActionEvent actionEvent) {
        NewInterface novDtvInterface = new NewInterface("fxml/DigitalnaTVPaketEdit.fxml", "Nov Digitalni TV Paket", resources);
        DigitalniTVPaketEditController novDtvController = novDtvInterface.getLoader().getController();
        novDtvController.client = client;
        novDtvInterface.getStage().showAndWait();
        showData();

    }

    public void edit(ActionEvent actionEvent) {
        NewInterface editDtvInterface = new NewInterface("fxml/DigitalnaTVPaketEdit.fxml", "Izmena Digitalnog TV Paketa", resources);
        DigitalniTVPaketEditController editDtvController = editDtvInterface.getLoader().getController();
        editDtvController.client = client;
        editDtvController.edit = true;
        editDtvController.dtvPaket = (digitalniTVPaket) tblDTV.getSelectionModel().getSelectedItem();
        editDtvController.show_data();
        editDtvInterface.getStage().showAndWait();
        showData();
    }
}