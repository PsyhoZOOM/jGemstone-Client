package net.yuvideo.jgemstone.client.classes.Printing;

import javafx.geometry.Pos;
import javafx.print.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.yuvideo.jgemstone.client.classes.Racun;
import net.glxn.qrgen.QRCode;

import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PrintRacun {
  public ArrayList<Racun> userRacun;
  public Window window;
  PageLayout pageLayout;
  DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
  private PrinterJob printerJob;
  private Printer printer;
  private Paper paper;

  public void setPrinterData(JobSettings js, Printer printer) {
    this.printerJob = PrinterJob.createPrinterJob();
    this.printer = printer;
    this.pageLayout = js.getPageLayout();
    Paper paper = pageLayout.getPaper();
    PageOrientation pageOrientation = pageLayout.getPageOrientation();
    this.pageLayout =
        this.printer.createPageLayout(paper, pageOrientation, Printer.MarginType.HARDWARE_MINIMUM);
    this.paper = paper;

    printerJob.setPrinter(this.printer);
  }

  public void printRacun() {

    final double WIDTH = pageLayout.getPrintableWidth();
    final double HEIGHT = pageLayout.getPrintableHeight();
    final double MAX_WIDTH = paper.getWidth();
    final double MAX_HEIGHT = paper.getHeight();

    final double NAZIV = 180;
    final double KOLICINA = 40;
    final double CENA = 60;
    final double STOPA_POPUST = 45;
    final double STOPA_PDV = 45;
    final double OSNOVICA = 60;
    final double PDV = 60;
    final double UKUPNO = 70;

    AnchorPane anchorPane = new AnchorPane();

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

    VBox table = new VBox();
    HBox row;
    HBox cell;

    row = new HBox();
    // NAZIV COLUMN
    Label naziv = new Label("NAZIV");
    naziv.setFont(fontBold);
    cell = new HBox(naziv);
    cell.setAlignment(Pos.CENTER);
    cell.setMinWidth(NAZIV);
    cell.setMaxWidth(NAZIV);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 1;");
    row.getChildren().add(cell);

    // KOLICINA COLUMN
    Label kolicina = new Label("KOL.");
    kolicina.setFont(fontBold);
    cell = new HBox(kolicina);
    cell.setMinWidth(KOLICINA);
    cell.setMaxWidth(KOLICINA);
    cell.setAlignment(Pos.CENTER);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 0;");
    row.getChildren().add(cell);

    // CENA COLUMN
    Label cena = new Label("CENA");
    cena.setFont(fontBold);
    cell = new HBox(cena);
    cell.setMinWidth(CENA);
    cell.setMaxWidth(CENA);
    cell.setAlignment(Pos.CENTER);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 0;");
    row.getChildren().add(cell);

    // POPUST COLUMN
    Label popust = new Label("POPUST");
    popust.setFont(fontBold);
    cell = new HBox(popust);

    cell.setMinWidth(STOPA_POPUST);
    cell.setMaxWidth(STOPA_POPUST);
    cell.setAlignment(Pos.CENTER);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 0;");
    row.getChildren().add(cell);

    // STOPA_PDV COLUMN
    Label stopaPDV = new Label("STOPA \n PDV");
    stopaPDV.setFont(fontBold);
    cell = new HBox(stopaPDV);
    cell.setMinWidth(STOPA_PDV);
    cell.setMaxWidth(STOPA_PDV);
    cell.setAlignment(Pos.CENTER);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 0;");
    row.getChildren().add(cell);

    // OSNOVICA COLUMN
    Label osnovica = new Label("OSNOVICA");
    osnovica.setFont(fontBold);
    cell = new HBox(osnovica);
    cell.setMinWidth(OSNOVICA);
    cell.setMaxWidth(OSNOVICA);
    cell.setAlignment(Pos.CENTER);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 0;");
    row.getChildren().add(cell);

    // PDV COLUMN
    Label pdv = new Label("PDV");
    pdv.setFont(fontBold);
    cell = new HBox(pdv);
    cell.setMinWidth(PDV);
    cell.setMaxWidth(PDV);
    cell.setAlignment(Pos.CENTER);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 0;");
    row.getChildren().add(cell);

    // UKUPNO COLUMN
    Label ukupnoL = new Label("UKUPNO");
    ukupnoL.setFont(fontBold);
    cell = new HBox(ukupnoL);
    cell.setMaxWidth(UKUPNO);
    cell.setMinWidth(UKUPNO);
    cell.setAlignment(Pos.CENTER);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 0;");
    row.getChildren().add(cell);

    table.getChildren().add(row);

    int i = 0;

    for (i = 0; i < userRacun.size() - 1; i++) {
      row = new HBox();

      Racun racun = userRacun.get(i);
      Label nazivUsluge = new Label(racun.getNazivUsluge());
      nazivUsluge.setFont(font);
      cell = new HBox(nazivUsluge);
      cell.setMinWidth(NAZIV);
      cell.setMaxWidth(NAZIV);
      cell.setAlignment(Pos.CENTER);
      cell.setStyle(
          "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 0 0.2 0.2 1;");

      row.getChildren().add(cell);

      kolicina = new Label(String.valueOf(racun.getKolicina()));
      kolicina.setFont(font);
      cell = new HBox(kolicina);
      cell.setMinWidth(KOLICINA);
      cell.setMaxWidth(KOLICINA);
      cell.setAlignment(Pos.CENTER);
      cell.setStyle(
          "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 0 0.2 0.2 0;");
      row.getChildren().add(cell);

      cena = new Label(df.format(racun.getCena()));
      cena.setFont(font);
      cell = new HBox(cena);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(CENA);
      cell.setMaxWidth(CENA);
      cell.setStyle(
          "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 0 0.2 0.2 0;");
      row.getChildren().add(cell);

      popust = new Label(df.format(racun.getPopust()) + "%");
      popust.setFont(font);
      cell = new HBox(popust);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(STOPA_POPUST);
      cell.setMaxWidth(STOPA_POPUST);
      cell.setStyle(
          "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 0 0.2 0.2 0;");
      row.getChildren().add(cell);

      stopaPDV = new Label(df.format(racun.getStopaPDV()) + "%");
      stopaPDV.setFont(font);
      cell = new HBox(stopaPDV);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(STOPA_PDV);
      cell.setMaxWidth(STOPA_PDV);
      cell.setStyle(
          "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 0 0.2 0.2 0;");
      row.getChildren().add(cell);

      osnovica = new Label(df.format(racun.getOsnovica()));
      osnovica.setFont(font);
      cell = new HBox(osnovica);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(OSNOVICA);
      cell.setMaxWidth(OSNOVICA);
      cell.setStyle(
          "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 0 0.2 0.2 0;");
      row.getChildren().add(cell);

      pdv = new Label(df.format(racun.getPdv()));
      pdv.setFont(font);
      cell = new HBox(pdv);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(PDV);
      cell.setMaxWidth(PDV);
      cell.setStyle(
          "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 0 0.2 0.2 0;");
      row.getChildren().add(cell);

      Label lukupno = new Label(df.format(racun.getUkupno()));
      lukupno.setFont(font);
      cell = new HBox(lukupno);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(UKUPNO);
      cell.setMaxWidth(UKUPNO);
      cell.setStyle(
          "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 0 1 0.2 0;");
      row.getChildren().add(cell);

      table.getChildren().add(row);
    }
    Racun racun = userRacun.get(i);

    // UKUPNO zaduzenje za obracunski period
    row = new HBox();
    Label zaduzenje = new Label("Zaduženje za obračunski period: ");
    zaduzenje.setFont(font);
    cell = new HBox(zaduzenje);
    cell.setMinWidth(NAZIV + KOLICINA + CENA + STOPA_POPUST + STOPA_PDV);
    cell.setAlignment(Pos.CENTER_RIGHT);
    row.getChildren().add(cell);

    // UKUPNO PDV OSNOVICA
    Label LukupnoOsnovica = new Label(df.format(racun.getOsnovicaUkupno()));
    LukupnoOsnovica.setFont(fontBold);
    cell = new HBox(LukupnoOsnovica);
    cell.setMinWidth(OSNOVICA);
    cell.setMaxWidth(OSNOVICA);
    cell.setAlignment(Pos.CENTER_RIGHT);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 1;");
    row.getChildren().add(cell);

    // UKUPNO PDV
    Label ukupnoPDV = new Label(df.format(racun.getPdvUkupno()));
    ukupnoPDV.setFont(fontBold);
    cell = new HBox(ukupnoPDV);
    cell.setMinWidth(PDV);
    cell.setMaxWidth(PDV);
    cell.setAlignment(Pos.CENTER_RIGHT);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 0;");
    row.getChildren().add(cell);

    /// UKUPNO
    Label ukupnoUkupno = new Label(df.format(racun.getUkupnoUkupno()));
    ukupnoUkupno.setFont(fontBold);
    cell = new HBox(ukupnoUkupno);
    cell.setMinWidth(UKUPNO);
    cell.setMaxWidth(UKUPNO);
    cell.setAlignment(Pos.CENTER_RIGHT);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 0;");
    row.getChildren().add(cell);

    table.getChildren().add(row);

    // RACUN PODACI
    VBox racunPodaci = new VBox();

    LocalDate zaPrDate =
        LocalDate.parse(racun.getZaPeriod() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String stDate = zaPrDate.withDayOfMonth(1).format(DateTimeFormatter.ofPattern("dd.MM"));
    String lstDate =
        zaPrDate
            .withDayOfMonth(zaPrDate.lengthOfMonth())
            .format(DateTimeFormatter.ofPattern("dd.MM"));

    row = new HBox();
    Label zaP =
        new Label(
            String.format(
                "Za period: \n %s-%s.%s",
                stDate, lstDate, zaPrDate.format(DateTimeFormatter.ofPattern("yyyy"))));
    zaP.setFont(font);
    cell = new HBox(zaP);
    row.getChildren().add(cell);

    racunPodaci.getChildren().add(row);

    row = new HBox();
    Label sifraK = new Label(String.format("Šifra korisnika: \n %s", racun.getSifraKorisnika()));
    sifraK.setFont(font);
    cell = new HBox(sifraK);
    row.getChildren().add(cell);

    racunPodaci.getChildren().add(row);

    row = new HBox();
    Label mestoDatumIzdavanja =
        new Label(
            String.format(
                "Mesto i datum izdavanja računa: \n Petrovac na Mlavi %s",
                LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
    mestoDatumIzdavanja.setFont(font);
    cell = new HBox(mestoDatumIzdavanja);
    row.getChildren().add(cell);

    racunPodaci.getChildren().add(row);

    row = new HBox();
    Label datumPrometa =
        new Label(
            String.format(
                "Datum prometa: \n %s",
                zaPrDate
                    .withDayOfMonth(zaPrDate.lengthOfMonth())
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
    datumPrometa.setFont(font);
    cell = new HBox(datumPrometa);
    row.getChildren().add(cell);

    racunPodaci.getChildren().add(row);

    row = new HBox();
    Label rokZaPlacanje =
        new Label(
            String.format(
                "Rok za plaćanje: \n %s",
                LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
    rokZaPlacanje.setFont(font);
    cell = new HBox(rokZaPlacanje);
    row.getChildren().add(cell);

    racunPodaci.getChildren().add(row);

    row = new HBox();
    Label adresaKorisnika =
        new Label(String.format("Adresa korisnika: \n %s ", racun.getAdresaRacuna()));
    adresaKorisnika.setFont(font);
    cell = new HBox(adresaKorisnika);
    row.getChildren().add(cell);

    racunPodaci.getChildren().add(row);

    // QRCODE
    // File fImg =
    // net.glxn.qrgen.javase.QRCode.from(racun.getSifraKorisnika()).withCharset("CP1250").file();

    File fImg = QRCode.from(racun.getSifraKorisnika()).withCharset("CP1250").file();

    Image img = new Image(fImg.toURI().toString());

    ImageView imgQR = new ImageView();
    imgQR.setImage(img);

    Canvas canvas = new Canvas(50, 50);
    Canvas canvas2 = new Canvas(50, 50);
    GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    graphicsContext.drawImage(img, 0, 0, 50, 50);
    graphicsContext = canvas2.getGraphicsContext2D();
    graphicsContext.drawImage(img, 0, 0, 50, 50);

    // FINAL RACUN
    VBox finalRacun = new VBox();

    row = new HBox();
    Label ukupnoZaUplatu =
        new Label(String.format("Ukupno: %s", df.format(racun.getUkupnoUkupno())));
    ukupnoZaUplatu.setFont(font);
    cell = new HBox(ukupnoZaUplatu);
    cell.setAlignment(Pos.CENTER_RIGHT);
    cell.setMinWidth(NAZIV + KOLICINA + CENA + STOPA_POPUST + STOPA_PDV + OSNOVICA + PDV + UKUPNO);
    row.getChildren().add(cell);

    finalRacun.getChildren().add(row);

    row = new HBox();
    Label prethodniDug =
        new Label(String.format("Prethodni dug: %s", df.format(racun.getPrethodniDug())));
    prethodniDug.setFont(font);
    cell = new HBox(prethodniDug);
    cell.setAlignment(Pos.CENTER_RIGHT);
    cell.setMinWidth(NAZIV + KOLICINA + CENA + STOPA_POPUST + STOPA_PDV + OSNOVICA + PDV + UKUPNO);
    row.getChildren().add(cell);

    finalRacun.getChildren().add(row);

    row = new HBox();
    Label UKUPNO_ZA_UPLATU =
        new Label(String.format("Ukupno za Uplatu: %s", df.format(racun.getUkupanDug())));
    UKUPNO_ZA_UPLATU.setFont(font);
    cell = new HBox(UKUPNO_ZA_UPLATU);
    cell.setAlignment(Pos.CENTER_RIGHT);
    cell.setMinWidth(NAZIV + KOLICINA + CENA + STOPA_POPUST + STOPA_PDV + OSNOVICA + PDV + UKUPNO);
    row.getChildren().add(cell);

    finalRacun.getChildren().add(row);

    table.getChildren().add(finalRacun);

    // RACUN ADRESA
    Text adresaRacunaKorisnika =
        new Text(
            String.format(
                    "%s\n%s\n%s", racun.getIme(), racun.getAdresaRacuna(), racun.getMestoRacuna())
                .toUpperCase());
    adresaRacunaKorisnika.setFont(fontADRESA);

    // RACUN_BOTTOM_DATA
    String ime = racun.getIme();

    String svrhaUplate = "189";

    String adresaNaRacunu = String.format("%s %s", racun.getAdresaRacuna(), racun.getMestoRacuna());

    String zaPerioRacun =
        String.format(
            "%s-%s.%s", stDate, lstDate, zaPrDate.format(DateTimeFormatter.ofPattern("yy")));

    String rokZaPlacanjeRacun =
        LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("dd.MM.yy"));

    String pozivNaBroj =
        String.format(
            "%s/%s",
            racun.getSifraKorisnika(),
            LocalDate.parse(racun.getZaPeriod() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .format(DateTimeFormatter.ofPattern("MM")));

    String ukupnoZaUplatuRacun = df.format(racun.getUkupnoUkupno());

    Text imeR = new Text(ime.toUpperCase());
    imeR.setFont(fontRacunDole);
    Text imeR2 = new Text(ime.toUpperCase());
    imeR2.setFont(fontRacunDole);

    Text svrhaUplateR = new Text(svrhaUplate);
    svrhaUplateR.setFont(fontBold);
    Text svrhaUplateR2 = new Text(svrhaUplate);
    svrhaUplateR2.setFont(fontBold);

    Text adresaNaRacunuR = new Text(adresaNaRacunu.toUpperCase());
    adresaNaRacunuR.setFont(fontRacunDole);
    Text adresaNaRacunuR2 = new Text(adresaNaRacunu.toUpperCase());
    adresaNaRacunuR2.setFont(fontRacunDole);

    Text zaPeriodR = new Text(zaPerioRacun);
    zaPeriodR.setFont(fontRacunDole);
    Text zaPeriodR2 = new Text(zaPerioRacun);
    zaPeriodR2.setFont(fontRacunDole);

    Text rokZaPLacanjeRacunR = new Text(rokZaPlacanjeRacun);
    rokZaPLacanjeRacunR.setFont(fontRacunDole);

    Text pozivNabrojR = new Text(pozivNaBroj);
    pozivNabrojR.setFont(fontBold);
    Text pozivNaBrojR2 = new Text(pozivNaBroj);
    pozivNaBrojR2.setFont(fontBold);

    Text ukupnoZaUplatuR = new Text(ukupnoZaUplatuRacun);
    ukupnoZaUplatuR.setFont(fontBold);
    Text ukupnoZaUplatuR2 = new Text(ukupnoZaUplatuRacun);
    ukupnoZaUplatuR2.setFont(fontBold);

    // KONTAKT
    String kontakt =
        String.format(
            "KONTAKT:\n"
                + "Info centar i prijava smetnji:\n"
                + "012/430-000\n"
                + "012/430-111\n"
                + "mail: yuvideo@yuvideo.net\n"
                + "https://www.yuvideo.net");
    Text kontaktT = new Text(kontakt);
    kontaktT.setFont(fontMini);

    anchorPane.setMinSize(MAX_WIDTH, MAX_HEIGHT);
    anchorPane.setPrefSize(MAX_WIDTH, MAX_HEIGHT);
    anchorPane.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
    anchorPane.getChildren().add(kontaktT);
    anchorPane.getChildren().add(canvas);
    anchorPane.getChildren().add(canvas2);
    anchorPane.getChildren().add(racunPodaci);
    anchorPane.getChildren().add(adresaRacunaKorisnika);
    anchorPane.getChildren().add(table);
    anchorPane.getChildren().add(imeR);
    anchorPane.getChildren().add(imeR2);
    anchorPane.getChildren().add(svrhaUplateR);
    anchorPane.getChildren().add(svrhaUplateR2);
    anchorPane.getChildren().add(adresaNaRacunuR);
    anchorPane.getChildren().add(adresaNaRacunuR2);
    anchorPane.getChildren().add(zaPeriodR);
    anchorPane.getChildren().add(zaPeriodR2);
    anchorPane.getChildren().add(rokZaPLacanjeRacunR);
    anchorPane.getChildren().add(pozivNabrojR);
    anchorPane.getChildren().add(pozivNaBrojR2);
    anchorPane.getChildren().add(ukupnoZaUplatuR);
    anchorPane.getChildren().add(ukupnoZaUplatuR2);

    // ImeLeft
    AnchorPane.setTopAnchor(imeR, 603.0);
    AnchorPane.setLeftAnchor(imeR, 53.0);
    // ImeRight
    AnchorPane.setTopAnchor(imeR2, 603.0);
    AnchorPane.setLeftAnchor(imeR2, 375.0);
    // adresaLeft
    AnchorPane.setTopAnchor(adresaNaRacunuR, 613.0);
    AnchorPane.setLeftAnchor(adresaNaRacunuR, 49.0);
    // adresaRight
    AnchorPane.setTopAnchor(adresaNaRacunuR2, 613.0);
    AnchorPane.setLeftAnchor(adresaNaRacunuR2, 370.0);
    // svrhaUplateLeft
    AnchorPane.setTopAnchor(svrhaUplateR, 623.0);
    AnchorPane.setLeftAnchor(svrhaUplateR, 70.0);
    // svrhaUplateRight
    AnchorPane.setTopAnchor(svrhaUplateR2, 623.0);
    AnchorPane.setLeftAnchor(svrhaUplateR2, 393.0);
    // zaPeriodLeft
    AnchorPane.setTopAnchor(zaPeriodR, 625.0);
    AnchorPane.setLeftAnchor(zaPeriodR, 157.0);
    // zaPeriodRight
    AnchorPane.setTopAnchor(zaPeriodR2, 625.0);
    AnchorPane.setLeftAnchor(zaPeriodR2, 494.0);
    // rokPlacanjaLeft
    AnchorPane.setTopAnchor(rokZaPLacanjeRacunR, 625.0);
    AnchorPane.setLeftAnchor(rokZaPLacanjeRacunR, 269.0);
    // iznosLeft
    AnchorPane.setTopAnchor(ukupnoZaUplatuR, 648.0);
    AnchorPane.setLeftAnchor(ukupnoZaUplatuR, 223.0);
    // iznosRight
    AnchorPane.setTopAnchor(ukupnoZaUplatuR2, 648.0);
    AnchorPane.setLeftAnchor(ukupnoZaUplatuR2, 473.0);
    // pozivNaBrojLeft
    AnchorPane.setTopAnchor(pozivNabrojR, 713.0);
    AnchorPane.setLeftAnchor(pozivNabrojR, 208.0);
    // pozivNaBrojRight
    AnchorPane.setTopAnchor(pozivNaBrojR2, 713.0);
    AnchorPane.setLeftAnchor(pozivNaBrojR2, 443.0);

    AnchorPane.setTopAnchor(table, 260.0);
    AnchorPane.setLeftAnchor(table, 10.0);

    AnchorPane.setTopAnchor(racunPodaci, 60.0);
    AnchorPane.setLeftAnchor(racunPodaci, 10.0);

    AnchorPane.setTopAnchor(adresaRacunaKorisnika, 120.0);
    AnchorPane.setLeftAnchor(adresaRacunaKorisnika, 340.0);

    AnchorPane.setTopAnchor(canvas, 549.0);
    AnchorPane.setLeftAnchor(canvas, 258.0);

    AnchorPane.setTopAnchor(canvas2, 549.0);
    AnchorPane.setLeftAnchor(canvas2, 512.0);

    AnchorPane.setTopAnchor(kontaktT, 90.0);
    AnchorPane.setLeftAnchor(kontaktT, 200.0);

    Scene scene =
        new Scene(anchorPane, pageLayout.getPrintableWidth(), pageLayout.getPrintableHeight());
    Stage stage = new Stage();
    stage.setScene(scene);
    // stage.showAndWait();

    boolean succ = printerJob.printPage(pageLayout, anchorPane);
    if (succ) {
      printerJob.endJob();
    }
  }

  private void createPage() {}
}
