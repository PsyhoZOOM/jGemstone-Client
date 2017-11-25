package net.yuvideo.jgemstone.client.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.OstaleUsluge;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class OstaleUslugeController implements Initializable {
    public Button bNov;
    public Button bIzmeni;
    public TableColumn<OstaleUsluge, String> cNazivUsluge;
    public TableColumn<OstaleUsluge, Double> cPopust;
    public TableColumn<OstaleUsluge, Double> cPDV;
    public TableColumn<OstaleUsluge, Double> cCena;
    public TableView<OstaleUsluge> tblOstaleUsluge;
    private ResourceBundle resources;
    private URL location;
    public Client client;


    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
        this.location = location;


        cNazivUsluge.setCellValueFactory(new PropertyValueFactory<>("nazivUsluge"));
        cPopust.setCellValueFactory(new PropertyValueFactory<>("popust"));
        cPDV.setCellValueFactory(new PropertyValueFactory<>("pdv"));
        cCena.setCellValueFactory(new PropertyValueFactory<>("cena"));

        cPopust.setCellFactory(new Callback<TableColumn<OstaleUsluge, Double>, TableCell<OstaleUsluge, Double>>() {
            @Override
            public TableCell<OstaleUsluge, Double> call(TableColumn<OstaleUsluge, Double> param) {
                return new TableCell<OstaleUsluge, Double>(){
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setText("");
                        }else{
                            setText(df.format(item));
                        }
                    }
                };
            }
        });

        cPDV.setCellFactory(new Callback<TableColumn<OstaleUsluge, Double>, TableCell<OstaleUsluge, Double>>() {
            @Override
            public TableCell<OstaleUsluge, Double> call(TableColumn<OstaleUsluge, Double> param) {
                return new TableCell<OstaleUsluge, Double>(){
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else{
                            setText(df.format(item));
                        }
                    }
                };
            }
        });

        cCena.setCellFactory(new Callback<TableColumn<OstaleUsluge, Double>, TableCell<OstaleUsluge, Double>>() {
            @Override
            public TableCell<OstaleUsluge, Double> call(TableColumn<OstaleUsluge, Double> param) {
                return new TableCell<OstaleUsluge, Double>(){
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty){
                            setText(null);
                        }else{
                            setText(df.format(item));
                        }
                    }
                };
            }
        });


    }


    public void updateItems(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "getOstaleUslugeData");
        jsonObject = client.send_object(jsonObject);



    }

}
