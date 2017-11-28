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
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.EncodingControl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class ClientMain extends Application {
    public static final Logger LOGGER = Logger.getLogger("CLIENT_MAIN");
    public static ResourceBundle bundle;
    Client client;
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
    public void start(final Stage primaryStage) throws Exception {

        //locale_sr = "RS";
        //locale = new Locale(locale_sr)
        //System.setProperty("javafx.userAgentStylesheetUrl", STYLESHEET_CASPIAN);

        System.out.println(ResourceBundle.getBundle("lang", new Locale("sr", "RS")).toString());
        bundle = ResourceBundle.getBundle("lang", new Locale("sr", "RS"), new EncodingControl("utf-8"));

        Font.loadFont(getClass().getResourceAsStream("font.roboto/Roboto-Black.ttf"), 9);
        Locale.setDefault(new Locale("sr_latin", "RS"));
        System.setProperty("file.encoding", "UTF-8");
        Charset.defaultCharset();

		///SET ICON
		primaryStage.getIcons().add(new Image(ClassLoader.getSystemResourceAsStream("images/YuVideoLogo.png")));
		
        this.stage = primaryStage;

        primaryStage.setTitle("JGemstone");

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
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
            scene = new Scene(rootMainWindow);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("YUVideo LOGIN");
            stage.show();
        } catch (IOException e) {
		System.out.println(e.getMessage());
        }

    }


}


