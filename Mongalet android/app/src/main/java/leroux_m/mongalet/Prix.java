package leroux_m.mongalet;

/**
 * Created by leroux_m on 28/02/2015.
 */
public class Prix {

    private Double value;
    private String magasin;

    public Prix(Double value, String magasin) {
        this.value = value;
        this.magasin = magasin;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getMagasin() {
        return magasin;
    }

    public void setMagasin(String magasin) {
        this.magasin = magasin;
    }
}
