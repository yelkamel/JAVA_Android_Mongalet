package leroux_m.mongalet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by leroux_m on 01/03/2015.
 */
public class DetailComposantFragment extends Fragment  {

    protected TextView type;
    protected TextView marque;
    protected TextView caracteristique;
    protected TextView prix;
    protected Button back;
    protected TextView header;
    protected Integer idComp = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View v = inflater.inflate(R.layout.details_composant, null, false);

        Bundle bundle = this.getArguments();
        idComp = bundle.getInt("idComp");
        DataBaseHelper db = new DataBaseHelper(this.getActivity());
        Composant comp = db.getComposantById(idComp);
        db.close();

        type= (TextView) v.findViewById(R.id.textViewResType);
        marque= (TextView) v.findViewById(R.id.textViewResMarq);
        caracteristique= (TextView) v.findViewById(R.id.textViewResCara);
        prix= (TextView) v.findViewById(R.id.textViewResPrix);
        back = (Button) v.findViewById(R.id.boutonAjout1);
        header = (TextView) v.findViewById(R.id.header_txtview);

        header.setText("Détails Composant");
        type.setText(comp.getType());
        marque.setText(comp.getMarque());
        caracteristique.setText(comp.getCaracteristique());
        back.setText("Retour");

        StringBuilder sb = new StringBuilder();
        for (Prix p : comp.getPrixMag())
        {
            sb.append("Coût:" +p.getValue() + " Magasin: " + p.getMagasin());
            sb.append("\n");
        }
        if (sb.length() >0)
            prix.setText(sb.toString());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();

            }
        });

        return v;
    }
    @Override
    public void onCreateOptionsMenu(
                Menu menu, MenuInflater inflater) {
            menu.clear();
          inflater.inflate(R.menu.menu_ajout_prix, menu);
        MenuItem menuItem = menu.findItem(R.id.prixMenu);
        menuItem.setIcon(R.drawable.ajouter_bleu);
         super.onCreateOptionsMenu(menu,inflater);

       	}
    private void back()
    {
        this.getActivity().getFragmentManager().popBackStack();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.prixMenu) {
            NouveauPrixFragment fragment2 = new NouveauPrixFragment();
            Bundle b = new Bundle();
            b.putInt("idComp", idComp);
            fragment2.setArguments(b);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.Main,fragment2);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
