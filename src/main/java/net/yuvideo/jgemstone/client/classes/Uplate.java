package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

/**
 * Created by zoom on 9/7/16.
 */
public class Uplate implements Serializable, Cloneable {

  int id;
  int id_ServiceUser;
  int id_service;
  String nazivPaket;
  String datumZaduzenja;
  int userID;
  double popust;
  String paketType;
  double cena;
  int kolicina;
  double uplaceno;
  double zaUplatu;
  String datumUplate;
  String operater;
  double dug;
  String zaMesec;
  double pdv;
  double osnovica;
  String mestoUplate;
  String zaduzenOd;
  String napomena;
  String identification;
  boolean haveFIX;
  String vrstaUplate;
  String fiksnTel;

  public String getFiksnTel() {
    return fiksnTel;
  }

  public void setFiksnTel(String fiksnTel) {
    this.fiksnTel = fiksnTel;
  }

  private boolean skipProduzenje;

  public void setPopust(double popust) {
    this.popust = popust;
  }

  public void setCena(double cena) {
    this.cena = cena;
  }

  public void setUplaceno(double uplaceno) {
    this.uplaceno = uplaceno;
  }

  public void setZaUplatu(double zaUplatu) {
    this.zaUplatu = zaUplatu;
  }

  public String getVrstaUplate() {
    return vrstaUplate;
  }

  public void setVrstaUplate(String vrstaUplate) {
    this.vrstaUplate = vrstaUplate;
  }

  public boolean isHaveFIX() {
    return haveFIX;
  }

  public void setHaveFIX(boolean haveFIX) {
    this.haveFIX = haveFIX;
  }

  public Uplate CopyUplate() throws CloneNotSupportedException {

    return (Uplate) this.clone();
  }

  public String getIdentification() {
    return identification;
  }

  public void setIdentification(String identification) {
    this.identification = identification;
  }

  public double getPdv() {
    return pdv;
  }

  public void setPdv(double pdv) {
    this.pdv = pdv;
  }


  public String getNapomena() {
    return napomena;
  }

  public void setNapomena(String napomena) {
    this.napomena = napomena;
  }

  public String getZaduzenOd() {
    return zaduzenOd;
  }

  public void setZaduzenOd(String zaduzenOd) {
    this.zaduzenOd = zaduzenOd;
  }

  public String getZaMesec() {
    return zaMesec;
  }

  public void setZaMesec(String zaMesec) {
    this.zaMesec = zaMesec;
  }

  public double getDug() {
    return dug;
  }

  public void setDug(double dug) {
    this.dug = dug;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getId_ServiceUser() {
    return id_ServiceUser;
  }

  public void setId_ServiceUser(int id_ServiceUser) {
    this.id_ServiceUser = id_ServiceUser;
  }

  public int getId_service() {
    return id_service;
  }

  public void setId_service(int id_service) {
    this.id_service = id_service;
  }

  public String getNazivPaket() {
    return nazivPaket;
  }

  public void setNazivPaket(String nazivPaket) {
    this.nazivPaket = nazivPaket;
  }

  public String getDatumZaduzenja() {
    return datumZaduzenja;
  }

  public void setDatumZaduzenja(String datumAZaduzenja) {
    this.datumZaduzenja = datumAZaduzenja;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public Double getPopust() {
    return popust;
  }

  public void setPopust(Double popust) {
    this.popust = popust;
  }

  public String getPaketType() {
    return paketType;
  }

  public void setPaketType(String paketType) {
    this.paketType = paketType;
  }

  public Double getCena() {
    return cena;
  }

  public void setCena(Double cena) {
    this.cena = cena;
  }

  public Double getUplaceno() {
    return uplaceno;
  }

  public void setUplaceno(Double uplaceno) {
    this.uplaceno = uplaceno;
  }

  public String getDatumUplate() {
    return datumUplate;
  }

  public void setDatumUplate(String datumUplate) {
    this.datumUplate = datumUplate;
  }

  public Double getZaUplatu() {
    return zaUplatu;
  }

  public void setZaUplatu(Double zaUplatu) {
    this.zaUplatu = zaUplatu;
  }

  public String getOperater() {
    return operater;
  }

  public void setOperater(String operater)

  {
    this.operater = operater;
  }

  public String getMestoUplate() {
    return mestoUplate;
  }

  public void setMestoUplate(String mestoUplate) {
    this.mestoUplate = mestoUplate;
  }

  public boolean isSkipProduzenje() {
    return skipProduzenje;
  }

  public void setSkipProduzenje(boolean skipProduzenje) {
    this.skipProduzenje = skipProduzenje;
  }

  public int getKolicina() {
    return kolicina;
  }

  public void setKolicina(int kolicina) {
    this.kolicina = kolicina;
  }

  public double getOsnovica() {
    return osnovica;
  }

  public void setOsnovica(double osnovica) {
    this.osnovica = osnovica;
  }
}


