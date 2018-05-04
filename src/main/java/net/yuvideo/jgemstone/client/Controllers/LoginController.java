package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Created by zoom on 8/8/16.
 */
public class LoginController implements Initializable {

  public Button bLogin;
  public Label lPoruk;
  public TextField tfUser;
  public PasswordField tfPass;
  public boolean login_ok = false;
  ResourceBundle bundle;


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.bundle = resources;

    bLogin.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {

      }
    });

  }


}
