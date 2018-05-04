package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Artikli;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Magacin;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import org.json.JSONObject;

/**
 * Created by PsyhoZOOM@gmail.com on 1/30/18.
 */
public class ArtikliMainController implements Initializable {

  private final DecimalFormat df = new DecimalFormat("#.##");
  public TextField tNaziv;
  public TextField tModel;
  public TextField tSerijski;
  public TextField tMAC;
  public TextField tDobavljac;
  public TextField tBrDok;
  public TextField tNabavnaCena;
  public TextArea tOpis;
  public Button bNov;
  public Button bIzmeni;
  public Button bIzbrisi;
  public Button bTrazi;
  public ComboBox cmbJMere;
  public Spinner spnKolicina;
  public TableView<Artikli> tblArtikli;
  public TableColumn<Artikli, String> cMagacin;
  public TableColumn<Artikli, String> cNaziv;
  public TableColumn<Artikli, String> cProzivodjac;
  public TableColumn<Artikli, String> cModel;
  public TableColumn<Artikli, String> cSerijski;
  public TableColumn<Artikli, String> cMac;
  public TableColumn<Artikli, String> cPON;
  public TableColumn<Artikli, String> cDobavljac;
  public TableColumn<Artikli, String> cBrDokumenta;
  public TableColumn<Artikli, Double> cNabavnaCena;
  public TableColumn<Artikli, String> cDatum;
  public TableColumn<Artikli, String> cJMere;
  public TableColumn<Artikli, Integer> cKolicina;
  public TableColumn<Artikli, String> cOpis;
  public Client client;
  public Menu zaduziMagacin;
  public MenuItem showArtInfo;
  public Button bNovMagacin;
  public Button bIzmenaMagacin;
  public Button bIzbrisiMagacin;
  public ComboBox<Magacin> cmbMagacin;
  public MenuItem zaduziKorisnik;
  public TextField tProizvodjac;
  public TextField tPON;
  SpinnerValueFactory.IntegerSpinnerValueFactory integerSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
      0, Integer.MAX_VALUE, 1);
  private URL location;
  private ResourceBundle resources;
  private boolean editArtikl = false;
  private boolean searchArtikl = false;
  private boolean addArtikl = false;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    cMagacin.setCellValueFactory(new PropertyValueFactory<>("nazivMagacina"));
    cNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
    cModel.setCellValueFactory(new PropertyValueFactory<>("model"));
    cProzivodjac.setCellValueFactory(new PropertyValueFactory<>("proizvodjac"));
    cSerijski.setCellValueFactory(new PropertyValueFactory<>("serijski"));
    cMac.setCellValueFactory(new PropertyValueFactory<>("mac"));
    cPON.setCellValueFactory(new PropertyValueFactory<>("pon"));
    cDobavljac.setCellValueFactory(new PropertyValueFactory<>("dobavljac"));
    cBrDokumenta.setCellValueFactory(new PropertyValueFactory<>("brDok"));
    cNabavnaCena.setCellValueFactory(new PropertyValueFactory<>("nabavnaCena"));
    cDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
    cJMere.setCellValueFactory(new PropertyValueFactory<>("jmere"));
    cOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
    cKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));

    cKolicina
        .setCellFactory(new Callback<TableColumn<Artikli, Integer>, TableCell<Artikli, Integer>>() {
          @Override
          public TableCell<Artikli, Integer> call(TableColumn<Artikli, Integer> param) {
            return new TableCell<Artikli, Integer>() {
              @Override
              protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setText("");
                } else {
                  setText(String.valueOf(item));
                }
              }
            };
          }
        });

    cNabavnaCena
        .setCellFactory(new Callback<TableColumn<Artikli, Double>, TableCell<Artikli, Double>>() {
          @Override
          public TableCell<Artikli, Double> call(TableColumn<Artikli, Double> param) {
            return new TableCell<Artikli, Double>() {
              @Override
              protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setText("");
                } else {
                  setText(df.format(item));
                }
              }
            };
          }
        });

    cmbMagacin.setConverter(new StringConverter<Magacin>() {
      @Override
      public String toString(Magacin object) {
        return object.getNaziv();
      }

      @Override
      public Magacin fromString(String string) {
        Magacin magacin = cmbMagacin.getValue();
        return magacin;
      }
    });

    //set spinner value factory min 0 max MAX_INTEGER_VALUE init value =1;
    spnKolicina.setValueFactory(integerSpinnerValueFactory);

    tblArtikli.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<Artikli>() {
          @Override
          public void changed(ObservableValue<? extends Artikli> observable, Artikli oldValue,
              Artikli newValue) {
            setValues(newValue);
          }
        });

  }

  private void setValues(Artikli artikli) {
    if (artikli == null) {
      return;
    }
    tNaziv.setText(artikli.getNaziv());
    tProizvodjac.setText(artikli.getProizvodjac());
    tModel.setText(artikli.getModel());
    tSerijski.setText(artikli.getSerijski());
    tPON.setText(artikli.getPon());
    tMAC.setText(artikli.getMac());
    tDobavljac.setText(artikli.getDobavljac());
    tBrDok.setText(artikli.getBrDok());
    tNabavnaCena.setText(String.valueOf(artikli.getNabavnaCena()));
    spnKolicina.getEditor().setText(String.valueOf(artikli.getKolicina()));
    tOpis.setText(artikli.getOpis());
    if (artikli.getJmere().equals("metri.")) {
      cmbJMere.getSelectionModel().select(1);
    } else {
      cmbJMere.getSelectionModel().select(2);
    }

    //ako je artikal zaduzen kod korisnika, else select magacin
    if (artikli.isUser()) {
      cmbMagacin.getSelectionModel().select(1);
    } else {
      cmbMagacin.getSelectionModel().select(getMagacin(artikli.getIdMagacin()));
    }


  }

  private ArrayList<Magacin> getMagacini() {
    Magacin magacin = new Magacin();
    return magacin.getMagaciniArr(client);

  }

  private Magacin getMagacin(int id) {
    Magacin mag = new Magacin();
    for (Magacin magacin : mag.getMagaciniArr(client)) {
      if (magacin.getId() == id) {
        return magacin;
      }
    }

    return null;

  }


  public void setForms() {

    //combobox add jedinice mere
    cmbJMere.getItems().removeAll();
    cmbJMere.getItems().add(0, "");
    cmbJMere.getItems().add(1, "m.");
    cmbJMere.getItems().add(2, "kom.");
    cmbJMere.getSelectionModel().select(0);

    tNabavnaCena.setText("0.00");

    updateMagacinCmb();


  }

  private void updateMagacinCmb() {
    cmbMagacin.getItems().clear();
    Magacin magacin = new Magacin();
    Magacin magacinALL = new Magacin();
    Magacin magacinUsers = new Magacin();

    magacinALL.setNaziv("SVE");
    magacin.setId(0);

    magacinUsers.setNaziv("KORISNICI");
    magacinUsers.setId(1);

    ObservableList magacinObs = FXCollections.observableArrayList(getMagacini());

    cmbMagacin.getItems().add(magacinALL);
    cmbMagacin.getItems().add(magacinUsers);
    cmbMagacin.getItems().addAll(magacinObs);

    cmbMagacin.getSelectionModel().select(0);
  }


  public void addArtikal(ActionEvent actionEvent) {
    if (cmbMagacin.getValue().getId() == 0) {
      AlertUser.error("GRESKA", "Morate izabrati magacin kome pripada Artikal! jbg..");
      return;
    }
    addArtikl = true;
    artiklFunc();
  }


  public void artiklFunc() {
    int artikalID = 0;
    JSONObject jsonObject = new JSONObject();

    if (editArtikl) {
      jsonObject.put("action", "editArtikal");
      if (tblArtikli.getSelectionModel().getSelectedIndex() == -1) {
        AlertUser.error("GRESKA", "Nije izabran ni jedan artikal");
        return;
      } else {
        artikalID = tblArtikli.getSelectionModel().getSelectedItem().getId();
        jsonObject.put("id", artikalID);
      }
    } else if (addArtikl) {
      jsonObject.put("action", "addArtikal");
      jsonObject.put("id", artikalID);

    } else if (searchArtikl) {
      jsonObject.put("action", "searchArtikal");
    }
    jsonObject.put("naziv", tNaziv.getText());
    jsonObject.put("model", tModel.getText());
    jsonObject.put("proizvodjac", tProizvodjac.getText());
    jsonObject.put("serijski", tSerijski.getText());
    jsonObject.put("pon", tPON.getText());
    jsonObject.put("mac", tMAC.getText());
    jsonObject.put("dobavljac", tDobavljac.getText());
    jsonObject.put("brDokumenta", tBrDok.getText());
    jsonObject.put("nabavnaCena", tNabavnaCena.getText());
    jsonObject.put("jMere", cmbJMere.getValue().toString());
    jsonObject.put("kolicina", Integer.valueOf(spnKolicina.getEditor().getText()));
    jsonObject.put("opis", tOpis.getText());
    if (cmbMagacin.getValue() != null) {
      jsonObject.put("idMagacin", cmbMagacin.getValue().getId());
    } else {
      jsonObject.put("idMagacin", tblArtikli.getSelectionModel().getSelectedItem().getIdMagacin());
    }

    if (addArtikl || editArtikl) {
      jsonObject = client.send_object(jsonObject);
    }
    //ako trazimo artikle populate table
    if (searchArtikl) {
      ObservableList searchArtikli = FXCollections.observableArrayList(getArtikli(jsonObject));
      tblArtikli.setItems(searchArtikli);

    }

    if (jsonObject.has("ERROR")) {
      AlertUser.error("GREŠKA", jsonObject.getString("ERROR"));
    } else if (addArtikl || editArtikl) {
      AlertUser.info("ARTIKAL SNIMLJEN", "Artikal je uspešno snimljen");
    }

    //all to false becouse we don't wanna conflict
    this.searchArtikl = false;
    this.addArtikl = false;
    this.editArtikl = false;

  }

  public void IzmeniArtikal(ActionEvent actionEvent) {
    this.editArtikl = true;
    artiklFunc();
  }

  public void izbrisiArtikal(ActionEvent actionEvent) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "deleteArtikl");
    if (tblArtikli.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.error("GRESKA", "Nije izabran ni jedan artikal");
      return;
    }

    Optional<ButtonType> upozorenje = AlertUser
        .yesNo("UPOZORENJE", String.format("Da li ste sigurni da zelite da izbrisete Artikal %s",
            tblArtikli.getSelectionModel().getSelectedItem().getNaziv()));

    if (AlertUser.NE == upozorenje.get()) {
      return;
    }

    int id = tblArtikli.getSelectionModel().getSelectedItem().getId();
    jsonObject.put("id", id);
    jsonObject = client.send_object(jsonObject);

    if (jsonObject.has("ERROR")) {
      AlertUser.error("GRESKA", jsonObject.getString("ERROR"));
    } else {
      AlertUser.info("ARTIKAL JE IZBRISAN", String.format("Artikal %s je obrisan",
          tblArtikli.getSelectionModel().getSelectedItem().getNaziv()));
    }
  }

  public void traziArtikal(ActionEvent actionEvent) {
    searchArtikl = true;
    artiklFunc();

  }

  private ArrayList<Artikli> getArtikli(JSONObject jsonObject) {
    Artikli artikli = new Artikli();
    artikli.searchArtikal(jsonObject, client);
    return artikli.getArtikliArrayList();

  }

  public void clearText(ActionEvent actionEvent) {
    tNaziv.clear();
    tProizvodjac.clear();
    tModel.clear();
    tSerijski.clear();
    tPON.clear();
    tMAC.clear();
    tDobavljac.clear();
    tBrDok.clear();
    tNabavnaCena.setText("0");
    spnKolicina.getEditor().setText("0");
    cmbJMere.getSelectionModel().select(0);
    tOpis.clear();

  }

  public void showArtInformation(ActionEvent actionEvent) {
    Artikli artikal = tblArtikli.getSelectionModel().getSelectedItem();
    if (artikal == null) {
      return;
    }
    System.out.println(artikal.toString());
  }


  public void zaduziArtikl(ActionEvent actionEvent) {
    if (tblArtikli.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }
    if (cmbMagacin.getValue().getId() <= 0) {
      return;
    }

    Artikli artikli = tblArtikli.getSelectionModel().getSelectedItem();

    NewInterface artikliZaduzivanje = new NewInterface("fxml/ArtikliZaduzivanje.fxml",
        "ZADUŽIVANJE ARTIKLA", resources);
    ArtikliZaduzivanjeController artikliZaduzivanjeController = artikliZaduzivanje.getLoader()
        .getController();
    artikliZaduzivanjeController.client = this.client;
    artikliZaduzivanjeController.artikal = artikli;
    artikliZaduzivanjeController.artikal
        .setIdMagacin(tblArtikli.getSelectionModel().getSelectedItem().getIdMagacin());
    artikliZaduzivanjeController.setData();
    artikliZaduzivanje.getStage().showAndWait();

  }

  public void showInfoArtikl(ActionEvent actionEvent) {
    int artID = tblArtikli.getSelectionModel().getSelectedItem().getId();
    int magId = tblArtikli.getSelectionModel().getSelectedItem().getIdMagacin();
    int uniqueID = tblArtikli.getSelectionModel().getSelectedItem().getUniqueID();
    NewInterface artInfoInterface = new NewInterface("fxml/ArtikliTracking.fxml", "INFO ARTIKLA",
        resources);
    ArtikliTrackingController artikliTrackingController = artInfoInterface.getLoader()
        .getController();
    artikliTrackingController.client = this.client;
    artikliTrackingController.artID = artID;
    artikliTrackingController.magID = magId;
    artikliTrackingController.uniqueID = uniqueID;
    artikliTrackingController.setData();
    artInfoInterface.getStage().showAndWait();
    ;

  }
}
