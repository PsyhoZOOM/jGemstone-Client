package JGemstone.interfaces;

import JGemstone.classes.db_connection;
import JGemstone.classes.md5_digiest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by zoom on 8/16/16.
 */
public class OptionsController implements Initializable {
    public CheckBox cSavePass;
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



    }

    public void bSnimi_options(ActionEvent actionEvent) {
        //pass to MD5 HASH
        String pass;
        md5_digiest md5 = new md5_digiest(tLocalPassword.getText());
        pass = md5.get_hash();

        if (!tPort.getText().isEmpty()) db_conn.local_settings.setREMOTE_PORT(Integer.parseInt(tPort.getText()));
        if (!tHostnameIp.getText().isEmpty()) db_conn.local_settings.setREMOTE_HOST(tHostnameIp.getText());
        if(!tLocalUsername.getText().isEmpty()) db_conn.local_settings.setLocalUser(tLocalUsername.getText());
        if (!tLocalPassword.getText().isEmpty()) db_conn.local_settings.setLocalPassword(pass);
        try {
            db_conn.set_settings();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void bCancel_close(ActionEvent actionEvent) throws SQLException {
        db_conn.close_db();
        Stage stage = (Stage) bCancel.getScene().getWindow();
        stage.close();
    }
}
