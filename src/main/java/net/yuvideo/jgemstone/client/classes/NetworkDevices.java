package net.yuvideo.jgemstone.client.classes;

import java.util.ArrayList;
import net.yuvideo.jgemstone.client.Controllers.Administration.Devices;
import org.json.JSONObject;

public class NetworkDevices {

  int id;
  String name;
  String ip;
  String hostName;
  String type;
  String userName;
  String pass;
  String url;
  String opis;
  boolean nas;
  String accessType;
  ArrayList<NetworkDevices> networkDevices = new ArrayList<>();
  private Client client;
  private JSONObject networkDevicesJSON;

  public NetworkDevices(Client client) {
    this.client = client;
  }

  public NetworkDevices() {
  }

  public void initDevices() {
    JSONObject obj = new JSONObject();
    obj.put("action", "getAllNetworkDevices");
    obj = client.send_object(obj);
    if (obj.has("ERROR")) {
      AlertUser.error("GRESKA", obj.getString("ERROR"));
      return;
    }

    setNetworkDevicesJSON(obj);

    for (int i = 0; i < obj.length(); i++) {
      JSONObject device = obj.getJSONObject(String.valueOf(i));
      NetworkDevices networkDevice = new NetworkDevices();
      networkDevice.setId(device.getInt("id"));
      networkDevice.setName(device.getString("name"));
      networkDevice.setIp(device.getString("ip"));
      networkDevice.setHostName(device.getString("hostName"));
      networkDevice.setType(device.getString("type"));
      networkDevice.setUserName(device.getString("userName"));
      networkDevice.setPass(device.getString("pass"));
      networkDevice.setUrl(device.getString("url"));
      networkDevice.setOpis(device.getString("opis"));
      networkDevice.setNas(device.getBoolean("nas"));
      networkDevice.setAccessType(device.getString("accessType"));
      networkDevices.add(networkDevice);
    }
  }

  public ArrayList<NetworkDevices> getNetworkDevices() {
    return networkDevices;
  }

  public void setNetworkDevices(
      ArrayList<NetworkDevices> networkDevices) {
    this.networkDevices = networkDevices;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPass() {
    return pass;
  }

  public void setPass(String pass) {
    this.pass = pass;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getOpis() {
    return opis;
  }

  public void setOpis(String opis) {
    this.opis = opis;
  }

  public JSONObject getNetworkDevicesJSON() {
    return networkDevicesJSON;
  }

  public void setNetworkDevicesJSON(JSONObject networkDevicesJSON) {
    this.networkDevicesJSON = networkDevicesJSON;
  }

  public boolean isNas() {
    return nas;
  }

  public void setNas(boolean nas) {
    this.nas = nas;
  }

  public String getAccessType() {
    return accessType;
  }

  public void setAccessType(String accessType) {
    this.accessType = accessType;
  }
}
