package net.yuvideo.jgemstone.client.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import net.yuvideo.jgemstone.client.classes.*;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Optional;
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
    public ComboBox<digitalniTVPaket> cmbPaketDTV;
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
    public TextField tProduzenjeBox;
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

    //IPTV PAKETI
    public Client client;
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
    public TextField tDTVPrekoracenje;
    public TextField tInternetPrekoracenje;
    public TextField tIPTVPrekoracenje;
    Calendar firstDateInMonth = Calendar.getInstance();
    private ResourceBundle resources;
    private URL location;
    private JSONObject jObj;
    private DecimalFormat df = new DecimalFormat("#,##0.00");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        tblServices.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<ServicesUser>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<ServicesUser>> observable, TreeItem<ServicesUser> oldValue, TreeItem<ServicesUser> newValue) {
                if (tblServices.getSelectionModel().getSelectedIndex() == -1) {
                    return;
                }
                ServicesUser srvusr = tblServices.getSelectionModel().getSelectedItem().getValue();

                System.out.println(
                        "IS BOX: " + srvusr.getBox() + "\n" + "NAZIV PAKETA: " + srvusr.getNazivPaketa() + "\n" +
                                "ID SERVICE: " + srvusr.getId_Service() + "\n" +
                                "PAKET TYPE: " + srvusr.getPaketType() + "\n" +
                                "IS LINKED: " + srvusr.getLinkedService() + "\n" +
                                "BOX_ID: " + srvusr.getBox_id() + "\n" +
                                "NEW_SERVICE: " + srvusr.getNewService() + "\n"
                );

                if (srvusr.getLinkedService()) {
                    bActivateService.setDisable(true);
                    bDeleteService.setDisable(true);
                } else {
                    bActivateService.setDisable(false);
                    bDeleteService.setDisable(false);
                }
                if (srvusr.getAktivan() || srvusr.getLinkedService()) {
                    bActivateService.setDisable(true);
                } else {
                    bActivateService.setDisable(false);
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

        cmbPaketDTV.setCellFactory(lv -> new ListCell<digitalniTVPaket>() {
            public void updateItem(digitalniTVPaket paket, boolean bool) {
                super.updateItem(paket, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(paket.getNaziv());
                }
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
            public void changed(ObservableValue<? extends BoxPaket> observable, BoxPaket oldValue, BoxPaket newValue) {
                //get out if combo is null
                if (newValue == null)
                    return;
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
                }
            }
        });


        cServicesBrojUgovora.setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("brUgovora"));
        cServicesDatum.setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("datum"));
        cServicesNaziv.setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("nazivPaketa"));
        cServicePopust.setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("popust"));
        cServiceCena.setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, Double>("cena"));
        cSservicesOperater.setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("operater"));
        cServiceAktivan.setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("aktivan"));
        cServiceObracun.setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("obracun"));
        cServiceIdentification.setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("idUniqueName"));
        cDatumIsteka.setCellValueFactory(new TreeItemPropertyValueFactory<ServicesUser, String>("endDate"));


        cServiceCena.setCellFactory(lv -> new TreeTableCell<ServicesUser, Double>() {
            protected void updateItem(Double cena, boolean bool) {
                super.updateItem(cena, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(df.format(cena));
                }
            }
        });

        cServicePopust.setCellFactory(lv -> new TreeTableCell<ServicesUser, Double>() {
            protected void updateItem(Double popust, boolean bool) {
                super.updateItem(popust, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(df.format(popust) + "%");
                }
            }
        });

        cServicesDatum.setCellFactory(cd -> new TreeTableCell<ServicesUser, String>() {
            protected void updateItem(String date, boolean bool) {
                super.updateItem(date, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(date);
                }
            }
        });
        cServiceAktivan.setCellFactory(cak -> new TreeTableCell<ServicesUser, Boolean>() {
            protected void updateItem(Boolean aktivan, boolean bool) {
                super.updateItem(aktivan, bool);
                if (bool) {
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


        cServiceObracun.setCellFactory(cObr -> new TreeTableCell<ServicesUser, Boolean>() {
            protected void updateItem(Boolean aktivan, boolean bool) {
                super.updateItem(aktivan, bool);
                if (bool) {
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


        cmbPaketDTV.setConverter(new StringConverter<digitalniTVPaket>() {
            @Override
            public String toString(digitalniTVPaket object) {
                return object.getNaziv();
            }

            @Override
            public digitalniTVPaket fromString(String string) {
                digitalniTVPaket paket = new digitalniTVPaket();
                paket.setNaziv(string);
                return paket;
            }
        });


        tMACIPTVBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (!newValue) {
                    if (tMACIPTVBox.getLength() != 12) {
                        AlertUser.error("MAC Adresa mora imati 12 karaktera", "MAC Adresa mora imati 12 karaktera");
                        return;
                    }
                    String s = tMACIPTVBox.getText();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < s.length(); ) {
                        sb.append(s.substring(i++, ++i));
                        if (i < s.length()) sb.append(":");
                    }
                    String mac = sb.toString();
                    tMACIPTVBox.setText(mac);
                }
            }
        });

        tStbMACIPTV.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (!newValue) {
                    if (tStbMACIPTV.getLength() != 12) {
                        AlertUser.error("MAC Adresa mora imati 12 karaktera", "MAC Adresa mora imati 12 karaktera");
                        return;
                    }
                    String s = tStbMACIPTV.getText();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < s.length(); ) {
                        sb.append(s.substring(i++, ++i));
                        if (i < s.length()) sb.append(":");
                    }
                    String mac = sb.toString();
                    tStbMACIPTV.setText(mac);
                }
            }
        });


    }

    public void setData() {
        //default fields
        tPopustBox.setText("0.00");
        tPopustDTV.setText("0.00");
        tPopustIPTV.setText("0.00");
        tPopustInternet.setText("0.00");
        tProduzenjeBox.setText("2");
        tFixPopust.setText("0.00");


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


    }

    private void setTableServicesData(ObservableList<ServicesUser> serviceList) {
        //main root node
        TreeItem rootNode = new TreeItem("SERVISI PAKETI");
        //creeate root node for each BOX service and add child nodes
        for (ServicesUser service : serviceList) {
            //ako je box dodati tree node childs na novom root itemu
            if (service.getBox() == true) {
                TreeItem<ServicesUser> rootService = new TreeItem(service);

                ArrayList<ServicesUser> arrSrvUser = get_linked_services_user(service.getId());
                for (ServicesUser srvUser : arrSrvUser) {
                    rootService.getChildren().add(new TreeItem<ServicesUser>(srvUser));
                }
                rootNode.getChildren().add(rootService);

            } else {
                rootNode.getChildren().add(new TreeItem<ServicesUser>(service));
            }


        }

        rootNode.setExpanded(true);
        tblServices.setShowRoot(false);
        tblServices.setRoot(rootNode);


    }


    private ArrayList<ServicesUser> get_linked_services_user(int BOX_ID) {
        jObj = new JSONObject();
        jObj.put("action", "get_user_linked_services");
        jObj.put("box_ID", BOX_ID);
        jObj.put("userID", userID);

        jObj = client.send_object(jObj);

        ArrayList<ServicesUser> servicesUsersArr = new ArrayList<>();
        JSONObject serviceObj;
        ServicesUser service;

        for (int i = 0; i < jObj.length(); i++) {
            service = new ServicesUser();
            serviceObj = (JSONObject) jObj.get(String.valueOf(i));
            service.setId(serviceObj.getInt("id"));
            service.setUserID(serviceObj.getInt("userID"));
            service.setId_Service(serviceObj.getInt("id_service"));
            service.setBox_id(serviceObj.getInt("box_ID"));
            service.setProduzenje(serviceObj.getInt("produzenje"));
            service.setCena(serviceObj.getDouble("cena"));
            service.setPopust(serviceObj.getDouble("popust"));
            service.setNazivPaketa(serviceObj.getString("nazivPaketa"));
            service.setLinkedService(serviceObj.getBoolean("linkedService"));
            service.setPaketType(serviceObj.getString("paketType"));
            service.setNewService(serviceObj.getBoolean("newService"));
            service.setEndDate(serviceObj.getString("endDate"));

            if (serviceObj.has("groupName"))
                service.setGroupName(serviceObj.getString("groupName"));

            if (serviceObj.has("userName")) {
                service.setUserName(serviceObj.getString("userName"));
                service.setIdUniqueName(serviceObj.getString("userName"));
            }

            if (serviceObj.has("idDTVCard")) {
                service.setIdDTVCard(serviceObj.getString("idDTVCard"));
                service.setIdUniqueName(serviceObj.getString("idDTVCard"));
            }


            if (serviceObj.has("IPTV_MAC")) {
                service.setIPTV_MAC(serviceObj.getString("IPTV_MAC"));
                service.setIdUniqueName(serviceObj.getString("IPTV_MAC"));
                service.setSTB_MAC(serviceObj.getString("IPTV_MAC"));
                service.setIdUniqueName(serviceObj.getString("IPTV_MAC"));
            }

            if (serviceObj.has("FIKSNA_TEL")) {
                service.setFIKSNA_TEL(serviceObj.getString("FIKSNA_TEL"));
                service.setIdUniqueName(serviceObj.getString("FIKSNA_TEL"));
            }


            servicesUsersArr.add(service);
        }


        return servicesUsersArr;
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
            service.setCena(serviceObj.getDouble("cena"));
            service.setPopust(serviceObj.getDouble("popust"));
            service.setOperater(serviceObj.getString("operName"));
            service.setDatum(serviceObj.getString("date_added"));
            service.setAktivan(serviceObj.getBoolean("aktivan"));
            service.setObracun(serviceObj.getBoolean("obracun"));
            service.setProduzenje(serviceObj.getInt("produzenje"));
            service.setId_Service(serviceObj.getInt("id_service"));
            service.setNewService(serviceObj.getBoolean("newService"));
            service.setLinkedService(serviceObj.getBoolean("linkedService"));

            if (serviceObj.has("endDate"))
                service.setEndDate(serviceObj.getString("endDate"));

            if (serviceObj.has("box_ID"))
                service.setBox_id(serviceObj.getInt("box_ID"));

            service.setNazivPaketa(serviceObj.getString("nazivPaketa"));

            if (serviceObj.has("idUniqueName"))
                service.setIdUniqueName(serviceObj.getString("idUniqueName"));

            if (serviceObj.has("brojTel")) {
                service.setFIKSNA_TEL(serviceObj.getString("brojTel"));
                service.setIdUniqueName(serviceObj.getString("brojTel"));
            }

            if (serviceObj.has("IPTV_MAC")) {
                service.setSTB_MAC(serviceObj.getString("IPTV_MAC"));
                //               service.setIPTV_EXT_ID(serviceObj.getString("external_id"));
            }

            service.setPaketType(serviceObj.getString("paketType"));
            service.setCena(serviceObj.getDouble("cena"));
            service.setBox(serviceObj.getBoolean("box"));
            service.setLinkedService(serviceObj.getBoolean("linkedService"));

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
            iPaketi.setOpis(iPaketiObj.getString("opis"));
            iPaketi.setIdleTimeout(iPaketiObj.getString("idleTimeout"));
            iPaketiArr.add(iPaketi);
        }
        return iPaketiArr;
    }


    public void addServiceInternet(ActionEvent actionEvent) {

        if (cmbPaketInternet.getValue() == null || cmbUgovorInternet.getValue() == null || tPopustInternet.getText().isEmpty() || tUserNameInternet.getText().isEmpty()) {
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
        jObj.put("obracun", chkRacunInternet.isSelected());
        jObj.put("brojUgovora", cmbUgovorInternet.getValue().getBr());
        jObj.put("userName", tUserNameInternet.getText());
        jObj.put("passWord", tLoznikaInternet.getText());
        jObj.put("produzenje", Integer.valueOf(tInternetPrekoracenje.getText()));
        jObj.put("groupName", cmbPaketInternet.getValue().getNaziv());


        jObj = client.send_object(jObj);

        if (jObj.has("Error")) {
            AlertUser.error("GRESKA!", jObj.getString("Error"));
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

    private ArrayList<digitalniTVPaket> get_digitalTV_paketi() {
        jObj = new JSONObject();
        jObj.put("action", "getDigitalTVPaketi");

        jObj = client.send_object(jObj);

        digitalniTVPaket dtvPaketi;
        ArrayList<digitalniTVPaket> dtvPaketiArr = new ArrayList();
        JSONObject dtvPaketiObj;

        for (int i = 0; i < jObj.length(); i++) {
            dtvPaketiObj = (JSONObject) jObj.get(String.valueOf(i));
            dtvPaketi = new digitalniTVPaket();
            dtvPaketi.setId(dtvPaketiObj.getInt("id"));
            dtvPaketi.setNaziv(dtvPaketiObj.getString("naziv"));
            dtvPaketi.setCena(dtvPaketiObj.getDouble("cena"));
            dtvPaketi.setPaketID(dtvPaketiObj.getInt("idPaket"));
            dtvPaketi.setOpis(dtvPaketiObj.getString("opis"));
            dtvPaketiArr.add(dtvPaketi);
        }

        return dtvPaketiArr;
    }

    public void addServiceDTV(ActionEvent actionEvent) {

        if (cmbPaketDTV == null || cmbUgovorDTV == null || tPopustDTV.getText().isEmpty()) {
            AlertUser.error("GRESKA", "Paket, ugovor i popust polje ne mogu biti prazna!");
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
        jObj.put("idPaket", cmbPaketDTV.getValue().getPaketID());
        jObj.put("brojUgovora", cmbUgovorDTV.getValue().getBr());
        jObj.put("idUniqueName", tKarticaDTV.getText());
        jObj.put("packetID", cmbPaketDTV.getValue().getPaketID());
        jObj.put("produzenje", Integer.valueOf(tDTVPrekoracenje.getText()));
        jObj.put("opis", tOpisDTV.getText());
        firstDateInMonth.set(Calendar.DAY_OF_MONTH, 1);
        firstDateInMonth.add(Calendar.MONTH, cmbPaketDTV.getValue().getProduzenje());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jObj.put("endDate", sdf.format(firstDateInMonth.getTime()));

        jObj.put("obracun", chkRacunDTV.isSelected());

        jObj = client.send_object(jObj);

        if (jObj.has("Error")) {
            AlertUser.error("GRESKA!", jObj.getString("Error"));
            return;
        } else {
            AlertUser.info("INFO", jObj.getString("Message"));
            tPopustDTV.setText("");
            tKarticaDTV.setText("");
            chkRacunDTV.setSelected(true);
            tOpisDTV.setText("");
            setData();
        }


    }

    public void addServiceBox(ActionEvent actionEvent) {
        if (cmbPaketBOX == null || cmbUgovorBox == null || tPopustBox.getText().isEmpty()) {
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
        jObj.put("produzenje", Integer.valueOf(tProduzenjeBox.getText()));
        firstDateInMonth.set(Calendar.DAY_OF_MONTH, 1);
        firstDateInMonth.add(Calendar.MONTH, Integer.valueOf(tProduzenjeBox.getText()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jObj.put("endDate", sdf.format(firstDateInMonth.getTime()));
        jObj.put("obracun", chkRacunBOX.isSelected());
        jObj.put("popust", Double.valueOf(tPopustBox.getText()));
        jObj.put("cena", Double.valueOf(cmbPaketBOX.getValue().getCena()));
        jObj.put("opis", tOpisBox.getText());

        if (!tUserNameBox.getText().isEmpty())
            jObj.put("userName", tUserNameBox.getText());
        jObj.put("passWord", tPasswordBox.getText());

        if (!cmbPaketBOX.getValue().getNET_naziv().isEmpty())
            jObj.put("groupName", cmbPaketBOX.getValue().getNET_naziv());


        if (!tKarticaBox.getText().isEmpty()) {
            jObj.put("DTVKartica", Integer.valueOf(tKarticaBox.getText()));
            jObj.put("DTVPaket", cmbPaketBOX.getValue().getDTVPaketID());
        }

        if (!tFiksnaTelBox.getText().isEmpty())
            jObj.put("FIX_TEL", tFiksnaTelBox.getText());

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


        jObj = client.send_object(jObj);

        if (jObj.has("Error")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));
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
            tProduzenjeBox.clear();
            tOpisBox.clear();
            setData();
        }

    }

    public void activateService(ActionEvent actionEvent) {
        if (tblServices.getSelectionModel().getSelectedIndex() == -1) {
            AlertUser.warrning("Izaberite uslugu za aktiviranje/deaktiviranje", "Nije izabrana ni jedna usluga");
            return;
        }

        ServicesUser servicesUser = tblServices.getSelectionModel().getSelectedItem().getValue();
        boolean aktivan = false;
        servicesUser.getAktivan();

        if (!aktivan) {
            aktivan = true;
        } else {
            bActivateService.setDisable(true);
        }

        jObj = new JSONObject();
        jObj.put("action", "activate_service");
        jObj.put("service_id", servicesUser.getId());

        jObj = client.send_object(jObj);

        setData();

    }

    public void deleteUserService(ActionEvent actionEvent) {
        jObj = new JSONObject();
        JSONObject srvObj = new JSONObject();
        jObj.put("action", "delete_service_user");
        jObj.put("userID", userID);
        ServicesUser srvUser = tblServices.getSelectionModel().getSelectedItem().getValue();


        int i = 0;
        srvObj.put("id", srvUser.getId());
        srvObj.put("paketType", srvUser.getPaketType());
        jObj.put(String.valueOf(i), srvObj);

        for (TreeItem<ServicesUser> servicesUser : tblServices.getSelectionModel().getSelectedItem().getChildren()) {
            i++;
            srvObj = new JSONObject();
            srvObj.put("id", servicesUser.getValue().getId());
            srvObj.put("paketType", servicesUser.getValue().getPaketType());
            jObj.put(String.valueOf(i), srvObj);
        }


        Optional<ButtonType> btype = AlertUser.yesNo("BRISANJE USLUGE KORISNIKA", "Da li ste sigurni da želite da izbrišite uslugu" + tblServices.getSelectionModel().getSelectedItem().getValue().getNaziv());
        if (btype.isPresent() && btype.get() == AlertUser.NE) {
            return;
        }

        jObj = client.send_object(jObj);

        if (jObj.has("Error")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));
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
    }

    public void addFixUslugu(ActionEvent actionEvent) {
        jObj = new JSONObject();

        cmbFixPaket.getValue().getId();
        cmbFixPaket.getValue().getNaziv();

        jObj.put("id", cmbFixPaket.getValue().getId());
        jObj.put("nazivPaketa", cmbFixPaket.getValue().getNaziv());
        jObj.put("userID", userID);
        jObj.put("popust", Double.valueOf(tFixPopust.getText()));
        jObj.put("cena", Double.valueOf(cmbFixPaket.getValue().getPretplata()));
        jObj.put("obracun", chkFixStampaObracun.isSelected());
        jObj.put("brojUgovora", cmbFixBrojUgovora.getValue().getBr());
        jObj.put("brojTel", tFixBrojTel.getText());

        jObj.put("action", "addFixUslugu");
        jObj = client.send_object(jObj);

        if (jObj.getString("Message").equals("SERVICE_ADDED")) {
            AlertUser.info("Servis je dodan", String.format("Servis %s je dodan",
                    cmbFixPaket.getValue().getNaziv()));
            setData();
        } else {
            AlertUser.error("Greska", jObj.getString("Message"));
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
            fiksnaPaketi.setBesplatniMinutiFiksna(fiksnaObj.getDouble("besplatniMinutiFiksna"));
            fiksnaPaketi.setPdv(fiksnaObj.getDouble("PDV"));
            fiksnaPaketi.setPretplata(fiksnaObj.getDouble("pretplata"));
            paketArr.add(fiksnaPaketi);

        }
        return paketArr;
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
            iptvPaketiArrayList.add(iptvPaketi);
        }
        return iptvPaketiArrayList;
    }

    public void addServiceIPTV(ActionEvent actionEvent) {
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
        jObj.put("produzenje", Integer.valueOf(tIPTVPrekoracenje.getText()));
        jObj.put("brojUgovora", cmbUgovorIPTV.getValue().getBr());
        jObj.put("STB_MAC", tStbMACIPTV.getText().trim());
        if (cmbObracunIPTV.isSelected()) {
            jObj.put("obracun", true);
        } else {
            jObj.put("obracun", false);
        }

        jObj = client.send_object(jObj);

        if (jObj.has("Error")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));
        } else {
            AlertUser.info("Servics je dodan", String.format("Servis %s je dodan.",
                    cmbIPTVPaket.getValue().getName()));
            setData();
        }
    }
}
