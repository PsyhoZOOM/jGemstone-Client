package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/**
 * Created by zoom on 8/23/16.
 */
public class Users implements Serializable {

  int br;
  int id;
  String username;
  String ime;
  String datum_rodjenja;
  String mesto;
  String postanski_broj;
  String br_lk;
  String JMBG;
  String adresa;
  String adresaRacuna;
  String mestoRacuna;
  String adresaUsluge;
  String mestoUsluge;
  String fiksni;
  String mobilni;
  String ostalo;
  String komentar;
  List services;
  String jMesto;
  String jAdresa;
  String jAdresaBroj;
  String jbroj;
  double dug;
  boolean firma;
  String nazivFirme;
  String kontaktOsoba;
  String telKontaktOsobe;
  String kodBanke;
  String PIB;
  String maticniBroj;
  String tekuciRacuna;
  String fax;
  String adresaFirme;
  String mestoFirme;
  String email;

  ArrayList<Users> usersArrayList;

  public ArrayList<Users> getUsers(Client client) {
    usersArrayList = new ArrayList<>();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "get_users");

    jsonObject = client.send_object(jsonObject);

    for (int i = 0; i < jsonObject.length(); i++) {
      usersArrayList.add(this.getUserData(jsonObject.getJSONObject(String.valueOf(i))));

    }

    return usersArrayList;
  }

  public ArrayList<Users> getUsers(Client client, String searchCrit) {
    usersArrayList = new ArrayList<>();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "get_users");
    jsonObject.put("username", searchCrit);

    jsonObject = client.send_object(jsonObject);

    for (int i = 0; i < jsonObject.length(); i++) {
      usersArrayList.add(this.getUserData(jsonObject.getJSONObject(String.valueOf(i))));

    }

    return usersArrayList;
  }

  private Users getUserData(JSONObject object) {
    Users user = new Users();
    user.setId(object.getInt("id"));
    user.setIme(object.getString("fullName"));
    user.setMesto(object.getString("mesto"));
    user.setAdresa(object.getString("adresa"));
    user.setAdresaRacuna(object.getString("adresaRacuna"));
    user.setMestoRacuna(object.getString("mestoRacuna"));
    user.setBr_lk(object.getString("brLk"));
    user.setDatum_rodjenja(object.getString("datumRodjenja"));
    user.setFiksni(object.getString("telFixni"));
    user.setMobilni(object.getString("telMobilni"));
    user.setJMBG(object.getString("JMBG"));
    user.setKomentar(object.getString("komentar"));
    user.setPostanski_broj(object.getString("postBr"));
    user.setJbroj(object.getString("jBroj"));
    user.setjAdresa(object.getString("jAdresa"));
    user.setjAdresaBroj(object.getString("jAdresaBroj"));
    user.setjMesto(object.getString("jMesto"));
    user.setAdresaUsluge(object.getString("adresaUsluge"));
    user.setMestoUsluge(object.getString("mestoUsluge"));
    user.setDug(object.getDouble("dug"));
    user.setFirma(object.getBoolean("firma"));
    user.setNazivFirme(object.getString("nazivFirme"));
    user.setKontaktOsoba(object.getString("kontaktOsoba"));
    user.setTelKontaktOsobe(object.getString("kontaktOsobaTel"));
    user.setKodBanke(object.getString("kodBanke"));
    user.setPIB(object.getString("PIB"));
    user.setMaticniBroj(object.getString("maticniBroj"));
    user.setTekuciRacuna(object.getString("tekuciRacun"));
    user.setFax(object.getString("fax"));
    user.setAdresaFirme(object.getString("adresaFirme"));
    user.setMestoFirme(object.getString("mestoFirme"));

    return user;
  }

  public String getAdresaRacuna() {
    return adresaRacuna;
  }

  public void setAdresaRacuna(String adresaRacuna) {
    this.adresaRacuna = adresaRacuna;
  }

  public String getMestoRacuna() {
    return mestoRacuna;
  }

  public void setMestoRacuna(String mestoRacuna) {
    this.mestoRacuna = mestoRacuna;
  }

  public String getAdresaUsluge() {
    return adresaUsluge;
  }

  public void setAdresaUsluge(String adresaUsluge) {
    this.adresaUsluge = adresaUsluge;
  }

  public String getMestoUsluge() {
    return mestoUsluge;
  }

  public void setMestoUsluge(String mestoUsluge) {
    this.mestoUsluge = mestoUsluge;
  }

  public String getTelKontaktOsobe() {
    return telKontaktOsobe;
  }

  public void setTelKontaktOsobe(String telKontaktOsobe) {
    this.telKontaktOsobe = telKontaktOsobe;
  }

  public String getKontaktOsoba() {
    return kontaktOsoba;
  }

  public void setKontaktOsoba(String kontaktOsoba) {
    this.kontaktOsoba = kontaktOsoba;
  }

  public String getKodBanke() {
    return kodBanke;
  }

  public void setKodBanke(String kodBanke) {
    this.kodBanke = kodBanke;
  }

  public String getPIB() {
    return PIB;
  }

  public void setPIB(String PIB) {
    this.PIB = PIB;
  }

  public String getMaticniBroj() {
    return maticniBroj;
  }

  public void setMaticniBroj(String maticniBroj) {
    this.maticniBroj = maticniBroj;
  }

  public String getTekuciRacuna() {
    return tekuciRacuna;
  }

  public void setTekuciRacuna(String tekuciRacuna) {
    this.tekuciRacuna = tekuciRacuna;
  }

  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public String getAdresaFirme() {
    return adresaFirme;
  }

  public void setAdresaFirme(String adresaFirme) {
    this.adresaFirme = adresaFirme;
  }

  public String getNazivFirme() {
    return nazivFirme;
  }

  public void setNazivFirme(String nazivFirme) {
    this.nazivFirme = nazivFirme;
  }

  public boolean isFirma() {
    return firma;
  }

  public void setFirma(boolean firma) {
    this.firma = firma;
  }

  public double getDug() {
    return dug;
  }

  public void setDug(double dug) {
    this.dug = dug;
  }

  public String getJbroj() {
    return jbroj;
  }

  public void setJbroj(String jbroj) {
    this.jbroj = jbroj;
  }

  public String getjMesto() {
    return jMesto;
  }

  public void setjMesto(String jMesto) {
    this.jMesto = jMesto;
  }

  public String getjAdresa() {
    return jAdresa;
  }

  public void setjAdresa(String jAdresa) {
    this.jAdresa = jAdresa;
  }

  public String getjAdresaBroj() {
    return jAdresaBroj;
  }

  public void setjAdresaBroj(String jAdresaBroj) {
    this.jAdresaBroj = jAdresaBroj;
  }

  public String getKomentar() {
    return komentar;
  }

  public void setKomentar(String komentar) {
    this.komentar = komentar;
  }

  public List getServices() {
    return services;
  }

  public void setServices(List services) {
    this.services = services;
  }

  public int getBr() {
    return br;
  }

  public void setBr(int br) {
    this.br = br;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getMesto() {
    return mesto;
  }

  public void setMesto(String mesto) {
    this.mesto = mesto;
  }

  public String getPostanski_broj() {
    return postanski_broj;
  }

  public void setPostanski_broj(String postanski_broj) {
    this.postanski_broj = postanski_broj;
  }

  public String getBr_lk() {
    return br_lk;
  }

  public void setBr_lk(String br_lk) {
    this.br_lk = br_lk;
  }

  public String getJMBG() {
    return JMBG;
  }

  public void setJMBG(String JMBG) {
    this.JMBG = JMBG;
  }

  public String getAdresa() {
    return adresa;
  }

  public void setAdresa(String adresa) {
    this.adresa = adresa;
  }


  public String getFiksni() {
    return fiksni;
  }

  public void setFiksni(String fiksni) {
    this.fiksni = fiksni;
  }

  public String getMobilni() {
    return mobilni;
  }

  public void setMobilni(String mobilni) {
    this.mobilni = mobilni;
  }

  public String getOstalo() {
    return ostalo;
  }

  public void setOstalo(String ostalo) {
    this.ostalo = ostalo;
  }

  public String getDatum_rodjenja() {
    return datum_rodjenja;
  }

  public void setDatum_rodjenja(String datum_rodjenja) {
    this.datum_rodjenja = datum_rodjenja;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getIme() {
    return ime;
  }

  public void setIme(String ime) {
    this.ime = ime;
  }


  @Override
  public String toString() {
    return this.getIme();
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMestoFirme() {
    return mestoFirme;
  }

  public void setMestoFirme(String mestoFirme) {
    this.mestoFirme = mestoFirme;
  }
}
