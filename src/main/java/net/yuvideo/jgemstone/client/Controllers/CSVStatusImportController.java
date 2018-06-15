package net.yuvideo.jgemstone.client.Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.BytesTo_KB_MB_GB_TB;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Settings;
import org.json.JSONObject;

public class CSVStatusImportController implements Initializable {

  public Label lImportCSV;
  public ProgressBar pProgress;
  public Button bClose;
  public Label lPerc;
  private URL location;
  private ResourceBundle resources;
  private Client client;
  DecimalFormat df = new DecimalFormat("0.##");
  private long maxPos = 0;
  private long curPos = 0;
  private boolean taskDone = false;
  private List<File> lf;
  public Settings LocalSettings;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.location = location;
    this.resources = resources;
    this.client = new Client(this.LocalSettings);
    bClose.setText("IMPORT");
    bClose.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        if (bClose.getText().equals("ZATVORI")) {
          exit();
        } else {
          startProgress(lf);
        }
      }
    });
  }

  private void exit() {
    Stage stage = (Stage) bClose.getScene().getWindow();
    stage.close();
  }

  public void CSVStatusImportController(Client client) {
    this.client = client;
  }

  public void setLf(List<File> lf) {
    this.lf = lf;
  }



  public void startProgress(List<File> lf) {

    bClose.setDisable(true);

    if (lf == null) {
      return;
    }

    this.lf = lf;

    Task<Void> progressTask = progressTask();
    pProgress.progressProperty().bind(progressTask.progressProperty());
    Thread thread = new Thread(progressTask);
    thread.start();


  }


  private Task<Void> progressTask() {
    Task<Void> progressTask = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        while (!taskDone) {
          maxPos = lf.size();
          int i = 1;
          for (File file : lf) {
            curPos = i;
            try {
              JSONObject jfileObj = new JSONObject();
              String content = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
              jfileObj.put("action", "add_CSV_FIX_Telefonija");
              jfileObj.put(file.getAbsoluteFile().getName(), content);

              Platform.runLater(new Runnable() {
                @Override
                public void run() {
                  updateProgress((double) curPos, (double) maxPos);
                  lImportCSV.setText(String.format("Naziv: %s, Veličina: %s", file.getName(),
                      BytesTo_KB_MB_GB_TB.getFormatedString((long) content.getBytes().length)));
                  lPerc.setText(String.format("%s%%", df.format(pProgress.getProgress() * 100)));
                }
              });
              jfileObj = client.send_object(jfileObj);
              if (jfileObj.has("ERROR")) {
                taskDone = true;
                AlertUser.error("GRESKA", jfileObj.getString("ERROR"));
                lPerc.setText("GREŠKA!!!");
              }
            } catch (FileNotFoundException e) {
              e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();
            }
            i++;
          }

          taskDone = true;
          Platform.runLater(new Runnable() {
            @Override
            public void run() {
              lPerc.setText("IMPORT CSV-a USPEŠAN");
              bClose.setText("ZATVORI");
              bClose.setDisable(false);
            }
          });
        }

        return null;
      }
    };
    return progressTask;
  }

}
