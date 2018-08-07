package net.yuvideo.jgemstone.client.Controllers.Administration;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTabPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import net.yuvideo.jgemstone.client.classes.Client;
import org.json.JSONObject;

public class Administration implements Initializable {


  public JFXProgressBar prgBarUpdate;
  public Label lNumberOnline;
  public JFXTabPane tabCenter;
  private Client client;


  private int counter = 0;
  private boolean loop = true;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    updateOnlineUsersSum();
  }

  private void updateOnlineUsersSum() {
    Task task = new Task() {
      @Override
      protected Object call() throws Exception {
        while (loop) {
          updateProgress(counter, 100);
          counter++;
          if (counter == 100) {
            counter = 0;
            updateOnlineUsers();
          }
          Thread.sleep(1000);
        }
        return null;
      }
    };

    prgBarUpdate.progressProperty().bind(task.progressProperty());
    Thread thread = new Thread(task);
    thread.start();

  }

  private void updateOnlineUsers() {
    JSONObject object = new JSONObject();
    object.put("action", "getMikrotikOnlineUsersSum");
    object = client.send_object(object);
    String onlineSum = object.getString("usersStatus");
    counter = 0;
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        lNumberOnline.setText(onlineSum);
      }
    });


  }

  public void setClient(Client client) {
    this.client = client;
  }

  public void updateUsers(ActionEvent actionEvent) {
    updateOnlineUsers();
  }

  public void showOnlineUsers(ActionEvent actionEvent) {
    Tab onlineTab = new Tab("ONLINE KORISNICI");
    JFXButton b = new JFXButton("asas");
    onlineTab.setContent(b);
    tabCenter.getTabs().add(onlineTab);
  }

  public void stopUpdate() {
    this.loop = false;
  }

  public void showSearchUsers(ActionEvent actionEvent) {
  }

  public void showIzvestajPotrosnje(ActionEvent actionEvent) {
  }

  public void showWifiFinder(ActionEvent actionEvent) {
  }
}
