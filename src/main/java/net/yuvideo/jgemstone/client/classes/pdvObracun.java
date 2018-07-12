package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

public class pdvObracun implements Serializable {

  int id;
  int userID;
  String korisnik;
  double cena;
  int kolicina;
  double osnovica;
  double pdv;
  double ukupno;

  double ukupnoOsnovica;
  double ukupnoPDV;
  double ukupnoUkupno;

  double pdvIznos;


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

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public String getKorisnik() {
    return korisnik;
  }

  public void setKorisnik(String korisnik) {
    this.korisnik = korisnik;
  }

  public double getOsnovica() {
    return osnovica;
  }

  public void setOsnovica(double osnovica) {
    this.osnovica = osnovica;
  }

  public double getPdv() {
    return pdv;
  }

  public void setPdv(double pdv) {
    this.pdv = pdv;
  }

  public double getUkupno() {
    return ukupno;
  }

  public void setUkupno(double ukupno) {
    this.ukupno = ukupno;
  }

  public double getUkupnoOsnovica() {
    return ukupnoOsnovica;
  }

  public void setUkupnoOsnovica(double ukupnoOsnovica) {
    this.ukupnoOsnovica = ukupnoOsnovica;
  }

  public double getUkupnoPDV() {
    return ukupnoPDV;
  }

  public void setUkupnoPDV(double ukupnoPDV) {
    this.ukupnoPDV = ukupnoPDV;
  }

  public double getUkupnoUkupno() {
    return ukupnoUkupno;
  }

  public void setUkupnoUkupno(double ukupnoUkupno) {
    this.ukupnoUkupno = ukupnoUkupno;
  }

  public double getPdvIznos() {
    return pdvIznos;
  }

  public void setPdvIznos(double pdvIznos) {
    this.pdvIznos = pdvIznos;
  }
}
