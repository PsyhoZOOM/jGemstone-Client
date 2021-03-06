package net.yuvideo.jgemstone.client.Controllers;

import java.net.URL;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;


import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;
import javax.smartcardio.ATR;
import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;

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

  public void scann(ActionEvent event) throws CardException {
    /*
    System.setProperty("sun.security.smartcardio.library", "libpcsclite.so.1");

    lnfo.setText("");

    lnfo.setText(codeString.toString());
    codeString = new StringBuffer();

    TerminalFactory factory = TerminalFactory.getDefault();

    List<CardTerminal> terminals = factory.terminals().list();

    System.out.println("Terminals: " + terminals);

    // get the first terminal
    CardTerminal terminal = terminals.get(0);

    // establish a connection with the card
    Card card = terminal.connect("*");
    System.out.println("card: " + card);

    // get the ATR
    ATR atr = card.getATR();
    byte[] baAtr = atr.getBytes();

    System.out.print("ATR = 0x");
    for (int i = 0; i < baAtr.length; i++) {
      System.out.printf("%02X ", baAtr[i]);
    }

    CardChannel channel = card.getBasicChannel();
    byte[] cmdApduGetCardUid = new byte[]{
        (byte) 0xFF, (byte) 0xCA, (byte) 0x00, (byte) 0x00, (byte) 0x00};

//        ResponseAPDU respApdu = channel.transmit( new CommandAPDU(cmdApduGetCardUid));
    ResponseAPDU respApdu = channel.transmit(new CommandAPDU(0xDD, 0xCA, 0x00, 0x00, 0xFF));

    if (respApdu.getSW1() == 0x90 && respApdu.getSW2() == 0x00) {

      byte[] baCardUid = respApdu.getData();

      System.out.print("Card UID = 0x");
      for (int i = 0; i < baCardUid.length; i++) {
        System.out.printf("%02X ", baCardUid[i]);
      }
    }

    card.disconnect(false);
    */

  }


  public void keyRelease(KeyEvent event) {
    if (event.getCode().equals(KeyCode.ENTER)) {
    } else {

      codeString.append(event.getText());


    }


  }


}

