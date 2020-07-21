package net.yuvideo.jgemstone.client.Controllers.Mape;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.MouseEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import de.micromata.opengis.kml.v_2_2_0.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PrintResolution;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Settings;
import netscape.javascript.JSObject;

import java.io.File;
import java.net.URL;
import java.util.*;

public class MainMapController implements Initializable, MapComponentInitializedListener {

    public GoogleMapView gMapView;
    public Settings LocalSettings;
    public ListView<Marker> lView;
    public ToggleButton bRemoveMarker;
    public Button bPrint;
    public AnchorPane gmapNode;
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
        gMapView.setDisableDoubleClick(true);
    gMapView.addMapInializedListener(this);
    gMapView.setKey("AIzaSyCJkNK8HIFNxbc8Ynwq_eI2uXHTmginiV4");
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


    reloadData(null);
      //   autoReload();

  }

  private void autoReload() {
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        while (true) {
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
    });
      thread.start();
  }


    private void addLine(List<Coordinate> coordinates) {
    /*example
    mvcArray.push(new LatLong(latLong1.getLatitude(), latLong1.getLongitude()));
    mvcArray.push(new LatLong(latLong2.getLatitude(), latLong2.getLongitude()));
    map.addMapShape(new Polyline(new PolylineOptions().draggable(true).editable(true).path(mvcArray)
        .strokeColor("#fc4c02")));

     */
        mvcArray = new MVCArray();
        for (Coordinate coordinate : coordinates) {
            mvcArray.push(new LatLong(coordinate.getLatitude(), coordinate.getLongitude()));
        }

        map.addMapShape(new Polyline(new PolylineOptions().draggable(false).editable(false).path(mvcArray)));


    }

  public void setData() {
    for (int i = 0; i < mvcArray.getLength(); i++) {
      System.out.println(mvcArray.getAt(i));
    }

  }


    public void reloadData(ActionEvent actionEvent) {
        markerList = new ArrayList<>();
        map.clearMarkers();
    /*
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

     */

        parseKml();

    }

    public void parseKml() {
        Kml kml = Kml.unmarshal(new File("/home/zoom/doc.kml"));
        Feature feature = kml.getFeature();
        parseFeature(feature);
    }

    private void parseFeature(Feature feature) {
        if (feature != null) {
            if (feature instanceof Document) {
                Document document = (Document) feature;
                List<Feature> featureList = document.getFeature();
                for (Feature documentFeature : featureList) {
                    if (documentFeature instanceof Placemark) {
                        Placemark placemark = (Placemark) documentFeature;
                        getPlacemark(placemark);
                    } else if (documentFeature instanceof Folder) {
                        getFutureList(((Folder) documentFeature).getFeature());
                    }
                }
            }
        }
    }

    private void getFutureList(List<Feature> features) {
        for (Feature f : features) {
            if (f instanceof Folder) {
                getFutureList(((Folder) f).getFeature());
            } else if (f instanceof Placemark) {
                getPlacemark((Placemark) f);
            } else {
                System.out.println("NOT HERE");
            }

        }
    }


    private void getPlacemark(Placemark placemark) {
        Geometry geometry = placemark.getGeometry();
        parseGeometry(geometry, placemark);
    }

    private void parseGeometry(Geometry geometry, Placemark placemark) {
        if (geometry != null) {
            if (geometry instanceof Polygon) {
                Polygon polygon = (Polygon) geometry;
                Boundary outerBoundaryIs = polygon.getOuterBoundaryIs();
                if (outerBoundaryIs != null) {
                    LinearRing linearRing = outerBoundaryIs.getLinearRing();
                    if (linearRing != null) {
                        List<Coordinate> coordinates = linearRing.getCoordinates();
                        if (coordinates != null) {
                            for (Coordinate coordinate : coordinates) {
                                //               results << parseCoordinate(coordinate);
                                parseCoordinate(coordinate, placemark);
                            }
                        }
                    }
                }
            } else if (geometry instanceof LineString) {
                LineString lineString = (LineString) geometry;
                List<Coordinate> coordinates = lineString.getCoordinates();
                if (coordinates != null) {
                    for (Coordinate coordinate : coordinates) {
                        //           results <<  parseCoordinate(coordinate);
                        //   parseCoordinate(coordinate, name);
                        addLine(coordinates);
                    }
                }
            } else if (geometry instanceof Point) {
                Point point = (Point) geometry;
                List<Coordinate> coordinates = point.getCoordinates();
                for (Coordinate coordinate : coordinates) {
                    parseCoordinate(coordinate, placemark);
                }
            } else {
                System.out.println("NESTO DRUGO");
            }
        }
    }

    private void parseCoordinate(Coordinate coordinate, Placemark placemark) {
        if (coordinate != null) {
            //     System.out.println("Longitude: " +  coordinate.getLongitude());
            //     System.out.println("Latitude : " +  coordinate.getLatitude());
            //     System.out.println("Altitude : " +  coordinate.getAltitude());
            ///     System.out.println("");
            LatLong latLong = new LatLong(coordinate.getLatitude(), coordinate.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.label(placemark.getName());
            markerOptions.position(latLong);
            map.addMarker(new Marker(markerOptions));
            mapOptions.overviewMapControl(true);
            mapOptions.mapTypeControl(true);

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


    public void printMap() {
        boolean fail = false;
        Printer printer = null;
        for (Printer pr : Printer.getAllPrinters()) {
            System.out.println(pr.getName());
            if (pr.getName().contains("267"))
                printer = pr;
        }
        PrinterJob printerJob = PrinterJob.createPrinterJob(printer);
        boolean printerDialog = printerJob.showPrintDialog(bPrint.getScene().getWindow());
        if (!printerDialog) return;

        if (printerJob.getJobSettings() == null) return;

        PageLayout pageLayout = printerJob.getJobSettings().getPageLayout();

        PrintResolution printResolution = printerJob.getJobSettings().getPrintResolution();


        boolean printed = false;
        printed = printerJob.printPage(pageLayout, gmapNode);


        if (printed) {
            System.out.println("printer");
        } else {
            System.out.println("failed print..");
            fail = true;
        }

        if (!fail) {
            printerJob.endJob();
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

    }


}
