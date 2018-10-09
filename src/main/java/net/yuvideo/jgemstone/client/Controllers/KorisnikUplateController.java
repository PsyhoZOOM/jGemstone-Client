package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Uplate;
import org.json.JSONObject;

public class KorisnikUplateController implements Initializable {

  public JFXTreeTableView<Uplate> tUplate;
  public TreeTableColumn<Uplate, String> cOper;
  public TreeTableColumn<Uplate, String> cDatum;
  public TreeTableColumn<Uplate, Double> cDuguje;
  public TreeTableColumn<Uplate, Double> cPotrazuje;
  public TreeTableColumn<Uplate, String> cOpis;
  public JFXTextField tCena;
  public JFXTextField tOpis;
  public JFXButton bUplati;
  public AnchorPane mainPane;
  public Label lDuguje;
  public Label lPotrazuje;
  public Label lUkupno;
  public JFXTextField tPretraga;
  public JFXButton bIzbrisiUplatu;


  private URL location;
  private ResourceBundle resources;
  private int userID;
  private Client client;

  private DecimalFormat df = new DecimalFormat("###,###,###,###,###,##0.00");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    cOper.setCellValueFactory(new TreeItemPropertyValueFactory<Uplate, String>("operater"));
    cDatum.setCellValueFactory(new TreeItemPropertyValueFactory<Uplate, String>("datum"));
    cDuguje.setCellValueFactory(new TreeItemPropertyValueFactory<Uplate, Double>("duguje"));
    cPotrazuje.setCellValueFactory(new TreeItemPropertyValueFactory<Uplate, Double>("potrazuje"));
    cOpis.setCellValueFactory(new TreeItemPropertyValueFactory<Uplate, String>("opis"));

    cDuguje.setCellFactory(
        new Callback<TreeTableColumn<Uplate, Double>, TreeTableCell<Uplate, Double>>() {
          @Override
          public TreeTableCell<Uplate, Double> call(TreeTableColumn<Uplate, Double> param) {
            return new TreeTableCell<Uplate, Double>() {
              @Override
              protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                  setText("");
                } else {
                  setText(df.format(item));
                }
              }
            };
          }
        });

    cPotrazuje.setCellFactory(
        new Callback<TreeTableColumn<Uplate, Double>, TreeTableCell<Uplate, Double>>() {
          @Override
          public TreeTableCell<Uplate, Double> call(TreeTableColumn<Uplate, Double> param) {
            return new TreeTableCell<Uplate, Double>() {
              @Override
              protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                  setText("");
                } else {
                  setText(df.format(item));
                }
              }
            };
          }
        });

    tPretraga.textProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue,
          String newValue) {
        if (newValue.isEmpty()) {
          return;
        }
        tUplate.setPredicate(new Predicate<TreeItem<Uplate>>() {
          @Override
          public boolean test(TreeItem<Uplate> uplateTreeItem) {
            return uplateTreeItem.getValue().getDuguje().toString().contains(newValue) ||
                uplateTreeItem.getValue().getPotrazuje().toString().contains(newValue) ||
                uplateTreeItem.getValue().getDatum().contains(newValue) ||
                uplateTreeItem.getValue().getOpis().contains(newValue);
          }
        });
      }
    });

    tUplate.getSelectionModel().selectedItemProperty().addListener(
        new ChangeListener<TreeItem<Uplate>>() {
          @Override
          public void changed(ObservableValue<? extends TreeItem<Uplate>> observable,
              TreeItem<Uplate> oldValue, TreeItem<Uplate> newValue) {
            if (newValue == null) {
              bIzbrisiUplatu.setDisable(true);
            } else {
              bIzbrisiUplatu.setDisable(false);
            }
          }
        });
  }

  public void initData() {
    setTableData();
  }


  private void setTableData() {
    Uplate uplate = new Uplate();
    uplate.setClient(client);
    ArrayList<Uplate> uplateUser = uplate.getUplateUser(userID);
    lDuguje.setText(df.format(uplate.getUkupnoUplaceno()));
    lPotrazuje.setText(df.format(uplate.getUkupnoDuguje()));
    lUkupno
        .setText(df.format(uplate.getUkupanDug()) + String.format(" (%f)", uplate.getUkupanDug()));
    ObservableList observableUplate = FXCollections.observableList(uplateUser);

    TreeItem<Uplate> treeUplate = new RecursiveTreeItem<Uplate>(observableUplate,
        RecursiveTreeObject::getChildren);
    tUplate.setShowRoot(false);
    tUplate.setRoot(treeUplate);

  }

  public void uplati(ActionEvent actionEvent) {
    Double uplata = 0.00;
    try {
      uplata = Double.valueOf(tCena.getText());
    } catch (NumberFormatException e) {
      AlertUser.error("GREKSA", "Uplata mora biti  u formatu \"1201.01 \"! ");
    }
    String opis = tOpis.getText();
    JSONObject object = new JSONObject();
    object.put("action", "uplata_korisnika");
    object.put("uplaceno", uplata);
    object.put("opis", tOpis.getText());
    object.put("userID", userID);
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    }
    setTableData();
    tCena.clear();
    tOpis.clear();

  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }

  public int getUserID() {
    return userID;
  }

  public void setUserID(int userID) {
    this.userID = userID;
  }

  public void izbrisiUplatu(ActionEvent actionEvent) {
    if (tUplate.getSelectionModel().getSelectedIndex() == -1) {
      return;
    }

    boolean brisanje_uplate = AlertUser
        .yesNo("BRISANJE UPLATE", "Da li ste sigurni da želite da izbrisete uplatu?");
    if (!brisanje_uplate) {
      return;
    }

    JSONObject object = new JSONObject();
    object.put("action", "uplata_delete");
    object.put("idUplate", tUplate.getSelectionModel().getSelectedItem().getValue().getId());
    object = client.send_object(object);
    if (object.has("ERROR")) {
      AlertUser.error("GRESKA", object.getString("ERROR"));
    }
    bIzbrisiUplatu.setDisable(true);

    setTableData();

  }

  public void showZaduzenja(ActionEvent actionEvent) {
  }

  public void stampajUplate(ActionEvent actionEvent) {
  }
}
