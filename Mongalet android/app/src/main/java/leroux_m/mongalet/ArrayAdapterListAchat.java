package leroux_m.mongalet;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by leroux_m on 28/02/2015.
 */
public class ArrayAdapterListAchat extends ArrayAdapter<ListeAchat> {

    private final Context context;
    private final ArrayList<ListeAchat> listeAchats;
    public ArrayAdapterListAchat(Context context, ArrayList<ListeAchat> listeAchats)
    {

        super(context, R.layout.custom_listview_achat, listeAchats);
        this.context = context;
        this.listeAchats= listeAchats;
    }

    public ListeAchat getListeAchat(int groupPosition) {
        return listeAchats.get(groupPosition);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ListeAchat liste =  getListeAchat(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_listview_achat, parent, false);
        TextView nomListe = (TextView) rowView.findViewById(R.id.textViewNomListe);
        Button resume = (Button) rowView.findViewById(R.id.button);
        ImageButton plus  = (ImageButton) rowView.findViewById(R.id.imageButton);
        nomListe.setText(liste.getNomListe());
        resume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResume(liste.getId());

            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAdd(liste.getId());

            }
        });
        return rowView;
    }
    private void showResume(Integer id)
    {
        ResumeFragment fragment2 = new ResumeFragment();
        Bundle b = new Bundle();
        b.putInt("idComp", id);
        fragment2.setArguments(b);
        FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Main, fragment2,"detail-comp");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void showAdd(Integer id)
    {
        BouttonPlusFragment fragment2 = new BouttonPlusFragment();
        Bundle b = new Bundle();
        b.putInt("idAdd", id);
        fragment2.setArguments(b);
        FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Main, fragment2,"add-comp");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
