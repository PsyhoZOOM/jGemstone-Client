package net.yuvideo.jgemstone.client.Controllers.DTV;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.Controllers.Administration.DTV.DTVKartice;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.DigitalniTVPaket;
import net.yuvideo.jgemstone.client.classes.ServicesUser;
import org.json.JSONObject;

public class DTVEditController implements Initializable {

  public JFXTextField tBrKartice;
  public JFXTextField tPrekoracenje;
  public JFXTextField tpopust;
  public CheckBox cmbObracun;
  public JFXTextField tBrojUgovora;
  public JFXTextArea taOpis;
  public JFXListView<DigitalniTVPaket> lsAktivni;
  public JFXButton bAddDodatak;
  public JFXButton bRemoveDodatak;
  public JFXListView<DigitalniTVPaket> lsDostupni;
  public Label lUkupno;
  public JFXComboBox<DigitalniTVPaket> cmbDodatnaKartica;
  public JFXButton bIzmeniBrojKartice;
  public JFXButton bSnimiIzmeneServisa;
  public JFXTextField tDodatnaKarticBroj;
  public JFXButton bAddDodatnaKartica;
  public JFXListView<DTVKartice> lsDodateneKartice;
  public JFXButton bRemoveAddonCard;
  public JFXButton bIzmeniPaketID;
  public JFXTextField tPaketID;
  public VBox vboxMain;


  private URL location;
  private ResourceBundle resources;
  private Client client;
  private ServicesUser service;
  private DTVKartice mainCard;

  private int service_paket_id;

  private boolean error = false;
  private String errorMSG = "";

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    lsDostupni.setCellFactory(
        new Callback<ListView<DigitalniTVPaket>, ListCell<DigitalniTVPaket>>() {
          @Override
          public ListCell<DigitalniTVPaket> call(ListView<DigitalniTVPaket> param) {
            return new ListCell<DigitalniTVPaket>() {
              @Override
              protected void updateItem(DigitalniTVPaket item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                  setText("");
                } else {
                  setText(item.getNaziv());
                }
              }
            };
          }
        });

    lsAktivni.setCellFactory(
        new Callback<ListView<DigitalniTVPaket>, ListCell<DigitalniTVPaket>>() {
          @Override
          public ListCell<DigitalniTVPaket> call(ListView<DigitalniTVPaket> param) {
            return new ListCell<DigitalniTVPaket>() {
              @Override
              protected void updateItem(DigitalniTVPaket item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                  setText("");
                } else {
                  setText(item.getNaziv());
                }
              }
            };
          }
        });

    lsDodateneKartice.setCellFactory(new Callback<ListView<DTVKartice>, ListCell<DTVKartice>>() {
      @Override
      public ListCell<DTVKartice> call(ListView<DTVKartice> param) {
        return new ListCell<DTVKartice>() {
          @Override
          protected void updateItem(DTVKartice item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setText("");
            } else {
              String main = "";
              if (item.getIdKartica() == Integer.valueOf(service.getIdDTVCard())) {
                main = "Glavna Kartica";
              }
              setText(String.format("%d %s", item.getIdKartica(), main));
            }
          }
        };
      }
    });

    cmbDodatnaKartica.setCellFactory(
        new Callback<ListView<DigitalniTVPaket>, ListCell<DigitalniTVPaket>>() {
          @Override
          public ListCell<DigitalniTVPaket> call(ListView<DigitalniTVPaket> param) {
            return new ListCell<DigitalniTVPaket>() {
              @Override
              protected void updateItem(DigitalniTVPaket item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setText("");
                } else {
                  setText(item.getNaziv());
                }
              }
            };
          }
        });

    cmbDodatnaKartica.setConverter(new StringConverter<DigitalniTVPaket>() {
      @Override
      public String toString(DigitalniTVPaket object) {
        return object.getNaziv();
      }

      @Override
      public DigitalniTVPaket fromString(String string) {
        DigitalniTVPaket digitalniTVPaket = new DigitalniTVPaket();
        digitalniTVPaket.setNaziv(string);
        return digitalniTVPaket;
      }
    });

    lsDodateneKartice.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<DTVKartice>() {
          @Override
          public void changed(ObservableValue<? extends DTVKartice> observable, DTVKartice oldValue,
              DTVKartice newValue) {
            setData();
          }
        });

  }

  private void setPaketID() {
    getServicePaketID();
    int paket_id = service_paket_id;
    for (DigitalniTVPaket paket : lsAktivni.getItems()) {
      paket_id += paket.getPaketID();
    }
    JSONObject object = new JSONObject();
    object.put("action", "updateUserDTVPaketID");
    object.put("paketID", paket_id);
    object.put("serviceID", service.getId());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      setError(true);
      setErrorMSG(object.getString("ERROR"));
      return;
    }
    tPaketID.setText(String.valueOf(paket_id));
  }


  public void initData() {
    setMainDTVCard();
    setDostupneDodatke();
    setKartice();
    setDodatneKarticaPaket();
    setData();
  }


  private void setMainDTVCard() {
    JSONObject object = new JSONObject();
    object.put("action", "getDTVCard");
    object.put("serviceID", service.getId());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    mainCard = new DTVKartice();
    mainCard.setData(object);

  }

  private void setKartice() {
    ObservableList kartiOb = FXCollections.observableArrayList(getUserCards());
    lsDodateneKartice.getItems().removeAll();
    lsDodateneKartice.setItems(kartiOb);
  }


  private void setData() {
    setMainDTVCard();
    setAktivneDodatke(mainCard);
    tBrKartice.setText(String.valueOf(mainCard.getIdKartica()));
    tPrekoracenje.setText(String.valueOf(service.getProduzenje()));
    tpopust.setText(String.valueOf(service.getPopust()));
    cmbObracun.setSelected(service.getObracun());
    tBrojUgovora.setText(service.getBrUgovora());
    taOpis.setText(service.getOpis());
    tPaketID.setText(String.valueOf(mainCard.getPaketID()));


  }

  private void setDodatneKarticaPaket() {
    ArrayList<DigitalniTVPaket> dtvAddonCards = getDTVAddonCards();
    ObservableList observableList = FXCollections.observableList(dtvAddonCards);
    cmbDodatnaKartica.setItems(observableList);
  }


  private void setAktivneDodatke(DTVKartice kartica) {
    setDostupneDodatke();
    lsAktivni.getItems().removeAll();
    int paketID = kartica.getPaketID();
    ArrayList<DigitalniTVPaket> dtvPaketForRemove = new ArrayList<>();
    ArrayList<DigitalniTVPaket> dtvPaketForAdd = new ArrayList<>();
    for (DigitalniTVPaket paket : lsDostupni.getItems()) {
      if (paketID >= paket.getPaketID()) {
        dtvPaketForAdd.add(paket);
        dtvPaketForRemove.add(paket);
        paketID -= paket.getPaketID();
      }
    }

    lsDostupni.getItems().removeAll(dtvPaketForRemove);
    lsAktivni.setItems(FXCollections.observableArrayList(dtvPaketForAdd));

  }

  private void setDostupneDodatke() {
    ArrayList<DigitalniTVPaket> dtvAddons = getDTVAddons();
    ObservableList addons = FXCollections.observableArrayList(dtvAddons);
    lsDostupni.setItems(addons);

  }

  public ArrayList<DTVKartice> getUserCards() {
    ArrayList<DTVKartice> userKartice = new ArrayList<>();
    JSONObject object = new JSONObject();
    object.put("action", "getUserServiceDTVKartice");
    object.put("idService", service.getId());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return null;
    }

    for (int i = 0; i < object.length(); i++) {
      DTVKartice kartica = new DTVKartice();
      JSONObject kar = object.getJSONObject(String.valueOf(i));
      kartica.setId(kar.getInt("id"));
      kartica.setEndDate(kar.getString("endDate"));
      kartica.setFreezeDate(kar.getString("freezeDate"));
      kartica.setIdKartica(kar.getInt("idKartica"));
      kartica.setPaketID(kar.getInt("paketID"));
      kartica.setUserID(kar.getInt("userID"));
      kartica.setIdServisa(String.valueOf(kar.getInt("idService")));
      userKartice.add(kartica);
    }
    return userKartice;
  }


  private ArrayList<DigitalniTVPaket> getDTVPaketi() {
    JSONObject object = new JSONObject();
    object.put("action", "getDigitalTVPaketi");
    object = client.send_object(object);
    ArrayList<DigitalniTVPaket> digitalniTVPakets = new ArrayList<>();
    for (int i = 0; i < object.length(); i++) {
      DigitalniTVPaket digitalniTVPaket = new DigitalniTVPaket();
      JSONObject dtvPaket = object.getJSONObject(String.valueOf(i));
      digitalniTVPaket.setId(dtvPaket.getInt("id"));
      digitalniTVPaket.setNaziv(dtvPaket.getString("naziv"));
      digitalniTVPaket.setCena(dtvPaket.getDouble("cena"));
      digitalniTVPaket.setPdv(dtvPaket.getDouble("pdv"));
      digitalniTVPaket.setPaketID(dtvPaket.getInt("idPaket"));
      digitalniTVPaket.setOpis(dtvPaket.getString("opis"));
      digitalniTVPaket.setDodatak(dtvPaket.getBoolean("dodatak"));
      digitalniTVPakets.add(digitalniTVPaket);
    }
    return digitalniTVPakets;
  }

  private ArrayList<DigitalniTVPaket> getDTVAddons() {
    JSONObject object = new JSONObject();
    object.put("action", "getDTVDodatke");
    object = client.send_object(object);
    ArrayList<DigitalniTVPaket> digitalniTVPakets = new ArrayList<>();
    for (int i = 0; i < object.length(); i++) {
      JSONObject dtvObj = object.getJSONObject(String.valueOf(i));
      DigitalniTVPaket digitalniTVPaket = new DigitalniTVPaket();
      digitalniTVPaket.setId(dtvObj.getInt("id"));
      digitalniTVPaket.setNaziv(dtvObj.getString("naziv"));
      digitalniTVPaket.setCena(dtvObj.getDouble("cena"));
      digitalniTVPaket.setPaketID(dtvObj.getInt("idPaket"));
      digitalniTVPaket.setProduzenje(dtvObj.getInt("prekoracenje"));
      digitalniTVPaket.setPdv(dtvObj.getDouble("pdv"));
      digitalniTVPaket.setDodatak(dtvObj.getBoolean("dodatak"));
      digitalniTVPakets.add(digitalniTVPaket);
    }
    return digitalniTVPakets;
  }

  private ArrayList<DigitalniTVPaket> getDTVAddonCards() {
    JSONObject object = new JSONObject();
    object.put("action", "getDigitalTVAddonCards");
    object = client.send_object(object);
    ArrayList<DigitalniTVPaket> digitalniTVPaketsCards = new ArrayList<>();
    for (int i = 0; i < object.length(); i++) {
      JSONObject card = object.getJSONObject(String.valueOf(i));
      DigitalniTVPaket digitalniTVPaket = new DigitalniTVPaket();
      digitalniTVPaket.setId(card.getInt("id"));
      digitalniTVPaket.setNaziv(card.getString("naziv"));
      digitalniTVPaket.setCena(card.getDouble("cena"));
      digitalniTVPaket.setPaketID(card.getInt("idPaket"));
      digitalniTVPaket.setOpis(card.getString("opis"));
      digitalniTVPaket.setProduzenje(card.getInt("prekoracenje"));
      digitalniTVPaket.setPdv(card.getDouble("pdv"));
      digitalniTVPaket.setDodatak(card.getBoolean("dodatak"));
      digitalniTVPaket.setDodatnaKartica(card.getBoolean("dodatnaKartica"));
      digitalniTVPaketsCards.add(digitalniTVPaket);
    }

    return digitalniTVPaketsCards;
  }


  public void setClient(Client client) {
    this.client = client;
  }

  public void setService(ServicesUser service) {
    this.service = service;
  }


  public void addDodatak(ActionEvent actionEvent) {
    if (lsDostupni.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }
    DigitalniTVPaket selectedItem = lsDostupni.getSelectionModel().getSelectedItem();
    //Dodavanje dodatka korisniku se automatski zaduzuje ako je servis aktivan, zato upozoravamo operatera u slucaju greske da moze da opozove komandu

    boolean zaduzivanje_korisnika_sa_dodatnim_paketom = AlertUser
        .yesNo("ZADUZIVANJE KORISNIKA SA DODATNIM PAKETOM",
            String.format("Korisnik ce biti zaduzen dodatkom %s. Da li ste sigurni?",
                selectedItem.getNaziv()));

    if (!zaduzivanje_korisnika_sa_dodatnim_paketom) {
      return;
    }

    lsAktivni.getItems().add(selectedItem);
    lsDostupni.getItems().remove(selectedItem);
    setPaketID();
    if (isError()) {
      return;
    }
    addService(selectedItem);

    setData();
  }

  public void removeDodatak(ActionEvent actionEvent) {
    if (lsAktivni.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }
    DigitalniTVPaket selectedItem = lsAktivni.getSelectionModel().getSelectedItem();

    //upozorenje korisnika zaa brisanje dodatka
    boolean brisanje_dodatka = AlertUser.yesNo("BRISANJE DODATKA", String
        .format("Da li ste sigurni da zelite da izbrišete dodatak %s?", selectedItem.getNaziv()));

    if (!brisanje_dodatka) {
      return;
    }
    lsAktivni.getItems().remove(selectedItem);
    lsDostupni.getItems().add(selectedItem);
    setPaketID();
    if (isError()) {
      return;
    }
    removeService(selectedItem);
    setData();
  }

  private void addService(DigitalniTVPaket selectedItem) {
    JSONObject object = new JSONObject();
    object.put("action", "addDTVPaketDodatakToUser");
    object.put("serviceID", service.getId());
    object.put("dodatakID", selectedItem.getId());
    object.put("userID", service.getUserID());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    }

  }

  private void removeService(DigitalniTVPaket selectedItem) {
    JSONObject object = new JSONObject();
    object.put("action", "removeDTVPaketDodatakFromUser");
    object.put("serviceID", service.getId());
    object.put("naziv", selectedItem.getNaziv());
    object.put("userID", service.getUserID());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("Eki bRROR"));
    }
  }


  private void getServicePaketID() {
    JSONObject object = new JSONObject();
    object.put("action", "getDTVPaketID");
    object.put("idService", service.getId());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    service_paket_id = object.getInt("paketID");
  }

  public void snimiServis(ActionEvent actionEvent) {
  }

  public void izmeniPaket(ActionEvent actionEvent) {
  }

  public void izmeniBrojKartice(ActionEvent actionEvent) {
  }

  public void snimiIzmeneServisa(ActionEvent actionEvent) {
  }

  public void addDodatnaKartica(ActionEvent actionEvent) {
    if (cmbDodatnaKartica.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.info("NIJE IZABRAN PAKET DODATNE KARTICE", "Izaberite paket dodatne kartice!");
      return;
    }
    JSONObject object = new JSONObject();
    object.put("action", "addDTVAddonCard");
    object.put("cardID", Integer.valueOf(tDodatnaKarticBroj.getText()));
    object.put("serviceID", cmbDodatnaKartica.getValue().getId());
    object.put("cena", cmbDodatnaKartica.getValue().getCena());
    object.put("pdv", cmbDodatnaKartica.getValue().getPdv());
    object.put("popust", service.getPopust());
    object.put("obracun", service.getObracun());
    object.put("naziv", cmbDodatnaKartica.getValue().getNaziv());
    object.put("paketID", mainCard.getPaketID());
    object.put("produzenje", service.getProduzenje());
    object.put("paketType", "DTV_ADDON");
    object.put("endDate", service.getEndDate());
    object.put("brUgovora", service.getBrUgovora());
    object.put("mainDTVServiceID", service.getId());
    object.put("aktivan", service.getAktivan());

    object.put("userID", service.getUserID());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
      return;
    }
    AlertUser.info("INFO",
        String.format("Dodatna kartica sa brojem %s je dodata!", tDodatnaKarticBroj.getText()));
    initData();
  }

  public void removeAddonCard(ActionEvent actionEvent) {
  }


  public void izmeniPaketID(ActionEvent actionEvent) {
  }


  public boolean isError() {
    return error;
  }

  public void setError(boolean error) {
    this.error = error;
  }

  public String getErrorMSG() {
    return errorMSG;
  }

  public void setErrorMSG(String errorMSG) {
    this.errorMSG = errorMSG;
  }

  public VBox getVboxMain() {
    return vboxMain;
  }
}
