package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Settings;
import net.yuvideo.jgemstone.client.classes.pdvObracun;
import org.json.JSONObject;

public class IzvestajPDVObracun implements Initializable {

  public Settings LocalSettings;
  public TextField tOdBrojaUser;
  public TextField tDoBrojaUser;
  public DatePicker dtpOd;
  public DatePicker dtpDo;
  public TableView tblPDV;
  public TableColumn cKorisnik;
  public TableColumn<pdvObracun, Double> cOsnovica;
  public TableColumn<pdvObracun, Double> cPDV;
  public TableColumn<pdvObracun, Double> cUkupno;
  public TableColumn<pdvObracun, Double> cPDVIznos;
  public Label lOsnovica;
  public Label lPDV;
  public Label lUkupno;


  private double ukupanIznos;
  private double ukupnoPDV;
  private double ukupnaOsnovica;


  private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  private DecimalFormat df = new DecimalFormat("###,###,###,###,##0.00");
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    dtpOd.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        if (object == null) {
          return "";
        }
        return dtf.format(object);
      }

      @Override
      public LocalDate fromString(String string) {
        if (string.isEmpty()) {
          return null;
        }
        LocalDate date = LocalDate.parse(string, dtf);
        return date;
      }
    });

    dtpDo.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        if (object == null) {
          return "";
        }
        return dtf.format(object);
      }

      @Override
      public LocalDate fromString(String string) {
        if (string.isEmpty()) {
          return null;
        }
        LocalDate date = LocalDate.parse(string, dtf);
        return date;
      }
    });

    tOdBrojaUser.setText("0");
    tDoBrojaUser.setText("1000");

    dtpOd.setValue(LocalDate.now().minusMonths(3).withDayOfMonth(1));
    dtpDo.setValue(LocalDate.now().withDayOfMonth(1));

    cKorisnik.setCellValueFactory(new PropertyValueFactory<pdvObracun, String>("korisnik"));
    cOsnovica.setCellValueFactory(new PropertyValueFactory<pdvObracun, Double>("osnovica"));
    cPDV.setCellValueFactory(new PropertyValueFactory<pdvObracun, Double>("pdv"));
    cPDVIznos.setCellValueFactory(new PropertyValueFactory<pdvObracun, Double>("pdvIznos"));
    cUkupno.setCellValueFactory(new PropertyValueFactory<pdvObracun, Double>("ukupno"));

    String alignRight = "-fx-alignment: CENTER-RIGHT;";
    cOsnovica.setStyle(alignRight);
    cPDVIznos.setStyle(alignRight);
    cPDV.setStyle(alignRight);
    cUkupno.setStyle(alignRight);
    cOsnovica.setCellFactory(
        new Callback<TableColumn<pdvObracun, Double>, TableCell<pdvObracun, Double>>() {
          @Override
          public TableCell<pdvObracun, Double> call(TableColumn<pdvObracun, Double> param) {
            return new TableCell<pdvObracun, Double>() {
              @Override
              protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  return;
                }
                setText(df.format(item));
              }
            };
          }
        });

    cPDV.setCellFactory(
        new Callback<TableColumn<pdvObracun, Double>, TableCell<pdvObracun, Double>>() {
          @Override
          public TableCell<pdvObracun, Double> call(TableColumn<pdvObracun, Double> param) {
            return new TableCell<pdvObracun, Double>() {
              @Override
              protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  return;
                }
                setText(df.format(item));
              }
            };
          }
        });

    cPDVIznos.setCellFactory(
        new Callback<TableColumn<pdvObracun, Double>, TableCell<pdvObracun, Double>>() {
          @Override
          public TableCell<pdvObracun, Double> call(TableColumn<pdvObracun, Double> param) {
            return new TableCell<pdvObracun, Double>() {
              @Override
              protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  return;
                }
                setText(df.format(item));
              }
            };
          }
        });

    cUkupno.setCellFactory(
        new Callback<TableColumn<pdvObracun, Double>, TableCell<pdvObracun, Double>>() {
          @Override
          public TableCell<pdvObracun, Double> call(TableColumn<pdvObracun, Double> param) {
            return new TableCell<pdvObracun, Double>() {
              @Override
              protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  return;
                }
                setText(df.format(item));
              }
            };
          }
        });

  }

  public void obracunaj(ActionEvent actionEvent) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "getMesecniObracun");
    jsonObject.put("brOd", Integer.valueOf(tOdBrojaUser.getText()));
    jsonObject.put("brDo", Integer.valueOf(tDoBrojaUser.getText()));
    jsonObject.put("odDatuma", dtpOd.getValue().toString());
    jsonObject.put("doDatum", dtpDo.getValue().toString());
    jsonObject = client.send_object(jsonObject);

    setData(jsonObject);
  }

  private void setData(JSONObject jsonObject) {
    tblPDV.refresh();
    ukupnaOsnovica = 0.00;
    ukupnoPDV = 0.00;
    ukupanIznos = 0.00;
    if (jsonObject.has("ERROR")) {
      AlertUser.error("GRESKA", jsonObject.getString("ERROR"));
      return;
    }
    ArrayList<pdvObracun> pdvObracunArrayList = new ArrayList<>();
    for (int i = 0; i < jsonObject.length(); i++) {
      JSONObject obracun = jsonObject.getJSONObject(String.valueOf(i));
      pdvObracun pdvObracun = new pdvObracun();
      pdvObracun.setId(obracun.getInt("id"));
      pdvObracun.setKorisnik(obracun.getString("imePrezime"));
      pdvObracun.setOsnovica(obracun.getDouble("cena"));
      pdvObracun.setPdv(obracun.getDouble("pdv"));
      pdvObracun.setPdvIznos(obracun.getDouble("pdvCena"));
      pdvObracun.setUkupno(obracun.getDouble("ukupno"));
      ukupnaOsnovica += obracun.getDouble("cena");
      ukupnoPDV += obracun.getDouble("pdvCena");
      ukupanIznos += obracun.getDouble("ukupno");
      pdvObracunArrayList.add(pdvObracun);
    }

    ObservableList data = FXCollections.observableArrayList(pdvObracunArrayList);

    tblPDV.setItems(data);
    lOsnovica.setText(
        String.format("%s (%s)", df.format(ukupnaOsnovica), String.valueOf(ukupnaOsnovica)));
    lPDV.setText(String.format("%s (%s)", df.format(ukupnoPDV), String.valueOf(ukupnoPDV)));
    lUkupno.setText(String.format("%s (%s)", df.format(ukupanIznos), String.valueOf(ukupanIznos)));


  }

  public void setClient(Client client) {
    this.client = client;
  }
}
