package net.yuvideo.jgemstone.client.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import javafx.stage.Window;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Mesta;
import net.yuvideo.jgemstone.client.classes.Users;
import net.yuvideo.jgemstone.client.classes.Racun;
import net.yuvideo.jgemstone.client.classes.Printing.PrintRacun;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import org.json.JSONObject;
import javafx.print.PrinterJob;
import javafx.print.JobSettings;

import java.net.URL;
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
  public Client client;
  SimpleDateFormat df = new SimpleDateFormat("MM-yyyy");
  private ResourceBundle resources;
  private URL location;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

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
    for (int i = 0; i < usersArrayList.size(); i++) {
      if (usersArrayList.get(i).getId() >= odId && usersArrayList.get(i).getId() <= doId) {
        tmp.add(usersArrayList.get(i));
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
    for (int i = 0; i < usersArrayList.size(); i++) {
      System.out.println(
          String.format("jmesto:%s, brMesta: %s", usersArrayList.get(i).getjMesto(), brMesta));
      if (usersArrayList.get(i).getjMesto().equals(brMesta)) {
        tmp.add(usersArrayList.get(i));
      }
    }

    tblKorisnici.setItems(FXCollections.observableArrayList(tmp));
  }

  public void showStampa(ActionEvent actionEvent) {
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
      PrintRacun printRacun = new PrintRacun();
      printRacun.setPrinterData(printerSettngs, printerJob.getPrinter());

      racun.initRacun(
          user.getId(),
          dtpZaMesec.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM")),
          this.client);
      printRacun.userRacun = racun.getRacunArrayList();
      printRacun.printRacun();

      System.out.println(
          String.format(
              "Stampam id: %s, ime: %s dug: %f",
              selectedItems.get(i).getId(),
              selectedItems.get(i).getIme(),
              selectedItems.get(i).getDug()));
    }
  }

  public void showPregled(ActionEvent actionEvent) {}

  public void setData() {
    setDataMesta();
  }

  public void izaberiSve(ActionEvent actionEvent) {
    tblKorisnici.getSelectionModel().selectAll();
  }
}
