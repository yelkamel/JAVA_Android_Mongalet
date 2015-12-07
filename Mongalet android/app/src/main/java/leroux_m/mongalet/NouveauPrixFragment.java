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
public class NouveauPrixFragment extends Fragment {

    protected Button valider;
    protected EditText val;
    protected EditText mag;
    protected TextView header;
    protected TextView chpVideVal;
    protected TextView chpVidMag;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_prix, null, false);
        valider = (Button) v.findViewById(R.id.boutonAjout1);
        val = (EditText) v.findViewById(R.id.editTextVal);
        mag = (EditText) v.findViewById(R.id.editTextMag);
        header = (TextView) v.findViewById(R.id.header_txtview);
        chpVideVal = (TextView) v.findViewById(R.id.textViewChpVideVal);
        chpVidMag= (TextView) v.findViewById(R.id.textViewChpVideMag);

        header.setText("Nouveau Prix");
        chpVidMag.setVisibility(View.GONE);
        chpVideVal.setVisibility(View.GONE);
        valider.setText("Ajouter prix");

        Bundle bundle = this.getArguments();
        final Integer myInt = bundle.getInt("idComp");
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajouterPrix(myInt);

            }
        });
        return v;
    }
    private void ajouterPrix(Integer id)
    {
        if (val.getText().length() > 0 && mag.getText().length() > 0) {
            DataBaseHelper db = new DataBaseHelper(this.getActivity());
            db.insertPrix(new Prix(Double.parseDouble(val.getText().toString()),mag.getText().toString()),id);
            db.close();
            getActivity().getFragmentManager().popBackStack();
        }
        else
        {
            if (!(val.getText().length() > 0))
            {
                val.setBackgroundResource(R.drawable.edittext_red_custom);
                chpVideVal.setVisibility(View.VISIBLE);
            }
            if (!(mag.getText().length() > 0))
            {
                mag.setBackgroundResource(R.drawable.edittext_red_custom);
                chpVidMag.setVisibility(View.VISIBLE);
            }
        }
    }


}
