package net.yuvideo.jgemstone.client;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.yuvideo.jgemstone.client.Controllers.LoginWinController;
import net.yuvideo.jgemstone.client.classes.EncodingControl;

public class ClientMain extends Application {

  public static ResourceBundle bundle;
  LoginWinController loginCtrl;
  Parent rootMainWindow;
  FXMLLoader fxmlLoader;
  Stage stage;
  Scene scene;

  public static void main(String[] args) {
    launch(args);
  }

  public void init() throws Exception {
    super.init();
  }

  @Override
  public void start(final Stage primaryStage) {

    // locale_sr = "RS";
    // locale = new Locale(locale_sr)
    // System.setProperty("javafx.userAgentStylesheetUrl", STYLESHEET_CASPIAN);
    Locale locale = new Locale("sr");


    bundle = ResourceBundle.getBundle("lang", new Locale("sr"), new EncodingControl("utf-8"));

    Locale.setDefault(new Locale("en"));
    System.setProperty("file.encoding", "UTF-8");
    Charset.defaultCharset();

    /// SET ICON
    primaryStage
        .getIcons()
        .add(new Image(ClassLoader.getSystemResourceAsStream("images/YuVideoLogo.png")));

    this.stage = primaryStage;

    primaryStage.setTitle("JGemstone");

    setUserAgentStylesheet(STYLESHEET_MODENA);

    primaryStage.setOnCloseRequest(
        new EventHandler<WindowEvent>() {
          @Override
          public void handle(WindowEvent event) {
            Platform.exit();
            System.exit(0);
          }
        });

    show_login_screen();
  }

  private void show_login_screen() {
    fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("fxml/LoginWin.fxml"), bundle);
    try {
      rootMainWindow = fxmlLoader.load();
      loginCtrl = fxmlLoader.getController();
      loginCtrl.stage = this.stage;

      //     scene = new Scene(rootMainWindow);
//      scene = new Scene(new JFXDecorator(stage, rootMainWindow));
//      stage.setScene(scene);
      //     stage.setResizable(false);
      //    stage.setTitle("YUVideo LOGIN");
      //    stage.show();
      scene = new Scene(rootMainWindow);
      scene.getStylesheets()
          .add(ClassLoader.getSystemResource("css/MainOrig.css").toExternalForm());
      stage.setScene(scene);
      loginCtrl.scene = scene;
      loginCtrl.stage = stage;
      stage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
