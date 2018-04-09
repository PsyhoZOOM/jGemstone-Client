package net.yuvideo.jgemstone.client.Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import net.yuvideo.jgemstone.client.classes.Client;
import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.PropertySheet;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zoom on 4/7/17.
 */
public class InternetMainController implements Initializable {
    public Client client;
    public Stage stage;
    public TreeView tTreeViewMain;
    public MasterDetailPane masterDP;
    private ResourceBundle resources;
    private URL location;

    private TableView tableView;
    private PropertySheet propertySheet;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;

        propertySheet = new PropertySheet();
        tableView = new TableView();
        masterDP.setDetailNode(propertySheet);
        masterDP.setMasterNode(tableView);
        masterDP.setDetailSide(Side.BOTTOM);
        masterDP.setShowDetailNode(true);

        


    }


    public  void setTreeItems(){
        FontAwesomeIconView iconUsers = new FontAwesomeIconView();
        iconUsers.setIcon(FontAwesomeIcon.USERS);
        iconUsers.setStyle("-fx-fill: white");
        //root item
        TreeItem root = new TreeItem("ADMINISTRATION");
        //korisnici tree
        TreeItem Korisnici = new TreeItem("KORISNICI");
        Korisnici.setGraphic(iconUsers);
        TreeItem PretragaKorisnika = new TreeItem("Pretraga");
        Korisnici.getChildren().add(PretragaKorisnika);

        //group item
        TreeItem Grupe = new TreeItem("GRUPE");
        TreeItem GrupeIzmena = new TreeItem("Izmena");
        Grupe.getChildren().add(GrupeIzmena);

        //NAS-Routers-AP-Switch Pristupne tacke
        TreeItem PristupneTacke = new TreeItem("PRISTUPNE TAČKE");
        TreeItem PretragaWiFi = new TreeItem("WiFi-Tracker ;)");
        TreeItem PristupnaTacka = new TreeItem("Pristupna tačka");

        PristupneTacke.getChildren().add(PretragaWiFi);
        PristupneTacke.getChildren().add(PristupnaTacka);


        //NETWORK
        TreeItem Network = new TreeItem("MREŽA");



        root.getChildren().add(Korisnici);
        root.getChildren().add(Grupe);
        root.getChildren().add(PristupneTacke);
        root.getChildren().add(Network);



        tTreeViewMain.setShowRoot(false);
        tTreeViewMain.setRoot(root);
    }
}
