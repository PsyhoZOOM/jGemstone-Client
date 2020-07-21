package net.yuvideo.jgemstone.client.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.*;
import net.yuvideo.jgemstone.client.classes.Printing.PrintFaktura;
import net.yuvideo.jgemstone.client.classes.Printing.PrintRacun;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StampaRacuna implements Initializable {

  public TextField idOd;
  public TextField idDo;
  public Button bIdShow;
  public ComboBox<Mesta> cmbMesto;
  public Button bCmbShow;
  public TableView<Users> tblKorisnici;
  public TableColumn<Users, String> cID;
  public TableColumn<Users, String> cIME;
  public TableColumn<Users, Double> cZaUplatu;
  public Button bPregled;
  public Button bStampa;
  public DatePicker dtpZaMesec;
  public CheckBox chkFaktura;
  SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
  DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.00");
  private ResourceBundle resources;
  private URL location;
  private Client client;
  private boolean jobDone = false;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    cID.setCellValueFactory(new PropertyValueFactory<>("jbroj"));
    cIME.setCellValueFactory(new PropertyValueFactory<>("ime"));
    cZaUplatu.setCellValueFactory(new PropertyValueFactory<>("dug"));

    cZaUplatu.setCellFactory(new Callback<TableColumn<Users, Double>, TableCell<Users, Double>>() {
      @Override
      public TableCell<Users, Double> call(TableColumn<Users, Double> param) {
        return new TableCell<Users, Double>() {
          @Override
          protected void updateItem(Double item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
              setText("");
            } else {
              setText(decimalFormat.format(item));
            }
          }
        };
      }
    });

    cmbMesto.setConverter(
        new StringConverter<Mesta>() {
          @Override
          public String toString(Mesta object) {
            return object.getNazivMesta();
          }

          @Override
          public Mesta fromString(String string) {
            Mesta mesta = new Mesta();
            mesta.setNazivMesta(string);
            return mesta;
          }
        });

    LocalDate ldate = LocalDate.now();
    ldate = ldate.minusMonths(1);
    dtpZaMesec.setValue(ldate);
    dtpZaMesec.setConverter(
        new StringConverter<LocalDate>() {
          private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-yyyy");

          @Override
          public String toString(LocalDate object) {
            return object.format(dateTimeFormatter);
          }

          @Override
          public LocalDate fromString(String string) {
            LocalDate localDate = LocalDate.parse(string, dateTimeFormatter);
            return localDate;
          }
        });

    tblKorisnici.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
  }

  private void setDataMesta() {
    ArrayList<Mesta> mestaArrayList = new ArrayList<>();
    JSONObject mestaObj = new JSONObject();
    mestaObj.put("action", "getMesta");
    mestaObj = client.send_object(mestaObj);

    for (int i = 0; i < mestaObj.length(); i++) {
      JSONObject mestaO = mestaObj.getJSONObject(String.valueOf(i));
      Mesta mesta = new Mesta();
      mesta.setId(mestaO.getInt("id"));
      mesta.setBrojMesta(mestaO.getString("brojMesta"));
      mesta.setNazivMesta(mestaO.getString("nazivMesta"));
      mestaArrayList.add(mesta);
    }

    cmbMesto.setItems(FXCollections.observableArrayList(mestaArrayList));
  }

  public void prikaziId(ActionEvent actionEvent) {
    int odId = Integer.parseInt(idOd.getText());
    int doId = Integer.parseInt(idDo.getText());
    Users users = new Users();
    ArrayList<Users> usersArrayList = users.getUsers(client);
    ArrayList<Users> tmp = new ArrayList<>();
    boolean fakture = chkFaktura.isSelected();
    for (int i = 0; i < usersArrayList.size(); i++) {
      boolean firma = usersArrayList.get(i).isFirma();
      if (usersArrayList.get(i).getId() >= odId && usersArrayList.get(i).getId() <= doId) {
        if (fakture) {
          if (firma) {
            tmp.add(usersArrayList.get(i));
          }
        } else {
          tmp.add(usersArrayList.get(i));
        }
      }
    }

    tblKorisnici.setItems(FXCollections.observableArrayList(tmp));
  }

  public void prikaziMesta(ActionEvent actionEvent) {

    tblKorisnici.getItems().removeAll();
    tblKorisnici.refresh();
    String brMesta = cmbMesto.getValue().getBrojMesta();
    Users users = new Users();
    ArrayList<Users> usersArrayList = users.getUsers(client);
    ArrayList<Users> tmp = new ArrayList<>();
    boolean fakture = chkFaktura.isSelected();
    for (int i = 0; i < usersArrayList.size(); i++) {
      boolean firma = usersArrayList.get(i).isFirma();
      if (fakture) {
        if (firma && usersArrayList.get(i).getjMesto().equals(brMesta)) {
          tmp.add(usersArrayList.get(i));
        }
      } else if (usersArrayList.get(i).getjMesto().equals(brMesta)) {
        tmp.add(usersArrayList.get(i));
      }
    }

    tblKorisnici.setItems(FXCollections.observableArrayList(tmp));
  }

  public void showStampa(ActionEvent actionEvent){
    printRacun(false);
  }

  public void printRacun(boolean onlyPreview) {
    if (tblKorisnici.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.error("GREŠKA", "Nije izabran korisnik za štampu");
      return;
    }
    ObservableList<Users> selectedItems = tblKorisnici.getSelectionModel().getSelectedItems();
    Window wind = bStampa.getScene().getWindow();

    Printer printer = Printer.getDefaultPrinter();
    if (printer == null)
      for (Printer pr : Printer.getAllPrinters()) {
        if (pr != null)
          printer = pr;
        break;
      }
    System.out.println(printer.getName());
    PrinterJob printerJob = PrinterJob.createPrinterJob(printer);
    boolean job = printerJob.showPrintDialog(this.bIdShow.getScene().getWindow());
    if (!job)
      return;

    System.out.println(printerJob.getPrinter().getName());

    printerJob.getJobSettings().setPageLayout(printerJob.getPrinter()
            .createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 20, 20, 20, 20));


    for (int i = 0; i < selectedItems.size(); i++) {
      Racun racun = new Racun();
      Users user = selectedItems.get(i);
      FirmaSettings firmaSettings = new FirmaSettings(client);
      PrintRacun printRacun;
      PrintFaktura printFaktura;

      if (chkFaktura.isSelected()) {
        printFaktura = new PrintFaktura();
        printFaktura.showPreview=onlyPreview;
        printFaktura.firmaData = firmaSettings.getJsonObject();
        if (firmaSettings.getFIRMA_FAKTURA_PEPDV().equals("da")) {
          printFaktura.printPDV = true;
        } else {
          printFaktura.printPDV = false;
        }
        racun.initRacun(
            user.getId(),
            dtpZaMesec.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM")),
            this.client);
        printFaktura.userRacun = racun.getRacunArrayList();
        jobDone = printFaktura.printFaktura(firmaSettings, printerJob);

      } else {
        printRacun = new PrintRacun();
        printRacun.showPreview=onlyPreview;
        printRacun.firmaData = firmaSettings.getJsonObject();
        if (firmaSettings.getFIRMA_FAKTURA_PEPDV().equals("da")) {
          printRacun.printPDV = true;
        } else {
          printRacun.printPDV = false;
        }
        //ako je samo preview nema potrebe za stampacem

        racun.initRacun(
                user.getId(),
                dtpZaMesec.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM")),
                this.client);
        printRacun.userRacun = racun.getRacunArrayList();
        jobDone = printRacun.printRacun(firmaSettings, printerJob);
      }
    }
    if (jobDone)
      printerJob.endJob();
  }

  public void showPregled(ActionEvent actionEvent) {
    printRacun(true);
  }

  public void setData() {
    setDataMesta();
  }

  public void izaberiSve(ActionEvent actionEvent) {
    tblKorisnici.getSelectionModel().selectAll();
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
