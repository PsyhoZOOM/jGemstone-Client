package net.yuvideo.jgemstone.client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.db_connection;
import net.yuvideo.jgemstone.client.classes.md5_digiest;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by zoom on 8/16/16.
 */
public class OptionsController implements Initializable {
    public CheckBox cSavePass;
    public Stage stage;
    @FXML
    private TextField tHostnameIp;
    @FXML
    private TextField tPort;
    @FXML
    private TextField tLocalUsername;
    @FXML
    private PasswordField tLocalPassword;
    @FXML
    private Button bCancel;
    @FXML
    private Button bSnimi;
    private db_connection db_conn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        db_conn = new db_connection();
        db_conn.init_database();
        tHostnameIp.setText(db_conn.local_settings.getREMOTE_HOST());
        tPort.setText(String.valueOf(db_conn.local_settings.getREMOTE_PORT()));
        tLocalUsername.setText(db_conn.local_settings.getLocalUser());
        db_conn.close_db();


    }

    public void bSnimi_options(ActionEvent actionEvent) {
        //pass to MD5 HASH
        String pass;
        md5_digiest md5 = new md5_digiest(tLocalPassword.getText());
        pass = md5.get_hash();

        db_conn.init_database();
        if (!tPort.getText().isEmpty()) db_conn.local_settings.setREMOTE_PORT(Integer.parseInt(tPort.getText()));
        if (!tHostnameIp.getText().isEmpty()) db_conn.local_settings.setREMOTE_HOST(tHostnameIp.getText());
        if (!tLocalUsername.getText().isEmpty()) db_conn.local_settings.setLocalUser(tLocalUsername.getText());
        if (!tLocalPassword.getText().isEmpty()) db_conn.local_settings.setLocalPassword(pass);
        try {
            db_conn.set_settings();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        db_conn.close_db();


    }

    public void bCancel_close(ActionEvent actionEvent) throws SQLException {

        Stage stage = (Stage) bCancel.getScene().getWindow();
        stage.close();


    }
}
