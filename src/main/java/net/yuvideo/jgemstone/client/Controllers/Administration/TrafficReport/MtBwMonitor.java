package net.yuvideo.jgemstone.client.Controllers.Administration.TrafficReport;

import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import net.yuvideo.jgemstone.client.classes.Client;
import org.json.JSONObject;

public class MtBwMonitor implements Initializable {

  public JFXToggleButton tStartStop;
  public AreaChart chartBwMonitor;
  boolean monitoringActive = false;
  private URL location;
  private ResourceBundle resources;
  private Client client;
  private String nasIP;
  private String interfaceName;
  private String remoteInterface;
  private String remoteIP;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
  }


  public void startStopMonitoring(ActionEvent actionEvent) {
    if (tStartStop.isArmed()) {
      startMonitoring();
    } else {
      monitoringActive = false;
    }
  }

  public void initData(String nasIP, String interfaceName) {

    this.nasIP = nasIP;
    this.interfaceName = interfaceName;
  }

  private void startMonitoring() {
    monitoringActive = true;
    Task task = new Task() {
      @Override
      protected Object call() throws Exception {
        BwMonitorData monitorData = new BwMonitorData();
        monitorData.setClient(new Client(client.getLocal_settings()));
        ArrayList<BwMonitorData> data = monitorData.getData(nasIP, interfaceName);
        chartBwMonitor.getData().add(data);

        return null;
      }
    };

    task.run();


  }


  public void setClient(Client client) {
    this.client = client;
  }

  public void setData(JSONObject data) {
//    chartBwMonitor

  }

}
