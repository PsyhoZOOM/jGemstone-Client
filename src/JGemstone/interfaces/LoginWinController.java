package JGemstone.interfaces;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
    public JFXTextField tPass;
    public Label lMessage;
    public JFXButton bLogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
