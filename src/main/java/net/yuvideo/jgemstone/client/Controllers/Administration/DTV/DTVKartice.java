package net.yuvideo.jgemstone.client.Controllers.Administration.DTV;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.UserData;
import org.json.JSONObject;

import java.util.ArrayList;

public class DTVKartice extends RecursiveTreeObject<DTVKartice> {

  int idKartica;
  int userID;
  int paketID;
  String endDate;
  String freezeDate;
  String idServisa;
  Client client;
  UserData userData;
  String ime;
  String adresaUsluge;
  String jbroj;
  private int id;
  private ArrayList<DTVKartice> dtvKarticeArrayList = new ArrayList<>();


  public ArrayList<DTVKartice> getDtvKarticeArrayList() {
    JSONObject object = new JSONObject();
    object.put("action", "getAllDTVCards");
    object = client.send_object(object);
    for (int i = 0; i < object.length(); i++) {
      DTVKartice dtvKartica = new DTVKartice();
      JSONObject kartica = object.getJSONObject(String.valueOf(i));
      dtvKartica.setEndDate(kartica.getString("endDate"));
      dtvKartica.setFreezeDate(kartica.getString("freezeDate"));
      dtvKartica.setId(kartica.getInt("id"));
      dtvKartica.setIdKartica(kartica.getInt("idKartica"));
      dtvKartica.setPaketID(kartica.getInt("paketID"));
      dtvKartica.setUserID(kartica.getInt("userID"));

      //userData
      userData = new UserData(client, kartica.getInt("userID"));
      dtvKartica.setUserData(userData);
      dtvKartica.setAdresaUsluge(
          String.format("%s %s", userData.getAdresaUsluge(), userData.getMestoUsluge()));
      dtvKartica.setIme(userData.getIme());
      dtvKartica.setJbroj(userData.getJbroj());

      dtvKarticeArrayList.add(dtvKartica);
    }

    return dtvKarticeArrayList;
  }

  public void setData(JSONObject data) {
    if (!data.has("id")) {
      AlertUser.error("GRESKA", "Korisnik nema DTV karticu");
      return;
    }
    this.setEndDate(data.getString("endDate"));
    this.setId(data.getInt("id"));
    this.setFreezeDate(data.getString("freezeDate"));
    this.setIdKartica(data.getInt("idKartica"));
    this.setPaketID(data.getInt("paketID"));
    this.setUserID(data.getInt("userID"));

  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIdKartica() {
    return idKartica;
  }

  public void setIdKartica(int idKartica) {
    this.idKartica = idKartica;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public int getPaketID() {
    return paketID;
  }

  public void setPaketID(int paketID) {
    this.paketID = paketID;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getFreezeDate() {
    return freezeDate;
  }

  public void setFreezeDate(String freezeDate) {
    this.freezeDate = freezeDate;
  }

  public String getIdServisa() {
    return idServisa;
  }

  public void setIdServisa(String idServisa) {
    this.idServisa = idServisa;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public UserData getUserData() {
    return userData;
  }

  public void setUserData(UserData userData) {
    this.userData = userData;
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


  public String getJbroj() {
    return jbroj;
  }

  public void setJbroj(String jbroj) {
    this.jbroj = jbroj;
  }


}
