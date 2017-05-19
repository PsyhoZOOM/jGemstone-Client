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
    Boolean box;
    int userID;
    String userName;
    String idDTVCard;
    String MAC_IPTV;
    String FIKSNA_TEL;
    String groupName;
    String paketType;
    Boolean linkedService;
    int box_id;
    Boolean newService;
    int DTVPaketID;


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

    public String getMAC_IPTV() {
        return MAC_IPTV;
    }

    public void setMAC_IPTV(String MAC_IPTV) {
        this.MAC_IPTV = MAC_IPTV;
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