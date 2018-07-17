package net.yuvideo.jgemstone.client.Controllers.Fiksna;

import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.CSVData;
import net.yuvideo.jgemstone.client.classes.Client;
import org.json.JSONObject;

public class FiksnaPozivi implements Initializable {

  public JFXDatePicker dtrZaMesec;
  public PieChart pieData;
  private URL location;
  private ResourceBundle resources;
  private Client client;
  private LocalDate dateNow = LocalDate.now();

  private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    pieData.setLegendSide(Side.LEFT);

    dtrZaMesec.setValue(dateNow.minusMonths(1));
    dtrZaMesec.setConverter(new StringConverter<LocalDate>() {
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

  public void setClient(Client client) {
    this.client = client;
  }

  public void showData(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "get_csv_data_account");
    object.put("zaMesec", dtrZaMesec.getValue().format(dateTimeFormatter));
    object.put("account", "");
    object = client.send_object(object);

    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERORR"));
      return;
    }

    ArrayList<CSVData> csvDataArrayList = new ArrayList<>();
    System.out.println(object.toString());
    for (int i = 0; i < object.length(); i++) {
      JSONObject data = object.getJSONObject(String.valueOf(i));
      CSVData csvData = new CSVData();
      //    csvData.setId(data.getInt("id"));
      //  csvData.setAccount(data.getString("account"));
      //csvData.setFrom(data.getString("from"));
      //csvData.setTo(data.getString("to"));
      csvData.setCountry(data.getString("country"));
      //csvData.setDescription(data.getString("description"));
      //csvData.setConnectTime(data.getString("connectTime"));
      //csvData.setChargedTimeMinSec(data.getString("chargedTimeMS"));
      csvData.setChargedTimeSec(data.getInt("chargedTimeS"));
      //csvData.setChargedAmountRSD(data.getDouble("chargedAmountRSD"));
      //csvData.setServiceName(data.getString("serviceName"));
      //csvData.setChargedQuantity(data.getInt("chargedQuantity"));
      //csvData.setServiceUnit(data.getString("serviceUnit"));
      //csvData.setCustomerID(data.getString("customerID"));
      //csvData.setFileName(data.getString("fileName"));
      csvDataArrayList.add(csvData);
    }

    setPieChard(csvDataArrayList);
  }

  private void setPieChard(ArrayList<CSVData> csvDataArrayList) {
    ObservableList<Data> data = FXCollections.observableArrayList();
    for (CSVData csvData : csvDataArrayList) {
      data.add(
          new PieChart.Data(csvData.getCountry() + " " + csvData.getChargedTimeSec() / 60 + "min.",
              csvData.getChargedTimeSec() / 60));
    }
    pieData.setData(data);
  }
}

