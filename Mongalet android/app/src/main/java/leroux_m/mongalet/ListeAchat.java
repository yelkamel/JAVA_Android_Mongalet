package leroux_m.mongalet;
import java.util.HashMap;

/**
 * Created by leroux_m on 28/02/2015.
 */
public class ListeAchat {
    private String nomListe;
    private HashMap<Composant,Integer> listComposant;
    private Integer id;

    public ListeAchat(String nomListe, HashMap<Composant,Integer> listComposant) {
        this.nomListe = nomListe;
        this.listComposant = listComposant;
    }

    public String getNomListe() {
        return nomListe;
    }

    public void setNomListe(String nomListe) {
        this.nomListe = nomListe;
    }

    public HashMap<Composant,Integer> getListComposant() {
        return listComposant;
    }

    public void setListComposant(HashMap<Composant,Integer> listComposant) {
        this.listComposant = listComposant;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
