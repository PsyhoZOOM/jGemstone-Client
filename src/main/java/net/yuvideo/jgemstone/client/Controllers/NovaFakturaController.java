package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Users;

/**
 * Created by zoom on 11/28/16.
 */
public class NovaFakturaController implements Initializable {
    public JFXButton bClose;
    public JFXButton bAddFaktura;
    public JFXTextField NazivMesec;
    public JFXTextField jedMere;
    public JFXTextField kolicina;
    public JFXTextField jedinacnaCena;
    public JFXTextField stopaPDV;


    public Users user;
    public String brFakture;
    public String godina;
    ResourceBundle resource;
    URL location;
    Client client;
    private Stage stage;
    private JSONObject jObj;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;
        this.location = location;

        bClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage = (Stage) bClose.getScene().getWindow();
                stage.close();
            }
        });
    }


    @FXML
    private void snimiFakturu() {
        //Proveri polja za brojeve i prazna polja

        jObj = new JSONObject();
        jObj.put("action", "snimiFakturu");
        jObj.put("nazivMesec", NazivMesec.getText());
        jObj.put("jedMere", jedMere.getText());
        jObj.put("kolicina", kolicina.getText());
        jObj.put("jedinacnaCena", jedinacnaCena.getText());
        jObj.put("stopaPDV", stopaPDV.getText());
        jObj.put("godina", godina);
        jObj.put("userId", user.getId());
        jObj.put("brFakture", brFakture);

        jObj = client.send_object(jObj);

        stage = (Stage) bAddFaktura.getScene().getWindow();
        stage.close();


    }


}
