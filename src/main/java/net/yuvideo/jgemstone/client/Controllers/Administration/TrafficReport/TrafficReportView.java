package net.yuvideo.jgemstone.client.Controllers.Administration.TrafficReport;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import net.yuvideo.jgemstone.client.Controllers.Administration.Radius.TrafficReport;
import net.yuvideo.jgemstone.client.classes.Client;

public class TrafficReportView implements Initializable {

  public JFXTextField tSearch;
  public JFXTreeTableView<TrafficReport> tblTrafficReport;
  public String username;
  private URL location;
  private ResourceBundle resources;
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    JFXTreeTableColumn<TrafficReport, String> cUserName = new JFXTreeTableColumn<>(
        "Korisnicko ime");
    JFXTreeTableColumn<TrafficReport, String> cIPAddress = new JFXTreeTableColumn<>("IP Adresa");
    JFXTreeTableColumn<TrafficReport, String> cMACAdresa = new JFXTreeTableColumn<>("MAC Adresa");
    JFXTreeTableColumn<TrafficReport, String> cNASIPAddress = new JFXTreeTableColumn<>(
        "NAS IP Adresa");
    JFXTreeTableColumn<TrafficReport, String> cService = new JFXTreeTableColumn<>("Service");
    JFXTreeTableColumn<TrafficReport, Long> cDownload = new JFXTreeTableColumn<>("Download");
    JFXTreeTableColumn<TrafficReport, Long> cUplaod = new JFXTreeTableColumn<>("Upload");
    JFXTreeTableColumn<TrafficReport, Integer> cOnlineTime = new JFXTreeTableColumn<>(
        "Online Vreme");
    JFXTreeTableColumn<TrafficReport, String> cStartTime = new JFXTreeTableColumn<>("Start Vreme");
    JFXTreeTableColumn<TrafficReport, String> cStopTime = new JFXTreeTableColumn<>("Stop Vreme");

    cUserName
        .setCellValueFactory(new TreeItemPropertyValueFactory<TrafficReport, String>("username"));
    cIPAddress.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, String>("framedipaddress"));
    cMACAdresa.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, String>("callingstationid"));
    cNASIPAddress.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, String>("nasipaddress"));
    cService.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, String>("calledstationid"));
    cDownload.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, Long>("acctinputoctets"));
    cUplaod.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, Long>("acctoutputoctets"));
    cOnlineTime.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, Integer>("acctsessiontime"));
    cStartTime.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, String>("acctstarttime"));
    cStopTime.setCellValueFactory(
        new TreeItemPropertyValueFactory<TrafficReport, String>("acctstoptime"));

    tblTrafficReport.getColumns()
        .addAll(cUserName, cIPAddress, cMACAdresa, cNASIPAddress, cService, cDownload, cUplaod,
            cOnlineTime,
            cStartTime, cStopTime);
    tblTrafficReport.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

    tSearch.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        tblTrafficReport.setPredicate(new Predicate<TreeItem<TrafficReport>>() {
          @Override
          public boolean test(TreeItem<TrafficReport> trafficReportTreeItem) {
            tblTrafficReport.getSelectionModel().clearSelection();
            TrafficReport value = trafficReportTreeItem.getValue();
            boolean flag = value.getUsername().contains(newValue) || value.getFramedipaddress()
                .contains(newValue) ||
                value.getCallingstationid().contains(newValue) || value.getCalledstationid()
                .contains(newValue) ||
                value.getConnectinfo_start().contains(newValue) || value.getConnectinfo_stop()
                .contains(newValue);
            return flag;
          }
        });
      }
    });


  }

  private ArrayList<TrafficReport> getTraffic(String username) {
    TrafficReport trafficReport = new TrafficReport(this.client);
    ArrayList<TrafficReport> trafficReportArrayList = trafficReport
        .getTrafficReportArrayList(username);

    return trafficReportArrayList;
  }

  public void initData() {
    fillTable(this.username);
  }


  private void fillTable(String username) {
    ObservableList<TrafficReport> trafficReportObservableList = FXCollections
        .observableList(getTraffic(username));
    TreeItem<TrafficReport> root = new RecursiveTreeItem<>(trafficReportObservableList,
        RecursiveTreeObject::getChildren);

    //  TreeItem<TrafficReport> root = new TreeItem("ROOT");
    // ArrayList<TrafficReport> traffic = getTraffic(username);
    //for(TrafficReport trafficReport : trafficReportObservableList){
    //    root.getChildren().add(new TreeItem<>(trafficReport));
//    }
    tblTrafficReport.setShowRoot(false);
    tblTrafficReport.setRoot(root);
    tblTrafficReport.getRoot().setExpanded(true);
  }


  public void setClient(Client client) {
    this.client = client;
  }
}
