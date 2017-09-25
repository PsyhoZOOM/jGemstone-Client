package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 5/25/17.
 */
public class Paketi implements Serializable {
    int id;
    String naziv;
    double pretplata;
    double PDV;
    int besplatiniMinutiFiksna;


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

    public double getPretplata() {
        return pretplata;
    }

    public void setPretplata(double pretplata) {
        this.pretplata = pretplata;
    }

    public double getPDV() {
        return PDV;
    }

    public void setPDV(double PDV) {
        this.PDV = PDV;
    }

    public int getBesplatiniMinutiFiksna() {
        return besplatiniMinutiFiksna;
    }

    public void setBesplatiniMinutiFiksna(int besplatiniMinutiFiksna) {
        this.besplatiniMinutiFiksna = besplatiniMinutiFiksna;
    }
}
