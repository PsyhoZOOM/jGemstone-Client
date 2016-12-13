package JGemstone.classes;

import java.io.Serializable;

/**
 * Created by zoom on 9/1/16.
 */
public class Groups implements Serializable{
    int id;
    int br;
    String GroupName;
    Double Cena;
    int Prepaid;
    String Opis;


    public String getOpis() {
        return Opis;
    }

    public void setOpis(String opis) {
        Opis = opis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBr() {
        return br;
    }

    public void setBr(int br) {
        this.br = br;
    }

    public String getGroupName() {
        return GroupName;
    }

    public void setGroupName(String GroupName) {
        this.GroupName = GroupName;
    }

    public Double getCena() {
        return Cena;
    }

    public void setCena(Double cena) {
        Cena = cena;
    }

    public int getPrepaid() {
        return Prepaid;
    }

    public void setPrepaid(int prepaid) {
        Prepaid = prepaid;
    }

    public String toString() {
        return this.getGroupName();
    }
}
