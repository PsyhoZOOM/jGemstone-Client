package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

/**
 * Created by zoom on 1/13/17.
 */
public class OpremaKorisnik implements Serializable {

  int id;
  String naziv;
  String model;
  String komentar;
  int userID;
  String sn;
  String MAC;
  int naplata;
  String naplataSTR;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNaziv() {
    return naziv;
  }

  public void setNaziv(String naziv) {
    this.naziv = naziv;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public String getKomentar() {
    return komentar;
  }

  public void setKomentar(String komentar) {
    this.komentar = komentar;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getMAC() {
    return MAC;
  }

  public void setMAC(String MAC) {
    this.MAC = MAC;
  }

  public int getNaplata() {
    return naplata;
  }

  public void setNaplata(int naplata) {
    this.naplata = naplata;
  }

  public String getNaplataSTR() {
    return naplataSTR;
  }

  public void setNaplataSTR(String naplataSTR) {
    this.naplataSTR = naplataSTR;
  }


  @Override
  public String toString() {
    switch (naplata) {
      case 1:
        return "Naplata";
      case 2:
        return "Rentiranje";
      case 3:
        return "Korisnik";
    }
    return "Naplata";
  }
}
