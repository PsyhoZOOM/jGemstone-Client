package net.yuvideo.jgemstone.client.classes;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by PsyhoZOOM@gmail.com on 2/14/18.
 */
public class ArtikliTracking {
    int id;
    String artikal;
    int kolicina;
    int sourceMag;
    int destMag;
    String source;
    String dest;
    String date;
    String datum;
    String operater;
    String opis;
    int artikalID;
    boolean user;
    int uniqueID;


    ArrayList<ArtikliTracking> artikliTrackingArrayList;

    public void initArtikle(Client client, int artID, int magID, int uniqueID) {
        JSONObject obj = new JSONObject();
        obj.put("action", "getArtikliTracking");
        obj.put("artiklID", artID);
        obj.put("magID", magID);
        obj.put("uniqueID", uniqueID);
        obj = client.send_object(obj);

        setArrayArt(obj);

    }

    public ArrayList<ArtikliTracking> getArtikliTrackingArrayList() {
        return artikliTrackingArrayList;
    }


    public int getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtikal() {
        return artikal;
    }

    public void setArtikal(String artikal) {
        this.artikal = artikal;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public int getSourceMag() {
        return sourceMag;
    }

    public void setSourceMag(int sourceMag) {
        this.sourceMag = sourceMag;
    }

    public int getDestMag() {
        return destMag;
    }

    public void setDestMag(int destMag) {
        this.destMag = destMag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public int getArtikalID() {
        return artikalID;
    }

    public void setArtikalID(int artikalID) {
        this.artikalID = artikalID;
    }

    private void setArrayArt(JSONObject arrayArt) {
        artikliTrackingArrayList = new ArrayList<>();
        for (int i = 0; i < arrayArt.length(); i++) {
            artikliTrackingArrayList.add(i, getArtiklObj((JSONObject) arrayArt.get(String.valueOf(i))));
        }
    }

    private ArtikliTracking getArtiklObj(JSONObject artikal) {
        ArtikliTracking art = new ArtikliTracking();
        art.setId(artikal.getInt("id"));
        art.setDatum(artikal.getString("date"));
        art.setArtikal(artikal.getString("artikalNaziv"));
        art.setSourceMag(artikal.getInt("sourceID"));
        art.setDestMag(artikal.getInt("destinationID"));
        art.setOperater(artikal.getString("operName"));
        art.setOpis(artikal.getString("message"));
        art.setSource(artikal.getString("source"));
        art.setDest(artikal.getString("destination"));
        art.setUser(artikal.getBoolean("isUser"));
        art.setKolicina(artikal.getInt("kolicina"));
        art.setArtikalID(artikal.getInt("artikalID"));
        art.setUniqueID(artikal.getInt("uniqueID"));


        return art;
    }
}
