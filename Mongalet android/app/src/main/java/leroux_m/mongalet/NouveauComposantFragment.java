package leroux_m.mongalet;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by leroux_m on 01/03/2015.
 */
public class NouveauComposantFragment extends Fragment {

    protected Button valider;
    protected EditText type;
    protected EditText marque;
    protected EditText caracteristique;
    protected TextView chpVidetype;
    protected TextView chpVidemarque;
    protected TextView chpVidecara;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.ajout_composant, null, false);
        valider = (Button) v.findViewById(R.id.boutonAjout1);
        type = (EditText ) v.findViewById(R.id.editTextType);
        marque =(EditText ) v.findViewById(R.id.editTextMarque);
        caracteristique = (EditText ) v.findViewById(R.id.editTextCara);
        chpVidetype = (TextView) v.findViewById(R.id.textViewChpVideType);
        chpVidemarque= (TextView) v.findViewById(R.id.textViewChpVideMarque);
        chpVidecara= (TextView) v.findViewById(R.id.textViewChampVideCar);

        valider.setText("Valider");
        chpVidetype.setVisibility(View.GONE);
        chpVidemarque.setVisibility(View.GONE);
        chpVidecara.setVisibility(View.GONE);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajoutComposant();

            }
        });
        return v;
    }
    private void ajoutComposant() {

        if (type.getText().length() > 0 && marque.getText().length() > 0 && caracteristique.getText().length() > 0) {
            DataBaseHelper db = new DataBaseHelper(this.getActivity());
            db.insertComposant(new Composant(type.getText().toString(),marque.getText().toString(),caracteristique.getText().toString()));
            db.close();
            getActivity().getFragmentManager().popBackStack();
        }
        else
        {
            if (!(type.getText().length() > 0))
            {
                type.setBackgroundResource(R.drawable.edittext_red_custom);
                chpVidetype.setVisibility(View.VISIBLE);
            }
            if (!(marque.getText().length() > 0))
            {
                marque.setBackgroundResource(R.drawable.edittext_red_custom);
                chpVidemarque.setVisibility(View.VISIBLE);
            }
            if (!(caracteristique.getText().length() > 0))
            {
                caracteristique.setBackgroundResource(R.drawable.edittext_red_custom);
                chpVidecara.setVisibility(View.VISIBLE);
            }
        }
    }
}
