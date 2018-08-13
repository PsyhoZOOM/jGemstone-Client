package net.yuvideo.jgemstone.client.classes.Administration.Radius;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class RadiusUsers extends RecursiveTreeObject<RadiusUsers> {

  int id;
  int br;
  String username;
  String attribute;
  String op;
  String value;


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

  public String getAttribute() {
    return attribute;
  }

  public void setAttribute(String attribute) {
    this.attribute = attribute;
  }

  public String getOp() {
    return op;
  }

  public void setOp(String op) {
    this.op = op;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
