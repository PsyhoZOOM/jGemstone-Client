package net.yuvideo.jgemstone.client.classes;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by zoom on 9/7/16.
 */
public class Uplate extends RecursiveTreeObject<Uplate> {

  private int id;
  private String datum;
  private double duguje;
  private double potrazuje;
  private String operater;
  private String opis;
  private int userID;
  private Client client;
  private ArrayList<Uplate> uplateArrayList;
  private boolean error;
  private String errorMSG;
  private double ukupnoDuguje = 0;
  private double ukupnoUplaceno = 0;
  private double ukupanDug = 0;

  public ArrayList<Uplate> getUplateUser(int userID) {
    JSONObject object = new JSONObject();
    object.put("action", "get_uplate_user");
    object.put("userID", userID);
    object = client.send_object(object);
    if (object.has("ERROR")) {
      setError(true);
      setErrorMSG(object.getString("ERROR"));
      return null;
    }

    uplateArrayList = new ArrayList<>();
    for (int i = 0; i < object.length(); i++) {
      Uplate uplata = new Uplate();
      JSONObject uplObj = object.getJSONObject(String.valueOf(i));
      uplata.setId(uplObj.getInt("id"));
      uplata.setUserID(uplObj.getInt("userID"));
      uplata.setDatum(uplObj.getString("datum"));
      uplata.setPotrazuje(uplObj.getDouble("potrazuje"));
      uplata.setDuguje(uplObj.getDouble("duguje"));
      uplata.setOperater(uplObj.getString("operater"));
      uplata.setOpis(uplObj.getString("opis"));

      ukupnoDuguje += uplata.getPotrazuje();
      ukupnoUplaceno += uplata.getDuguje();

      uplateArrayList.add(uplata);

    }

    ukupanDug = ukupnoDuguje - ukupnoUplaceno;

    return uplateArrayList;
  }


  public double getUkupnoDuguje() {
    return ukupnoDuguje;
  }

  public void setUkupnoDuguje(double ukupnoDuguje) {
    this.ukupnoDuguje = ukupnoDuguje;
  }

  public double getUkupnoUplaceno() {
    return ukupnoUplaceno;
  }

  public void setUkupnoUplaceno(double ukupnoUplaceno) {
    this.ukupnoUplaceno = ukupnoUplaceno;
  }

  public void setDuguje(double duguje) {
    this.duguje = duguje;
  }

  public void setPotrazuje(double potrazuje) {
    this.potrazuje = potrazuje;
  }

  public double getUkupanDug() {
    return ukupanDug;
  }

  public void setUkupanDug(double ukupanDug) {
    this.ukupanDug = ukupanDug;
  }

  public boolean isError() {
    return error;
  }

  public void setError(boolean error) {
    this.error = error;
  }

  public String getErrorMSG() {
    return errorMSG;
  }

  public void setErrorMSG(String errorMSG) {
    this.errorMSG = errorMSG;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public ArrayList<Uplate> getUplateArrayList() {
    return uplateArrayList;
  }

  public void setUplateArrayList(
      ArrayList<Uplate> uplateArrayList) {
    this.uplateArrayList = uplateArrayList;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDatum() {
    return datum;
  }

  public void setDatum(String datum) {
    this.datum = datum;
  }

  public Double getDuguje() {
    return duguje;
  }

  public void setDuguje(Double duguje) {
    this.duguje = duguje;
  }

  public Double getPotrazuje() {
    return potrazuje;
  }

  public void setPotrazuje(Double potrazuje) {
    this.potrazuje = potrazuje;
  }

  public String getOperater() {
    return operater;
  }

  public void setOperater(String operater) {
    this.operater = operater;
  }

  public String getOpis() {
    return opis;
  }

  public void setOpis(String opis) {
    this.opis = opis;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

}


