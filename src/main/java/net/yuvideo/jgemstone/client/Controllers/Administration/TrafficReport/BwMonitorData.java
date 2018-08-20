package net.yuvideo.jgemstone.client.Controllers.Administration.TrafficReport;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import net.yuvideo.jgemstone.client.classes.Client;
import org.json.JSONObject;

public class BwMonitorData {


  private Client client;
  private double upload;
  private double download;
  private ArrayList<BwMonitorData> arrayListData;

  public void setClient(Client client) {
    this.client = client;
  }

  public ArrayList<BwMonitorData> getData(String nasIP, String interfaceName) {
    this.arrayListData = new ArrayList<>();
    JSONObject object = new JSONObject();
    object.put("action", "bwMonitor");
    object.put("nasIP", nasIP);
    object.put("interfaceName", interfaceName);

    object = client.send_object(object);
    //   upload = Double.valueOf()

    return arrayListData;
  }
}
