package JGemstone.interfaces;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 11/17/16.
 */
public class UgovoriPreviewController  implements Initializable{
    public HTMLEditor htmlEditUgovor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void closeUgovor(ActionEvent actionEvent) {
        Stage stage = (Stage) htmlEditUgovor.getScene().getWindow();
        stage.close();
    }

    public void printUgovor(ActionEvent actionEvent) {
    }

    public void snimiUgovor(ActionEvent actionEvent) {
    }
}
