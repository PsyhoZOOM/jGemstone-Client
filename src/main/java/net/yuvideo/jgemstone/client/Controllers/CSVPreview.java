package net.yuvideo.jgemstone.client.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.yuvideo.jgemstone.client.classes.CSVData;
import net.yuvideo.jgemstone.client.classes.Client;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zoom on 7/26/17.
 */
public class CSVPreview implements Initializable {
    public TableView tblCSV;
    public TableColumn cBrojTel;
    public TableColumn cPozivOd;
    public TableColumn cPozivKa;
    public TableColumn cZemlja;
    public TableColumn cOpis;
    public TableColumn cVreme;
    public TableColumn cNaplacenoMinSec;
    public TableColumn cNaplacenoSek;
    public TableColumn cNaplacenoRSD;
    public TableColumn cImeServisa;
    public TableColumn cNaplacenaKolicina;
    public TableColumn cServiceUnit;
    public TableColumn cCustomerID;
    public TableColumn cFileName;
    public Client client;
    private URL location;
    private ResourceBundle resources;
    private JSONObject jObj;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        //init table
        cBrojTel.setCellValueFactory(new PropertyValueFactory<CSVData, String>("account"));
        cPozivOd.setCellValueFactory(new PropertyValueFactory<CSVData, String>("from"));
        cPozivKa.setCellValueFactory(new PropertyValueFactory<CSVData, String>("to"));
        cZemlja.setCellValueFactory(new PropertyValueFactory<CSVData, String>("country"));
        cOpis.setCellValueFactory(new PropertyValueFactory<CSVData, String>("description"));
        cVreme.setCellValueFactory(new PropertyValueFactory<CSVData, String>("connectTime"));
        cNaplacenoMinSec.setCellValueFactory(new PropertyValueFactory<CSVData, String>("chargedTimeMinSec"));
        cNaplacenoSek.setCellValueFactory(new PropertyValueFactory<CSVData, Integer>("chargedTimeSec"));
        cNaplacenoRSD.setCellValueFactory(new PropertyValueFactory<CSVData, Double>("chargedAmountRSD"));
        cImeServisa.setCellValueFactory(new PropertyValueFactory<CSVData, String>("serviceName"));
        cNaplacenaKolicina.setCellValueFactory(new PropertyValueFactory<CSVData, Integer>("chargedQuantity"));
        cServiceUnit.setCellValueFactory(new PropertyValueFactory<CSVData, String>("serviceUnit"));
        cCustomerID.setCellValueFactory(new PropertyValueFactory<CSVData, String>("customerID"));
        cFileName.setCellValueFactory(new PropertyValueFactory<CSVData, String>("fileName"));

        cNaplacenoRSD.setCellFactory(tc -> new TableCell<CSVData, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(df.format(item));
                }
            }
        });
    }

    public void setData() {
        jObj = new JSONObject();
        jObj.put("action", "get_CSV_Data");

        jObj = client.send_object(jObj);

        ArrayList<CSVData> csvDataArrayList = new ArrayList<>();
        CSVData csvData;

        //for(int i =0; i <= jObj.length();i++){
        for (String key : jObj.keySet()) {
            JSONObject csvDataJSON = (JSONObject) jObj.get(key);
            csvData = new CSVData();
            csvData.setId(csvDataJSON.getInt("id"));
            csvData.setAccount(csvDataJSON.getString("account"));
            csvData.setFrom(csvDataJSON.getString("from"));
            csvData.setTo(csvDataJSON.getString("to"));
            csvData.setCountry(csvDataJSON.getString("country"));
            csvData.setDescription(csvDataJSON.getString("description"));
            csvData.setConnectTime(csvDataJSON.getString("connectTime"));
            csvData.setChargedTimeMinSec(csvDataJSON.getString("chargedTimeMS"));
            csvData.setChargedTimeSec(csvDataJSON.getInt("chargedTimeS"));
            csvData.setChargedAmountRSD(csvDataJSON.getDouble("chargedAmountRSD"));
            csvData.setServiceName(csvDataJSON.getString("serviceName"));
            csvData.setChargedQuantity(csvDataJSON.getInt("chargedQuantity"));
            csvData.setServiceUnit(csvDataJSON.getString("serviceUnit"));
            csvData.setCustomerID(csvDataJSON.getString("customerID"));
            csvData.setFileName(csvDataJSON.getString("fileName"));
            csvDataArrayList.add(csvData);

        }

        ObservableList data = FXCollections.observableArrayList(csvDataArrayList);
        tblCSV.setItems(data);


    }

    public void refreshTable(ActionEvent actionEvent) {
    }
}
