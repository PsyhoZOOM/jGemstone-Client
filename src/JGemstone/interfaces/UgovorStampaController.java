package JGemstone.interfaces;

import JGemstone.classes.Client;
import JGemstone.classes.Users;
import JGemstone.classes.ugovori_types;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.web.WebView;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 4/4/17.
 */
public class UgovorStampaController implements Initializable {
    public WebView browser;
    public Client client;
    public

    JSONObject jObj;
    public ugovori_types ugovor;
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
            System.out.println(printerJob.getJobSettings().getPageLayout());
            browser.getEngine().print(printerJob);
            printerJob.endJob();
        }

    }
}
