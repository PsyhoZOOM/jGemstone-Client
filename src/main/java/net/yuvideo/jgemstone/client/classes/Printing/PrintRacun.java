package net.yuvideo.jgemstone.client.classes.Printing;

import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PrinterJob;
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
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.glxn.qrgen.QRCode;
import net.yuvideo.jgemstone.client.classes.FirmaSettings;
import net.yuvideo.jgemstone.client.classes.MonthFromNumber;
import net.yuvideo.jgemstone.client.classes.Racun;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PrintRacun {

  public ArrayList<Racun> userRacun;
  public Window window;
  public boolean showPreview = false;
  public boolean printPDV;
  DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
  public JSONObject firmaData;
  private PrinterJob printerJob;


  public boolean printRacun(FirmaSettings firmaSettings, PrinterJob pJob) {
    this.printerJob = pJob;


    double NAZIV = 180;
    final double KOLICINA = 30;
    final double CENA = 60;
    final double STOPA_POPUST = 30;
    final double STOPA_PDV = 50;
    final double OSNOVICA = 60;
    final double PDV = 60;
    final double UKUPNO = 60;

    if (!printPDV) {
      NAZIV = 180 + STOPA_PDV + PDV;
    }

    AnchorPane anchorPane = new AnchorPane();

    Font font =
        Font.loadFont(
            getClass().getResource("/font/roboto/Roboto-Regular.ttf").toExternalForm(), 8);
    Font fontBolds = Font
        .loadFont(getClass().getResource("/font/roboto/Roboto-Bold.ttf").toExternalForm(), 5);
    Font fontBold = Font
        .loadFont(getClass().getResource("/font/roboto/Roboto-Bold.ttf").toExternalForm(), 8);
    Font fontBoldL = Font
        .loadFont(getClass().getResource("/font/roboto/Roboto-Regular.ttf").toExternalForm(), 10);
    Font fontADRESA =
        Font.loadFont(getClass().getResource("/font/roboto/Roboto-Bold.ttf").toExternalForm(), 12);
    Font fontRacunDole =
        Font.loadFont(getClass().getResource("/font/roboto/Roboto-Bold.ttf").toExternalForm(), 8);
    Font fontRacunDoleNormal = Font
        .loadFont(getClass().getResource("/font/roboto/Roboto-Regular.ttf").toExternalForm(), 8);

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
    popust.setFont(fontBolds);
    cell = new HBox(popust);

    cell.setMinWidth(STOPA_POPUST);
    cell.setMaxWidth(STOPA_POPUST);
    cell.setAlignment(Pos.CENTER);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 0;");
    row.getChildren().add(cell);

    // STOPA_PDV COLUMN
    Label stopaPDV = new Label("STOPA\nPDV");
    stopaPDV.setFont(fontBolds);
    cell = new HBox(stopaPDV);
    cell.setMinWidth(STOPA_PDV);
    cell.setMaxWidth(STOPA_PDV);
    cell.setAlignment(Pos.CENTER);
    cell.setStyle("-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 0;");
    if (printPDV)
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
    if (printPDV)
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

      if (printPDV) {
        cena = new Label(df.format(racun.getCena()));
      } else {
        cena = new Label(df.format(
            racun.getCena() + valueToPercent.getPDVOfValue(racun.getCena(), racun.getStopaPDV())));
      }
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
      if (printPDV)
        row.getChildren().add(cell);

      osnovica = new Label(df.format(racun.getOsnovica() + racun.getPdv()));
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
      if (printPDV)
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
    if (printPDV) {
      cell.setMinWidth(NAZIV + KOLICINA + CENA + STOPA_POPUST + STOPA_PDV);
    } else {
      cell.setMinWidth(NAZIV + KOLICINA + CENA + STOPA_POPUST);
    }
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
    if (printPDV)
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
                            "Rok za plaćanje: %s",
                            LocalDate.now().plusDays(Long.parseLong(firmaSettings.getFIRMA_ROK_PLACANJA_RACUN())).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
    rokZaPlacanje.setFont(font);
    cell = new HBox(rokZaPlacanje);
    row.getChildren().add(cell);

    racunPodaci.getChildren().add(row);

    row = new HBox();
    Label adresaKorisnika =
        new Label(String.format("Adresa korisnika: \n%s ", racun.getAdresaRacuna()));
    adresaKorisnika.setFont(font);
    cell = new HBox(adresaKorisnika);
    row.getChildren().add(cell);

    racunPodaci.getChildren().add(row);

    File fImg = QRCode.from(String
        .format("Ime: %s \nPoziv na broj: %s\\%s \nRok za plaćanje: %s\nUkupno za uplatu: %s ",
            racun.getIme(),
            racun.getSifraKorisnika(),
            LocalDate.parse(racun.getZaPeriod() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .format(DateTimeFormatter.ofPattern("MM"))
            , LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
            df.format(racun.getUkupanDug()))).withCharset("UTF-8").withSize(50, 50).file();

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
    if (printPDV) {
      cell.setMinWidth(
          NAZIV + KOLICINA + CENA + STOPA_POPUST + STOPA_PDV + OSNOVICA + PDV + UKUPNO);
    } else {
      cell.setMinWidth(NAZIV + CENA + CENA + OSNOVICA + UKUPNO);
    }
    row.getChildren().add(cell);

    finalRacun.getChildren().add(row);

    row = new HBox();
    Label prethodniDug =
        new Label(String.format("Prethodni dug: %s", df.format(racun.getPrethodniDug())));
    prethodniDug.setFont(font);
    cell = new HBox(prethodniDug);
    cell.setAlignment(Pos.CENTER_RIGHT);
    if (printPDV) {
      cell.setMinWidth(
          NAZIV + KOLICINA + CENA + STOPA_POPUST + STOPA_PDV + OSNOVICA + PDV + UKUPNO);
    } else {
      cell.setMinWidth(NAZIV + CENA + CENA + OSNOVICA + UKUPNO);
    }
    row.getChildren().add(cell);

    finalRacun.getChildren().add(row);

    row = new HBox();
    Label UKUPNO_ZA_UPLATU =
        new Label(String.format("Ukupno za Uplatu: %s", df.format(racun.getUkupanDug())));
    UKUPNO_ZA_UPLATU.setFont(font);
    cell = new HBox(UKUPNO_ZA_UPLATU);
    cell.setAlignment(Pos.CENTER_RIGHT);
    if (printPDV) {
      cell.setMinWidth(
          NAZIV + KOLICINA + CENA + STOPA_POPUST + STOPA_PDV + OSNOVICA + PDV + UKUPNO);
    } else {
      cell.setMinWidth(NAZIV + CENA + CENA + OSNOVICA + UKUPNO);
    }
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

    String adresaNaRacunu = String
        .format("%s\n%s", racun.getAdresaRacuna(), racun.getMestoRacuna());

    String rokZaPlacanjeRacun =
        LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("dd.MM.yy"));

    String pozivNaBroj =
        String.format(
            "%s/%s",
            racun.getSifraKorisnika(),
            LocalDate.parse(racun.getZaPeriod() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                .format(DateTimeFormatter.ofPattern("MM")));

    String ukupnoZaUplatuRacun = df.format(racun.getUkupanDug());

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

    TextFlow userDataL = new TextFlow();
    Text userAdresaL = new Text(String
        .format("\n%s\n%s\n%s\n", racun.getIme().toUpperCase(),
            racun.getAdresaRacuna().toUpperCase(),
            racun.getMestoRacuna().toUpperCase()));
    userAdresaL.setFont(fontRacunDole);
    Text svrhaUplate = new Text("\nSvrha uplate: ");
    svrhaUplate.setFont(fontRacunDoleNormal);
    Text svrgaUpateUsl = new Text("Usluge\n");
    svrgaUpateUsl.setFont(fontRacunDole);

    String monthOfNumber = MonthFromNumber
        .getMonthOfNumber(String.valueOf(zaPrDate.getMonthValue()));

    Text zaPeriod = new Text("Za period: ");
    zaPeriod.setFont(fontRacunDoleNormal);
    Text zaPeriodDa = new Text(String.format("%s %s\n", monthOfNumber, zaPrDate.getYear()));
    zaPeriodDa.setFont(fontRacunDole);
    Text rokPlacanja = new Text(String.format("\n\n%s", rokZaPlacanje.getText()));
    rokPlacanja.setFont(fontRacunDole);

    userDataL.getChildren()
        .addAll(userAdresaL, svrhaUplate, svrgaUpateUsl, zaPeriod, zaPeriodDa,
            rokPlacanja);

    TextFlow userDataR = new TextFlow();
    Text userAdresaR = new Text(String
        .format("\n%s\n%s\n%s\n", racun.getIme().toUpperCase(),
            racun.getAdresaRacuna().toUpperCase(),
            racun.getMestoRacuna().toUpperCase()));
    userAdresaR.setFont(fontRacunDole);
    Text zaPeriodR = new Text("Za period: ");
    zaPeriodR.setFont(fontRacunDoleNormal);
    Text zaPeriodDaR = new Text(String.format("%s %s\n", monthOfNumber, zaPrDate.getYear()));
    zaPeriodDaR.setFont(fontRacunDole);

    userDataR.getChildren().addAll(userAdresaR, zaPeriodR, zaPeriodDaR);

    anchorPane.getStylesheets().removeAll();


    /*
    Text aa = new Text("PRVO GORE LEVO");
    anchorPane.getChildren().add(aa);
    AnchorPane.setTopAnchor(aa, 0.0);
    AnchorPane.setLeftAnchor(aa, 0.0);
*/
    anchorPane.getChildren().add(canvas);
    anchorPane.getChildren().add(canvas2);
    anchorPane.getChildren().add(racunPodaci);
    anchorPane.getChildren().add(adresaRacunaKorisnika);
    anchorPane.getChildren().add(table);
    anchorPane.getChildren().add(userDataL);
    anchorPane.getChildren().add(userDataR);
    anchorPane.getChildren().add(pozivNabrojR);
    anchorPane.getChildren().add(pozivNaBrojR2);
    anchorPane.getChildren().add(ukupnoZaUplatuR);
    anchorPane.getChildren().add(ukupnoZaUplatuR2);

    // ImeLeft
    AnchorPane.setTopAnchor(userDataL, 680.0);
    AnchorPane.setLeftAnchor(userDataL, 10.0);
    // ImeRight
    AnchorPane.setTopAnchor(userDataR, 680.0);
    AnchorPane.setLeftAnchor(userDataR, 340.0);
    // iznosLeft
    AnchorPane.setTopAnchor(ukupnoZaUplatuR, 600.0);
    AnchorPane.setLeftAnchor(ukupnoZaUplatuR, 150.0);
    // iznosRight
    AnchorPane.setTopAnchor(ukupnoZaUplatuR2, 600.0);
    AnchorPane.setLeftAnchor(ukupnoZaUplatuR2, 460.0);

    AnchorPane.setTopAnchor(pozivNabrojR, 647.0);
    AnchorPane.setLeftAnchor(pozivNabrojR, 120.0);

    AnchorPane.setTopAnchor(pozivNaBrojR2, 647.0);
    AnchorPane.setLeftAnchor(pozivNaBrojR2, 430.0);

    AnchorPane.setTopAnchor(table, 260.0);
    AnchorPane.setLeftAnchor(table, 10.0);

    AnchorPane.setTopAnchor(racunPodaci, 60.0);
    AnchorPane.setLeftAnchor(racunPodaci, 10.0);

    AnchorPane.setTopAnchor(adresaRacunaKorisnika, 130.0);
    AnchorPane.setLeftAnchor(adresaRacunaKorisnika, 320.0);

    AnchorPane.setTopAnchor(canvas, 720.0);
    AnchorPane.setLeftAnchor(canvas, 220.0);

    AnchorPane.setTopAnchor(canvas2, 720.0);
    AnchorPane.setLeftAnchor(canvas2, 500.0);

    PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();
    double w = pageLayout.getPrintableWidth();
    double h = pageLayout.getPrintableHeight();

    anchorPane.setPrefSize(w, h);
    anchorPane.setTranslateY(0);
    anchorPane.setTranslateX(0);






    anchorPane.setStyle("-fx-background-color: white;");
    if (showPreview) {
      Stage stage = new Stage();
      Scene scene = new Scene(anchorPane);
      stage.setScene(scene);
      stage.showAndWait();
    } else {

      System.out.println(printerJob.getJobSettings().getPageLayout().toString());
      System.out.println(printerJob.getJobSettings().getPrintResolution().toString());
      boolean succ = printerJob.printPage(anchorPane);
      if (succ) {
        return true;
      }


    }

    return false;
  }



}
