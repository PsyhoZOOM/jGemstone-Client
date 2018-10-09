package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXDecorator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.BytesTo_KB_MB_GB_TB;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Settings;
import net.yuvideo.jgemstone.client.classes.db_connection;

/**
 * Created by zoom on 1/3/17.
 */
public class LoginWinController implements Initializable {

  public ImageView imgLogo;
  public TextField tUserName;
  public PasswordField tPass;
  public Label lMessage;
  public Button bLogin;
  public Stage stage;
  ResourceBundle resource;
  URL location;
  FXMLLoader fxmlLoader;
  Parent rootMainWindow;
  public Scene scene;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.resource = resources;
    this.location = location;


  }


  public void clientLogin(ActionEvent actionEvent) {
    db_connection db = new db_connection();
    db.get_settings();
    Settings LocalSettings = db.getLocal_settings();
    LocalSettings.setLocalUser(tUserName.getText());
    LocalSettings.setLocalPassword(tPass.getText());
    db.setLocal_settings(LocalSettings);
    db.set_settings();
    db.get_settings();

    System.out.println(db.getLocal_settings().getLocalPassword());

    Client client = new Client(db.getLocal_settings(), false);
    client.main_run();
    client.login_to_server();
    lMessage.setText(client.status_login);
    if (client.isConnected()) {
      fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("fxml/MainWindow.fxml"), resource);
      try {
        rootMainWindow = fxmlLoader.load();
        MainWindowController mainCtrl = fxmlLoader.getController();
        mainCtrl.setClient(client);
        client.result.addListener(new ChangeListener<Number>() {

          @Override
          public void changed(ObservableValue<? extends Number> observable, Number oldValue,
              Number newValue) {
            Platform.runLater(new Runnable() {
              @Override
              public void run() {
                String ping = String.valueOf(client.result.getValue());
                String rec = String.valueOf(client.rs.getValue());
                String send = String.valueOf(client.ss.getValue());
                mainCtrl.lStatusConnection
                    .setText(String.format("Ping: %sms. | Rec: %sb / Send: %sb ", ping, rec, send));
                if (client.canSend) {
                  mainCtrl.iStatusServer.setImage(mainCtrl.imgGreen);
                } else {
                  mainCtrl.iStatusServer.setImage(mainCtrl.imgRed);
                }
              }
            });

          }
        });
        mainCtrl.setStage(stage);
        mainCtrl.LocalSettings = client.getLocal_settings();
        scene.setRoot(rootMainWindow);
        stage.setScene(scene);
        stage.setTitle("JGemstone");
        stage.setMaximized(true);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void openOptions(ActionEvent actionEvent) {
    FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("fxml/Options.fxml"),
        resource);

    Scene scene = null;
    try {
      scene = new Scene(fxmlLoader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
    Stage stageOptions = new Stage();

    stageOptions.setScene(scene);
    stageOptions.setTitle("Podesavanja");
    stageOptions.initOwner(bLogin.getScene().getWindow());
    stageOptions.initModality(Modality.APPLICATION_MODAL);
    stageOptions.showAndWait();


  }
}
