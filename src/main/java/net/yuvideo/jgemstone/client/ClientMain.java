package net.yuvideo.jgemstone.client;


import net.yuvideo.jgemstone.client.classes.Client;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.scene.text.Font;
import net.yuvideo.jgemstone.client.Controllers.LoginWinController;
import net.yuvideo.jgemstone.client.Controllers.MainWindowController;
import net.yuvideo.jgemstone.client.classes.EncodingControl;


public class ClientMain extends Application {
    public static final Logger LOGGER = Logger.getLogger("CLIENT_MAIN");
    public static ResourceBundle bundle;
    Client client;
    MainWindowController mainCtrl;
    LoginWinController loginCtrl;
    Parent rootMainWindow;
    Stage mainStage = new Stage();
    FXMLLoader fxmlLoader;
    private Locale locale;
    private String locale_sr;
    private boolean appExit = false;


    //// TODO: 9/14/16  4. Stampanje ugovora
    //// TODO: 9/14/16  5. Dozvole operatera
    //// TODO: 9/14/16  6. Stampa racuna

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


            show_login_screen();
            if (client.get_connection_state() && client != null) {
                show_main_win();
        }


        //scene.getStylesheets().add("src/main/resources/css/Main.css");



/*
        Parent root = FXMLLoader.load(getClass().getResource("resources/MainWindow.fxml"), bundle);

        Scene scene = new Scene(root,640, 480 );
        String css = this.getClass().getResource("resources/css/Main.css").toExternalForm();
        primaryStage.setTitle("jGemstone");
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
*/
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
            client.status_conn = mainCtrl.lStatusConnection;
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


