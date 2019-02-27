package net.yuvideo.jgemstone.client.classes.Izvestaji;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Izvestaj extends RecursiveTreeObject<Izvestaj> {

  int id;
  String datum;
  int userID;
  double uplaceno;
  String jbroj;
  String opis;
  String operater;
  String ime;

  public String getIme() {
    return ime;
  }

  public void setIme(String ime) {
    this.ime = ime;
  }

  public String getDatum() {
    return datum;
  }

  public void setDatum(String datum) {
    this.datum = datum;
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

  public double getUplaceno() {
    return uplaceno;
  }

  public void setUplaceno(double uplaceno) {
    this.uplaceno = uplaceno;
  }

  public String getJbroj() {
    return jbroj;
  }

  public void setJbroj(String jbroj) {
    this.jbroj = jbroj;
  }

  public String getOpis() {
    return opis;
  }

  public void setOpis(String opis) {
    this.opis = opis;
  }

  public String getOperater() {
    return operater;
  }

  public void setOperater(String operater) {
    this.operater = operater;
  }

}
