package net.yuvideo.jgemstone.client.Controllers.Mape;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.MouseEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MVCArray;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapShape;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.shapes.Circle;
import com.lynden.gmapsfx.shapes.CircleOptions;
import com.lynden.gmapsfx.shapes.MapShapeOptions;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import net.yuvideo.jgemstone.client.classes.Client;
import netscape.javascript.JSObject;
import org.json.JSONObject;

public class MainMapController implements Initializable, MapComponentInitializedListener {

  public GoogleMapView gMapView;
  public Client client;
  private URL location;
  private GoogleMap map;
  private MapOptions mapOptions;
  LatLong latLong;
  LatLong latLong1;
  LatLong latLong2;
  int clickTimes = 0;
  MVCArray mvcArray;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.location = location;
    gMapView.setDisableDoubleClick(true);
    gMapView.addMapInializedListener(this);


  }

  @Override
  public void mapInitialized() {

    mvcArray = new MVCArray();

    latLong = new LatLong(44.378789, 21.417001);
    mapOptions = new MapOptions();
    mapOptions.center(latLong)
        .mapType(MapTypeIdEnum.TERRAIN)
        .mapTypeControl(true)
        .overviewMapControl(true)
        .panControl(true)
        .rotateControl(false)
        .zoomControl(true)
        .zoom(18);

    map = gMapView.createMap(mapOptions);

    map.addMouseEventHandler(UIEventType.mouseup, new MouseEventHandler() {
      @Override
      public void handle(GMapMouseEvent mouseEvent) {
        System.out.println(clickTimes);
        if (clickTimes == 0) {
          latLong1 = mouseEvent.getLatLong();
        }
        if (clickTimes == 1) {
          latLong2 = mouseEvent.getLatLong();
          addLine();
          latLong1 = mouseEvent.getLatLong();
          System.out.println("line added " + latLong1 + " " + latLong2);
          clickTimes = 0;
          setData();

        }
        System.out.println("Lat1: " + latLong1);
        System.out.println("Lat2: " + latLong2);
        clickTimes++;
      }

    });
  }


  private void addLine() {
    mvcArray.push(new LatLong(latLong1.getLatitude(), latLong1.getLongitude()));
    mvcArray.push(new LatLong(latLong2.getLatitude(), latLong2.getLongitude()));
    map.addMapShape(new Polyline(new PolylineOptions().draggable(true).editable(true).path(mvcArray)
        .strokeColor("#fc4c02")));
  }

  public void setData() {
    for (int i = 0; i < mvcArray.getLength(); i++) {
      System.out.println(mvcArray.getAt(i));
    }

  }


  public void reloadData(ActionEvent actionEvent) {
    map.clearMarkers();
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("action", "getAllClientsLocations");
    jsonObject = client.send_object(jsonObject);
    for (int i = 0; i < jsonObject.length(); i++) {
      JSONObject obj = jsonObject.getJSONObject(String.valueOf(i));
      MarkerOptions markerOptions = new MarkerOptions();
      LatLong latLong = new LatLong(obj.getDouble("latitude"), obj.getDouble("longitude"));

      markerOptions.position(latLong);
      markerOptions.label(obj.getString("identification"));
      markerOptions.title(obj.getString("identification") + " " + obj.getString("lastUpdateTime"));
      Marker marker = new Marker(markerOptions);
      map.addMarker(marker);
    }


  }
}
