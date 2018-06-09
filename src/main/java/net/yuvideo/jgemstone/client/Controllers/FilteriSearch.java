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
    Stage stage = (Stage) bOK.getScene().getWindow();
    stage.close();
  }
}
