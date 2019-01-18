package net.yuvideo.jgemstone.client.classes;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.yuvideo.jgemstone.client.ClientMain;

/**
 * Created by zoom on 8/29/16.
 */
public class NewInterface {

  private Parent root;
  private Scene scene;
  private Stage stage;
  private FXMLLoader loader;
  private int width;
  private int height;
  private String title;
  private boolean decorated = true;
  private boolean application = true;

  private String resourceFXML;
  private ResourceBundle resources;

  public NewInterface(String resourceFXML, String title, ResourceBundle resources,
      boolean decorated) {
    this.title = title;
    this.decorated = decorated;
    this.resourceFXML = resourceFXML;
    this.resources = resources;
    set_interface();
  }

  public NewInterface(String resourceFXML, String title, ResourceBundle resources) {
    this.width = width;
    this.height = height;
    this.resourceFXML = resourceFXML;
    this.title = title;
    this.resources = resources;
    set_interface();
  }

  public NewInterface(String resourceFXML, String title, ResourceBundle resources,
      boolean decorated, boolean application) {
    this.width = width;
    this.height = height;
    this.resourceFXML = resourceFXML;
    this.title = title;
    this.resources = resources;
    this.decorated = decorated;
    this.application = application;
    set_interface();
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public Stage getStage() {
    return stage;
  }

  public Parent getRoot() {
    return root;
  }

  public void setRoot(Parent root) {
    this.root = root;
  }

  public FXMLLoader getLoader() {
    return loader;
  }

  public void setLoader(FXMLLoader loader) {
    this.loader = loader;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getResourceFXML() {
    return resourceFXML;
  }

  public void setResourceFXML(String resourceFXML) {
    this.resourceFXML = resourceFXML;
  }

  public void setDecorated() {
    this.decorated = true;
  }

  public Scene getScene() {
    return scene;
  }

  public void setScene(Scene scene) {
    this.scene = scene;
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public void set_interface() {
    loader = new FXMLLoader(ClassLoader.getSystemResource(resourceFXML), resources);

    try {
      root = loader.load();
      stage = new Stage();
      stage.setTitle(title);
      scene = new Scene(root);
      scene.getStylesheets().add(ClientMain.cssTheme);
      if (application) {
        stage.initModality(Modality.APPLICATION_MODAL);
      } else {
        stage.initModality(Modality.WINDOW_MODAL);
      }
      if (decorated) {
        stage.initStyle(StageStyle.DECORATED);
      } else {
        stage.initStyle(StageStyle.UNDECORATED);
      }
      stage.setResizable(true);
      stage.setMaximized(true);

      stage.setScene(scene);


    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
