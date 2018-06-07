package net.yuvideo.jgemstone.client.Controllers;

import static javafx.application.Platform.exit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
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
import net.yuvideo.jgemstone.client.Controllers.Mape.MainMapController;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import org.json.JSONObject;

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
  Thread threadCheckAlive;
  private FXMLLoader fxmloader;
  private Timer checkPingTimer = new Timer(true);

  // CONTROLLERS
  private KorisniciController korctrl;
  private boolean disconnect = false;

  public MainWindowController() {
  }

  @Override
  public void initialize(URL location, final ResourceBundle resources) {
    this.resource = resources;
    // EXIT From Application

    lStatusConnection.setText("Konektovan");
    exitApp();

    /*
    checkPingTimer.schedule(new TimerTask() {
        @Override
        public void run() {
            checkPing();
        }
    }, 1000);

    */

  }

  public void checkData() {
    final int[] sendDt = new int[1];
    final int[] recivDt = new int[1];
    final long[] latency = {0};

    client.ss.addListener(
        new ChangeListener<Number>() {
          @Override
          public void changed(
              ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            sendDt[0] = (int) newValue;
            setStatus(latency[0], sendDt[0], recivDt[0]);
          }
        });
    client.rs.addListener(
        new ChangeListener<Number>() {
          @Override
          public void changed(
              ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            recivDt[0] = (int) newValue;
            setStatus(latency[0], sendDt[0], recivDt[0]);
          }
        });
    client.result.addListener(
        new ChangeListener<Number>() {
          @Override
          public void changed(
              ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            latency[0] = (long) newValue;
            setStatus(latency[0], sendDt[0], recivDt[0]);
          }
        });
  }

  private void setStatus(long latency, int sendDt, int recivDt) {
    int sendBytes = sendDt;
    int receivedBytes = recivDt;
    String sendByte;
    String receivedByte;

    if (sendBytes > 1000 && sendBytes < 1000000) {
      sendByte = String.format("%dKb/s", sendBytes / 1000);
    } else if (sendBytes > 1000000) {
      sendByte = String.format("%dMb/s", sendBytes / 1000 / 1000);

    } else {
      sendByte = String.format("%db/s", sendBytes);
    }
    if (receivedBytes > 1000 && receivedBytes < 1000000) {
      receivedByte = String.format("%dKb/s", receivedBytes / 1000);
    } else if (receivedBytes > 1000000) {
      receivedByte = String.format("%dMb/s", receivedBytes / 1000 / 1000);
    } else {
      receivedByte = String.format("%db/s", receivedBytes);
    }

    String isAlive;
    if (client.get_connection_state()) {
      isAlive = "OK";
    } else {
      isAlive = "Disconnected";
    }

    Task task =
        new Task() {
          @Override
          protected Object call() {

            Platform.runLater(
                new Runnable() {
                  @Override
                  public void run() {
                    lStatusConnection.setText(
                        String.format(
                            "Server: %s, Latency: %s/ms SendBytes: %s ReceivedBytes: %s ",
                            isAlive, latency, sendByte, receivedByte));
                  }
                });

            return null;
          }
        };
    Thread thread = new Thread(task);
    thread.start();
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
    optionsController.client = client;
    optionsController.saveFIRMA = true;
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
    NewInterface internetPaketInterface =
        new NewInterface("fxml/InternetPaket.fxml", "Internet Paketi", resource);
    InternetPaketController internetPaketController =
        internetPaketInterface.getLoader().getController();
    internetPaketController.client = this.client;
    internetPaketController.showData();
    internetPaketInterface.getStage().showAndWait();
  }

  public void showDTVPaket(ActionEvent actionEvent) {
    NewInterface digitalniTVPaketInterface =
        new NewInterface("fxml/DigitalnaTVPaket.fxml", "Digitalni TV Paketi", resource);
    DigitalniTVPaketController digitalniTVPaketController =
        digitalniTVPaketInterface.getLoader().getController();
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

  public void showInternetMain(ActionEvent actionEvent) {
    NewInterface internetMainInterface =
        new NewInterface("fxml/Administration/InternetMain.fxml", "INTERNET", resource);
    InternetMainController internetMainController =
        internetMainInterface.getLoader().getController();
    internetMainController.client = client;
    internetMainController.setTreeItems();
    internetMainInterface.getStage().showAndWait();
  }

  public void showFisknaTelefonijaPaket(ActionEvent actionEvent) {
    NewInterface fiksnaTelefonijaInterface =
        new NewInterface("fxml/FiksnaTelefonijaPaket.fxml", "FIKSNA TELEFONIJA", resource);
    FiksnaTelefonijaPaket fiksnaTelefonijaPaketController =
        fiksnaTelefonijaInterface.getLoader().getController();
    fiksnaTelefonijaPaketController.client = this.client;
    fiksnaTelefonijaPaketController.setTable();
    fiksnaTelefonijaInterface.getStage().showAndWait();
  }

  public void showObracun(ActionEvent actionEvent) {
    NewInterface mesecniObracunInterface =
        new NewInterface("fxml/FiksnaObracun.fxml", "FIKSNA TELEFONIJA OBRAČUN", resource);
    FiksnaMesecniObracuni fiksnaMesecniObracuniController =
        mesecniObracunInterface.getLoader().getController();
    fiksnaMesecniObracuniController.client = client;
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
    jsonObject = client.send_object(jsonObject);
  }

  public void showIPTVPaketi(ActionEvent actionEvent) {
    NewInterface IPTVPaketInterface =
        new NewInterface("fxml/IPTVPaketi.fxml", "IPTV _Paketi", resource);
    IPTVPaketiController iptvPaketiController = IPTVPaketInterface.getLoader().getController();
    iptvPaketiController.client = client;
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
    csvStatusImportController.CSVStatusImportController(client);

//             client.send_object(jfileObj);
    csvStatusImportController.setLf(lf);

    progressInd.getStage().showAndWait();


  }

  public void showPregledCSV(ActionEvent actionEvent) {
    NewInterface showPregledCSVInterface =
        new NewInterface("fxml/CSVPreview.fxml", "Pregled CSV-a", this.resource);
    CSVPreview csvPreviewController = showPregledCSVInterface.getLoader().getController();
    csvPreviewController.client = this.client;
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
    ostaleUslugeController.client = this.client;
    ostaleUslugeController.setData();
    showOstaleUsluge.getStage().showAndWait();
  }

  public void showArtikli(ActionEvent actionEvent) {
    NewInterface magacinInterface =
        new NewInterface("fxml/ArtikliMain.fxml", "ARTIKLI", this.resource);
    ArtikliMainController artikliMainController = magacinInterface.getLoader().getController();
    artikliMainController.client = this.client;
    artikliMainController.setForms();
    magacinInterface.getStage().showAndWait();
  }

  public void mOpenPDFPreview(ActionEvent actionEvent) {
  }

  public void MagacinShow(ActionEvent actionEvent) {
    NewInterface addMagacinInt =
        new NewInterface("fxml/MagaciniPreview.fxml", "KREIRANJE MAGACINA", this.resource);
    MagacinPreviewController magacinEditController = addMagacinInt.getLoader().getController();
    magacinEditController.client = this.client;
    magacinEditController.showData();
    addMagacinInt.getStage().showAndWait();
  }

  public void showPDVObracun(ActionEvent actionEvent) {
    NewInterface izvestajPDVObracunInteface =
        new NewInterface("fxml/IzvestajPDVObracun.fxml", "OBRAČUN PDV", this.resource);
    IzvestajPDVObracun izvestajPDVObracunController =
        izvestajPDVObracunInteface.getLoader().getController();
    izvestajPDVObracunController.client = this.client;
    izvestajPDVObracunInteface.getStage().showAndWait();
  }

  public void showProgressTest(ActionEvent actionEvent) {
  }

  public void showStampaRacuna(ActionEvent actionEvent) {
    NewInterface stampaRacunaInterface =
        new NewInterface("fxml/Racuni/StampaRacuna.fxml", "ŠTAMPA RAČUNA", this.resource);
    StampaRacuna stampaRacunaController = stampaRacunaInterface.getLoader().getController();
    stampaRacunaController.client = this.client;
    stampaRacunaController.setData();
    stampaRacunaInterface.getStage().showAndWait();
  }

  public void showMape(ActionEvent actionEvent) {
    NewInterface mapeInterface = new NewInterface("fxml/Maps/MainMaps.fxml", "MPE", this.resource);
    MainMapController mainMapController = mapeInterface.getLoader().getController();
    mainMapController.client = this.client;
    mapeInterface.getStage().showAndWait();
  }
}
