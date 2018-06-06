package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;
import org.json.JSONObject;

public class FirmaSettings implements Serializable{
  JSONObject jsonObject;
  String FIRMA_WEB_PAGE;
  String FIRMA_ADRESA;
  String FIRMA_MBR;
  String FIRMA_FAX;
  String FIRMA_TELEFON;
  String FIRMA_NAZIV;
  String FIRMA_SERVIS_TELEFON;
  String FIRMA_TEKUCIRACUN;
  String FIRMA_FAKTURA_PEPDV;
  String FIRMA_SERVIS_EMAIL;
  String FIRMA_PIB;
  String FIRMA_MESTO_IZDAVANJA_RACUNA;
  String FIRMA_MESTO_PROMETA_DOBARA;
  String FIRMA_NACIN_PLACANJA_FAKTURA;
  String FIRMA_ROK_PLACANJA_RACUN;
  String FIRMA_ROK_PLACANJA_FAKTURA;


  public FirmaSettings(Client client) {
    jsonObject = new JSONObject();
    jsonObject.put("action", "GET_OPTIONS");
    jsonObject = client.send_object(jsonObject);
    if(jsonObject.has("ERROR")){
      AlertUser.error("GRESKA", jsonObject.getString("ERROR"));
      return;
    }

    this.FIRMA_WEB_PAGE = jsonObject.getString("FIRMA_WEBPAGE");
    this.FIRMA_ADRESA = jsonObject.getString("FIRMA_ADRESA");
    this.FIRMA_MBR = jsonObject.getString("FIRMA_MBR");
    this.FIRMA_FAX = jsonObject.getString("FIRMA_FAX");
    this.FIRMA_TELEFON = jsonObject.getString("FIRMA_TELEFON");
    this.FIRMA_NAZIV = jsonObject.getString("FIRMA_NAZIV");
    this.FIRMA_SERVIS_TELEFON = jsonObject.getString("FIRMA_SERVIS_TELEFON");
    this.FIRMA_TEKUCIRACUN = jsonObject.getString("FIRMA_TEKUCIRACUN");
    this.FIRMA_FAKTURA_PEPDV = jsonObject.getString("FIRMA_FAKTURA_PEPDV");
    this.FIRMA_SERVIS_EMAIL = jsonObject.getString("FIRMA_SERVIS_EMAIL");
    this.FIRMA_PIB = jsonObject.getString("FIRMA_PIB");
    this.FIRMA_MESTO_IZDAVANJA_RACUNA = jsonObject.getString("FIRMA_MESTO_IZDAVANJA_RACUNA");
    this.FIRMA_MESTO_PROMETA_DOBARA = jsonObject.getString("FIRMA_MESTO_PROMETA_DOBARA");
    this.FIRMA_NACIN_PLACANJA_FAKTURA = jsonObject.getString("FIRMA_NACIN_PLACANJA_FAKTURA");
    this.FIRMA_ROK_PLACANJA_FAKTURA = jsonObject.getString("FIRMA_ROK_PLACANJA_FAKTURA");
    this.FIRMA_ROK_PLACANJA_RACUN = jsonObject.getString("FIRMA_ROK_PLACANJA_RACUN");

  }

  public JSONObject getJsonObject() {
    return jsonObject;
  }

  public String getFIRMA_WEB_PAGE() {
    return FIRMA_WEB_PAGE;
  }

  public void setFIRMA_WEB_PAGE(String FIRMA_WEB_PAGE) {
    this.FIRMA_WEB_PAGE = FIRMA_WEB_PAGE;
  }

  public String getFIRMA_ADRESA() {
    return FIRMA_ADRESA;
  }

  public void setFIRMA_ADRESA(String FIRMA_ADRESA) {
    this.FIRMA_ADRESA = FIRMA_ADRESA;
  }

  public String getFIRMA_MBR() {
    return FIRMA_MBR;
  }

  public void setFIRMA_MBR(String FIRMA_MBR) {
    this.FIRMA_MBR = FIRMA_MBR;
  }

  public String getFIRMA_FAX() {
    return FIRMA_FAX;
  }

  public void setFIRMA_FAX(String FIRMA_FAX) {
    this.FIRMA_FAX = FIRMA_FAX;
  }

  public String getFIRMA_TELEFON() {
    return FIRMA_TELEFON;
  }

  public void setFIRMA_TELEFON(String FIRMA_TELEFON) {
    this.FIRMA_TELEFON = FIRMA_TELEFON;
  }

  public String getFIRMA_NAZIV() {
    return FIRMA_NAZIV;
  }

  public void setFIRMA_NAZIV(String FIRMA_NAZIV) {
    this.FIRMA_NAZIV = FIRMA_NAZIV;
  }

  public String getFIRMA_SERVIS_TELEFON() {
    return FIRMA_SERVIS_TELEFON;
  }

  public void setFIRMA_SERVIS_TELEFON(String FIRMA_SERVIS_TELEFON) {
    this.FIRMA_SERVIS_TELEFON = FIRMA_SERVIS_TELEFON;
  }

  public String getFIRMA_TEKUCIRACUN() {
    return FIRMA_TEKUCIRACUN;
  }

  public void setFIRMA_TEKUCIRACUN(String FIRMA_TEKUCIRACUN) {
    this.FIRMA_TEKUCIRACUN = FIRMA_TEKUCIRACUN;
  }

  public String getFIRMA_FAKTURA_PEPDV() {
    return FIRMA_FAKTURA_PEPDV;
  }

  public void setFIRMA_FAKTURA_PEPDV(String FIRMA_FAKTURA_PEPDV) {
    this.FIRMA_FAKTURA_PEPDV = FIRMA_FAKTURA_PEPDV;
  }

  public String getFIRMA_SERVIS_EMAIL() {
    return FIRMA_SERVIS_EMAIL;
  }

  public void setFIRMA_SERVIS_EMAIL(String FIRMA_SERVIS_EMAIL) {
    this.FIRMA_SERVIS_EMAIL = FIRMA_SERVIS_EMAIL;
  }

  public String getFIRMA_PIB() {
    return FIRMA_PIB;
  }

  public void setFIRMA_PIB(String FIRMA_PIB) {
    this.FIRMA_PIB = FIRMA_PIB;
  }

  public String getFIRMA_MESTO_IZDAVANJA_RACUNA() {
    return FIRMA_MESTO_IZDAVANJA_RACUNA;
  }

  public void setFIRMA_MESTO_IZDAVANJA_RACUNA(String FIRMA_MESTO_IZDAVANJA_RACUNA) {
    this.FIRMA_MESTO_IZDAVANJA_RACUNA = FIRMA_MESTO_IZDAVANJA_RACUNA;
  }

  public String getFIRMA_MESTO_PROMETA_DOBARA() {
    return FIRMA_MESTO_PROMETA_DOBARA;
  }

  public void setFIRMA_MESTO_PROMETA_DOBARA(String FIRMA_MESTO_PROMETA_DOBARA) {
    this.FIRMA_MESTO_PROMETA_DOBARA = FIRMA_MESTO_PROMETA_DOBARA;
  }

  public String getFIRMA_NACIN_PLACANJA_FAKTURA() {
    return FIRMA_NACIN_PLACANJA_FAKTURA;
  }

  public void setFIRMA_NACIN_PLACANJA_FAKTURA(String FIRMA_NACIN_PLACANJA_FAKTURA) {
    this.FIRMA_NACIN_PLACANJA_FAKTURA = FIRMA_NACIN_PLACANJA_FAKTURA;
  }

  public String getFIRMA_ROK_PLACANJA_RACUN() {
    return FIRMA_ROK_PLACANJA_RACUN;
  }

  public void setFIRMA_ROK_PLACANJA_RACUN(String FIRMA_ROK_PLACANJA_RACUN) {
    this.FIRMA_ROK_PLACANJA_RACUN = FIRMA_ROK_PLACANJA_RACUN;
  }

  public String getFIRMA_ROK_PLACANJA_FAKTURA() {
    return FIRMA_ROK_PLACANJA_FAKTURA;
  }

  public void setFIRMA_ROK_PLACANJA_FAKTURA(String FIRMA_ROK_PLACANJA_FAKTURA) {
    this.FIRMA_ROK_PLACANJA_FAKTURA = FIRMA_ROK_PLACANJA_FAKTURA;
  }
}
