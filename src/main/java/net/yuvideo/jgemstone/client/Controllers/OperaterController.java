package net.yuvideo.jgemstone.client.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.*;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zoom on 1/30/17.
 */
public class OperaterController implements Initializable {
    public TextField tAdresa;
    public TextField tTel;
    public TextArea tKomentar;
    public CheckBox chkAktivan;
    public PasswordField tPass;
    public PasswordField tPassC;

    public Client client;
    public TableView tblOperaters;
    public TableColumn cKorisnickoIme;
    public TableColumn cImeIPrezime;
    public TableColumn cAdresa;
    public TableColumn cTel;
    public TableColumn cAktivan;
    public TextField tImeIPrezime;
    public TextField tUsername;
    public CheckBox chkNov;
    public Button bClose;
    URL location;
    ResourceBundle resource;
    private Operaters operaters;
    private Operaters operEdit;
    private JSONObject jObj;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resource = resources;

        cKorisnickoIme.setCellValueFactory(new PropertyValueFactory<Operaters, String>("username"));
        cAdresa.setCellValueFactory(new PropertyValueFactory<Operaters, String>("adresa"));
        cAktivan.setCellValueFactory(new PropertyValueFactory<Operaters, Boolean>("aktivan"));
        cImeIPrezime.setCellValueFactory(new PropertyValueFactory<Operaters, String>("ime"));
        cTel.setCellValueFactory(new PropertyValueFactory<Operaters, String>("telefon"));


        tblOperaters.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (tblOperaters.getSelectionModel().getSelectedIndex() == -1)
                    return;

                operEdit = (Operaters) tblOperaters.getSelectionModel().getSelectedItem();
                tPass.setText("");
                tPassC.setText("");
                set_data();
            }
        });


    }

    private void set_data() {
        tUsername.setText(operEdit.getUsername());
        tImeIPrezime.setText(operEdit.getIme());
        tAdresa.setText(operEdit.getAdresa());
        tTel.setText(operEdit.getTelefon());
        chkAktivan.setSelected(operEdit.isAktivan());
        tKomentar.setText(operEdit.getKomentar());
    }

    public void snimiOper(ActionEvent actionEvent) {
        if (chkNov.isSelected()) {
            saveOperater();
        } else {
            updateOperater(operEdit.getId());
        }
    }

    private void saveOperater() {
        jObj = new JSONObject();
        jObj.put("action", "saveOperater");
        jObj.put("username", tUsername.getText());
        jObj.put("password", new md5_digiest(tPass.getText()).get_hash());
        jObj.put("adresa", tAdresa.getText());
        jObj.put("telefon", tTel.getText());
        jObj.put("komentar", tKomentar.getText());
        jObj.put("aktivan", chkAktivan.isSelected());
        jObj.put("ime", tImeIPrezime.getText());

        jObj = client.send_object(jObj);


        show_data();

    }

    private void updateOperater(int operID) {
        if (tblOperaters.getSelectionModel().getSelectedIndex() == -1) {
            NotifyUser.NotifyUser("Greška", "Nije izabran ni jedan operater", 2);
            return;
        }
        String pass = null;
        if (!tPass.getText().isEmpty()) {
            if (!tPass.getText().equals(tPassC.getText())) {
                NotifyUser.NotifyUser("Upozorenje", "Lozinke nisu identične", 2);
                return;
            } else {
                pass = new md5_digiest(tPass.getText()).get_hash();
            }
        }


        jObj = new JSONObject();
        jObj.put("action", "updateOperater");
        jObj.put("operaterID", operID);
        jObj.put("username", tUsername.getText());
        if (tPass != null)
            jObj.put("password", pass);
        jObj.put("adresa", tAdresa.getText());
        jObj.put("telefon", tTel.getText());
        jObj.put("komentar", tKomentar.getText());
        jObj.put("aktivan", chkAktivan.isSelected());
        jObj.put("ime", tImeIPrezime.getText());

        jObj = client.send_object(jObj);


        show_data();

    }

    public void show_data() {
        ObservableList opers = FXCollections.observableArrayList(getOperaterList());
        tblOperaters.setItems(opers);

    }


    private ArrayList<Operaters> getOperaterList() {
        jObj = new JSONObject();
        jObj.put("action", "getOperaters");
        jObj = client.send_object(jObj);

        ArrayList<Operaters> operatersArr = new ArrayList();
        JSONObject operaterObj;

        for (int i = 0; i < jObj.length(); i++) {
            operaters = new Operaters();
            operaterObj = (JSONObject) jObj.get(String.valueOf(i));
            operaters.setId(operaterObj.getInt("id"));
            operaters.setIme(operaterObj.getString("ime"));
            operaters.setUsername(operaterObj.getString("username"));
            operaters.setAktivan(operaterObj.getBoolean("aktivan"));
            operaters.setAdresa(operaterObj.getString("adresa"));
            operaters.setKomentar(operaterObj.getString("komentar"));
            operaters.setTelefon(operaterObj.getString("telefon"));
            operatersArr.add(operaters);

        }

        return operatersArr;
    }

    public void deleteOper(ActionEvent actionEvent) {
        deleteOperater(operEdit.getId());
    }


    public void deleteOperater(int operID) {
        jObj = new JSONObject();
        jObj.put("action", "deleteOper");
        jObj.put("operID", operID);

        jObj = client.send_object(jObj);


        show_data();
    }

    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) bClose.getScene().getWindow();
        stage.close();
    }

    public void showDozvoleOper(ActionEvent actionEvent) {
        NewInterface operaterDozvoleInterface = new NewInterface("fxml/OperaterDozvole.fxml", "Dozvole  Operatera", resource);
        OperaterDozvoleController operaterDozvoleController = operaterDozvoleInterface.getLoader().getController();
        operaterDozvoleController.client = this.client;
        operaterDozvoleController.operaterID = operEdit.getId();
        operaterDozvoleController.show_data();
        operaterDozvoleInterface.getStage().showAndWait();

    }
}
