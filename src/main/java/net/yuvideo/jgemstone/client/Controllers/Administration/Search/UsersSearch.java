package net.yuvideo.jgemstone.client.Controllers.Administration.Search;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class UsersSearch extends RecursiveTreeObject<UsersSearch> {

  int id;
  String jbroj;
  String username;
  String endDate;
  String adresaUsluge;
  String ime;
  String groupName;


  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getJbroj() {
    return jbroj;
  }

  public void setJbroj(String jbroj) {
    this.jbroj = jbroj;
  }

  public String getIme() {
    return ime;
  }

  public void setIme(String ime) {
    this.ime = ime;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public String getAdresaUsluge() {
    return adresaUsluge;
  }

  public void setAdresaUsluge(String adresaUsluge) {
    this.adresaUsluge = adresaUsluge;
  }
}
