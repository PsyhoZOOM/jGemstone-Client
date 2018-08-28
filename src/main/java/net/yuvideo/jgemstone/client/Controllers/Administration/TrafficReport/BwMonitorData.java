package net.yuvideo.jgemstone.client.Controllers.Administration.TrafficReport;

import java.awt.image.AreaAveragingScaleFilter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import net.yuvideo.jgemstone.client.classes.Client;
import org.json.JSONObject;

public class BwMonitorData {


  private Client client;
  private double upload;
  private double download;
  private LocalTime time;
  private ArrayList<BwMonitorData> arrayListData;

  public void setClient(Client client) {
    this.client = client;
  }

  public void getData(String nasIP, String interfaceName) {
    this.arrayListData = new ArrayList<>();
    JSONObject object = new JSONObject();
    object.put("action", "bwMonitor");
    object.put("nasIP", nasIP);
    object.put("interfaceName", interfaceName);

    object = client.send_object(object);
    upload = Double.valueOf(object.getString("tx-bits-per-second"));
    download = Double.valueOf(object.getString("rx-bits-per-second"));
    time = LocalTime.now();
  }

  public double getUpload() {
    return upload;
  }

  public void setUpload(double upload) {
    this.upload = upload;
  }

  public double getDownload() {
    return download;
  }

  public void setDownload(double download) {
    this.download = download;
  }

  public LocalTime getTime() {
    return time;
  }

  public void setTime(LocalTime time) {
    this.time = time;
  }
}
