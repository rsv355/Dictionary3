package com.example.android.newsvocabdictionary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.newsvocab.dictionary.R;
import com.pixplicity.easyprefs.library.Prefs;

import net.qiujuer.genius.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import base.DBAdapter;
import base.WORD_MNG;

public class WordofDayActivity extends ActionBarActivity {

    private Toolbar toolbar;
    ListView list;
    int wordlimit;
    DBAdapter db;
    LinearLayout linear;
    public static CustomAdapter adapter;
    public static ArrayList<String> words;
    public static ArrayList<String> meang;


    public static ArrayList<String> Main_Word ;
    public static ArrayList<String> Match1 ;
    public static ArrayList<String> Match2 ;
    public static ArrayList<String> Match3 ;
    public static ArrayList<String> Match4 ;
    public static ArrayList<String> Match5 ;


    public static ArrayList<String> FinalWords ;

    String[] word_in_string = {"Word1","Word2","Word3","Word4","Word5"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordofday);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);

        toolbar.setTitle("Words of the Day");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        db= new DBAdapter(WordofDayActivity.this);
        list = (ListView)findViewById(R.id.list);
        linear = (LinearLayout)findViewById(R.id.linear);




        animation();
    }

    private void Display(Cursor c) {

        c.moveToFirst();

        while (!c.isAfterLast()) {

          //  newobj.WORD = c.getString(c.getColumnIndex("WORD"));
            Main_Word.add(c.getString(c.getColumnIndex("WORD")));
            meang.add(c.getString(c.getColumnIndex("MEANING_HIN")));

            if(c.getString(c.getColumnIndex("MATCH1"))==null){
                Match1.add("NA");
            }
            else{
                Match1.add(c.getString(c.getColumnIndex("MATCH1")));

            }

            if(c.getString(c.getColumnIndex("MATCH2"))==null){
                Match2.add("NA");
            }
            else{
                Match2.add(c.getString(c.getColumnIndex("MATCH2")));
            }

            if(c.getString(c.getColumnIndex("MATCH3"))==null){
                Match3.add("NA");
            }
            else{
                Match3.add(c.getString(c.getColumnIndex("MATCH3")));
            }

            if(c.getString(c.getColumnIndex("MATCH4"))==null){
                Match4.add("NA");
            }
            else{
                Match4.add(c.getString(c.getColumnIndex("MATCH4")));
            }

            if(c.getString(c.getColumnIndex("MATCH5"))==null){
                Match5.add("NA");
            }
            else{
                Match5.add(c.getString(c.getColumnIndex("MATCH5")));
            }





        //    AllWords.add(newobj);
         //   ActualWords.add(newobj);
            //    catg.add(c.getString(c.getColumnIndex("WORD")));
            //    meang.add(c.getString(c.getColumnIndex("MEANING_HIN")));
            c.moveToNext();
        }



        insertactualWordofdayWORDS();
    }

  /*  private void Display2(Cursor c) {

        c.moveToFirst();

        while (!c.isAfterLast()) {

            if(AllWords)
            catg.add(c.getString(c.getColumnIndex("MATCH1")));
            catg.add(c.getString(c.getColumnIndex("MATCH2")));
            catg.add(c.getString(c.getColumnIndex("MATCH3")));
            catg.add(c.getString(c.getColumnIndex("MATCH4")));
            catg.add(c.getString(c.getColumnIndex("MATCH5")));
            c.moveToNext();
        }

    }*/

void insertactualWordofdayWORDS(){

    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    wordlimit = preferences.getInt("wordlimit", 2);
    wordlimit+=1;

    FinalWords=new ArrayList<String>(wordlimit);
    words=new ArrayList<String>();

    boolean isMatch=false;

    for(int i=0;i<Main_Word.size();i++) {
        isMatch=false;

        for (int j = 0; j < Main_Word.size(); j++) {
            if (Main_Word.get(i).trim().equalsIgnoreCase(Match1.get(j).trim())) {
                isMatch=true;
                break;
            }
            else{
                isMatch=false;
            }
        }
        if(!isMatch) {
            FinalWords.add(Main_Word.get(i).trim());
        }
    }


    for(int i=0;i<wordlimit;i++){
        words.add(FinalWords.get(i));
    }


    }


    void insertdataintoDATABASE(){
        db.open();
        db.deleteRecord2();
        for (int i = 0; i < words.size(); i++) {
            db.insertRecord2(words.get(i).trim(),meang.get(i).trim());
            //   db.insertContact("dd",3);
        }
        db.close();
    }

    private void Display2(Cursor c) {

        c.moveToFirst();

        while (!c.isAfterLast()) {
            words.add(c.getString(c.getColumnIndex("WORD")));
            meang.add(c.getString(c.getColumnIndex("MEANING_HIN")));
            c.moveToNext();
        }

    }

    void loaddata(){

        try{

            Calendar cal = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String todayDate = df.format(cal.getTime());

            String Saveddate = Prefs.getString("date","null");

            String FirstTime = Prefs.getString("FirstTime","yes");

            Toast.makeText(WordofDayActivity.this,FirstTime,Toast.LENGTH_SHORT).show();

            // if word of day is open first time day
            if(FirstTime.equalsIgnoreCase("yes"))
            {
                db.open();
                Prefs.putString("FirstTime","no");
                Cursor c = db.getWordofDay();
                if (c.moveToFirst()) {
                    Display(c);
                }
                c.close();
                db.close();
                insertdataintoDATABASE();
            }
            else {
                // if word of day is open next day
                if (Saveddate.compareTo(todayDate) < 0) {
                    db.open();
                    Prefs.putString("FirstTime","no");
                    Cursor c = db.getWordofDay();
                    if (c.moveToFirst()) {
                        Display(c);
                    }
                    c.close();
                    db.close();
                    insertdataintoDATABASE();

             /*   if (date1.compareTo(date2)<0)
                {
                    System.out.println("date2 is Greater than my date1");
                }*/
                    // System.out.println("date2 is Greater than my date1");
                }

                // if word of day is open same day
                else{
                    db.open();
                    Prefs.putString("FirstTime","no");
                    Cursor c = db.getOldWordofDay();
                    if (c.moveToFirst()) {
                        Display2(c);
                    }
                    c.close();
                    db.close();
                }
            }


        }catch(Exception e){
            Log.e("exce", e.toString());
        }



    }


    @Override
    protected void onResume() {
        super.onResume();

       /* SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        wordlimit = preferences.getInt("wordlimit", 2);
        wordlimit+=1;*/

        Main_Word = new ArrayList<String>();
        meang = new ArrayList<String>();
        words = new ArrayList<String>();
        Match1 = new ArrayList<String>();
        Match2 = new ArrayList<String>();
        Match3 = new ArrayList<String>();
        Match4 = new ArrayList<String>();
        Match5 = new ArrayList<String>();

        loaddata();



        adapter = new CustomAdapter(WordofDayActivity.this, words,meang);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Prefs.remove("istextchange1");
                Intent i = new Intent(WordofDayActivity.this, WordofDayMeanPageActivity.class);
                i.putExtra("word", words.get(position).trim());
                startActivity(i);
            }
        });
    }

    public void animation(){
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        anim.reset();
        linear.clearAnimation();
        linear.startAnimation(anim);
    }


    public class CustomAdapter extends BaseAdapter {
        ArrayList<String> result;
        ArrayList<String> result2;

        Context context;
        private LayoutInflater inflater = null;

        public CustomAdapter(Context ctx, ArrayList<String> prgmNameList, ArrayList<String> prgmNameList2) {
            // TODO Auto-generated constructor stub
            result = prgmNameList;
            result2 = prgmNameList2;

            this.context = ctx;
            //  inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return result.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_list_category, null);
            }

          /*  View rowView;
            rowView = inflater.inflate(R.layout.item_list_example, null);
*/
            TextView txt = (TextView) convertView.findViewById(R.id.txt1);
            txt.setText(result.get(position).trim() + " - " + result2.get(position).trim());

          /*  holder.tv=(TextView) rowView.findViewById(R.id.txt1);
            holder.tv.setText(result[position]);
*/

            return convertView;
        }
    }


    //end of main class
}
