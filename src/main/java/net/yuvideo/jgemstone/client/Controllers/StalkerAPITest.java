package net.yuvideo.jgemstone.client.Controllers;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.json.JSONArray;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import net.yuvideo.jgemstone.client.classes.StalkerRestAPI2;

/**
 * Created by PsyhoZOOM@gmail.com on 7/20/17.
 */
public class StalkerAPITest implements Initializable {

    public TextField tMethod;
    public Text lLogin;
    public Text lMac;
    public Text lIP;
    public Text lAccBallance;
    public Text lAditionalService;
    public Text lLs;
    public Text lOnline;
    public Text lLastActive;
    public Text lVersion;
    public ComboBox<StalkerRestAPI2> cmbUsers;
    public net.yuvideo.jgemstone.client.classes.Client client;
    public Button bGet;
    public Button bGetUsers;
    WebResource webResource; //= apiClient.resource(APIURL);
    ClientResponse response; //= webResource.accept("application/json")
    private URL location;
    private ResourceBundle resources;
    private String APIURL = "http://blaster.yuvideo.net/stalker_portal/api/";
    private String UserName = "stalkerapi";
    private String Pass = "stalker_pass_api";
    private String AuthString = UserName + ":" + Pass;
    private String authStirngEnc = new BASE64Encoder().encode(AuthString.getBytes());
    private Client apiClient = Client.create();
    //.header("Authorization", "Basic" + authStirngEnc)
    //.get(ClientResponse.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        cmbUsers.setConverter(new StringConverter<StalkerRestAPI2>() {
            @Override
            public String toString(StalkerRestAPI2 object) {
                return object.getLogin();
            }

            @Override
            public StalkerRestAPI2 fromString(String string) {
                StalkerRestAPI2 api2 = new StalkerRestAPI2();
                api2.setLogin(string);
                return api2;
            }
        });

        cmbUsers.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<StalkerRestAPI2>() {
            @Override
            public void changed(ObservableValue<? extends StalkerRestAPI2> observable, StalkerRestAPI2 oldValue, StalkerRestAPI2 newValue) {
                StalkerRestAPI2 data = newValue;
                lLogin.setText(data.getLogin());
                lMac.setText(data.getMac());
                lIP.setText(data.getIp());
                lAccBallance.setText(data.getAccount_balance());
                lAditionalService.setText(data.getAdditional_service_on());
                lLs.setText(data.getLs());
                lOnline.setText(data.getOnline());
                lLastActive.setText(data.getLast_active());
                lVersion.setText(data.getVersion());
            }
        });
    }


    public void getAction(ActionEvent actionEvent) {
        JSONObject jObj = new JSONObject();
        jObj.put("action", "getIPTVPakets");

	        jObj = client.send_object(jObj);
        System.out.println(jObj.toString());

    }

    public void getAction2(ActionEvent actionEvent) {
        System.out.println("ENCODED PASS: " + authStirngEnc);

        String method = tMethod.getText();

        webResource = apiClient.resource(APIURL + method);
        response = webResource.accept("appication/json")
                .header("Authorization", "Basic " + authStirngEnc)
                .get(ClientResponse.class);

        System.out.println("RESPONSE CODE: " + response.getStatus());

        if (response.getStatus() != 200) {
            System.err.println("Unable to connect to server");
        }

        String output = response.getEntity(String.class);
        System.out.println("RESPONSE: " + output);


        JSONObject jsonObject = new JSONObject(output);

        System.out.println(jsonObject.getString("status"));

        System.out.println(jsonObject.getJSONArray("results"));
        JSONArray stbjsonArray = jsonObject.getJSONArray("results");

        StalkerRestAPI2 stb;
        JSONObject stbObj;
        ArrayList<StalkerRestAPI2> arrayList = new ArrayList<>();
        for (int i = 0; i < stbjsonArray.length(); i++) {
            stb = new StalkerRestAPI2();
            stbObj = (JSONObject) stbjsonArray.get(i);
            stb.setAccount_balance(stbObj.getString("account_balance"));
            stb.setAdditional_service_on(stbObj.getString("additional_services_on"));
            stb.setIp(stbObj.getString("ip"));
            stb.setEnd_date(stbObj.getString("end_date"));
            stb.setLast_active(stbObj.getString("last_active"));
            stb.setLogin(stbObj.getString("login"));
            stb.setLs(stbObj.getString("ls"));
            stb.setMac(stbObj.getString("mac"));
            stb.setOnline(stbObj.getString("online"));
            stb.setVersion(stbObj.getString("version"));
            arrayList.add(stb);
        }

        ObservableList observableList = FXCollections.observableArrayList(arrayList);

        cmbUsers.setItems(observableList);


    }

    public void getUsers(ActionEvent actionEvent) {
        JSONObject jObj = new JSONObject();
        jObj.put("action", "getIPTVUsers");

        jObj = client.send_object(jObj);
        System.out.println(jObj);
    }
}
