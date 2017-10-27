/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.yuvideo.jgemstone.client.classes;

import net.devbase.jfreesteel.EidCard;
import net.devbase.jfreesteel.EidInfo;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.TerminalFactory;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author zoom
 */
public class SmartCardReader {

    public Image userImage;
    List<CardTerminal> terminals = null;
    private Users user;

    private CardTerminal pickTerminal(List<CardTerminal> terminals) {
        if (terminals.size() > 1) {
            System.out.println("Dostupne kartice");
            int c = 1;
            for (CardTerminal terminal : terminals) {
                System.out.format("$d) %s\n", c++, terminal);
            }

            Scanner in = new Scanner(System.in);
            while (true) {
                System.out.println("Select number:");
                System.out.flush();

                c = in.nextInt();
                if (c > 0 && c <= terminals.size()) {
                    in.close();
                    return terminals.get(c - 1);
                }
            }
        } else {
            return terminals.get(0);
        }
    }

    public void rn() {
        System.setProperty("sun.security.smartcardio.library", "libpcsclite.so.1");
        try {

            TerminalFactory factory = TerminalFactory.getDefault();
            terminals = factory.terminals().list();

            System.out.println("Using reader		: " + terminals);

            CardTerminal terminal = terminals.get(0);
            Card card = terminal.connect("*");
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

}
