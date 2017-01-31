package JGemstone.classes;

import java.io.Serializable;

/**
 * Created by zoom on 9/7/16.
 */
public class Uplate implements Serializable {
    int id;
    int br;
    String username;
    String grupa;
    String datumZaduzenja;
    String datumUplate;
    String za_mesec;
    String godina;
    Double uplaceno;
    String operater;
    String serviceName;
    Double zaUplatu;
    Double punaCena;
    Double popustPerc;

    String title;


    boolean payed;
    int serviceID;
    int userID;

    public Uplate(String title) {
        this.datumZaduzenja = title;
    }

    public Uplate() {

    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    public int getServiceID() {
        return serviceID;
    }

    public void setServiceID(int serviceID) {
        this.serviceID = serviceID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Double getPopustPerc() {
        return popustPerc;
    }

    public void setPopustPerc(Double popustPerc) {
        this.popustPerc = popustPerc;
    }

    public Double getPunaCena() {
        return punaCena;
    }

    public void setPunaCena(Double punaCena) {
        this.punaCena = punaCena;
    }

    public Double getZaUplatu() {
        return zaUplatu;
    }

    public void setZaUplatu(Double zaUplatu) {
        this.zaUplatu = zaUplatu;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDatumUplate() {
        return datumUplate;
    }

    public void setDatumUplate(String datumUplate) {
        this.datumUplate = datumUplate;
    }

    public String getGodina() {
        return godina;
    }

    public void setGodina(String godina) {
        this.godina = godina;
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

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }

    public String getDatumZaduzenja() {
        return datumZaduzenja;
    }

    public void setDatumZaduzenja(String datumZaduzenja) {
        this.datumZaduzenja = datumZaduzenja;
    }

    public String getZa_mesec() {
        return za_mesec;
    }

    public void setZa_mesec(String za_mesec) {
        this.za_mesec = za_mesec;
    }

    public Double getUplaceno() {
        return uplaceno;
    }

    public void setUplaceno(Double uplaceno) {
        this.uplaceno = uplaceno;

    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }

    @Override
    public String toString() {
        return this.datumZaduzenja;
    }
}


