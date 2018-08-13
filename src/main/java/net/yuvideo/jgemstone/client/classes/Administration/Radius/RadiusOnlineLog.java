package net.yuvideo.jgemstone.client.classes.Administration.Radius;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class RadiusOnlineLog extends RecursiveTreeObject<RadiusOnlineLog> {

  int id;
  String username;
  String authdate;
  String reply;
  String clientid;
  String nas;


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

  public String getAuthdate() {
    return authdate;
  }

  public void setAuthdate(String authdate) {
    this.authdate = authdate;
  }

  public String getReply() {
    return reply;
  }

  public void setReply(String reply) {
    this.reply = reply;
  }

  public String getClientid() {
    return clientid;
  }

  public void setClientid(String clientid) {
    this.clientid = clientid;
  }

  public String getNas() {
    return nas;
  }

  public void setNas(String nas) {
    this.nas = nas;
  }
}
