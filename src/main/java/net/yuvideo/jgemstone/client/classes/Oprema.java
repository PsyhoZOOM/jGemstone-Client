package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

/**
 * Created by zoom on 1/13/17.
 */
public class Oprema implements Serializable {

  String naziv;
  String model;
  int id;

  public String getNaziv() {
    return naziv;
  }

  public void setNaziv(String naziv) {
    this.naziv = naziv;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return naziv;
  }
}
