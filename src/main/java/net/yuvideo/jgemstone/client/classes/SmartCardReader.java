/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yuvideo.jgemstone.client.classes;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;
import net.devbase.jfreesteel.EidCard;
import net.devbase.jfreesteel.EidInfo;

/**
 * @author zoom
 */
public class SmartCardReader {

  public Image userImage;
  List<CardTerminal> terminals = null;
  TerminalFactory factory = TerminalFactory.getDefault();
  CardTerminal terminal;
  private Users user;

  public void scan(CardTerminal terminalNo) {
    //System.setProperty("sun.security.smartcardio.library", "libpcsclite.so.1");
    //System.setProperty("sun.securuty.smartcardio.library", "/usr/local/opt/pcsc-lite/libpcsclite.1.dylib");
    try {

      Card card;
      card = terminalNo.connect("*");
      EidCard eidCard = EidCard.fromCard(card);
      EidInfo eidInfo = eidCard.readEidInfo();

      user = new Users();
      user.setIme(eidInfo.getNameFull());
      user.setJMBG(eidInfo.getPersonalNumber());
      user.setDatum_rodjenja(eidInfo.getDateOfBirth());
      user.setMesto(eidInfo.getPlace());
      user.setAdresa(eidInfo.getStreet() + " " + eidInfo.getHouseNumber());
      user.setBr_lk(eidInfo.getDocRegNo());

      this.userImage = eidCard.readEidPhoto();

    } catch (CardException ex) {
      Logger.getLogger(SmartCardReader.class.getName()).log(Level.SEVERE, null, ex);
    }

  }

  public BufferedImage toBufferedImage() {
    int w = this.userImage.getWidth(null);
    int h = this.userImage.getHeight(null);
    int type = BufferedImage.TYPE_INT_RGB; // other options
    BufferedImage dest = new BufferedImage(w, h, type);
    Graphics2D g2 = dest.createGraphics();
    g2.drawImage(this.userImage, 0, 0, null);
    g2.dispose();
    return dest;
  }

  public Users getUserData() {
    return this.user;
  }

  public Image getUserImage() {
    return this.userImage;
  }

  public List<CardTerminal> getTerminals() {
    try {
      terminals = factory.terminals().list();
      return terminals;
    } catch (CardException ex) {
      ex.printStackTrace();
    }
    return terminals;

  }


}
