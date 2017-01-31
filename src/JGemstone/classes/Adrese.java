package JGemstone.classes;

import java.io.Serializable;

/**
 * Created by zoom on 1/11/17.
 */
public class Adrese implements Serializable {
    private int id;
    private String nazivAdrese;
    private String brojAdrese;
    private int idMesta;
    private String brojMesta;
    private String nazivMesta;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazivAdrese() {
        return nazivAdrese;
    }

    public void setNazivAdrese(String nazivAdrese) {
        this.nazivAdrese = nazivAdrese;
    }

    public String getBrojAdrese() {
        return brojAdrese;
    }

    public void setBrojAdrese(String brojAdrese) {
        this.brojAdrese = brojAdrese;
    }

    public int getIdMesta() {
        return idMesta;
    }

    public void setIdMesta(int idMesta) {
        this.idMesta = idMesta;
    }

    public String getBrojMesta() {
        return brojMesta;
    }

    public void setBrojMesta(String brojMesta) {
        this.brojMesta = brojMesta;
    }

    public String getNazivMesta() {
        return nazivMesta;
    }

    public void setNazivMesta(String nazivMesta) {
        this.nazivMesta = nazivMesta;
    }

    @Override
    public String toString() {
        return this.nazivAdrese;
    }

}
