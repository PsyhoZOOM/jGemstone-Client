package JGemstone.classes;

import java.io.Serializable;

/**
 * Created by zoom on 9/2/16.
 */
public class Services implements Serializable {
    int id;
    int br;
    int service_id;
    String naziv;
    Double cena;
    String opis;


    public int getBr() {
        return br;
    }

    public void setBr(int br) {
        this.br = br;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
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
}
