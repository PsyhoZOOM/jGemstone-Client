package net.yuvideo.jgemstone.client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.db_connection;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by zoom on 8/16/16.
 */
public class OptionsController implements Initializable {
    public Stage stage;
    @FXML
    private TextField tHostnameIp;
    @FXML
    private TextField tPort;
    @FXML
    private Button bSnimi;
    private db_connection db_conn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db_conn = new db_connection();
        db_conn.init_database();
        tHostnameIp.setText(db_conn.local_settings.getREMOTE_HOST());
        tPort.setText(String.valueOf(db_conn.local_settings.getREMOTE_PORT()));
        db_conn.close_db();
    }

    public void bSnimi_options(ActionEvent actionEvent) {

        db_conn.init_database();
        if (!tPort.getText().isEmpty()) db_conn.local_settings.setREMOTE_PORT(Integer.parseInt(tPort.getText()));
        if (!tHostnameIp.getText().isEmpty()) db_conn.local_settings.setREMOTE_HOST(tHostnameIp.getText());
        try {
            db_conn.set_settings();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db_conn.close_db();

        AlertUser.info("IZMENE SNIMLJENE", "Izmene uspešno snimljene ;)");
        Stage stage = (Stage) bSnimi.getScene().getWindow();
        stage.close();


    }

}
