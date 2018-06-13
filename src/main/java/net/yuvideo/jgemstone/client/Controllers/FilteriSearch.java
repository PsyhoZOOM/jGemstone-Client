package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FilteriSearch implements Initializable {


  public CheckBox chkIme;
  public TextField tIme;
  public CheckBox chkNazivFirme;
  public TextField tNazivFirme;
  public CheckBox chkMesto;
  public TextField tMesto;
  public CheckBox chkAdresa;
  public TextField tAdresa;
  public CheckBox chkTel;
  public TextField tTelefon;
  public CheckBox chkEmail;
  public TextField tEMail;
  public Button bOK;
  private URL location;
  private ResourceBundle resources;
  String query;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    chkIme.onActionProperty().addListener(new ChangeListener<EventHandler<ActionEvent>>() {
      @Override
      public void changed(ObservableValue<? extends EventHandler<ActionEvent>> observable,
          EventHandler<ActionEvent> oldValue, EventHandler<ActionEvent> newValue) {
        System.out.println(newValue);
      }
    });
  }


  public void saveFilters(ActionEvent actionEvent) {
    stringBuilder();
    Stage stage = (Stage) bOK.getScene().getWindow();
    stage.close();
  }

  private void stringBuilder() {
    query = String.format("SELECT * FROM users WHERE ime LIKE '%s%%'", tIme.getText());
    if (chkNazivFirme.isSelected()) {
      query = String.format("%s AND nazivFirme LIKE '%s%%'", query, tNazivFirme.getText());
    }
    if (chkMesto.isSelected()) {
      query = String
          .format("%s AND mesto LIKE '%s%%' OR mestoRacuna LIKE '%s%%'", query, tMesto.getText(),
              tMesto.getText());
    }
    if (chkAdresa.isSelected()) {
      query = String
          .format("%s AND adresa LIKE '%s%%' OR adresaRacuna LIKE '%s%%'", query, tAdresa.getText(),
              tAdresa.getText());
    }
    if (chkTel.isSelected()) {
      query = String.format("%s AND telMobilni LIKE '%s%%' OR telFiksni LIKE '%s%%'", query,
          tTelefon.getText(), tTelefon.getText());
    }
    if (chkEmail.isSelected()) {
      query = String.format("%s AND email LIKE '%s%%'", query, tMesto.getText());
    }

  }
}
