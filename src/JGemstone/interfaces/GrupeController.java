package JGemstone.interfaces;

import JGemstone.classes.Client;
import JGemstone.classes.Groups;
import JGemstone.classes.NewInterface;
import JGemstone.classes.messageS;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;




/**
 * Created by zoom on 8/29/16.
 */
public class GrupeController implements Initializable {
    public Client client;
    

    public Button bNovaGrupa;

    public TextField tSearchGroup;
    public Button bTrazi;
    public TableView tableGroup;
    public TableColumn cID;
    public TableColumn cNaziv;
    public TableColumn cCena;
    public TableColumn cOpis;
    public MenuItem bIzmena;
    public MenuItem bmDeleteGroup;
    public Logger LOGGER = LogManager.getLogger("GROUPS");
    String resoruceFXML;
    Stage stage;
    private Groups group;
    private ResourceBundle resources;
    private messageS mess;
    private ArrayList<Groups> groups;

    //JSON
    private JSONObject jObj = new JSONObject();
    private JSONObject jGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.resources = resources;
    }

    public void setTableGroup(String groupName) {
        ObservableList<Groups> data = FXCollections.observableArrayList(get_groups_table_list(groupName));
        tableGroup.setEditable(true);
        cID.setCellValueFactory(new PropertyValueFactory<Groups, Integer>("id"));
        cNaziv.setCellValueFactory(new PropertyValueFactory<Groups, String>("GroupName"));
        cCena.setCellValueFactory(new PropertyValueFactory<Groups, Double>("Cena"));
        cOpis.setCellValueFactory(new PropertyValueFactory<Groups, String>("Opis"));
        tableGroup.setItems(data);
    }

    private ArrayList<Groups> get_groups_table_list(String groupName) {
        jObj = new JSONObject();
        jObj.put("action", "get_groups");
        jObj.put("groupName", groupName);

        jObj= client.send_object(jObj);

        groups = new ArrayList<Groups>();
        jGroup = new JSONObject();
        for(int i=0; i<jObj.length();i++) {
            jGroup = (JSONObject) jObj.get(String.valueOf(i));
            group = new Groups();
            group.setId(jGroup.getInt("id"));
            group.setGroupName(jGroup.getString("groupname"));
            group.setCena(jGroup.getDouble("cena"));
            group.setBr(i);
            group.setPrepaid(jGroup.getInt("prepaid"));
            group.setOpis(jGroup.getString("opis"));
            groups.add(group);
        }

        return groups;
    }



    public void newGroup(ActionEvent actionEvent) {
        resoruceFXML = "/JGemstone/resources/fxml/NovaGrupa.fxml";
        NewInterface newGroupInterface = new NewInterface(380, 370, resoruceFXML, "Nova Grupa", resources);
        NovaGrupaController newGroupController = newGroupInterface.getLoader().getController();
        newGroupController.client = client;
        newGroupInterface.getStage().showAndWait();
        setTableGroup("");



    }

    public void izmenaGroups(ActionEvent actionEvent) {
        if (tableGroup.getSelectionModel().getSelectedIndex() == -1) {
            //Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + "no user selected" + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nije izabran ni jedna grupa", ButtonType.OK);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("GREŠKA!");
            alert.initOwner(stage);
            alert.showAndWait();
            System.out.println(alert.getButtonTypes());
            if (alert.getResult() == ButtonType.YES) {
                //do stuff
            }
            return;
        }
        group = (Groups) tableGroup.getSelectionModel().getSelectedItem();


        resoruceFXML = "/JGemstone/resources/fxml/EditGrupa.fxml";
        NewInterface newGroupEditInterface = new NewInterface(0, 0, resoruceFXML, "Izmena Grupa", resources);
        EditGroupController editGroupController = newGroupEditInterface.getLoader().getController();
        editGroupController.client = client;

        editGroupController.show_group(group.getId());
        newGroupEditInterface.getStage().showAndWait();
        setTableGroup(tSearchGroup.getText());


    }

    public void traziGroupe(ActionEvent actionEvent) {
        setTableGroup(tSearchGroup.getText());
    }

    public void deleteGroup(ActionEvent actionEvent) {
        Alert alert;
        group = (Groups) tableGroup.getSelectionModel().getSelectedItem();

        if (tableGroup.getSelectionModel().getSelectedIndex() == -1) {
            //Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + "no user selected" + " ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert = new Alert(Alert.AlertType.WARNING, "Nije izabrana ni jedna grupa", ButtonType.OK);
            alert.setTitle("Upozorenje");
            alert.setHeaderText("GREŠKA!");
            alert.initOwner(stage);
            alert.showAndWait();
            System.out.println(alert.getButtonTypes());
            if (alert.getResult() == ButtonType.YES) {
                //do stuff
            }
            return;
        }else{
            ButtonType bYES = new ButtonType("Da", ButtonBar.ButtonData.YES);
            ButtonType bNO = new ButtonType("NE", ButtonBar.ButtonData.NO);

            alert = new Alert(Alert.AlertType.CONFIRMATION, "Da li ste sigurni da zelite da izbrišete grupu " + group.getGroupName(), bYES, bNO);
            alert.setTitle("Upozorenje");
            alert.setHeaderText(String.format("Brisanje grupe %s!", group.getGroupName()));
            alert.initOwner(stage);
            alert.showAndWait();
            System.out.println(alert.getButtonTypes());
            if (alert.getResult() == bNO) {
                return;
            }
        }




        jObj = new JSONObject();
        jObj.put("action", "delete_group");
        jObj.put("groupID", group.getId());
        jObj = client.send_object(jObj);
        LOGGER.log(Level.INFO, jObj.get("message").toString());

        setTableGroup("");
    }
}
