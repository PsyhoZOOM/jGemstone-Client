package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 8/14/17.
 */
public class IPTVPaketi implements Serializable {
    int id;
    int iptv_id;
    String external_id;
    String name;
    String user_default;
    String description;
    Double cena;
    int prekoracenje;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExternal_id() {
        return external_id;
    }

    public void setExternal_id(String external_id) {
        this.external_id = external_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_default() {
        return user_default;
    }

    public void setUser_default(String user_default) {
        this.user_default = user_default;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public int getIptv_id() {
        return iptv_id;
    }

    public void setIptv_id(int iptv_id) {
        this.iptv_id = iptv_id;
    }

    public int getPrekoracenje() {
        return prekoracenje;
    }

    public void setPrekoracenje(int prekoracenje) {
        this.prekoracenje = prekoracenje;
    }
}
