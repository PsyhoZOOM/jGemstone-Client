package net.yuvideo.jgemstone.client.Controllers.Administration.WiFi;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class WifiFinder extends RecursiveTreeObject<WifiFinder> {

  int id;
  String mac;
  String hsIP;
  String hsName;

  String signal;
  String lastTimeSeen;
  String lastTimeUpdated;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getHsIP() {
    return hsIP;
  }

  public void setHsIP(String hsIP) {
    this.hsIP = hsIP;
  }

  public String getHsName() {
    return hsName;
  }

  public void setHsName(String hsName) {
    this.hsName = hsName;
  }

  public String getSignal() {
    return signal;
  }

  public void setSignal(String signal) {
    this.signal = signal;
  }

  public String getLastTimeSeen() {
    return lastTimeSeen;
  }

  public void setLastTimeSeen(String lastTimeSeen) {
    this.lastTimeSeen = lastTimeSeen;
  }

  public String getLastTimeUpdated() {
    return lastTimeUpdated;
  }

  public void setLastTimeUpdated(String lastTimeUpdated) {
    this.lastTimeUpdated = lastTimeUpdated;
  }
}
