package JGemstone.classes;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * Created by zoom on 8/11/16.
 */
public class trViewLeft {
    public AnchorPane apCenter;
    private TreeView<tiProperty> treeViewLeft;
    private TreeItem tiRootKorisnici;
    private TreeItem tiListKorisnici;
    private TreeItem tiLogovi;
    private ResourceBundle resources;

    public trViewLeft(TreeView<tiProperty> treeViewLeft) {
        this.treeViewLeft = treeViewLeft;
    }

    private TreeView<tiProperty> setTree() {
        treeViewLeft.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<tiProperty>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<tiProperty>> observable, TreeItem<tiProperty> oldValue, TreeItem<tiProperty> newValue) {

                System.out.println(String.format("Value je: %s Naziv je: %s", newValue.getValue().getValue(), newValue.getValue().getNaziv()));

                if (newValue.getValue().getNaziv().equals("useri")) {
                    apCenter.getChildren().setAll(iNoviKorisnik(resources));
                }
            }
        });

        TreeView tv = new TreeView();

        TreeItem root = new TreeItem(new tiProperty("YUVideo", 0, "yuvideo"));
        TreeItem<tiProperty> ti1 = new TreeItem(new tiProperty(resources.getString("USERS"), 1, "useri"));
        TreeItem<tiProperty> ti2 = new TreeItem(new tiProperty(resources.getString("GROUPS"), 2, "grupice"));

        treeViewLeft.setRoot(root);
        root.getChildren().addAll(ti1, ti2);

        return treeViewLeft;
    }

    private Node iNoviKorisnik(ResourceBundle resource) {
        Node node = null;
        try {
            node = (Node) FXMLLoader.load(getClass().getResource("/JGemstone/resources/fxml/Korisnici.fxml"), resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }
}
