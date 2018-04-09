package net.yuvideo.jgemstone.client.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.HTMLEditor;
import net.yuvideo.jgemstone.client.classes.*;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by zoom on 11/7/16.
 */
public class UgovoriController implements Initializable {
    public HTMLEditor htmlUgovorEdit;
    public TableView<ugovori_types> tblUgovori;
    public TableColumn cId;
    public TableColumn cBr;
    public TableColumn cNaziv;
    public Button bDeleteUgovor;
    public Button bEditUgovor;
    public Button bNovUgovor;
    public Client client;
    private ResourceBundle resources;
    private URL location;
    private JSONObject jObj;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        cId.setCellValueFactory(new PropertyValueFactory<ugovori_types, Integer>("id"));
        cBr.setCellValueFactory(new PropertyValueFactory<ugovori_types, Integer>("br"));
        cNaziv.setCellValueFactory(new PropertyValueFactory<ugovori_types, String>("Naziv"));

    }

    public void set_datas() {
        jObj = new JSONObject();
        jObj.put("action", "get_ugovori");
        jObj = client.send_object(jObj);

        ObservableList<ugovori_types> ugovoryObserv;
        ArrayList<ugovori_types> ugovoriArr;
        ugovori_types ugovori;
        JSONObject jObjUgovori;

        ugovoriArr = new ArrayList();
        for (int i = 0; i < jObj.length(); i++) {
            jObjUgovori = (JSONObject) jObj.get(toString().valueOf(i));
            ugovori = new ugovori_types();
            ugovori.setId(jObjUgovori.getInt("idUgovora"));
            ugovori.setNaziv(jObjUgovori.getString("nazivUgovora"));
            if (jObj.has("textUgovora"))
                ugovori.setTextUgovora(jObj.getString("textUgovora"));
            ugovoriArr.add(ugovori);

        }

        ugovoryObserv = FXCollections.observableArrayList(ugovoriArr);
        tblUgovori.setItems(ugovoryObserv);

    }

    public void deleteUgovor(ActionEvent actionEvent) {
        if (tblUgovori.getSelectionModel().getSelectedIndex() == -1) {
            NotifyUser.NotifyUser("Upozorenje", "Izaberite ugovor", 1);
            return;
        }

        Optional<ButtonType> brisanje_ugovora = AlertUser.yesNo("Brisanje ugovora", "Da li ste sigurni da želite da izbrišete template ugovora?");
        if (brisanje_ugovora.get().equals(AlertUser.NE)) return;

        ugovori_types ug;
        ug = tblUgovori.getSelectionModel().getSelectedItem();

        jObj = new JSONObject();
        jObj.put("action", "delete_ugovor");
        jObj.put("ugovorId", ug.getId());
        jObj = client.send_object(jObj);

        set_datas();

    }

    public void gotoEditor(ActionEvent actionEvent) {
        showEditor(2);
    }

    public void gotoEditorNov(ActionEvent actionEvent) {
        showEditor(1);
    }

    //TYPE 1=new 2=edit
    private void showEditor(int type) {

        if (type == 2)
            if (tblUgovori.getSelectionModel().getSelectedIndex() == -1) {
                NotifyUser.NotifyUser("Upozorenje", "Izaberite ugovor", 1);
                return;
            }

        String EditorType;
        if (type == 1) {
            EditorType = "Nov Ugovor";
        } else {
            EditorType = "Izmena Ugovora";
        }


        NewInterface ugovoriEditInterface = new NewInterface("fxml/UgovoriEdit.fxml", EditorType, resources);
        UgovoriEditController ugovoriEditController = ugovoriEditInterface.getLoader().getController();
        ugovoriEditController.client = this.client;
        ugovoriEditController.Ugovor = tblUgovori.getSelectionModel().getSelectedItem();
        ugovoriEditController.type = type;
        if (type == 2)
            ugovoriEditController.show_Data();
        ugovoriEditInterface.getStage().showAndWait();
        set_datas();

    }
}
