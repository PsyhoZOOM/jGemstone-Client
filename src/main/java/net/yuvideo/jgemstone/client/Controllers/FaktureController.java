package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.Fakture;
import net.yuvideo.jgemstone.client.classes.Users;
import org.json.JSONObject;

/**
 * Created by zoom on 11/22/16.
 */
public class FaktureController implements Initializable {

  public Button bPrikaziFakture;
  public TableView tblFakture;
  public TableColumn cId;
  public TableColumn colBr;
  public TableColumn colVrstaNaziv;
  public TableColumn cJedMere;
  public TableColumn cKolicina;
  public TableColumn cCenaBezPdv;
  public TableColumn cVrednostBezPdv;
  public TableColumn cOsnovicaZaPdv;
  public TableColumn cStopaPDV;
  public TableColumn cVrednostSaPDV;
  public Button bDodajFakturu;
  public MenuItem miDeleteFakture;
  public TableColumn cIznosPDV;
  public Label lOsnovicaZaPDV;
  public Label lPDV;
  public Label lvrednostSaPDVZbir;
  public Label lUkupnoOsnovicaZaPDV;
  public Label lIznosPDVZbir;
  public Client client;
  public Users userData;
  public ResourceBundle resource;
  public Label lBrFakture;
  public Label lUkupnoPDV;
  public Label lUkupno;
  Stage stage;
  Fakture faktura;
  Users user;
  private URL location;
  private String resourceFXML;
  private Fakture selectedFacture;
  private JSONObject jObj;
  private Logger LOGGER = Logger.getLogger("FAKTURE");

  @Override
  public void initialize(URL location, ResourceBundle resources) {
        /*
        makeHeaderWrappable(cId);
        makeHeaderWrappable(colBr);
        makeHeaderWrappable(colVrstaNaziv);
        makeHeaderWrappable(cJedMere);
        makeHeaderWrappable(cKolicina);
        makeHeaderWrappable(cCenaBezPdv);
        makeHeaderWrappable(cVrednostBezPdv);
        makeHeaderWrappable(cOsnovicaZaPdv);
        makeHeaderWrappable(cStopaPDV);
        makeHeaderWrappable(cVrednostSaPDV);
        */
    set_table();

  }

  public void set_table() {

    cId.setCellValueFactory(new PropertyValueFactory<Fakture, Integer>("id"));
    colBr.setCellValueFactory(new PropertyValueFactory<Fakture, Integer>("br"));
    colVrstaNaziv.setCellValueFactory(new PropertyValueFactory<Fakture, String>("naziv"));
    cJedMere.setCellValueFactory(new PropertyValueFactory<Fakture, String>("jedMere"));
    cKolicina.setCellValueFactory(new PropertyValueFactory<Fakture, Integer>("kolicina"));
    cCenaBezPdv.setCellValueFactory(new PropertyValueFactory<Fakture, Double>("jedCena"));
    cCenaBezPdv.setCellFactory(tc -> new TableCell<Fakture, Double>() {
      @Override
      protected void updateItem(Double value, boolean empty) {
        super.updateItem(value, empty);
        if (empty || value == null) {
          setText(null);
        } else {
          setText(String.valueOf(value));
        }
      }
    });

    cVrednostBezPdv
        .setCellValueFactory(new PropertyValueFactory<Fakture, Double>("vrednostBezPDV"));
    cVrednostBezPdv.setCellFactory(tc -> new TableCell<Fakture, Double>() {
      @Override
      protected void updateItem(Double value, boolean empty) {
        super.updateItem(value, empty);
        if (empty) {
          setText(null);
        } else {
          setText(String.valueOf(value));
        }
      }
    });

    cOsnovicaZaPdv.setCellValueFactory(new PropertyValueFactory<Fakture, Double>("osnovicaZaPDV"));
    cOsnovicaZaPdv.setCellFactory(tc -> new TableCell<Fakture, Double>() {
      @Override
      protected void updateItem(Double value, boolean empty) {
        super.updateItem(value, empty);
        if (empty) {
          setText(null);
        } else {
          setText(String.valueOf(value));
        }
      }
    });

    cStopaPDV.setCellValueFactory(new PropertyValueFactory<Fakture, Double>("stopaPDV"));
    cStopaPDV.setCellFactory(tc -> new TableCell<Fakture, Double>() {
      @Override
      protected void updateItem(Double value, boolean empty) {
        super.updateItem(value, empty);
        if (empty) {
          setText(null);
        } else {
          setText(String.valueOf(value));
        }
      }
    });

    cIznosPDV.setCellValueFactory(new PropertyValueFactory<Fakture, Double>("iznosPDV"));
    cIznosPDV.setCellFactory(tc -> new TableCell<Fakture, Double>() {
      @Override
      protected void updateItem(Double Value, boolean empty) {
        super.updateItem(Value, empty);
        {
          if (empty) {
            setText(null);
          } else {
            setText(String.valueOf(Value));
          }
        }
      }
    });

    cVrednostSaPDV.setCellValueFactory(new PropertyValueFactory<Fakture, Double>("vrednostSaPDV"));
    cVrednostSaPDV.setCellFactory(tc -> new TableCell<Fakture, Double>() {
      @Override
      protected void updateItem(Double Value, boolean empty) {
        super.updateItem(Value, empty);
        if (empty) {
          setText(null);
        } else {
          setText(String.valueOf(Value));
        }
      }
    });

  }

  private ArrayList<Fakture> get_fakture() {
    Fakture fakture;
    jObj = new JSONObject();
    jObj.put("action", "get_fakture");
    jObj.put("datum", faktura.getDatum());
    jObj.put("userID", faktura.getUserId());
    jObj.put("br", faktura.getBrFakture());
    jObj = client.send_object(jObj);

    double vrednostSaPDV = 0.00;
    double ukupno = 0.00;
    double ukupnoPDV = 0.00;
    double ukpnoOsnovicaSaPDV = 0.00;

    JSONObject jfakture;

    ArrayList<Fakture> faktureArray = new ArrayList<>();

    for (int i = 0; i < jObj.length(); i++) {
      jfakture = (JSONObject) jObj.get(String.valueOf(i));
      fakture = new Fakture();
      fakture.setId(jfakture.getInt("id"));
      fakture.setBr(jfakture.getInt("br"));
      fakture.setUserId(jfakture.getInt("userID"));
      fakture.setNaziv(jfakture.getString("naziv"));
      fakture.setDatum(jfakture.getString("datum"));
      fakture.setMesec(jfakture.getString("mesec"));
      fakture.setGodina(jfakture.getString("godina"));
      fakture.setJedMere(jfakture.getString("jedMere"));
      fakture.setKolicina(jfakture.getInt("kolicina"));
      fakture.setJedCena(jfakture.getDouble("cenaBezPDV"));
      fakture.setStopaPDV(jfakture.getDouble("pdv"));
      fakture.setBrFakture(jfakture.getInt("br"));
      fakture.setOperater(jfakture.getString("operater"));
      fakture.setVrednostBezPDV(jfakture.getDouble("cenaBezPDV"));
      fakture.setOsnovicaZaPDV(jfakture.getDouble("OsnovicaZaPDV"));
      fakture.setIznosPDV(jfakture.getDouble("iznosPDV"));
      fakture.setVrednostSaPDV(jfakture.getDouble("VrednostSaPDV"));

      //set label racunica
      lPDV.setText(String.valueOf(fakture.getStopaPDV()));
      vrednostSaPDV = +vrednostSaPDV + fakture.getOsnovicaZaPDV() + fakture.getIznosPDV();
      ukupno = +fakture.getVrednostBezPDV();
      ukupnoPDV = +ukupnoPDV + fakture.getIznosPDV();
      ukpnoOsnovicaSaPDV = ukupno + ukupnoPDV;

      lOsnovicaZaPDV.setText(String.valueOf(ukupno));
      lIznosPDVZbir.setText(String.valueOf(ukupnoPDV));
      lvrednostSaPDVZbir.setText(String.valueOf(ukupno + ukupnoPDV));
      lUkupnoOsnovicaZaPDV.setText(String.valueOf(ukupno));
      lUkupnoPDV.setText(String.valueOf(ukupnoPDV));
      lUkupno.setText(String.valueOf(ukpnoOsnovicaSaPDV));

      faktureArray.add(fakture);

    }
    return faktureArray;
  }

  private void makeHeaderWrappable(TableColumn col) {
    Label label = new Label(col.getText());
    label.setStyle("-fx-padding: 8px");
    label.setWrapText(true);
    label.setAlignment(Pos.CENTER);
    label.setTextAlignment(TextAlignment.CENTER);

    StackPane stack = new StackPane();
    stack.getChildren().add(label);
    stack.prefWidthProperty().bind(col.widthProperty().subtract(5));
    label.prefWidthProperty().bind(stack.prefWidthProperty());
    col.setGraphic(stack);
  }

  public void dodajFakturu(ActionEvent event) {
  }

  public void deleteFakturu(ActionEvent event) {
  }

  public void prikaziFakture(ActionEvent event) {
  }

  public void setData() {
    this.user = getUserData(faktura.getUserId());
    ObservableList data = FXCollections.observableArrayList(get_fakture());
    tblFakture.setItems(data);

    lBrFakture.setText(
        String.format("%s/%d/%s", user.getJbroj(), faktura.getBrFakture(), faktura.getGodina()));
  }

  private Users getUserData(int userId) {
    JSONObject jUser = new JSONObject();
    jUser.put("action", "get_user_data");
    jUser.put("userId", userId);
    jUser = client.send_object(jUser);

    Users user = new Users();
    user.setId(jUser.getInt("id"));
    user.setjMesto(jUser.getString("jMesto"));
    user.setjAdresa(jUser.getString("jAdresa"));
    user.setjAdresaBroj(jUser.getString("jAdresaBroj"));
    user.setIme(jUser.getString("fullName"));
    user.setMesto(jUser.getString("mesto"));
    user.setAdresa(jUser.getString("adresa"));
    user.setAdresaRacuna(jUser.getString("adresaRacuna"));
    user.setMestoRacuna(jUser.getString("mestoRacuna"));
    user.setBr_lk(jUser.getString("brLk"));
    user.setDatum_rodjenja(jUser.getString("datumRodjenja"));
    user.setFiksni(jUser.getString("telFix"));
    user.setMobilni(jUser.getString("telMob"));
    user.setJMBG(jUser.getString("JMBG"));
    user.setKomentar(jUser.getString("komentar"));
    user.setPostanski_broj(jUser.getString("postBr"));
    user.setJbroj(jUser.getString("jBroj"));
    user.setFirma(jUser.getBoolean("firma"));
    if (!jUser.getString("jBroj").isEmpty()) {
      user.setBr(jUser.getString("jBroj"));
    }

    return user;

  }
}
