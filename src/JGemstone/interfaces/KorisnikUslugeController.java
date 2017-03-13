package JGemstone.interfaces;

import JGemstone.classes.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
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
    public Client client;
    public int userID;
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


    }

    public void setData() {
        //default fields
        tPopustBox.setText("0.00");
        tPopustDTV.setText("0.00");
        tPopustInternet.setText("0.00");
        tProduzenjeBox.setText("2");


        ObservableList dataInternetPaket = FXCollections.observableArrayList(get_internet_paketi());
        cmbPaketInternet.getItems().clear();
        cmbPaketInternet.setItems(dataInternetPaket);


        ObservableList dataDTVPaket = FXCollections.observableArrayList(get_digitalTV_paketi());
        cmbPaketDTV.getItems().clear();
        cmbPaketDTV.setItems(dataDTVPaket);


        refreshUgovori();

        ObservableList serviceList = FXCollections.observableArrayList(get_services_user());
        // set table datatblServices.setItems(serviceList);
        setTableServicesData(serviceList);

        ObservableList paketBox = FXCollections.observableArrayList(get_box_pakete());
        cmbPaketBOX.setItems(paketBox);


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
            service.setAktivan(serviceObj.getBoolean("aktivan"));
            service.setPaketType(serviceObj.getString("paketType"));
            service.setNewService(serviceObj.getBoolean("newService"));

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


            if (serviceObj.has("MAC_IPTV")) {
                service.setMAC_IPTV(serviceObj.getString("MAC_IPTV"));
                service.setIdUniqueName(serviceObj.getString("MAC_IPTV"));
            }

            if (serviceObj.has("FIKSNA_TEL")) {
                service.setFIKSNA_TEL(serviceObj.getString("FIKSNA_TEL"));
                service.setIdUniqueName(serviceObj.getString("FIKNS_TEL"));
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
            service.setBrUgovora(serviceObj.getInt("brojUgovora"));
            service.setCena(serviceObj.getDouble("cena"));
            service.setPopust(serviceObj.getDouble("popust"));
            service.setOperater(serviceObj.getString("operName"));
            service.setDatum(serviceObj.getString("date_added"));
            service.setAktivan(serviceObj.getBoolean("aktivan"));
            service.setObracun(serviceObj.getBoolean("obracun"));
            service.setProduzenje(serviceObj.getInt("produzenje"));
            service.setId_Service(serviceObj.getInt("id_service"));
            service.setNewService(serviceObj.getBoolean("newService"));

            if (serviceObj.has("box_ID"))
                service.setBox_id(serviceObj.getInt("box_ID"));

            service.setNazivPaketa(serviceObj.getString("nazivPaketa"));

            if (serviceObj.has("idUniqueName"))
                service.setIdUniqueName(serviceObj.getString("idUniqueName"));

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
            if (boxObj.has("TEL_id")) {
                box.setFIKSNA(boxObj.getInt("TEL_id"));
                box.setFIKSNA_naziv(boxObj.getString("TEL_naziv"));
            }
            if (boxObj.has("IPTV_id")) {
                box.setIPTV(boxObj.getInt("IPTV_id"));
                box.setIPTV_naziv(boxObj.getString("IPTV_naziv"));
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
            iPaketi.setPrekoracenje(iPaketiObj.getInt("prekoracenje"));
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
        jObj.put("produzenje", cmbPaketInternet.getValue().getPrekoracenje());
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
            dtvPaketi.setProduzenje(dtvPaketiObj.getInt("prekoracenje"));
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
        jObj.put("produzenje", cmbPaketDTV.getValue().getProduzenje());
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
        jObj.put("userName", tUserNameBox.getText());
        jObj.put("passWord", tPasswordBox.getText());
        jObj.put("groupName", cmbPaketBOX.getValue().getNET_naziv());
        jObj.put("MAC_IPTV", tMACIPTVBox.getText());
        jObj.put("DTVKartica", Integer.valueOf(tKarticaBox.getText()));
        jObj.put("DTVPaket", cmbPaketBOX.getValue().getDTVPaketID());


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


        //izracunavanje cene usluge do kraja meseca od trenutnog datuma
        //cena / dana u mesecu = cena po danu
        //cena po danu * preostalo dana do kraja meseca

        double cena = servicesUser.getCena();
        Calendar cal = Calendar.getInstance();
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int current_day = cal.get(Calendar.DAY_OF_MONTH);
        int est_day = days - current_day;

        cena = cena / days;
        cena = cena * est_day;


        jObj = new JSONObject();
        //ako je servis nov dodacemo cenu samo do kraja meseca
        //ako nije dodacemo punu cenu

        if (servicesUser.getPaketType().equals("BOX")) {
            jObj.put("action", "activate_service");
            jObj.put("actionService", "activate_BOX_service");
            jObj.put("id", servicesUser.getId());
            jObj.put("boxID", servicesUser.getBox_id());
            jObj.put("userID", userID);
            jObj.put("newService", servicesUser.getNewService());
            jObj.put("cena", servicesUser.getCena());
            jObj.put("popust", servicesUser.getPopust());
            jObj.put("produzenje", servicesUser.getProduzenje());
            jObj.put("paketType", servicesUser.getPaketType());
            jObj.put("nazivPaketa", servicesUser.getNazivPaketa());
            jObj = client.send_object(jObj);
        }

        if (servicesUser.getPaketType().equals("DTV")) {
            jObj.put("action", "activate_service");
            jObj.put("actionService", "activate_DTV_service");
            jObj.put("id", servicesUser.getId());
            jObj.put("serviceID", servicesUser.getId_ServiceUser());
            jObj.put("idKartica", servicesUser.getIdUniqueName());
            jObj.put("userID", userID);
            jObj.put("newService", servicesUser.getNewService());
            jObj.put("cena", servicesUser.getCena());
            jObj.put("popust", servicesUser.getPopust());
            jObj.put("produzenje", servicesUser.getProduzenje());
            jObj.put("paketType", servicesUser.getPaketType());
            jObj.put("nazivPaketa", servicesUser.getNazivPaketa());
            jObj = client.send_object(jObj);
        }

        if (servicesUser.getPaketType().equals("NET")) {
            jObj.put("action", "activate_service");
            jObj.put("actionService", "activate_NET_service");
            jObj.put("id", servicesUser.getId());
            jObj.put("serviceID", servicesUser.getId_ServiceUser());
            jObj.put("userID", userID);
            jObj.put("newService", servicesUser.getNewService());
            jObj.put("cena", servicesUser.getCena());
            jObj.put("popust", servicesUser.getPopust());
            jObj.put("produzenje", servicesUser.getProduzenje());
            jObj.put("userName", servicesUser.getIdUniqueName());
            jObj.put("paketType", servicesUser.getPaketType());
            jObj.put("nazivPaketa", servicesUser.getNazivPaketa());
            jObj = client.send_object(jObj);
        }


        setData();

    }

    public void deleteUserService(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "delete_service_user");
        jObj.put("userID", userID);
        jObj.put("serviceId", tblServices.getSelectionModel().getSelectedItem().getValue().getId());
        jObj.put("paketType", tblServices.getSelectionModel().getSelectedItem().getValue().getVrsta());
        jObj.put("idUniqueName", tblServices.getSelectionModel().getSelectedItem().getValue().getIdUniqueName());

        Optional<ButtonType> btype = AlertUser.yesNo("BRISANJE KORISNIKA", "Da li ste sigurni da želite da izbrišite uslugu" + tblServices.getSelectionModel().getSelectedItem().getValue().getNaziv());
        if (btype.isPresent() && btype.get() == AlertUser.NE) {
            return;
        }

        jObj = client.send_object(jObj);

        if (jObj.has("Error")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));
        } else {
            AlertUser.info("SERVIS", "Usluuga izbrisana");
            ;
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
    }

}
