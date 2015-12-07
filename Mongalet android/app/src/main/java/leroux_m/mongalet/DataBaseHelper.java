package leroux_m.mongalet;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by leroux_m on 01/03/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "mongalet";
    private static final String DATABASE_TABLE_LISTEACHAT = "listeAchat";
    private static final String DATABASE_TABLE_COMPOSANT = "composant";
    private static final String DATABASE_TABLE_LISTEACHATCOMP = "listeAchatComp";
    private static final String DATABASE_TABLE_PRIX ="prix";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_CREATE_LISTEACHAT = "create table "
            + DATABASE_TABLE_LISTEACHAT + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, nom STRING"
            + ")";
    private static final String DATABASE_CREATE_COMPOSANT = "create table "
            + DATABASE_TABLE_COMPOSANT + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, type STRING,"
            + "marque STRING, caracteristique STRING)";
    private static final String DATABASE_CREATE_LISTEACHAT_COMP = "create table "
            + DATABASE_TABLE_LISTEACHATCOMP + "( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "COMPOSANTID INTEGER REFERENCES composant(_id)," +
            "listeAchat INTEGER REFERENCES listeAchat(_id)," +
            "quantite INTEGER) ";
    private static final String DATABASE_CREATE_PRIX ="create table "
            + DATABASE_TABLE_PRIX + " (_id INTEGER PRIMARY KEY AUTOINCREMENT,IDCOMPOSANT INTEGER REFERENCES composant(_id)," +
            " value DOUBLE, " +
            " magasin STRING) ";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase arg0) {
        arg0.execSQL(DATABASE_CREATE_LISTEACHAT);
        arg0.execSQL(DATABASE_CREATE_COMPOSANT);
        arg0.execSQL(DATABASE_CREATE_LISTEACHAT_COMP);
        arg0.execSQL(DATABASE_CREATE_PRIX);

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        arg0.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_LISTEACHAT);
        arg0.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_COMPOSANT);
        arg0.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_LISTEACHATCOMP);
        arg0.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_PRIX);
        this.onCreate(arg0);

    }

    protected ArrayList<Composant> getAllComposant()
    {
        ArrayList<Composant> listComp = new ArrayList<Composant>();
        SQLiteDatabase DB = this.getReadableDatabase();
        String query = "SELECT _id,type,marque,caracteristique from " + DATABASE_TABLE_COMPOSANT;

        Cursor c = DB.rawQuery(query, null);
        while(c.moveToNext()) {
            Composant comp = new Composant(c.getInt(0),c.getString(1),c.getString(2),c.getString(3));
            listComp.add(comp);
        }
        c.close();
        DB.close();
        return listComp;
    }
    protected Composant getComposantById(Integer id)
    {
        Composant comp = null;
        SQLiteDatabase DB = this.getReadableDatabase();
        String query = "SELECT _id,type,marque,caracteristique from " + DATABASE_TABLE_COMPOSANT + " where _id="+id;

        Cursor c = DB.rawQuery(query, null);
        while(c.moveToNext()) {
            comp = new Composant(c.getInt(0),c.getString(1),c.getString(2),c.getString(3));
            comp.setPrixMag(getPrixByComposant(c.getInt(0)));
        }
        c.close();
        DB.close();
        return comp;
    }
    private ArrayList<Prix> getPrixByComposant(Integer composant)
    {
        ArrayList<Prix> listPrix = new ArrayList<Prix>();
        SQLiteDatabase DB = this.getReadableDatabase();
        String query = "SELECT _id,IDCOMPOSANT,value,magasin from " + DATABASE_TABLE_PRIX + " where IDCOMPOSANT="+composant;

        Cursor c = DB.rawQuery(query, null);
        while(c.moveToNext()) {
            Prix prix = new Prix(c.getDouble(2),c.getString(3));
            listPrix.add(prix);
        }
        c.close();
        DB.close();
        return listPrix;

    }
    protected ArrayList<ListeAchat> getAllListAchatId()
    {
        SQLiteDatabase DB = this.getReadableDatabase();
        String query = "SELECT _id,nom from listeAchat" ;
        ArrayList<ListeAchat> arrayListId = new ArrayList<ListeAchat>();
        Cursor c = DB.rawQuery(query, null);
        while(c.moveToNext()) {
            ListeAchat la = new ListeAchat(c.getString(1),null);
            la.setId(c.getInt(0));
            arrayListId.add(la);

        }
        return arrayListId;
    }
    protected ListeAchat getListeAchat(Integer id) {
        ListeAchat listeAchat = null;
        HashMap<Composant,Integer> listComp = new HashMap<Composant,Integer>();
        SQLiteDatabase DB = this.getReadableDatabase();
        String query = "SELECT la._id,la.nom,c._id,c.type,c.marque,c.caracteristique,lac.listeAchat,lac.quantite from "
                + " listeAchat la  JOIN listeAchatComp lac on la._id=lac.listeAchat "
                + " JOIN composant c on c._id=lac.COMPOSANTID where lac.listeAchat=" + id;

        Cursor c = DB.rawQuery(query, null);
        while (c.moveToNext()) {
            Composant comp = new Composant(c.getInt(2), c.getString(3), c.getString(4), c.getString(5));
            comp.setPrixMag(getPrixByComposant(c.getInt(2)));
            listComp.put(comp,c.getInt(7));

            listeAchat = new ListeAchat(c.getString(1), listComp);
            listeAchat.setId(c.getInt(0));

        }
        c.close();

        return listeAchat;
    }
    protected ArrayList<ListeAchat> getAllListeAchat()
    {
        ArrayList<ListeAchat> listeAchat = new ArrayList<ListeAchat>();
        HashMap<Composant,Integer> listComp = new HashMap<Composant,Integer>();
        SQLiteDatabase DB = this.getReadableDatabase();
        String query = "SELECT la._id,la.nom,c._id,c.type,c.marque,c.caracteristique,lac.listeAchat,lac.quantite from "
                + " listeAchat la  JOIN listeAchatComp lac on la._id=lac.listeAchat "
                + " JOIN composant as c ";

        Cursor c = DB.rawQuery(query, null);
        while (c.moveToNext()) {
            Composant comp = new Composant(c.getInt(2), c.getString(3), c.getString(4), c.getString(5));
            comp.setPrixMag(getPrixByComposant(c.getInt(2)));
            listComp.put(comp, c.getInt(7));

            ListeAchat Achat = new ListeAchat(c.getString(1), listComp);
            Achat.setId(c.getInt(0));
            listeAchat.add(Achat);
        }
        c.close();


        return listeAchat;
    }

    protected void insertComposant (Composant comp)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("type", comp.getType());
        values.put("marque",comp.getMarque());
        values.put("caracteristique",comp.getCaracteristique());
        DB.insert(DATABASE_TABLE_COMPOSANT, null, values);
        DB.close();
    }
    protected void insertPrix(Prix prix, Integer idComposant)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("IDCOMPOSANT", idComposant);
        values.put("value", prix.getValue());
        values.put("magasin",prix.getMagasin());
        DB.insert(DATABASE_TABLE_PRIX, null, values);
        DB.close();
    }
    protected void deleteComposant(Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE_COMPOSANT, "_id=" + id, null);
        db.close();
    }
    protected void initListeAchat(String nomListe)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nom", nomListe);

        DB.insert(DATABASE_TABLE_LISTEACHAT, null, values);
        DB.close();
    }
    protected void insertListeAchat(Integer idComposant,Integer idListeAchat, Integer quantite)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("COMPOSANTID", idComposant);
        values.put("listeAchat", idListeAchat);
        values.put("quantite", quantite);
        DB.insert(DATABASE_TABLE_LISTEACHATCOMP, null, values);
        DB.close();
    }
    protected Integer getLastIdListeAchat()
    {
        SQLiteDatabase formationDB = this.getReadableDatabase();
        String query = "SELECT ROWID from "+DATABASE_TABLE_LISTEACHAT +" order by ROWID DESC limit 1";
        Cursor c = formationDB.rawQuery(query,null);
        Integer lastId = null;
        if (c != null && c.moveToFirst()) {
            lastId = c.getInt(0); //The 0 is the column index, we only have 1 column, so the index is 0
        }
        c.close();

        return lastId;
    }
}
