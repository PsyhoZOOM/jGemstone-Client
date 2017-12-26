package net.yuvideo.jgemstone.client.classes;

/**
 * Created by zoom on 2/2/17.
 */
public class digitalniTVPaket {
    int id;
    String naziv;
    int paketID;
    int produzenje;
    String opis;
    Double cena;
    Double pdv;
    boolean obracun;
    Double cenaPDV;

    public void setProduzenje(int produzenje) {
        this.produzenje = produzenje;
    }

    public Double getCenaPDV() {
        return cenaPDV;
    }

    public void setCenaPDV(Double cenaPDV) {
        this.cenaPDV = cenaPDV;
    }

    public Double getPdv() {
        return pdv;
    }

    public void setPdv(Double pdv) {
        this.pdv = pdv;
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

    public int getPaketID() {
        return paketID;
    }

    public void setPaketID(int paketID) {
        this.paketID = paketID;
    }

    public int getProduzenje() {
        return produzenje;
    }


    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public boolean isObracun() {
        return obracun;
    }

    public void setObracun(boolean obracun) {
        this.obracun = obracun;
    }

}
