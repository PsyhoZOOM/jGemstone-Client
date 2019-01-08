package net.yuvideo.jgemstone.client.Controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.ugovori_types;
import org.json.JSONObject;


/**
 * Created by zoom on 11/16/16.
 */
public class UgovoriEditController implements Initializable {

  public WebView webV;
  private Client client;
  public ResourceBundle resource;
  public HTMLEditor htmlUgovor;
  public Button bSnimi;
  public Button bClose;
  public TextField tNazivUgovora;
  int type;
  ugovori_types Ugovor;
  JSONObject jObj;
  private Stage stage;
  private PageLayout pl = null;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.resource = resources;
    bClose.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

      }
    });

    bSnimi.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        snimiUgovor(null);
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

      }
    });


  }

  public void show_Data() {
    if (type == 2) {
      jObj = new JSONObject();
      jObj.put("action", "get_single_ugovor");
      jObj.put("idUgovora", Ugovor.getId());
      jObj = client.send_object(jObj);

      Ugovor = new ugovori_types();
      Ugovor.setId(jObj.getInt("idUgovora"));
      Ugovor.setNaziv(jObj.getString("nazivUgovora"));
      Ugovor.setTextUgovora(jObj.getString("textUgovora"));
      tNazivUgovora.setText(Ugovor.getNaziv());

      htmlUgovor.setHtmlText(Ugovor.getTextUgovora());
      htmlUgovor.setStyle("-fx-line-height: 1");


    }
  }

  public void snimiUgovor(ActionEvent actionEvent) {

    if (type == 1) {
      snimiNovUgovor();
    }
    if (type == 2) {
      snimiEditUgovor();
      show_Data();
    }

  }

  private void snimiNovUgovor() {

    jObj = new JSONObject();
    jObj.put("action", "add_new_ugovor");
    jObj.put("nazivUgovora", tNazivUgovora.getText());
    jObj.put("textUgovora", htmlUgovor.getHtmlText());

    jObj = client.send_object(jObj);

  }

  private void snimiEditUgovor() {
    jObj = new JSONObject();
    jObj.put("action", "update_ugovor_temp");
    jObj.put("id", Ugovor.getId());
    jObj.put("text_ugovora", htmlUgovor.getHtmlText());
    jObj.put("naziv", tNazivUgovora.getText());

    jObj = client.send_object(jObj);


  }


  public void openPDFFile(ActionEvent actionEvent) {
    File pdfFile;
    FileChooser pdfFileChose = new FileChooser();
    pdfFileChose.setTitle("Izaberite PDF Fajl");
    pdfFileChose.setInitialDirectory(new File(System.getProperty("UserData.home")));
    pdfFileChose.getExtensionFilters()
        .addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf", "*.PDF"));
    pdfFile = pdfFileChose.showOpenDialog(this.stage);


  }

  public void insertData(MouseEvent mouseEvent) {
    if (mouseEvent.isSecondaryButtonDown()) {

    }
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void setUgovorPreviewText(ActionEvent actionEvent) {
    Double margin = 55.0;
    Double mDif = -10.0;
    pl = Printer.defaultPrinterProperty().get()
        .createPageLayout(Paper.A4, PageOrientation.PORTRAIT, margin, margin, margin, margin);
    Double w = pl.getPaper().getWidth();
    Double h = pl.getPaper().getHeight();
    webV.setMinSize(w - mDif, h - mDif);
    webV.setPrefSize(w - mDif, h - mDif);
    webV.setMaxSize(w - mDif, h - mDif);
    webV.getEngine().loadContent(htmlUgovor.getHtmlText());
    htmlUgovor.setPadding(new Insets(5, 5, 5, 5));
  }

  public void printUgovor(ActionEvent actionEvent) {
    ObservableSet<Printer> allPrinters = Printer.getAllPrinters();
    Printer printer = null;
    for (Printer p : allPrinters) {
      System.out.println(p.getName());
      printer = p;
    }
    PrinterJob pj = PrinterJob.createPrinterJob(printer);
    if (pj == null || !pj.showPrintDialog(bClose.getScene().getWindow())) {
      AlertUser.error("GRESKA", "NEMA STAMPACA");
      return;
    }
    pj.getJobSettings().setPageLayout(pl);
    pj.getJobSettings().pageLayoutProperty().setValue(pl);
    webV.getEngine().print(pj);
    pj.endJob();
    pj.getPrinter().getPrinterAttributes().toString();
  }
}
