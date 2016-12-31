package net.yuvideo.jgemstone.client.classes;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zoom on 1/30/17.
 */
public class Operaters implements Serializable {
    int id;
    String username;
    String password;
    String adresa;
    String telefon;
    String komentar;
    Boolean aktivan;
    String ime;
    String type;
    int typeNo;
    ArrayList<Operaters> operaters = new ArrayList<>();


    public ArrayList<Operaters> getOperaters() {
        return operaters;
    }

    public void setOperaters(ArrayList<Operaters> operaters) {
        this.operaters = operaters;
    }

    public Boolean getAktivan() {
        return aktivan;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(int typeNo) {
        this.typeNo = typeNo;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public Boolean isAktivan() {
        return aktivan;
    }

    public void setAktivan(Boolean aktivan) {
        this.aktivan = aktivan;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }


    public void initData(Client client) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "getOperaters");
        jsonObject = client.send_object(jsonObject);

        for (int i = 0; i < jsonObject.length(); i++) {
            Operaters opers = new Operaters();
            JSONObject jsonObject2 = jsonObject.getJSONObject(String.valueOf(i));
            opers.setId(jsonObject2.getInt("id"));
            opers.setIme(jsonObject2.getString("ime"));
            opers.setUsername(jsonObject2.getString("username"));
            opers.setAktivan(jsonObject2.getBoolean("aktivan"));
            opers.setAdresa(jsonObject2.getString("adresa"));
            opers.setKomentar(jsonObject2.getString("komentar"));
            opers.setTelefon(jsonObject2.getString("telefon"));
            opers.setType(jsonObject2.getString("type"));
            opers.setTypeNo(jsonObject2.getInt("typeNo"));
            operaters.add(opers);
        }


    }
}
