package net.yuvideo.jgemstone.client.Controllers;

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
import net.yuvideo.jgemstone.client.classes.Client;

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
    ResourceBundle resource;
    URL location;
    public Stage stage;
    FXMLLoader fxmlLoader;
    Parent rootMainWindow;

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
            //           tUserName.getScene().getWindow().hide();
            fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("fxml/MainWindow.fxml"), resource);
            try {
                rootMainWindow = fxmlLoader.load();
                MainWindowController mainCtrl = fxmlLoader.getController();
                mainCtrl.client = client;
                Scene scene;
                scene = new Scene(rootMainWindow);
                stage.setScene(scene);
                stage.setResizable(true);
                stage.setTitle("YUVIDEO");
                stage.setMaximized(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openOptions(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("fxml/Options.fxml"), resource);

        Scene scene = null;
        try {
            scene = new Scene((Parent) fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        OptionsController optionsController = fxmlLoader.<OptionsController>getController();
        Stage stageOptions = new Stage();

        stageOptions.setScene(scene);
        stageOptions.setTitle("Podesavanja");
        stageOptions.initOwner(bLogin.getScene().getWindow());
        stageOptions.initModality(Modality.APPLICATION_MODAL);
        stageOptions.showAndWait();


    }
}