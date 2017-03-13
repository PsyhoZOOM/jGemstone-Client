package JGemstone.classes;

import java.io.Serializable;

/**
 * Created by zoom on 2/24/17.
 */
public class BoxPaket implements Serializable {
    int id;
    String naziv;
    int DTVPaketID;
    int IPTVPaketID;
    int NETPaketID;
    int TELPaketID;
    String opis;
    String username;
    String password;
    String iptvmac;
    int popust;
    int ugovorid;
    int produzenje;
    double cena;
    int obracune;
    String paketType;

    private int DTV;
    private String DTV_naziv;
    private int IPTV;
    private String IPTV_naziv;
    private int NET;
    private String NET_naziv;
    private int FIKSNA;
    private String FIKSNA_naziv;


    public String getPaketType() {
        return paketType;
    }

    public void setPaketType(String paketType) {
        this.paketType = paketType;
    }

    public int getDTV() {
        return DTV;
    }

    public void setDTV(int DTV) {
        this.DTV = DTV;
    }

    public String getDTV_naziv() {
        return DTV_naziv;
    }

    public void setDTV_naziv(String DTV_naziv) {
        this.DTV_naziv = DTV_naziv;
    }

    public int getIPTV() {
        return IPTV;
    }

    public void setIPTV(int IPTV) {
        this.IPTV = IPTV;
    }

    public String getIPTV_naziv() {
        return IPTV_naziv;
    }

    public void setIPTV_naziv(String IPTV_naziv) {
        this.IPTV_naziv = IPTV_naziv;
    }

    public int getNET() {
        return NET;
    }

    public void setNET(int NET) {
        this.NET = NET;
    }

    public String getNET_naziv() {
        return NET_naziv;
    }

    public void setNET_naziv(String NET_naziv) {
        this.NET_naziv = NET_naziv;
    }

    public int getFIKSNA() {
        return FIKSNA;
    }

    public void setFIKSNA(int FIKSNA) {
        this.FIKSNA = FIKSNA;
    }

    public String getFIKSNA_naziv() {
        return FIKSNA_naziv;
    }

    public void setFIKSNA_naziv(String FIKSNA_naziv) {
        this.FIKSNA_naziv = FIKSNA_naziv;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public int getObracune() {
        return obracune;
    }

    public void setObracune(int obracune) {
        this.obracune = obracune;
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

    public int getDTVPaketID() {
        return DTVPaketID;
    }

    public void setDTVPaketID(int DTVPaketID) {
        this.DTVPaketID = DTVPaketID;
    }

    public int getIPTVPaketID() {
        return IPTVPaketID;
    }

    public void setIPTVPaketID(int IPTVPaketID) {
        this.IPTVPaketID = IPTVPaketID;
    }

    public int getNETPaketID() {
        return NETPaketID;
    }

    public void setNETPaketID(int NETPaketID) {
        this.NETPaketID = NETPaketID;
    }

    public int getTELPaketID() {
        return TELPaketID;
    }

    public void setTELPaketID(int TELPaketID) {
        this.TELPaketID = TELPaketID;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIptvmac() {
        return iptvmac;
    }

    public void setIptvmac(String iptvmac) {
        this.iptvmac = iptvmac;
    }

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
    }

    public int getUgovorid() {
        return ugovorid;
    }

    public void setUgovorid(int ugovorid) {
        this.ugovorid = ugovorid;
    }

    public int getProduzenje() {
        return produzenje;
    }

    public void setProduzenje(int produzenje) {
        this.produzenje = produzenje;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
