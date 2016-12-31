package net.yuvideo.jgemstone.client.classes;

import org.json.JSONObject;

import java.util.List;


/**
 * Created by PsyhoZOOM@gmail.com on 2/2/18.
 */
public class UserData {
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

    Client client;


    public UserData(Client client, int userID) {
        this.client = client;
        this.id = userID;
        setData();
    }

    public void updateData() {
        setData();
    }

    private void setData() {
        JSONObject obj = new JSONObject();
        obj.put("action", "get_user_data");
        obj.put("userId", this.id);

        obj = client.send_object(obj);

        setId(obj.getInt("id"));
        setIme(obj.getString("fullName"));
        setDatum_rodjenja(obj.getString("datumRodjenja"));
        setAdresa(obj.getString("adresa"));
        setMesto(obj.getString("mesto"));
        setPostanski_broj(obj.getString("postBr"));
        setFiksni(obj.getString("telFix"));
        setMobilni(obj.getString("telMob"));
        setBr_lk(obj.getString("brLk"));
        setJMBG(obj.getString("JMBG"));
        setKomentar(obj.getString("komentar"));
        setjMesto(obj.getString("jMesto"));
        setjAdresa(obj.getString("jAdresa"));
        setjAdresaBroj(obj.getString("jAdresaBroj"));
        setJbroj(obj.getString("jBroj"));
        setAdresaRacuna(obj.getString("adresaRacuna"));
        setMestoRacuna(obj.getString("mestoRacuna"));
        setAdresaUsluge(obj.getString("adresaUsluge"));
        setMestoUsluge(obj.getString("mestoUsluge"));
        setFirma(obj.getBoolean("firma"));
        setNazivFirme(obj.getString("nazivFirme"));
        setKontaktOsoba(obj.getString("kontaktOsoba"));
        setTelKontaktOsobe(obj.getString("kontaktOsobaTel"));
        setKodBanke(obj.getString("kodBanke"));
        setTekuciRacuna(obj.getString("tekuciRacun"));
        setPIB(obj.getString("PIB"));
        setMaticniBroj(obj.getString("maticniBroj"));
        setFax(obj.getString("fax"));
        setAdresaFirme(obj.getString("adresaFirme"));

    }

    public String getJbroj() {
        return jbroj;
    }

    public void setJbroj(String jbroj) {
        this.jbroj = jbroj;
    }

    public double getDug() {
        return dug;
    }

    public void setDug(double dug) {
        this.dug = dug;
    }

    public boolean isFirma() {
        return firma;
    }

    public void setFirma(boolean firma) {
        this.firma = firma;
    }

    public String getNazivFirme() {
        return nazivFirme;
    }

    public void setNazivFirme(String nazivFirme) {
        this.nazivFirme = nazivFirme;
    }

    public String getKontaktOsoba() {
        return kontaktOsoba;
    }

    public void setKontaktOsoba(String kontaktOsoba) {
        this.kontaktOsoba = kontaktOsoba;
    }

    public String getTelKontaktOsobe() {
        return telKontaktOsobe;
    }

    public void setTelKontaktOsobe(String telKontaktOsobe) {
        this.telKontaktOsobe = telKontaktOsobe;
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

    public String getDatum_rodjenja() {
        return datum_rodjenja;
    }

    public void setDatum_rodjenja(String datum_rodjenja) {
        this.datum_rodjenja = datum_rodjenja;
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
}
