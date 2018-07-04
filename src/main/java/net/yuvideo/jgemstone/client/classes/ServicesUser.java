package net.yuvideo.jgemstone.client.classes;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.Serializable;
import java.util.ArrayList;
import org.json.JSONObject;

/**
 * Created by zoom on 2/9/17.
 */
public class ServicesUser extends RecursiveTreeObject<ServicesUser> implements Serializable {

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
  private ArrayList<ServicesUser> boxServices;
  private String komentar;
  private String opis;


  public ArrayList<ServicesUser> getUserServiceArr(int userID, Client client) {

    JSONObject jObj = new JSONObject();
    jObj.put("action", "get_user_services");
    jObj.put("userID", userID);
    jObj = client.send_object(jObj);

    JSONObject userServiceObj;
    ArrayList<ServicesUser> userServiceArr = new ArrayList();
    ServicesUser userService;

    for (int i = 0; i < jObj.length(); i++) {
      userServiceObj = (JSONObject) jObj.get(String.valueOf(i));
      userService = new ServicesUser();
      userService.setId(userServiceObj.getInt("id"));
      userService.setUserID(userServiceObj.getInt("userID"));
      userService.setBrUgovora(userServiceObj.getString("brojUgovora"));
      userService.setCena(userServiceObj.getDouble("cena"));
      userService.setPopust(userServiceObj.getDouble("popust"));
      userService.setOperater(userServiceObj.getString("operName"));
      userService.setDatum(userServiceObj.getString("date_added"));
      userService.setNazivPaketa(userServiceObj.getString("nazivPaketa"));
      userService.setNaziv(userServiceObj.getString("nazivPaketa"));
      userService.setAktivan(userServiceObj.getBoolean("aktivan"));
      if (userServiceObj.has("idDTVCard")) {
        userService.setIdDTVCard(userServiceObj.getString("idUniqueName"));
      }
      userService.setObracun(userServiceObj.getBoolean("obracun"));
      userService.setAktivan(userServiceObj.getBoolean("aktivan"));
      userService.setProduzenje(userServiceObj.getInt("produzenje"));
      if (userServiceObj.has("id_service")) {
        userService.setId_Service(userServiceObj.getInt("id_service"));
      }
      if (userServiceObj.has("box")) {
        userService.setBox(userServiceObj.getBoolean("box"));
      } else {
        userService.setBox(false);
      }
      if (userServiceObj.has("box_id")) {
        userService.setBox_id(userServiceObj.getInt("box_id"));
      }
      if (userService.getBox()) {
        userService.setBoxServices(addBoxServices(userServiceObj.getInt("id"), userID, client));
      }
      if (userServiceObj.has("IPTV_MAC")) {
        userService.setIPTV_MAC(userServiceObj.getString("IPTV_MAC"));
        userService.setSTB_MAC(userServiceObj.getString("IPTV_MAC"));
      }
      userService.setPaketType(userServiceObj.getString("paketType"));
      userService.setLinkedService(userServiceObj.getBoolean("linkedService"));
      userService.setNewService(userServiceObj.getBoolean("newService"));
      userService.setDTVPaketID(userServiceObj.getInt("DTVPaketID"));
      userService.setPopust(userServiceObj.getDouble("popust"));
      userService.setPdv(userServiceObj.getDouble("pdv"));
      userService.setKomentar(userServiceObj.getString("komentar"));
      userService.setOpis(userServiceObj.getString("opis"));
      userService.setUserName(userServiceObj.getString("userName"));
      userServiceArr.add(userService);
    }

    return userServiceArr;
  }

  private ArrayList<ServicesUser> addBoxServices(int box_id, int userID, Client client) {
    ArrayList<ServicesUser> servicesBox = new ArrayList<>();
    JSONObject object = new JSONObject();
    object.put("action", "get_user_linked_services");
    object.put("box_ID", box_id);
    object.put("userID", userID);
    object = client.send_object(object);
    for (int i = 0; i < object.length(); i++) {
      JSONObject servObj = object.getJSONObject(String.valueOf(i));
      ServicesUser servicesUser = new ServicesUser();
      servicesUser.setId(servObj.getInt("id"));
      servicesUser.setUserID(servObj.getInt("userID"));
      servicesUser.setId_Service(servObj.getInt("id_service"));
      servicesUser.setBox_id(servObj.getInt("box_ID"));
      servicesUser.setNazivPaketa(servObj.getString("nazivPaketa"));
      servicesUser.setNaziv(servObj.getString("naziv"));
      servicesUser.setProduzenje(servObj.getInt("produzenje"));
      servicesUser.setGroupName(servObj.getString("groupName"));
      servicesUser.setUserName(servObj.getString("userName"));
      servicesUser.setIdDTVCard(servObj.getString("idDTVCard"));
      servicesUser.setIPTV_MAC(servObj.getString("IPTV_MAC"));
      servicesUser.setSTB_MAC(servObj.getString("STB_MAC"));
      servicesUser.setFIKSNA_TEL(servObj.getString("FIKSNA_TEL"));
      servicesUser.setPopust(servObj.getDouble("popust"));
      servicesUser.setCena(servObj.getDouble("cena"));
      servicesUser.setPdv(servObj.getDouble("pdv"));
      servicesUser.setLinkedService(servObj.getBoolean("linkedService"));
      servicesUser.setAktivan(servObj.getBoolean("aktivan"));
      servicesUser.setEndDate(servObj.getString("endDate"));
      servicesUser.setPaketType(servObj.getString("paketType"));
      servicesUser.setNewService(servObj.getBoolean("newService"));
      servicesUser.setKomentar(servObj.getString("komentar"));
      servicesUser.setOpis(servObj.getString("opis"));
      servicesUser.setUserName(servObj.getString("userName"));
      servicesBox.add(servicesUser);

    }
    return servicesBox;

  }


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

  public ArrayList<ServicesUser> getBoxServices() {
    return boxServices;
  }

  public void setBoxServices(ArrayList<ServicesUser> boxServices) {
    this.boxServices = boxServices;
  }

  @Override
  public String toString() {
    String paketType = null;
    if (this.paketType.contains("DTV")) {
      paketType = "DTV";
    }
    if (this.paketType.contains("NET")) {
      paketType = "NET";
    }
    if (this.paketType.contains("BOX")) {
      paketType = "BOX";
    }
    if (this.paketType.contains("IPTV")) {
      paketType = "IPTV";
    }
    if (this.paketType.contains("FIX")) {
      paketType = "FIKSNA TEL.";
    }

    return String.format("%s (%s)", this.naziv, paketType);
  }

  public String getKomentar() {
    return komentar;
  }

  public void setKomentar(String komentar) {
    this.komentar = komentar;
  }


  public String getOpis() {
    return opis;
  }

  public void setOpis(String opis) {
    this.opis = opis;
  }
}
