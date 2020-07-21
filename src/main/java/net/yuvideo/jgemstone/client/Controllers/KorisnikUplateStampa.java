package net.yuvideo.jgemstone.client.Controllers;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Window;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Uplate;
import net.yuvideo.jgemstone.client.classes.UserData;

import java.text.DecimalFormat;

public class KorisnikUplateStampa {

  private final ObservableList<TreeItem<Uplate>> uplateUser;
  private final Window window;
  private final UserData user;
  private PrinterJob printerJob;
  private DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");

  private static final String TABLE_BORDER =
          "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 1;";
  private static final String headerRowStyleLMiddle =
          "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 0;";
  private static final String headerRowStyleRight =
          "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 1 1 1 0;";
  private static final String cellRowStyle =
          "-fx-border-style: solid; -fx-border-color: black; -fx-border-width: 0 0.2 0.2 0;";

  private final double PADDING = 3;
  private final double ROW_WIDTH = 80;
  private final double ROW_IME_WIDTH = 160;

  public KorisnikUplateStampa(
          ObservableList<TreeItem<Uplate>> uplateUser, Window window, UserData userData) {
    this.uplateUser = uplateUser;
    this.window = window;
    this.user = userData;
    initPrinter();
  }

  private void initPrinter() {
    for (Printer print : Printer.getAllPrinters()) {
      if (print != null) {
        printerJob = PrinterJob.createPrinterJob(print);
        boolean b = printerJob.showPrintDialog(window);
        if (b) break;
      }
    }

    if (printerJob == null) AlertUser.error("GRESKA", "Greska sa Stampacem!");

    printerJob
            .getJobSettings()
            .setPageLayout(
                    printerJob
                            .getPrinter()
                            .createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 20, 20, 20, 20));

    printUplate();
  }

  private void printUplate() {
    AnchorPane anchorPane = new AnchorPane();

    Font font =
            Font.loadFont(
                    getClass().getResource("/font/roboto/Roboto-Regular.ttf").toExternalForm(), 8);
    Font fontBolds =
            Font.loadFont(getClass().getResource("/font/roboto/Roboto-Bold.ttf").toExternalForm(), 5);
    Font fontBold =
            Font.loadFont(getClass().getResource("/font/roboto/Roboto-Bold.ttf").toExternalForm(), 8);
    Font fontBoldL =
            Font.loadFont(
                    getClass().getResource("/font/roboto/Roboto-Regular.ttf").toExternalForm(), 10);
    Font fontADRESA =
            Font.loadFont(getClass().getResource("/font/roboto/Roboto-Bold.ttf").toExternalForm(), 12);
    Font fontRacunDole =
            Font.loadFont(getClass().getResource("/font/roboto/Roboto-Bold.ttf").toExternalForm(), 8);
    Font fontRacunDoleNormal =
            Font.loadFont(
                    getClass().getResource("/font/roboto/Roboto-Regular.ttf").toExternalForm(), 8);

    VBox table = new VBox();
    table.setStyle(TABLE_BORDER);
    table.setFillWidth(true);
    HBox row;
    HBox cell;

    row = new HBox();

    // IME PREZIME COLUMN
    Label ime = new Label("IME I PREZIME");
    ime.setFont(fontBold);
    cell = new HBox(ime);
    cell.setMinWidth(ROW_IME_WIDTH);
    cell.setPadding(new Insets(PADDING));
    cell.setAlignment(Pos.CENTER);
    ;
    cell.setStyle(TABLE_BORDER);
    row.getChildren().add(cell);

    // DATUM COLUMN
    Label datum = new Label("DATUM");
    datum.setFont(fontBold);
    cell = new HBox(datum);
    cell.setMinWidth(ROW_WIDTH);
    cell.setPadding(new Insets(PADDING));
    cell.setAlignment(Pos.CENTER);
    cell.setStyle(TABLE_BORDER);
    row.getChildren().add(cell);

    // DUGUJE COLUMN
    Label duguje = new Label("DUGUJE");
    duguje.setFont(fontBold);
    cell = new HBox(duguje);
    cell.setMinWidth(ROW_WIDTH);
    cell.setPadding(new Insets(PADDING));
    cell.setAlignment(Pos.CENTER);
    cell.setStyle(TABLE_BORDER);
    row.getChildren().add(cell);

    Label potrazuje = new Label("POTRAŽUJE");
    potrazuje.setFont(fontBold);
    cell = new HBox(potrazuje);
    cell.setMinWidth(ROW_WIDTH);
    cell.setPadding(new Insets(PADDING));
    cell.setAlignment(Pos.CENTER);
    cell.setStyle(TABLE_BORDER);
    row.getChildren().add(cell);

    Label opis = new Label("OPIS");
    opis.setFont(fontBold);
    cell = new HBox(opis);
    HBox.setHgrow(cell, Priority.ALWAYS);
    cell.setMinWidth(ROW_WIDTH);
    cell.setPadding(new Insets(PADDING));
    cell.setAlignment(Pos.CENTER);
    cell.setStyle(TABLE_BORDER);
    row.getChildren().add(cell);

    table.getChildren().add(row);

    double dug = 0.00;
    double pot = 0.00;
    for (int i = 0; i < uplateUser.size(); i++) {
      Uplate uplata = uplateUser.get(i).getValue();
      dug += uplata.getDuguje();
      pot += uplata.getPotrazuje();
      row = new HBox();

      Label lIme = new Label(user.getIme());
      lIme.setFont(font);
      cell = new HBox(lIme);
      cell.setMinWidth(ROW_IME_WIDTH);
      cell.setPadding(new Insets(PADDING));
      cell.setAlignment(Pos.CENTER);
      cell.setStyle(cellRowStyle);
      row.getChildren().add(cell);

      Label lDatum = new Label(uplata.getDatum());
      lDatum.setFont(font);
      cell = new HBox(lDatum);
      cell.setMinWidth(ROW_WIDTH);
      cell.setPadding(new Insets(PADDING));
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setStyle(cellRowStyle);
      row.getChildren().add(cell);

      Label lDuguje = new Label(df.format(uplata.getDuguje()));
      lDuguje.setFont(font);
      cell = new HBox(lDuguje);
      cell.setMinWidth(ROW_WIDTH);
      cell.setPadding(new Insets(PADDING));
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setStyle(cellRowStyle);
      row.getChildren().add(cell);

      Label lPotrazuje = new Label(df.format(uplata.getPotrazuje()));
      lPotrazuje.setFont(font);
      cell = new HBox(lPotrazuje);
      cell.setMinWidth(ROW_WIDTH);
      cell.setPadding(new Insets(PADDING));
      cell.setAlignment(Pos.CENTER_RIGHT);
      cell.setStyle(cellRowStyle);
      row.getChildren().add(cell);

      Label lOpis = new Label(uplata.getOpis());
      lOpis.setFont(font);
      lOpis.setAlignment(Pos.CENTER);
      cell = new HBox(lOpis);
      HBox.setHgrow(cell, Priority.ALWAYS);
      cell.setAlignment(Pos.CENTER);
      cell.setMinWidth(ROW_WIDTH);
      cell.setPadding(new Insets(PADDING));
      cell.setStyle(cellRowStyle);
      row.getChildren().add(cell);

      table.getChildren().add(row);
      if (i == uplateUser.size() - 1) {
        row = new HBox();

        cell = new HBox(new Label(" "));
        cell.minWidth(ROW_WIDTH + ROW_IME_WIDTH);
        cell.setPrefWidth(ROW_WIDTH + ROW_IME_WIDTH);
        cell.setStyle(TABLE_BORDER);
        row.getChildren().add(cell);

        Label lUkupnoDuguje = new Label(df.format(dug));
        lUkupnoDuguje.setFont(fontBold);
        cell = new HBox(lUkupnoDuguje);
        cell.minWidth(ROW_WIDTH);
        cell.setAlignment(Pos.CENTER_RIGHT);
        cell.setPrefWidth(ROW_WIDTH);
        cell.setStyle(TABLE_BORDER);
        cell.setPadding(new Insets(PADDING));
        row.getChildren().add(cell);

        Label lUkupnoPotrazuje = new Label(df.format(pot));
        lUkupnoPotrazuje.setFont(fontBold);
        cell = new HBox(lUkupnoPotrazuje);
        cell.setAlignment(Pos.CENTER_RIGHT);
        cell.minWidth(ROW_WIDTH);
        cell.setPrefWidth(ROW_WIDTH);
        cell.setStyle(TABLE_BORDER);
        cell.setPadding(new Insets(PADDING));
        row.getChildren().add(cell);

        table.getChildren().add(row);

        row = new HBox();
        Label ukupno = new Label(String.format("UKUPNO: %s", df.format(pot - dug)));
        ukupno.setFont(fontBold);
        cell = new HBox(ukupno);
        cell.setAlignment(Pos.CENTER_RIGHT);
        cell.setStyle(TABLE_BORDER);
        cell.setPrefWidth(ROW_IME_WIDTH + (ROW_WIDTH * 3));
        cell.setPadding(new Insets(PADDING));
        row.getChildren().add(cell);

        table.getChildren().add(row);
      }
    }
    anchorPane.getChildren().add(table);

    anchorPane.getStylesheets().removeAll();
    anchorPane.setStyle("-fx-background-color: white");
    double w = printerJob.getJobSettings().getPageLayout().getPrintableWidth();
    double h = printerJob.getJobSettings().getPageLayout().getPrintableHeight();

    anchorPane.setPrefSize(w, h);
    anchorPane.setTranslateX(0);
    anchorPane.setTranslateY(0);

    AnchorPane.setTopAnchor(table, printerJob.getJobSettings().getPageLayout().getTopMargin());
    AnchorPane.setLeftAnchor(table, printerJob.getJobSettings().getPageLayout().getLeftMargin());
    AnchorPane.setRightAnchor(table, printerJob.getJobSettings().getPageLayout().getRightMargin());

    boolean success = printerJob.printPage(anchorPane);
    if (success) {
      printerJob.endJob();
    }
  }
}
