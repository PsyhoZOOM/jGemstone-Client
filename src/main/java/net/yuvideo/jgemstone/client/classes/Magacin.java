package net.yuvideo.jgemstone.client.classes;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by PsyhoZOOM@gmail.com on 2/9/18.
 */
public class Magacin implements Serializable {
    int id;
    String naziv;
    String opis;
    boolean glavniMagacin;

    ArrayList<Magacin> magaciniArr = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public boolean isGlavniMagacin() {
        return glavniMagacin;
    }

    public void setGlavniMagacin(boolean glavniMagacin) {
        this.glavniMagacin = glavniMagacin;
    }

    private void initMagacini(Client client) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "getMagacini");

        jsonObject = client.send_object(jsonObject);

        for (int i = 0; i < jsonObject.length(); i++) {
            JSONObject magacinObj = (JSONObject) jsonObject.get(String.valueOf(i));
            Magacin magacin = new Magacin();
            magacin.setId(magacinObj.getInt("id"));
            magacin.setNaziv(magacinObj.getString("naziv"));
            magacin.setOpis(magacinObj.getString("opis"));
            magacin.setGlavniMagacin(magacinObj.getBoolean("glavniMagacin"));
            magaciniArr.add(magacin);
        }
    }

    public ArrayList<Magacin> getMagaciniArr(Client client) {
        initMagacini(client);
        return this.magaciniArr;
    }

    public Magacin getMagacin(int id, Client client) {
        initMagacini(client);
        for (Magacin magacin : this.magaciniArr) {
            if (magacin.getId() == id) {
                return magacin;
            }
        }
        return null;
    }

    public ArrayList<Magacin> getMagacin(String naziv, Client client) {
        initMagacini(client);
        ArrayList<Magacin> magacinArrayList = new ArrayList<>();
        for (Magacin magacin : this.magaciniArr) {
            if (magacin.getNaziv().contains(naziv)) {

                magacinArrayList.add(magacin);
            }
        }
        return magacinArrayList;
    }
}
