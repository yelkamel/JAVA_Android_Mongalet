package leroux_m.mongalet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by leroux_m on 01/03/2015.
 */
public class NouvelleListeAchatFragment extends Fragment{

    protected Button valider;
    protected TextView header;
    protected EditText nomList;
    protected ImageButton ajoutComposant;
    protected TextView champVide;
    protected ListView listView;
    protected ArrayAdapterListeCompo adapterListeCompo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.ajout_liste_achat, null, false);
        valider = (Button) v.findViewById(R.id.boutonAjout1);
        header = (TextView) v.findViewById(R.id.header_txtview);
        nomList = (EditText) v.findViewById(R.id.editTextNomListe);
        ajoutComposant = (ImageButton) v.findViewById(R.id.imageButtonAjoutCompPlus);
        champVide = (TextView) v.findViewById(R.id.editTextChampVide);
        listView = (ListView) v.findViewById(R.id.listViewCompPlus);

        valider.setText("Valider");
        header.setText("Nouvelle liste d\'achat");
        champVide.setVisibility(View.GONE);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutList();

            }
        });
        ajoutComposant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutComposant();

            }
        });
        adapterListeCompo = new ArrayAdapterListeCompo(this.getActivity(),getListcomposant());
        listView.setAdapter(adapterListeCompo);
        adapterListeCompo.notifyDataSetChanged();
        return v;
    }
    @Override
    public void onResume()
    {
        super.onResume();
        adapterListeCompo.notifyDataSetChanged();
    }
    private void ajoutList() {
        if (!nomList.getText().toString().equals(null) && !nomList.getText().toString().equals("")) {
           DataBaseHelper db = new DataBaseHelper(getActivity());
            db.initListeAchat(nomList.getText().toString());
            Integer idList = db.getLastIdListeAchat();
            for(int i=0;i<adapterListeCompo.getCountList();i++){
                View view=listView.getChildAt(i);
                EditText editText= (EditText) view.findViewById(R.id.quantite);
                TextView tv = (TextView) view.findViewById(R.id.textViewInvisible);
                String string=editText.getText().toString();
                if(!string.equals("")) {
                    db.insertListeAchat(Integer.parseInt(tv.getText().toString()),idList, Integer.parseInt(string));
                }

            }
            db.close();
            this.getActivity().getFragmentManager().popBackStack();
        } else {
            nomList.setBackgroundResource(R.drawable.edittext_red_custom);
            champVide.setVisibility(View.VISIBLE);
        }

    }
    private void ajoutComposant()
    {
        NouveauComposantFragment fragment2 = new NouveauComposantFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Main,fragment2);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private ArrayList<Composant> getListcomposant()
    {
        ArrayList<Composant> arrayList = new ArrayList<Composant>();
        DataBaseHelper db = new DataBaseHelper(this.getActivity());
        arrayList = db.getAllComposant();
        db.close();
        return arrayList;
    }


}
