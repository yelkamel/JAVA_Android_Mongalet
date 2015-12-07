package leroux_m.mongalet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by leroux_m on 01/03/2015.
 */
public class ListeAchatFragment extends Fragment {

       protected ListView lv;
       protected ArrayAdapterListAchat adapterListAchat;
    protected Button nvlleList;
     @Override
      public View onCreateView(LayoutInflater inflater,
                         ViewGroup container, Bundle savedInstanceState) {

         View v = inflater.inflate(
                 R.layout.fragment_list_achat, container, false);
         lv = (ListView) v.findViewById(R.id.listViewListAchat);

         DataBaseHelper db = new DataBaseHelper(getActivity());
         adapterListAchat = new ArrayAdapterListAchat(getActivity(),db.getAllListAchatId());
         lv.setAdapter(adapterListAchat);
         adapterListAchat.notifyDataSetChanged();

         db.close();

         nvlleList = (Button) v.findViewById(R.id.boutonAjout1);

         nvlleList.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 NouvelleListeAchatFragment fragment2 = new NouvelleListeAchatFragment();

                 FragmentManager fragmentManager = getFragmentManager();
                 FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                 //fragmentTransaction.hide(f2);
                 //  fragmentTransaction.show(fragment2);
                 fragmentTransaction.replace(R.id.Main, fragment2,"nouvelle-list");
                 fragmentTransaction.addToBackStack(null);
                 fragmentTransaction.commit();

             }
         });
         return v;
     }
    @Override
    public void onResume()
    {
        super.onResume();
        adapterListAchat.notifyDataSetChanged();
    }
}
