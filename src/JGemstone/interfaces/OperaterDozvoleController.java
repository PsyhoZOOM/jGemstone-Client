package JGemstone.interfaces;

import JGemstone.classes.Client;
import JGemstone.classes.OperatersDozvole;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zoom on 1/31/17.
 */
public class OperaterDozvoleController implements Initializable {
    public Client client;

    public CheckBox cUserSearch;
    public CheckBox cNewUser;
    public CheckBox cEditUser;
    public CheckBox cUplateUser;
    public CheckBox cEditOper;
    public int operaterID;
    ResourceBundle resource;
    URL location;
    Logger LOGGER = LogManager.getLogger("OPER_PERMISSIONS");
    JSONObject jObj;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resource = resources;

    }


    public void searchUser(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "editOperPermission");
        jObj.put("operaterID", operaterID);
        jObj.put("dozvola", "userSearch");
        jObj.put("value", cUserSearch.isSelected());

        jObj = client.send_object(jObj);

        LOGGER.info(jObj.getString("Message"));
    }

    public void newUser(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "editOperPermission");
        jObj.put("operaterID", operaterID);
        jObj.put("dozvola", "newUser");
        jObj.put("value", cNewUser.isSelected());

        jObj = client.send_object(jObj);

        LOGGER.info(jObj.getString("Message"));
    }

    public void editUser(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "editOperPermission");
        jObj.put("operaterID", operaterID);
        jObj.put("dozvola", "editUser");
        jObj.put("value", cEditUser.isSelected());

        jObj = client.send_object(jObj);

        LOGGER.info(jObj.getString("Message"));
    }

    public void uplateUser(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "editOperPermission");
        jObj.put("operaterID", operaterID);
        jObj.put("dozvola", "uplateUser");
        jObj.put("value", cUplateUser.isSelected());

        jObj = client.send_object(jObj);

        LOGGER.info(jObj.getString("Message"));
    }

    public void editOper(ActionEvent actionEvent) {
        jObj = new JSONObject();
        jObj.put("action", "editOperPermission");
        jObj.put("operaterID", operaterID);
        jObj.put("dozvola", "editOper");
        jObj.put("value", cEditOper.isSelected());

        jObj = client.send_object(jObj);

        LOGGER.info(jObj.getString("Message"));
    }


    public void show_data() {
        jObj = new JSONObject();
        jObj.put("action", "getOperPermissions");
        jObj.put("operaterID", operaterID);
        jObj = client.send_object(jObj);

        ArrayList<OperatersDozvole> operPerm = new ArrayList();
        JSONObject operObj;
        OperatersDozvole oper;

        for (int i = 0; i < jObj.length(); i++) {
            oper = new OperatersDozvole();
            operObj = (JSONObject) jObj.get(String.valueOf(i));
            oper.setId(operObj.getInt("id"));
            oper.setDozvola(operObj.getString("dozvola"));
            oper.setOperaterID(operObj.getInt("operaterID"));
            oper.setValue(operObj.getBoolean("value"));
            operPerm.add(oper);

        }


        for (int i = 0; i < operPerm.size(); i++) {
            if (operPerm.get(i).getDozvola().equals("userSearch"))
                cUserSearch.setSelected(operPerm.get(i).getValue());
            if (operPerm.get(i).getDozvola().equals("newUser"))
                cNewUser.setSelected(operPerm.get(i).getValue());
            if (operPerm.get(i).getDozvola().equals("editUser"))
                cEditUser.setSelected(operPerm.get(i).getValue());
            if (operPerm.get(i).getDozvola().equals("uplateUser"))
                cUplateUser.setSelected(operPerm.get(i).getValue());
            if (operPerm.get(i).getDozvola().equals("editOper"))
                cEditOper.setSelected(operPerm.get(i).getValue());
        }


    }


}
