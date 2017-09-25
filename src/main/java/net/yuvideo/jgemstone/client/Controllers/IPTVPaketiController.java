package net.yuvideo.jgemstone.client.Controllers;

import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.IPTVPaketi;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 8/15/17.
 */
public class IPTVPaketiController implements Initializable {
    public Button bIzbrisi;
    public Button bIzmeni;
    public Button bNov;
    public TableView<IPTVPaketi> tblPaketiIPTV;
    public TableColumn<IPTVPaketi, String> cNaziv;
    public TableColumn<IPTVPaketi, String> cOpis;
    public TableColumn<IPTVPaketi, Integer> cID;
    public TableColumn<IPTVPaketi, Integer> cExternalID;
    public TableColumn<IPTVPaketi, Double> cCena;
    public TableColumn<IPTVPaketi, Integer> cIPTVID;
    public Client client;
    private URL location;
    private ResourceBundle resources;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        cNaziv.setCellValueFactory(new PropertyValueFactory<IPTVPaketi, String>("name"));
        cOpis.setCellValueFactory(new PropertyValueFactory<IPTVPaketi, String>("description"));
        cID.setCellValueFactory(new PropertyValueFactory<IPTVPaketi, Integer>("id"));
        cExternalID.setCellValueFactory(new PropertyValueFactory<IPTVPaketi, Integer>("external_id"));
        cCena.setCellValueFactory(new PropertyValueFactory<IPTVPaketi, Double>("cena"));
        cIPTVID.setCellValueFactory(new PropertyValueFactory<IPTVPaketi, Integer>("iptv_id"));

        tblPaketiIPTV.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<IPTVPaketi>() {
            @Override
            public void changed(ObservableValue<? extends IPTVPaketi> observable, IPTVPaketi oldValue, IPTVPaketi newValue) {
                if (tblPaketiIPTV.getSelectionModel().getSelectedIndex() == -1)
                    return;
                if (newValue.getId() == 0) {
                    bIzmeni.setDisable(true);
                }
            }
        });

    }

    public void izmeniIPTVPaket(ActionEvent actionEvent) {
        String resourceFXML = "src/main/resources/IPTVPaketEdit.fxml";
        NewInterface iptvPaketEditInterface = new NewInterface(resourceFXML, "Izmena IPTV Paketa", resources);
        IPTVPaketiEditController iptvPaketEditController = iptvPaketEditInterface.getLoader().getController();

        iptvPaketEditController.client = this.client;
        iptvPaketEditController.edit = true;
        iptvPaketEditController.paket = tblPaketiIPTV.getSelectionModel().getSelectedItem();
        iptvPaketEditController.paketEditID = tblPaketiIPTV.getSelectionModel().getSelectedItem().getId();
        iptvPaketEditController.setItemsEdit();
        iptvPaketEditInterface.getStage().showAndWait();
        showPaketiTable();


    }

    public void novIPTVPaket(ActionEvent actionEvent) {
        String resourceFXML = "src/main/resources/IPTVPaketEdit.fxml";
        NewInterface iptvPaketNewInterface = new NewInterface(resourceFXML, "Nov IPTV Paket", resources);
        IPTVPaketiEditController iptvPaketiNewControoler = iptvPaketNewInterface.getLoader().getController();
        iptvPaketiNewControoler.client = this.client;
        iptvPaketiNewControoler.edit = false;
        iptvPaketiNewControoler.setData();
        iptvPaketNewInterface.getStage().showAndWait();
        showPaketiTable();

    }

    public void showPaketiTable() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "getIPTVDataLocal");
        jsonObject = client.send_object(jsonObject);


        ArrayList<IPTVPaketi> iptvPaketiArrayList = new ArrayList<>();

        System.out.println(jsonObject);

        for (int i = 0; i < jsonObject.length(); i++) {
            JSONObject pakobj = (JSONObject) jsonObject.get(String.valueOf(i));
            IPTVPaketi paketi = new IPTVPaketi();
            paketi.setName(pakobj.getString("name"));
            paketi.setId(pakobj.getInt("id"));
            paketi.setExternal_id(pakobj.getString("external_id"));
            paketi.setCena(pakobj.getDouble("cena"));
            paketi.setDescription(pakobj.getString("opis"));
            paketi.setPrekoracenje(pakobj.getInt("prekoracenje"));
            paketi.setIptv_id(pakobj.getInt("IPTV_id"));
            iptvPaketiArrayList.add(paketi);

        }
        ObservableList paketi = FXCollections.observableArrayList(iptvPaketiArrayList);
        tblPaketiIPTV.setItems(paketi);
    }

    private IPTVPaketi getPaketLocalData(int external_id) {
        IPTVPaketi paket;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "getIPTVData");
        jsonObject.put("external_id", external_id);
        jsonObject = client.send_object(jsonObject);
        paket = new IPTVPaketi();

        //ako ne postoji u bazi preskociti;
        if (!jsonObject.has("id"))
            return paket;
        paket.setId(jsonObject.getInt("id"));
        paket.setName(jsonObject.getString("name"));
        paket.setIptv_id(jsonObject.getInt("IPTV_id"));
        paket.setExternal_id(jsonObject.getString("external_id"));
        paket.setDescription(jsonObject.getString("opis"));
        paket.setCena(jsonObject.getDouble("cena"));

        return paket;
    }


}
