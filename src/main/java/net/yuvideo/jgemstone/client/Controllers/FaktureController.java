package net.yuvideo.jgemstone.client.Controllers;

import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Fakture;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.Users;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXToolbar;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.controlsfx.control.Notifications;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

/**
 * Created by zoom on 11/22/16.
 */
public class FaktureController implements Initializable {
    public JFXComboBox cmbBrFakture;
    public JFXComboBox cmbGodina;
    public JFXButton bPrikaziFakture;
    public TableView tblFakture;
    public TableColumn cId;
    public TableColumn colBr;
    public TableColumn colVrstaNaziv;
    public TableColumn cJedMere;
    public TableColumn cKolicina;
    public TableColumn cCenaBezPdv;
    public TableColumn cVrednostBezPdv;
    public TableColumn cOsnovicaZaPdv;
    public TableColumn cStopaPDV;
    public TableColumn cVrednostSaPDV;
    public JFXButton bDodajFakturu;
    public JFXButton bClose;
    public JFXToolbar jfxToolbar;
    public MenuItem miDeleteFakture;
    public TableColumn cIznosPDV;
    public Label lOsnovicaZaPDV;
    public Label lPDV;
    public Label lvrednostSaPDVZbir;
    public Label lUkupnoOsnovicaZaPDV;
    public Label lIznosPDVZbir;
    public Client client;
    public Users userData;
    public ResourceBundle resource;
    DecimalFormat decFormat = new DecimalFormat("#,###.00");
    Stage stage;
    private URL location;
    private String resourceFXML;
    private Fakture selectedFacture;
    private JSONObject jObj;
    private Logger LOGGER = LogManager.getLogger("FAKTURE");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        makeHeaderWrappable(cId);
        makeHeaderWrappable(colBr);
        makeHeaderWrappable(colVrstaNaziv);
        makeHeaderWrappable(cJedMere);
        makeHeaderWrappable(cKolicina);
        makeHeaderWrappable(cCenaBezPdv);
        makeHeaderWrappable(cVrednostBezPdv);
        makeHeaderWrappable(cOsnovicaZaPdv);
        makeHeaderWrappable(cStopaPDV);
        makeHeaderWrappable(cVrednostSaPDV);

        bClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage = (Stage) bClose.getScene().getWindow();
                stage.close();
            }
        });

        set_table();

    }

    public void set_table() {

        cId.setCellValueFactory(new PropertyValueFactory<Fakture, Integer>("id"));
        colBr.setCellValueFactory(new PropertyValueFactory<Fakture, Integer>("brFakture"));
        colVrstaNaziv.setCellValueFactory(new PropertyValueFactory<Fakture, String>("naziv"));
        cJedMere.setCellValueFactory(new PropertyValueFactory<Fakture, String>("jedMere"));
        cKolicina.setCellValueFactory(new PropertyValueFactory<Fakture, Integer>("kolicina"));
        cCenaBezPdv.setCellValueFactory(new PropertyValueFactory<Fakture, Double>("jedCena"));
        cCenaBezPdv.setCellFactory(tc -> new TableCell<Fakture, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(decFormat.format(value.doubleValue()));
                }
            }
        });

        cVrednostBezPdv.setCellValueFactory(new PropertyValueFactory<Fakture, Double>("vrednostBezPDV"));
        cVrednostBezPdv.setCellFactory(tc -> new TableCell<Fakture, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(decFormat.format(value.doubleValue()));
                }
            }
        });

        cOsnovicaZaPdv.setCellValueFactory(new PropertyValueFactory<Fakture, Double>("osnovicaZaPDV"));
        cOsnovicaZaPdv.setCellFactory(tc -> new TableCell<Fakture, Double>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(decFormat.format(value.doubleValue()));
                }
            }
        });

        cStopaPDV.setCellValueFactory(new PropertyValueFactory<Fakture, Integer>("stopaPDV"));
        cStopaPDV.setCellFactory(tc -> new TableCell<Fakture, Integer>() {
            @Override
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(decFormat.format(value));
                }
            }
        });

        cIznosPDV.setCellValueFactory(new PropertyValueFactory<Fakture, Double>("iznosPDV"));
        cIznosPDV.setCellFactory(tc -> new TableCell<Fakture, Double>() {
            @Override
            protected void updateItem(Double Value, boolean empty) {
                super.updateItem(Value, empty);
                {
                    if (empty) {
                        setText(null);
                    } else {
                        setText(decFormat.format(Value.doubleValue()));
                    }
                }
            }
        });

        cVrednostSaPDV.setCellValueFactory(new PropertyValueFactory<Fakture, Double>("vrednostSaPDV"));
        cVrednostSaPDV.setCellFactory(tc -> new TableCell<Fakture, Double>() {
            @Override
            protected void updateItem(Double Value, boolean empty) {
                super.updateItem(Value, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(decFormat.format(Value.doubleValue()));
                }
            }
        });


    }

    private ArrayList<Fakture> get_fakture() {
        Fakture fakture;
        jObj = new JSONObject();
        jObj.put("action", "get_fakture");
        jObj.put("userId", userData.getId());
        jObj.put("brFakture", cmbBrFakture.getValue());
        jObj.put("godina", cmbGodina.getValue());
        jObj = client.send_object(jObj);


        JSONObject jfakture;

        ArrayList<Fakture> faktureArray = new ArrayList<>();

        for (int i = 0; i < jObj.length(); i++) {
            jfakture = (JSONObject) jObj.get(String.valueOf(i));
            fakture = new Fakture();
            fakture.setId(jfakture.getInt("id"));
            fakture.setNaziv(jfakture.getString("vrstaNaziv"));
            fakture.setJedMere(jfakture.getString("jedMere"));
            fakture.setKolicina(jfakture.getInt("kolicina"));
            fakture.setJedCena(jfakture.getDouble("jedCena"));
            fakture.setStopaPDV(jfakture.getInt("stopaPdv"));
            fakture.setBrFakture(jfakture.getInt("brFakture"));
            fakture.setGodina(jfakture.getString("godina"));
            fakture.setDateCreated(jfakture.getString("dateCreated"));
            fakture.setVrednostBezPDV(jfakture.getDouble("VrednostBezPDV"));
            fakture.setOsnovicaZaPDV(jfakture.getDouble("OsnovicaZaPDV"));
            fakture.setIznosPDV(jfakture.getDouble("iznosPDV"));
            fakture.setVrednostSaPDV(jfakture.getDouble("VrednostSaPDV"));
            faktureArray.add(fakture);

        }
        return faktureArray;
    }


    public void prikaziFakture(ActionEvent actionEvent) {
        ObservableList<Fakture> fakture = FXCollections.observableArrayList(get_fakture());
        tblFakture.setItems(fakture);
        set_table();
        set_cmbBoxes();
        calculate_fakture();

    }

    private void set_cmbBoxes() {
        jObj = new JSONObject();
        jObj.put("action", "get_fakture");
        jObj.put("userId", userData.getId());
        jObj = client.send_object(jObj);

        JSONObject jfakture;

        ArrayList<Fakture> faktureArray = new ArrayList<>();
        cmbBrFakture.getItems().removeAll();
        cmbGodina.getItems().removeAll();

        for (int i = 0; i < jObj.length(); i++) {
            jfakture = (JSONObject) jObj.get(String.valueOf(i));
            if (!cmbBrFakture.getItems().contains(jfakture.getInt("brFakture"))) {
                cmbBrFakture.getItems().add(jfakture.getInt("brFakture"));
            }
            if (!cmbGodina.getItems().contains(jfakture.getString("godina"))) {
                cmbGodina.getItems().add(jfakture.getString("godina"));
            }
        }
        cmbBrFakture.getItems().sorted(Comparator.naturalOrder());
        cmbGodina.getItems().sorted(Comparator.naturalOrder());
    }

    private void calculate_fakture() {
        int table_size = tblFakture.getItems().size();
        double osnovica_za_pdv = 0;
        double pdv = 0;
        double vrednost_sa_pdv = 0;
        double ukupno_osnovica_za_pdv;
        double iznos_pdv = 0;
        double ukupno_vrednost_sa_pdv;
        double OsnovicaZaPdvSaPdvZbir = 0;

        Fakture fak;

        for (int i = 0; i < table_size; i++) {
            fak = (Fakture) tblFakture.getItems().get(i);
            osnovica_za_pdv += fak.getOsnovicaZaPDV();
            pdv = fak.getStopaPDV();
            iznos_pdv += fak.getIznosPDV();
            vrednost_sa_pdv += fak.getVrednostSaPDV();

        }


        lOsnovicaZaPDV.setText(decFormat.format(osnovica_za_pdv));
        lPDV.setText(decFormat.format(pdv));
        lIznosPDVZbir.setText(decFormat.format(iznos_pdv));
        lvrednostSaPDVZbir.setText(decFormat.format(vrednost_sa_pdv));


    }

    private void makeHeaderWrappable(TableColumn col) {
        Label label = new Label(col.getText());
        label.setStyle("-fx-padding: 8px");
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);

        StackPane stack = new StackPane();
        stack.getChildren().add(label);
        stack.prefWidthProperty().bind(col.widthProperty().subtract(5));
        label.prefWidthProperty().bind(stack.prefWidthProperty());
        col.setGraphic(stack);
    }

    public void dodajFakturu(ActionEvent actionEvent) {

        if (cmbBrFakture.getEditor().getText().isEmpty() || cmbGodina.getEditor().getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nedostaje broj fakture ili godina");
            alert.setTitle("Upozornje");
            alert.setHeaderText("Greška");
            alert.initOwner(stage);
            alert.showAndWait();
            return;
        } else {
        }
        resourceFXML = "src/main/resources/NovaFaktura.fxml";
        NewInterface novaFakturaInterface = new NewInterface(resourceFXML, "Nova Faktura", resource);
        NovaFakturaController novaFakturaController = novaFakturaInterface.getLoader().getController();
        novaFakturaController.client = this.client;
        novaFakturaController.brFakture = cmbBrFakture.getEditor().getText();
        novaFakturaController.user = userData;
        novaFakturaController.godina = cmbGodina.getEditor().getText();
        novaFakturaInterface.getStage().showAndWait();
        prikaziFakture(null);


    }


    public void deleteFakturu(ActionEvent actionEvent) {
        if (tblFakture.getSelectionModel().getSelectedIndex() == -1) {
            Notifications.create()
                    .position(Pos.BOTTOM_RIGHT)
                    .text("Nije izabrana faktura za brisanje")
                    .hideAfter(Duration.seconds(6))
                    .title("Upozorenje")
                    .showWarning();
            return;
        }
        selectedFacture = (Fakture) tblFakture.getSelectionModel().getSelectedItem();
        jObj = new JSONObject();
        jObj.put("action", "delete_fakturu");
        jObj.put("idFactura", selectedFacture.getId());
        jObj = client.send_object(jObj);
        LOGGER.info(jObj.getString("Message"));
        prikaziFakture(null);
    }


}
