package net.yuvideo.jgemstone.client.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ResourceBundle;

/**
 * Created by PsyhoZOOM@gmail.com on 12/18/17.
 */
public class QrScannerController implements Initializable {
    public Button bScan;
    public Label lnfo;
    private String scanString;
    private ByteBuffer barCode;
    private URL url;
    private ResourceBundle resources;
    private StringBuffer codeString = new StringBuffer();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.url = location;
        this.resources = resources;
    }

    public void scann(ActionEvent event) {


        lnfo.setText("");

        lnfo.setText(codeString.toString());
        codeString = new StringBuffer();

    }

    public void keyRelease(KeyEvent event) {
        System.out.println(event.getCode().getName());
        if (event.getCode().equals(KeyCode.ENTER)) {
            System.out.println("DONE");
        } else {

            codeString.append(event.getText());


        }


    }
}

