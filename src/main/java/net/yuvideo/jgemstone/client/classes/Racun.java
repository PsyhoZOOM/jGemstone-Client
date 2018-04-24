package net.yuvideo.jgemstone.client.classes;

import javafx.scene.image.Image;
import org.json.JSONObject;

import java.util.ArrayList;

public class Racun {
    String zaMesec;
    String nazivUsluge;
    String datumZaduzenja;
    String sifraKorisnika;
    String mesec;
    String mestoDatumIzdavanja;
    String datumPrometa;
    String rokZaPlacanje;
    String adresaUsluge;
    String mestoRacuna;
    String adresaRacuna;
    String zaPeriod;
    String ime;
    String adresaKorisnika;

    Image qrCode;

    int kolicina;
    double iznos;
    double cena;
    double popust;
    double osnovica;
    double osnovicaUkupno;
    double stopaPDV;
    double pdv;
    double pdvUkupno;
    double ukupno;
    double ukupnoUkupno;

    double prethodniDug;
    double ukupanDug;

    ArrayList<Racun> racunArrayList;
    ArrayList<Racun> racunArrayListTmp;


    int userID;
    Client client;


    public void initRacun(int userID, String zaMesec, Client client) {
        this.userID = userID;
        this.zaMesec = zaMesec;
        this.client = client;
        getData();
    }

    public void getData() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "getUserRacun");
        jsonObject.put("userID", this.userID);
        jsonObject.put("zaMesec", this.zaMesec);

        jsonObject = client.send_object(jsonObject);
        int i = 0;
        racunArrayList = new ArrayList<>();
        for (i = 0; i < jsonObject.length() - 1; i++) {
            System.out.println(jsonObject.getJSONObject(String.valueOf(i)));
            JSONObject rac = jsonObject.getJSONObject(String.valueOf(i));
            Racun racun = new Racun();
            racun.setNazivUsluge(rac.getString("nazivUsluge"));
            racun.setOsnovica(rac.getDouble("osnovica"));
            racun.setKolicina(rac.getInt("kolicina"));
            racun.setPopust(rac.getInt("popust"));
            racun.setCena(rac.getDouble("cena"));
            racun.setStopaPDV(rac.getDouble("stopaPDV"));
            racun.setPdv(rac.getDouble("pdv"));
            racun.setUkupno(rac.getDouble("ukupno"));
            racunArrayList.add(racun);


        }

        Racun racun = new Racun();
        JSONObject rac = jsonObject.getJSONObject(String.valueOf(i));
        System.out.println(jsonObject.getJSONObject(String.valueOf(i)));
        racun.setIme(rac.getString("imePrezime"));
        racun.setSifraKorisnika(rac.getString("sifraKorisnika"));
        racun.setZaMesec(rac.getString("zaMesec"));
        racun.setAdresaRacuna(rac.getString("adresaRacuna"));
        racun.setMestoRacuna(rac.getString("mestoRacuna"));
        racun.setAdresaKorisnika(rac.getString("adresaKorisnika"));
        racun.setOsnovicaUkupno(rac.getDouble("ukupnoOsnovica"));
        racun.setPdvUkupno(rac.getDouble("ukupnoPDV"));
        racun.setUkupnoUkupno(rac.getDouble("zaduzenjeZaObrPeriod"));
        racun.setPrethodniDug(rac.getDouble("prethodniDug"));
        racun.setUkupanDug(rac.getDouble("ukupnoZaUplatu"));
        racun.setZaPeriod(rac.getString("zaMesec"));
        racunArrayList.add(racun);

    }

    public ArrayList<Racun> getRacunArrayList() {
        return racunArrayList;
    }

    public void getPages(){
        createPages();
    }

    private void createPages() {

    }


    public String getZaMesec() {
        return zaMesec;
    }

    public void setZaMesec(String zaMesec) {
        this.zaMesec = zaMesec;
    }

    public String getNazivUsluge() {
        return nazivUsluge;
    }

    public void setNazivUsluge(String nazivUsluge) {
        this.nazivUsluge = nazivUsluge;
    }

    public String getDatumZaduzenja() {
        return datumZaduzenja;
    }

    public void setDatumZaduzenja(String datumZaduzenja) {
        this.datumZaduzenja = datumZaduzenja;
    }

    public String getSifraKorisnika() {
        return sifraKorisnika;
    }

    public void setSifraKorisnika(String sifraKorisnika) {
        this.sifraKorisnika = sifraKorisnika;
    }

    public String getMesec() {
        return mesec;
    }

    public void setMesec(String mesec) {
        this.mesec = mesec;
    }

    public String getMestoDatumIzdavanja() {
        return mestoDatumIzdavanja;
    }

    public void setMestoDatumIzdavanja(String mestoDatumIzdavanja) {
        this.mestoDatumIzdavanja = mestoDatumIzdavanja;
    }

    public String getDatumPrometa() {
        return datumPrometa;
    }

    public void setDatumPrometa(String datumPrometa) {
        this.datumPrometa = datumPrometa;
    }

    public String getRokZaPlacanje() {
        return rokZaPlacanje;
    }

    public void setRokZaPlacanje(String rokZaPlacanje) {
        this.rokZaPlacanje = rokZaPlacanje;
    }

    public String getAdresaUsluge() {
        return adresaUsluge;
    }

    public void setAdresaUsluge(String adresaUsluge) {
        this.adresaUsluge = adresaUsluge;
    }

    public String getAdresaRacuna() {
        return adresaRacuna;
    }

    public void setAdresaRacuna(String adresaRacuna) {
        this.adresaRacuna = adresaRacuna;
    }

    public String getZaPeriod() {
        return zaPeriod;
    }

    public void setZaPeriod(String zaPeriod) {
        this.zaPeriod = zaPeriod;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getIznos() {
        return iznos;
    }

    public void setIznos(double iznos) {
        this.iznos = iznos;
    }

    public double getPopust() {
        return popust;
    }

    public void setPopust(double popust) {
        this.popust = popust;
    }

    public double getOsnovica() {
        return osnovica;
    }

    public void setOsnovica(double osnovica) {
        this.osnovica = osnovica;
    }

    public double getOsnovicaUkupno() {
        return osnovicaUkupno;
    }

    public void setOsnovicaUkupno(double osnovicaUkupno) {
        this.osnovicaUkupno = osnovicaUkupno;
    }

    public double getStopaPDV() {
        return stopaPDV;
    }

    public void setStopaPDV(double stopaPDV) {
        this.stopaPDV = stopaPDV;
    }

    public double getPdv() {
        return pdv;
    }

    public void setPdv(double pdv) {
        this.pdv = pdv;
    }

    public double getPdvUkupno() {
        return pdvUkupno;
    }

    public void setPdvUkupno(double pdvUkupno) {
        this.pdvUkupno = pdvUkupno;
    }

    public double getUkupno() {
        return ukupno;
    }

    public void setUkupno(double ukupno) {
        this.ukupno = ukupno;
    }

    public double getUkupnoUkupno() {
        return ukupnoUkupno;
    }

    public void setUkupnoUkupno(double ukupnoUkupno) {
        this.ukupnoUkupno = ukupnoUkupno;
    }

    public double getPrethodniDug() {
        return prethodniDug;
    }

    public void setPrethodniDug(double prethodniDug) {
        this.prethodniDug = prethodniDug;
    }

    public double getUkupanDug() {
        return ukupanDug;
    }

    public void setUkupanDug(double ukupanDug) {
        this.ukupanDug = ukupanDug;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public Image getQrCode() {
        return qrCode;
    }

    public void setQrCode(Image qrCode) {
        this.qrCode = qrCode;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public String getAdresaKorisnika() {
        return adresaKorisnika;
    }

    public void setAdresaKorisnika(String adresaKorisnika) {
        this.adresaKorisnika = adresaKorisnika;
    }

    public String getMestoRacuna() {
        return mestoRacuna;
    }

    public void setMestoRacuna(String mestoRacuna) {
        this.mestoRacuna = mestoRacuna;
    }
}
