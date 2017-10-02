package net.yuvideo.jgemstone.client.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import net.yuvideo.jgemstone.client.classes.*;
import org.controlsfx.control.textfield.TextFields;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;


/**
 * Created by zoom on 3/20/17.
 */
public class UplateMain implements Initializable {
    public DatePicker dtpDatumUplate;
    public ComboBox<Users> cmbUserSearch;
    public TextField tUplaceno;
    public TextField tNapomena;
    public Button bUplati;
    public TableView<Uplate> tblUplate;
    public TableColumn cDatumUplate;
    public TableColumn cUser;
    public TableColumn cUplaceno;
    public TableColumn cNapomena;
    public TableColumn cMestoUplate;
    public ComboBox<UplateMesta> cmbMestoUplate;

    public Client client;
    public Label lUkupanDug;
    public Label lUkupnoUplaceno;
    public Label lZaUplatu;
    public Label lStatus;
    public AnchorPane anchor;
    Calendar cal = Calendar.getInstance();
    DateTimeFormatter formatterBack = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DecimalFormat df = new DecimalFormat("#,##0.00");
    private URL location;
    private ResourceBundle resources;
    private JSONObject jObj;
    private Users userEDIT;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        dtpDatumUplate.setValue(LocalDate.now());

        cDatumUplate.setCellValueFactory(new PropertyValueFactory<Uplate, String>("datumUplate"));
        cUser.setCellValueFactory(new PropertyValueFactory<Uplate, String>("operater"));
        cUplaceno.setCellValueFactory(new PropertyValueFactory<Uplate, Double>("uplaceno"));
        cNapomena.setCellValueFactory(new PropertyValueFactory<Uplate, String>("napomena"));
        cMestoUplate.setCellValueFactory(new PropertyValueFactory<Uplate, String>("mestoUplate"));

        cUplaceno.setCellFactory(tc -> new TableCell<Uplate, Double>() {
            protected void updateItem(Double uplaceno, boolean bool) {
                super.updateItem(uplaceno, bool);
                if (bool) {
                    setText("");
                } else {
                    setText(df.format(uplaceno));
                }
            }
        });


        tUplaceno.setText("0.00");
        tUplaceno.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Double.parseDouble(tUplaceno.getText());
            } catch (Exception e) {
                AlertUser.error("POGRESAN UNOS", tUplaceno.getText() + " Nije validan");
                tUplaceno.setText(oldValue);
            }
        });

        cmbUserSearch.selectionModelProperty().addListener(new ChangeListener<SingleSelectionModel<Users>>() {
            @Override
            public void changed(ObservableValue<? extends SingleSelectionModel<Users>> observable, SingleSelectionModel<Users> oldValue, SingleSelectionModel<Users> newValue) {
                showUplateKorisnika(newValue.getSelectedItem());
            }
        });

        cmbUserSearch.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.ENTER){
                String id = cmbUserSearch.getEditor().getText();
                for (Users user : cmbUserSearch.getItems()){
                    if (id.equals(user.getjMesto() + user.getjAdresa() + user.getId() + ", " + user.getIme())) {
                        cmbUserSearch.getSelectionModel().select(user);
                        cmbUserSearch.getEditor().commitValue();
                        userEDIT=user;
                        showUplateKorisnika(user);
                        lStatus.setText("STATUS " + user.getIme() + ", " + user.getjMesto() + user.getjAdresa() + user.getId());
                    }
                }
            }
        });

        cmbUserSearch.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(cmbUserSearch.getEditor().isFocused()  || cmbUserSearch.isFocused() && cmbUserSearch.getEditor().getText().isEmpty()){
                cmbUserSearch.getEditor().selectAll();
            }
        });




    }


    private void showUplateKorisnika(Users user) {

        ObservableList userUplate = FXCollections.observableArrayList(get_uplate_user(user.getId()));
        tblUplate.setItems(userUplate);
        ShowUserZaduzenja(userEDIT);
    }


    public void showData() {
        ObservableList<Users> users = FXCollections.observableArrayList(get_users());
        cmbUserSearch.setEditable(true);
        cmbUserSearch.setItems(users);

        TextFields.bindAutoCompletion(cmbUserSearch.getEditor(), cmbUserSearch.getItems());

        ObservableList mestoUplate = FXCollections.observableArrayList(getMestaUplate());
        cmbMestoUplate.setItems(mestoUplate);


    }

    private ArrayList<Uplate> get_uplate_user(int userID) {
        jObj = new JSONObject();
        jObj.put("action", "get_uplate_user");
        jObj.put("userID", userID);

        jObj = client.send_object(jObj);

        ArrayList<Uplate> uplate = new ArrayList();
        JSONObject uplateObj;
        Uplate uplata;

        for (int i = 0; i < jObj.length(); i++) {
            uplateObj = (JSONObject) jObj.get(String.valueOf(i));
            uplata = new Uplate();
            uplata.setId(uplateObj.getInt("id"));
            uplata.setUplaceno(uplateObj.getDouble("uplaceno"));
            uplata.setDatumUplate(uplateObj.getString("datumUplate"));
            uplata.setMestoUplate(uplateObj.getString("mesto"));
            uplata.setOperater(uplateObj.getString("operater"));
            uplata.setNapomena(uplateObj.getString("napomena"));
            uplate.add(uplata);
        }

        return uplate;
    }

    private ArrayList<Users> get_users() {
        jObj = new JSONObject();
        Users users;
        JSONObject usersObj;


        jObj.put("action", "get_users");
        jObj = client.send_object(jObj);

        ArrayList<Users> usersData = new ArrayList();


        for (int i = 0; i < jObj.length(); i++) {
            usersObj = (JSONObject) jObj.get(String.valueOf(i));
            users = new Users();
            users.setId(usersObj.getInt("id"));
            users.setIme(usersObj.getString("fullName"));
            users.setMesto(usersObj.getString("mesto"));
            users.setAdresa(usersObj.getString("adresa"));
            users.setAdresa_usluge(usersObj.getString("adresaUsluge"));
            users.setMesto_usluge(usersObj.getString("mestoUsluge"));
            users.setBr_lk(usersObj.getString("brLk"));
            users.setDatum_rodjenja(usersObj.getString("datumRodjenja"));
            users.setFiksni(usersObj.getString("telFixni"));
            users.setMobilni(usersObj.getString("telMobilni"));
            users.setJMBG(usersObj.getString("JMBG"));
            users.setKomentar(usersObj.getString("komentar"));
            users.setPostanski_broj(usersObj.getString("postBr"));
            users.setjMesto(usersObj.getString("jMesto"));
            users.setjAdresa(usersObj.getString("jAdresa"));
            usersData.add(users);

        }
        return usersData;
    }

    private void ShowUserZaduzenja(Users user) {
        jObj = new JSONObject();
        jObj.put("action", "get_zaduzenja_user");
        jObj.put("userID", user.getId());
        jObj.put("sveUplate", true);
        jObj = client.send_object(jObj);

        Double dug = 0.00;
        Double uplaceno = 0.00;
        Double zaUplatu = 0.00;

        JSONObject zaduzenje;
        JSONObject uplacenoObj;

        for (int i = 0; i < jObj.length(); i++) {
            zaduzenje = (JSONObject) jObj.get(String.valueOf(i));
            dug = dug + zaduzenje.getDouble("dug");

        }


        jObj = new JSONObject();
        jObj.put("action", "get_uplate_user");
        jObj.put("userID", user.getId());

        jObj = client.send_object(jObj);


        for (int i = 0; i < jObj.length(); i++) {
            uplacenoObj = (JSONObject) jObj.get(String.valueOf(i));
            uplaceno = uplaceno + uplacenoObj.getDouble("uplaceno");
        }


        lUkupanDug.setText(df.format(dug));
        lUkupnoUplaceno.setText(df.format(uplaceno));
        zaUplatu = dug - uplaceno;
        lZaUplatu.setText(df.format(zaUplatu));


    }

    private ArrayList<UplateMesta> getMestaUplate() {
        ArrayList<UplateMesta> mestaUplate = new ArrayList();


        UplateMesta uplate = new UplateMesta();
        uplate.setMestoUplate("Keš");
        uplate.setMesto(0);
        mestaUplate.add(uplate);

        uplate = new UplateMesta();
        uplate.setMestoUplate("Faktura");
        uplate.setMesto(1);
        mestaUplate.add(uplate);

        uplate = new UplateMesta();
        uplate.setMestoUplate("Pošta");
        uplate.setMesto(2);
        mestaUplate.add(uplate);

        uplate = new UplateMesta();
        uplate.setMestoUplate("Čekovi");
        uplate.setMesto(3);
        mestaUplate.add(uplate);

        return mestaUplate;

    }


    public void uplati(ActionEvent actionEvent) {
        if (dtpDatumUplate.getValue() == null || tUplaceno.getText().isEmpty() || cmbMestoUplate.getValue() == null) {
            AlertUser.error("GREŠKA", "Polja datum uplate, uplaćeno i mesto uplate ne mogu biti prazna");
            return;
        }


        jObj = new JSONObject();
        jObj.put("action", "new_uplata");
        jObj.put("userID", userEDIT.getId());
        jObj.put("datumUplate", dtpDatumUplate.getValue().format(formatterBack));
        jObj.put("uplaceno", Double.valueOf(tUplaceno.getText()));
        jObj.put("mesto", cmbMestoUplate.getValue().getMestoUplate());
        jObj.put("napomena", tNapomena.getText());
        jObj = client.send_object(jObj);

        if (jObj.has("Error")) {
            AlertUser.error("GRESKA", jObj.getString("Error"));
        } else {
            AlertUser.info("UPLATA", "UPLATA JE IZVRŠENA");
        }

        showUplateKorisnika(userEDIT);
        tUplaceno.setText("0");

    }

}
