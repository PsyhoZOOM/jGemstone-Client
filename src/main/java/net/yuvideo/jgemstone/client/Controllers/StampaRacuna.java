package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.print.JobSettings;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Window;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.FirmaSettings;
import net.yuvideo.jgemstone.client.classes.Mesta;
import net.yuvideo.jgemstone.client.classes.Printing.PrintFaktura;
import net.yuvideo.jgemstone.client.classes.Printing.PrintRacun;
import net.yuvideo.jgemstone.client.classes.Racun;
import net.yuvideo.jgemstone.client.classes.Settings;
import net.yuvideo.jgemstone.client.classes.Users;
import org.json.JSONObject;

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
  public Settings LocalSettings;
  SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
  private ResourceBundle resources;
  private URL location;
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
    this.client = new Client(LocalSettings);

    cID.setCellValueFactory(new PropertyValueFactory<>("jbroj"));
    cIME.setCellValueFactory(new PropertyValueFactory<>("ime"));
    cZaUplatu.setCellValueFactory(new PropertyValueFactory<>("dug"));

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

    PrinterJob printerJob = PrinterJob.createPrinterJob();
    boolean b = printerJob.showPrintDialog(wind);
    JobSettings printerSettngs = printerJob.getJobSettings();

    if (!b) {
      return;
    }

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
        printFaktura.setPrinterData(printerSettngs, printerJob.getPrinter());
        racun.initRacun(
            user.getId(),
            dtpZaMesec.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM")),
            this.client);
        printFaktura.userRacun = racun.getRacunArrayList();
        printFaktura.printFaktura();

      } else {
        printRacun = new PrintRacun();
        printRacun.showPreview=onlyPreview;
        printRacun.firmaData = firmaSettings.getJsonObject();
        printRacun.setPrinterData(printerSettngs, printerJob.getPrinter());

        racun.initRacun(
            user.getId(),
            dtpZaMesec.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM")),
            this.client);
        printRacun.userRacun = racun.getRacunArrayList();
        printRacun.printRacun();
      }
    }
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
}
