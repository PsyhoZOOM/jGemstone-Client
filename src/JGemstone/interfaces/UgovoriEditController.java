package JGemstone.interfaces;

import JGemstone.classes.Client;
import JGemstone.classes.ugovori_types;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by zoom on 11/16/16.
 */
public class UgovoriEditController implements Initializable{
    public Client client;
    public ResourceBundle resource;
    public HTMLEditor htmlUgovor;
    public Button bSnimi;
    public Button bClose;
    public TextField tNazivUgovora;
    int type;
    ugovori_types Ugovor;
    JSONObject jObj;
    Logger LOGGER = LogManager.getLogger("UGOVORI_EDIT");
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resource = resources;
        bClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

            }
        });

        bSnimi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                snimiUgovor(null);
                ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();

            }
        });



    }

    public void show_Data() {
        if (type == 2) {
            jObj = new JSONObject();
            jObj.put("action", "get_single_ugovor");
            jObj.put("idUgovora", Ugovor.getId());
            jObj = client.send_object(jObj);

            Ugovor = new ugovori_types();
            Ugovor.setId(jObj.getInt("idUgovora"));
            Ugovor.setNaziv(jObj.getString("nazivUgovora"));
            Ugovor.setTextUgovora(jObj.getString("textUgovora"));

            htmlUgovor.setHtmlText(Ugovor.getTextUgovora());
            tNazivUgovora.setText(Ugovor.getNaziv());


        }
    }

    public void snimiUgovor(ActionEvent actionEvent) {

        if (type == 1) {
            snimiNovUgovor();
        }
        if (type == 2) {
            snimiEditUgovor();
            show_Data();
        }

    }

    private void snimiNovUgovor() {

        jObj = new JSONObject();
        jObj.put("action", "add_new_ugovor");
        jObj.put("nazivUgovora", tNazivUgovora.getText());
        jObj.put("textUgovora", htmlUgovor.getHtmlText());

        jObj = client.send_object(jObj);
        LOGGER.info(jObj.getString("Message"));

    }

    private void snimiEditUgovor() {
        jObj = new JSONObject();
        jObj.put("action", "update_ugovor_temp");
        jObj.put("id", Ugovor.getId());
        jObj.put("text_ugovora", htmlUgovor.getHtmlText());
        jObj.put("naziv", tNazivUgovora.getText());


        jObj = client.send_object(jObj);
        LOGGER.info(jObj.getString("Message"));


    }


}
