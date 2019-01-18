package net.yuvideo.jgemstone.client.Controllers;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
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
import net.yuvideo.jgemstone.client.classes.Settings;
import net.yuvideo.jgemstone.client.classes.db_connection;
import org.json.JSONException;
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
  public Settings LocalSettings;
  public boolean saveFIRMA = false;
  public JFXTextField tNacinPlacanjaFaktura;
  public JFXTextField tRokPlacanjaFaktura;
  public JFXTextField tRokPlacanjaRacun;
  public JFXTextField tMestoIzdravanjaRacuna;
  public JFXTextField tMestoIzdavanjeDobara;
  public JFXTextField tUDPTimeout;
  public JFXTextField tDTVCryptHOST;
  public JFXTextField tDTVCryptPORT;
  public JFXTextField tStalkerApiUrl;
  public JFXTextField tStalkerApiUserName;
  public JFXPasswordField tStalkerApiPass;
  public Tab tabStalMin;
  public Tab tabDTV;
  public JFXCheckBox chkDtvService;
  @FXML
  private TextField tHostnameIp;
  @FXML
  private TextField tPort;
  @FXML
  private Button bSnimi;
  private Client client;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    if (!saveFIRMA) {
      tabFirma.setDisable(true);
      tabRacunFaktura.setDisable(true);
      tabStalMin.setDisable(true);
      tabDTV.setDisable(true);

    }
    if(LocalSettings== null) {
      db_connection db  = new db_connection();
      LocalSettings = db.getLocal_settings();
    }
      tHostnameIp.setText(LocalSettings.getREMOTE_HOST());
      tPort.setText(String.valueOf(LocalSettings.getREMOTE_PORT()));

  }

  public void enableTabs() {
    tabFirma.setDisable(false);
    tabRacunFaktura.setDisable(false);
    tabDTV.setDisable(false);
    tabStalMin.setDisable(false);
  }

  public void bSnimi_options(ActionEvent actionEvent) {
    boolean firmaIsSaved = true;

    if (!tPort.getText().isEmpty()) {
      LocalSettings.setREMOTE_PORT(Integer.parseInt(tPort.getText()));
    }
    if (!tHostnameIp.getText().isEmpty()) {
      LocalSettings.setREMOTE_HOST(tHostnameIp.getText());
    }
    db_connection db_conn = new db_connection();
    db_conn.setLocal_settings(LocalSettings);
    db_conn.set_settings();

    if (saveFIRMA) {
      firmaIsSaved = save_Data();
      if (!firmaIsSaved) {
        AlertUser.error("GREŠKA", "Došlo je do greške prilikom snimljana podataka firme!");
      }
    }

    AlertUser.info("IZMENE SNIMLJENE", "Izmene uspešno snimljene ;)");
    Stage stage = (Stage) bSnimi.getScene().getWindow();
    stage.close();

  }

  private boolean save_Data() {
    JSONObject data = new JSONObject();
    data.put("action", "OPTIONS_SAVE");
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
    data.put("FIRMA_FAKTURA_PEPDV", tPEPDV.getText());
    data.put("FIRMA_MESTO_IZDAVANJA_RACUNA", tMestoIzdravanjaRacuna.getText());
    data.put("FIRMA_MESTO_PROMETA_DOBARA", tMestoIzdavanjeDobara.getText());
    data.put("FIRMA_NACIN_PLACANJA_FAKTURA", tNacinPlacanjaFaktura.getText());
    data.put("FIRMA_ROK_PLACANJA_RACUN", tRokPlacanjaRacun.getText());
    data.put("FIRMA_ROK_PLACANJA_FAKTURA", tRokPlacanjaFaktura.getText());
    data.put("MINISTRA_API_URL", tStalkerApiUrl.getText());
    data.put("MINISTRA_API_USER", tStalkerApiUserName.getText());
    data.put("MINISTRA_API_PASS", tStalkerApiPass.getText());
    data.put("DTV_UDP_TIMEOUT", tUDPTimeout.getText());
    data.put("DTV_EMM_HOST", tDTVCryptHOST.getText());
    data.put("DTV_EMM_PORT", tDTVCryptPORT.getText());
    data.put("DTV_SERVICE", chkDtvService.isSelected());

    data = client.send_object(data);
    return !data.has("ERROR");

  }

  public void setDataFirma() {
    JSONObject obj = new JSONObject();
    obj.put("action", "GET_OPTIONS");
    obj = client.send_object(obj);
    if (obj.has("ERROR")) {
      AlertUser.error("GRESKA", "Došlo je do greške pri dobijanju informacija o firmi");
      return;
    }
    try {
      if (obj.has("FIRMA_FAKTURA_PEPDV"))
        tPEPDV.setText(obj.getString("FIRMA_FAKTURA_PEPDV"));
      if (obj.has("FIRMA_NAZIV"))
        tNazivFirme.setText(obj.getString("FIRMA_NAZIV"));
      if (obj.has("FIRMA_ADRESA"))
        tAdresaFirme.setText(obj.getString("FIRMA_ADRESA"));
      if (obj.has("FIRMA_PIB"))
        tPIB.setText(obj.getString("FIRMA_PIB"));
      if (obj.has("FIRMA_MBR"))
        tMaticniBroj.setText(obj.getString("FIRMA_MBR"));
      if (obj.has("FIRMA_TEKUCIRACUN"))
        tTekuciRacun.setText(obj.getString("FIRMA_TEKUCIRACUN"));
      if (obj.has("FIRMA_TELEFON"))
        tTelefon.setText(obj.getString("FIRMA_TELEFON"));
      if (obj.has("FIRMA_FAX"))
        tFAX.setText(obj.getString("FIRMA_FAX"));
      if (obj.has("FIRMA_SERVIS_TELEFON"))
        tTelServis.setText(obj.getString("FIRMA_SERVIS_TELEFON"));
      if (obj.has("FIRMA_SERVIS_EMAIL"))
        tEmailServis.setText(obj.getString("FIRMA_SERVIS_EMAIL"));
      if (obj.has("FIRMA_WEBPAGE"))
        tInternetStranica.setText(obj.getString("FIRMA_WEBPAGE"));
      if (obj.has("FIRMA_NACIN_PLACANJA_FAKTURA"))
        tNacinPlacanjaFaktura.setText(obj.getString("FIRMA_NACIN_PLACANJA_FAKTURA"));
      if (obj.has("FIRMA_ROK_PLACANJA_FAKTURA"))
        tRokPlacanjaFaktura.setText(obj.getString("FIRMA_ROK_PLACANJA_FAKTURA"));
      if (obj.has("FIRMA_ROK_PLACANJA_RACUN"))
        tRokPlacanjaRacun.setText(obj.getString("FIRMA_ROK_PLACANJA_RACUN"));
      if (obj.has("FIRMA_MESTO_IZDAVANJA_RACUNA"))
        tMestoIzdravanjaRacuna.setText(obj.getString("FIRMA_MESTO_IZDAVANJA_RACUNA"));
      if (obj.has("FIRMA_MESTO_PROMETA_DOBARA"))
        tMestoIzdavanjeDobara.setText(obj.getString("FIRMA_MESTO_PROMETA_DOBARA"));
      if (obj.has("MINISTRA_API_URL"))
        tStalkerApiUrl.setText(obj.getString("MINISTRA_API_URL"));
      if (obj.has("MINISTRA_API_PASS"))
        tStalkerApiPass.setText(obj.getString("MINISTRA_API_PASS"));
      if (obj.has("DTV_EMM_HOST"))
        tDTVCryptHOST.setText(obj.getString("DTV_EMM_HOST"));
      if (obj.has("DTV_EMM_PORT"))
        tDTVCryptPORT.setText(obj.getString("DTV_EMM_PORT"));
      if (obj.has("DTV_UDP_TIMEOUT"))
        tUDPTimeout.setText(obj.getString("DTV_UDP_TIMEOUT"));
      if (obj.has("DTV_SERVICE")) {
        chkDtvService.setSelected(obj.getBoolean("DTV_SERVICE"));
      }
      if (obj.has("MINISTRA_API_USER"))
        tStalkerApiUserName.setText(obj.getString("MINISTRA_API_USER"));
    } catch (JSONException e) {
      e.printStackTrace();
    }


  }

  public void setClient(Client client) {
    this.client = client;
  }
}
