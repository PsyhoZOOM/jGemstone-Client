package JGemstone.classes;

import java.io.Serializable;

/**
 * Created by zoom on 11/9/16.
 */
public class ugovori_types implements Serializable {
    private int id;
    private String br;
    private String Naziv;
    private String Vrsta;
    private String textUgovora;
    private String Komentar;
    private String pocetakUgovora;
    private String krajUgovora;
    private int userID;
    private int serviceID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBr() {
        return br;
    }

    public void setBr(String br) {
        this.br = br;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setNaziv(String naziv) {
        Naziv = naziv;
    }

    public String getVrsta() {
        return Vrsta;
    }

    public void setVrsta(String vrsta) {
        Vrsta = vrsta;
    }

    public String getTextUgovora() {
        return textUgovora;
    }

    public void setTextUgovora(String textUgovora) {
        this.textUgovora = textUgovora;
    }

    public String toString() {
        return Naziv;
    }


    public String getKomentar() {
        return Komentar;
    }

    public void setKomentar(String komentar) {
        Komentar = komentar;
    }


    public String getPocetakUgovora() {
        return pocetakUgovora;
    }

    public void setPocetakUgovora(String pocetakUgovora) {
        this.pocetakUgovora = pocetakUgovora;
    }

    public String getKrajUgovora() {
        return krajUgovora;
    }

    public void setKrajUgovora(String krajUgovora) {
        this.krajUgovora = krajUgovora;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }
}
