package net.yuvideo.jgemstone.client.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.Controllers.BOX.BOXEditController;
import net.yuvideo.jgemstone.client.Controllers.DTV.DTVEditController;
import net.yuvideo.jgemstone.client.classes.*;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

/**
 * Created by zoom on 2/2/17.
 */
public class KorisnikUslugeController implements Initializable {


  //list services
  public TreeTableView<ServicesUser> tblServices;
  public TreeTableColumn cServicesNaziv;
  public TreeTableColumn cServicesDatum;
  public TreeTableColumn cDatumIsteka;
  public TreeTableColumn cServicesBrojUgovora;
  public TreeTableColumn cSservicesOperater;
  public TreeTableColumn cServicePopust;
  public TreeTableColumn cServiceCena;
  public TreeTableColumn cServiceIdentification;
  public TreeTableColumn cServiceObracun;
  public TreeTableColumn cServiceAktivan;

  //ostaleusluge
  public ComboBox<OstaleUsluge> cmbNazivUslugeOstalo;
  public TextField tPopustOstalo;
  public TextField tSerijskiBrojOstalo;
  public TextField tMacOstalo;
  public TextArea taKomentarOstalo;
  public Button bSnimiOstalo;
  public CheckBox chkProduzenjeOstalo;
  public ComboBox<ugovori_types> cmbUgovorOstalo;
  //intenret service
  public ComboBox<InternetPaketi> cmbPaketInternet;
  public TextField tUserNameInternet;
  public PasswordField tLoznikaInternet;
  public PasswordField tLozinkaCInternet;
  public CheckBox chkRacunInternet;
  public TextArea tOpisInternet;
  public Button bAddServiceInternet;
  public TextField tPopustInternet;
  public ComboBox<ugovori_types> cmbUgovorInternet;
  //DTV service
  public ComboBox<DigitalniTVPaket> cmbPaketDTV;
  public TextField tPopustDTV;
  public TextField tKarticaDTV;
  public CheckBox chkRacunDTV;
  public TextArea tOpisDTV;
  public Button bAddServiceDTV;
  public ComboBox<ugovori_types> cmbUgovorDTV;
  public AnchorPane anchorPane;
  public Button bActivateService;
  public Button bDeleteService;
  //BOX Service
  public ComboBox<BoxPaket> cmbPaketBOX;
  public TextField tKarticaBox;
  public TextField tUserNameBox;
  public TextField tMACIPTVBox;
  public TextField tFiksnaTelBox;
  public TextField tPopustBox;
  public TextArea tOpisBox;
  public ComboBox<ugovori_types> cmbUgovorBox;
  public PasswordField tPasswordBox;
  public Button bSnimiBox;
  public CheckBox chkRacunBOX;
  //Fixna Telefonija Service
  public ComboBox<FiksnaPaketi> cmbFixPaket;
  public TextField tFixPopust;
  public TextField tFixBrojTel;
  public CheckBox chkFixStampaObracun;
  public ComboBox<ugovori_types> cmbFixBrojUgovora;
  public TextArea tFixOpis;
  public Button bFixDodajUslugu;
  public CheckBox chkTempZaduzenje;
  public CheckBox chk_punaCena;
  //IPTV PAKETI
  private Client client;
  public int userID;
  public ComboBox<IPTVPaketi> cmbIPTVPaket;
  public TextField tUserNameIPTV;
  public PasswordField tPasswordIPTV;
  public Button bSnimiIPTVUsluguIPTV;
  public CheckBox cmbObracunIPTV;
  public TextField tPopustIPTV;
  public ComboBox<ugovori_types> cmbUgovorIPTV;
  public TextField tStbMACIPTV;
  public Button bIzmeniServis;
  public Users userEdit;
  public PasswordField tPasswordIPTVCheck;
  public TextArea tOpisIPTV;
  Calendar firstDateInMonth = Calendar.getInstance();
  @FXML
  private TreeTableColumn cPDV;
  @FXML
  private TreeTableColumn cZaUplatu;
  private ResourceBundle resources;
  private URL location;
  private JSONObject jObj;
  private DecimalFormat df = new DecimalFormat("###,###,###,##0.00");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    tKarticaDTV.setText("0");
    tKarticaBox.setText("0");

    cServicesNaziv.setStyle("-fx-alignment: CENTER-LEFT;");

    tblServices.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<TreeItem<ServicesUser>>() {
          @Override
          public void changed(ObservableValue<? extends TreeItem<ServicesUser>> observable,
              TreeItem<ServicesUser> oldValue, TreeItem<ServicesUser> newValue) {
            if (tblServices.getSelectionModel().getSelectedIndex() == -1) {
              return;
            }
            ServicesUser srvusr = newValue.getValue();
            if (
                srvusr.getPaketType().equals("DTV") ||
                    srvusr.getPaketType().equals("NET") ||
                    srvusr.getPaketType().equals("BOX") ||
                    srvusr.getPaketType().equals("FIX") ||
                    srvusr.getPaketType().equals("IPTV") ||
                    srvusr.getPaketType().equals("OSTALE_USLUGE")
            ) {
              bIzmeniServis.setDisable(false);
              bDeleteService.setDisable(false);
            } else {
              bIzmeniServis.setDisable(true);
              bDeleteService.setDisable(true);
            }

            if (srvusr.isAktivan() || srvusr.getPaketType().contains("LINKED") || srvusr
                .getPaketType().contains("DTV_ADDON")) {
              bActivateService.setDisable(true);
            } else {
              bActivateService.setDisable(false);
            }

          }
        });

    cmbNazivUslugeOstalo.setCellFactory(lc -> new ListCell<OstaleUsluge>() {
      @Override
      protected void updateItem(OstaleUsluge item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setText("");
        } else {
          setText(item.getNazivUsluge());
        }
      }
    });

    cmbPaketInternet.setCellFactory(lv -> new ListCell<InternetPaketi>() {
      public void updateItem(InternetPaketi paket, boolean bool) {
        super.updateItem(paket, bool);
        if (bool) {
          setText(null);
        } else {
          setText(paket.getNaziv());
        }
      }
    });

    cmbPaketDTV.setCellFactory(lv -> new ListCell<DigitalniTVPaket>() {
      public void updateItem(DigitalniTVPaket paket, boolean bool) {
        super.updateItem(paket, bool);
        if (bool) {
          setText(null);
        } else {
          setText(paket.getNaziv());
        }
      }
    });

    cmbUgovorBox.setCellFactory(new Callback<ListView<ugovori_types>, ListCell<ugovori_types>>() {
      @Override
      public ListCell<ugovori_types> call(ListView<ugovori_types> param) {
        return new ListCell<ugovori_types>() {
          @Override
          protected void updateItem(ugovori_types item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
              setText(null);
            } else {
              setText(String.format("%s, %s", item.getNaziv(), item.getBr()));
            }
          }
        };
      }
    });

    cmbUgovorBox.setConverter(new StringConverter<ugovori_types>() {
      @Override
      public String toString(ugovori_types object) {
        return String.format("%s, %s", object.getNaziv(), object.getBr());
      }

      @Override
      public ugovori_types fromString(String string) {
        return null;
      }
    });

    cmbUgovorInternet.setCellFactory(d -> new ListCell<ugovori_types>() {
      public void updateItem(ugovori_types ugovor, boolean bool) {
        super.updateItem(ugovor, bool);
        if (bool) {
          setText(null);
        } else {
          setText(ugovor.getNaziv() + ", " + ugovor.getBr());
        }
      }
    });

    cmbUgovorDTV.setCellFactory(dtv -> new ListCell<ugovori_types>() {
      public void updateItem(ugovori_types ugovord, boolean boold) {
        super.updateItem(ugovord, boold);
        if (boold) {
          setText(null);
        } else {
          setText(ugovord.getNaziv() + ", " + ugovord.getBr());
        }
      }
    });

    cmbFixBrojUgovora
        .setCellFactory(new Callback<ListView<ugovori_types>, ListCell<ugovori_types>>() {
          @Override
          public ListCell<ugovori_types> call(ListView<ugovori_types> param) {
            return new ListCell<ugovori_types>() {
              @Override
              protected void updateItem(ugovori_types item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                  setText(String.format("%s, %s", item.getNaziv(), item.getBr()));
                }
              }
            };
          }
        });

    cmbUgovorIPTV.setCellFactory(new Callback<ListView<ugovori_types>, ListCell<ugovori_types>>() {
      @Override
      public ListCell<ugovori_types> call(ListView<ugovori_types> param) {
        return new ListCell<ugovori_types>() {
          @Override
          protected void updateItem(ugovori_types item, boolean empty) {
            super.updateItem(item, empty);
            if (!empty) {
              setText(String.format("%s, %s", item.getNaziv(), item.getBr()));
            }
          }
        };
      }
    });

    cmbFixPaket.setCellFactory(fixPaket -> new ListCell<FiksnaPaketi>() {
      public void updateItem(FiksnaPaketi fiksnaPaketi, boolean bool) {
        super.updateItem(fiksnaPaketi, bool);
        if (bool) {
          setText(null);
        } else {
          setText(fiksnaPaketi.getNaziv());
        }

      }
    });

    cmbIPTVPaket.setCellFactory(iptvPaket -> new ListCell<IPTVPaketi>() {
      @Override
      protected void updateItem(IPTVPaketi iptvPak, boolean empty) {
        super.updateItem(iptvPak, empty);
        if (empty) {
          setText(null);
        } else {
          setText(iptvPak.getName());
        }
      }
    });

    cmbNazivUslugeOstalo.setConverter(new StringConverter<OstaleUsluge>() {
      @Override
      public String toString(OstaleUsluge object) {
        return object.getNazivUsluge();
      }

      @Override
      public OstaleUsluge fromString(String string) {
        OstaleUsluge ostaleUsluge = new OstaleUsluge();
        ostaleUsluge.setNazivUsluge(string);
        return ostaleUsluge;
      }
    });

    cmbIPTVPaket.setConverter(new StringConverter<IPTVPaketi>() {
      @Override
      public String toString(IPTVPaketi object) {
        return object.getName();
      }

      @Override
      public IPTVPaketi fromString(String string) {
        IPTVPaketi iptvPaketiController = new IPTVPaketi();
        iptvPaketiController.setName(string);
        return iptvPaketiController;
      }
    });

    cmbFixPaket.setConverter(new StringConverter<FiksnaPaketi>() {
      @Override
      public String toString(FiksnaPaketi object) {
        return object.getNaziv();
      }

      @Override
      public FiksnaPaketi fromString(String string) {
        FiksnaPaketi fiksnaPaketi = new FiksnaPaketi();
        fiksnaPaketi.setNaziv(string);
        return fiksnaPaketi;
      }
    });

    cmbPaketBOX.valueProperty().addListener(new ChangeListener<BoxPaket>() {
      @Override
      public void changed(ObservableValue<? extends BoxPaket> observable, BoxPaket oldValue,
          BoxPaket newValue) {
        //get out if combo is null
        if (newValue == null) {
          return;
        }
        //disable-enable box input if value (not)exist
        if (newValue.getDTV_naziv() == null) {
          tKarticaBox.setDisable(true);
        } else {
          tKarticaBox.setDisable(false);
        }
        if (newValue.getFIKSNA_naziv() == null) {
          tFiksnaTelBox.setDisable(true);
        } else {
          tFiksnaTelBox.setDisable(false);
        }
        if (newValue.getNET_naziv() == null && newValue.getIPTV_naziv() == null) {
          tUserNameBox.setDisable(true);
          tPasswordBox.setDisable(true);
        } else {
          tUserNameBox.setDisable(false);
          tPasswordBox.setDisable(false);
        }
        if (newValue.getIPTV_naziv() == null) {
          tMACIPTVBox.setDisable(true);
        } else {
          tMACIPTVBox.setDisable(false);
          checkIPTVServerIsAlive();
        }
      }
    });

    cmbIPTVPaket.valueProperty().addListener(new ChangeListener<IPTVPaketi>() {
      @Override
      public void changed(ObservableValue<? extends IPTVPaketi> observable, IPTVPaketi oldValue,
          IPTVPaketi newValue) {
        checkIPTVServerIsAlive();
      }
    });

    cServicesBrojUgovora
        .setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("brUgovora"));
    cServicesDatum
        .setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("datum"));
    cServicesNaziv
        .setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("nazivPaketa"));
    cServicePopust
        .setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("popust"));
    cServiceCena
        .setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, Double>("cena"));
    cSservicesOperater
        .setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("operater"));
    cServiceAktivan
        .setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("aktivan"));
    cServiceObracun
        .setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("obracun"));
    cServiceIdentification.setCellValueFactory(
        new TreeItemPropertyValueFactory<ServicesUser, String>("idUniqueName"));
    cDatumIsteka
        .setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("endDate"));
    cPDV.setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, Double>("pdv"));
    cZaUplatu
        .setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, Double>("zaUplatu"));

    cPDV.setCellFactory(cf -> new TreeTableCell<ServicesUser, Double>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText("");
        } else {
          setText(df.format(item));
        }
      }

    });

    cZaUplatu.setCellFactory(cf -> new TreeTableCell<ServicesUser, Double>() {
      @Override
      protected void updateItem(Double item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText("");
        } else {
          setText(df.format(item));
        }
      }

    });

    cServiceCena.setCellFactory(lv -> new TreeTableCell<ServicesUser, Double>() {
      protected void updateItem(Double cena, boolean bool) {
        super.updateItem(cena, bool);
        if (bool || cena == null) {
          setText("");
        } else {
          setText(df.format(cena));
        }
      }
    });

    cServicePopust.setCellFactory(lv -> new TreeTableCell<ServicesUser, Double>() {
      protected void updateItem(Double popust, boolean bool) {
        super.updateItem(popust, bool);
        if (bool || popust == null) {
          setText("");
        } else {
          setText(df.format(popust) + "%");
        }
      }
    });

    cServicesDatum.setCellFactory(cd -> new TreeTableCell<ServicesUser, String>() {
      protected void updateItem(String date, boolean bool) {
        super.updateItem(date, bool);
        if (bool) {
          setText("");
        } else {
          setText(date);
        }
      }
    });
    cServiceAktivan.setCellFactory(cak -> new TreeTableCell<ServicesUser, Boolean>() {
      protected void updateItem(Boolean aktivan, boolean bool) {
        super.updateItem(aktivan, bool);
        if (bool || aktivan == null) {
          setText("");
        } else {
          if (aktivan) {
            setText("Da");
          } else {
            setText("Ne");
          }
        }
      }
    });

    cServiceObracun.setCellFactory(cObr -> new TreeTableCell<ServicesUser, Boolean>() {
      protected void updateItem(Boolean aktivan, boolean bool) {
        super.updateItem(aktivan, bool);
        if (bool || aktivan == null) {
          setText(null);
        } else {
          if (aktivan) {
            setText("Da");
          } else {
            setText("Ne");
          }
        }
      }
    });

    cmbPaketDTV.setConverter(new StringConverter<DigitalniTVPaket>() {
      @Override
      public String toString(DigitalniTVPaket object) {
        return object.getNaziv();
      }

      @Override
      public DigitalniTVPaket fromString(String string) {
        DigitalniTVPaket paket = new DigitalniTVPaket();
        paket.setNaziv(string);
        return paket;
      }
    });

    tMACIPTVBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
          Boolean newValue) {
        tMACIPTVBox.setText(tMACIPTVBox.getText().replace(":", "").trim());
        if (!newValue) {
          if (tMACIPTVBox.getLength() != 12) {
            AlertUser
                .error("MAC Adresa mora imati 12 karaktera", "MAC Adresa mora imati 12 karaktera");
            return;
          }
          String s = tMACIPTVBox.getText();
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < s.length(); ) {
            sb.append(s.substring(i++, ++i));
            if (i < s.length()) {
              sb.append(":");
            }
          }
          String mac = sb.toString();
          tMACIPTVBox.setText(mac);
        }
      }
    });

    tStbMACIPTV.focusedProperty().addListener(new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
          Boolean newValue) {

        tStbMACIPTV.setText(tStbMACIPTV.getText().replace(":", "").trim());

        if (!newValue) {
          if (tStbMACIPTV.getLength() != 12) {
            AlertUser
                .error("MAC Adresa mora imati 12 karaktera", "MAC Adresa mora imati 12 karaktera");
            return;
          }
          String s = tStbMACIPTV.getText();
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < s.length(); ) {
            sb.append(s.substring(i++, ++i));
            if (i < s.length()) {
              sb.append(":");
            }
          }
          String mac = sb.toString();
          tStbMACIPTV.setText(mac);
        }
      }
    });


  }

  private void checkIPTVServerIsAlive() {
    JSONObject object = new JSONObject();
    object.put("action", "check_iptv_is_alive");
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA",
          "IPTV Server nije dostupan, uslugu koju dodajete korisniku nece biti funkionalna");
    }
  }

  public void setData() {
    //default fields
    tPopustBox.setText("0.00");
    tPopustDTV.setText("0.00");
    tPopustIPTV.setText("0.00");
    tPopustInternet.setText("0.00");
    tFixPopust.setText("0.00");
    tPopustOstalo.setText("0.00");

    ObservableList dataInternetPaket = FXCollections.observableArrayList(get_internet_paketi());
    cmbPaketInternet.getItems().clear();
    cmbPaketInternet.setItems(dataInternetPaket);

    ObservableList dataDTVPaket = FXCollections.observableArrayList(get_digitalTV_paketi());
    cmbPaketDTV.getItems().clear();
    cmbPaketDTV.setItems(dataDTVPaket);

    ObservableList dataFiksnaPaket = FXCollections.observableArrayList(get_fiksna_paketi());
    cmbFixPaket.getItems().clear();
    cmbFixPaket.setItems(dataFiksnaPaket);

    refreshUgovori();

    ObservableList serviceList = FXCollections.observableArrayList(get_services_user());
    // set table datatblServices.setItems(serviceList);
    setTableServicesData(serviceList);

    ObservableList paketBox = FXCollections.observableArrayList(get_box_pakete());
    cmbPaketBOX.setItems(paketBox);

    ObservableList iptvPakets = FXCollections.observableArrayList(get_IPTV_paketi());
    cmbIPTVPaket.setItems(iptvPakets);

    ObservableList ostaleUsluge = FXCollections.observableArrayList(get_ostale_usluge());
    cmbNazivUslugeOstalo.setItems(ostaleUsluge);


  }

  private void setTableServicesData(ObservableList<ServicesUser> serviceList) {
    //main root node
    TreeItem rootNode = new TreeItem("SERVISI PAKETI");
    //creeate root node for each BOX AND DTV service and add child nodes
    for (ServicesUser service : serviceList) {
      //ako je box dodati tree node childs na novom root itemu
      if (service.getPaketType().equals("BOX")) {
        //get root tree item for box and all linked services included DTV with linked  addons, addonsPrograms and cards..
        TreeItem<ServicesUser> rootBOXService = get_linked_services_user(service);
        rootNode.getChildren().add(rootBOXService);

      } else if (service.getPaketType().equals("DTV")) {
        ///get root tree item for DTV And assigned addons
        ServicesUser servicesUser = new ServicesUser();
        TreeItem<ServicesUser> userServiceDTVAddonsROOT = servicesUser
            .getUserServiceDTVAddonsROOT(userID, service, false, client);
        rootNode.getChildren().add(userServiceDTVAddonsROOT);
      } else {
        rootNode.getChildren().add(new TreeItem<ServicesUser>(service));
      }
    }

    rootNode.setExpanded(true);
    tblServices.setShowRoot(false);
    tblServices.setRoot(rootNode);


  }


  private TreeItem<ServicesUser> get_linked_services_user(ServicesUser BOX_ID) {

    jObj = new JSONObject();
    jObj.put("action", "get_user_linked_services");
    jObj.put("box_ID", BOX_ID.getId());
    jObj.put("userID", userID);

    jObj = client.send_object(jObj);

    //we need to clone service to display in table independent, without sum of root treeitem
    ServicesUser serviceBOX = new ServicesUser();
    try {
      serviceBOX = BOX_ID.getClone();
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
    TreeItem<ServicesUser> rootService = new TreeItem<ServicesUser>(BOX_ID);
    rootService.getChildren().add(new TreeItem<ServicesUser>(serviceBOX));
    JSONObject serviceObj;
    ServicesUser service;

    double cena = BOX_ID.getCena();
    double pdv = BOX_ID.getPdv();
    double popust = BOX_ID.getPopust();
    double ukupno = 0;

    for (int i = 0; i < jObj.length(); i++) {
      service = new ServicesUser();
      serviceObj = jObj.getJSONObject(String.valueOf(i));
      service.setId(serviceObj.getInt("id"));
      service.setUserID(serviceObj.getInt("userID"));
      service.setId_Service(serviceObj.getInt("id_service"));
      service.setBox_id(serviceObj.getInt("box_ID"));
      service.setAktivan(serviceObj.getBoolean("aktivan"));
      service.setObracun(serviceObj.getBoolean("obracun"));
      service.setCena(serviceObj.getDouble("cena"));
      service.setPopust(serviceObj.getDouble("popust"));
      service.setPdv(serviceObj.getDouble("pdv"));
      service.setNazivPaketa(serviceObj.getString("nazivPaketa"));
      service.setNaziv(serviceObj.getString("nazivPaketa"));
      service.setLinkedService(serviceObj.getBoolean("linkedService"));
      service.setNewService(serviceObj.getBoolean("newService"));
      service.setPaketType(serviceObj.getString("paketType"));
      service.setEndDate(serviceObj.getString("endDate"));
      service.setBrUgovora(serviceObj.getString("brojUgovora"));
      service.setOperater(serviceObj.getString("operName"));
      service.setDatum(serviceObj.getString("date_added"));

      if (!serviceObj.getString("userName").trim().isEmpty()) {
        service.setGroupName(serviceObj.getString("groupName"));
        service.setUserName(serviceObj.getString("userName"));
        service.setIdUniqueName(serviceObj.getString("userName"));
      }

      if (!serviceObj.getString("idDTVCard").trim().isEmpty()) {
        service.setIdDTVCard(serviceObj.getString("idDTVCard"));
        service.setIdUniqueName(serviceObj.getString("idDTVCard"));
      }

      if (!serviceObj.getString("IPTV_MAC").trim().isEmpty()) {
        service.setIPTV_MAC(serviceObj.getString("IPTV_MAC"));
        service.setSTB_MAC(serviceObj.getString("IPTV_MAC"));
        service.setIdUniqueName(serviceObj.getString("IPTV_MAC"));
      }

      if (!serviceObj.getString("FIKSNA_TEL").trim().isEmpty()) {
        service.setFIKSNA_TEL(serviceObj.getString("FIKSNA_TEL"));
        service.setIdUniqueName(serviceObj.getString("FIKSNA_TEL"));
      }

      if (service.getPaketType().equals("LINKED_DTV")) {
        ServicesUser servicesUser = new ServicesUser();
        TreeItem<ServicesUser> userServiceDTVAddonsROOT = servicesUser
            .getUserServiceDTVAddonsROOT(userID, service, true, client);
        cena = cena + userServiceDTVAddonsROOT.getValue().getCena();
        rootService.getChildren().add(userServiceDTVAddonsROOT);

      } else if (service.getPaketType().equals("BOX")) {
        service.setIdUniqueName("");

      } else {
        rootService.getChildren().add(new TreeItem<>(service));
      }
    }
    ukupno = cena - valueToPercent.getPDVOfValue(cena, popust);
    ukupno = ukupno + valueToPercent.getPDVOfValue(ukupno, pdv);
    rootService.getValue().setCena(cena);
    rootService.getValue().setZaUplatu(ukupno);

    return rootService;
  }

  private ArrayList<ServicesUser> get_services_user() {
    jObj = new JSONObject();
    jObj.put("action", "get_user_services");
    jObj.put("userID", userID);

    jObj = client.send_object(jObj);

    ArrayList<ServicesUser> servicesArr = new ArrayList();
    JSONObject serviceObj;
    ServicesUser service;

    for (int i = 0; i < jObj.length(); i++) {
      service = new ServicesUser();
      serviceObj = (JSONObject) jObj.get(String.valueOf(i));
      service.setId(serviceObj.getInt("id"));
      service.setBrUgovora(serviceObj.getString("brojUgovora"));
      double cena = serviceObj.getDouble("cena");
      double popust = serviceObj.getDouble("popust");
      double pdv = serviceObj.getDouble("pdv");
      double zaUplatu = cena - valueToPercent.getPDVOfSum(cena, popust);
      zaUplatu = zaUplatu + valueToPercent.getPDVOfValue(zaUplatu, pdv);

      service.setCena(cena);
      service.setPopust(popust);
      service.setPdv(pdv);
      service.setZaUplatu(zaUplatu);
      service.setUserID(serviceObj.getInt("userID"));
      service.setOperater(serviceObj.getString("operName"));
      service.setDatum(serviceObj.getString("date_added"));
      service.setAktivan(serviceObj.getBoolean("aktivan"));
      service.setObracun(serviceObj.getBoolean("obracun"));
      service.setId_Service(serviceObj.getInt("id_service"));
      service.setNewService(serviceObj.getBoolean("newService"));
      service.setLinkedService(serviceObj.getBoolean("linkedService"));

      if (serviceObj.has("groupName")) {
        service.setGroupName(serviceObj.getString("groupName"));
        service.setUserName(serviceObj.getString("userName"));
      }

      if (serviceObj.has("endDate")) {
        service.setEndDate(serviceObj.getString("endDate"));
      }

      if (serviceObj.has("box_ID")) {
        service.setBox_id(serviceObj.getInt("box_ID"));
      }

      service.setNazivPaketa(serviceObj.getString("nazivPaketa"));
      service.setNaziv(serviceObj.getString("nazivPaketa"));

      if (serviceObj.has("idUniqueName")) {
        service.setIdUniqueName(serviceObj.getString("idUniqueName"));
      }

      if (serviceObj.has("FIKSNA_TEL")) {
        service.setFIKSNA_TEL(serviceObj.getString("FIKSNA_TEL"));
        service.setIdUniqueName(serviceObj.getString("FIKSNA_TEL"));
      }

      if (serviceObj.has("IPTV_MAC")) {
        service.setSTB_MAC(serviceObj.getString("IPTV_MAC"));
        //               service.setIPTV_EXT_ID(serviceObj.getString("external_id"));
      }

      if (serviceObj.has("idDTVCard")) {
        service.setIdDTVCard(serviceObj.getString("idDTVCard"));
      }

      service.setPaketType(serviceObj.getString("paketType"));
      service.setBox(serviceObj.getBoolean("box"));
      service.setLinkedService(serviceObj.getBoolean("linkedService"));
      service.setIdUniqueName(serviceObj.getString("idUniqueName"));

      servicesArr.add(service);
    }

    return servicesArr;
  }

  private ArrayList<ugovori_types> get_ugovori() {
    jObj = new JSONObject();
    jObj.put("action", "get_ugovori_user");
    jObj.put("userID", userID);

    jObj = client.send_object(jObj);

    ugovori_types ugovor;
    ArrayList<ugovori_types> ugovoriArr = new ArrayList();
    JSONObject ugovorObj;

    for (int i = 0; i < jObj.length(); i++) {
      ugovorObj = (JSONObject) jObj.get(String.valueOf(i));
      ugovor = new ugovori_types();
      ugovor.setId(ugovorObj.getInt("id"));
      ugovor.setBr(ugovorObj.getString("brojUgovora"));
      ugovor.setNaziv(ugovorObj.getString("naziv"));
      ugovor.setVrsta(ugovorObj.getString("vrsta"));
      ugovor.setTextUgovora(ugovorObj.getString("textUgovora"));
      ugovor.setKomentar(ugovorObj.getString("komentar"));
      ugovor.setPocetakUgovora(ugovorObj.getString("pocetakUgovora"));
      ugovor.setKrajUgovora(ugovorObj.getString("krajUgovora"));
      ugovor.setUserID(ugovorObj.getInt("userID"));
      ugovor.setServiceID(ugovorObj.getInt("serviceID"));
      ugovoriArr.add(ugovor);
    }

    return ugovoriArr;

  }

  private ArrayList<BoxPaket> get_box_pakete() {
    jObj = new JSONObject();
    jObj.put("action", "get_paket_box");
    jObj = client.send_object(jObj);

    ArrayList<BoxPaket> boxPaketsArr = new ArrayList<>();
    JSONObject boxObj;
    BoxPaket box;

    for (int i = 0; i < jObj.length(); i++) {
      box = new BoxPaket();
      boxObj = (JSONObject) jObj.get(String.valueOf(i));
      box.setId(boxObj.getInt("id"));
      box.setNaziv(boxObj.getString("naziv"));
      box.setCena(boxObj.getDouble("cena"));
      box.setPdv(boxObj.getDouble("pdv"));
      box.setPaketType(boxObj.getString("paketType"));
      if (boxObj.has("DTV_id")) {
        box.setDTV(boxObj.getInt("DTV_id"));
        box.setDTV_naziv(boxObj.getString("DTV_naziv"));
        box.setDTVPaketID(boxObj.getInt("DTV_PAKET_ID"));
      }
      if (boxObj.has("NET_id")) {
        box.setNET(boxObj.getInt("NET_id"));
        box.setNET_naziv(boxObj.getString("NET_naziv"));
      }
      if (boxObj.has("FIX_id")) {
        box.setFIKSNA(boxObj.getInt("FIX_id"));
        box.setFIKSNA_naziv(boxObj.getString("FIX_naziv"));
        box.setFIXPaketID(boxObj.getInt("FIX_PAKET_ID"));
      }
      if (boxObj.has("IPTV_id")) {
        box.setIPTV(boxObj.getInt("IPTV_id"));
        box.setIPTV_naziv(boxObj.getString("IPTV_naziv"));
        box.setTariff_plan(boxObj.getString("tariff_plan"));

      }
      boxPaketsArr.add(box);
    }
    return boxPaketsArr;
  }

  private ArrayList<InternetPaketi> get_internet_paketi() {
    jObj = new JSONObject();
    jObj.put("action", "get_internet_paketi");

    jObj = client.send_object(jObj);

    InternetPaketi iPaketi;
    ArrayList<InternetPaketi> iPaketiArr = new ArrayList();
    JSONObject iPaketiObj;

    for (int i = 0; i < jObj.length(); i++) {
      iPaketiObj = (JSONObject) jObj.get(String.valueOf(i));
      iPaketi = new InternetPaketi();
      iPaketi.setId(iPaketiObj.getInt("id"));
      iPaketi.setNaziv(iPaketiObj.getString("naziv"));
      iPaketi.setBrzina(iPaketiObj.getString("brzina"));
      iPaketi.setCena(iPaketiObj.getDouble("cena"));
      iPaketi.setPdv(iPaketiObj.getDouble("pdv"));
      iPaketi.setOpis(iPaketiObj.getString("opis"));
      iPaketi.setIdleTimeout(iPaketiObj.getString("idleTimeout"));
      iPaketiArr.add(iPaketi);
    }
    return iPaketiArr;
  }


  public void addServiceInternet(ActionEvent actionEvent) {

    if (cmbPaketInternet.getValue() == null || cmbUgovorInternet.getValue() == null
        || tPopustInternet.getText().isEmpty() || tUserNameInternet.getText().isEmpty()) {
      AlertUser.error("GRESKA", "Paket, Ugovor ili popust polja ne mogu biti prazna!");
      return;
    }

    jObj = new JSONObject();
    jObj.put("action", "add_service_to_user_NET");

    jObj.put("id", cmbPaketInternet.getValue().getId());
    jObj.put("nazivPaketa", cmbPaketInternet.getValue().getNaziv());
    jObj.put("userID", userID);
    jObj.put("servicePopust", Double.valueOf(tPopustInternet.getText()));
    jObj.put("paketType", "INTERNET");
    jObj.put("cena", Double.valueOf(cmbPaketInternet.getValue().getCena()));
    jObj.put("cena", cmbPaketInternet.getValue().getCena());
    jObj.put("obracun", chkRacunInternet.isSelected());
    jObj.put("brojUgovora", cmbUgovorInternet.getValue().getBr());
    jObj.put("userName", tUserNameInternet.getText());
    jObj.put("passWord", tLoznikaInternet.getText());
    jObj.put("groupName", cmbPaketInternet.getValue().getNaziv());
    jObj.put("pdv", cmbPaketInternet.getValue().getPdv());
    jObj.put("komentar", tOpisInternet.getText().trim());

    jObj = client.send_object(jObj);

    if (jObj.has("ERROR")) {
      AlertUser.error("GRESKA!", jObj.getString("ERROR"));
      return;
    } else {
      AlertUser.info("INFO", jObj.getString("Message"));

      tLoznikaInternet.setText(null);
      tOpisInternet.setText(null);
      tPopustInternet.setText(null);
      tLozinkaCInternet.setText(null);
      tOpisInternet.setText(null);
      tUserNameInternet.setText(null);
      chkRacunInternet.setSelected(true);
      setData();
    }
  }

  private ArrayList<DigitalniTVPaket> get_digitalTV_paketi() {
    jObj = new JSONObject();
    jObj.put("action", "getDigitalTVPaketi");

    jObj = client.send_object(jObj);

    DigitalniTVPaket dtvPaketi;
    ArrayList<DigitalniTVPaket> dtvPaketiArr = new ArrayList();
    JSONObject dtvPaketiObj;

    for (int i = 0; i < jObj.length(); i++) {
      dtvPaketiObj = (JSONObject) jObj.get(String.valueOf(i));
      dtvPaketi = new DigitalniTVPaket();
      dtvPaketi.setId(dtvPaketiObj.getInt("id"));
      dtvPaketi.setNaziv(dtvPaketiObj.getString("naziv"));
      dtvPaketi.setCena(dtvPaketiObj.getDouble("cena"));
      dtvPaketi.setPdv(dtvPaketiObj.getDouble("pdv"));
      dtvPaketi.setPaketID(dtvPaketiObj.getInt("idPaket"));
      dtvPaketi.setOpis(dtvPaketiObj.getString("opis"));
      dtvPaketiArr.add(dtvPaketi);
    }

    return dtvPaketiArr;
  }

  public void addServiceDTV(ActionEvent actionEvent) {

    if (cmbPaketDTV == null || cmbUgovorDTV == null || tPopustDTV.getText().isEmpty() || tKarticaDTV
        .getText().isEmpty()) {
      AlertUser.error("GRESKA", "Paket, ugovor, broj kartice i popust polje ne mogu biti prazna!");
      return;
    }

    jObj = new JSONObject();
    jObj.put("action", "add_service_to_user_DTV");

    jObj.put("id", cmbPaketDTV.getValue().getId());
    jObj.put("nazivPaketa", cmbPaketDTV.getValue().getNaziv());
    jObj.put("userID", userID);
    jObj.put("servicePopust", Double.valueOf(tPopustDTV.getText()));
    jObj.put("paketType", "DTV");
    jObj.put("cena", Double.valueOf(cmbPaketDTV.getValue().getCena()));
    jObj.put("pdv", cmbPaketDTV.getValue().getPdv());
    jObj.put("brojUgovora", cmbUgovorDTV.getValue().getBr());
    jObj.put("idUniqueName", tKarticaDTV.getText());
    jObj.put("DTVKarticaID", Integer.valueOf(tKarticaDTV.getText()));
    jObj.put("packetID", cmbPaketDTV.getValue().getPaketID());
    jObj.put("komentar", tOpisDTV.getText());
    jObj.put("pdv", cmbPaketDTV.getValue().getPdv());
    firstDateInMonth.set(Calendar.DAY_OF_MONTH, 1);
    firstDateInMonth.add(Calendar.MONTH, cmbPaketDTV.getValue().getProduzenje());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    jObj.put("endDate", sdf.format(firstDateInMonth.getTime()));

    jObj.put("obracun", chkRacunDTV.isSelected());

    jObj = client.send_object(jObj);

    if (jObj.has("ERROR")) {
      AlertUser.error("GRESKA!", jObj.getString("ERROR"));
      return;
    } else {
      AlertUser.info("INFO", "Usluga dodana");
      tPopustDTV.setText("");
      tKarticaDTV.setText("");
      chkRacunDTV.setSelected(true);
      tOpisDTV.setText("");
      setData();
    }


  }

  public void addServiceBox(ActionEvent actionEvent) {
    if (cmbPaketBOX.getValue() == null || cmbUgovorBox.getValue() == null || tPopustBox.getText()
        .isEmpty()) {
      AlertUser.error("GRESKA", "Paket, ugovor i popust polje ne mogu biti prazna");
      return;
    }

    jObj = new JSONObject();
    jObj.put("action", "add_BOX_Service");

    jObj.put("id", cmbPaketBOX.getValue().getId());
    jObj.put("nazivPaketa", cmbPaketBOX.getValue().getNaziv());
    if (cmbPaketBOX.getValue().getDTV_naziv() != null) {
      jObj.put("nazivPaketaDTV", cmbPaketBOX.getValue().getDTV_naziv());
      jObj.put("DTV_service_ID", cmbPaketBOX.getValue().getDTV());
    }
    if (cmbPaketBOX.getValue().getNET_naziv() != null) {
      jObj.put("nazivPaketaNET", cmbPaketBOX.getValue().getNET_naziv());
      jObj.put("NET_service_ID", cmbPaketBOX.getValue().getNET());
    }
    if (cmbPaketBOX.getValue().getFIKSNA_naziv() != null) {
      jObj.put("nazivPaketaFIKSNA", cmbPaketBOX.getValue().getFIKSNA_naziv());
      jObj.put("FIKSNA_service_ID", cmbPaketBOX.getValue().getFIKSNA());
      jObj.put("FIKSNA_PAKET_ID", cmbPaketBOX.getValue().getFIXPaketID());
    }
    if (cmbPaketBOX.getValue().getIPTV_naziv() != null) {
      jObj.put("nazivPaketaIPTV", cmbPaketBOX.getValue().getIPTV_naziv());
      jObj.put("IPTV_service_ID", cmbPaketBOX.getValue().getIPTV());
    }

    jObj.put("userID", userID);
    jObj.put("servicePopust", Double.valueOf(tPopustBox.getText()));
    jObj.put("cena", Double.valueOf(cmbPaketBOX.getValue().getCena()));
    jObj.put("idPaket", cmbPaketBOX.getValue().getId());
    jObj.put("brojUgovora", cmbUgovorBox.getValue().getBr());
    firstDateInMonth.set(Calendar.DAY_OF_MONTH, 1);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    jObj.put("endDate", sdf.format(firstDateInMonth.getTime()));
    jObj.put("obracun", chkRacunBOX.isSelected());
    jObj.put("popust", Double.valueOf(tPopustBox.getText()));
    jObj.put("cena", Double.valueOf(cmbPaketBOX.getValue().getCena()));
    jObj.put("pdv", cmbPaketBOX.getValue().getPdv());
    jObj.put("komentar", tOpisBox.getText());

    if (!tUserNameBox.getText().isEmpty()) {
      jObj.put("userName", tUserNameBox.getText());
    }
    jObj.put("passWord", tPasswordBox.getText());

    if (cmbPaketBOX.getValue().getNET_naziv() != null) {
      jObj.put("groupName", cmbPaketBOX.getValue().getNET_naziv());
    }

    if (!tKarticaBox.getText().isEmpty()) {
      jObj.put("cardID", Integer.valueOf(tKarticaBox.getText()));
      jObj.put("paketID", cmbPaketBOX.getValue().getDTVPaketID());
    }

    if (!tFiksnaTelBox.getText().isEmpty()) {
      jObj.put("FIX_TEL", tFiksnaTelBox.getText());
    }

    if (!tMACIPTVBox.getText().isEmpty()) {
      jObj.put("IPTV_MAC", tMACIPTVBox.getText().trim());
      jObj.put("STB_MAC", tMACIPTVBox.getText().trim());
    }
    if (jObj.has("STB_MAC")) {
      jObj.put("login", tUserNameBox.getText());
      jObj.put("full_name", userEdit.getIme());
      jObj.put("account_number", userEdit.getId());
      jObj.put("tariff_plan", cmbPaketBOX.getValue().getIPTV_naziv());
      jObj.put("password", tPasswordBox.getText());
      jObj.put("IPTV_Service_ID", cmbPaketBOX.getValue().getIPTV());
    }
    jObj.put("opis", tOpisBox.getText());

    jObj = client.send_object(jObj);

    if (jObj.has("ERROR")) {
      AlertUser.error("GRESKA", jObj.getString("ERROR"));
      return;
    } else {
      AlertUser.info("INFO", "Usluga dodana korisniku");
      tPopustBox.clear();
      tKarticaBox.clear();
      tUserNameBox.clear();
      tPasswordBox.clear();
      tMACIPTVBox.clear();
      tFiksnaTelBox.clear();
      tPopustBox.clear();
      tOpisBox.clear();
      setData();
    }

  }

  public void activateService(ActionEvent actionEvent) {
    if (tblServices.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.warrning("Izaberite uslugu za aktiviranje/deaktiviranje",
          "Nije izabrana ni jedna usluga");
      return;
    }

    ServicesUser servicesUser = tblServices.getSelectionModel().getSelectedItem().getValue();

    jObj = new JSONObject();
    jObj.put("action", "activate_new_service");
    jObj.put("service_id", servicesUser.getId());
    jObj.put("userID", servicesUser.getUserID());

    jObj = client.send_object(jObj);
    if (jObj.has("ERROR")) {
      AlertUser.error("GRESKA", jObj.getString("ERROR"));
    }

    setData();
  }

  public void deleteUserService(ActionEvent actionEvent) {
    jObj = new JSONObject();
    JSONObject srvObj = new JSONObject();
    jObj.put("action", "delete_service_user");
    jObj.put("userID", userID);
    //   ServicesUser srvUser = (ServicesUser) tblServices.getSelectionModel().getSelectedItem();
    TreeItem<ServicesUser> srvUser = (TreeItem<ServicesUser>) tblServices.getSelectionModel()
        .getSelectedItem();

    if (!AlertUser.yesNo("BRISANJE USLUGE KORISNIKA",
        "Da li ste sigurni da želite da izbrišite uslugu " + srvUser.getValue().getNaziv())) {
      return;
    }

    boolean zaduzi = AlertUser
        .yesNo("ZADUZIVANJE", String.format("Zaduzi uslugu %s?", srvUser.getValue().getNaziv()));
    jObj.put("serviceID", srvUser.getValue().getId());
    jObj.put("zaduzi", zaduzi);

    jObj = client.send_object(jObj);

    if (jObj.has("ERROR")) {
      AlertUser.error("GRESKA", jObj.getString("ERROR"));
    } else {
      AlertUser.info("SERVIS", "Usluga izbrisana");
    }

    setData();
  }

  public void refreshUgovori() {
    ObservableList ugovoriCombo = FXCollections.observableArrayList(get_ugovori());
    cmbUgovorDTV.getItems().clear();
    cmbUgovorDTV.setItems(ugovoriCombo);
    cmbUgovorInternet.getItems().clear();
    cmbUgovorInternet.setItems(ugovoriCombo);
    cmbUgovorBox.getItems().clear();
    cmbUgovorBox.setItems(ugovoriCombo);
    cmbFixBrojUgovora.getItems().clear();
    cmbFixBrojUgovora.setItems(ugovoriCombo);
    cmbUgovorIPTV.setItems(ugovoriCombo);
    cmbUgovorOstalo.setItems(ugovoriCombo);
  }

  public void addServiceFIX(ActionEvent actionEvent) {
    jObj = new JSONObject();

    cmbFixPaket.getValue().getId();
    cmbFixPaket.getValue().getNaziv();

    jObj.put("id", cmbFixPaket.getValue().getId());
    jObj.put("nazivPaketa", cmbFixPaket.getValue().getNaziv());
    jObj.put("userID", userID);
    jObj.put("popust", Double.valueOf(tFixPopust.getText()));
    jObj.put("cena", cmbFixPaket.getValue().getPretplata());
    jObj.put("pdv", cmbFixPaket.getValue().getPdv());
    jObj.put("obracun", chkFixStampaObracun.isSelected());
    jObj.put("brojUgovora", cmbFixBrojUgovora.getValue().getBr());
    jObj.put("brojTel", tFixBrojTel.getText());
    jObj.put("komentar", tFixOpis.getText().trim());

    jObj.put("action", "addFixUslugu");
    jObj = client.send_object(jObj);

    if (jObj.has("ERROR")) {
      AlertUser.error("Greska", jObj.getString("ERROR"));
    } else {
      AlertUser.info("Servis je dodan", String.format("Servis %s je dodan ",
          cmbFixPaket.getValue().getNaziv()));
      setData();
    }


  }

  public ArrayList<FiksnaPaketi> get_fiksna_paketi() {
    jObj = new JSONObject();
    jObj.put("action", "show_fixTel_paketi");
    jObj = client.send_object(jObj);
    ArrayList<FiksnaPaketi> paketArr = new ArrayList<>();
    FiksnaPaketi fiksnaPaketi;
    JSONObject fiksnaObj;

    for (int i = 0; i < jObj.length(); i++) {
      fiksnaObj = (JSONObject) jObj.get(String.valueOf(i));
      fiksnaPaketi = new FiksnaPaketi();
      fiksnaPaketi.setId(fiksnaObj.getInt("id"));
      fiksnaPaketi.setNaziv(fiksnaObj.getString("naziv"));
      fiksnaPaketi.setBesplatniMinutiFiksna(fiksnaObj.getInt("besplatniMinutiFiksna"));
      fiksnaPaketi.setPdv(fiksnaObj.getDouble("pdv"));
      fiksnaPaketi.setPretplata(fiksnaObj.getDouble("pretplata"));
      paketArr.add(fiksnaPaketi);

    }
    return paketArr;
  }

  public ArrayList<OstaleUsluge> get_ostale_usluge() {
    jObj = new JSONObject();
    jObj.put("action", "getOstaleUslugeData");
    jObj = client.send_object(jObj);
    ArrayList<OstaleUsluge> ostaleUslugesArr = new ArrayList<>();
    for (int i = 0; i < jObj.length(); i++) {
      JSONObject objUsluge = jObj.getJSONObject(String.valueOf(i));
      OstaleUsluge ostaleUsluge = new OstaleUsluge();
      ostaleUsluge.setId(objUsluge.getInt("id"));
      ostaleUsluge.setNazivUsluge(objUsluge.getString("naziv"));
      ostaleUsluge.setCena(objUsluge.getDouble("cena"));
      ostaleUsluge.setPdv(objUsluge.getDouble("pdv"));
      ostaleUsluge.setKomentar(objUsluge.getString("opis"));
      ostaleUslugesArr.add(ostaleUsluge);

    }
    return ostaleUslugesArr;
  }


  private ArrayList<IPTVPaketi> get_IPTV_paketi() {
    JSONObject jObj = new JSONObject();
    jObj.put("action", "getIPTVDataLocal");
    jObj = client.send_object(jObj);
    ArrayList<IPTVPaketi> iptvPaketiArrayList = new ArrayList<>();
    IPTVPaketi iptvPaketi;
    JSONObject iptvJSON;

    for (int i = 0; i < jObj.length(); i++) {
      iptvJSON = (JSONObject) jObj.get(String.valueOf(i));
      iptvPaketi = new IPTVPaketi();
      iptvPaketi.setId(iptvJSON.getInt("id"));
      iptvPaketi.setExternal_id(iptvJSON.getString("external_id"));
      iptvPaketi.setIptv_id(iptvJSON.getInt("IPTV_id"));
      iptvPaketi.setName(iptvJSON.getString("name"));
      iptvPaketi.setDescription(iptvJSON.getString("opis"));
      iptvPaketi.setCena(iptvJSON.getDouble("cena"));
      iptvPaketi.setPdv(iptvJSON.getDouble("pdv"));
      iptvPaketiArrayList.add(iptvPaketi);
    }
    return iptvPaketiArrayList;
  }

  public void addServiceIPTV(ActionEvent actionEvent) {
    if (!tPasswordIPTV.getText().equals(tPasswordIPTVCheck.getText())) {
      AlertUser.error("GRESKA", "Passwordi nisu identični");
      return;
    }

    JSONObject jObj = new JSONObject();
    jObj.put("action", "save_IPTV_USER");
    jObj.put("id", cmbIPTVPaket.getValue().getId());
    jObj.put("userID", userID);
    jObj.put("login", tUserNameIPTV.getText());
    jObj.put("password", tPasswordIPTV.getText());
    jObj.put("account_number", userID);
    jObj.put("nazivPaketa", cmbIPTVPaket.getValue().getName());
    jObj.put("full_name", userEdit.getIme());
    jObj.put("external_id", cmbIPTVPaket.getValue().getExternal_id());
    jObj.put("tariff_plan", cmbIPTVPaket.getValue().getName());
    jObj.put("status", 1);
    jObj.put("cena", cmbIPTVPaket.getValue().getCena());
    jObj.put("popust", tPopustIPTV.getText());
    jObj.put("pdv", cmbIPTVPaket.getValue().getPdv());
    jObj.put("brojUgovora", cmbUgovorIPTV.getValue().getBr());
    jObj.put("STB_MAC", tStbMACIPTV.getText().trim());
    jObj.put("komentar", tOpisIPTV.getText().trim());
    if (cmbObracunIPTV.isSelected()) {
      jObj.put("obracun", true);
    } else {
      jObj.put("obracun", false);
    }

    jObj = client.send_object(jObj);

    if (jObj.has("Error") || jObj.has("ERROR")) {
      AlertUser.error("GRESKA", jObj.getString("ERROR"));
    } else {
      AlertUser.info("Usluga je dodana", String.format("Usluga %s je dodana.",
          cmbIPTVPaket.getValue().getName()));
      setData();
    }
  }

  public void addServiceOstalo(ActionEvent actionEvent) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "save_OstaleUsluge_USER");
    jsonObject.put("id", cmbNazivUslugeOstalo.getValue().getId());
    jsonObject.put("naziv", cmbNazivUslugeOstalo.getValue().getNazivUsluge());
    jsonObject.put("userID", userID);
    jsonObject.put("cena", cmbNazivUslugeOstalo.getValue().getCena());
    jsonObject.put("pdv", cmbNazivUslugeOstalo.getValue().getPdv());
    jsonObject.put("popust", tPopustOstalo.getText());
    jsonObject.put("obracun", chkProduzenjeOstalo.isSelected());
    jsonObject.put("komentar", taKomentarOstalo.getText().trim());
    jsonObject.put("markForDelete", chkTempZaduzenje.isSelected());
    if (cmbUgovorOstalo.getSelectionModel().isEmpty()) {
      jsonObject.put("brojUgovora", "0");
    } else {
      jsonObject.put("brojUgovora", cmbUgovorOstalo.getValue().getBr());
    }
    jsonObject.put("paketType", "OSTALE_USLUGE");

    jsonObject = client.send_object(jsonObject);

    if (jObj.has("ERROR")) {
      AlertUser.error("GRESKA", jsonObject.getString("ERROR"));
    } else {
      AlertUser.info("Usluga je dodana",
          String.format("Usluga %s je dodana", cmbNazivUslugeOstalo.getValue().getNazivUsluge()));
    }
    setData();
  }

  public void showIzmeniServis(ActionEvent actionEvent) {
    if (tblServices.getSelectionModel().getSelectedIndex() == -1) {
      AlertUser.info("NIJE IZABRANA USLUGA", "Izaberite usluge za izmene");
      return;
    }
    ServicesUser selectedItem = tblServices.getSelectionModel().getSelectedItem().getValue();
    if (selectedItem.getPaketType().equals("DTV")) {
      NewInterface dtvEditServiceInterface = new NewInterface("fxml/Dtv/DTVEditService.fxml",
          "IZMENA DTV SERVISA", resources);
      DTVEditController dtvEditController = dtvEditServiceInterface.getLoader().getController();
      dtvEditController.setClient(this.client);
      dtvEditController.setService(selectedItem);
      dtvEditController.initData();
      dtvEditServiceInterface.getStage().showAndWait();
    } else if (selectedItem.getPaketType().equals("BOX")) {
      NewInterface boxEditInterface = new NewInterface("fxml/Box/BOXEdit.fxml",
          "IZMENA BOX SERVISA", resources);
      BOXEditController boxEditController = boxEditInterface.getLoader().getController();
      boxEditController.setClient(this.client);
      boxEditController.setService(selectedItem);
      boxEditController.initData();
      boxEditInterface.getStage().showAndWait();
    }

    this.setData();
  }

  public void setClient(Client client) {
    this.client = client;
  }


}
