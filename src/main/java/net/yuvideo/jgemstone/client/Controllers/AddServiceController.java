package net.yuvideo.jgemstone.client.Controllers;

import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Services;
import net.yuvideo.jgemstone.client.classes.valueToPercent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Created by zoom on 1/20/17.
 */
public class AddServiceController implements Initializable {
    public Button bZatvori;
    public Button bSnimi;
    public Label lCenaBezPopusta;
    public Label lServiceName;
    public Spinner<Double> cmbPopust;
    public Label lCenaSaPopustom;
    public Services service;
    public Client client;
    public Label lPopustDin;
    public int userID;
    JSONObject jObj;
    private Logger LOGGER = Logger.getLogger("ADD_SERVICE");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        bZatvori.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) bZatvori.getScene().getWindow();
                stage.close();
            }
        });

        SpinnerValueFactory spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.00, 100.0);
        spinnerValueFactory.setConverter(new StringConverter() {
            DecimalFormat df = new DecimalFormat("###,##0.00");


            @Override
            public String toString(Object object) {
                return df.format(object);
            }

            @Override
            public Object fromString(String string) {
                try {
                    if (string == null) {
                        return null;
                    }
                    string = string.trim();

                    if (string.length() < 1) {
                        return null;
                    }

                    return df.parse(string).doubleValue();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }

            }

        });

        cmbPopust.setValueFactory(spinnerValueFactory);


        cmbPopust.valueProperty().addListener((obs, oldValue, newValue) -> {

                    lCenaSaPopustom.setText(String.valueOf(valueToPercent.getValue(Double.valueOf(lCenaBezPopusta.getText()), cmbPopust.getValue())));
                    lPopustDin.setText(String.valueOf((Double.valueOf(lCenaBezPopusta.getText()) - Double.valueOf(lCenaSaPopustom.getText()))));
                }
        );

        cmbPopust.getEditor().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                cmbPopust.valueProperty().notify();
            }
        });


    }

    private void count_iznost() {

    }


    public void set_items() {
        lServiceName.setText(service.getNaziv());
        lCenaBezPopusta.setText(String.valueOf(service.getCena()));
        lCenaSaPopustom.setText(String.valueOf(service.getCena()));
    }


    public void bSnimiAddService(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "add_service_to_user");
        jObj.put("id", service.getId());
        jObj.put("userID", userID);
        jObj.put("servicePopust", Double.valueOf(cmbPopust.getEditor().getText()));

        jObj = client.send_object(jObj);
        LOGGER.info(jObj.getString("message"));


    }

    public void cmbPopustFinished(KeyEvent keyEvent) {

    }

    public void tPopustReleased(KeyEvent keyEvent) {
    }
}
