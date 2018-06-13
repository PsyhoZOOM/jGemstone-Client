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


  int broj;
  int id;


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
