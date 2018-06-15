package net.yuvideo.jgemstone.client.Controllers.Mape;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.MouseEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MVCArray;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Settings;
import netscape.javascript.JSObject;
import org.json.JSONObject;

public class MainMapController implements Initializable, MapComponentInitializedListener {

  public GoogleMapView gMapView;
  public Settings LocalSettings;
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
  Model model = new Model();
  private InfoWindow infoWindow;
  private InfoWindowOptions infoWindowOptions;
  private Map<String, Integer> mMarkers = new HashMap<String, Integer>();
  private Client client;

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
          String position = String.valueOf(selectedItem.getJSObject().getMember("latLong"));
          System.out.println(String.format("mOptions: %s, latLong: %s",
              selectedItem.getJSObject().getMember("markerOptions"),
              position
          ));

          JSObject jsObject = selectedItem.getJSObject();
          System.out.println(jsObject.toString());

        }
      }
    });


  }



  @Override
  public void mapInitialized() {

    mvcArray = new MVCArray();

    infoWindow = new InfoWindow();

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

    if (model.isAlive()) {
      model.interrupt();
    }
    model.start();

    Stage window = (Stage) gMapView.getScene().getWindow();
    window.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override
      public void handle(WindowEvent event) {
        if (!model.isInterrupted()) {
          model.shutDown();
        }
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
      JSObject jsObject = markerOptions.getJSObject();
      System.out.println(
          String.format("Title: %s, Label: %s, Lat: %s, Long: %s, latLng: %s"
              , jsObject.getMember("title")
              , jsObject.getMember("label")
              , jsObject.getMember("latitude")
              , jsObject.getMember("longitude")
              , jsObject.getMember("position"))
      );

      map.addUIEventHandler(marker, UIEventType.click, (JSObject objec) -> {
        LatLong latLong3 = new LatLong((JSObject) objec.getMember("latLng"));
        if (removeMarker) {
          map.removeMarker(marker);
        }
        infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content(latLong3.toString());

        infoWindow.open(map, marker);
        infoWindow.setOptions(infoWindowOptions);
      });
    }


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

  public void setClient(Client client) {
    this.client = client;
  }

  public class Model extends Thread {

    boolean run = true;

    public Model() {
      setDaemon(true);
    }

    public void shutDown() {
      this.run = false;
    }

    @Override
    public void run() {
      while (run) {
        Platform.runLater(new Runnable() {
          @Override
          public void run() {
            reloadData(null);
          }
        });
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
