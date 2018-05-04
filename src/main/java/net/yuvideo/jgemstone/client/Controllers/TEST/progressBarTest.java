package net.yuvideo.jgemstone.client.Controllers.TEST;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

public class progressBarTest implements Initializable {

  public ProgressBar pprogress;
  public Slider sslder;
  private URL location;
  private ResourceBundle resources;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;

    sslder.onMouseMovedProperty()
        .addListener(new ChangeListener<EventHandler<? super MouseEvent>>() {
          @Override
          public void changed(
              ObservableValue<? extends EventHandler<? super MouseEvent>> observable,
              EventHandler<? super MouseEvent> oldValue,
              EventHandler<? super MouseEvent> newValue) {
            pprogress.setProgress(sslder.getValue());
          }
        });

    sslder.valueProperty().addListener(new ChangeListener<Number>() {
      @Override
      public void changed(ObservableValue<? extends Number> observable, Number oldValue,
          Number newValue) {
        pprogress.setProgress((Double) newValue / 100);
      }
    });
  }
}
