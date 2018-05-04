package net.yuvideo.jgemstone.client.classes.Printing;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.Racun;

public class PrintFaktura {

  public ArrayList<Racun> userRacun;
  public boolean showPreview = false;
  private PageLayout pageLayout;
  private DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
  private PrinterJob printerJob;
  private Printer printer;
  private Paper paper;
  private PageOrientation pageOrientation;

  public void setPrinterData(JobSettings js, Printer printer) {
    this.printerJob = PrinterJob.createPrinterJob();
    this.printer = printer;
    this.pageLayout = js.getPageLayout();
    this.paper = js.getPageLayout().getPaper();
    this.pageOrientation = js.getPageLayout().getPageOrientation();
    this.pageLayout =
        this.printer.createPageLayout(
            this.paper, this.pageOrientation, Printer.MarginType.HARDWARE_MINIMUM);
    this.printerJob.setPrinter(printer);
  }

  public void printFaktura() {

    final double WIDTH = pageLayout.getPrintableWidth();
    final double HEIGHT = pageLayout.getPrintableHeight();
    final double MAX_WIDTH = paper.getWidth();
    final double MAX_HEIGHT = paper.getHeight();

    final double _C_NAZIV = 180;
    final double _C_JMERE = 50;
    final double _C_KOLICINA = 60;
    final double _C_CENA = 60;
    final double _C_STOPA_POPUST = 45;
    final double _C_STOPA_PDV = 45;
    final double _C_OSNOVICA = 60;
    final double _C_PDV = 60;
    final double _C_UKUPNO = 70;

    Font font =
        Font.loadFont(
            getClass().getResource("/font/roboto/Roboto-Regular.ttf").toExternalForm(), 10);
    Font fontSmall =
        Font.loadFont(getClass().getResource("/font/roboto/Roboto-Light.ttf").toExternalForm(), 8);
    Font fontMini =
        Font.loadFont(getClass().getResource("/font/roboto/Roboto-Thin.ttf").toExternalForm(), 6);
    Font fontBold =
        Font.loadFont(getClass().getResource("/font/roboto/Roboto-Bold.ttf").toExternalForm(), 10);
    Font fontADRESA =
        Font.loadFont(getClass().getResource("/font/roboto/Roboto-Bold.ttf").toExternalForm(), 12);
    Font fontRacunDole =
        Font.loadFont(getClass().getResource("/font/roboto/Roboto-Bold.ttf").toExternalForm(), 8);

    AnchorPane anchorPane = new AnchorPane();

    VBox table = new VBox();
    table.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 1;");
    HBox row;
    HBox cell;

    row = new HBox();
    row.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 1;");
    // NAZIV USLUGE
    Label naziv = new Label("VRSTA-NAZIV DOBARA-USLUGA");
    naziv.setFont(font);
    naziv.setWrapText(true);
    naziv.setAlignment(Pos.CENTER);
    cell = new HBox(naziv);
    cell.setAlignment(Pos.CENTER);
    cell.setMinWidth(_C_NAZIV);
    cell.setMaxWidth(_C_NAZIV);
    row.getChildren().add(cell);

    //JEDINICA MERE

    Label jedinicaMere = new Label("JEDINICA MERE");
    jedinicaMere.setWrapText(true);
    jedinicaMere.setFont(font);
    jedinicaMere.setAlignment(Pos.CENTER);
    cell = new HBox(jedinicaMere);
    cell.setMinWidth(_C_JMERE);
    cell.setMaxWidth(_C_JMERE);
    cell.setAlignment(Pos.CENTER);
    row.getChildren().add(cell);

    // KOLICINA
    Label kolicina = new Label("KOLIČINA");
    kolicina.setFont(font);
    kolicina.setAlignment(Pos.CENTER);
    cell = new HBox(kolicina);
    cell.setAlignment(Pos.CENTER);
    cell.setMinWidth(_C_KOLICINA);
    cell.setMaxWidth(_C_KOLICINA);
    row.getChildren().add(cell);

    // CENA BEZ PDV
    Label cenaBezPDV = new Label("CENA BEZ PDV");
    cenaBezPDV.setFont(font);
    cenaBezPDV.setWrapText(true);
    cenaBezPDV.setAlignment(Pos.CENTER);
    cell = new HBox(cenaBezPDV);
    cell.setAlignment(Pos.CENTER);
    cell.setMinWidth(_C_CENA);
    cell.setMaxWidth(_C_CENA);
    row.getChildren().add(cell);

    // VREDNOST BEZ PDV
    Label vrednostBezPDV = new Label("VREDNOST BEZ PDV");
    vrednostBezPDV.setFont(font);
    vrednostBezPDV.setWrapText(true);
    vrednostBezPDV.setAlignment(Pos.CENTER);
    cell = new HBox(vrednostBezPDV);
    cell.setMinWidth(_C_OSNOVICA);
    cell.setMaxWidth(_C_OSNOVICA);
    cell.setAlignment(Pos.CENTER);
    row.getChildren().add(cell);

    // RABAT
    Label rabat = new Label("RABAT");
    rabat.setRotate(-90);
    rabat.setFont(font);
    rabat.setAlignment(Pos.CENTER);
    cell = new HBox(rabat);
    cell.setMinSize(80, 40);
    cell.setMaxSize(80, 40);
    cell.setAlignment(Pos.CENTER);
    row.getChildren().add(cell);

    // OSNOVICA BEZ PDV
    Label osnovicaBezPDV = new Label("OSNOVICA BEZ PDV");
    osnovicaBezPDV.setWrapText(true);
    osnovicaBezPDV.setFont(font);
    osnovicaBezPDV.setAlignment(Pos.CENTER);
    cell = new HBox(osnovicaBezPDV);
    cell.setMinWidth(_C_OSNOVICA);
    cell.setMaxWidth(_C_OSNOVICA);
    cell.setAlignment(Pos.CENTER);
    row.getChildren().add(cell);

    //STOPA PDV
    Label stopaPDV = new Label("STOPA PDV");
    stopaPDV.setFont(font);
    stopaPDV.setWrapText(true);
    stopaPDV.setRotate(-90);
    stopaPDV.setAlignment(Pos.CENTER);
    stopaPDV
        .setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 1;");
    stopaPDV.setMinWidth(80);
    cell = new HBox(stopaPDV);
    cell.setMinSize(30, 80);
    cell.setMaxSize(30, 80);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 1;");
    cell.setAlignment(Pos.CENTER);
    row.getChildren().add(cell);

    //IZNOS PDV
    Label pdv = new Label("IZNOS PDV");
    pdv.setWrapText(true);
    pdv.setFont(font);
    pdv.setAlignment(Pos.CENTER);
    cell = new HBox(pdv);
    cell.setMaxWidth(_C_PDV);
    cell.setMinWidth(_C_PDV);
    row.getChildren().add(cell);

    //VREDNOST SA PDV
    Label vrednostSaPDV = new Label("VREDNOST SA PDV");
    vrednostSaPDV.setFont(font);
    vrednostSaPDV.setWrapText(true);
    vrednostBezPDV.setAlignment(Pos.CENTER);
    cell = new HBox(vrednostSaPDV);
    cell.setMinWidth(_C_UKUPNO);
    cell.setMaxWidth(_C_UKUPNO);
    row.getChildren().add(cell);

    table.getChildren().add(row);

    anchorPane.setMinSize(MAX_WIDTH, MAX_HEIGHT);
    anchorPane.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
    anchorPane.setPrefSize(MAX_WIDTH, MAX_HEIGHT);
    anchorPane.getChildren().add(table);

    Scene scene = new Scene(anchorPane, pageLayout.getPrintableWidth(),
        pageLayout.getPrintableHeight());
    Stage stage = new Stage();
    stage.setScene(scene);
    stage.showAndWait();

    boolean succ = printerJob.printPage(pageLayout, anchorPane);
    if (succ) {
      printerJob.endJob();
    }


  }
}
