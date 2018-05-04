package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.OstaleUsluge;
import org.json.JSONObject;

public class OstaleUslugeController implements Initializable {

  public Button bNov;
  public Button bIzmeni;
  public TableColumn<OstaleUsluge, String> cNazivUsluge;
  public TableColumn<OstaleUsluge, Double> cPDV;
  public TableColumn<OstaleUsluge, Double> cCena;
  public TableView<OstaleUsluge> tblOstaleUsluge;
  public TableColumn<OstaleUsluge, String> cOpis;
  public TableColumn<OstaleUsluge, Double> cCenaPDV;
  public Client client;
  DecimalFormat df = new DecimalFormat("0.00");
  private ResourceBundle resources;
  private URL location;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.resources = resources;
    this.location = location;

    cNazivUsluge.setCellValueFactory(new PropertyValueFactory<>("nazivUsluge"));
    cPDV.setCellValueFactory(new PropertyValueFactory<>("pdv"));
    cCena.setCellValueFactory(new PropertyValueFactory<>("cena"));
    cOpis.setCellValueFactory(new PropertyValueFactory<>("komentar"));
    cCenaPDV.setCellValueFactory(new PropertyValueFactory<>("cenaPDV"));

    cPDV.setCellFactory(
        new Callback<TableColumn<OstaleUsluge, Double>, TableCell<OstaleUsluge, Double>>() {
          @Override
          public TableCell<OstaleUsluge, Double> call(TableColumn<OstaleUsluge, Double> param) {
            return new TableCell<OstaleUsluge, Double>() {
              @Override
              protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setText(null);
                } else {
                  setText(df.format(item));
                }
              }
            };
          }
        });

    cCena.setCellFactory(
        new Callback<TableColumn<OstaleUsluge, Double>, TableCell<OstaleUsluge, Double>>() {
          @Override
          public TableCell<OstaleUsluge, Double> call(TableColumn<OstaleUsluge, Double> param) {
            return new TableCell<OstaleUsluge, Double>() {
              @Override
              protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                  setText(null);
                } else {
                  setText(df.format(item));
                }
              }
            };
          }
        });


  }


  public void setData() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "getOstaleUslugeData");
    jsonObject = client.send_object(jsonObject);

    ArrayList<OstaleUsluge> ostaleUslugesArr = new ArrayList<>();
    for (int i = 0; i < jsonObject.length(); i++) {
      JSONObject obj = jsonObject.getJSONObject(String.valueOf(i));
      OstaleUsluge ostaleUsluge = new OstaleUsluge();
      ostaleUsluge.setId(obj.getInt("id"));
      ostaleUsluge.setNazivUsluge(obj.getString("naziv"));
      ostaleUsluge.setCena(obj.getDouble("cena"));
      ostaleUsluge.setCenaPDV(obj.getDouble("cenaPDV"));
      ostaleUsluge.setPdv(obj.getDouble("pdv"));
      ostaleUsluge.setKomentar(obj.getString("opis"));
      ostaleUslugesArr.add(ostaleUsluge);

    }

    ObservableList<OstaleUsluge> observableList = FXCollections
        .observableArrayList(ostaleUslugesArr);
    tblOstaleUsluge.setItems(observableList);

  }


  public void showNov(ActionEvent actionEvent) {
    NewInterface novServiceInterface = new NewInterface("fxml/OstaleUslugeEdit.fxml", "Nova usluga",
        resources);
    OstaleUslugeEditController novServiceController = novServiceInterface.getLoader()
        .getController();
    novServiceController.edit = false;
    novServiceController.client = this.client;
    novServiceInterface.getStage().showAndWait();
    setData();
  }

  public void showEdit(ActionEvent actionEvent) {
    NewInterface editInTerface = new NewInterface("fxml/OstaleUslugeEdit.fxml", "Izmena usluge",
        resources);
    OstaleUslugeEditController editServiceController = editInTerface.getLoader().getController();
    editServiceController.client = this.client;
    editServiceController.edit = true;
    editServiceController.ostaleUsluge = tblOstaleUsluge.getSelectionModel().getSelectedItem();
    editServiceController.setData();
    editInTerface.getStage().showAndWait();
    setData();
  }
}
