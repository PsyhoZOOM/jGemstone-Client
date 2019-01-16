package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.text.Font;
import javafx.scene.web.WebView;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Users;
import net.yuvideo.jgemstone.client.classes.ugovori_types;
import org.json.JSONObject;

/**
 * Created by zoom on 4/4/17.
 */
public class UgovorStampaController implements Initializable {

  public WebView browser;
  private Client client;
  public ugovori_types ugovor;
  JSONObject jObj;
  URL location;
  ResourceBundle resources;
  int ugovorID;
  Users user;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
    Font.loadFont(ClassLoader.getSystemResource("font/roboto/Roboto-Regular.ttf").toExternalForm(),
        8);
  }


  public void show_data() {
    browser.getEngine().loadContent(get_ugovor(ugovorID));

  }

  private String get_ugovor(int ugovorID) {
    jObj = new JSONObject();
    jObj.put("action", "get_ugovor_user");
    jObj.put("ugovorID", ugovor.getId());

    jObj = client.send_object(jObj);
    return jObj.getString("textUgovora");
  }


  public void printContent(ActionEvent actionEvent) {
    //Printer printer = Printer.getDefaultPrinter();
    ////PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
    //PrinterJob printerJob = PrinterJob.createPrinterJob(printer);
    //printerJob.getJobSettings().setPageLayout(pageLayout);

    ObservableSet<Printer> allPrinters = Printer.getAllPrinters();
    Printer pri = null;
    for (Printer pr : allPrinters) {
      System.out.println(pr.getName());
      pri = pr;
    }

    PrinterJob printerJob = PrinterJob.createPrinterJob(pri);
    boolean b = printerJob.showPrintDialog(browser.getScene().getWindow());
    PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();
    pageLayout = pri.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 75, 75, 75, 75);
    printerJob.getJobSettings().setPageLayout(pageLayout);

    if (printerJob == null && !printerJob.showPrintDialog(browser.getScene().getWindow())) {
      AlertUser.error("GRESKA", "NEMA ŠTAMPAČA");
      return;
    }
    browser.getEngine().print(printerJob);
    printerJob.endJob();

  }

  public void scaleDown(ActionEvent
      actionEvent) {
    browser.setScaleX(browser.getScaleX() / 2);
    browser.setScaleY(browser.getScaleY() / 2);
  }

  public void scaleUp(ActionEvent actionEvent) {
    browser.setScaleX(browser.getScaleX() * 2);
    browser.setScaleY(browser.getScaleY() * 2);

  }

  public void scaleFull(ActionEvent actionEvent) {
    browser.setScaleX(1);
    browser.setScaleY(1);
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
