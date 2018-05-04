package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

/**
 * Created by zoom on 1/31/17.
 */
public class OperatersDozvole implements Serializable {

  int id;
  int operaterID;
  String dozvola;
  Boolean value;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getOperaterID() {
    return operaterID;
  }

  public void setOperaterID(int operaterID) {
    this.operaterID = operaterID;
  }

  public String getDozvola() {
    return dozvola;
  }

  public void setDozvola(String dozvola) {
    this.dozvola = dozvola;
  }

  public Boolean getValue() {
    return value;
  }

  public void setValue(Boolean value) {
    this.value = value;
  }
}
