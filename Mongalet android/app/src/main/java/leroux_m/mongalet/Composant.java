package leroux_m.mongalet;

import java.util.ArrayList;

/**
 * Created by leroux_m on 28/02/2015.
 */
public class Composant {

    private String type;
    private String marque;
    private String caracteristique;
    private ArrayList<Prix> prixMag;
    private Integer id;

    public Composant(Integer id, String type, String marque, String caracteristique) {
        this.id=id;
        this.type = type;
        this.marque = marque;
        this.caracteristique = caracteristique;
        prixMag = new ArrayList<Prix>();
    }
    public Composant( String type, String marque, String caracteristique) {

        this.type = type;
        this.marque = marque;
        this.caracteristique = caracteristique;
        prixMag = new ArrayList<Prix>();
    }
    public String getType() {
        return type;
    }

    public String getCaracteristique() {
        return caracteristique;
    }

    public String getMarque() {
        return marque;
    }

    public void setCaracteristique(String caracteristique) {
        this.caracteristique = caracteristique;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public ArrayList<Prix> getPrixMag() {
        return prixMag;
    }

    public void setPrixMag(ArrayList<Prix> prixMag) {

        this.prixMag = prixMag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}