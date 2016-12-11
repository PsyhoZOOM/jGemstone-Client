package JGemstone.interfaces;

import JGemstone.classes.Client;
import JGemstone.classes.Uplate;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.json.JSONObject;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zoom on 9/7/16.
 */
public class KorisnikUplateController implements Initializable {
    public Button bClose;
    //table dugovanja
    public TableColumn cID;
    public TableColumn cBR;
    public TableColumn cDatumZaduzenja;
    public TableColumn cServiceName;
    public TableView tblKorUplate;
    public TableColumn cUplaceno;
    public Label lUkupnoUplaceno;
    public MenuItem mmDeletePayment;
    //table uplate
    public TableView tblUplate;
    public TableColumn cuId;
    public TableColumn cuBr;
    public TableColumn cuNazivServisa;
    public TableColumn cuMesecZaduzenja;
    public TableColumn cuDatumUplate;
    public TableColumn cuOperater;
    public TableColumn cuUplaceno;
    public MenuItem cmUplati;
    public Button bUplati;
    //table zaduzenja
    public TableColumn czId;
    public TableColumn czBr;
    public TableColumn czDatumZaduzenja;
    public TableColumn czNazivServisa;
    public TableColumn czZaUplatu;
    public TableColumn czUplaceno;
    public TableView czTbl;
    public Label lZaduzenje;
    public Button bPrint;
    public GridPane gridPane;
    public ResourceBundle resource;
    Client client;
    String UserName;
    private int userID;
    private String resourceFXML;
    private ArrayList<Uplate> uplate;
    private Uplate uplata;
    private Double ukupno;
    private Alert alert;
    private Stage stage;

    private Double zUplaceno = 0.00;
    private Double zDug = 0.00;


    private Logger LOGGER = LogManager.getLogger("USER_PAYMENTS");

    //JSON
    private JSONObject jObj;
    private JSONObject juplata;


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;
    }

    public void set_table_uplate(int userID) {
        //table && columns dugovanja
        tblKorUplate.setEditable(true);
        cBR.setCellValueFactory(new PropertyValueFactory<Uplate, Integer>("br"));
        cID.setCellValueFactory(new PropertyValueFactory<Uplate, Integer>("id"));
        cDatumZaduzenja.setCellValueFactory(new PropertyValueFactory<Uplate, String>("datumZaduzenja"));
        cServiceName.setCellValueFactory(new PropertyValueFactory<Uplate, String>("serviceName"));
        cUplaceno.setCellValueFactory(new PropertyValueFactory<Uplate, String>("uplaceno") {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Uplate, String> uplaceno_c) {
                SimpleStringProperty property = new SimpleStringProperty();
                property.setValue(String.format("%,.2f", Double.valueOf(uplaceno_c.getValue().getUplaceno())));
                return property;
            }
        });

        //table uplate
        tblUplate.setEditable(true);
        cuBr.setCellValueFactory(new PropertyValueFactory<Uplate, Integer>("br"));
        cuId.setCellValueFactory(new PropertyValueFactory<Uplate, Integer>("id"));
        cuDatumUplate.setCellValueFactory(new PropertyValueFactory<Uplate, String>("datumUplate"));
        cuMesecZaduzenja.setCellValueFactory(new PropertyValueFactory<Uplate, String>("datumZaduzenja"));
        cuNazivServisa.setCellValueFactory(new PropertyValueFactory<Uplate, String>("serviceName"));
        cuOperater.setCellValueFactory(new PropertyValueFactory<Uplate, String>("operater"));
        cuUplaceno.setCellValueFactory(new PropertyValueFactory<Uplate, String>("uplaceno") {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Uplate, String> uplaceno_u) {
                SimpleStringProperty propertyU = new SimpleStringProperty();
                propertyU.setValue(String.format("%,.2f", Double.valueOf(uplaceno_u.getValue().getUplaceno())));
                return propertyU;
            }
        });

        //table zaduznje
        czTbl.setEditable(true);
        czBr.setCellValueFactory(new PropertyValueFactory<Uplate, Integer>("br"));
        czId.setCellValueFactory(new PropertyValueFactory<Uplate, Integer>("id"));
        czDatumZaduzenja.setCellValueFactory(new PropertyValueFactory<Uplate, String>("datumZaduzenja"));
        czNazivServisa.setCellValueFactory(new PropertyValueFactory<Uplate, String>("serviceName"));
        czZaUplatu.setCellValueFactory(new PropertyValueFactory<Uplate, String>("zaUplatu") {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Uplate, String> zauplatu) {
                SimpleStringProperty propertyZaUplatu = new SimpleStringProperty();
                propertyZaUplatu.setValue(String.format("%,.2f", Double.valueOf(zauplatu.getValue().getZaUplatu())));
                return propertyZaUplatu;
            }
        });
        czUplaceno.setCellValueFactory(new PropertyValueFactory<Uplate, String>("uplaceno") {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Uplate, String> zuplaceno) {
                SimpleStringProperty propertyUplaceno = new SimpleStringProperty();
                propertyUplaceno.setValue(String.format("%,.2f", Double.valueOf(zuplaceno.getValue().getUplaceno())));
                return propertyUplaceno;
            }
        });

        //table &  columns uplate
        show_table_uplate(userID);
    }

    public void show_table_uplate(int userID) {
        ObservableList<Uplate> data = FXCollections.observableArrayList(get_uplate_zaduzenja_table_list(userID));
        ObservableList<Uplate> data_u = FXCollections.observableArrayList(get_uplate_table_list(userID));
        ObservableList<Uplate> data_z = FXCollections.observableArrayList(get_uplate_table_list_sve(userID));
        tblKorUplate.setItems(data);
        tblUplate.setItems(data_u);
        czTbl.setItems(data_z);

        //setting row to red if user has debt
        czTbl.setRowFactory(new Callback<TableView<Uplate>, TableRow<Uplate>>() {
            @Override
            public TableRow<Uplate> call(TableView<Uplate> param) {
                return new TableRow<Uplate>() {
                    @Override
                    protected void updateItem(Uplate upl, boolean empty) {
                        super.updateItem(upl, empty);
                        if (upl != null) {
                            if (upl.getUplaceno() == 0 && upl.getZaUplatu() != 0) {
                                setStyle("-fx-background-color: #9f0001");
                            } else {
                            }
                        }
                    }
                };
            }
        });
        czTbl.refresh();

        ukupno = 0.00;

        for (int i = 0; i < data.size(); i++) {
            ukupno = ukupno + Double.valueOf(data.get(i).getUplaceno());
        }

        lUkupnoUplaceno.setText(String.format("%,.2f", ukupno));


    }
    @FXML
    private void set_row_to_red(){
         //setting row to red if user has debt
        czTbl.setRowFactory(new Callback<TableView<Uplate>, TableRow<Uplate>>() {
            @Override
            public TableRow<Uplate> call(TableView<Uplate> param) {
                return new TableRow<Uplate>() {
                    @Override
                    protected void updateItem(Uplate upl, boolean empty) {
                        super.updateItem(upl, empty);
                        if (upl != null) {
                            if (upl.getUplaceno() == 0 && upl.getZaUplatu() != 0) {
                                setStyle("-fx-background-color: #9f0000");
                            } else {
                            }
                        }
                    }
                };
            }
        });
        czTbl.refresh();

    }


    private ArrayList<Uplate> get_uplate_table_list_sve(int userID) {
        jObj = new JSONObject();
        uplate = new ArrayList<>();
        zDug = zUplaceno = 0.00;

        jObj.put("action", "get_uplate_zaduzenja_user_sve");
        jObj.put("userID", userID);
        jObj = client.send_object(jObj);

        for (int i = 0; i < jObj.length(); i++) {
            juplata = (JSONObject) jObj.get(String.valueOf(i));
            uplata = new Uplate();
            uplata.setId(juplata.getInt("id"));
            uplata.setBr(i + 1);
            uplata.setDatumZaduzenja(juplata.getString("datumZaduzenja"));
            uplata.setServiceName(juplata.getString("serviceName"));
            uplata.setZaUplatu(juplata.getDouble("zaUplatu"));
            zDug = zDug + uplata.getZaUplatu();
            uplata.setUplaceno(juplata.getDouble("uplaceno"));
            zUplaceno = zUplaceno + uplata.getUplaceno();
            uplate.add(uplata);
        }
        LOGGER.info(zDug - zUplaceno);
        lZaduzenje.setText(String.format("UKUPNO: %,.2f - %,.2f = %,.2f", zDug, zUplaceno, zDug - zUplaceno));
        return uplate;
    }

    private ArrayList<Uplate> get_uplate_zaduzenja_table_list(int userID) {
        jObj = new JSONObject();
        uplate = new ArrayList<>();

        jObj.put("action", "get_uplate_zaduzenja_user");
        jObj.put("userID", userID);
        jObj = client.send_object(jObj);

        for (int i = 0; i < jObj.length(); i++) {
            juplata = (JSONObject) jObj.get(String.valueOf(i));
            uplata = new Uplate();
            uplata.setId(juplata.getInt("id"));
            uplata.setBr(i + 1);
            if(juplata.has("datumZaduzenja"))
            uplata.setDatumZaduzenja(juplata.getString("datumZaduzenja"));
            if(juplata.has("serviceName"))
            uplata.setServiceName(juplata.getString("serviceName"));
            if(juplata.has("uplaceno"))
            uplata.setUplaceno(juplata.getDouble("uplaceno"));
            if(juplata.has("datumUplate"))
            uplata.setDatumUplate(juplata.getString("datumUplate"));
            if(juplata.has("operName"))
            uplata.setOperater(juplata.getString("operName"));
            uplate.add(uplata);
        }
        return uplate;
    }

    private ArrayList<Uplate> get_uplate_table_list(int userID) {
        jObj = new JSONObject();
        uplate = new ArrayList<>();

        jObj.put("action", "get_uplate_user");
        jObj.put("userID", userID);
        jObj = client.send_object(jObj);

        for (int i = 0; i < jObj.length(); i++) {
            juplata = (JSONObject) jObj.get(String.valueOf(i));
            uplata = new Uplate();
            uplata.setId(juplata.getInt("id"));
            uplata.setBr(i + 1);
            uplata.setDatumZaduzenja(juplata.getString("dateDebt"));
            uplata.setDatumUplate(juplata.getString("paymentDate"));
            uplata.setOperater(juplata.getString("operName"));
            uplata.setUplaceno(juplata.getDouble("debt"));
            uplata.setServiceName(juplata.getString("serviceName"));
            uplate.add(uplata);
        }
        return uplate;
    }

    public void deletePayment(ActionEvent actionEvent) {
        if (tblUplate.getSelectionModel().getSelectedIndex() == -1) {
            alert = new Alert(Alert.AlertType.WARNING, "Nije izabrana ni jedna uplata", ButtonType.OK);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("GREŠKA!");
            alert.initOwner(stage);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                //do stuff
            }
            return;
        } else {
            Uplate id_c_del = (Uplate) tblUplate.getSelectionModel().getSelectedItem();

            jObj = new JSONObject();
            jObj.put("action", "delete_payment");
            jObj.put("paymentId", id_c_del.getId());

            jObj = client.send_object(jObj);

            LOGGER.info(jObj.getString("message"));

            show_table_uplate(userID);


        }
    }


    public void uplatiAction(ActionEvent actionEvent) {
        Uplate colUplate = (Uplate) tblKorUplate.getSelectionModel().getSelectedItem();

        LOGGER.info(colUplate.getId());
        jObj = new JSONObject();
        jObj.put("action", "new_payment");
        jObj.put("id", colUplate.getId());
        jObj = client.send_object(jObj);
        LOGGER.info(jObj.getString("message"));
        show_table_uplate(userID);
    }

    public void printAction(ActionEvent actionEvent) throws PrinterException, IOException {
        /*
        FileChooser filechose = new FileChooser();
        File selectedFile = filechose.showOpenDialog(null);

        if(selectedFile == null) return;

        String filename = selectedFile.getAbsolutePath();
        LOGGER.info(selectedFile.getAbsoluteFile());
        //PDDocument document = new PDDocument.load(new File(filename));
        PDDocument document =  PDDocument.load(new File(filename));
        */

        PDDocument doc = new PDDocument();
        PDPage empty = new PDPage(PDRectangle.A4);
        PDFont fontb = PDType1Font.TIMES_BOLD;
        PDFont font = PDType1Font.TIMES_ROMAN;
        doc.addPage(empty);
        PDPageContentStream content = new PDPageContentStream(doc, empty);

        content.beginText();
        content.setFont(fontb, 18);
        float x = 50, y = 800;
        content.setLeading(14.5f);
        content.newLineAtOffset(100, 800);

        ArrayList<Uplate> aa = get_uplate_table_list_sve(userID);
        String linija;
        LOGGER.info("size of aa :"+aa.size());

        content.showText("BR Datum Zaduzenja Usluga Dug Uplaceno");
        content.setFont(font, 13);
        for (int i = 0; i < aa.size(); i++) {
            content.newLine();

            content.showText(String.format("%d %s %,.2f %,.2f",
                    aa.get(i).getBr(), aa.get(i).getDatumZaduzenja(), aa.get(i).getZaUplatu(),
                    aa.get(i).getUplaceno()));
            LOGGER.info("EE" + aa.get(i).getId() + UserName + aa.get(i).getZaUplatu());
        }
        content.endText();
        content.close();
        PDStream pdstr = new PDStream(doc);
        printWithDialog(doc);
        doc.close();
    }

    private void printWithDialog(PDDocument document) {
//        PrinterJob job = PrinterJob.getPrinterJob();
        Printer printer = Printer.getDefaultPrinter();
        LOGGER.info(Printer.getAllPrinters());
        printer.getDefaultPageLayout();

        PrinterJob job  = PrinterJob.createPrinterJob();
            Stage stageTheLabelBelongs = (Stage) gridPane.getScene().getWindow();
//        job.showPageSetupDialog(stageTheLabelBelongs.getOwner());
//            job.showPrintDialog(stageTheLabelBelongs.getOwner());
 //           job.printPage(czTbl);



       /*
        job.setPageable(new PDFPageable(document));
        job.setJobName("PDF_JOB");
        if (job != null) {
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
        */
    }
}
