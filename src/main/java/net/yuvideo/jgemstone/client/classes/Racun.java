package net.yuvideo.jgemstone.client.classes;

import javafx.scene.image.Image;
import org.json.JSONObject;

public class Racun {
    String zaMesec;
    String nazivUsluge;
    String datumZaduzenja;
    String sifraKorisnika;
    String mesec;
    String mestoDatumIzdavanja;
    String datumPrometa;
    String rokZaPlacanje;
    String adresaUsluge;
    String adresaRacuna;
    String zaPeriod;

    Image qrCode;

    int kolicina;
    double iznos;
    double popust;
    double osnovica;
    double osnovicaUkupno;
    double stopaPDV;
    double pdv;
    double pdvUkupno;
    double ukupno;
    double ukupnoUkupno;

    double prethodniDug;
    double ukupanDug;


    int userID;
    Client client;

    public Racun(int userID, String zaMesec, Client client) {
        this.userID = userID;
        this.zaMesec = zaMesec;
        this.client = client;
        getData();
    }

    public void getData() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "getUserRacun");
        jsonObject.put("userID", this.userID);
        jsonObject.put("zaMesec", this.zaMesec);

        jsonObject = client.send_object(jsonObject);

        System.out.println(String.format("Za Mesec: %s",this.zaMesec));
        for(int i =0; i < jsonObject.length(); i++){
            System.out.println(jsonObject.getString(String.valueOf(i)));
        }

    }
    
    public void getPages(){
        createPages();
    }

    private void createPages() {

    }
}
