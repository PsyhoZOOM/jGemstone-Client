package JGemstone.interfaces;

import JGemstone.classes.Client;
import JGemstone.classes.checkAlive;
import JGemstone.classes.tiProperty;
import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    public MenuItem mExit;
    public Label lAction;
    public AnchorPane apCenter;
    public ImageView iStatusServer;
    public MenuItem mSetup;
    public Button bConnect;
    public BorderPane bpPane;
    public JFXHamburger hambMenuleft;
    public JFXDrawer drawerLeft;
    public VBox vbMenuLeft;
    public JFXButton bKorisnici;
    public JFXButton bGrupe;
    public JFXButton bServisi;
    public JFXButton bUgovori;
    public JFXToolbar jfxToolBar;
    public AnchorPane anchorCenter;
    public JFXSnackbar snackBar;
    public AnchorPane anchorMainWindow;
    public Label lStatusConnection;
    public Client client;
    public Logger LOGGER = LogManager.getLogger();
    @FXML
    TreeView<tiProperty> treeViewLeft;
    Thread th;

    ResourceBundle resource;
    int depth = 0;
    Thread clientThread;
    private HamburgerSlideCloseTransition hambSlideMenuLeft;
    private FXMLLoader fxmloader;
    private AnchorPane bpone;
    private KorisniciController korctrl;
    private GrupeController GroupControll;
    private ServisiController servisiController;
    private UgovoriController ugovoriController;
    private JFXDepthManager managger;
    public MainWindowController() {


    }

    @Override
    public void initialize(URL location, final ResourceBundle resources) {
        this.resource = resources;
        //EXIT From Application
       drawerLeft.setSidePane(vbMenuLeft);
        drawerLeft.close();
        hambSlideMenuLeft = new HamburgerSlideCloseTransition(hambMenuleft);
        hambSlideMenuLeft.setRate(-1);
        hambMenuleft.addEventHandler(MouseEvent.MOUSE_RELEASED, (e) -> {
            hambSlideMenuLeft.setRate(hambSlideMenuLeft.getRate() * -1);
            hambSlideMenuLeft.play();
            if (drawerLeft.isShown()) {
                drawerLeft.close();
            } else {
                drawerLeft.open();
            }
        });

        exitApp();
        connect_to_server();


    }

    private void exitApp() {

        //button exit

        mExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clientThread.interrupt();
                Platform.exit();
            }

        });
    }

    public void mOpenSetup(ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(OptionsController.class.getResource("/JGemstone/resources/fxml/Options.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Podešavanja");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                lAction.getScene().getWindow()
                // ((Node)actionEvent.getSource()).getScene().getWindow()
        );
        stage.show();
    }

    private void connect_to_server() {
        client = new Client();
        client.main_run();
        checkAlive checkPing = new checkAlive(client);
        lStatusConnection = checkPing.lStatusConnection;
        Thread ping_Check = new Thread(checkPing);
        ping_Check.start();

    }

    public void connect_to(ActionEvent actionEvent) {
        connect_to_server();

    }





    public void showKorisnici(ActionEvent actionEvent) {
        fxmloader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/Korisnici.fxml"), resource);


        try {
            bpone = fxmloader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        korctrl = fxmloader.getController();
        korctrl.client = client;
        korctrl.set_table_users("");
        jfxToolBar.setCenter(bpone);



    }

    public void showGrupe(ActionEvent actionEvent) {
        fxmloader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/Grupe.fxml"), resource);
        bpone = null;
        try {
            bpone = fxmloader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        GroupControll = fxmloader.getController();

        jfxToolBar.setCenter(bpone);
        GroupControll.client = client;
        GroupControll.setTableGroup("");
    }

    public void showServisi(ActionEvent actionEvent) {
        fxmloader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/Servisi.fxml"), resource);
        bpone = null;

        try {
            bpone = fxmloader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        servisiController = fxmloader.getController();

        jfxToolBar.setCenter(fxmloader.getRoot());
        servisiController.client = client;
        servisiController.set_table_services("");
    }

    public void showUgovori(ActionEvent actionEvent) {
        fxmloader = new FXMLLoader(getClass().getResource("/JGemstone/resources/fxml/Ugovori.fxml"), resource);
        bpone = null;
        try {
            bpone = fxmloader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ugovoriController = fxmloader.getController();

        jfxToolBar.setCenter(bpone);
        ugovoriController.client = client;
        ugovoriController.set_datas();
    }
}


