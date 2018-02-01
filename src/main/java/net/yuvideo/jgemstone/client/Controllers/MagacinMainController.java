package net.yuvideo.jgemstone.client.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import net.yuvideo.jgemstone.client.classes.AlertUser;
import net.yuvideo.jgemstone.client.classes.Artikli;
import net.yuvideo.jgemstone.client.classes.Client;
import org.json.JSONObject;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 1/30/18.
 */
public class MagacinMainController implements Initializable {
    private final DecimalFormat df = new DecimalFormat("#.##");
    public TextField tNaziv;
    public TextField tModel;
    public TextField tSerijski;
    public TextField tPserijski;
    public TextField tMAC;
    public TextField tDobavljac;
    public TextField tBrDok;
    public TextField tNabavnaCena;
    public TextArea tOpis;
    public Button bNov;
    public Button bIzmeni;
    public Button bIzbrisi;
    public Button bTrazi;
    public ComboBox cmbJMere;
    public Spinner spnKolicina;
    public TableView<Artikli> tblArtikli;
    public TableColumn<Artikli, String> cNaziv;
    public TableColumn<Artikli, String> cModel;
    public TableColumn<Artikli, String> cSerijski;
    public TableColumn<Artikli, String> cMac;
    public TableColumn<Artikli, String> cPSerijski;
    public TableColumn<Artikli, String> cDobavljac;
    public TableColumn<Artikli, String> cBrDokumenta;
    public TableColumn<Artikli, Double> cNabavnaCena;
    public TableColumn<Artikli, String> cDatum;
    public TableColumn<Artikli, String> cJMere;
    public TableColumn<Artikli, Integer> cKolicina;
    public TableColumn<Artikli, String> cOpis;
    public Client client;
    SpinnerValueFactory.IntegerSpinnerValueFactory integerSpinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1);
    private URL location;
    private ResourceBundle resources;
    private boolean editArtikl = false;
    private boolean searchArtikl = false;
    private boolean addArtikl = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;


        cNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        cModel.setCellValueFactory(new PropertyValueFactory<>("model"));
        cSerijski.setCellValueFactory(new PropertyValueFactory<>("serijski"));
        cMac.setCellValueFactory(new PropertyValueFactory<>("mac"));
        cPSerijski.setCellValueFactory(new PropertyValueFactory<>("pserijski"));
        cDobavljac.setCellValueFactory(new PropertyValueFactory<>("dobavljac"));
        cBrDokumenta.setCellValueFactory(new PropertyValueFactory<>("brDok"));
        cNabavnaCena.setCellValueFactory(new PropertyValueFactory<>("nabavnaCena"));
        cDatum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        cJMere.setCellValueFactory(new PropertyValueFactory<>("jmere"));
        cOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        cKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));

        cKolicina.setCellFactory(new Callback<TableColumn<Artikli, Integer>, TableCell<Artikli, Integer>>() {
            @Override
            public TableCell<Artikli, Integer> call(TableColumn<Artikli, Integer> param) {
                return new TableCell<Artikli, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText("");
                        } else {
                            setText(String.valueOf(item));
                        }
                    }
                };
            }
        });

        cNabavnaCena.setCellFactory(new Callback<TableColumn<Artikli, Double>, TableCell<Artikli, Double>>() {
            @Override
            public TableCell<Artikli, Double> call(TableColumn<Artikli, Double> param) {
                return new TableCell<Artikli, Double>() {
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText("");
                        } else {
                            setText(df.format(item));
                        }
                    }
                };
            }
        });


        //set spinner value factoru min 0 max MAX_INTEGER_VALUE init value =1;
        spnKolicina.setValueFactory(integerSpinnerValueFactory);

        tblArtikli.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Artikli>() {
            @Override
            public void changed(ObservableValue<? extends Artikli> observable, Artikli oldValue, Artikli newValue) {
                setValues();
            }
        });

    }

    private void setValues() {
        Artikli artikli = tblArtikli.getSelectionModel().getSelectedItem();
        tNaziv.setText(artikli.getNaziv());
        tModel.setText(artikli.getModel());
        tSerijski.setText(artikli.getSerijski());
        tPserijski.setText(artikli.getPserijski());
        tMAC.setText(artikli.getMac());
        tDobavljac.setText(artikli.getDobavljac());
        tBrDok.setText(artikli.getBrDok());
        tNabavnaCena.setText(String.valueOf(artikli.getNabavnaCena()));
        spnKolicina.getEditor().setText(String.valueOf(artikli.getKolicina()));
        tOpis.setText(artikli.getOpis());
        if (artikli.getJmere().equals("metri.")) {
            cmbJMere.getSelectionModel().select(1);
        } else {
            cmbJMere.getSelectionModel().select(2);
        }


    }


    public void setForms() {

        //combobox add jedinice mere
        cmbJMere.getItems().removeAll();
        cmbJMere.getItems().add(0, "");
        cmbJMere.getItems().add(1, "metri.");
        cmbJMere.getItems().add(2, "kom.");

        cmbJMere.getSelectionModel().select(0);

        tNabavnaCena.setText("0.00");

    }


    public void addArtikal(ActionEvent actionEvent) {
        addArtikl = true;
        artiklFunc();
    }


    public void artiklFunc() {
        int artikalID = 0;
        JSONObject jsonObject = new JSONObject();

        if (editArtikl) {
            jsonObject.put("action", "editArtikal");
            if (tblArtikli.getSelectionModel().getSelectedIndex() == -1) {
                AlertUser.error("GRESKA", "Nije izabran ni jedan artikal");
                return;
            } else {
                artikalID = tblArtikli.getSelectionModel().getSelectedItem().getId();
                jsonObject.put("id", artikalID);
            }
        } else if (addArtikl) {
            jsonObject.put("action", "addArtikal");
            jsonObject.put("id", artikalID);

        } else if (searchArtikl) {
            jsonObject.put("action", "searchArtikal");
        }
        jsonObject.put("naziv", tNaziv.getText());
        jsonObject.put("model", tModel.getText());
        jsonObject.put("serijski", tSerijski.getText());
        jsonObject.put("pserijski", tPserijski.getText());
        jsonObject.put("mac", tMAC.getText());
        jsonObject.put("dobavljac", tDobavljac.getText());
        jsonObject.put("brDokumenta", tBrDok.getText());
        jsonObject.put("nabavnaCena", tNabavnaCena.getText());
        jsonObject.put("jMere", cmbJMere.getEditor().getText().toString());
        jsonObject.put("kolicina", Integer.valueOf(spnKolicina.getEditor().getText()));
        jsonObject.put("opis", tOpis.getText());


        if (addArtikl || editArtikl) {
            jsonObject = client.send_object(jsonObject);
        }
        //ako trazimo artikle populate table
        if (searchArtikl) {
            ObservableList searchArtikli = FXCollections.observableArrayList(getArtikli(jsonObject));
            tblArtikli.setItems(searchArtikli);

        }

        if (jsonObject.has("ERROR")) {
            AlertUser.error("GREŠKA", jsonObject.getString("ERROR"));
        } else if (addArtikl || editArtikl) {
            AlertUser.info("ARTIKAL SNIMLJEN", "Artikal je uspešno snimljen");
        }


        //all to false becouse we don't wanna conflict
        this.searchArtikl = false;
        this.addArtikl = false;
        this.editArtikl = false;

    }

    public void IzmeniArtikal(ActionEvent actionEvent) {
        this.editArtikl = true;
        artiklFunc();
    }

    public void izbrisiArtikal(ActionEvent actionEvent) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "deleteArtikl");
        if (tblArtikli.getSelectionModel().getSelectedIndex() == -1) {
            AlertUser.error("GRESKA", "Nije izabran ni jedan artikal");
            return;
        }

        int id = tblArtikli.getSelectionModel().getSelectedItem().getId();
        jsonObject.put("id", id);
        jsonObject = client.send_object(jsonObject);

        if (jsonObject.has("ERROR")) {
            AlertUser.error("GRESKA", jsonObject.getString("ERROR"));
        } else {
            AlertUser.info("ARTIKAL JE IZBRISA", String.format("Artikal ID:%d je obrisan", id));
        }
    }

    public void traziArtikal(ActionEvent actionEvent) {
        searchArtikl = true;
        artiklFunc();

    }

    private ArrayList<Artikli> getArtikli(JSONObject jsonObject) {
        JSONObject artikliObj = new JSONObject();
        ArrayList<Artikli> artikliArrayList = new ArrayList<>();
        artikliObj = client.send_object(jsonObject);

        for (int i = 0; i < artikliObj.length(); i++) {
            Artikli artikli = new Artikli();
            JSONObject object = artikliObj.getJSONObject(String.valueOf(i));
            artikli.setId(object.getInt("id"));
            artikli.setNaziv(object.getString("naziv"));
            artikli.setModel(object.getString("model"));
            artikli.setSerijski(object.getString("serijski"));
            artikli.setPserijski(object.getString("pserijski"));
            artikli.setMac(object.getString("mac"));
            artikli.setNabavnaCena(object.getDouble("nabavnaCena"));
            artikli.setDobavljac(object.getString("dobavljac"));
            artikli.setKolicina(object.getInt("kolicina"));
            artikli.setBrDok(object.getString("brDokumenta"));
            artikli.setJmere(object.getString("jMere"));
            artikli.setKolicina(object.getInt("kolicina"));
            artikli.setOpis(object.getString("opis"));
            artikli.setDatum(object.getString("datum"));
            artikli.setOperName(object.getString("operName"));
            artikliArrayList.add(artikli);
        }
        return artikliArrayList;


    }

    public void clearText(ActionEvent actionEvent) {
        tNaziv.clear();
        tModel.clear();
        tSerijski.clear();
        tPserijski.clear();
        tMAC.clear();
        tDobavljac.clear();
        tBrDok.clear();
        tNabavnaCena.clear();
        spnKolicina.getEditor().setText("0");
        cmbJMere.getSelectionModel().select(0);
        tOpis.clear();

    }
}
