package JGemstone.interfaces;

import JGemstone.classes.Client;
import JGemstone.classes.ServicesUser;
import JGemstone.classes.Users;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by zoom on 3/23/17.
 */
public class UslugeZaduzivenjeController implements Initializable {
    public TextField tNazivUsluge;
    public DatePicker dtpDatumZaduzenja;
    public CheckBox chkUplaceno;
    public Button bSave;

    public Client client;
    public Users user;
    public TextField tBrojRata;
    public ComboBox<ServicesUser> cmbUserServices;
    public Label lIznosRate;

    private URL location;
    private ResourceBundle resources;
    private JSONObject jObj;

    private DecimalFormat df = new DecimalFormat("#,##0.00");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM");
    private DateTimeFormatter dtfNormal = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        dtpDatumZaduzenja.setValue(LocalDate.now());
        tBrojRata.setText("0");

        tBrojRata.textProperty().addListener(new ChangeListener<String>() {
            Integer brojRate = 0;

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    brojRate = Integer.valueOf(tBrojRata.getText());
                    countRate();
                } catch (Exception e) {
                    tBrojRata.setText(String.valueOf(brojRate));

                }
            }
        });


        cmbUserServices.setConverter(new StringConverter<ServicesUser>() {
            @Override
            public String toString(ServicesUser object) {
                return object.getNazivPaketa();
            }

            @Override
            public ServicesUser fromString(String string) {
                ServicesUser user = new ServicesUser();
                user.setNazivPaketa(string);
                return user;
            }
        });

    }

    private void countRate() {
        Double cena = cmbUserServices.getValue().getCena();
        Double popust = cmbUserServices.getValue().getPopust();
        int rate = Integer.parseInt(tBrojRata.getText());

        Double ukupno = cena / rate;
        lIznosRate.setText(df.format(ukupno) + " x " + rate + " = " + df.format(ukupno * rate));


    }


    public void addZaduzenje(ActionEvent actionEvent) {
        System.out.println(cmbUserServices.getValue().getNazivPaketa() + cmbUserServices.getValue().getId());

    }

    public void show_data() {
        ObservableList userService = FXCollections.observableArrayList(get_user_services());
        cmbUserServices.setItems(userService);


    }

    public ArrayList<ServicesUser> get_user_services() {
        jObj = new JSONObject();
        jObj.put("action", "get_user_services");
        jObj.put("userID", user.getId());
        jObj = client.send_object(jObj);

        JSONObject userServiceObj;
        ArrayList<ServicesUser> userServiceArr = new ArrayList();
        ServicesUser userService;

        for (int i = 0; i < jObj.length(); i++) {
            userServiceObj = (JSONObject) jObj.get(String.valueOf(i));
            userService = new ServicesUser();
            userService.setId(userServiceObj.getInt("id"));
            userService.setBrUgovora(userServiceObj.getInt("brojUgovora"));
            userService.setCena(userServiceObj.getDouble("cena"));
            userService.setPopust(userServiceObj.getDouble("popust"));
            userService.setOperater(userServiceObj.getString("operName"));
            userService.setDatum(userServiceObj.getString("date_added"));
            userService.setNazivPaketa(userServiceObj.getString("nazivPaketa"));
            if (userServiceObj.has("idUniqueName"))
                userService.setIdDTVCard(userServiceObj.getString("idUniqueName"));
            userService.setObracun(userServiceObj.getBoolean("obracun"));
            userService.setAktivan(userServiceObj.getBoolean("aktivan"));
            userService.setProduzenje(userServiceObj.getInt("produzenje"));
            if (userServiceObj.has("id_service"))
                userService.setId_Service(userServiceObj.getInt("id_service"));
            if (userServiceObj.has("BOX_service"))
                userService.setBox(userServiceObj.getBoolean("BOX_service"));
            if (userServiceObj.has("box_id"))
                userService.setBox_id(userServiceObj.getInt("box_id"));
            userService.setPaketType(userServiceObj.getString("paketType"));
            userService.setLinkedService(userServiceObj.getBoolean("linkedService"));
            userService.setNewService(userServiceObj.getBoolean("newService"));
            userServiceArr.add(userService);
        }

        return userServiceArr;
    }
}
