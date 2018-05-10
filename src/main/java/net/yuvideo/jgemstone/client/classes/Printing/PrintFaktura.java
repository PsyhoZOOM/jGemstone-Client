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
import javafx.scene.text.TextAlignment;
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

    final double _C_NAZIV = 130;
    final double _C_JMERE = 30;
    final double _C_KOLICINA = 50;
    final double _C_CENA = 60;
    final double _C_STOPA = 30;
    final double _C_OSNOVICA = 60;
    final double _C_PDV = 60;
    final double _C_UKUPNO = 75;

    Font font =
        Font.loadFont(
            getClass().getResource("/font/roboto/Roboto-Regular.ttf").toExternalForm(), 9);
    Font fontSmall =
        Font.loadFont(getClass().getResource("/font/roboto/Roboto-Light.ttf").toExternalForm(), 7);
    Font fontMini =
        Font.loadFont(getClass().getResource("/font/roboto/Roboto-Thin.ttf").toExternalForm(), 5);
    Font fontBold =
        Font.loadFont(getClass().getResource("/font/roboto/Roboto-Bold.ttf").toExternalForm(), 9);
    Font fontADRESA =
        Font.loadFont(getClass().getResource("/font/roboto/Roboto-Bold.ttf").toExternalForm(), 11);
    Font fontRacunDole =
        Font.loadFont(getClass().getResource("/font/roboto/Roboto-Bold.ttf").toExternalForm(), 7);

    String thickFull = "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 1;";
    String thickDown = "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 0.5 0.5 1 0.5;";

    AnchorPane anchorPane = new AnchorPane();

    VBox table = new VBox();
    table.setFillWidth(true);
    table.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 1;");
    HBox row;
    HBox cell;

    row = new HBox();
    //row.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 1;");
    // NAZIV USLUGE
    Label naziv = new Label("VRSTA-NAZIV DOBARA-USLUGA");
    naziv.setFont(fontBold);
    naziv.setWrapText(true);
    naziv.setAlignment(Pos.CENTER);
    naziv.setTextAlignment(TextAlignment.CENTER);
    cell = new HBox(naziv);
    cell.setAlignment(Pos.CENTER);
    cell.setMinWidth(_C_NAZIV);
    cell.setMaxWidth(_C_NAZIV);
    cell.setFillHeight(true);
    cell.setStyle(thickFull);
    row.getChildren().add(cell);

    //JEDINICA MERE

    Label jedinicaMere = new Label("JED. MERE");
    jedinicaMere.setWrapText(true);
    jedinicaMere.setFont(font);
    jedinicaMere.setAlignment(Pos.CENTER);
    jedinicaMere.setTextAlignment(TextAlignment.CENTER);
    cell = new HBox(jedinicaMere);
    cell.setMinWidth(_C_JMERE);
    cell.setMaxWidth(_C_JMERE);
    cell.setAlignment(Pos.CENTER);
    cell.setFillHeight(true);
    cell.setStyle(thickFull);
    row.getChildren().add(cell);

    // KOLICINA
    Label kolicina = new Label("KOLIČINA");
    kolicina.setFont(font);
    kolicina.setAlignment(Pos.CENTER);
    cell = new HBox(kolicina);
    cell.setAlignment(Pos.CENTER);
    cell.setMinWidth(_C_KOLICINA);
    cell.setMaxWidth(_C_KOLICINA);
    cell.setStyle(thickFull);
    row.getChildren().add(cell);

    // CENA BEZ PDV
    Label cenaBezPDV = new Label("CENA BEZ PDV");
    cenaBezPDV.setFont(font);
    cenaBezPDV.setWrapText(true);
    cenaBezPDV.setAlignment(Pos.CENTER);
    cenaBezPDV.setTextAlignment(TextAlignment.CENTER);
    cell = new HBox(cenaBezPDV);
    cell.setAlignment(Pos.CENTER);
    cell.setFillHeight(true);
    cell.setMinWidth(_C_CENA);
    cell.setMaxWidth(_C_CENA);
    cell.setStyle(thickFull);
    row.getChildren().add(cell);

    // VREDNOST BEZ PDV
    Label vrednostBezPDV = new Label("VREDNOST BEZ PDV");
    vrednostBezPDV.setFont(font);
    vrednostBezPDV.setWrapText(true);
    vrednostBezPDV.setAlignment(Pos.CENTER);
    vrednostBezPDV.setTextAlignment(TextAlignment.CENTER);
    cell = new HBox(vrednostBezPDV);
    cell.setMinWidth(_C_OSNOVICA);
    cell.setMaxWidth(_C_OSNOVICA);
    cell.setAlignment(Pos.CENTER);
    cell.setFillHeight(true);
    cell.setStyle(thickFull);
    row.getChildren().add(cell);

    // RABAT
    Label rabat = new Label("RABAT");
    rabat.setRotate(90);
    rabat.setFont(fontSmall);
    rabat.setStyle(thickFull);
    rabat.setAlignment(Pos.CENTER);
    rabat.setTextAlignment(TextAlignment.CENTER);
    rabat.setPrefSize(40, _C_STOPA);
    rabat.setMinSize(40, _C_STOPA);
    cell = new HBox(rabat);
    cell.setStyle(thickFull);
    cell.setMinSize(_C_STOPA, 40);
    cell.setMaxSize(_C_STOPA, 40);
    cell.setFillHeight(true);
    cell.setAlignment(Pos.CENTER);
    row.getChildren().add(cell);

    // OSNOVICA BEZ PDV
    Label osnovicaBezPDV = new Label("OSNOVICA BEZ PDV");
    osnovicaBezPDV.setWrapText(true);
    osnovicaBezPDV.setFont(font);
    osnovicaBezPDV.setAlignment(Pos.CENTER);
    osnovicaBezPDV.setTextAlignment(TextAlignment.CENTER);
    cell = new HBox(osnovicaBezPDV);
    cell.setMinWidth(_C_OSNOVICA);
    cell.setMaxWidth(_C_OSNOVICA);
    cell.setAlignment(Pos.CENTER);
    cell.setStyle(thickFull);
    row.getChildren().add(cell);

    //STOPA PDV
    Label stopaPDV = new Label("STOPA PDV");
    stopaPDV.setFont(fontSmall);
    stopaPDV.setWrapText(true);
    stopaPDV.setRotate(90);
    stopaPDV.setAlignment(Pos.CENTER);
    stopaPDV.setPrefSize(40, _C_STOPA);
    stopaPDV.setMinSize(40, _C_STOPA);
    cell = new HBox(stopaPDV);
    cell.setMinSize(_C_STOPA, 40);
    cell.setMaxSize(_C_STOPA, 40);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 1;");
    cell.setAlignment(Pos.CENTER);
    cell.setStyle(thickFull);
    cell.setFillHeight(true);
    row.getChildren().add(cell);

    //IZNOS PDV
    Label pdv = new Label("IZNOS PDV");
    pdv.setWrapText(true);
    pdv.setFont(font);
    pdv.setAlignment(Pos.CENTER);
    pdv.setTextAlignment(TextAlignment.CENTER);
    cell = new HBox(pdv);
    cell.setMaxWidth(_C_PDV);
    cell.setMinWidth(_C_PDV);
    cell.setStyle(thickFull);
    cell.setAlignment(Pos.CENTER);
    row.getChildren().add(cell);

    //VREDNOST SA PDV
    Label vrednostSaPDV = new Label("VREDNOST SA PDV");
    vrednostSaPDV.setFont(font);
    vrednostSaPDV.setWrapText(true);
    vrednostSaPDV.setAlignment(Pos.CENTER);
    vrednostSaPDV.setTextAlignment(TextAlignment.CENTER);
    vrednostSaPDV.setMinWidth(_C_UKUPNO);
    cell = new HBox(vrednostSaPDV);
    cell.setMinWidth(_C_UKUPNO);
    cell.setMaxWidth(_C_UKUPNO);
    cell.setAlignment(Pos.CENTER);
    cell.setStyle(thickFull);
    row.getChildren().add(cell);

    //header
    Label izdRacuna = new Label("Izdavalac računa:");
    Label lFirma = new Label("YU VIDEO SERVIS");
    Label lFirmaAdresa = new Label("Proleterska 131/6, Majdanpek");
    Label lPIB = new Label("PIB: 100625052");

    //table
    table.getChildren().add(row);

    int i = 0;

    for (i = 0; i < userRacun.size() - 1; i++) {
      row = new HBox();
      Racun racun = userRacun.get(i);
      Label nazivUsluge = new Label(racun.getNazivUsluge());
      naziv.setFont(font);
      cell = new HBox(nazivUsluge);
      cell.setMinWidth(_C_NAZIV);
      cell.setMaxWidth(_C_NAZIV);
      cell.setAlignment(Pos.CENTER);
      row.getChildren().add(cell);

      Label  l_jMere = new Label(racun.getjMere());
      l_jMere.setFont(font);
      cell = new HBox(l_jMere);
      cell.setAlignment(Pos.CENTER);
      cell.setMinWidth(_C_JMERE);
      cell.setMaxWidth(_C_JMERE);
      row.getChildren().add(cell);

      Label l_kolicina = new Label(String.valueOf(racun.getKolicina()));
      l_kolicina.setFont(font);
      l_kolicina.setMinWidth(_C_KOLICINA);
      l_kolicina.setMaxWidth(_C_KOLICINA);
      l_kolicina.setAlignment(Pos.CENTER);
      cell = new HBox(l_kolicina);
      cell.setAlignment(Pos.CENTER);
      cell.setMinWidth(_C_KOLICINA);
      cell.setMaxWidth(_C_KOLICINA);
      row.getChildren().add(l_kolicina);


      Label l_cenaBezPDV = new Label(df.format(racun.getCena()));
      l_cenaBezPDV.setFont(font);
      cell = new HBox(l_cenaBezPDV);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(_C_CENA);
      cell.setMaxWidth(_C_CENA);
      row.getChildren().add(cell);

      Label l_vrednostBezPDV = new Label(df.format(racun.getVrednostBezPDV()));
      l_vrednostBezPDV.setFont(font);
      cell = new HBox(l_vrednostBezPDV);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(_C_CENA);
      cell.setMaxWidth(_C_CENA);
      cell.setStyle(thickFull);
      row.getChildren().add(cell);

      Label popust = new Label(df.format(racun.getPopust()) + "%");
      popust.setFont(fontSmall);
      popust.setAlignment(Pos.CENTER);
      popust.setMinWidth(_C_STOPA);
      popust.setMaxWidth(_C_STOPA);
      cell = new HBox(popust);
      cell.setAlignment(Pos.CENTER);
      cell.setMinWidth(_C_STOPA);
      cell.setMaxWidth(_C_STOPA);
      cell.setStyle(thickFull);
      row.getChildren().add(cell);

      Label osnovica = new Label(df.format(racun.getOsnovica()));
      osnovica.setFont(font);
      osnovica.setMinWidth(_C_OSNOVICA);
      osnovica.setAlignment(Pos.CENTER_RIGHT);
      cell = new HBox(osnovica);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(_C_OSNOVICA);
      cell.setMaxWidth(_C_OSNOVICA);
      cell.setStyle(thickFull);
      row.getChildren().add(cell);


      Label _lStopaPDV = new Label(df.format(racun.getStopaPDV())+ "%");
      _lStopaPDV.setFont(fontSmall);
      _lStopaPDV.setAlignment(Pos.CENTER);
      _lStopaPDV.setMinWidth(_C_STOPA);
      cell = new HBox(_lStopaPDV);
      cell.setAlignment(Pos.CENTER);
      cell.setMinWidth(_C_STOPA);
      cell.setMaxWidth(_C_STOPA);
      cell.setStyle(thickFull);
      row.getChildren().add(cell);


      Label _l_pdv = new Label(df.format(racun.getPdv()));
      _l_pdv.setFont(font);
      _l_pdv.setMinWidth(_C_PDV);
      _l_pdv.setMaxWidth(_C_PDV);
      _l_pdv.setAlignment(Pos.CENTER_RIGHT);
      cell = new HBox(_l_pdv);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(_C_PDV);
      cell.setMaxWidth(_C_PDV);
      cell.setStyle(thickFull);
      row.getChildren().add(cell);

      Label _l_ukupno = new Label(df.format(racun.getUkupno()));
      _l_ukupno.setFont(font);
      cell = new HBox(_l_ukupno);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(_C_UKUPNO);
      cell.setMaxWidth(_C_UKUPNO);
      cell.setStyle(thickFull);
      row.getChildren().add(cell);

      table.getChildren().add(row);
    }

    //UKUPNO zaduzenje / poslednji row
    Racun racun = userRacun.get(i);
    row = new HBox();
    Label ukupno = new Label("Ukupno:");
    ukupno.setFont(font);
    cell = new HBox(ukupno);
    cell.setMinWidth(_C_NAZIV + _C_JMERE + _C_KOLICINA + _C_CENA + _C_CENA) ;
    cell.setAlignment(Pos.CENTER_RIGHT);
    row.getChildren().add(cell);

    //ukupno pdv osnovica
    Label l_ukupnoOsnovica = new Label(df.format(racun.getOsnovicaUkupno()));
    l_ukupnoOsnovica.setFont(font);
    cell = new HBox(l_ukupnoOsnovica);
    cell.setMinWidth(_C_OSNOVICA+_C_STOPA);
    cell.setMaxWidth(_C_OSNOVICA+_C_STOPA);
    cell.setAlignment(Pos.CENTER_RIGHT);
    row.getChildren().add(cell);

    //UKUPNO PDV
    Label l_ukupnoPDV = new Label(df.format(racun.getPdvUkupno()));
    l_ukupnoPDV.setFont(font);
    cell = new HBox(l_ukupnoPDV);
    cell.setAlignment(Pos.CENTER_RIGHT);
    cell.setMinWidth(_C_PDV + _C_STOPA);
    cell.setMaxWidth(_C_PDV + _C_STOPA);
    row.getChildren().add(cell);

    //UKUPNO
    Label l_ukupno = new Label(df.format(racun.getUkupnoUkupno()));
    l_ukupno.setFont(font);
    cell = new HBox(l_ukupno);
    cell.setAlignment(Pos.CENTER_RIGHT);
    cell.setMinWidth(_C_UKUPNO);
    cell.setMaxWidth(_C_UKUPNO);
    row.getChildren().add(cell);

    table.getChildren().add(row);

    //SPECIFIKACIJA POREZA

    anchorPane.setMinSize(MAX_WIDTH, MAX_HEIGHT);
    anchorPane.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
    anchorPane.setPrefSize(MAX_WIDTH, MAX_HEIGHT);
    anchorPane.getChildren().add(table);

    Scene scene = new Scene(anchorPane, pageLayout.getPrintableWidth(),
        pageLayout.getPrintableHeight());
    Stage stage = new Stage();
    stage.setScene(scene);

    if(showPreview) {
      stage.showAndWait();
    }else {

      boolean succ = printerJob.printPage(pageLayout, anchorPane);
      if (succ) {
        printerJob.endJob();
      }
    }


  }
}
