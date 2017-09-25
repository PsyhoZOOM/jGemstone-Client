package net.yuvideo.jgemstone.client.classes;

import java.io.Serializable;

/**
 * Created by PsyhoZOOM@gmail.com on 7/20/17.
 */
public class StalkerRestAPI2 implements Serializable {
    int id;
    String end_date;
    String account_balance;
    String additional_service_on;
    String ip;
    String ls;
    String online;
    String last_active;
    String login;
    String version;
    String mac;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getAccount_balance() {
        return account_balance;
    }

    public void setAccount_balance(String account_balance) {
        this.account_balance = account_balance;
    }

    public String getAdditional_service_on() {
        return additional_service_on;
    }

    public void setAdditional_service_on(String additional_service_on) {
        this.additional_service_on = additional_service_on;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLs() {
        return ls;
    }

    public void setLs(String ls) {
        this.ls = ls;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public String getLast_active() {
        return last_active;
    }

    public void setLast_active(String last_active) {
        this.last_active = last_active;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
