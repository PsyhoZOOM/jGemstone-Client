package net.yuvideo.jgemstone.client.Controllers.Administration.Radius;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.ArrayList;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import org.json.JSONObject;

public class TrafficReport extends RecursiveTreeObject<TrafficReport> {

  private long radacctid;
  private String acctsessionid;
  private String acctuniqueid;
  private String username;
  private String realm;
  private String nasipaddress;
  private String nasportid;
  private String nasporttype;
  private String acctstarttime;
  private String acctstoptime;
  private int acctsessiontime;
  private String acctauthentic;
  private String connectinfo_start;
  private String connectinfo_stop;
  private long acctinputoctets;
  private long acctoutputoctets;
  private String calledstationid;
  private String callingstationid;
  private String acctterminatecause;
  private String servicetype;
  private String framedprotocol;
  private String framedipaddress;

  private Client client;

  private ArrayList<TrafficReport> trafficReportArrayList = new ArrayList<>();

  public TrafficReport(Client client) {
    this.client = client;
  }

  public TrafficReport() {
  }

  public ArrayList<TrafficReport> getTrafficReportArrayList(String UserName) {
    JSONObject object = new JSONObject();
    object.put("action", "getUserTrafficReport");
    object.put("username", UserName);
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return null;
    }
    trafficReportArrayList.clear();

    for (int i = 0; i < object.length(); i++) {
      JSONObject trafficObj = object.getJSONObject(String.valueOf(i));
      TrafficReport trafficReport = new TrafficReport();
      trafficReport.setRadacctid(trafficObj.getLong("radacctid"));
      trafficReport.setAcctsessionid(trafficObj.getString("acctsessionid"));
      trafficReport.setAcctuniqueid(trafficObj.getString("acctuniqueid"));
      trafficReport.setUsername(trafficObj.getString("username"));
      trafficReport.setRealm(trafficObj.getString("realm"));
      trafficReport.setNasipaddress(trafficObj.getString("nasipaddress"));
      trafficReport.setNasportid(trafficObj.getString("nasportid"));
      trafficReport.setNasporttype(trafficObj.getString("nasporttype"));
      trafficReport.setAcctstarttime(trafficObj.getString("acctstarttime"));
      trafficReport.setAcctstoptime(trafficObj.getString("acctstoptime"));
      trafficReport.setAcctsessiontime(trafficObj.getInt("acctsessiontime"));
      trafficReport.setAcctauthentic(trafficObj.getString("acctauthentic"));
      trafficReport.setConnectinfo_start(trafficObj.getString("connectinfo_start"));
      trafficReport.setConnectinfo_stop(trafficObj.getString("connectinfo_stop"));
      trafficReport.setAcctinputoctets(trafficObj.getLong("acctinputoctets"));
      trafficReport.setAcctoutputoctets(trafficObj.getLong("acctoutputoctets"));
      trafficReport.setCalledstationid(trafficObj.getString("calledstationid"));
      trafficReport.setCallingstationid(trafficObj.getString("callingstationid"));
      trafficReport.setAcctterminatecause(trafficObj.getString("acctterminatecause"));
      trafficReport.setServicetype(trafficObj.getString("servicetype"));
      trafficReport.setFramedprotocol(trafficObj.getString("framedprotocol"));
      trafficReport.setFramedipaddress(trafficObj.getString("framedipaddress"));
      trafficReportArrayList.add(trafficReport);


    }

    return trafficReportArrayList;
  }

  public String getServicetype() {
    return servicetype;
  }

  public void setServicetype(String servicetype) {
    this.servicetype = servicetype;
  }

  public String getFramedprotocol() {
    return framedprotocol;
  }

  public void setFramedprotocol(String framedprotocol) {
    this.framedprotocol = framedprotocol;
  }

  public String getFramedipaddress() {
    return framedipaddress;
  }

  public void setFramedipaddress(String framedipaddress) {
    this.framedipaddress = framedipaddress;
  }

  public long getRadacctid() {
    return radacctid;
  }

  public void setRadacctid(long radacctid) {
    this.radacctid = radacctid;
  }

  public String getAcctsessionid() {
    return acctsessionid;
  }

  public void setAcctsessionid(String acctsessionid) {
    this.acctsessionid = acctsessionid;
  }

  public String getAcctuniqueid() {
    return acctuniqueid;
  }

  public void setAcctuniqueid(String acctuniqueid) {
    this.acctuniqueid = acctuniqueid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getRealm() {
    return realm;
  }

  public void setRealm(String realm) {
    this.realm = realm;
  }

  public String getNasipaddress() {
    return nasipaddress;
  }

  public void setNasipaddress(String nasipaddress) {
    this.nasipaddress = nasipaddress;
  }

  public String getNasportid() {
    return nasportid;
  }

  public void setNasportid(String nasportid) {
    this.nasportid = nasportid;
  }

  public String getNasporttype() {
    return nasporttype;
  }

  public void setNasporttype(String nasporttype) {
    this.nasporttype = nasporttype;
  }

  public String getAcctstarttime() {
    return acctstarttime;
  }

  public void setAcctstarttime(String acctstarttime) {
    this.acctstarttime = acctstarttime;
  }

  public String getAcctstoptime() {
    return acctstoptime;
  }

  public void setAcctstoptime(String acctstoptime) {
    this.acctstoptime = acctstoptime;
  }

  public int getAcctsessiontime() {
    return acctsessiontime;
  }

  public void setAcctsessiontime(int acctsessiontime) {
    this.acctsessiontime = acctsessiontime;
  }

  public String getAcctauthentic() {
    return acctauthentic;
  }

  public void setAcctauthentic(String acctauthentic) {
    this.acctauthentic = acctauthentic;
  }

  public String getConnectinfo_start() {
    return connectinfo_start;
  }

  public void setConnectinfo_start(String connectinfo_start) {
    this.connectinfo_start = connectinfo_start;
  }

  public String getConnectinfo_stop() {
    return connectinfo_stop;
  }

  public void setConnectinfo_stop(String connectinfo_stop) {
    this.connectinfo_stop = connectinfo_stop;
  }

  public long getAcctinputoctets() {
    return acctinputoctets;
  }

  public void setAcctinputoctets(long acctinputoctets) {
    this.acctinputoctets = acctinputoctets;
  }

  public long getAcctoutputoctets() {
    return acctoutputoctets;
  }

  public void setAcctoutputoctets(long acctoutputoctets) {
    this.acctoutputoctets = acctoutputoctets;
  }

  public String getCalledstationid() {
    return calledstationid;
  }

  public void setCalledstationid(String calledstationid) {
    this.calledstationid = calledstationid;
  }

  public String getCallingstationid() {
    return callingstationid;
  }

  public void setCallingstationid(String callingstationid) {
    this.callingstationid = callingstationid;
  }

  public String getAcctterminatecause() {
    return acctterminatecause;
  }

  public void setAcctterminatecause(String acctterminatecause) {
    this.acctterminatecause = acctterminatecause;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
