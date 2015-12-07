package leroux_m.mongalet;

import android.app.Activity;
import android.app.Fragment;
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
 * Created by leroux_m on 01/03/2015.
 */
public class ArrayAdapterListeCompo extends ArrayAdapter<Composant> {

    private final Context context;
    private final ArrayList<Composant> listecompo;
    public ArrayAdapterListeCompo(Context context, ArrayList<Composant> listecompo)
    {

        super(context, R.layout.custom_listview_achat, listecompo);
        this.context = context;
        this.listecompo= listecompo;
    }
    protected Integer getCountList()
    {
        return listecompo.size();
    }
    public Composant getListeCompo(int groupPosition) {
        return listecompo.get(groupPosition);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Composant comp =  getListeCompo(position);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom_listview_complist, parent, false);
        TextView nomCom = (TextView)rowView.findViewById(R.id.textViewNomCompo);
        TextView Invisible = (TextView)rowView.findViewById(R.id.textViewInvisible);
        Button details = (Button) rowView.findViewById(R.id.buttonDetailsComp);

        ImageButton delete = (ImageButton) rowView.findViewById(R.id.imageButtonDeleteComp);

        Invisible.setText(comp.getId().toString());
        Invisible.setVisibility(View.INVISIBLE);

        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetail(comp.getId());

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteComp(comp.getId());

            }
        });

        nomCom.setText(comp.getType());
        return rowView;
    }
    private void showDetail(Integer id)
    {
        DetailComposantFragment fragment2 = new DetailComposantFragment();
        Bundle b = new Bundle();
        b.putInt("idComp", id);
        fragment2.setArguments(b);
        FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Main, fragment2,"detail-comp");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
    private void deleteComp(Integer id)
    {
        DataBaseHelper dataBaseHelper = new DataBaseHelper((Activity)context);
        dataBaseHelper.deleteComposant(id);
        dataBaseHelper.close();
        Fragment frg = null;
        frg = ((Activity)context).getFragmentManager().findFragmentByTag("nouvelle-list");
        final FragmentTransaction ft = ((Activity)context).getFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();
    }
  }
