package net.yuvideo.jgemstone.client.classes;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class UsersOnline extends RecursiveTreeObject<UsersOnline> {

  String identification;
  String ime;
  String adresaUsluge;
  String mestoUsluge;
  String service;
  String mac;
  String uptime;
  String ip;
  String nasIP;
  String sessionID;
  String NASName;
  String userName;
  String calledID;
  String interfaceName;
  String linkUP;
  String rxByte;
  String txByte;
  String rxError;
  String txError;
  String upTime;
  int userID;





  int broj;
  int id;


  public String getUpTime() {
    return upTime;
  }

  public void setUpTime(String upTime) {
    this.upTime = upTime;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public String getInterfaceName() {
    return interfaceName;
  }

  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  }

  public String getLinkUP() {
    return linkUP;
  }

  public void setLinkUP(String linkUP) {
    this.linkUP = linkUP;
  }

  public String getRxByte() {
    return rxByte;
  }

  public void setRxByte(String rxByte) {
    this.rxByte = rxByte;
  }

  public String getTxByte() {
    return txByte;
  }

  public void setTxByte(String txByte) {
    this.txByte = txByte;
  }

  public String getRxError() {
    return rxError;
  }

  public void setRxError(String rxError) {
    this.rxError = rxError;
  }

  public String getTxError() {
    return txError;
  }

  public void setTxError(String txError) {
    this.txError = txError;
  }

  public String getCalledID() {
    return calledID;
  }

  public void setCalledID(String calledID) {
    this.calledID = calledID;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getNASName() {
    return NASName;
  }

  public void setNASName(String NASName) {
    this.NASName = NASName;
  }

  public String getSessionID() {
    return sessionID;
  }

  public void setSessionID(String sessionID) {
    this.sessionID = sessionID;
  }

  public String getNasIP() {
    return nasIP;
  }

  public void setNasIP(String nasIP) {
    this.nasIP = nasIP;
  }

  public int getBroj() {
    return broj;
  }

  public void setBroj(int broj) {
    this.broj = broj;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  boolean online;

  public String getIdentification() {
    return identification;
  }

  public void setIdentification(String identification) {
    this.identification = identification;
  }

  public String getIme() {
    return ime;
  }

  public void setIme(String ime) {
    this.ime = ime;
  }

  public String getAdresaUsluge() {
    return adresaUsluge;
  }

  public void setAdresaUsluge(String adresaUsluge) {
    this.adresaUsluge = adresaUsluge;
  }

  public String getMestoUsluge() {
    return mestoUsluge;
  }

  public void setMestoUsluge(String mestoUsluge) {
    this.mestoUsluge = mestoUsluge;
  }

  public boolean isOnline() {
    return online;
  }

  public void setOnline(boolean online) {
    this.online = online;
  }

  public String getService() {
    return service;
  }

  public void setService(String service) {
    this.service = service;
  }

  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getUptime() {
    return uptime;
  }

  public void setUptime(String uptime) {
    this.uptime = uptime;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }
}
