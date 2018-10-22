package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.CSVData;
import net.yuvideo.jgemstone.client.classes.Client;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by zoom on 7/26/17.
 */
public class CSVPreview implements Initializable {

  public TableView<CSVData> tblCSV;
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
  public JFXDatePicker dtpOd;
  public JFXDatePicker dtpDo;
  private Client client;
  private URL location;
  private ResourceBundle resources;
  private JSONObject jObj;

  private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    //init table
    tblCSV.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    cBrojTel.setCellValueFactory(new PropertyValueFactory<CSVData, String>("account"));
    cPozivOd.setCellValueFactory(new PropertyValueFactory<CSVData, String>("from"));
    cPozivKa.setCellValueFactory(new PropertyValueFactory<CSVData, String>("to"));
    cZemlja.setCellValueFactory(new PropertyValueFactory<CSVData, String>("country"));
    cOpis.setCellValueFactory(new PropertyValueFactory<CSVData, String>("description"));
    cVreme.setCellValueFactory(new PropertyValueFactory<CSVData, String>("connectTime"));
    cNaplacenoMinSec
        .setCellValueFactory(new PropertyValueFactory<CSVData, String>("chargedTimeMinSec"));
    cNaplacenoSek.setCellValueFactory(new PropertyValueFactory<CSVData, Integer>("chargedTimeSec"));
    cNaplacenoRSD
        .setCellValueFactory(new PropertyValueFactory<CSVData, Double>("chargedAmountRSD"));
    cImeServisa.setCellValueFactory(new PropertyValueFactory<CSVData, String>("serviceName"));
    cNaplacenaKolicina
        .setCellValueFactory(new PropertyValueFactory<CSVData, Integer>("chargedQuantity"));
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
          setText(String.valueOf(item));
        }
      }
    });

    dtpOd.setValue(LocalDate.now().minusMonths(3).withDayOfMonth(1));
    dtpDo.setValue(LocalDate.now());

    dtpOd.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        return object.format(dateTimeFormatter);
      }

      @Override
      public LocalDate fromString(String string) {
        LocalDate date = LocalDate.parse(string, dateTimeFormatter);
        return date;
      }
    });

    dtpDo.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        return object.format(dateTimeFormatter);
      }

      @Override
      public LocalDate fromString(String string) {
        LocalDate date = LocalDate.parse(string, dateTimeFormatter);
        return date;
      }
    });
  }

  public void prikazi(ActionEvent actionEvent) {
    jObj = new JSONObject();
    jObj.put("action", "get_CSV_Data");
    jObj.put("od", dtpOd.getValue().format(dateTimeFormatter));
    jObj.put("do", dtpDo.getValue().format(dateTimeFormatter));

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


  public void setClient(Client client) {
    this.client = client;
  }

  public void deleteSelected(ActionEvent actionEvent) {
    ObservableList<CSVData> selectedItems = tblCSV.getSelectionModel().getSelectedItems();
    if (selectedItems.size() <= 0) {
      return;
    }

    JSONObject object = new JSONObject();
    JSONArray array = new JSONArray();

    for (CSVData data : selectedItems) {
      array.put(data.getId());

    }
    object.put("action", "deleteCSV_ID");
    object.put("intArrays", array);
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    prikazi(null);
  }
}
