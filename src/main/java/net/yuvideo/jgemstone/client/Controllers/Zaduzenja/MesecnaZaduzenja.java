package net.yuvideo.jgemstone.client.Controllers.Zaduzenja;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.security.PrivateKey;

public class MesecnaZaduzenja extends RecursiveTreeObject<MesecnaZaduzenja> {

  private int id;
  private String zaMesec;
  private double dug;
  private double cena;
  private int kolicina;
  private String datum;
  private double pdv;
  private double popust;
  private String naziv;
  private String opis;
  private String zaduzenOd;
  private int userID;
  private String paketType;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getCena() {
    return cena;
  }

  public void setCena(double cena) {
    this.cena = cena;
  }

  public int getKolicina() {
    return kolicina;
  }

  public void setKolicina(int kolicina) {
    this.kolicina = kolicina;
  }

  public String getDatum() {
    return datum;
  }

  public void setDatum(String datum) {
    this.datum = datum;
  }

  public double getPdv() {
    return pdv;
  }

  public void setPdv(double pdv) {
    this.pdv = pdv;
  }

  public double getPopust() {
    return popust;
  }

  public void setPopust(double popust) {
    this.popust = popust;
  }

  public String getNaziv() {
    return naziv;
  }

  public void setNaziv(String naziv) {
    this.naziv = naziv;
  }

  public String getOpis() {
    return opis;
  }

  public void setOpis(String opis) {
    this.opis = opis;
  }

  public String getZaduzenOd() {
    return zaduzenOd;
  }

  public void setZaduzenOd(String zaduzenOd) {
    this.zaduzenOd = zaduzenOd;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public String getPaketType() {
    return paketType;
  }

  public void setPaketType(String paketType) {
    this.paketType = paketType;
  }

  public double getDug() {
    return dug;
  }

  public void setDug(double dug) {
    this.dug = dug;
  }

  public String getZaMesec() {
    return zaMesec;
  }

  public void setZaMesec(String zaMesec) {
    this.zaMesec = zaMesec;
  }
}
