package JGemstone;

import JGemstone.classes.Client;
import JGemstone.classes.EncodingControl;
import JGemstone.classes.messageS;
import JGemstone.interfaces.MainWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main extends Application {
    public static final Logger LOGGER = LogManager.getLogger();
    public static ResourceBundle bundle;
    public static Boolean LoginSuccess = false;
    Scene scene;
    Parent rootMainWindow;
    Client client;
    MainWindowController mainCtrl;
    messageS msg = new messageS();
    private Locale locale;
    private String locale_sr;
    private String login;


    //// TODO: 9/14/16  3. UGOVORI ZA KORISNIK
    //// TODO: 9/14/16  4. Stampanje ugovora
    //// TODO: 9/14/16  5. Dozvole operatera
    //// TODO: 9/14/16  6. Stampa racuna

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(final Stage primaryStage) throws Exception {


        locale_sr = "sr";
        locale = new Locale(locale_sr);
 //       System.setProperty("javafx.userAgentStylesheetUrl", STYLESHEET_CASPIAN);


        bundle = ResourceBundle.getBundle("JGemstone.locale.lang", locale, new EncodingControl("UTF8"));

        final FXMLLoader main_scr = new FXMLLoader((getClass().getResource("/JGemstone/resources/fxml/MainWindow.fxml")), bundle);
        FXMLLoader login__scr = new FXMLLoader((getClass().getResource("/JGemstone/resources/fxml/LoginWin.fxml")), bundle);

        //setup network client
        //client.setup_client();
        //setup windows controllers
        rootMainWindow = main_scr.load();

        scene = new Scene(rootMainWindow);
        //scene.getStylesheets().add("/JGemstone/resources/css/Main.css");
        primaryStage.setTitle("jGemstone");

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);

        mainCtrl = main_scr.getController();




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

    private String object_worker(Object obj) {
        messageS mess = null;
        if (obj.getClass().equals(messageS.class)) {
            mess = (messageS) obj;

        }
        return mess.getMessage();


    }


}


