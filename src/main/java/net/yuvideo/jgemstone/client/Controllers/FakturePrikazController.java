package net.yuvideo.jgemstone.client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.NewInterface;
import net.yuvideo.jgemstone.client.classes.Users;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Fakture;
import org.json.JSONObject;

/**
 * Created by PsyhoZOOM@gmail.com on 10/31/17.
 */
public class FakturePrikazController implements Initializable {

	public Client client;
	public Users user;
	public Button bPrikaziFakturu;
	private URL location;
	private ResourceBundle resources;

	@FXML
	TableView<Fakture> tblFakture;
	@FXML
	TableColumn<Fakture, Integer> cBr;
	@FXML
	TableColumn<Fakture, String> cDatum;
	@FXML
	TableColumn<Fakture, Double> cIznosBezPDV;
	@FXML
	TableColumn<Fakture, Double> cIznostPDV;
	@FXML
	TableColumn<Fakture, Double> cCenaSaPDV;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.location = location;
		this.resources = resources;

	}

	public void prikaziFakturu(ActionEvent event) {
		NewInterface faktureInterface = new NewInterface("fxml/Fakture.fxml", "FAKTURE: " + user.getIme(), resources);
		FaktureController faktureController = faktureInterface.getLoader().getController();
		faktureController.client = this.client;
		faktureController.userData = user;
		faktureInterface.getStage().showAndWait();

	}

	public void set_data() {
		ObservableList<Fakture> data = FXCollections.observableArrayList(getFaktureData());
		tblFakture.setItems(data);

	}

	private ArrayList<Fakture> getFaktureData() {
		JSONObject jObj = new JSONObject();
		jObj.put("action", "get_fakture");
		jObj.put("userID", user.getId());
		jObj = client.send_object(jObj);

		if (jObj.has("ERROR")) {
			AlertUser.error("GRESKA", jObj.getString("ERROR"));
			return null;
		}

		Fakture fakture;
		ArrayList<Fakture> faktureArr = new ArrayList<>();

		for (int i = 0; i < jObj.length(); i++) {
			fakture = new Fakture();
			fakture.setId(jObj.getInt("id"));
			fakture.setBrFakture(jObj.getInt("br"));
			fakture.setNaziv(jObj.getString("naziv"));
			fakture.setJedMere(jObj.getString("jedMere"));
			fakture.setKolicina(jObj.getInt("kolicina"));
			fakture.setVrednostBezPDV(jObj.getDouble("cena"));
			fakture.setIznosPDV(jObj.getDouble("pdv"));
			fakture.setOperater(jObj.getString("operater"));
			fakture.setUserId(jObj.getInt("userID"));
			fakture.setDatum(jObj.getString("datum"));
			fakture.setGodina(jObj.getString("godina"));
			fakture.setMesec(jObj.getString("mesec"));
			faktureArr.add(fakture);
		}

		return faktureArr;

	}
}
