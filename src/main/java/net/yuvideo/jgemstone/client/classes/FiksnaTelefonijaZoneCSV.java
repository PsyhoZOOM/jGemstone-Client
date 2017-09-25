package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 5/29/17.
 */
public class FiksnaTelefonijaZoneCSV implements Serializable {
    int id;
    String country;
    String description;
    String zone;
    int CenaZoneID;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public int getCenaZoneID() {
        return CenaZoneID;
    }

    public void setCenaZoneID(int cenaZoneID) {
        CenaZoneID = cenaZoneID;
    }
}
