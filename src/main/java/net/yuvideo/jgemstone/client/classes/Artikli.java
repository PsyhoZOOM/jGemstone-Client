package net.yuvideo.jgemstone.client.classes;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by PsyhoZOOM@gmail.com on 1/30/18.
 */
public class Artikli implements Serializable {
    int id;
    String naziv;
    String model;
    String serijski;
    String pserijski;
    String mac;
    String dobavljac;
    Double nabavnaCena;
    String jmere;
    int kolicina;
    String opis;
    String operName;
    String datum;
    String brDok;
    int idMagacin;

    ArrayList<Artikli> artikliArrayList;

    public void initMagacin(Client client) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "getAllArtikles");
        jsonObject = client.send_object(jsonObject);
        artikliArrayList = new ArrayList<>();

        for (int i = 0; i < jsonObject.length(); i++) {
            JSONObject artkal = (JSONObject) jsonObject.get(String.valueOf(i));
            Artikli artikli = new Artikli();
            artikli.setId(artkal.getInt("id"));
            artikli.setNaziv(artkal.getString("naziv"));
            artikli.setModel(artkal.getString("model"));
            artikli.setSerijski(artkal.getString("serijski"));
            artikli.setPserijski(artkal.getString("pserijski"));
            artikli.setMac(artkal.getString("mac"));
            artikli.setDobavljac(artkal.getString("dobavljac"));
            artikli.setBrDok(artkal.getString("brDokumenta"));
            artikli.setNabavnaCena(artkal.getDouble("nabavnaCena"));
            artikli.setJmere(artkal.getString("jMere"));
            artikli.setKolicina(artkal.getInt("kolicina"));
            artikli.setOpis(artkal.getString("opis"));
            artikli.setDatum(artkal.getString("datum"));
            artikli.setOperName(artkal.getString("operName"));
            artikli.setIdMagacin(artkal.getInt("idMagacin"));
            artikliArrayList.add(artikli);
        }
    }

    public ArrayList<Artikli> getArtikliArrayList() {
        return artikliArrayList;
    }

    public Artikli getArtikal(int id) {
        for (Artikli art : getArtikliArrayList()) {
            if (art.getId() == id)
                return art;
        }
        return null;
    }

    public int getIdMagacin() {
        return idMagacin;
    }

    public void setIdMagacin(int idMagacin) {
        this.idMagacin = idMagacin;
    }

    public String getPserijski() {
        return pserijski;
    }

    public void setPserijski(String pserijski) {
        this.pserijski = pserijski;
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

}
