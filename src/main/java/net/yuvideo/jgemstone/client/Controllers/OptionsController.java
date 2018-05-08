package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Client;
import net.yuvideo.jgemstone.client.classes.db_connection;
import org.json.JSONObject;

/**
 * Created by zoom on 8/16/16.
 */
public class OptionsController implements Initializable {

  public Stage stage;
  public JFXTextField tNazivFirme;
  public JFXTextField tAdresaFirme;
  public JFXTextField tPIB;
  public JFXTextField tMaticniBroj;
  public JFXTextField tTekuciRacun;
  public JFXTextField tTelefon;
  public JFXTextField tFAX;
  public JFXTextField tTelServis;
  public JFXTextField tEmailServis;
  public JFXTextField tInternetStranica;
  public Tab tabRacunFaktura;
  public Tab tabFirma;
  public JFXTextField tPEPDV;
  public Client client;
  public boolean saveFIRMA = false;
  @FXML
  private TextField tHostnameIp;
  @FXML
  private TextField tPort;
  @FXML
  private Button bSnimi;
  private db_connection db_conn;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    if (!saveFIRMA) {
      tabFirma.setDisable(true);
      tabRacunFaktura.setDisable(true);
    }
    db_conn = new db_connection();
    db_conn.init_database();
    tHostnameIp.setText(db_conn.local_settings.getREMOTE_HOST());
    tPort.setText(String.valueOf(db_conn.local_settings.getREMOTE_PORT()));
    db_conn.close_db();
  }

  public void enableTabs() {
    tabFirma.setDisable(false);
    tabRacunFaktura.setDisable(false);
  }

  public void bSnimi_options(ActionEvent actionEvent) {
    boolean firmaIsSaved = true;

    db_conn.init_database();
    if (!tPort.getText().isEmpty()) {
      db_conn.local_settings.setREMOTE_PORT(Integer.parseInt(tPort.getText()));
    }
    if (!tHostnameIp.getText().isEmpty()) {
      db_conn.local_settings.setREMOTE_HOST(tHostnameIp.getText());
    }
    try {
      db_conn.set_settings();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    db_conn.close_db();

    if (saveFIRMA) {
      firmaIsSaved = save_FirmaData();
      if (!firmaIsSaved) {
        AlertUser.error("GREŠKA", "Došlo je do greške prilikom snimljana podataka firme!");
      }
    }

    AlertUser.info("IZMENE SNIMLJENE", "Izmene uspešno snimljene ;)");
    Stage stage = (Stage) bSnimi.getScene().getWindow();
    stage.close();


  }

  private boolean save_FirmaData() {
    JSONObject data = new JSONObject();
    data.put("action", "FIRMA_OPTIONS_SAVE");
    data.put("FIRMA_NAZIV", tNazivFirme.getText());
    data.put("FIRMA_ADRESA", tAdresaFirme.getText());
    data.put("FIRMA_PIB", tPIB.getText());
    data.put("FIRMA_MBR", tMaticniBroj.getText());
    data.put("FIRMA_TEKUCIRACUN", tTekuciRacun.getText());
    data.put("FIRMA_TELEFON", tTelefon.getText());
    data.put("FIRMA_FAX", tFAX.getText());
    data.put("FIRMA_SERVIS_TELEFON", tTelServis.getText());
    data.put("FIRMA_SERVIS_EMAIL", tEmailServis.getText());
    data.put("FIRMA_WEBPAGE", tInternetStranica.getText());
    data.put(("FIRMA_FAKTURA_PEPDV"), tPEPDV.getText());

    data = client.send_object(data);
    return !data.has("ERROR");

  }

  public void setDataFirma() {
    JSONObject obj = new JSONObject();
    obj.put("action", "get_FIRMA_OPTIONS");
    obj = client.send_object(obj);
    if (obj.has("ERROR")) {
      AlertUser.error("GRESKA", "Došlo je do greške pri dobijanju informacija o firmi");
      return;
    }
    tPEPDV.setText(obj.getString("FIRMA_FAKTURA_PEPDV"));
    tNazivFirme.setText(obj.getString("FIRMA_NAZIV"));
    tAdresaFirme.setText(obj.getString("FIRMA_ADRESA"));
    tPIB.setText(obj.getString("FIRMA_PIB"));
    tMaticniBroj.setText(obj.getString("FIRMA_MBR"));
    tTekuciRacun.setText(obj.getString("FIRMA_TEKUCIRACUN"));
    tTelefon.setText(obj.getString("FIRMA_TELEFON"));
    tFAX.setText(obj.getString("FIRMA_FAX"));
    tTelServis.setText(obj.getString("FIRMA_SERVIS_TELEFON"));
    tEmailServis.setText(obj.getString("FIRMA_SERVIS_EMAIL"));
    tInternetStranica.setText(obj.getString("FIRMA_WEBPAGE"));
  }
}
