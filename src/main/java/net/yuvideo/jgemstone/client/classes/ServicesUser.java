package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

/**
 * Created by zoom on 2/9/17.
 */
public class ServicesUser implements Serializable {
    int id;
    String naziv;
    String datum;
    String vrsta;
    String brUgovora;
    String operater;
    Double cena;
    Double pdv;
    Double popust;
    Double zaUplatu;
    boolean aktivan;
    boolean obracun;
    String idUniqueName;
    int produzenje;
    int id_ServiceUser;
    int id_Service;
    String nazivPaketa;
    Boolean box;
    int userID;
    String userName;
    String idDTVCard;
    String IPTV_MAC;
    String FIKSNA_TEL;
    String groupName;
    String paketType;
    Boolean linkedService;
    int box_id;
    Boolean newService;
    int DTVPaketID;
    String STB_MAC;
    String IPTV_EXT_ID;
    private String nazivIPTV;
    private String endDate;

    public Double getZaUplatu() {
        return zaUplatu;
    }

    public void setZaUplatu(Double zaUplatu) {
        this.zaUplatu = zaUplatu;
    }


    public Double getPdv() {
        return pdv;
    }

    public void setPdv(Double pdv) {
        this.pdv = pdv;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getIPTV_EXT_ID() {
        return IPTV_EXT_ID;
    }

    public void setIPTV_EXT_ID(String IPTV_EXT_ID) {
        this.IPTV_EXT_ID = IPTV_EXT_ID;
    }

    public String getSTB_MAC() {
        return STB_MAC;
    }

    public void setSTB_MAC(String STB_MAC) {
        this.STB_MAC = STB_MAC;
    }

    public int getDTVPaketID() {
        return DTVPaketID;
    }

    public void setDTVPaketID(int DTVPaketID) {
        this.DTVPaketID = DTVPaketID;
    }

    public Boolean getNewService() {
        return newService;
    }

    public void setNewService(Boolean newService) {
        this.newService = newService;
    }

    public int getBox_id() {
        return box_id;
    }

    public void setBox_id(int box_id) {
        this.box_id = box_id;
    }

    public Boolean getLinkedService() {
        return linkedService;
    }

    public void setLinkedService(Boolean linkedService) {
        this.linkedService = linkedService;
    }

    public String getPaketType() {
        return paketType;
    }

    public void setPaketType(String paketType) {
        this.paketType = paketType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdDTVCard() {
        return idDTVCard;
    }

    public void setIdDTVCard(String idDTVCard) {
        this.idDTVCard = idDTVCard;
    }

    public String getIPTV_MAC() {
        return IPTV_MAC;
    }

    public void setIPTV_MAC(String IPTV_MAC) {
        this.IPTV_MAC = IPTV_MAC;
    }

    public String getFIKSNA_TEL() {
        return FIKSNA_TEL;
    }

    public void setFIKSNA_TEL(String FIKSNA_TEL) {
        this.FIKSNA_TEL = FIKSNA_TEL;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public boolean isAktivan() {
        return aktivan;
    }

    public boolean isObracun() {
        return obracun;
    }

    public Boolean getBox() {
        return box;
    }

    public void setBox(Boolean box) {
        this.box = box;
    }

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

    public String getBrUgovora() {
        return brUgovora;
    }

    public void setBrUgovora(String brUgovora) {
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

    public String getNazivIPTV() {
        return nazivIPTV;
    }

    public void setNazivIPTV(String nazivIPTV) {
        this.nazivIPTV = nazivIPTV;
    }
}
