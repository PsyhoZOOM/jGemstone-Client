package net.yuvideo.jgemstone.client.classes.Printing;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.geometry.Insets;
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
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.Racun;
import net.yuvideo.jgemstone.client.classes.css.cssStyleProperty;
import org.json.JSONObject;

public class PrintFaktura {

  public ArrayList<Racun> userRacun;
  public boolean showPreview = false;
  private PageLayout pageLayout;
  private DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
  private PrinterJob printerJob;
  private Printer printer;
  private Paper paper;
  private PageOrientation pageOrientation;
  public JSONObject firmaData;

  public void setPrinterData(JobSettings js, Printer printer) {
    this.printerJob = PrinterJob.createPrinterJob();
    this.printer = printer;
    this.pageLayout = js.getPageLayout();
    this.paper = this.pageLayout.getPaper();
    this.pageOrientation = this.pageLayout.getPageOrientation();
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

    final double _C_NAZIV = 120;
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



    AnchorPane anchorPane = new AnchorPane();

    VBox table = new VBox();
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
    cell.setStyle(cssStyleProperty.getBorder(1, 1,1,1));
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
    cell.setStyle(cssStyleProperty.getBorder(1, 1,1,1));
    row.getChildren().add(cell);

    // KOLICINA
    Label kolicina = new Label("KOLIČINA");
    kolicina.setFont(font);
    kolicina.setAlignment(Pos.CENTER);
    cell = new HBox(kolicina);
    cell.setAlignment(Pos.CENTER);
    cell.setMinWidth(_C_KOLICINA);
    cell.setMaxWidth(_C_KOLICINA);
    cell.setStyle(cssStyleProperty.getBorder(1, 1,1,1));
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
    cell.setStyle(cssStyleProperty.getBorder(1, 1,1,1));
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
    cell.setStyle(cssStyleProperty.getBorder(1, 1,1,1));
    row.getChildren().add(cell);

    // RABAT
    Label rabat = new Label("RABAT");
    rabat.setRotate(90);
    rabat.setFont(fontSmall);
    rabat.setAlignment(Pos.CENTER);
    rabat.setTextAlignment(TextAlignment.CENTER);
    rabat.setPrefSize(40, _C_STOPA);
    rabat.setMinSize(40, _C_STOPA);
    cell = new HBox(rabat);
    cell.setStyle(cssStyleProperty.getBorder(1, 1,1,1));
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
    cell.setStyle(cssStyleProperty.getBorder(1, 1,1,1));
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
    cell.setStyle(cssStyleProperty.getBorder(1, 1,1,1));
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
    cell.setStyle(cssStyleProperty.getBorder(1, 1,1,1));
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
    cell.setStyle(cssStyleProperty.getBorder(1, 1,1,1));
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
      nazivUsluge.setFont(font);
      cell = new HBox(nazivUsluge);
      cell.setMinWidth(_C_NAZIV);
      cell.setMaxWidth(_C_NAZIV);
      cell.setAlignment(Pos.CENTER);
      cell.setStyle(cssStyleProperty.getBorder(0, 0.5, 0.5, 1));
      row.getChildren().add(cell);

      Label  l_jMere = new Label(racun.getjMere());
      l_jMere.setFont(font);
      cell = new HBox(l_jMere);
      cell.setAlignment(Pos.CENTER);
      cell.setMinWidth(_C_JMERE);
      cell.setMaxWidth(_C_JMERE);
      cell.setStyle(cssStyleProperty.getBorder(0, 0.5, 0.5, 0));
      row.getChildren().add(cell);

      Label l_kolicina = new Label(String.valueOf(racun.getKolicina()));
      l_kolicina.setFont(font);
      l_kolicina.setAlignment(Pos.CENTER);
      cell = new HBox(l_kolicina);
      cell.setAlignment(Pos.CENTER);
      cell.setMinWidth(_C_KOLICINA);
      cell.setMaxWidth(_C_KOLICINA);
      cell.setStyle(cssStyleProperty.getBorder(0, 0.5, 0.5, 0));
      row.getChildren().add(cell);


      Label l_cenaBezPDV = new Label(df.format(racun.getCena()));
      l_cenaBezPDV.setFont(font);
      cell = new HBox(l_cenaBezPDV);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(_C_CENA);
      cell.setMaxWidth(_C_CENA);
      cell.setStyle(cssStyleProperty.getBorder(0, 0.5, 0.5, 0));
      row.getChildren().add(cell);

      Label l_vrednostBezPDV = new Label(df.format(racun.getVrednostBezPDV()));
      l_vrednostBezPDV.setFont(font);
      cell = new HBox(l_vrednostBezPDV);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(_C_CENA);
      cell.setMaxWidth(_C_CENA);
      cell.setStyle(cssStyleProperty.getBorder(0 , 0.5 , 0.5 ,0));
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
      cell.setStyle(cssStyleProperty.getBorder(0, 0.5, 0.5, 0));
      row.getChildren().add(cell);

      Label osnovica = new Label(df.format(racun.getOsnovica()));
      osnovica.setFont(font);
      osnovica.setMinWidth(_C_OSNOVICA);
      osnovica.setAlignment(Pos.CENTER_RIGHT);
      cell = new HBox(osnovica);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(_C_OSNOVICA);
      cell.setMaxWidth(_C_OSNOVICA);
      cell.setStyle(cssStyleProperty.getBorder(0 ,0.5 , 0.5, 0));
      row.getChildren().add(cell);


      Label _lStopaPDV = new Label(df.format(racun.getStopaPDV())+ "%");
      _lStopaPDV.setFont(fontSmall);
      _lStopaPDV.setAlignment(Pos.CENTER);
      _lStopaPDV.setMinWidth(_C_STOPA);
      cell = new HBox(_lStopaPDV);
      cell.setAlignment(Pos.CENTER);
      cell.setMinWidth(_C_STOPA);
      cell.setMaxWidth(_C_STOPA);
      cell.setStyle(cssStyleProperty.getBorder(0, 0.5, 0.5, 0));
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
      cell.setStyle(cssStyleProperty.getBorder(0, 0.5 , 0.5 , 0));
      row.getChildren().add(cell);

      Label _l_ukupno = new Label(df.format(racun.getUkupno()));
      _l_ukupno.setFont(font);
      cell = new HBox(_l_ukupno);
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setMinWidth(_C_UKUPNO);
      cell.setMaxWidth(_C_UKUPNO);
      cell.setStyle(cssStyleProperty.getBorder(0, 1, 0.5, 0));
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
    cell.setStyle(cssStyleProperty.getBorder(1, 0, 0,0 ));
    row.getChildren().add(cell);

    //ukupno pdv osnovica
    Label l_ukupnoOsnovica = new Label(df.format(racun.getOsnovicaUkupno()));
    l_ukupnoOsnovica.setFont(font);
    cell = new HBox(l_ukupnoOsnovica);
    cell.setMinWidth(_C_OSNOVICA+_C_STOPA);
    cell.setMaxWidth(_C_OSNOVICA+_C_STOPA);
    cell.setAlignment(Pos.CENTER_RIGHT);
    cell.setStyle(cssStyleProperty.getBorder(1, 0 , 1, 1));
    row.getChildren().add(cell);

    //UKUPNO PDV
    Label l_ukupnoPDV = new Label(df.format(racun.getPdvUkupno()));
    l_ukupnoPDV.setFont(font);
    cell = new HBox(l_ukupnoPDV);
    cell.setAlignment(Pos.CENTER_RIGHT);
    cell.setMinWidth(_C_PDV + _C_STOPA);
    cell.setMaxWidth(_C_PDV + _C_STOPA);
    cell.setStyle(cssStyleProperty.getBorder(1,0,1,1));
    row.getChildren().add(cell);

    //UKUPNO
    Label l_ukupno = new Label(df.format(racun.getUkupnoUkupno()));
    l_ukupno.setFont(font);
    cell = new HBox(l_ukupno);
    cell.setAlignment(Pos.CENTER_RIGHT);
    cell.setMinWidth(_C_UKUPNO);
    cell.setMaxWidth(_C_UKUPNO);
    cell.setStyle(cssStyleProperty.getBorder(1, 1,1,1));
    row.getChildren().add(cell);

    table.getChildren().add(row);

    //SPECIFIKACIJA POREZA




    //TOP DATA

    VBox tableTopRacun = new VBox();

    Text tIzdavalacRacuna = new Text("Izdavalac računa:");
    tIzdavalacRacuna.setFont(font);
    tableTopRacun.getChildren().add(tIzdavalacRacuna);

    Text tNazivFirme = new Text(firmaData.getString("FIRMA_NAZIV"));
    tNazivFirme.setFont(fontBold);
    tNazivFirme.setText(tNazivFirme.getText().toUpperCase());
    tableTopRacun.getChildren().add(tNazivFirme);

    Text tAdresaFime = new Text(firmaData.getString("FIRMA_ADRESA"));
    tAdresaFime.setFont(fontBold);
    tableTopRacun.getChildren().add(tAdresaFime);

    Text tPIB = new Text("PIB: ");
    tPIB.setFont(font);
    Text tFIMRMA_PIB = new Text(firmaData.getString("FIRMA_PIB"));
    tFIMRMA_PIB.setFont(fontBold);
    Text tMatBr = new Text("Matični broj: ");
    tMatBr.setFont(font);
    Text tFirmaMATBr = new Text(firmaData.getString("FIRMA_MBR"));
    tFirmaMATBr.setFont(fontBold);
    Text tPEPEDV = new Text("\tPEPEDV: ");
    tPEPEDV.setFont(font);
    Text tFirmaPEPDV = new Text(firmaData.getString("FIRMA_FAKTURA_PEPDV"));
    tFirmaPEPDV.setFont(fontBold);
    HBox row1 = new HBox();
    row1.getChildren().add(tPIB);
    row1.getChildren().add(tFIMRMA_PIB);
    row1.getChildren().add(tPEPEDV);
    row1.getChildren().add(tFirmaPEPDV);
    tableTopRacun.getChildren().add(row1);

    Text tTekuciRacun = new Text("Tekući račun: ");
    tTekuciRacun.setFont(font);
    Text tTekuciRacunFirma = new Text(firmaData.getString("FIRMA_TEKUCIRACUN"));
    tTekuciRacunFirma.setFont(fontBold);
    HBox row2 = new HBox();
    row2.getChildren().add(tTekuciRacun);
    row2.getChildren().add(tTekuciRacunFirma);
    tableTopRacun.getChildren().add(row2);

    Text tTelefon = new Text("Telefon: ");
    tTelefon.setFont(font);
    Text tTelefonFirme = new Text(firmaData.getString("FIRMA_TELEFON"));
    tTelefonFirme.setFont(fontBold);
    Text tFax = new Text("\tFAX: ");
    tFax.setFont(font);
    Text tFaxFirma = new Text(firmaData.getString("FIRMA_FAX"));
    tFaxFirma.setFont(fontBold);
    Text tEmail = new Text("\tE-mail: ");
    tEmail.setFont(font);
    Text tEmailFirma = new Text(firmaData.getString("FIRMA_SERVIS_EMAIL"));
    tEmailFirma.setFont(fontBold);
    HBox row3 = new HBox();
    row3.getChildren().addAll(tTelefon, tTelefonFirme, tFax, tFaxFirma, tEmail, tEmailFirma);
    tableTopRacun.getChildren().add(row3);

    Text tIzdavalacRacunaNapomena = new Text(
        "* izdavalac računa se nalazi u registru PDV obveznika");
    tIzdavalacRacunaNapomena.setFont(fontSmall);
    tableTopRacun.getChildren().add(tIzdavalacRacunaNapomena);

    //RACUN DATA
    LocalDate ldateDatumPrometa = LocalDate
        .parse(racun.getZaPeriod() + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    String mesecPrometa = String.valueOf(ldateDatumPrometa.getMonthValue());
    String godinaPrometa = String
        .valueOf(ldateDatumPrometa.format(DateTimeFormatter.ofPattern("yy")));

    Text tRacunPodaci = new Text(String.format(
        "RAČUN broj: %s\n"
            + "Datum izdavanja računa: %s\n"
            + "Mesto izdavanja računa: %s\n"
            + "Datum prometa dobara i usluga: %s\n"
            + "Mesto prometa dobara i usluge: %s\n"
            + "Rok plaćanja: %s dana\n",
        racun.getSifraKorisnika() + "/" + mesecPrometa + "/" + godinaPrometa,
        LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
        firmaData.getString("FIRMA_MESTO_IZDAVANJA_RACUNA"),
        ldateDatumPrometa.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
        firmaData.getString("FIRMA_MESTO_PROMETA_DOBARA"),
        firmaData.getString("FIRMA_ROK_PLACANJA_FAKTURA"),
        firmaData.getString("FIRMA_NACIN_PLACANJA_FAKTURA")
    ));
    tRacunPodaci.setFont(font);

//ADRESA POSILJKE
    VBox adresaPosiljke = new VBox();
    adresaPosiljke.setStyle(cssStyleProperty.getBorder(0.5, 0.5, 0.5, 0.5));
    adresaPosiljke.setMinWidth(200);
    adresaPosiljke.setFillWidth(true);

    Text tNazivFirmeAdresa = new Text(racun.getNazivFirme());
    tNazivFirmeAdresa.setFont(fontADRESA);
    tNazivFirmeAdresa.minHeight(40);
    Text tAdresaFirme = new Text(racun.getAdresaFirme());
    tAdresaFirme.setFont(fontADRESA);
    Text tMestoFirme = new Text(racun.getMestoFirme());
    tMestoFirme.setFont(fontADRESA);
    Text tPIBFirme = new Text(racun.getPIB());
    tPIBFirme.setFont(fontBold);
    Text tMatFirme = new Text(racun.getMaticniBrojFirme());
    tMatFirme.setFont(fontBold);
    Text tTelFirme = new Text(racun.getKontakOsobaFirme());
    tTelFirme.setFont(fontBold);
    Text tFaxFirme = new Text(racun.getFAX());
    tFaxFirme.setFont(fontBold);
    Text tTekuciRacunFirme = new Text(racun.getTekuciRacunFirme());
    tTekuciRacunFirme.setFont(fontBold);
    Text tdokumDown = new Text(
        "Dokument izdat po Zakonu o porezu na dodatu vrednost i ostalim propisima");
    tdokumDown.setFont(fontBold);

    Text textPIB = new Text("PIB: ");
    textPIB.setFont(font);
    Text textMAT = new Text("\tMat: ");
    textMAT.setFont(font);
    Text textTelefon = new Text("Telefon: ");
    textTelefon.setFont(font);
    Text textFAX = new Text("\tFax: ");
    textFAX.setFont(font);

    Text textTekRacun = new Text("Tekući račun: ");
    textTekRacun.setFont(font);

    HBox rowPBMAT = new HBox();
    rowPBMAT.getChildren().addAll(textPIB, tPIBFirme, textMAT, tMatFirme);

    HBox rowTELFAX = new HBox();
    rowTELFAX.getChildren().addAll(textTelefon, tTelFirme, textFAX, tFaxFirme);

    HBox rowTekuciRacun = new HBox();
    rowTekuciRacun.getChildren().addAll(textTekRacun, tTekuciRacunFirme);

    adresaPosiljke.getChildren().add(tNazivFirmeAdresa);
    adresaPosiljke.getChildren().add(tAdresaFirme);
    adresaPosiljke.getChildren().add(tMestoFirme);
    adresaPosiljke.getChildren().addAll(rowPBMAT, rowTELFAX, rowTekuciRacun);
    adresaPosiljke.setPadding(new Insets(5, 5, 5, 5));
    adresaPosiljke.setAlignment(Pos.CENTER);
    adresaPosiljke.setFillWidth(true);

    //SPECIFIKACIJA POREZA
    //COD
    ArrayList<Racun> arrPorRacun = new ArrayList<>();

    for (int n = 0; n < userRacun.size() - 1; n++) {
      Racun racunSpecPoreza = new Racun();
      Racun racunN = userRacun.get(n);
      for (int z = n + 1; z < userRacun.size(); z++) {
        Racun racunZ = userRacun.get(z);
        if (racunN.getStopaPDV() == racunZ.getStopaPDV()) {
          racunSpecPoreza.setStopaPDV(racunZ.getStopaPDV());
          racunSpecPoreza.setOsnovica(racunZ.getOsnovica() + racunSpecPoreza.getOsnovica());
          racunSpecPoreza.setPdv(racunSpecPoreza.getPdv() + racunZ.getPdv());
          racunSpecPoreza
              .setUkupno(racunSpecPoreza.getUkupno() + racunZ.getPdv() + racunZ.getOsnovica());
          userRacun.remove(z);
        } else {
          racunSpecPoreza.setStopaPDV(racunN.getStopaPDV());
          racunSpecPoreza.setOsnovica(racunN.getOsnovica());
          racunSpecPoreza.setPdv(racunN.getPdv());
          racunSpecPoreza.setUkupno(racunN.getPdv() + racunN.getOsnovica());
        }


      }
      arrPorRacun.add(racunSpecPoreza);
    }

    VBox rowSpecPoreze = new VBox();
    Text specPorezaTitle = new Text("Specifikacija poreza: ");
    specPorezaTitle.setFont(fontBold);
    rowSpecPoreze.getChildren().add(specPorezaTitle);

    double ukupnoOsno = 0.00;
    double ukupnoPDV = 0.00;
    double ukupnoOsnovUkupno = 0.00;

    for (int l = 0; l < arrPorRacun.size(); l++) {
      String strPor = String
          .format("OSNOVICA za PDV: \t %f + PDV %f %% : \t%f = vrednost sa PDV: %f",
              arrPorRacun.get(l).getOsnovica(), arrPorRacun.get(l).getStopaPDV(),
              arrPorRacun.get(l).getPdv(), arrPorRacun.get(l).getUkupno());
      ukupnoOsno += arrPorRacun.get(l).getOsnovica();
      ukupnoPDV += arrPorRacun.get(l).getPdv();
      ukupnoOsnovUkupno += arrPorRacun.get(l).getUkupno();
      Text textPor = new Text(strPor);
      textPor.setFont(font);
      rowSpecPoreze.getChildren().add(textPor);
    }
    Text textUkupnoAll = new Text(
        String.format("Ukupno osnovica: \t %f \t ukupno pdv: \t %f \t UKUPNO: \t %f",
            ukupnoOsno, ukupnoPDV, ukupnoOsnovUkupno
        ));
    textUkupnoAll.setFont(font);
    rowSpecPoreze.getChildren().add(textUkupnoAll);

    for (int a = 0; a < arrPorRacun.size(); a++) {
      System.out
          .println(String.format("OSNOVICA za PDV: \t %f + PDV %f %% : \t%f = vrednost sA PDV: %f",
              arrPorRacun.get(a).getOsnovica(), arrPorRacun.get(a).getStopaPDV(),
              arrPorRacun.get(a).getPdv(), arrPorRacun.get(a).getUkupno()));

    }




    anchorPane.setMinSize(MAX_WIDTH, MAX_HEIGHT);
    anchorPane.setMaxSize(MAX_WIDTH, MAX_HEIGHT);
    anchorPane.setPrefSize(MAX_WIDTH, MAX_HEIGHT);
    anchorPane.getChildren().add(tableTopRacun);

    anchorPane.getChildren().add(tRacunPodaci);
    anchorPane.getChildren().add(adresaPosiljke);
    anchorPane.getChildren().add(table);
    anchorPane.getChildren().add(rowSpecPoreze);

    AnchorPane.setTopAnchor(tableTopRacun, 20.0);
    AnchorPane.setTopAnchor(tRacunPodaci, 120.0);
    AnchorPane.setTopAnchor(adresaPosiljke, 120.0);
    AnchorPane.setLeftAnchor(adresaPosiljke, 340.0);
    AnchorPane.setTopAnchor(table, 240.00);
    anchorPane.setTopAnchor(rowSpecPoreze, 700.0);

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
