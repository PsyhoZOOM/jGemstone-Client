package JGemstone.interfaces;

import JGemstone.classes.Client;
import JGemstone.classes.NewInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    public static boolean appExit = false;
    public MenuItem mExit;
    public ImageView iStatusServer;
    public MenuItem mSetup;
    public AnchorPane anchorMainWindow;
    public Label lStatusConnection;
    public Client client;
    public Logger LOGGER = LogManager.getLogger();
    public BorderPane MainBorderPane;

    ResourceBundle resource;
    private FXMLLoader fxmloader;


    //CONTROLLERS
    private KorisniciController korctrl;
    private GrupeController GroupControll;
    private ServisiController servisiController;
    private UgovoriController ugovoriController;
    private MestaController mestaController;
    private OpremaController opremaController;
    public MainWindowController() {

    }

    @Override
    public void initialize(URL location, final ResourceBundle resources) {
        this.resource = resources;
        //EXIT From Application

        exitApp();
    }

    private void exitApp() {

        //button exit
        mExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                appExit = true;
                anchorMainWindow.getScene().getWindow().hide();
                Platform.exit();
            }

        });
    }



    public void mOpenSetup(ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(OptionsController.class.getResource("/JGemstone/resources/fxml/Options.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Podešavanja");
        stage.initModality(Modality.WINDOW_MODAL);

        stage.showAndWait();
    }



    public void showKorisnici(ActionEvent actionEvent) {
        fxmloader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/Korisnici.fxml"), resource);
        try {
            MainBorderPane.setCenter(fxmloader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        korctrl = fxmloader.getController();
        korctrl.client = client;



    }

    public void showGrupe(ActionEvent actionEvent) {
        fxmloader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/Grupe.fxml"), resource);

        GroupControll = fxmloader.getController();

        GroupControll.client = client;
        GroupControll.setTableGroup("");

        try {
            MainBorderPane.setCenter(fxmloader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showServisi(ActionEvent actionEvent) {
        fxmloader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/Servisi.fxml"), resource);

        servisiController = fxmloader.getController();
        servisiController.client = client;
        servisiController.set_table_services("");

        try {
            MainBorderPane.setCenter(fxmloader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void showUgovori(ActionEvent actionEvent) {
        fxmloader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/Ugovori.fxml"), resource);

        ugovoriController = fxmloader.getController();

        ugovoriController.client = client;
        ugovoriController.set_datas();

        try {
            MainBorderPane.setCenter(fxmloader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMesta(ActionEvent actionEvent) {
        fxmloader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/Mesta.fxml"), resource);

        mestaController = fxmloader.getController();
        mestaController.client = client;

        mestaController.osveziMesto(null);

        try {
            MainBorderPane.setCenter(fxmloader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOprema(ActionEvent actionEvent) {
        fxmloader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/Oprema.fxml"), resource);

        opremaController = fxmloader.getController();
        opremaController.client = client;
        opremaController.refresh_table();

        try {
            MainBorderPane.setCenter(fxmloader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showOperaterList(ActionEvent actionEvent) {
        NewInterface operatersEdit = new NewInterface("/JGemstone/resources/fxml/Operateri.fxml", "Operateri", resource);
        OperaterController operaterController = operatersEdit.getLoader().getController();
        operaterController.client = client;
        operaterController.show_data();
        operatersEdit.getStage().showAndWait();

    }
}


