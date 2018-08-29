package net.yuvideo.jgemstone.client.Controllers;

import static javafx.application.Platform.exit;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.yuvideo.jgemstone.client.Controllers.Administration.Administration;
import net.yuvideo.jgemstone.client.Controllers.Administration.Devices;
import net.yuvideo.jgemstone.client.Controllers.Fiksna.FiksnaPozivi;
import net.yuvideo.jgemstone.client.Controllers.Mape.MainMapController;
import net.yuvideo.jgemstone.client.classes.AlertUser;
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
  public Label lMessage;
  public JFXButton bShowMessage;
  public StackPane mainStackPane;
  public JFXListView jfxLIstMessages;
  public VBox vbSideView;
  public JFXButton bCloseMessageWin;

  ResourceBundle resource;
  Thread threadCheckAlive;
  private FXMLLoader fxmloader;
  private Timer checkPingTimer = new Timer(true);

  // CONTROLLERS
  private KorisniciController korctrl;
  private boolean disconnect = false;
  private boolean administrationView = false;
  private Stage stage;
  public Settings LocalSettings;
  private Client client;

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
    initMessageWindow();


  }

  public void initMessageWindow() {
    lMessage.setText("0");
    vbSideView.setVisible(false);
    lMessage.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (Integer.valueOf(newValue) > 0) {
          bShowMessage.setDisable(false);
        } else {
          bShowMessage.setDisable(true);
        }
      }
    });

    bShowMessage.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (vbSideView.isVisible()) {
          vbSideView.setVisible(false);
        } else {
          vbSideView.setVisible(true);
        }

      }
    });

    bCloseMessageWin.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (vbSideView.isVisible()) {
          vbSideView.setVisible(false);
        } else {
          vbSideView.setVisible(true);
        }
      }
    });

    updateMessages();


  }

  private void updateMessages() {
    Image img = new Image(ClassLoader.getSystemResourceAsStream("icons/YuVideoLogo.png"), 20.0,
        20.0, true, true);

    Label label1 = new Label(String.format("%s - Poruka od: SYSTEM ", LocalDateTime.now()));
    label1.setBackground(
        new Background(new BackgroundFill(Color.RED, new CornerRadii(5), Insets.EMPTY)));
    Label label2 = new Label(String.format("%s - Poruka od: BAKI ", LocalDateTime.now()));
    Label label3 = new Label(String.format("%s - Poruka od MARKO ", LocalDateTime.now()));
    label1.setGraphic(new ImageView(img));
    label2.setGraphic(new ImageView(img));
    label3.setGraphic(new ImageView(img));

    jfxLIstMessages.getItems().addAll(label1, label2, label3);

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

    //   optionsController.setClient(this.client);
    optionsController.setClient(this.client);

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
    korctrl.setClient(this.client);
    korctrl.setStage(stage);
  }

  public void showMesta(ActionEvent actionEvent) {
    NewInterface mestaInterface = new NewInterface("fxml/Mesta.fxml", "Mesta", resource);
    MestaController mestaController = mestaInterface.getLoader().getController();
    mestaController.setClient(this.client);
    mestaController.osveziMesto(null);
    mestaInterface.getStage().showAndWait();
  }

  public void showOprema(ActionEvent actionEvent) {
    NewInterface opremaInterface = new NewInterface("fxml/Oprema.fxml", "Oprema", resource);
    OpremaController opremaController = opremaInterface.getLoader().getController();
    opremaController.setClient(this.client);
    opremaController.refresh_table();
    opremaInterface.getStage().showAndWait();
  }

  public void showOperaterList(ActionEvent actionEvent) {
    NewInterface operatersEdit = new NewInterface("fxml/Operateri.fxml", "Operateri", resource);
    OperaterController operaterController = operatersEdit.getLoader().getController();
    operaterController.setClient(this.client);
    operaterController.show_data();
    operatersEdit.getStage().showAndWait();
  }

  public void showInternetPaket(ActionEvent actionEvent) {
    NewInterface internetPaketInterface =
        new NewInterface("fxml/InternetPaket.fxml", "Internet Paketi", resource);
    InternetPaketController internetPaketController =
        internetPaketInterface.getLoader().getController();
    internetPaketController.setClient(this.client);
    internetPaketController.showData();
    internetPaketInterface.getStage().showAndWait();
  }

  public void showDTVPaket(ActionEvent actionEvent) {
    NewInterface digitalniTVPaketInterface =
        new NewInterface("fxml/DigitalnaTVPaket.fxml", "Digitalni TV Paketi", resource);
    DigitalniTVPaketController digitalniTVPaketController =
        digitalniTVPaketInterface.getLoader().getController();
    digitalniTVPaketController.setClient(this.client);
    digitalniTVPaketController.showData();
    digitalniTVPaketInterface.getStage().showAndWait();
  }

  public void showUgovoriTemplate(ActionEvent actionEvent) {
  }

  public void showUgovori(ActionEvent actionEvent) {
    NewInterface ugovoriInterface = new NewInterface("fxml/Ugovori.fxml", "Ugovori", resource);
    UgovoriController ugovoriController = ugovoriInterface.getLoader().getController();
    ugovoriController.setClient(this.client);
    ugovoriController.set_datas();
    ugovoriInterface.getStage().showAndWait();
  }

  public void showBoxPaket(ActionEvent actionEvent) {
    NewInterface boxPaketInterface = new NewInterface("fxml/BoxPaket.fxml", "BOX Paket", resource);
    BoxPaketController boxPaketController = boxPaketInterface.getLoader().getController();
    boxPaketController.setClient(this.client);
    boxPaketController.set_data();
    boxPaketInterface.getStage().showAndWait();
  }

  public void showInternetMain(ActionEvent actionEvent) {
    if (administrationView) {

      return;
    }
    NewInterface administrationInterface =
        new NewInterface("fxml/Administration/Administration.fxml", "ADMINISTRACIJA", resource,
            true,
            false);
    Administration administrationController = administrationInterface.getLoader().getController();
    this.administrationView = true;
    administrationController.setClient(this.client);
    administrationInterface.getStage().show();
    administrationInterface.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        administrationView = false;
        administrationInterface.getStage().close();
        administrationController.stopUpdate();
      }
    });
  }

  public void showFisknaTelefonijaPaket(ActionEvent actionEvent) {
    NewInterface fiksnaTelefonijaInterface =
        new NewInterface("fxml/FiksnaTelefonijaPaket.fxml", "FIKSNA TELEFONIJA", resource);
    FiksnaTelefonijaPaket fiksnaTelefonijaPaketController =
        fiksnaTelefonijaInterface.getLoader().getController();
    fiksnaTelefonijaPaketController.setClient(this.client);
    fiksnaTelefonijaPaketController.setTable();
    fiksnaTelefonijaInterface.getStage().showAndWait();
  }

  public void showObracun(ActionEvent actionEvent) {
    NewInterface mesecniObracunInterface =
        new NewInterface("fxml/FiksnaObracun.fxml", "FIKSNA TELEFONIJA OBRAČUN", resource);
    FiksnaMesecniObracuni fiksnaMesecniObracuniController =
        mesecniObracunInterface.getLoader().getController();
    fiksnaMesecniObracuniController.setClient(this.client);
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
    iptvPaketiController.setClient(this.client);
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
    csvStatusImportController.setClient(this.client);

    csvStatusImportController.setLf(lf);

    progressInd.getStage().showAndWait();


  }

  public void showPregledCSV(ActionEvent actionEvent) {
    NewInterface showPregledCSVInterface =
        new NewInterface("fxml/CSVPreview.fxml", "Pregled CSV-a", this.resource);
    CSVPreview csvPreviewController = showPregledCSVInterface.getLoader().getController();
    csvPreviewController.setClient(this.client);
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
    ostaleUslugeController.setClient(this.client);
    ostaleUslugeController.setData();
    showOstaleUsluge.getStage().showAndWait();
  }

  public void showArtikli(ActionEvent actionEvent) {
    NewInterface magacinInterface =
        new NewInterface("fxml/ArtikliMain.fxml", "ARTIKLI", this.resource);
    ArtikliMainController artikliMainController = magacinInterface.getLoader().getController();
    artikliMainController.setClient(this.client);
    artikliMainController.setForms();
    magacinInterface.getStage().showAndWait();
  }

  public void mOpenPDFPreview(ActionEvent actionEvent) {
  }

  public void MagacinShow(ActionEvent actionEvent) {
    NewInterface addMagacinInt =
        new NewInterface("fxml/MagaciniPreview.fxml", "KREIRANJE MAGACINA", this.resource);
    MagacinPreviewController magacinEditController = addMagacinInt.getLoader().getController();
    magacinEditController.setClient(this.client);
    magacinEditController.showData();
    addMagacinInt.getStage().showAndWait();
  }

  public void showPDVObracun(ActionEvent actionEvent) {
    NewInterface izvestajPDVObracunInteface =
        new NewInterface("fxml/IzvestajPDVObracun.fxml", "OBRAČUN PDV", this.resource);
    IzvestajPDVObracun izvestajPDVObracunController =
        izvestajPDVObracunInteface.getLoader().getController();
    izvestajPDVObracunController.setClient(this.client);
    izvestajPDVObracunInteface.getStage().showAndWait();
  }

  public void showProgressTest(ActionEvent actionEvent) {
  }

  public void showStampaRacuna(ActionEvent actionEvent) {
    NewInterface stampaRacunaInterface =
        new NewInterface("fxml/Racuni/StampaRacuna.fxml", "ŠTAMPA RAČUNA", this.resource);
    StampaRacuna stampaRacunaController = stampaRacunaInterface.getLoader().getController();
    stampaRacunaController.setClient(this.client);
    stampaRacunaController.setData();
    stampaRacunaInterface.getStage().showAndWait();
  }

  public void showMape(ActionEvent actionEvent) {
    NewInterface mapeInterface = new NewInterface("fxml/Maps/MainMaps.fxml", "MPE", this.resource);
    MainMapController mainMapController = mapeInterface.getLoader().getController();
    mainMapController.setClient(this.client);
    mapeInterface.getStage().showAndWait();
  }

  public void showUredjaji(ActionEvent actionEvent) {
    NewInterface devicesInterface = new NewInterface("fxml/Administration/Devices.fxml", "UREĐAJI",
        this.resource);
    Devices devicesController = devicesInterface.getLoader().getController();
    devicesController.setClient(this.client);
    devicesController.setItems();
    devicesInterface.getStage().showAndWait();
  }


  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void showFiksnaPozivi(ActionEvent actionEvent) {
    NewInterface fiksnaPoziviInterface = new NewInterface("fxml/Fiksna/FiksnaPozivi.fxml",
        "FIKSNA POZIVI", this.resource);
    FiksnaPozivi fiksnaPoziviController = fiksnaPoziviInterface.getLoader().getController();
    fiksnaPoziviController.setClient(this.client);
    fiksnaPoziviInterface.getStage().showAndWait();
  }

  public void testSNMPWALK(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "testSNMP");
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    }

  }

  public void showOnlinePPPeEInterface(ActionEvent actionEvent) {
    JSONObject object = new JSONObject();
    object.put("action", "mtAPITest");
    Client mtCl = new Client(client.getLocal_settings());
    object = mtCl.send_object(object);
    System.out.println(object.toString());
  }
}
