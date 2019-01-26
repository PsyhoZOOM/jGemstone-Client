package net.yuvideo.jgemstone.client.classes.Printing;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
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
  private DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
  public JSONObject firmaData;
  private PrinterJob printerJob;


  public void printFaktura() {
    printerJob = PrinterJob.createPrinterJob();

    printerJob.getJobSettings().setPageLayout(printerJob.getPrinter()
        .createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 20, 20, 20, 20));
    printerJob.showPrintDialog(null);

    final double _C_NAZIV = 100;
    final double _C_JMERE = 30;
    final double _C_KOLICINA = 45;
    final double _C_CENA = 60;
    final double _C_STOPA = 20;
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

    String racunKorisnika = racun.getSifraKorisnika() + "/" + mesecPrometa + "/" + godinaPrometa;

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
    ArrayList<Racun> arrPorRacun = userRacun;
    //last index is info not a values of debt, so we need to remove it
    int size = userRacun.size();
    userRacun.remove(size - 1);

    for (int n = 0; n < arrPorRacun.size(); n++) {
      Racun racunN = arrPorRacun.get(n);
      for (int z = n + 1; z < arrPorRacun.size(); z++) {
        Racun racunZ = arrPorRacun.get(z);
        if (racunN.getStopaPDV() == racunZ.getStopaPDV()) {
          arrPorRacun.get(n).setStopaPDV(racunZ.getStopaPDV());
          arrPorRacun.get(n).setPdv(racunN.getPdv() + racunZ.getPdv());
          arrPorRacun.get(n).setOsnovica(racunN.getOsnovica() + racunZ.getOsnovica());
          arrPorRacun.get(n).setUkupno(racunN.getUkupno() + racunZ.getOsnovica() + racunZ.getPdv());
          arrPorRacun.remove(z);
          z--;
        }
      }
    }

    VBox rowSpecPoreze = new VBox();
    HBox cellPor = new HBox();
    rowSpecPoreze.setFillWidth(true);
    Text specPorezaTitle = new Text("Specifikacija poreza:");
    specPorezaTitle.setFont(fontBold);
    rowSpecPoreze.getChildren().add(specPorezaTitle);

    double ukupnoOsno = 0.00;
    double ukupnoPDV = 0.00;
    double ukupnoOsnovUkupno = 0.00;

    HBox rowOsnovicaZaPDV;
    for (int l = 0; l < arrPorRacun.size(); l++) {
      rowOsnovicaZaPDV = new HBox();

      Text osnovZaPDVLabel = new Text("Osnovica za PDV:");
      Text osnovZaPDVCENA = new Text(df.format(arrPorRacun.get(l).getOsnovica()));
      osnovZaPDVCENA.setFont(font);
      osnovZaPDVLabel.setFont(font);
      cellPor = new HBox(osnovZaPDVLabel);
      cellPor.setMinWidth(80);
      cellPor.setMaxWidth(80);
      cellPor.setAlignment(Pos.CENTER_RIGHT);
      rowOsnovicaZaPDV.getChildren().add(cellPor);
      cellPor = new HBox(osnovZaPDVCENA);
      cellPor.setMinWidth(80);
      cellPor.setMaxWidth(80);
      cellPor.setAlignment(Pos.CENTER_RIGHT);
      rowOsnovicaZaPDV.getChildren().add(cellPor);

      Text osnovPDVStopa = new Text(
          String.format("+ PDV ", df.format(arrPorRacun.get(l).getStopaPDV())));
      Text osnoPDVStopCena = new Text(df.format(arrPorRacun.get(l).getStopaPDV()) + " %:");
      osnoPDVStopCena.setFont(font);
      osnovPDVStopa.setFont(font);

      cellPor = new HBox(osnovPDVStopa);
      cellPor.setMinWidth(50);
      cellPor.setMaxWidth(50);
      cellPor.setAlignment(Pos.CENTER);
      rowOsnovicaZaPDV.getChildren().add(cellPor);
      cellPor = new HBox(osnoPDVStopCena);
      cellPor.setMinWidth(50);
      cellPor.setMaxWidth(50);
      cellPor.setAlignment(Pos.CENTER_RIGHT);
      rowOsnovicaZaPDV.getChildren().add(cellPor);

      Text osnovPDV = new Text(String.format(df.format(arrPorRacun.get(l).getPdv())));
      osnovPDV.setFont(font);
      cellPor = new HBox(osnovPDV);
      cellPor.setMinWidth(80);
      cellPor.setMaxWidth(80);
      cellPor.setAlignment(Pos.CENTER_RIGHT);
      rowOsnovicaZaPDV.getChildren().add(cellPor);
      Text osnovVrednostSaPDV = new Text(" = vrednost sa PDV:");
      Text osnovVrednostSaPDVCena = new Text(df.format(arrPorRacun.get(l).getUkupno()));
      osnovVrednostSaPDVCena.setFont(font);
      osnovVrednostSaPDV.setFont(font);
      cellPor = new HBox(osnovVrednostSaPDV);
      cellPor.setMinWidth(80);
      cellPor.setMaxWidth(80);
      cellPor.setAlignment(Pos.CENTER_RIGHT);
      rowOsnovicaZaPDV.getChildren().add(cellPor);
      cellPor = new HBox(osnovVrednostSaPDVCena);
      cellPor.setMinWidth(80);
      cellPor.setMaxWidth(80);
      cellPor.setAlignment(Pos.CENTER_RIGHT);
      rowOsnovicaZaPDV.getChildren().add(cellPor);

      rowSpecPoreze.getChildren().add(rowOsnovicaZaPDV);



      ukupnoOsno += arrPorRacun.get(l).getOsnovica();
      ukupnoPDV += arrPorRacun.get(l).getPdv();
      ukupnoOsnovUkupno += arrPorRacun.get(l).getUkupno();
      //rowSpecPoreze.getChildren().add(textPor);
    }

    rowOsnovicaZaPDV = new HBox();

    Text osnoUkupnoOsno = new Text("Ukupno:");
    Text osnoUkupnoOsnoCena = new Text(df.format(ukupnoOsno));
    osnoUkupnoOsnoCena.setFont(font);
    osnoUkupnoOsno.setFont(font);
    cellPor = new HBox(osnoUkupnoOsno);
    cellPor.setMinWidth(80);
    cellPor.setMaxWidth(80);
    cellPor.setAlignment(Pos.CENTER_RIGHT);
    rowOsnovicaZaPDV.getChildren().add(cellPor);
    cellPor = new HBox(osnoUkupnoOsnoCena);
    cellPor.setMinWidth(80);
    cellPor.setMaxWidth(80);
    cellPor.setAlignment(Pos.CENTER_RIGHT);
    rowOsnovicaZaPDV.getChildren().add(cellPor);

    Text osnoUkupnoPDV = new Text("ukupno:");
    Text osnoUkupnoPDVCena = new Text(df.format(ukupnoPDV));
    osnoUkupnoPDVCena.setFont(font);
    osnoUkupnoPDV.setFont(font);
    cellPor = new HBox(osnoUkupnoPDV);
    cellPor.setAlignment(Pos.CENTER_RIGHT);
    cellPor.setMinWidth(100);
    cellPor.setMaxWidth(100);
    rowOsnovicaZaPDV.getChildren().add(cellPor);
    cellPor = new HBox(osnoUkupnoPDVCena);
    cellPor.setAlignment(Pos.CENTER_RIGHT);
    cellPor.setMinWidth(80);
    cellPor.setMaxWidth(80);
    rowOsnovicaZaPDV.getChildren().add(cellPor);

    Text osnoUkupnoSve = new Text("ukupno:");
    Text osnoUkupnoSveCena = new Text(df.format(ukupnoOsnovUkupno));
    osnoUkupnoSveCena.setFont(fontBold);
    osnoUkupnoSve.setFont(font);
    cellPor = new HBox(osnoUkupnoSve);
    cellPor.setAlignment(Pos.CENTER_RIGHT);
    cellPor.setMinWidth(80);
    cellPor.setMaxWidth(80);
    rowOsnovicaZaPDV.getChildren().add(cellPor);
    cellPor = new HBox(osnoUkupnoSveCena);
    cellPor.setAlignment(Pos.CENTER_RIGHT);
    cellPor.setMinWidth(80);
    cellPor.setMaxWidth(80);
    rowOsnovicaZaPDV.getChildren().add(cellPor);
    rowSpecPoreze.getChildren().add(rowOsnovicaZaPDV);

    rowOsnovicaZaPDV = new HBox();
    rowOsnovicaZaPDV.setAlignment(Pos.CENTER_RIGHT);

    Text ukpnaVrednostSaPDV = new Text("UKUPNA VREDNOST sa PDV: ");
    Text ukpnaVrednostSaPDVCena = new Text(df.format(ukupnoOsnovUkupno));
    ukpnaVrednostSaPDV.setFont(fontBold);
    ukpnaVrednostSaPDVCena.setFont(fontADRESA);
    cellPor = new HBox(ukpnaVrednostSaPDV);
    cellPor.setAlignment(Pos.CENTER_RIGHT);
    rowOsnovicaZaPDV.getChildren().add(cellPor);
    cellPor = new HBox(ukpnaVrednostSaPDVCena);
    cellPor.setAlignment(Pos.CENTER_RIGHT);
    rowOsnovicaZaPDV.getChildren().add(cellPor);
    rowSpecPoreze.getChildren().add(rowOsnovicaZaPDV);

    Text podaciOOdgLicu = new Text("Podaci o odgovornom licu:");
    podaciOOdgLicu.setFont(fontBold);

    Text potipisOdgLica = new Text("_______________________________\npotpis odgovornog lica");
    potipisOdgLica.setFont(fontSmall);
    potipisOdgLica.setTextAlignment(TextAlignment.CENTER);

    Text mp1 = new Text("M.P");
    Text mp2 = new Text("M.P");
    mp1.setFont(fontSmall);
    mp2.setFont(fontSmall);

    Text imePrezimeOdgLica = new Text(
        "_______________________________\nime i prezime odgovornog lica");
    imePrezimeOdgLica.setFont(fontSmall);
    imePrezimeOdgLica.setTextAlignment(TextAlignment.CENTER);

    Text adresabrTel = new Text("_______________________________\nadresa i broj telefona odg.lica");
    adresabrTel.setFont(fontSmall);
    adresabrTel.setTextAlignment(TextAlignment.CENTER);

    Text napomenaOporOs = new Text("napomena o poreskom oslobođenju: ");
    napomenaOporOs.setFont(fontSmall);

    Text podaciOprimaocu = new Text("Podaci o primaocu dobara:");
    podaciOprimaocu.setFont(fontBold);

    Text primioIkontr = new Text("_______________________________\nprimio i kontrolisao");
    primioIkontr.setFont(fontSmall);
    primioIkontr.setTextAlignment(TextAlignment.CENTER);

    Text napomenaFin = new Text(String.format(
        "Prilikom uplate upisati broj računa u poziv na broj odobrenja na nalogu za prenos: %s",
        racunKorisnika));
    napomenaFin.setFont(fontBold);

    VBox rowFin = new VBox();
    rowFin.setFillWidth(true);
    rowFin.setSpacing(10);
    rowFin.setPadding(new Insets(5, 20, 20, 5));
    rowFin.getChildren().add(podaciOOdgLicu);
    HBox hBoxFin = new HBox();
    hBoxFin.setAlignment(Pos.BOTTOM_CENTER);
    hBoxFin.setSpacing(10);
    hBoxFin.getChildren().addAll(potipisOdgLica, mp1, imePrezimeOdgLica, adresabrTel);
    rowFin.getChildren().add(hBoxFin);
    rowFin.getChildren().add(napomenaOporOs);
    rowFin.getChildren().add(podaciOprimaocu);
    hBoxFin = new HBox();
    hBoxFin.setSpacing(10);
    hBoxFin.getChildren().addAll(primioIkontr, mp2);
    rowFin.getChildren().add(hBoxFin);
    rowFin.getChildren().add(napomenaFin);


    anchorPane.getStylesheets().removeAll();


    anchorPane.getChildren().add(tableTopRacun);

    anchorPane.getChildren().add(tRacunPodaci);
    anchorPane.getChildren().add(adresaPosiljke);
    anchorPane.getChildren().add(table);
    anchorPane.getChildren().add(rowSpecPoreze);
    anchorPane.getChildren().add(rowFin);

    AnchorPane.setTopAnchor(tableTopRacun, 10.0);
    AnchorPane.setTopAnchor(tRacunPodaci, 120.0);
    AnchorPane.setTopAnchor(adresaPosiljke, 120.0);
    AnchorPane.setLeftAnchor(adresaPosiljke, 320.0);
    AnchorPane.setTopAnchor(table, 240.00);
    AnchorPane.setLeftAnchor(table, 10.0);
    AnchorPane.setTopAnchor(rowSpecPoreze, 620.0);
    AnchorPane.setLeftAnchor(rowSpecPoreze, 10.0);
    AnchorPane.setTopAnchor(rowFin, 680.0);


    anchorPane.setStyle("-fx-background-color: white;");
    PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();
    double w = pageLayout.getPrintableWidth();
    double h = pageLayout.getPrintableHeight();

    anchorPane.setPrefSize(w, h);
    anchorPane.setTranslateY(0);
    anchorPane.setTranslateX(0);

    if (showPreview) {
      Scene scene = new Scene(anchorPane);
      Stage stage = new Stage();
      stage.setScene(scene);
      stage.showAndWait();
    }else {

      boolean succ = printerJob.printPage(anchorPane);
      if (succ) {
        printerJob.endJob();
      }
    }


  }
}
