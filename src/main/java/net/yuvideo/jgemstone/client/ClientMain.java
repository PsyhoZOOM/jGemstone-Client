package net.yuvideo.jgemstone.client;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.yuvideo.jgemstone.client.Controllers.LoginWinController;
import net.yuvideo.jgemstone.client.Controllers.MainWindowController;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.EncodingControl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;


public class ClientMain extends Application {
    public static final Logger LOGGER = Logger.getLogger("CLIENT_MAIN");
    public static ResourceBundle bundle;
    Client client;
    MainWindowController mainCtrl;
    LoginWinController loginCtrl;
    Parent rootMainWindow;
    Stage mainStage = new Stage();
    FXMLLoader fxmlLoader;
    private boolean appExit = false;


    public static void main(String[] args) {
        launch(args);

    }

    public void init() throws Exception {
        super.init();

        Font.loadFont(getClass().getResourceAsStream("font.roboto/Roboto-Black.ttf"), 9);
	
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {

        //locale_sr = "RS";
        //locale = new Locale(locale_sr)
        //System.setProperty("javafx.userAgentStylesheetUrl", STYLESHEET_CASPIAN);

	    System.out.println(ResourceBundle.getBundle("lang", new Locale("sr", "RS")).toString());
        bundle = ResourceBundle.getBundle("lang", new Locale("sr", "RS"), new EncodingControl("utf-8"));

        Locale.setDefault(new Locale("sr_latin", "RS"));
        System.setProperty("file.encoding", "UTF-8");
        Charset.defaultCharset();

        primaryStage.setTitle("JGemstone");

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });


            show_login_screen();
            if (client != null && client.get_connection_state()) {
                show_main_win();
        }
    }

    private void show_login_screen() {
        fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("fxml/LoginWin.fxml"), bundle);
        try {
            rootMainWindow = fxmlLoader.load();
            loginCtrl = fxmlLoader.getController();
            Scene scene = new Scene(rootMainWindow);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.setTitle("YUVideo LOGIN");
            mainStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

        client = loginCtrl.client;
        appExit = loginCtrl.appExit;
    }

    private void show_main_win() {
        fxmlLoader = new FXMLLoader(ClassLoader.getSystemResource("fxml/MainWindow.fxml"), bundle);
        try {
            rootMainWindow = fxmlLoader.load();
            mainCtrl = fxmlLoader.getController();
            mainCtrl.client = client;
            Scene scene = new Scene(rootMainWindow);
            mainStage.setScene(scene);
            mainStage.setResizable(true);
            mainStage.setTitle("YUVIDEO");
            mainStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        appExit = true;

    }
}


