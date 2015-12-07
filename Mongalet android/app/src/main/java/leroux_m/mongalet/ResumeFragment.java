package leroux_m.mongalet;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by leroux_m on 02/03/2015.
 */
public class ResumeFragment extends Fragment {
    protected TextView res;
    protected Button back;
    protected TextView header;
    protected Integer idComp = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.resume_layout, null, false);
        header = (TextView) v.findViewById(R.id.header_txtview);
        res = (TextView) v.findViewById(R.id.textViewResume);
        back = (Button) v.findViewById(R.id.boutonAjout1);

        Bundle bundle = this.getArguments();
        idComp = bundle.getInt("idComp");
        header.setText("Resumé Achat");
        back.setText("Retour");
        DataBaseHelper db = new DataBaseHelper(this.getActivity());
        ListeAchat listeAchat = db.getListeAchat(idComp);

        StringBuilder sb = new StringBuilder();
        Double prix = 0.0;

            Set cles = listeAchat.getListComposant().keySet();
            Iterator it = cles.iterator();
            while (it.hasNext()) {
                Composant cle = (Composant) it.next();
                sb.append("- Composant :\n");
                sb.append("\tType:");
                sb.append(cle.getType()+"\n");
                sb.append("\tMarque:" + cle.getMarque() +"\n\tCaract.:" + cle.getCaracteristique());// tu peux typer plus finement ici
                Log.e("all", cle.getType() + cle.getMarque() + cle.getCaracteristique());
                Object valeur = listeAchat.getListComposant().get(cle); // tu peux typer plus finement ici
                sb.append("\n\tQuantite:"+valeur + "*" + minPrix(cle.getPrixMag()).toString() + "€\n");
                prix += (minPrix(cle.getPrixMag()) * Integer.parseInt(valeur.toString()));
            }
            sb.append("--------------------------\n");
            sb.append("Prix total:"+prix.toString()+ "€\n");

        res.setText(sb.toString());
        db.close();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();

            }
        });
        return v;
    }
    private Double minPrix(ArrayList<Prix> prix)
    {
      Double min;
        if (prix.size() > 0) {
            min = prix.get(0).getValue();
            for (int i = 0; i < prix.size(); i++) {
                if (prix.get(i).getValue() < min)
                    min = prix.get(i).getValue();
            }
        }
        else
        min = 0.0;
        return min;

    }
    private void back()
    {
        this.getActivity().getFragmentManager().popBackStack();
    }
    }
