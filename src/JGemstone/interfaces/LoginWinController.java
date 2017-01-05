package JGemstone.interfaces;

import JGemstone.classes.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 1/3/17.
 */
public class LoginWinController implements Initializable {
    public ImageView imgLogo;
    public JFXTextField tUserName;
    public JFXPasswordField tPass;
    public Label lMessage;
    public JFXButton bLogin;
    public Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        client = new Client();
        client.main_run();
    }


    public void clientLogin(ActionEvent actionEvent) {
        client.main_run();
    }
}
