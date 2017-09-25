package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zoom on 8/23/16.
 */
public class Users implements Serializable {
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
    String adresa_usluge;
    String mesto_usluge;
    String fiksni;
    String mobilni;
    String ostalo;
    String komentar;
    List services;
    String jMesto;
    String jAdresa;
    String jAdresaBroj;
    String jbroj;

    public String getJbroj() {
        return jbroj;
    }

    public void setJbroj(String jbroj) {
        this.jbroj = jbroj;
    }

    public String getMesto_usluge() {
        return mesto_usluge;
    }

    public void setMesto_usluge(String mesto_usluge) {
        this.mesto_usluge = mesto_usluge;
    }

    public String getAdresa_usluge() {
        return adresa_usluge;
    }

    public void setAdresa_usluge(String adresa_usluge) {
        this.adresa_usluge = adresa_usluge;
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

    public String getDatum_rodjenja() {
        return datum_rodjenja;
    }

    public void setDatum_rodjenja(String datum_rodjenja) {
        this.datum_rodjenja = datum_rodjenja;
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


    @Override
    public String toString() {
        return jMesto + "" + jAdresa + "" + id + ", " + ime;
    }
}
