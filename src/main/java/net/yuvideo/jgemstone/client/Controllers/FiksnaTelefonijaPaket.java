package net.yuvideo.jgemstone.client.Controllers;

import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NotifyUser;
import net.yuvideo.jgemstone.client.classes.Paketi;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 5/24/17.
 */
public class FiksnaTelefonijaPaket implements Initializable {
    public TableView<Paketi> tblPaketi;
    public TableColumn cNaziv;
    public TableColumn cPretplata;
    public TableColumn cPDV;
    public TextField tNaziv;
    public TextField tPretplata;
    public TextField tPDV;
    public Button bDelete;
    public Button bNov;
    public Button bSnimi;
    public Button bOsvezi;
    public TextField tBesplatniMinutiFiksna;
    public Client client;
    private ResourceBundle resources;
    private URL location;
    private JSONObject jObj;

    private DecimalFormat df = new DecimalFormat("###,###,##0.##");


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        this.location = location;

        cNaziv.setCellValueFactory(new PropertyValueFactory<Paketi, String>("naziv"));
        cPDV.setCellValueFactory(new PropertyValueFactory<Paketi, Double>("PDV"));
        cPretplata.setCellValueFactory(new PropertyValueFactory<Paketi, Double>("pretplata"));

        cPDV.setCellFactory(new Callback<TableColumn<Paketi, Double>, TableCell<Paketi, Double>>() {
            @Override
            public TableCell<Paketi, Double> call(TableColumn<Paketi, Double> param) {
                return new TableCell<Paketi, Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(df.format(item));
                        }

                    }
                };
            }
        });

        cPretplata.setCellFactory(new Callback<TableColumn<Paketi, Double>, TableCell<Paketi, Double>>() {
            @Override
            public TableCell<Paketi, Double> call(TableColumn<Paketi, Double> param) {
                return new TableCell<Paketi, Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(df.format(item));
                        }
                    }
                };
            }
        });



        tblPaketi.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Paketi>() {
            @Override
            public void changed(ObservableValue<? extends Paketi> observable, Paketi oldValue, Paketi newValue) {
                if (tblPaketi.getSelectionModel().getSelectedIndex() == -1)
                    return;

                Paketi paketData = tblPaketi.getSelectionModel().getSelectedItem();
                tNaziv.setText(paketData.getNaziv());
                tPDV.setText(String.valueOf(paketData.getPDV()));
                tPretplata.setText(String.valueOf(paketData.getPretplata()));
                tBesplatniMinutiFiksna.setText(String.valueOf(paketData.getBesplatiniMinutiFiksna()));
            }
        });
    }

    public void deleteData(ActionEvent actionEvent) {
        if (tblPaketi.getSelectionModel().getSelectedIndex() == -1)
            return;


        jObj = new JSONObject();
        jObj.put("action", "del_fixTel_paket");
        jObj.put("id", tblPaketi.getSelectionModel().getSelectedItem().getId());
        jObj = client.send_object(jObj);
        if (jObj.has("Error")) {
            NotifyUser.NotifyUser("Greska", jObj.getString("Error"), 3);
        } else {
            NotifyUser.NotifyUser("Informacija", String.format("Paket %s je obrisan",
                    tblPaketi.getSelectionModel().getSelectedItem().getNaziv()), 1);
        }
        setTable();
    }

    public void snimiNov(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "add_fixTel_paket");
        jObj.put("naziv", tNaziv.getText());
        jObj.put("pretplata", Double.valueOf(tPretplata.getText()));
        jObj.put("PDV", Double.valueOf(tPDV.getText()));
        jObj.put("besplatniMinutiFiksna", Integer.valueOf(tBesplatniMinutiFiksna.getText()));
        client.send_object(jObj);
        if (jObj.has("Error")) {
            NotifyUser.NotifyUser("Greska", jObj.getString("Error"), 3);
        } else {
            NotifyUser.NotifyUser("Paket snimljen", String.format("Paket %s je snimljen", tNaziv.getText()), 1);
        }

        setTable();
    }

    public void snimi(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "edit_fixTel_paket");

        jObj.put("naziv", tNaziv.getText());
        jObj.put("PDV", Double.valueOf(tPDV.getText()));
        jObj.put("pretplata", Double.valueOf(tPretplata.getText()));
        jObj.put("besplatniMinutiFiksna", Integer.valueOf(tBesplatniMinutiFiksna.getText()));
        jObj.put("id", tblPaketi.getSelectionModel().getSelectedItem().getId());

        jObj = client.send_object(jObj);

        if (jObj.has("Error")) {
            NotifyUser.NotifyUser("Greska", jObj.getString("Error"), 3);
        } else {
            NotifyUser.NotifyUser("Informacija",
                    String.format("Paket %s je izmenjen", tNaziv.getText()), 1);
        }

        setTable();


    }

    public void osveziTable(ActionEvent actionEvent) {
        setTable();
    }


    public void setTable() {
        jObj = new JSONObject();
        jObj.put("action", "show_fixTel_paketi");
        jObj = client.send_object(jObj);

        ArrayList<Paketi> paketis = new ArrayList<>();
        Paketi paketi;
        JSONObject paketiObj;
        for (int i = 0; i < jObj.length(); i++) {
            paketi = new Paketi();
            paketiObj = (JSONObject) jObj.get(String.valueOf(i));
            paketi.setId(paketiObj.getInt("id"));
            paketi.setNaziv(paketiObj.getString("naziv"));
            paketi.setPretplata(paketiObj.getDouble("pretplata"));
            paketi.setBesplatiniMinutiFiksna(paketiObj.getInt("besplatniMinutiFiksna"));
            paketi.setPDV(paketiObj.getDouble("PDV"));
            paketis.add(paketi);
        }

        ObservableList data = FXCollections.observableArrayList(paketis);
        tblPaketi.setItems(data);


    }
}
