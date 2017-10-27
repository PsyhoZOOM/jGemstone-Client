package net.yuvideo.jgemstone.client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.web.WebView;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Users;
import net.yuvideo.jgemstone.client.classes.ugovori_types;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 4/4/17.
 */
public class UgovorStampaController implements Initializable {
    public WebView browser;
    public Client client;
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
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        PrinterJob printerJob = PrinterJob.createPrinterJob(printer);
        printerJob.getJobSettings().setPageLayout(pageLayout);
        if (printerJob != null) {
            if (printerJob.showPrintDialog(browser.getScene().getWindow())) {
                browser.getEngine().print(printerJob);
                printerJob.endJob();
            }
        }
    }

    public void scaleDown(ActionEvent actionEvent) {
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
}
