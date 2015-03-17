package base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Android on 08-12-2014.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    public static final String KEY_ROWID = "_id";
   /* public static final String KEY_NAME = "playername";
    public static final String KEY_SCORE = "score";*/
    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "MyDB1";

    private static final String DATABASE_TABLE = "D_Word_ENG_HIN";
    private static final String DATABASE_TABLE_History = "D_Word_History";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_HISTORY =
            "create table D_Word_History (_id integer primary key autoincrement, "
                    + "WORD String not null," +
                      "MEANING text );";


    private static final String DATABASE_CREATE_WORD_OF_DAY =
            "create table D_Word_Word_Of_Day (_id integer primary key autoincrement, "+
                    "WORD String not null," +
                    "MEANING text );";


    private static final String DATABASE_CREATE =
            "create table D_Word_ENG_HIN (_id integer primary key autoincrement, "
                    + "CATG_ID String not null," +
                      "WORD text not null," +
                      "PRON_ENG text," +
                      "MEANING_HIN text not null," +
                      "MEANING2 text," +
                      "MEANING3 text," +
                      "MEANING4 text," +
                      "MEANING5 text ," +
                      "EX1 text ," +
                      "EX2 text ," +
                      "EX3 text ," +
                      "EX4 text ," +
                      "EX5 text ," +
                      "MATCH1 text ," +
                      "MATCH2 text ," +
                      "MATCH3 text ," +
                      "MATCH4 text ," +
                      "MATCH5 text ," +
                      "HIN_EX1 text ," +
                      "HIN_EX2 text ," +
                      "HIN_EX3 text ," +
                      "HIN_EX4 text ," +
                      "HIN_EX5 text );";

    private final Context context;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
                db.execSQL(DATABASE_CREATE_HISTORY);
                db.execSQL(DATABASE_CREATE_WORD_OF_DAY);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS D_Word_ENG_HIN");
            db.execSQL("DROP TABLE IF EXISTS D_Word_History");
            onCreate(db);
        }
    }


    //---opens the database---
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }


    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    /*CATG_ID number not null," +
            "WORD text not null," +
            "MEANING_HIN text not null," +
            "PRON_ENG ," +
            "EX1 text ," +
            "EX2 text ," +
            "EX3 text ," +
            "EX4 text ," +
            "EX5 text ," +
            "MATCH1 text ," +
            "MATCH2 text ," +
            "MATCH3 text ," +
            "MATCH4 text ," +
            "MATCH5 text );";*/



    //---insert a contact into the database---
    public long insertRecord(String CATG_ID,String WORD, String PRON_ENG,String MEANING_HIN
                              ,String MEANING2,String MEANING3,String MEANING4,String MEANING5
                              ,String EX1,String EX2,String EX3,String EX4,String EX5
                              ,String MATCH1,String MATCH2,String MATCH3,String MATCH4,String MATCH5
                              ,String HIN_EX1,String HIN_EX2,String HIN_EX3,String HIN_EX4,String HIN_EX5)
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put("CATG_ID", CATG_ID);//1
        initialValues.put("WORD", WORD);//2

        initialValues.put("PRON_ENG", PRON_ENG);//3

        initialValues.put("MEANING_HIN", MEANING_HIN);//4

        initialValues.put("MEANING2", MEANING2);//5
        initialValues.put("MEANING3", MEANING3);//6
        initialValues.put("MEANING4", MEANING4);//7
        initialValues.put("MEANING5", MEANING5);//8

        initialValues.put("EX1", EX1);//9
        initialValues.put("EX2", EX2);//10
        initialValues.put("EX3", EX3);//11
        initialValues.put("EX4", EX4);//12
        initialValues.put("EX5", EX5);//13

        initialValues.put("MATCH1", MATCH1);//14
        initialValues.put("MATCH2", MATCH2);//15
        initialValues.put("MATCH3", MATCH3);//16
        initialValues.put("MATCH4", MATCH4);//17
        initialValues.put("MATCH5", MATCH5);//18

        initialValues.put("HIN_EX1", HIN_EX1);//19
        initialValues.put("HIN_EX2", HIN_EX2);//20
        initialValues.put("HIN_EX3", HIN_EX3);//21
        initialValues.put("HIN_EX4", HIN_EX4);//22
        initialValues.put("HIN_EX5", HIN_EX5);//23

        Log.e("insert ","ok");

        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public long insertRecord2(String WORD,String MEANING)
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put("WORD", WORD);//1
        initialValues.put("MEANING", MEANING);//2

        Log.e("insert in meaning ","ok");

        return db.insert(DATABASE_CREATE_WORD_OF_DAY, null, initialValues);
    }

    //---deletes a particular contact---
    public void deleteRecord( )
    {
        Log.e("record ddelted ","ok");
        db.execSQL("delete  from "+ DATABASE_TABLE);
      //  return db.delete(DATABASE_TABLE,null );
    }


    public void deleteRecord2( )
    {
        Log.e("record ddelted ","ok");
        db.execSQL("delete  from "+ DATABASE_CREATE_WORD_OF_DAY);
        //  return db.delete(DATABASE_TABLE,null );
    }
    //---retrieves all the contacts---
    /*public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,KEY_SCORE},
                null, null, null, null, null);
    }
*/






    //---retrieves a particular contact---
   public Cursor getRecord(String rowId) throws SQLException
    {
        String selectQuery = "SELECT * FROM D_Word_ENG_HIN WHERE UPPER(WORD) ="+"\""+rowId.toString().trim().toUpperCase()+"\"";
        Cursor cursor = db.rawQuery(selectQuery, null);


        /*Cursor mCursor =db.query(true, DATABASE_TABLE, new String[] {
                        KEY_NAME, KEY_SCORE}, KEY_NAME + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }*/
        return cursor;
    }

    public Cursor getCategory(String CatgId) throws SQLException
    {
        String selectQuery = "SELECT * FROM D_Word_ENG_HIN WHERE CATG_ID ="+"\""+CatgId.toString().trim()+"\"";
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

    public Cursor getAllCategory() throws SQLException
    {
        String selectQuery = "SELECT * FROM D_Word_ENG_HIN";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }



    public Cursor getHistoryWord(String WORD) throws SQLException
    {
        String selectQuery = "SELECT * FROM D_Word_History WHERE WORD ="+"\""+WORD.toString().trim()+"\"";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }

    public Cursor getWordofDay( ) throws SQLException
    {
        String selectQuery = "SELECT * FROM D_Word_ENG_HIN WHERE WORD NOT IN (Select WORD From D_Word_History) ";
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }


    public Cursor getAllHistoryWord() throws SQLException
    {
        String selectQuery = "SELECT * FROM D_Word_History";
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }

    public Cursor getOldWordofDay() throws SQLException
    {
        String selectQuery = "SELECT * FROM D_Word_Word_Of_Day";
        Cursor cursor = db.rawQuery(selectQuery, null);

        return cursor;
    }


    public long insertHistoryRecord(String WORD,String MEANING)
    {
        ContentValues initialValues = new ContentValues();

        initialValues.put("WORD", WORD);//1
        initialValues.put("MEANING", MEANING);//2

        Log.e("insert ","ok2");

        return db.insert(DATABASE_TABLE_History, null, initialValues);
    }
/*
    //---updates a contact---
    public boolean updateContact(long rowId, String name, int email)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_SCORE, email);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }*/
}