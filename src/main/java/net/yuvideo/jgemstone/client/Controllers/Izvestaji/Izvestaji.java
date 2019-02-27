package net.yuvideo.jgemstone.client.Controllers.Izvestaji;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Izvestaji.Izvestaj;
import org.json.JSONObject;

public class Izvestaji implements Initializable {


  public JFXTextField tSearch;
  public Button bRefresh;
  public JFXTreeTableView<Izvestaj> tblIzvestaj;
  public JFXDatePicker dtpOd;
  public JFXDatePicker dtpDo;
  public Label lUkupno;
  private URL location;
  private ResourceBundle resources;
  JFXTreeTableColumn<Izvestaj, String> cJBroj = new JFXTreeTableColumn<>("USER ID");
  JFXTreeTableColumn<Izvestaj, Double> cUplaceno = new JFXTreeTableColumn<>("UPLAĆENO");
  JFXTreeTableColumn<Izvestaj, String> cDatumUplate = new JFXTreeTableColumn<>("DATUM UPLATE");
  JFXTreeTableColumn<Izvestaj, String> cOperater = new JFXTreeTableColumn<>("OPERATER");
  JFXTreeTableColumn<Izvestaj, String> cIme = new JFXTreeTableColumn<>("IME");

  DecimalFormat decimalFormat = new DecimalFormat("###,###,###,##0.00");


  private Client client;
  DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    dtpDo.setEditable(false);
    dtpOd.setEditable(false);

    dtpDo.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        if (object != null) {
          return object.format(dateTimeFormatter);
        }

        return "";
      }

      @Override
      public LocalDate fromString(String string) {
        if (string != null && !string.isEmpty()) {
          LocalDate dateTime = LocalDate.parse(string, dateTimeFormatter);
          return dateTime;
        }
        return null;
      }
    });

    dtpOd.setConverter(new StringConverter<LocalDate>() {
      @Override
      public String toString(LocalDate object) {
        if (object != null) {
          return object.format(dateTimeFormatter);
        }

        return "";
      }

      @Override
      public LocalDate fromString(String string) {
        if (string != null && !string.isEmpty()) {
          LocalDate localDate = LocalDate.parse(string, dateTimeFormatter);
          return localDate;
        }

        return null;
      }
    });

    cJBroj.setCellValueFactory(new TreeItemPropertyValueFactory<Izvestaj, String>("jbroj"));
    cUplaceno.setCellValueFactory(new TreeItemPropertyValueFactory<Izvestaj, Double>("uplaceno"));
    cDatumUplate.setCellValueFactory(new TreeItemPropertyValueFactory<Izvestaj, String>("datum"));
    cOperater.setCellValueFactory(new TreeItemPropertyValueFactory<Izvestaj, String>("operater"));
    cIme.setCellValueFactory(new TreeItemPropertyValueFactory<Izvestaj, String>("ime"));

    tblIzvestaj.getColumns().addAll(cJBroj, cIme, cDatumUplate, cOperater, cUplaceno);
    tblIzvestaj.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);

    tSearch.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {

        tblIzvestaj.setPredicate(new Predicate<TreeItem<Izvestaj>>() {
          @Override
          public boolean test(TreeItem<Izvestaj> izvestajTreeItem) {
            return izvestajTreeItem.getValue().getIme().toLowerCase().trim().toString()
                .contains(newValue.toLowerCase().trim())
                || izvestajTreeItem.getValue().getJbroj().trim().toString()
                .contains(newValue.trim())
                ;
          }
        });
      }
    });

    dtpOd.setValue(LocalDate.now());
    dtpDo.setValue(LocalDate.now());
    tblIzvestaj.setShowRoot(false);
  }

  public void trazi(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "getIzvestajPoDatumu");
    object.put("od", dtpOd.getValue().format(dateTimeFormatter));
    object.put("do", dtpDo.getValue().format(dateTimeFormatter));
    object = client.send_object(object);

    ArrayList<Izvestaj> arrayListIzvestaji = new ArrayList<>();

    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }

    double ukupno = 0.00;
    for (int i = 0; i < object.length(); i++) {
      JSONObject izv = object.getJSONObject(String.valueOf(i));
      Izvestaj izvestaj = new Izvestaj();
      izvestaj.setId(izv.getInt("id"));
      izvestaj.setDatum(izv.getString("datumUplate"));
      izvestaj.setIme(izv.getString("ime"));
      izvestaj.setOpis(izv.getString("opis"));
      izvestaj.setOperater(izv.getString("operater"));
      izvestaj.setUplaceno(izv.getDouble("uplaceno"));
      izvestaj.setJbroj(izv.getString("jBroj"));
      izvestaj.setUserID(izv.getInt("userID"));
      ukupno += izv.getDouble("uplaceno");
      arrayListIzvestaji.add(izvestaj);
    }

    ObservableList observableList = FXCollections.observableList(arrayListIzvestaji);

    TreeItem<Izvestaj> root = new RecursiveTreeItem<Izvestaj>(
        observableList, RecursiveTreeObject::getChildren);
    root.setExpanded(true);
    tblIzvestaj.setRoot(root);
    lUkupno.setText(decimalFormat.format(ukupno));


  }

  public void refresh(ActionEvent actionEvent) {
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
