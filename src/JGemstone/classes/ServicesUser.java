package JGemstone.classes;

import java.io.Serializable;

/**
 * Created by zoom on 2/9/17.
 */
public class ServicesUser implements Serializable {
    int id;
    String naziv;
    String datum;
    String vrsta;
    int brUgovora;
    String operater;
    Double cena;
    Double popust;
    boolean aktivan;
    boolean obracun;
    String idUniqueName;
    int produzenje;
    int id_ServiceUser;
    int id_Service;
    String nazivPaketa;

    public String getNazivPaketa() {
        return nazivPaketa;
    }

    public void setNazivPaketa(String nazivPaketa) {
        this.nazivPaketa = nazivPaketa;
    }

    public int getId_Service() {
        return id_Service;
    }

    public void setId_Service(int id_Service) {
        this.id_Service = id_Service;
    }

    public int getId_ServiceUser() {
        return id_ServiceUser;
    }

    public void setId_ServiceUser(int id_ServiceUser) {
        this.id_ServiceUser = id_ServiceUser;
    }

    public Double getCena() {

        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public Double getPopust() {
        return popust;
    }

    public void setPopust(Double popust) {
        this.popust = popust;
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

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getVrsta() {
        return vrsta;
    }

    public void setVrsta(String vrsta) {
        this.vrsta = vrsta;
    }

    public int getBrUgovora() {
        return brUgovora;
    }

    public void setBrUgovora(int brUgovora) {
        this.brUgovora = brUgovora;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {

        this.operater = operater;
    }

    public boolean getAktivan() {
        return aktivan;
    }

    public void setAktivan(boolean aktivan) {
        this.aktivan = aktivan;
    }

    public boolean getObracun() {
        return obracun;
    }

    public void setObracun(boolean obracun) {
        this.obracun = obracun;
    }

    public String getIdUniqueName() {
        return idUniqueName;
    }

    public void setIdUniqueName(String idUniqueName)

    {
        this.idUniqueName = idUniqueName;
    }

    public int getProduzenje() {
        return produzenje;
    }

    public void setProduzenje(int produzenje) {
        this.produzenje = produzenje;
    }
}
