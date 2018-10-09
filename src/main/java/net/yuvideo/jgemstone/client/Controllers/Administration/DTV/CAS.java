package net.yuvideo.jgemstone.client.Controllers.Administration.DTV;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.ArrayList;
import net.yuvideo.jgemstone.client.classes.Client;
import org.json.JSONObject;

public class CAS extends RecursiveTreeObject<CAS> {

  int paketID;
  int code;
  ArrayList<CAS> casArrayList = new ArrayList<>();
  private int id;

  public ArrayList<CAS> getCasArrayList(Client client) {
    JSONObject object = new JSONObject();
    object.put("action", "getAllCAS");
    object = client.send_object(object);

    for (int i = 0; i < object.length(); i++) {
      JSONObject casO = object.getJSONObject(String.valueOf(i));
      CAS cas = new CAS();
      cas.setId(casO.getInt("id"));
      cas.setCode(casO.getInt("code"));
      cas.setPaketID(casO.getInt("paketID"));
      casArrayList.add(cas);
    }

    return casArrayList;
  }

  public void setCasArrayList(
      ArrayList<CAS> casArrayList) {
    this.casArrayList = casArrayList;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getPaketID() {
    return paketID;
  }

  public void setPaketID(int paketID) {
    this.paketID = paketID;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }
}
