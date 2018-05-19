package net.yuvideo.jgemstone.client.Controllers;

import java.io.File;
import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.StringConverter;
import net.glxn.qrgen.QRCode;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.CenaFormatter;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.Printing.PrintRacun;
import net.yuvideo.jgemstone.client.classes.Racun;
import net.yuvideo.jgemstone.client.classes.ServicesUser;
import net.yuvideo.jgemstone.client.classes.Uplate;
import net.yuvideo.jgemstone.client.classes.Users;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import org.json.JSONObject;

/**
 * Created by zoom on 9/7/16.
 */
public class KorisnikUplateController implements Initializable {

  public Button bClose;

  public Spinner cmbZaUplatu;
  public DatePicker dtpDatumZaNaplatu;
  public Button bZaduzi;
  public Button bUplatiServis;
  public Client client;
  public Users user;
  public Label lStatusDatumIsteka;
  public Label lStatusZaduzenOd;
  public Label lStatusUplatioOper;
  public Label lStatusDatumUplate;
  public Label lDugZaduzenja;
  public Button bZaduziCustomService;
  public ComboBox<ServicesUser> cmbUserServices;
  public TextField tNazivUslugeCustom;
  public DatePicker dtpDatumZaNaplatuCustom;
  public TextField tCenaCustom;
  public TextField tPDVCustom;
  public TextField tRate;
  public CheckBox chkSveUplate;
  public Label lUserJBroj;
  public Label lImePrezime;
  public Label lCustomCena;
  public ComboBox cmbTypeUplate;
  public Button bPrikaziRacun;
  public TextField tKolicina;
  public ComboBox cmbJMere;
  @FXML
  ImageView imgQR;
  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  DateTimeFormatter formatterBack = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  DateTimeFormatter formatMonthYear = DateTimeFormatter.ofPattern("yyyy-MM");
  @FXML
  private TreeTableView<Uplate> tblZaduzenja;
  @FXML
  private TreeTableColumn<Uplate, String> cDatumZaduzenja;
  @FXML
  private TreeTableColumn<Uplate, String> cZaMesec;
  @FXML
  private TreeTableColumn<Uplate, Double> cPopust;
  @FXML
  private TreeTableColumn<Uplate, Double> cPDV;
  @FXML
  private TreeTableColumn<Uplate, Double> cZaUplatu;
  @FXML
  private TreeTableColumn<Uplate, Double> cCena;
  @FXML
  private TreeTableColumn<Uplate, Integer> cKolicina;
  @FXML
  private TreeTableColumn<Uplate, Double> cUplaceno;
  @FXML
  private TreeTableColumn<Uplate, String> cNazivServisa;
  @FXML
  private TreeTableColumn<Uplate, String> cZaduzio;
  @FXML
  private TreeTableColumn<Uplate, String> cRazduzio;
  @FXML
  private Label lIdentifikacija;
  private ResourceBundle resource;
  private URL url;
  private JSONObject jObj;
  private Double zaUplatu = 0.00;
  private Double ukupno_uplaceno = 0.00;
  private Double _DUG = 0.00;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.resource = resources;
    this.url = location;

    ObservableList<String> jmereItems = FXCollections.observableArrayList("kom.", "m.","l.");
    cmbJMere.setItems(jmereItems);
    cmbJMere.getSelectionModel().select(0);
    tKolicina.setText("1");




    dtpDatumZaNaplatu.setValue(LocalDate.now());
    dtpDatumZaNaplatuCustom.setValue(LocalDate.now());

    dtpDatumZaNaplatu.setConverter(
        new StringConverter<LocalDate>() {
          @Override
          public String toString(LocalDate object) {
            return object.format(formatter);
          }

          @Override
          public LocalDate fromString(String string) {
            return LocalDate.parse(string, formatter);
          }
        });

    dtpDatumZaNaplatuCustom.setConverter(
        new StringConverter<LocalDate>() {
          @Override
          public String toString(LocalDate object) {
            return object.format(formatter);
          }

          @Override
          public LocalDate fromString(String string) {
            return LocalDate.parse(string, formatter);
          }
        });

    tRate.setText("1");
    tCenaCustom.setText("0.00");

    tCenaCustom
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (CenaFormatter.CenaFormatterString(newValue)) {
                  tCenaCustom.setText(newValue);
                } else {
                  tCenaCustom.setText(oldValue);
                }
              }
            });

    cDatumZaduzenja.setCellValueFactory(
        new TreeItemPropertyValueFactory<Uplate, String>("datumZaduzenja"));
    cNazivServisa.setCellValueFactory(
        new TreeItemPropertyValueFactory<Uplate, String>("nazivPaket"));
    cCena.setCellValueFactory(new TreeItemPropertyValueFactory<Uplate, Double>("cena"));
    cKolicina.setCellValueFactory(new TreeItemPropertyValueFactory<Uplate, Integer>("kolicina"));
    cPopust.setCellValueFactory(new TreeItemPropertyValueFactory<Uplate, Double>("popust"));
    cZaUplatu.setCellValueFactory(new TreeItemPropertyValueFactory<Uplate, Double>("dug"));
    cZaMesec.setCellValueFactory(new TreeItemPropertyValueFactory<Uplate, String>("zaMesec"));
    cUplaceno.setCellValueFactory(new TreeItemPropertyValueFactory<Uplate, Double>("uplaceno"));
    cZaduzio.setCellValueFactory(new TreeItemPropertyValueFactory<Uplate, String>("zaduzenOd"));
    cRazduzio.setCellValueFactory(new TreeItemPropertyValueFactory<Uplate, String>("operater"));
    cPDV.setCellValueFactory(new TreeItemPropertyValueFactory<Uplate, Double>("pdv"));

    cUplaceno.setCellFactory(
        tc ->
            new TreeTableCell<Uplate, Double>() {
              protected void updateItem(Double uplaceno, boolean bool) {
                super.updateItem(uplaceno, bool);
                if (bool || uplaceno == null) {
                  setText(null);
                } else {
                  TreeTableRow<Uplate> currentRow = getTreeTableRow();
                  Uplate uplate = currentRow.getItem();
                  if (uplate == null) {
                    return;
                  }
                  double upl = uplate.getUplaceno();
                  double dug = uplate.getDug();
                  setText(String.valueOf(uplaceno));
                  if (upl == 0) {
                    currentRow.setStyle(
                        ""
                            + "-fx-background-color: red;"
                            + "-fx-border-color: black;"
                            + "-fx-table-cell-border-color: black;");
                  } else if (upl > 0 && upl < dug) {
                    currentRow.setStyle(
                        "-fx-background-color: darkorange;"
                            + "-fx-border-color: black;"
                            + "-fx-table-cell-border-color: black;");
                  } else if (upl == dug) {
                    currentRow.setStyle(
                        "-fx-background-color: darkgreen; "
                            + "-fx-border-color: black;"
                            + "-fx-table-cell-border-color: black;");
                  } else if (upl > dug) {
                    currentRow.setStyle(
                        "-fx-background-color: blue; "
                            + "-fx-border-color: black;"
                            + "-fx-table-cell-border-color: black;");
                  }
                }

                tblZaduzenja.refresh();
              }
            });

    cPDV.setCellFactory(
        tc ->
            new TreeTableCell<Uplate, Double>() {
              @Override
              protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                  setText(null);
                } else {
                  setText(String.valueOf(item));
                }
              }
            });

    cCena.setCellFactory(
        tc ->
            new TreeTableCell<Uplate, Double>() {
              protected void updateItem(Double uplata, boolean bool) {
                super.updateItem(uplata, bool);
                if (bool || uplata == null) {
                  setText(null);
                } else {
                  setText(String.valueOf(uplata));
                }
              }
            });

    cPopust.setCellFactory(
        tc ->
            new TreeTableCell<Uplate, Double>() {
              protected void updateItem(Double uplata, boolean bool) {
                super.updateItem(uplata, bool);
                if (bool || uplata == null) {
                  setText(null);
                } else {
                  setText(String.valueOf(uplata) + "%");
                }
              }
            });

    cZaUplatu.setCellFactory(
        tc ->
            new TreeTableCell<Uplate, Double>() {
              protected void updateItem(Double zaUplatu, boolean bool) {
                super.updateItem(zaUplatu, bool);
                if (bool || zaUplatu == null) {
                  setText(null);
                } else {
                  setText(String.valueOf(zaUplatu));
                }
              }
            });

    tblZaduzenja
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            new ChangeListener<TreeItem<Uplate>>() {
              @Override
              public void changed(
                  ObservableValue<? extends TreeItem<Uplate>> observable,
                  TreeItem<Uplate> oldValue,
                  TreeItem<Uplate> newValue) {
                if (tblZaduzenja.getSelectionModel().getSelectedIndex() == -1) {
                  return;
                }

                double uplaceno = 0;
                double dug = 0;
                double zaUplatutxt = 0;

                TreeItem<Uplate> selectedItem = tblZaduzenja.getSelectionModel().getSelectedItem();
                if (selectedItem.getChildren().size() > 0) {
                  for (TreeItem<Uplate> uplateTreeItem : selectedItem.getChildren()) {
                    uplaceno += Double.valueOf(uplateTreeItem.getValue().getUplaceno());
                  }
                } else {
                  uplaceno = Double.valueOf(selectedItem.getValue().getUplaceno());
                }

                dug = tblZaduzenja
                                .getSelectionModel()
                                .getSelectedItem()
                                .getValue()
                    .getDug();

                // BUTTON DISABLE UPLATE
                zaUplatutxt = dug - uplaceno;
                cmbZaUplatu.getEditor().setText(String.valueOf(zaUplatutxt));

                if (newValue.getValue().getDug() <= 0) {
                  bUplatiServis.setDisable(true);
                } else {
                  cmbZaUplatu.setDisable(false);
                  bUplatiServis.setDisable(false);
                }

                // STATUS UPLATE
                Uplate uplate = tblZaduzenja.getSelectionModel().getSelectedItem().getValue();
                if (uplate == null) {
                  lStatusDatumUplate.setText("--");
                } else if (uplate.getDatumUplate().equals("1000-01-01 00:00:00.0")) {
                  lStatusDatumUplate.setText("--");
                } else if (uplate.getDatumUplate() != null) {
                  lStatusDatumUplate.setText(uplate.getDatumUplate());
                } else {
                  lStatusDatumUplate.setText("--");
                }
                lStatusUplatioOper.setText(uplate.getOperater());
                lStatusZaduzenOd.setText(uplate.getZaduzenOd());
                lStatusDatumIsteka.setText(get_datum_isteka_servicsa(uplate.getId_ServiceUser()));
                lIdentifikacija.setText(uplate.getIdentification());

                // disable uplate if cena i zaduzenje je isto ili je treeitem child
                if (uplaceno == dug
                    || uplate.getPaketType().contains("LINKED")
                    || uplate.getPaketType().contains("SAOBRACAJ")) {
                  bUplatiServis.setDisable(true);
                  cmbZaUplatu.setDisable(true);
                } else {
                  bUplatiServis.setDisable(false);
                  cmbZaUplatu.setDisable(false);
                }
              }
            });

    cmbZaUplatu.setValueFactory(
        new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, Double.MAX_VALUE));
    cmbZaUplatu.setEditable(true);

    cmbUserServices.setConverter(
        new StringConverter<ServicesUser>() {
          @Override
          public String toString(ServicesUser object) {
            return object.getNazivPaketa();
          }

          @Override
          public ServicesUser fromString(String string) {
            ServicesUser sUser = new ServicesUser();
            sUser.setNazivPaketa(string);
            return null;
          }
        });

    tRate
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("[0-9]*")) {
                  tRate.setText(oldValue);
                  return;
                }
                try {
                  double meseciRate = Double.parseDouble(newValue);
                  tRate.setText(newValue);
                } catch (Exception e) {
                  System.out.println(e.getMessage());
                  tRate.setText(oldValue);
                }
              }
            });

    tCenaCustom
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null || newValue.isEmpty()) {
                  return;
                }
                calculateCenaPDV(tCenaCustom.getText(), tPDVCustom.getText());
              }
            });

    tPDVCustom
        .textProperty()
        .addListener(
            new ChangeListener<String>() {
              @Override
              public void changed(
                  ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null || newValue.isEmpty()) {
                  return;
                }
                calculateCenaPDV(tCenaCustom.getText(), tPDVCustom.getText());
              }
            });

    chkSveUplate.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            filter_data();
          }
        });
  }

  public void filter_data() {
    show_data();
    if (chkSveUplate.isSelected()) {
      return;
    }

    TreeItem<Uplate> root = tblZaduzenja.getRoot();
    TreeItem<Uplate> root_tmp = new TreeItem<>();
    tblZaduzenja.getRoot().getChildren().removeAll();

    for (TreeItem<Uplate> uplataRow : root.getChildren()) {
      if (uplataRow.getValue().getDug() > uplataRow.getValue().getUplaceno()) {
        root_tmp.getChildren().add(uplataRow);
      }
    }

    tblZaduzenja.setRoot(root_tmp);
  }

  private String calculateCenaPDV(String cenaT, String pdvT) {
    double cena = Double.valueOf(cenaT);
    double pdv = Double.valueOf(pdvT);
    double perc = valueToPercent.getValueOfPercentSub(cena, pdv);
    System.out.println(perc);

    _DUG  = cena - perc;
    lCustomCena.setText(String.valueOf(_DUG));

    return String.valueOf(_DUG);
  }

  private String get_datum_isteka_servicsa(int id) {
    jObj = new JSONObject();
    jObj.put("action", "get_datum_isteka_servisa");
    jObj.put("serviceID", id);

    jObj = client.send_object(jObj);

    if (jObj.has("datumIsteka")) {
      return jObj.getString("datumIsteka");
    } else {
      return "--";
    }
  }

  public void close(ActionEvent actionEvent) {
    Stage stage = (Stage) bClose.getScene().getWindow();
    stage.close();
  }

  public void show_data() {

    lUserJBroj.setText(user.getJbroj());
    if (user.isFirma()) {
      lImePrezime.setText(user.getNazivFirme());
    } else {
      lImePrezime.setText(user.getIme());
    }
    TreeItem rootNode = new TreeItem("UPLATE");
    ArrayList<Uplate> zaduzenja = get_Zaduzenja();
    for (Uplate uplata : zaduzenja) {
      if (uplata.getPaketType().equals("BOX")) {
        if (uplata.isHaveFIX()) {
          try {
            Uplate uplataRoot = uplata.CopyUplate();
            Uplate uplataSaobracaj =
                getFixSaobracaj(uplata.getIdentification(), uplata.getZaMesec());

            // ako postji saobracaj izracunati i dodeliti root tree drugacije ne prikazati saobracaj
            if (uplataSaobracaj != null) {
              uplataRoot.setDug(uplataSaobracaj.getDug() + uplata.getDug());
              uplataRoot.setUplaceno(uplataSaobracaj.getUplaceno() + uplata.getUplaceno());
            }

            uplataRoot.setDug(uplataRoot.getDug());

            // set TreeItems
            TreeItem<Uplate> rootTree = new TreeItem<>(uplataRoot);
            TreeItem<Uplate> paketTree = new TreeItem<>(uplata);

            rootTree.getChildren().add(paketTree);
            if (uplataSaobracaj != null) {
              TreeItem<Uplate> saobracajTree = new TreeItem<>(uplataSaobracaj);
              rootTree.getChildren().add(saobracajTree);
            }

            rootNode.getChildren().add(rootTree);
          } catch (CloneNotSupportedException e) {
            e.printStackTrace();
          }
          // ako uplata nije BOX
        } else {
          TreeItem<Uplate> rootTree = new TreeItem<>(uplata);
          rootNode.getChildren().add(rootTree);
        }
      } else if (uplata.getPaketType().equals("FIX")) {
        try {
          Uplate uplataRoot = uplata.CopyUplate();
          Uplate uplataSaobracaj = getFixSaobracaj(uplata.getIdentification(), uplata.getZaMesec());

          // ako postoji saobracaj izracunati i dodati root tree inace ne prikazivate saobracaj
          if (uplataSaobracaj != null) {
            uplataRoot.setDug(uplataSaobracaj.getDug() + uplata.getDug());
            uplataRoot.setUplaceno(uplataSaobracaj.getUplaceno() + uplata.getUplaceno());
          }
          uplataRoot.setDug(uplataRoot.getDug());

          // set TreeItems
          TreeItem<Uplate> rootTree = new TreeItem<>(uplataRoot);
          TreeItem<Uplate> paketTree = new TreeItem<>(uplata);
          rootTree.getChildren().add(paketTree);
          if (uplataSaobracaj != null) {
            TreeItem<Uplate> saobracajTree = new TreeItem<>(uplataSaobracaj);
            rootTree.getChildren().add(saobracajTree);
          }
          rootNode.getChildren().add(rootTree);
        } catch (CloneNotSupportedException e) {
          e.printStackTrace();
        }

      } else {
        TreeItem<Uplate> rootTree = new TreeItem<>(uplata);
        rootNode.getChildren().add(rootTree);
      }



    }

    cmbTypeUplate.getItems().removeAll();
    cmbTypeUplate.getItems().clear();
    cmbTypeUplate.getItems().add(0, "Keš");
    cmbTypeUplate.getItems().add(1, "Faktura");
    cmbTypeUplate.getItems().add(2, "Pošta");
    cmbTypeUplate.getItems().add(3, "Ostalo");
    cmbTypeUplate.getSelectionModel().select(0);

    rootNode.setExpanded(true);
    tblZaduzenja.setShowRoot(false);
    tblZaduzenja.setRoot(rootNode);

    ObservableList<ServicesUser> userServices =
        FXCollections.observableArrayList(get_user_services());
    cmbUserServices.setItems(userServices);

    getUserDebt(user.getId());
    setQrCode();
  }

  private void getUserDebt(int id) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "getUserDebt");
    jsonObject.put("userID", id);

    jsonObject = client.send_object(jsonObject);
    lDugZaduzenja.setText(String.valueOf(jsonObject.getDouble("ukupanDug")));
  }

  private void setQrCode() {
    // File fImg = net.glxn.qrgen.javase.QRCode.from(user.getJbroj()).withCharset("CP1250").file();
    File fImg = QRCode.from(user.getJbroj()).withCharset("CP1250").file();

    // imgQR.setImage(img);
    Image img = new Image(fImg.toURI().toString());
    imgQR.setImage(img);
  }

  ArrayList<Uplate> get_Zaduzenja() {
    zaUplatu = 0.00;
    jObj = new JSONObject();
    jObj.put("action", "get_zaduzenja_user");
    jObj.put("userID", user.getId());
    jObj.put("sveUplate", true);
    Double dug = 0.00;

    jObj = client.send_object(jObj);

    ArrayList<Uplate> uplate = new ArrayList();

    if (jObj.has("Error")) {
      AlertUser.error("GRESKA", jObj.getString("Error"));
      return uplate;
    }

    Uplate uplata;
    JSONObject uplataObj;

    for (int i = 0; i < jObj.length(); i++) {
      uplataObj = (JSONObject) jObj.get(String.valueOf(i));

      //COMMMENT
      System.out.println(uplataObj.toString());

      uplata = new Uplate();
      uplata.setId(uplataObj.getInt("id"));
      uplata.setId_ServiceUser(uplataObj.getInt("id_ServiceUser"));
      uplata.setNazivPaket(uplataObj.getString("nazivPaketa"));
      uplata.setDatumZaduzenja(uplataObj.getString("datumZaduzenja"));
      uplata.setUserID(uplataObj.getInt("userID"));
      uplata.setPopust(uplataObj.getDouble("popust"));
      uplata.setPaketType(uplataObj.getString("paketType"));
      uplata.setCena(uplataObj.getDouble("cena"));
      uplata.setDatumUplate(uplataObj.getString("datumUplate"));
      uplata.setKolicina(uplataObj.getInt("kolicina"));
      uplata.setDug(uplataObj.getDouble("dug"));
      uplata.setPdv(uplataObj.getDouble("pdv"));
      uplata.setPopust(uplataObj.getDouble("popust"));
      dug = dug + uplataObj.getDouble("dug");
      uplata.setUplaceno(uplataObj.getDouble("uplaceno"));
      dug = dug - uplataObj.getDouble("uplaceno");
      uplata.setOperater(uplataObj.getString("operater"));
      uplata.setZaMesec(uplataObj.getString("zaMesec"));
      uplata.setZaduzenOd(uplataObj.getString("zaduzenOd"));
      uplata.setSkipProduzenje(uplataObj.getBoolean("skipProduzenje"));
      uplata.setIdentification(uplataObj.getString("identification"));
      uplata.setHaveFIX(uplataObj.getBoolean("haveFIX"));
      uplate.add(uplata);
    }
    return uplate;
  }

  private ArrayList<Uplate> getUserUplate() {
    ukupno_uplaceno = 0.00;
    jObj = new JSONObject();
    jObj.put("action", "get_uplate_user");
    jObj.put("userID", user.getId());

    jObj = client.send_object(jObj);

    Uplate uplate;
    ArrayList<Uplate> uplateArr = new ArrayList();
    JSONObject uplateObj;

    for (int i = 0; i < jObj.length(); i++) {
      uplate = new Uplate();
      uplateObj = (JSONObject) jObj.get(String.valueOf(i));
      uplate.setUplaceno(uplateObj.getDouble("uplaceno"));
      uplate.setMestoUplate(uplateObj.getString("mesto"));
      uplate.setOperater(uplateObj.getString("operater"));
      uplate.setDatumUplate(uplateObj.getString("datumUplate"));
      uplate.setId(uplateObj.getInt("id"));
      uplateArr.add(uplate);
    }

    return uplateArr;
  }

  public void uplatiServis(ActionEvent actionEvent) {

    if (tblZaduzenja.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.error("GRESKA", "Nije izabran servis");
      return;
    }

    Uplate uplata = tblZaduzenja.getSelectionModel().getSelectedItem().getValue();

    jObj = new JSONObject();
    jObj.put("action", "uplata_servisa");
    jObj.put("userID", user.getId());
    jObj.put("id", uplata.getId());
    jObj.put("id_ServiceUser", uplata.getId_ServiceUser());
    jObj.put("paketType", uplata.getPaketType());
    jObj.put("skipProduzenje", uplata.isSkipProduzenje());
    jObj.put("mestoUplate", cmbTypeUplate.getValue().toString());

    Double numbUplacenoNov = null;
    Double numbZauplatu = null;
    Double numbUplaceno = null;
    numbZauplatu = uplata.getDug();
    numbUplaceno = uplata.getUplaceno();
    numbUplacenoNov = Double.valueOf(cmbZaUplatu.getEditor().getText());

    Double ukupnoUplaceno = numbUplaceno.doubleValue() + numbUplacenoNov.doubleValue();

    if (ukupnoUplaceno > numbZauplatu) {
      AlertUser.error("SUMA NIJE JEDNAKA SA DUGOM", "Uplata ne može biti veca od zaduženja");
      return;
    }
    jObj.put("uplaceno", numbUplacenoNov);
    jObj.put("dug", uplata.getDug());
    jObj.put("paketType", uplata.getPaketType());
    jObj.put("userServiceID", uplata.getId_ServiceUser());
    jObj.put("zaMesec", uplata.getZaMesec());
    jObj.put("identification", uplata.getIdentification());
    jObj.put("haveFIX", uplata.isHaveFIX());
    jObj.put("nazivPaketa", uplata.getNazivPaket());

    jObj = client.send_object(jObj);

    if (jObj.has("Error")) {
      AlertUser.error("GRESKA", jObj.getString("Error"));
    } else {
      AlertUser.info("UPLACENO", "Uplata izvrsena");
    }

    show_data();
  }

  public void zaduziCustomService(ActionEvent actionEvent) {

    if (tNazivUslugeCustom.getText().isEmpty()
        || dtpDatumZaNaplatuCustom.getEditor().getText().isEmpty()) {
      AlertUser.warrning("POLJA SU PRAZNA", "Datum i naziv ne smeju biti prazni");
      return;
    }

    jObj = new JSONObject();

    LocalDate datumZaMesec = dtpDatumZaNaplatuCustom.getValue();
    Calendar cal = Calendar.getInstance();
    cal.set(
        dtpDatumZaNaplatuCustom.getValue().getYear(),
        dtpDatumZaNaplatuCustom.getValue().getMonthValue(),
        dtpDatumZaNaplatuCustom.getValue().getDayOfMonth());
    cal.set(Calendar.DAY_OF_MONTH, 1);

    jObj.put("action", "zaduzi_servis_manual");
    jObj.put("nazivPaketa", tNazivUslugeCustom.getText());
    jObj.put("cena", _DUG);
    jObj.put("pdv", tPDVCustom.getText());
    jObj.put("uplaceno", false);
    jObj.put("zaMesec", formatMonthYear.format(datumZaMesec));
    jObj.put("paketType", "CUSTOM");
    jObj.put("userID", user.getId());
    jObj.put("rate", Integer.valueOf(tRate.getText()));
    jObj.put("kolicina", Integer.valueOf(tKolicina.getText()));
    jObj.put("jMere", cmbJMere.getValue().toString());

    jObj = client.send_object(jObj);
    if (jObj.has("Error")) {
      AlertUser.error("GRESKA", jObj.getString("Error"));
    } else {
      AlertUser.info("KORISNIK ZADUZEN", "Korisnik zaduzenje izvrseno");
    }

    show_data();
  }

  public ArrayList<ServicesUser> get_user_services() {
    jObj = new JSONObject();
    jObj.put("action", "get_user_services");
    jObj.put("userID", user.getId());
    jObj = client.send_object(jObj);

    JSONObject userServiceObj;
    ArrayList<ServicesUser> userServiceArr = new ArrayList();
    ServicesUser userService;

    for (int i = 0; i < jObj.length(); i++) {
      userServiceObj = (JSONObject) jObj.get(String.valueOf(i));
      userService = new ServicesUser();
      userService.setId(userServiceObj.getInt("id"));
      userService.setBrUgovora(userServiceObj.getString("brojUgovora"));
      userService.setCena(userServiceObj.getDouble("cena"));
      userService.setPopust(userServiceObj.getDouble("popust"));
      userService.setOperater(userServiceObj.getString("operName"));
      userService.setDatum(userServiceObj.getString("date_added"));
      userService.setNazivPaketa(userServiceObj.getString("nazivPaketa"));
      userService.setAktivan(userServiceObj.getBoolean("aktivan"));
      if (userServiceObj.has("idUniqueName")) {
        userService.setIdDTVCard(userServiceObj.getString("idUniqueName"));
      }
      userService.setObracun(userServiceObj.getBoolean("obracun"));
      userService.setAktivan(userServiceObj.getBoolean("aktivan"));
      userService.setProduzenje(userServiceObj.getInt("produzenje"));
      if (userServiceObj.has("id_service")) {
        userService.setId_Service(userServiceObj.getInt("id_service"));
      }
      if (userServiceObj.has("BOX_service")) {
        userService.setBox(userServiceObj.getBoolean("BOX_service"));
      }
      if (userServiceObj.has("box_id")) {
        userService.setBox_id(userServiceObj.getInt("box_id"));
      }
      userService.setPaketType(userServiceObj.getString("paketType"));
      userService.setLinkedService(userServiceObj.getBoolean("linkedService"));
      userService.setNewService(userServiceObj.getBoolean("newService"));
      userService.setDTVPaketID(userServiceObj.getInt("DTVPaketID"));
      userService.setPopust(userServiceObj.getDouble("popust"));
      userService.setPdv(userServiceObj.getDouble("pdv"));
      userServiceArr.add(userService);
    }

    return userServiceArr;
  }

  public void zaduziUsluguUnapred(ActionEvent actionEvent) {
    if (cmbUserServices.getSelectionModel().getSelectedIndex() == -1
        || !cmbUserServices.getValue().getAktivan()) {
      AlertUser.warrning("GRESKA", "Usluga nije izabrana ili nije aktivirana");
      return;
    }

    jObj = new JSONObject();
    jObj.put("action", "zaduzi_uslugu");
    jObj.put("userID", user.getId());
    jObj.put("paketType", cmbUserServices.getValue().getPaketType());
    jObj.put("id_ServiceUser", cmbUserServices.getValue().getId());
    jObj.put("id_service", cmbUserServices.getValue().getId_Service());
    jObj.put("nazivPaketa", cmbUserServices.getValue().getNazivPaketa());
    jObj.put("popust", cmbUserServices.getValue().getPopust());
    jObj.put("cena", cmbUserServices.getValue().getCena());
    jObj.put("pdv", cmbUserServices.getValue().getPdv());
    jObj.put("zaMesec", formatMonthYear.format(dtpDatumZaNaplatu.getValue()));

    jObj = client.send_object(jObj);

    if (jObj.has("Error")) {
      AlertUser.error("GRESKA", jObj.getString("Error"));
    } else {
      AlertUser.info("USLUGA ZADUZENA", jObj.getString("Message"));
    }

    show_data();
  }

  private String getIdentity(int id_service) {
    JSONObject jObj = new JSONObject();
    jObj.put("action", "get_Service_ident");
    jObj.put("id_Service", id_service);

    jObj = client.send_object(jObj);

    if (jObj.has("ERROR")) {
      AlertUser.error("GRESKA", jObj.getString("ERROR"));
      return "--";
    }

    return jObj.getString("ident");
  }

  public Uplate getFixSaobracaj(String account, String zaMesec) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "get_FIX_account_saobracaj");
    jsonObject.put("account", account);
    jsonObject.put("zaMesec", zaMesec);
    jsonObject = client.send_object(jsonObject);
    if (!jsonObject.has("id")) {
      return null;
    }
    Uplate uplata;
    if (jsonObject.has("ERROR") || jsonObject == null) {
      AlertUser.error("GRESKA", jsonObject.getString("ERROR"));
      return null;
    } else {
      uplata = new Uplate();
      uplata.setId(jsonObject.getInt("id"));
      uplata.setNazivPaket(jsonObject.getString("nazivPaketa"));
      uplata.setDatumZaduzenja(jsonObject.getString("datumZaduzenja"));
      uplata.setUserID(jsonObject.getInt("userID"));
      uplata.setPopust(jsonObject.getDouble("popust"));
      uplata.setPaketType(jsonObject.getString("paketType"));
      uplata.setCena(jsonObject.getDouble("cena"));
      uplata.setUplaceno(jsonObject.getDouble("uplaceno"));
      uplata.setDatumUplate(jsonObject.getString("datumUplate"));
      uplata.setDug(jsonObject.getDouble("dug"));
      uplata.setOperater(jsonObject.getString("operater"));
      uplata.setZaduzenOd(jsonObject.getString("zaduzenOd"));
      uplata.setZaMesec(jsonObject.getString("zaMesec"));
      uplata.setSkipProduzenje(jsonObject.getBoolean("skipProduzenje"));
      uplata.setPdv(jsonObject.getDouble("pdv"));
    }

    return uplata;
  }

  public void showAllUplate(ActionEvent actionEvent) {
    NewInterface allUplateInterface =
        new NewInterface(
            "fxml/UplatePregled.fxml",
            String.format("UPLATE KORISNIK %s - %s", user.getIme(), user.getJbroj()),
            this.resource);
    UplatePregledController uplatePregledController =
        allUplateInterface.getLoader().getController();
    uplatePregledController.user = this.user;
    uplatePregledController.client = this.client;
    uplatePregledController.setData();
    allUplateInterface.getStage().showAndWait();
  }

  public void prikaziRacun(ActionEvent actionEvent) {
    if (tblZaduzenja.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.warrning("Nije izabran mesec", "Izaberite zaMesec iz table");
      return;
    }
    // Racun userRacun = new Racun(this.user.getId(),
    // tblZaduzenja.getSelectionModel().getSelectedItem().getValue().getZaMesec(), this.client);
    Racun userRacun = new Racun();
    userRacun.initRacun(
        this.user.getId(),
        tblZaduzenja.getSelectionModel().getSelectedItem().getValue().getZaMesec(),
        this.client);
    PrintRacun printRacun = new PrintRacun();
    printRacun.showPreview = true;
    PrinterJob printerJob = PrinterJob.createPrinterJob();
    Window win = bPrikaziRacun.getScene().getWindow();
    boolean b = printerJob.showPrintDialog(win);
    if (!b) {
      return;
    }
    printRacun.userRacun = userRacun.getRacunArrayList();
    printRacun.setPrinterData(printerJob.getJobSettings(), printerJob.getPrinter());
    printRacun.printRacun();
  }
}
