package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 6/6/17.
 */
public class FiksnaPaketi implements Serializable {
    int id;
    String naziv;
    Double pretplata;
    Double pdv;
    Double besplatniMinutiFiksna;

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

    public Double getPretplata() {
        return pretplata;
    }

    public void setPretplata(Double pretplata) {
        this.pretplata = pretplata;
    }

    public Double getPdv() {
        return pdv;
    }

    public void setPdv(Double pdv) {
        this.pdv = pdv;
    }

    public Double getBesplatniMinutiFiksna() {
        return besplatniMinutiFiksna;
    }

    public void setBesplatniMinutiFiksna(Double besplatniMinutiFiksna) {
        this.besplatniMinutiFiksna = besplatniMinutiFiksna;
    }
}
