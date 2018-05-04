package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 5/29/17.
 */
public class FiksnaZoneCene implements Serializable {

  int id;
  String vrstaUsluge;
  double providerCena;
  double providerPDV;
  double providerUkupno;
  double cena;
  double PDV;
  double cenaPDV;
  double competitionCena;
  double razlika;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getVrstaUsluge() {
    return vrstaUsluge;
  }

  public void setVrstaUsluge(String vrstaUsluge) {
    this.vrstaUsluge = vrstaUsluge;
  }

  public double getProviderCena() {
    return providerCena;
  }

  public void setProviderCena(double providerCena) {
    this.providerCena = providerCena;
  }

  public double getProviderPDV() {
    return providerPDV;
  }

  public void setProviderPDV(double providerPDV) {
    this.providerPDV = providerPDV;
  }

  public double getProviderUkupno() {
    return providerUkupno;
  }

  public void setProviderUkupno(double providerUkupno) {
    this.providerUkupno = providerUkupno;
  }

  public double getCena() {
    return cena;
  }

  public void setCena(double cena) {
    this.cena = cena;
  }

  public double getPDV() {
    return PDV;
  }

  public void setPDV(double PDV) {
    this.PDV = PDV;
  }

  public double getCompetitionCena() {
    return competitionCena;
  }

  public void setCompetitionCena(double competitionCena) {
    this.competitionCena = competitionCena;
  }

  public double getRazlika() {
    return razlika;
  }

  public void setRazlika(double razlika) {
    this.razlika = razlika;
  }

  public double getCenaPDV() {
    return cenaPDV;
  }

  public void setCenaPDV(double cenaPDV) {
    this.cenaPDV = cenaPDV;
  }
}
