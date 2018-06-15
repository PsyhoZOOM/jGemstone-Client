package net.yuvideo.jgemstone.client.Controllers;

import static javafx.application.Platform.exit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.yuvideo.jgemstone.client.Controllers.Administration.Devices;
import net.yuvideo.jgemstone.client.Controllers.Mape.MainMapController;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.Settings;
import net.yuvideo.jgemstone.client.classes.db_connection;
import org.json.JSONObject;

public class MainWindowController implements Initializable {

  public static boolean appExit = false;
  public MenuItem mExit;
  public ImageView iStatusServer;
  public MenuItem mSetup;
  public AnchorPane anchorMainWindow;
  public Label lStatusConnection;
  public BorderPane MainBorderPane;
  public MenuItem mesta;
  public Button bUplateMain;
  public MenuItem mImportCSV;
  public MenuItem IPTVPaketi;

  ResourceBundle resource;
  Thread threadCheckAlive;
  private FXMLLoader fxmloader;
  private Timer checkPingTimer = new Timer(true);

  // CONTROLLERS
  private KorisniciController korctrl;
  private boolean disconnect = false;
  private boolean internetIsShowing = false;
  private Stage stage;
  public Settings LocalSettings;

  public MainWindowController() {
  }

  @Override
  public void initialize(URL location, final ResourceBundle resources) {
    this.resource = resources;
    // EXIT From Application
    db_connection db = new db_connection();
    this.LocalSettings = db.getLocal_settings();

    lStatusConnection.setText("Konektovan");
    exitApp();


  }


  private void exitApp() {

    // button exit
    mExit.setOnAction(
        new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
            appExit = true;
            anchorMainWindow.getScene().getWindow().hide();
            disconnect = true;
            if (threadCheckAlive != null) {
              threadCheckAlive.interrupt();
            }
            exit();
            System.exit(0);
          }
        });
  }

  public void mOpenSetup(ActionEvent actionEvent) {

    NewInterface optionsInterface = new NewInterface("fxml/Options.fxml", "PODESŠAVANJA", resource);
    OptionsController optionsController = optionsInterface.getLoader().getController();
    optionsController.saveFIRMA = true;
    optionsController.LocalSettings = LocalSettings;
    optionsController.setDataFirma();
    optionsController.enableTabs();
    optionsInterface.getStage().showAndWait();

  }

  public void showKorisnici(ActionEvent actionEvent) {
    fxmloader = new FXMLLoader(ClassLoader.getSystemResource("fxml/Korisnici.fxml"), resource);
    try {
      MainBorderPane.setCenter(fxmloader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }

    korctrl = fxmloader.getController();
    korctrl.setClient(new Client(LocalSettings));
    korctrl.setStage(stage);
  }

  public void showMesta(ActionEvent actionEvent) {
    NewInterface mestaInterface = new NewInterface("fxml/Mesta.fxml", "Mesta", resource);
    MestaController mestaController = mestaInterface.getLoader().getController();
    mestaController.LocalSettings = LocalSettings;
    mestaController.osveziMesto(null);
    mestaInterface.getStage().showAndWait();
  }

  public void showOprema(ActionEvent actionEvent) {
    NewInterface opremaInterface = new NewInterface("fxml/Oprema.fxml", "Oprema", resource);
    OpremaController opremaController = opremaInterface.getLoader().getController();
    opremaController.LocalSettings = LocalSettings;
    opremaController.refresh_table();
    opremaInterface.getStage().showAndWait();
  }

  public void showOperaterList(ActionEvent actionEvent) {
    NewInterface operatersEdit = new NewInterface("fxml/Operateri.fxml", "Operateri", resource);
    OperaterController operaterController = operatersEdit.getLoader().getController();
    operaterController.show_data();
    operatersEdit.getStage().showAndWait();
  }

  public void showInternetPaket(ActionEvent actionEvent) {
    NewInterface internetPaketInterface =
        new NewInterface("fxml/InternetPaket.fxml", "Internet Paketi", resource);
    InternetPaketController internetPaketController =
        internetPaketInterface.getLoader().getController();
    internetPaketController.setClient(new Client(LocalSettings));
    internetPaketController.showData();
    internetPaketInterface.getStage().showAndWait();
  }

  public void showDTVPaket(ActionEvent actionEvent) {
    NewInterface digitalniTVPaketInterface =
        new NewInterface("fxml/DigitalnaTVPaket.fxml", "Digitalni TV Paketi", resource);
    DigitalniTVPaketController digitalniTVPaketController =
        digitalniTVPaketInterface.getLoader().getController();
    digitalniTVPaketController.showData();
    digitalniTVPaketInterface.getStage().showAndWait();
  }

  public void showUgovoriTemplate(ActionEvent actionEvent) {
  }

  public void showUgovori(ActionEvent actionEvent) {
    NewInterface ugovoriInterface = new NewInterface("fxml/Ugovori.fxml", "Ugovori", resource);
    UgovoriController ugovoriController = ugovoriInterface.getLoader().getController();
    ugovoriController.set_datas();
    ugovoriInterface.getStage().showAndWait();
  }

  public void showBoxPaket(ActionEvent actionEvent) {
    NewInterface boxPaketInterface = new NewInterface("fxml/BoxPaket.fxml", "BOX Paket", resource);
    BoxPaketController boxPaketController = boxPaketInterface.getLoader().getController();
    boxPaketController.setClient(new Client(LocalSettings));
    boxPaketController.set_data();
    boxPaketInterface.getStage().showAndWait();
  }

  public void showInternetMain(ActionEvent actionEvent) {
    if (internetIsShowing) {

      return;
    }
    NewInterface internetMainInterface =
        new NewInterface("fxml/Administration/InternetMain.fxml", "INTERNET", resource, true,
            false);
    InternetMainController internetMainController =
        internetMainInterface.getLoader().getController();
    internetMainController.setClient(new Client(LocalSettings));
    internetMainController.setItems();
    internetMainInterface.getStage().show();
    this.internetIsShowing = true;
    internetMainInterface.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        internetIsShowing = false;
        internetMainInterface.getStage().close();
      }
    });
  }

  public void showFisknaTelefonijaPaket(ActionEvent actionEvent) {
    NewInterface fiksnaTelefonijaInterface =
        new NewInterface("fxml/FiksnaTelefonijaPaket.fxml", "FIKSNA TELEFONIJA", resource);
    FiksnaTelefonijaPaket fiksnaTelefonijaPaketController =
        fiksnaTelefonijaInterface.getLoader().getController();
    fiksnaTelefonijaPaketController.setTable();
    fiksnaTelefonijaInterface.getStage().showAndWait();
  }

  public void showObracun(ActionEvent actionEvent) {
    NewInterface mesecniObracunInterface =
        new NewInterface("fxml/FiksnaObracun.fxml", "FIKSNA TELEFONIJA OBRAČUN", resource);
    FiksnaMesecniObracuni fiksnaMesecniObracuniController =
        mesecniObracunInterface.getLoader().getController();
    fiksnaMesecniObracuniController.setClient(new Client(LocalSettings));
    fiksnaMesecniObracuniController.check_if_obracun_postoji(
        LocalDate.now()
            .minusMonths(1)
            .with(TemporalAdjusters.firstDayOfMonth())
            .format(DateTimeFormatter.ofPattern("yyyy-MM")));
    mesecniObracunInterface.getStage().showAndWait();
  }

  public void mOpenStalkerAPITest(ActionEvent actionEvent) {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "test_REST_API");
    jsonObject.put("API_ACTION", "changeMMAC");
    jsonObject.put("account", "baki");
    jsonObject.put("mac", "11:22:33:44:55:66");
  }

  public void showIPTVPaketi(ActionEvent actionEvent) {
    NewInterface IPTVPaketInterface =
        new NewInterface("fxml/IPTVPaketi.fxml", "IPTV _Paketi", resource);
    IPTVPaketiController iptvPaketiController = IPTVPaketInterface.getLoader().getController();
    iptvPaketiController.showPaketiTable();
    IPTVPaketInterface.getStage().showAndWait();
  }

  public void importCSV(ActionEvent actionEvent) {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extensionFilter =
        new FileChooser.ExtensionFilter("Mesečni obračun CSV fajl", "*.csv");
    fileChooser.getExtensionFilters().addAll(extensionFilter);
    fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
    fileChooser.setTitle("Importovanje CSV fajla");

    List<File> lf = fileChooser.showOpenMultipleDialog(anchorMainWindow.getScene().getWindow());

    NewInterface progressInd = new NewInterface("fxml/CSVStatusImport.fxml", "CSV IMPORT",
        resource, false);
    CSVStatusImportController csvStatusImportController = progressInd.getLoader()
        .getController();
    csvStatusImportController.LocalSettings = LocalSettings;

    csvStatusImportController.setLf(lf);

    progressInd.getStage().showAndWait();


  }

  public void showPregledCSV(ActionEvent actionEvent) {
    NewInterface showPregledCSVInterface =
        new NewInterface("fxml/CSVPreview.fxml", "Pregled CSV-a", this.resource);
    CSVPreview csvPreviewController = showPregledCSVInterface.getLoader().getController();
    csvPreviewController.setData();
    showPregledCSVInterface.getStage().showAndWait();
  }

  public void mOpenQrScanner(ActionEvent actionEvent) {
    NewInterface showQrScanner =
        new NewInterface("fxml/QrScanner.fxml", "QR Scanner", this.resource);
    QrScannerController qrScannerController = showQrScanner.getLoader().getController();
    showQrScanner.getStage().showAndWait();
  }

  public void showOstaleUsluge(ActionEvent actionEvent) {
    NewInterface showOstaleUsluge =
        new NewInterface("fxml/OstaleUsluge.fxml", "Ostale Usluge", this.resource);
    OstaleUslugeController ostaleUslugeController = showOstaleUsluge.getLoader().getController();
    ostaleUslugeController.setClient(new Client(LocalSettings));
    ostaleUslugeController.setData();
    showOstaleUsluge.getStage().showAndWait();
  }

  public void showArtikli(ActionEvent actionEvent) {
    NewInterface magacinInterface =
        new NewInterface("fxml/ArtikliMain.fxml", "ARTIKLI", this.resource);
    ArtikliMainController artikliMainController = magacinInterface.getLoader().getController();
    artikliMainController.setClient(new Client(LocalSettings));
    artikliMainController.setForms();
    magacinInterface.getStage().showAndWait();
  }

  public void mOpenPDFPreview(ActionEvent actionEvent) {
  }

  public void MagacinShow(ActionEvent actionEvent) {
    NewInterface addMagacinInt =
        new NewInterface("fxml/MagaciniPreview.fxml", "KREIRANJE MAGACINA", this.resource);
    MagacinPreviewController magacinEditController = addMagacinInt.getLoader().getController();
    magacinEditController.LocalSettings = LocalSettings;
    magacinEditController.showData();
    addMagacinInt.getStage().showAndWait();
  }

  public void showPDVObracun(ActionEvent actionEvent) {
    NewInterface izvestajPDVObracunInteface =
        new NewInterface("fxml/IzvestajPDVObracun.fxml", "OBRAČUN PDV", this.resource);
    IzvestajPDVObracun izvestajPDVObracunController =
        izvestajPDVObracunInteface.getLoader().getController();
    izvestajPDVObracunController.LocalSettings = LocalSettings;
    izvestajPDVObracunInteface.getStage().showAndWait();
  }

  public void showProgressTest(ActionEvent actionEvent) {
  }

  public void showStampaRacuna(ActionEvent actionEvent) {
    NewInterface stampaRacunaInterface =
        new NewInterface("fxml/Racuni/StampaRacuna.fxml", "ŠTAMPA RAČUNA", this.resource);
    StampaRacuna stampaRacunaController = stampaRacunaInterface.getLoader().getController();
    stampaRacunaController.LocalSettings = LocalSettings;
    stampaRacunaController.setData();
    stampaRacunaInterface.getStage().showAndWait();
  }

  public void showMape(ActionEvent actionEvent) {
    NewInterface mapeInterface = new NewInterface("fxml/Maps/MainMaps.fxml", "MPE", this.resource);
    MainMapController mainMapController = mapeInterface.getLoader().getController();
    mainMapController.setClient(new Client(LocalSettings));
    mapeInterface.getStage().showAndWait();
  }

  public void showUredjaji(ActionEvent actionEvent) {
    NewInterface devicesInterface = new NewInterface("fxml/Administration/Devices.fxml", "UREĐAJI",
        this.resource);
    Devices devicesController = devicesInterface.getLoader().getController();
    devicesController.LocalSettings = LocalSettings;
    devicesController.setItems();
    devicesInterface.getStage().showAndWait();
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }
}
