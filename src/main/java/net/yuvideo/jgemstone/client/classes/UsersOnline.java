package net.yuvideo.jgemstone.client.classes;

public class UsersOnline {

  String identification;
  String ime;
  String adresaUsluge;
  String mestoUsluge;
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
}
