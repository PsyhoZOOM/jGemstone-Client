package net.yuvideo.jgemstone.client.Controllers.Mape;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.MouseEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javax.swing.plaf.basic.BasicTreeUI.MouseHandler;
import net.yuvideo.jgemstone.client.classes.Client;

public class MainMapController implements Initializable, MapComponentInitializedListener {

  public GoogleMapView gMapView;
  public Client client;
  private URL location;
  private GoogleMap map;
  private MapOptions mapOptions;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.location = location;
    gMapView.addMapInializedListener(() -> cofigureMap());
  }

  @Override
  public void mapInitialized() {

    map.clearMarkers();
    gMapView.onMouseClickedProperty().addListener(
        new ChangeListener<EventHandler<? super MouseEvent>>() {
          @Override
          public void changed(
              ObservableValue<? extends EventHandler<? super MouseEvent>> observable,
              EventHandler<? super MouseEvent> oldValue,
              EventHandler<? super MouseEvent> newValue) {
            System.out.println(newValue);
          }
        });


  }

  private void cofigureMap() {
    mapOptions = new MapOptions();
    mapOptions.center(new LatLong(44.378789, 21.417001))
        .mapType(MapTypeIdEnum.ROADMAP)
        .mapTypeControl(true)
        .overviewMapControl(true)
        .panControl(true)
        .rotateControl(false)
        .zoomControl(true)
        .zoom(18);

    map = gMapView.createMap(mapOptions);

    map.addMouseEventHandler(UIEventType.click, new MouseEventHandler() {
      @Override
      public void handle(GMapMouseEvent mouseEvent) {
        System.out.println(mouseEvent.getLatLong());
        map.addMarker(new Marker(new MarkerOptions().position(mouseEvent.getLatLong())));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(mouseEvent.getLatLong());
        markerOptions.label("Marker :)");
        markerOptions.title("TITLE: ");
        markerOptions.icon(
            "https://upload.wikimedia.org/wikipedia/commons/thumb/1/13/Disc_Plain_red.svg/32px-Disc_Plain_red.svg.png");
        markerOptions.visible(true);
        Animation animation = Animation.BOUNCE;
        markerOptions.animation(animation);
        Marker marker = new Marker(markerOptions);
        marker.setOptions(markerOptions);
        marker.setTitle("TIEL@");
        map.removeMarker(marker);
        marker.setOptions(markerOptions);
      }
    });
  }

  public void setData() {
  }
}
