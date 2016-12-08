package JGemstone.interfaces;

import JGemstone.classes.Client;
import JGemstone.classes.ugovori_types;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.HTMLEditor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zoom on 11/7/16.
 */
public class UgovoriController implements Initializable {
    public Button bEditUgovorText;
    public Button bAddUgovor;
    public TextField tNaziv;
    public HTMLEditor htmlUgovorEdit;
    public TableView<ugovori_types> tblUgovori;
    public TableColumn cId;
    public TableColumn cBr;
    public TableColumn cNaziv;
    public Button bDeleteUgovor;

    private ResourceBundle resources;
    private URL location;
    private JSONObject jObj;
    private ArrayList<ugovori_types> ugovoriArr;
    private ugovori_types ugovori;
    private JSONObject jObjUgovori;
    public Client client;
    private Logger LOGGER = LogManager.getLogger("UGOVORI");
    private ObservableList<ugovori_types> ugovoryObserv;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
        tNaziv.setEditable(true);
        cId.setCellValueFactory(new PropertyValueFactory<ugovori_types, Integer>("id"));
        cBr.setCellValueFactory(new PropertyValueFactory<ugovori_types, Integer>("br"));
        cNaziv.setCellValueFactory(new PropertyValueFactory<ugovori_types, String>("Naziv"));
    }


    public void snimiNovUgovor(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "add_new_ugovor");
        jObj.put("nazivUgovora", tNaziv.getText());
        jObj.put("textUgovora", htmlUgovorEdit.getHtmlText());

        jObj = client.send_object(jObj);

        LOGGER.info(jObj.getString("Message"));
        set_datas();


    }

    public void set_datas() {
        jObj = new JSONObject();
        jObj.put("action", "get_ugovori");
        jObj = client.send_object(jObj);

        ugovoriArr = new ArrayList();
        for (int i = 0; i < jObj.length(); i++) {
            jObjUgovori = (JSONObject) jObj.get(toString().valueOf(i));
            ugovori = new ugovori_types();
            ugovori.setId(jObjUgovori.getInt("idUgovora"));
            ugovori.setBr(i + 1);
            ugovori.setNaziv(jObjUgovori.getString("nazivUgovora"));
            if (jObj.has("textUgovora"))
                ugovori.setTextUgovora(jObj.getString("textUgovora"));
            ugovoriArr.add(ugovori);

        }

        ugovoryObserv = FXCollections.observableArrayList(ugovoriArr);
        tblUgovori.setItems(ugovoryObserv);


    }


    public void deleteUgovor(ActionEvent actionEvent) {
        ugovori_types ug;
        ug = tblUgovori.getSelectionModel().getSelectedItem();
        LOGGER.info(ug.getNaziv());

        jObj = new JSONObject();
        jObj.put("action", "delete_ugovor");
        jObj.put("ugovorId",  ug.getId());
        jObj = client.send_object(jObj);
        LOGGER.info(jObj.get("Message"));

        set_datas();

    }
}
