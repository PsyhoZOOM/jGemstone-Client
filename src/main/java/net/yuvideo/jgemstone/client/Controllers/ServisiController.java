package net.yuvideo.jgemstone.client.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.Services;
import net.yuvideo.jgemstone.client.classes.messageS;

/**
 * Created by zoom on 9/2/16.
 */
public class ServisiController implements Initializable {
    public Button bNovaServices;
    public Button bTraziServices;
    public TableView tServicesTable;
    public TableColumn cID;
    public TableColumn cNaziv;
    public TableColumn cCena;
    public TableColumn cOpis;
    public MenuItem miIzmena;
    public MenuItem miIzbrisiServis;

    public Client client;
    public TextField tTraziService;
    //JSON
    JSONObject jObj;
    JSONObject jService;
    private ResourceBundle resources;
    private String resoruceFXML;
    private Stage stage;
    private messageS mess;
    private ArrayList<Services> services;
    private Services service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    public void set_table_services(String serviceName) {

        tServicesTable.setEditable(true);
        cID.setCellValueFactory(new PropertyValueFactory<Services, Integer>("id"));
        cNaziv.setCellValueFactory(new PropertyValueFactory<Services, String>("naziv"));
        cCena.setCellValueFactory(new PropertyValueFactory<Services, String>("cena"));
        cOpis.setCellValueFactory(new PropertyValueFactory<Services, String>("opis"));
        set_table(serviceName);

    }

    private void set_table(String serviceName) {
        ObservableList<Services> data = FXCollections.observableArrayList(get_serivces_table_list(serviceName));
        tServicesTable.setItems(data);
    }

    private ArrayList<Services> get_serivces_table_list(String serviceName) {
        mess = new messageS();
        mess.setAction("get_services");
        mess.setQuery(serviceName);
        jObj = new JSONObject();
        jObj.put("action", "get_services");
        jObj.put("serviceName", serviceName);

        jObj = client.send_object(jObj);

        services = new ArrayList<>();

        jService = new JSONObject();
        for (int i = 1; i < jObj.length(); i++) {
            jService = (JSONObject) jObj.get(String.valueOf(i));
            service = new Services();
            service.setBr(i);
            service.setId(jService.getInt("id"));
            service.setNaziv(jService.getString("naziv"));
            service.setCena(Double.valueOf(jService.getInt("cena")));
            service.setOpis(jService.getString("opis"));
            services.add(service);

        }

        return services;
    }

    public void traziServise(ActionEvent actionEvent) {
        set_table(tTraziService.getText());
    }

    public void izmenaServices(ActionEvent actionEvent) {
        if (tServicesTable.getSelectionModel().getSelectedIndex() == -1) {
            //Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + "no user selected" + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nije izabran ni jedan servis", ButtonType.OK);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("GREŠKA!");
            alert.initOwner(stage);
            alert.showAndWait();
            System.out.println(alert.getButtonTypes());
            if (alert.getResult() == ButtonType.YES) {
                //do stuff
            }
            return;
        }

        service = (Services) tServicesTable.getSelectionModel().getSelectedItem();
        resoruceFXML = "src/main/resources/EditServisi.fxml";

        final NewInterface editServiceInterface = new NewInterface(resoruceFXML, "Izmena Servisa", resources);
        EditServicesController editServicesController = editServiceInterface.getLoader().getController();
        editServicesController.client = client;
        editServicesController.serviceID = service.getId();
        editServicesController.show_data();
        editServiceInterface.getStage().showAndWait();
        set_table("");


    }

    public void novService(ActionEvent actionEvent) {
        resoruceFXML = "src/main/resources/NovServis.fxml";
        NewInterface newServiceInterface = new NewInterface(resoruceFXML, "Nov Servis", resources);
        NovServicesController novServicesController = newServiceInterface.getLoader().getController();
        novServicesController.client = client;
        novServicesController.bClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                newServiceInterface.getStage().close();
                set_table("");
            }
        });
        newServiceInterface.getStage().showAndWait();
        set_table("");
    }


    public void deleteService(ActionEvent actionEvent) {
        Alert alert;
        if (tServicesTable.getSelectionModel().getSelectedIndex() == -1) {
            //Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + "no user selected" + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert = new Alert(Alert.AlertType.WARNING, "Nije izabran ni jedan servis", ButtonType.OK);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("GREŠKA!");
            alert.initOwner(stage);
            alert.showAndWait();
            System.out.println(alert.getButtonTypes());
            return;
        } else {
            ButtonType bYES = new ButtonType("Da", ButtonBar.ButtonData.YES);
            ButtonType bNO = new ButtonType("NE", ButtonBar.ButtonData.NO);

            alert = new Alert(Alert.AlertType.CONFIRMATION, "Da li ste sigurni da želite da izbrišete servis?\n" +
                    "Svim korisnicima kojima pripada ovaj servis ce biti izbrisan!", bYES, bNO);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("Brisanje Servisa!");
            alert.initOwner(stage);
            alert.showAndWait();
            if (alert.getResult() == bNO) {
                return;
            }
        }
        service = (Services) tServicesTable.getSelectionModel().getSelectedItem();


        jObj = new JSONObject();
        jObj.put("action", "delete_service");
        jObj.put("serviceId", service.getId());

        jObj = client.send_object(jObj);
        set_table("");


    }
}





