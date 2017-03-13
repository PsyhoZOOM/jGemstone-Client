package JGemstone;

import JGemstone.classes.Client;
import JGemstone.classes.EncodingControl;
import JGemstone.interfaces.LoginWinController;
import JGemstone.interfaces.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    public static final Logger LOGGER = LogManager.getLogger();
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

        Font.loadFont(Main.class.getResource("/JGemstone/resources/font/roboto/Roboto-Black.ttf").toExternalForm(), 12);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {


        //locale_sr = "RS";
        //locale = new Locale(locale_sr);
        //System.setProperty("javafx.userAgentStylesheetUrl", STYLESHEET_CASPIAN);


        bundle = ResourceBundle.getBundle("JGemstone.locale.lang", new Locale("sr", "RS"), new EncodingControl("utf-8"));


        Locale.setDefault(new Locale("sr", "RS"));


        //FXMLLoader main_scr = new FXMLLoader((getClass().getResource("/JGemstone/resources/fxml/MainWindow.fxml")), bundle);
        //FXMLLoader login_scr = new FXMLLoader((getClass().getResource("/JGemstone/resources/fxml/LoginWin.fxml")), bundle);

        primaryStage.setTitle("JGemstone");


        while (!appExit) {
            LOGGER.info(Locale.getDefault());
            show_login_screen();
            if (client.get_connection_state() && client != null) {
                show_main_win();
            }
        }


        //scene.getStylesheets().add("/JGemstone/resources/css/Main.css");



/*
        Parent root = FXMLLoader.load(getClass().getResource("resources/fxml/MainWindow.fxml"), bundle);

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
        fxmlLoader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/LoginWin.fxml"), bundle);
        try {
            rootMainWindow = fxmlLoader.load();
            loginCtrl = fxmlLoader.getController();
            Scene scene = new Scene(rootMainWindow);
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.setTitle("YUVidoe LOGIN");
            mainStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

        client = loginCtrl.client;
        appExit = loginCtrl.appExit;


    }

    private void show_main_win() {
        fxmlLoader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/MainWindow.fxml"), bundle);
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


