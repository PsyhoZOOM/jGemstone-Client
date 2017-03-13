package JGemstone.interfaces;

import JGemstone.classes.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;
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
    public boolean appExit;
    ResourceBundle resource;
    URL location;
    OptionsController optionsController;
    private Logger LOGGER = Logger.getLogger("LOGIN_WIN_CONTROLLER");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;
        this.location = location;


    }


    public void clientLogin(ActionEvent actionEvent) {

        client = new Client();
        client.user_pass_manual(tUserName.getText(), tPass.getText());
        client.main_run();
        lMessage.setText(client.status_login);
        if (client.get_connection_state()) {
            tUserName.getScene().getWindow().hide();
        }
        LOGGER.info("CLIENT STATE: " + client.get_connection_state());
    }

    public void openOptions(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/JGemstone/resources/fxml/Options.fxml"), resource);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Podesavanja");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(bLogin.getScene().getWindow());
        stage.showAndWait();


    }
}
