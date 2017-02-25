package JGemstone.interfaces;

import JGemstone.classes.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    public TableView<ServicesUser> tblServices;
    public TableColumn cServicesNaziv;
    public TableColumn cServicesDatum;
    public TableColumn cServicesVrsta;
    public TableColumn cServicesBrojUgovora;
    public TableColumn cSservicesOperater;
    public TableColumn cServicePopust;
    public TableColumn cServiceCena;
    public TableColumn cServiceIdentification;


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
    public TableColumn cServiceAktivan;
    public TableColumn cServiceObracun;
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

        cServicesBrojUgovora.setCellValueFactory(new PropertyValueFactory<ServicesUser, Integer>("brUgovora"));
        cServicesDatum.setCellValueFactory(new PropertyValueFactory<ServicesUser, String>("datum"));
        cServicesNaziv.setCellValueFactory(new PropertyValueFactory<ServicesUser, String>("nazivPaketa"));
        cServicesVrsta.setCellValueFactory(new PropertyValueFactory<ServicesUser, String>("vrsta"));
        cServicePopust.setCellValueFactory(new PropertyValueFactory<ServicesUser, Double>("popust"));
        cServiceCena.setCellValueFactory(new PropertyValueFactory<ServicesUser, Double>("cena"));
        cSservicesOperater.setCellValueFactory(new PropertyValueFactory<ServicesUser, String>("operater"));
        cServiceAktivan.setCellValueFactory(new PropertyValueFactory<ServicesUser, String>("aktivan"));
        cServiceObracun.setCellValueFactory(new PropertyValueFactory<ServicesUser, String>("obracun"));
        cServiceIdentification.setCellValueFactory(new PropertyValueFactory<ServicesUser, String>("idUniqueName"));

        cServiceCena.setCellFactory(lv -> new TableCell<ServicesUser, Double>() {
            protected void updateItem(Double cena, boolean bool) {
                super.updateItem(cena, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(df.format(cena));
                }
            }
        });

        cServicePopust.setCellFactory(lv -> new TableCell<ServicesUser, Double>() {
            protected void updateItem(Double popust, boolean bool) {
                super.updateItem(popust, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(df.format(popust) + "%");
                }
            }
        });

        cServicesDatum.setCellFactory(cd -> new TableCell<ServicesUser, String>() {
            protected void updateItem(String date, boolean bool) {
                super.updateItem(date, bool);
                if (bool) {
                    setText(null);
                } else {
                    setText(date);
                }
            }
        });

        cServiceAktivan.setCellFactory(cak -> new TableCell<ServicesUser, Boolean>() {
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

        cServiceObracun.setCellFactory(cObr -> new TableCell<ServicesUser, Boolean>() {
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


        tblServices.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ServicesUser>() {
            @Override
            public void changed(ObservableValue<? extends ServicesUser> observable, ServicesUser oldValue, ServicesUser newValue) {
                if (tblServices.getSelectionModel().getSelectedIndex() != -1) {
                    if (tblServices.getSelectionModel().getSelectedItem().getAktivan()) {
                        bActivateService.setText("Deaktiviraj");
                        bActivateService.setDisable(true);
                    } else {
                        bActivateService.setText("Aktiviraj");
                        bActivateService.setDisable(false);
                    }
                }
            }
        });


    }

    public void setData() {
        ObservableList dataInternetPaket = FXCollections.observableArrayList(get_internet_paketi());
        cmbPaketInternet.getItems().clear();
        cmbPaketInternet.setItems(dataInternetPaket);


        ObservableList dataDTVPaket = FXCollections.observableArrayList(get_digitalTV_paketi());
        cmbPaketDTV.getItems().clear();
        cmbPaketDTV.setItems(dataDTVPaket);


        refreshUgovori();

        ObservableList serviceList = FXCollections.observableArrayList(get_services_user());
        tblServices.setItems(serviceList);

        ObservableList paketBox = FXCollections.observableArrayList(get_box_pakete());
        cmbPaketBOX.setItems(paketBox);


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
            service.setVrsta(serviceObj.getString("vrsta"));
            service.setAktivan(serviceObj.getBoolean("aktivan"));
            service.setObracun(serviceObj.getBoolean("obracun"));
            service.setIdUniqueName(serviceObj.getString("idUniqueName"));
            service.setProduzenje(serviceObj.getInt("produzenje"));
            service.setId_Service(serviceObj.getInt("id_service"));
            service.setNazivPaketa(serviceObj.getString("nazivPaketa"));
            service.setCena(serviceObj.getDouble("cena"));

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
            if (boxObj.has("DTV_id")) {
                box.setDTV(boxObj.getInt("DTV_id"));
                box.setDTV_naziv(boxObj.getString("DTV_naziv"));
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
        jObj.put("action", "add_service_to_user");

        jObj.put("id", cmbPaketInternet.getValue().getId());
        jObj.put("nazivPaketa", cmbPaketInternet.getValue().getNaziv());
        jObj.put("userID", userID);
        jObj.put("servicePopust", Double.valueOf(tPopustInternet.getText()));
        jObj.put("paketType", "INTERNET");
        jObj.put("cena", Double.valueOf(cmbPaketInternet.getValue().getCena()));
        jObj.put("obracun", chkRacunInternet.isSelected());
        jObj.put("brojUgovora", cmbUgovorInternet.getValue().getBr());
        jObj.put("idUniqueName", tUserNameInternet.getText());
        jObj.put("password", new md5_digiest(tLozinkaCInternet.getText()).get_hash());
        jObj.put("produzenje", cmbPaketInternet.getValue().getPrekoracenje());


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
        jObj.put("action", "add_service_to_user");

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
        jObj.put("action", "add_service_to_user");

        jObj.put("id", cmbPaketBOX.getValue().getId());
        jObj.put("nazivPaketa", cmbPaketBOX.getValue().getNaziv());
        jObj.put("userID", userID);
        jObj.put("servicePopust", Double.valueOf(tPopustBox.getText()));
        jObj.put("paketType", "BOX");
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

        boolean aktivan;
        aktivan = tblServices.getSelectionModel().getSelectedItem().getAktivan();
        if (!aktivan) {
            aktivan = true;
        } else {
            bActivateService.setDisable(true);
        }
        ServicesUser servicesUser = tblServices.getSelectionModel().getSelectedItem();


        //izracunavanje cene usluge do kraja meseca od trenutnog datuma
        //cena / dana u meseceu = cena po danu
        //cena po danu * preostalo dana do kraja meseca

        double cena = servicesUser.getCena();
        Calendar cal = Calendar.getInstance();
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int current_day = cal.get(Calendar.DAY_OF_MONTH);
        int est_day = days - current_day;

        cena = cena / days;
        cena = cena * est_day;


        jObj = new JSONObject();
        jObj.put("action", "activate_service");
        jObj.put("id", tblServices.getSelectionModel().getSelectedItem().getId());
        jObj.put("aktivan", aktivan);
        jObj.put("idUniqueName", tblServices.getSelectionModel().getSelectedItem().getIdUniqueName());
        jObj.put("paketType", tblServices.getSelectionModel().getSelectedItem().getVrsta());
        jObj.put("produzenje", servicesUser.getProduzenje());
        jObj.put("userID", userID);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jObj.put("endDate", sdf.format(firstDateInMonth.getTime()));

        jObj.put("id_ServiceUser", servicesUser.getId());
        jObj.put("idService", servicesUser.getId_Service());
        jObj.put("nazivPaketa", servicesUser.getNazivPaketa());
        jObj.put("popust", servicesUser.getPopust());
        jObj.put("cena", servicesUser.getCena());
        jObj.put("dug", cena);

        jObj = client.send_object(jObj);

        String aktivate = jObj.getString("activate");

        if (jObj.getString("Message").equals("ERROR")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));
        } else {
            AlertUser.info("Usluga ", "Usluga " + jObj.getString("activate"));
        }

        setData();

    }

    public void deleteUserService(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "delete_service_user");
        jObj.put("userID", userID);
        jObj.put("serviceId", tblServices.getSelectionModel().getSelectedItem().getId());
        jObj.put("paketType", tblServices.getSelectionModel().getSelectedItem().getVrsta());
        jObj.put("idUniqueName", tblServices.getSelectionModel().getSelectedItem().getIdUniqueName());

        Optional<ButtonType> btype = AlertUser.yesNo("BRISANJE KORISNIKA", "Da li ste sigurni da želite da izbrišite uslugu" + tblServices.getSelectionModel().getSelectedItem().getNaziv());
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
