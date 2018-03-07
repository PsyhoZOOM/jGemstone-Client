package net.yuvideo.jgemstone.client.Controllers;


import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
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
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;

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

        /*
        checkPingTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkPing();
            }
        }, 1000);

        */

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
                if (threadCheckAlive != null)
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


    public void showInternetMain(ActionEvent actionEvent) {
        NewInterface internetMainInterface = new NewInterface("fxml/Administration/InternetMain.fxml", "INTERNET", resource);
        InternetMainController internetMainController = internetMainInterface.getLoader().getController();
        internetMainController.client = client;
        internetMainController.setTreeItems();
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
        fiksnaMesecniObracuniController.check_if_obracun_postoji(LocalDate.now().minusMonths(1).with(TemporalAdjusters.firstDayOfMonth()).format(DateTimeFormatter.ofPattern("yyyy-MM")));
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

        for (File file : lf) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                jfileObj.put(file.getAbsoluteFile().getName(), content);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    public void mOpenQrScanner(ActionEvent actionEvent) {
        NewInterface showQrScanner = new NewInterface("fxml/QrScanner.fxml", "QR Scanner",
                this.resource);
        QrScannerController qrScannerController = showQrScanner.getLoader().getController();
        showQrScanner.getStage().showAndWait();


    }


    public void showOstaleUsluige(ActionEvent actionEvent) {
        NewInterface showOstaleUsluge =
                new NewInterface("fxml/OstaleUsluge.fxml",
                        "Ostale Usluge", this.resource);
        OstaleUslugeController ostaleUslugeController = showOstaleUsluge.getLoader().getController();
        ostaleUslugeController.client = this.client;
        ostaleUslugeController.setData();
        showOstaleUsluge.getStage().showAndWait();
    }

    public void showArtikli(ActionEvent actionEvent) {
        NewInterface magacinInterface = new NewInterface("fxml/ArtikliMain.fxml", "ARTIKLI", this.resource);
        ArtikliMainController artikliMainController = magacinInterface.getLoader().getController();
        artikliMainController.client = this.client;
        artikliMainController.setForms();
        magacinInterface.getStage().showAndWait();

    }

    public void mOpenPDFPreview(ActionEvent actionEvent) {
        JSONObject object = new JSONObject();
        object.put("action", "get_single_ugovor");
        object.put("idUgovora", 8);
        object = client.send_object(object);


        HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setHtmlText(object.getString("textUgovora"));
        String htmlString = htmlEditor.getHtmlText();
        htmlEditor.setHtmlText(object.getString("textUgovora"));
        htmlString = htmlString.replace("<br>", "");
        htmlString = htmlString.replace("<br/>", "");
        htmlString = htmlString.replace("<br />", "");

        htmlString = htmlString.replace("<hr>", "<p></p>");
        htmlString = htmlString.replace("<hr/>", "<p></p>");
        htmlString = htmlString.replace("<hr />", "<p></p>");
        htmlString = htmlString.replace("px", "");


        try {
            Document doc = new Document();
            OutputStream file = new FileOutputStream(System.getProperty("user.home") + "/test.pdf");
            StringReader is = new StringReader(htmlString);
            PdfWriter pdfWriter = PdfWriter.getInstance(doc, file);
            doc.open();
            doc.add(new Chunk(""));

            try {
                final Paragraph test = new Paragraph();
                System.out.println(is.toString());
                XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, doc, is);

                pdfWriter.flush();
                doc.close();
                pdfWriter.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    public void MagacinShow(ActionEvent actionEvent) {
        NewInterface addMagacinInt = new NewInterface("fxml/MagaciniPreview.fxml", "KREIRANJE MAGACINA", this.resource);
        MagacinPreviewController magacinEditController = addMagacinInt.getLoader().getController();
        magacinEditController.client = this.client;
        magacinEditController.showData();
        addMagacinInt.getStage().showAndWait();
    }
}


