package net.yuvideo.jgemstone.client.Controllers.Administration.TrafficReport;

import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.BytesTo_KB_MB_GB_TB;
import net.yuvideo.jgemstone.client.classes.Client;

public class MtBwMonitor implements Initializable {

  public JFXToggleButton tStartStop;
  public AreaChart<String, Number> chartBwMonitor;
  boolean monitoringActive = false;
  private URL location;
  private ResourceBundle resources;
  private Client client;
  private String nasIP;
  private String interfaceName;
  private String remoteInterface;
  private String remoteIP;
  DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
  private BwMonitorData monitorData;
  private XYChart.Series xyCharDownload = new XYChart.Series();
  private XYChart.Series xyCharUpload = new XYChart.Series();
  private Thread thread;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
    CategoryAxis numberAxisVreme = new CategoryAxis();
    numberAxisVreme.setLabel("Vreme");
    numberAxisVreme.setAutoRanging(true);
    NumberAxis numberAxisBrzina = new NumberAxis();
    numberAxisBrzina.setLabel("Brzina");

    chartBwMonitor.getXAxis().setLabel("VREME");
    chartBwMonitor.getYAxis().setLabel("BRZINA");
    ((ValueAxis<Number>) chartBwMonitor.getYAxis()).setTickLabelFormatter(
        new StringConverter<Number>() {
          @Override
          public String toString(Number object) {
            return BytesTo_KB_MB_GB_TB.getFormatedString(object.longValue());
          }

          @Override
          public Number fromString(String string) {
            return null;
          }
        });

    xyCharDownload.setName("Download");
    xyCharUpload.setName("Upload");
    chartBwMonitor.getData().addAll(xyCharUpload, xyCharDownload);

    chartBwMonitor.setCreateSymbols(true);

    monitorData = new BwMonitorData();

    tStartStop.armedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
          Boolean newValue) {
        monitoringActive = tStartStop.isSelected();
        if (monitoringActive) {
          startMonitoring();
        } else {
          stopMonitoring();
        }


      }
    });


  }

  public void stopMonitoring() {
    monitoringActive = false;
  }


  public void startStopMonitoring(ActionEvent actionEvent) {
    thread = new Thread(() -> {
      while (monitoringActive) {
        System.out.println(tStartStop.isArmed());
        monitorData.getData(nasIP, interfaceName);
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            xyCharUpload.getData()
                .add(new Data(monitorData.getTime().format(dtf), monitorData.getUpload()));
            xyCharDownload.getData()
                .add(new Data(monitorData.getTime().format(dtf), monitorData.getDownload()));


          }
        });
        try {
          Thread.sleep(2000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

    });
    thread.setDaemon(true);
  }

  public void initData(String nasIP, String interfaceName) {

    this.nasIP = nasIP;
    this.interfaceName = interfaceName;
    chartBwMonitor.setTitle(String.format("MONITORING %s", interfaceName));
    monitorData.setClient(client);
    startMonitoring();

  }

  private void startMonitoring() {
    if (monitoringActive == false) {
      return;
    }
    thread.start();




  }


  public void setClient(Client client) {
    this.client = client;
  }

  public void setData(double download, double upload, String time) {



  }

}
