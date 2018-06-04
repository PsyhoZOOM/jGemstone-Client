package net.yuvideo.jgemstone.client.classes;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;


/**
 * Created by zoom on 2/1/17.
 */
public class AlertUser {

  public static ButtonType DA = new ButtonType("DA", ButtonBar.ButtonData.YES);
  public static ButtonType NE = new ButtonType("NE", ButtonBar.ButtonData.CANCEL_CLOSE);
  private static Alert alert;

  public static void info(String title, String content) {
    alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setContentText(content);
    alert.setHeaderText("INFORMACIJA");
    alert.getDialogPane().getStylesheets()
        .add(ClassLoader.getSystemResource("css/Main.css").toExternalForm());
    alert.showAndWait();
  }

  public static void error(String title, String content) {
    alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(title);
    alert.setContentText(content);
    alert.setHeaderText("GREŠKA");
    alert.getDialogPane().getStylesheets()
        .add(ClassLoader.getSystemResource("css/Main.css").toExternalForm());
    alert.showAndWait();
  }

  public static void warrning(String title, String conent) {
    alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setContentText(conent);
    alert.setHeaderText("UPOZORENJE");
    alert.getDialogPane().getStylesheets()
        .add(ClassLoader.getSystemResource("css/Main.css").toExternalForm());
    alert.showAndWait();

  }

  public static boolean yesNo(String title, String content) {
    alert = new Alert(Alert.AlertType.WARNING, content, NE, DA);
    alert.setHeaderText(title);
    alert.setContentText(content);
    alert.setHeaderText("CHOOSE YOUR DESTINY");
    alert.getDialogPane().getStylesheets()
        .add(ClassLoader.getSystemResource("css/Main.css").toExternalForm());

    Optional<ButtonType> result = alert.showAndWait();

    if (result.isPresent() && result.get() == AlertUser.DA) {
      return true;
    }

    return false;

  }
}
