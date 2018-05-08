package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

/**
 * Created by zoom on 11/23/16.
 */
public class Fakture implements Serializable {

  private int id;
  private int br;
  private String brRacuna;
  private String nazivFirme;
  private String naziv;
  private Double jedCena;
  private double stopaPDV;
  private int brFakture;
  private String datum;
  private String godina;
  private String jedMere;
  private int userId;
  private int kolicina;
  private String dateCreated;
  private double vrednostBezPDV;
  private double osnovicaZaPDV;
  private double iznosPDV;
  private double vrednostSaPDV;
  private String operater;
  private String mesec;

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getMesec() {
    return mesec;
  }

  public void setMesec(String mesec) {
    this.mesec = mesec;
  }


  public String getOperater() {
    return operater;
  }

  public void setOperater(String operater) {
    this.operater = operater;
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

  public int getBr() {
    return br;
  }

  public void setBr(int br) {
    this.br = br;
  }

  public String getBrRacuna() {
    return brRacuna;
  }

  public void setBrRacuna(String brRacuna) {
    this.brRacuna = brRacuna;
  }

  public String getNazivFirme() {
    return nazivFirme;
  }

  public void setNazivFirme(String nazivFirme) {
    this.nazivFirme = nazivFirme;
  }

  public String getNaziv() {
    return naziv;
  }

  public void setNaziv(String naziv) {
    this.naziv = naziv;
  }

  public Double getJedCena() {
    return jedCena;
  }

  public void setJedCena(Double jedCena) {
    this.jedCena = jedCena;
  }

  public double getStopaPDV() {
    return stopaPDV;
  }

  public void setStopaPDV(double stopaPDV) {
    this.stopaPDV = stopaPDV;
  }

  public int getBrFakture() {
    return brFakture;
  }

  public void setBrFakture(int brFakture) {
    this.brFakture = brFakture;
  }

  public String getDatum() {
    return datum;
  }

  public void setDatum(String datum) {
    this.datum = datum;
  }

  public String getGodina() {
    return godina;
  }

  public void setGodina(String godina) {
    this.godina = godina;
  }

  public String getJedMere() {
    return jedMere;
  }

  public void setJedMere(String jedMere) {
    this.jedMere = jedMere;
  }


  public String getDateCreated() {
    return dateCreated;
  }

  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }

  public double getVrednostBezPDV() {
    return vrednostBezPDV;
  }

  public void setVrednostBezPDV(double vrednostBezPDV) {
    this.vrednostBezPDV = vrednostBezPDV;
  }

  public double getOsnovicaZaPDV() {
    return osnovicaZaPDV;
  }

  public void setOsnovicaZaPDV(double osnovicaZaPDV) {
    this.osnovicaZaPDV = osnovicaZaPDV;
  }

  public double getIznosPDV() {
    return iznosPDV;
  }

  public void setIznosPDV(double iznosPDV) {
    this.iznosPDV = iznosPDV;
  }

  public double getVrednostSaPDV() {
    return vrednostSaPDV;
  }

  public void setVrednostSaPDV(double vrednostSaPDV) {
    this.vrednostSaPDV = vrednostSaPDV;
  }


}
