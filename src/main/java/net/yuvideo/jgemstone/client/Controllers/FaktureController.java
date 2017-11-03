package net.yuvideo.jgemstone.client.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Fakture;
import net.yuvideo.jgemstone.client.classes.Users;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Created by zoom on 11/22/16.
 */
public class FaktureController implements Initializable {
    public Button bPrikaziFakture;
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
    public Button bDodajFakturu;
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
    public Label lBrFakture;
    public TextField tNaizv;
    public ComboBox cmbJedMere;
    public Spinner spnKLolicina;
    public Spinner spnCena;
    public Spinner spnPDV;
    DecimalFormat decFormat = new DecimalFormat("#,###.00");
    Stage stage;
    private URL location;
    private String resourceFXML;
    private Fakture selectedFacture;
    private JSONObject jObj;
    private Logger LOGGER = Logger.getLogger("FAKTURE");

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


        spnCena.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    spnCena.getEditor().setText(String.valueOf(Double.parseDouble(newValue)));
                } catch (NumberFormatException ne) {
                    spnCena.getEditor().setText(oldValue);
                }
            }
        });


        spnPDV.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    spnPDV.getEditor().setText(String.valueOf(Double.parseDouble(newValue)));
                } catch (NumberFormatException ne) {
                    spnPDV.getEditor().setText(oldValue);
                }
            }
        });

        spnKLolicina.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    spnKLolicina.getEditor().setText(String.valueOf(Double.parseDouble(newValue)));
                } catch (NumberFormatException ne) {
                    spnKLolicina.getEditor().setText(oldValue);
                }
            }
        });

        SpinnerValueFactory.DoubleSpinnerValueFactory doubleSpinValueFactoryCena = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleSpinValueFactoryKol = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0);
        SpinnerValueFactory.DoubleSpinnerValueFactory doubleSpinValueFactoryPDV = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE, 0);
        spnCena.setValueFactory(doubleSpinValueFactoryCena);
        spnKLolicina.setValueFactory(doubleSpinValueFactoryKol);
        spnPDV.setValueFactory(doubleSpinValueFactoryPDV);
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


    public void dodajFakturu(ActionEvent event) {
    }

    public void deleteFakturu(ActionEvent event) {
    }

    public void prikaziFakture(ActionEvent event) {
    }
}
