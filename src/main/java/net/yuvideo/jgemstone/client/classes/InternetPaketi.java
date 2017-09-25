package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

/**
 * Created by zoom on 1/31/17.
 */
public class InternetPaketi implements Serializable {
    int id;
    String naziv;
    String brzina;
    Double cena;
    String opis;
    String idleTimeout;
    int prekoracenje;
    boolean obracun = true;

    public String getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(String idleTimeout) {
        this.idleTimeout = idleTimeout;
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

    public String getBrzina() {
        return brzina;
    }

    public void setBrzina(String brzina) {
        this.brzina = brzina;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public int getPrekoracenje() {
        return prekoracenje;
    }

    public void setPrekoracenje(int prekoracenje) {
        this.prekoracenje = prekoracenje;
    }

    public boolean isObracun() {
        return obracun;
    }

    public void setObracun(boolean obracun) {
        this.obracun = obracun;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
