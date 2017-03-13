package JGemstone.classes;

/**
 * Created by zoom on 8/3/16.
 */
public class tiProperty {
    String value;
    int id;
    String naziv;


    public tiProperty(String value, int id, String naziv) {
        this.value = value;
        this.id = id;
        this.naziv = naziv;
    }

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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}
