package net.yuvideo.jgemstone.client.Controllers.Administration.TrafficReport;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class TrafficReport extends RecursiveTreeObject<TrafficReport> {

  int id;
  String username;
  String nasIP;
  String startTime;
  String stopTime;
  String onlineTime;
  long inputOctets;
  long outputOctets;
  String service;
  String callingStationID;
  String terminateCause;
  String ipAddress;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getNasIP() {
    return nasIP;
  }

  public void setNasIP(String nasIP) {
    this.nasIP = nasIP;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getStopTime() {
    return stopTime;
  }

  public void setStopTime(String stopTime) {
    this.stopTime = stopTime;
  }

  public String getOnlineTime() {
    return onlineTime;
  }

  public void setOnlineTime(String onlineTime) {
    this.onlineTime = onlineTime;
  }

  public long getInputOctets() {
    return inputOctets;
  }

  public void setInputOctets(long inputOctets) {
    this.inputOctets = inputOctets;
  }

  public long getOutputOctets() {
    return outputOctets;
  }

  public void setOutputOctets(long outputOctets) {
    this.outputOctets = outputOctets;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getCallingStationID() {
    return callingStationID;
  }

  public void setCallingStationID(String callingStationID) {
    this.callingStationID = callingStationID;
  }

  public String getTerminateCause() {
    return terminateCause;
  }

  public void setTerminateCause(String terminateCause) {
    this.terminateCause = terminateCause;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }
}
