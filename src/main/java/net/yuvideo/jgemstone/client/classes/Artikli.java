package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 * Created by PsyhoZOOM@gmail.com on 1/30/18.
 */
public class Artikli implements Serializable {

  int id;
  String naziv;
  String model;
  String proizvodjac;
  String serijski;
  String pon;
  String mac;
  String dobavljac;
  Double nabavnaCena;
  String jmere;
  int kolicina;
  String opis;
  String operName;
  String datum;
  String brDok;
  String nazivMagacina;
  int idMagacin;
  int uniqueID;
  boolean isUser;


  ArrayList<Artikli> artikliArrayList;

  ArrayList<Magacin> magacinArrayList;
  private Client client;

  private void setArtikle(JSONObject jsonObject) {
    artikliArrayList = new ArrayList<>();

    for (int i = 0; i < jsonObject.length(); i++) {
      JSONObject artkal = (JSONObject) jsonObject.get(String.valueOf(i));
      Artikli artikli = new Artikli();
      artikli.setId(artkal.getInt("id"));
      artikli.setNaziv(artkal.getString("naziv"));
      artikli.setProizvodjac(artkal.getString("proizvodjac"));
      artikli.setModel(artkal.getString("model"));
      artikli.setSerijski(artkal.getString("serijski"));
      artikli.setPon(artkal.getString("pon"));
      artikli.setMac(artkal.getString("mac"));
      artikli.setDobavljac(artkal.getString("dobavljac"));
      artikli.setBrDok(artkal.getString("brDokumenta"));
      artikli.setNabavnaCena(artkal.getDouble("nabavnaCena"));
      artikli.setJmere(artkal.getString("jMere"));
      artikli.setKolicina(artkal.getInt("kolicina"));
      artikli.setOpis(artkal.getString("opis"));
      artikli.setDatum(artkal.getString("datum"));
      artikli.setOperName(artkal.getString("operName"));
      artikli.setUser(artkal.getBoolean("isUser"));
      if (artkal.has("idMagacin")) {
        artikli.setIdMagacin(artkal.getInt("idMagacin"));
      }
      if (artkal.getBoolean("isUser")) {
        artikli.setNazivMagacina(getUserIme(artkal.getInt("idMagacin")));
      } else {
        artikli.setNazivMagacina(getImeMagacina(artkal.getInt("idMagacin")));
      }
      artikli.setUniqueID(artkal.getInt("uniqueID"));
      artikliArrayList.add(artikli);
    }
  }

  private String getUserIme(int idMagacin) {
    UserData userData = new UserData(client, idMagacin);
    return String.format("%s, %s", userData.getIme(), userData.getJbroj());
  }

  private String getImeMagacina(int idMagacin) {
    for (Magacin magacin : this.magacinArrayList) {
      if (magacin.getId() == idMagacin) {
        return magacin.getNaziv();
      }
    }
    return "";
  }


  public void getAllArtikle(Client client) {
    Magacin magacin = new Magacin();
    this.magacinArrayList = magacin.getMagaciniArr(client);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "getAllArtikles");
    jsonObject = client.send_object(jsonObject);
    this.setArtikle(jsonObject);


  }


  public ArrayList<Artikli> getArtikliArrayList() {
    return artikliArrayList;
  }

  public Artikli getArtikal(int id) {
    for (Artikli art : getArtikliArrayList()) {
      if (art.getId() == id) {
        return art;
      }
    }
    return null;
  }

  public void searchArtikal(JSONObject obj, Client client) {
    this.client = client;
    Magacin magacin = new Magacin();
    this.magacinArrayList = magacin.getMagaciniArr(client);

    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "searchArtikal");
    jsonObject = client.send_object(obj);
    this.setArtikle(jsonObject);


  }

  public int getUniqueID() {
    return uniqueID;
  }

  public void setUniqueID(int uniqueID) {
    this.uniqueID = uniqueID;
  }

  public String getNazivMagacina() {
    return nazivMagacina;
  }

  public void setNazivMagacina(String nazivMagacina) {
    this.nazivMagacina = nazivMagacina;
  }

  public int getIdMagacin() {
    return idMagacin;
  }

  public void setIdMagacin(int idMagacin) {
    this.idMagacin = idMagacin;
  }

  public String getProizvodjac() {
    return proizvodjac;
  }

  public void setProizvodjac(String proizvodjac) {
    this.proizvodjac = proizvodjac;
  }

  public String getPon() {
    return pon;
  }

  public void setPon(String pon) {
    this.pon = pon;
  }


  public String getBrDok() {
    return brDok;
  }

  public void setBrDok(String brDok) {
    this.brDok = brDok;
  }

  public String getDatum() {
    return datum;
  }

  public void setDatum(String datum) {
    this.datum = datum;
  }

  public String getOperName() {
    return operName;
  }

  public void setOperName(String operName) {
    this.operName = operName;
  }

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

  public String getSerijski() {
    return serijski;
  }

  public void setSerijski(String serijski) {
    this.serijski = serijski;
  }


  public String getMac() {
    return mac;
  }

  public void setMac(String mac) {
    this.mac = mac;
  }

  public String getDobavljac() {
    return dobavljac;
  }

  public void setDobavljac(String dobavljac) {
    this.dobavljac = dobavljac;
  }

  public Double getNabavnaCena() {
    return nabavnaCena;
  }

  public void setNabavnaCena(Double nabavnaCena) {
    this.nabavnaCena = nabavnaCena;
  }

  public String getJmere() {
    return jmere;
  }

  public void setJmere(String jmere) {
    this.jmere = jmere;
  }

  public int getKolicina() {
    return kolicina;
  }

  public void setKolicina(int kolicina) {
    this.kolicina = kolicina;
  }

  public String getOpis() {
    return opis;
  }

  public void setOpis(String opis) {
    this.opis = opis;
  }

  public boolean isUser() {
    return isUser;
  }

  public void setUser(boolean user) {
    isUser = user;
  }
}
