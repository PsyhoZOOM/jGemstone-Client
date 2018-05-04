package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

/**
 * Created by zoom on 2/14/17.
 */
public class UplateMesta implements Serializable {

  int id;
  int mesto;
  String mestoUplate;


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getMesto() {
    return mesto;
  }

  public void setMesto(int mesto) {
    this.mesto = mesto;
  }

  public String getMestoUplate() {
    return mestoUplate;
  }

  public void setMestoUplate(String mestoUplate) {
    this.mestoUplate = mestoUplate;
  }

  @Override
  public String toString() {
    return mestoUplate;
  }
}
