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
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.classes.Client;
import netscape.javascript.JSObject;
import org.json.JSONObject;

public class MainMapController implements Initializable, MapComponentInitializedListener {

  public GoogleMapView gMapView;
  public Client client;
  public ListView<Marker> lView;
  public ToggleButton bRemoveMarker;
  private URL location;
  private GoogleMap map;
  private MapOptions mapOptions;
  LatLong latLong;
  LatLong latLong1;
  LatLong latLong2;
  int clickTimes = 0;
  MVCArray mvcArray;
  boolean removeMarker = false;
  ArrayList<Marker> markerList = new ArrayList<>();

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.location = location;
    gMapView.setDisableDoubleClick(true);
    gMapView.addMapInializedListener(this);

    lView.setCellFactory(new Callback<ListView<Marker>, ListCell<Marker>>() {
      @Override
      public ListCell<Marker> call(ListView<Marker> param) {
        ListCell<Marker> lcell = new ListCell<Marker>() {
          @Override
          protected void updateItem(Marker item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
              setText("");
            } else {
              setText(item.getTitle());
            }

          }
        };

        return lcell;
      }
    });

    lView.setOnMouseClicked(new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent event) {
        if (event.getClickCount() == 2) {
          MarkerOptions markerOptions;

          Marker selectedItem = lView.getSelectionModel().getSelectedItem();

        }
      }
    });


  }

  @Override
  public void mapInitialized() {

    mvcArray = new MVCArray();

    latLong = new LatLong(44.378789, 21.417001);
    mapOptions = new MapOptions();
    mapOptions.center(latLong)
        .mapType(MapTypeIdEnum.ROADMAP)
        .mapTypeControl(true)
        .overviewMapControl(true)
        .panControl(true)
        .rotateControl(false)
        .zoomControl(true)
        .streetViewControl(false)
        .overviewMapControl(false)
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
          //addLine();
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

    runInBack();
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
    markerList = new ArrayList<>();
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
      markerList.add(marker);

      map.addUIEventHandler(marker, UIEventType.click, (JSObject objec) -> {
        LatLong latLong3 = new LatLong((JSObject) objec.getMember("latLng"));
        System.out.println(latLong.toString() + "JSOBje");
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(latLong3);
        Marker mar = new Marker(markerOptions1);
        if (removeMarker) {
          map.removeMarker(marker);
        }
      });
    }
  }

  public void runInBack() {
    Task<Void> task = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        while (true) {
          Platform.runLater(new Runnable() {
            @Override
            public void run() {
              reloadData(null);
              System.out.println("UPDATED");
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }
          });
          return null;
        }
      }
    };

    task.run();
  }


  public void showList(ActionEvent actionEvent) {
    lView.getItems().removeAll();
    lView.getItems().clear();
    lView.getItems().addAll(markerList);
  }

  public void removeMarker(ActionEvent actionEvent) {
    if (bRemoveMarker.isSelected()) {
      removeMarker = true;
    } else {
      removeMarker = false;
    }
  }
}
