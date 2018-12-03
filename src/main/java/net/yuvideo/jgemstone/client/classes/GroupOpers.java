package net.yuvideo.jgemstone.client.classes;

import java.util.ArrayList;

public class GroupOpers {

  int id;
  String groupName;
  Client client;
  ArrayList<Operaters> operatersArrayList = new ArrayList<>();


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public ArrayList<Operaters> getOperatersArrayList() {
    return operatersArrayList;
  }

  public void setOperatersArrayList(
      ArrayList<Operaters> operatersArrayList) {
    this.operatersArrayList = operatersArrayList;
  }
}
