package net.yuvideo.jgemstone.client.Controllers;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainWindowController implements Initializable {
    public static boolean appExit = false;
    public MenuItem mExit;
    public ImageView iStatusServer;
    public MenuItem mSetup;
    public AnchorPane anchorMainWindow;
    public Label lStatusConnection;
    public Client client;
    public BorderPane MainBorderPane;
    public MenuItem mesta;
    public Button bUplateMain;
    public MenuItem mImportCSV;
    public MenuItem IPTVPaketi;


    ResourceBundle resource;
    private FXMLLoader fxmloader;
    Thread threadCheckAlive;

    private Timer checkPingTimer = new Timer(true);


    //CONTROLLERS
    private KorisniciController korctrl;
    private boolean disconnect = false;

    public MainWindowController() {

    }

    @Override
    public void initialize(URL location, final ResourceBundle resources) {
        this.resource = resources;
        //EXIT From Application

        lStatusConnection.setText("Konektovan");
        exitApp();


        checkPingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkPing();
            }
        }, 1000);




    }

    private void checkPing() {
        Platform.runLater(() -> lStatusConnection.setText((client.checkLatency())));
    }

    private void exitApp() {

        //button exit
        mExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                appExit = true;
                anchorMainWindow.getScene().getWindow().hide();
                disconnect = true;
                threadCheckAlive.interrupt();
                Platform.exit();
                System.exit(0);
            }

        });


    }


    public void mOpenSetup(ActionEvent actionEvent) throws IOException {

        Stage stage = new Stage();
        Parent root = null;
        root = FXMLLoader.load(ClassLoader.getSystemResource("fxml/Options.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Podešavanja");
        stage.initModality(Modality.WINDOW_MODAL);

        stage.showAndWait();
    }


    public void showKorisnici(ActionEvent actionEvent) {
        fxmloader = new FXMLLoader(ClassLoader.getSystemResource("fxml/Korisnici.fxml"), resource);
        try {
            MainBorderPane.setCenter(fxmloader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }

        korctrl = fxmloader.getController();
        korctrl.client = client;


    }

    public void showMesta(ActionEvent actionEvent) {
        NewInterface mestaInterface = new NewInterface("fxml/Mesta.fxml", "Mesta", resource);
        MestaController mestaController = mestaInterface.getLoader().getController();
        mestaController.client = client;
        mestaController.osveziMesto(null);
        mestaInterface.getStage().showAndWait();
    }

    public void showOprema(ActionEvent actionEvent) {
        NewInterface opremaInterface = new NewInterface("fxml/Oprema.fxml", "Oprema", resource);
        OpremaController opremaController = opremaInterface.getLoader().getController();
        opremaController.client = client;
        opremaController.refresh_table();
        opremaInterface.getStage().showAndWait();
    }

    public void showOperaterList(ActionEvent actionEvent) {
        NewInterface operatersEdit = new NewInterface("fxml/Operateri.fxml", "Operateri", resource);
        OperaterController operaterController = operatersEdit.getLoader().getController();
        operaterController.client = client;
        operaterController.show_data();
        operatersEdit.getStage().showAndWait();

    }

    public void showInternetPaket(ActionEvent actionEvent) {
        NewInterface internetPaketInterface = new NewInterface("fxml/InternetPaket.fxml", "Internet Paketi", resource);
        InternetPaketController internetPaketController = internetPaketInterface.getLoader().getController();
        internetPaketController.client = this.client;
        internetPaketController.showData();
        internetPaketInterface.getStage().showAndWait();
    }

    public void showDTVPaket(ActionEvent actionEvent) {
        NewInterface digitalniTVPaketInterface = new NewInterface("fxml/DigitalnaTVPaket.fxml", "Digitalni TV Paketi", resource);
        DigitalniTVPaketController digitalniTVPaketController = digitalniTVPaketInterface.getLoader().getController();
        digitalniTVPaketController.client = client;
        digitalniTVPaketController.showData();
        digitalniTVPaketInterface.getStage().showAndWait();
    }

    public void showUgovoriTemplate(ActionEvent actionEvent) {
    }

    public void showUgovori(ActionEvent actionEvent) {
        NewInterface ugovoriInterface = new NewInterface("fxml/Ugovori.fxml", "Ugovori", resource);
        UgovoriController ugovoriController = ugovoriInterface.getLoader().getController();
        ugovoriController.client = client;
        ugovoriController.set_datas();
        ugovoriInterface.getStage().showAndWait();
    }

    public void showBoxPaket(ActionEvent actionEvent) {
        NewInterface boxPaketInterface = new NewInterface("fxml/BoxPaket.fxml", "BOX Paket", resource);
        BoxPaketController boxPaketController = boxPaketInterface.getLoader().getController();
        boxPaketController.client = client;
        boxPaketController.set_data();
        boxPaketInterface.getStage().showAndWait();
    }

    public void showUplateMain(ActionEvent actionEvent) {
        NewInterface uplateMainInterface = new NewInterface("fxml/UplateMain.fxml", "UPLATE", resource);
        UplateMain uplateMainController = uplateMainInterface.getLoader().getController();
        uplateMainController.client = this.client;
        uplateMainController.showData();
        uplateMainInterface.getStage().showAndWait();
    }

    public void showInternetMain(ActionEvent actionEvent) {
        NewInterface internetMainInterface = new NewInterface("fxml/InternetMain.fxml", "INTERNET", resource);
        InternetMainController internetMainController = internetMainInterface.getLoader().getController();
        internetMainController.client = client;
        internetMainController.stage = internetMainInterface.getStage();
        internetMainInterface.getStage().showAndWait();
    }

    public void showFisknaTelefonijaPaket(ActionEvent actionEvent) {
        NewInterface fiksnaTelefonijaInterface = new NewInterface("fxml/FiksnaTelefonijaPaket.fxml", "FIKSNA TELEFONIJA", resource);
        FiksnaTelefonijaPaket fiksnaTelefonijaPaketController = fiksnaTelefonijaInterface.getLoader().getController();
        fiksnaTelefonijaPaketController.client = this.client;
        fiksnaTelefonijaPaketController.setTable();
        fiksnaTelefonijaInterface.getStage().showAndWait();
    }


    public void showObracun(ActionEvent actionEvent) {
        NewInterface mesecniObracunInterface = new NewInterface("fxml/FiksnaObracun.fxml", "FIKSNA TELEFONIJA OBRAČUN", resource);
        FiksnaMesecniObracuni fiksnaMesecniObracuniController = mesecniObracunInterface.getLoader().getController();
        fiksnaMesecniObracuniController.client = client;
        mesecniObracunInterface.getStage().showAndWait();

    }

    public void mOpenStalkerAPITest(ActionEvent actionEvent) {
    }

    public void showIPTVPaketi(ActionEvent actionEvent) {
        NewInterface IPTVPaketInterface = new NewInterface("fxml/IPTVPaketi.fxml", "IPTV _Paketi", resource);
        IPTVPaketiController iptvPaketiController = IPTVPaketInterface.getLoader().getController();
        iptvPaketiController.client = client;
        iptvPaketiController.showPaketiTable();
        IPTVPaketInterface.getStage().showAndWait();
    }

    public void importCSV(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("Mesečni obračun CSV fajl", "*.csv");
        fileChooser.getExtensionFilters().addAll(extensionFilter);
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setTitle("Importovanje CSV fajla");

        List<File> lf = fileChooser.showOpenMultipleDialog(anchorMainWindow.getScene().getWindow());

        JSONObject jfileObj = new JSONObject();
        jfileObj.put("action", "add_CSV_FIX_Telefonija");
        if (lf == null)
            return;

        for (File file : lf)
            try {
                String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                jfileObj.put(file.getAbsoluteFile().getName(), content);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        JSONObject jObj = client.send_object(jfileObj);

        if (jObj.has("ERROR")) {
            AlertUser.error("GESKA", jObj.getString("ERROR"));
        } else {
            AlertUser.info("Import CSV fajla", "Importovanje CSV fajla je uspesno zavrseno!");
        }


    }

    public void showPregledCSV(ActionEvent actionEvent) {
        NewInterface showPregledCSVInterface =
                new NewInterface("fxml/CSVPreview.fxml",
                        "Pregled CSV-a", this.resource);
        CSVPreview csvPreviewController = showPregledCSVInterface.getLoader().getController();
        csvPreviewController.client = this.client;
        csvPreviewController.setData();
        showPregledCSVInterface.getStage().showAndWait();
    }


}


