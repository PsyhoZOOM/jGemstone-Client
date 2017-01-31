package JGemstone.classes;

import java.io.Serializable;

/**
 * Created by zoom on 1/11/17.
 */
public class Mesta implements Serializable {
    private int id;
    private String nazivMesta;
    private String brojMesta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazivMesta() {
        return nazivMesta;
    }

    public void setNazivMesta(String nazivMesta) {
        this.nazivMesta = nazivMesta;
    }

    public String getBrojMesta() {
        return brojMesta;
    }

    public void setBrojMesta(String brojMesta) {
        this.brojMesta = brojMesta;
    }

    public String toString() {
        return this.nazivMesta + ", " + this.getBrojMesta();
    }
}
