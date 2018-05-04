package net.yuvideo.jgemstone.client.classes;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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

  private String resourceFXML;
  private ResourceBundle resources;

  public NewInterface(String resourceFXML, String title, ResourceBundle resources) {
    this.width = width;
    this.height = height;
    this.resourceFXML = resourceFXML;
    this.title = title;
    this.resources = resources;
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


  public void set_interface() {
    loader = new FXMLLoader(ClassLoader.getSystemResource(resourceFXML), resources);

    try {
      root = (Parent) loader.load();
      scene = new Scene(root);
      stage = new Stage();
      stage.initModality(Modality.APPLICATION_MODAL);
      stage.initStyle(StageStyle.DECORATED);
      stage.setResizable(true);

      stage.setScene(scene);
      stage.setTitle(title);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
