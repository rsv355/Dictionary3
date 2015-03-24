package com.example.android.newsvocabdictionary;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
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


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


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
    public static ArrayList<String> groupname;

    public static ArrayList<String> oldwords;

    public static ArrayList<String> FinalWords ;
    ArrayList<String> newwords;


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

void writedataonSDcard(){

    try {
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/com.newsvocab.dictionary/");

        if (!dir.exists())
        {
            dir.mkdirs();
        }

        final File myFile = new File(dir, "dict" + ".dat");
        myFile.createNewFile();

        FileOutputStream fOut = new FileOutputStream(myFile);
        OutputStreamWriter myOutWriter =
                new OutputStreamWriter(fOut);

        for(int i=0;i<groupname.size();i++) {
            myOutWriter.append(groupname.get(i).trim()+",");
        }

        myOutWriter.close();
        fOut.close();
       /* Toast.makeText(getBaseContext(),"Done writing SD 'mysdfile.txt'", Toast.LENGTH_LONG).show();*/
    } catch (Exception e) {
        Toast.makeText(getBaseContext(), e.getMessage(),
                Toast.LENGTH_LONG).show();
        Log.e("file exc",e.toString());
    }
}

void readdatafromSDcard(){
    oldwords = new ArrayList<String>();
    File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/com.newsvocab.dictionary/");
    final File myFile2 = new File(dir, "dict" + ".dat");

    String filename=myFile2.getName().toString() ;

    StringBuffer stringBuffer = new StringBuffer();
    String aDataRow = "";
    String aBuffer = "";
    try {
        File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Android/data/com.newsvocab.dictionary/"+filename);
        FileInputStream fIn = new FileInputStream(myFile);
        BufferedReader myReader = new BufferedReader(
                new InputStreamReader(fIn));

        while ((aDataRow = myReader.readLine()) != null) {
            aBuffer += aDataRow + "\n";

        }
        myReader.close();

    } catch (IOException e) {
        e.printStackTrace();
    }
    /*Toast.makeText(getApplicationContext

            (),aBuffer,Toast.LENGTH_LONG).show();
*/
    char[] letters = aBuffer.toCharArray();
    int count=0;
    String temp="";

    for(int i=0;i<letters.length;i++){
        if (letters[i] == ','){
        count++;
        oldwords.add(temp);
        temp="";
        }
        else{
            temp+=letters[i];
        }
    }


}



    private void Display(Cursor c) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        wordlimit = preferences.getInt("wordlimit", 2);
        wordlimit+=1;

        c.moveToFirst();
        while (!c.isAfterLast()) {

            words.add(c.getString(c.getColumnIndex("WORD")));
            meang.add(c.getString(c.getColumnIndex("MEANING_HIN")));
            groupname.add(c.getString(c.getColumnIndex("GROUP_NAME")));
            c.moveToNext();
        }
    }




    void checkfromSDcardfile(){
        newwords = new ArrayList<String>();
        readdatafromSDcard();
        for(int i=0;i<oldwords.size();i++){
           if(oldwords.get(i).trim().equalsIgnoreCase(groupname.get(i).trim())){

           }
            else{
                newwords.add(groupname.get(i).trim());
           }

       }
    }



void insertdataintoDATABASE(){
        db.open();
        db.deleteRecord2();
        for (int i = 0; i < words.size(); i++) {
            db.insertRecord2(words.get(i).trim(),meang.get(i).trim(),groupname.get(i).trim());
            //   db.insertContact("dd",3);
        }
        db.close();
    }


    private void Display2(Cursor c1) {
        meang = new ArrayList<String>();
        words = new ArrayList<String>();
        groupname= new ArrayList<String>();

//   !c1.isAfterLast()||
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        wordlimit = preferences.getInt("wordlimit", 2);
        wordlimit+=1;

        c1.moveToFirst();
        while (!c1.isAfterLast()) {


            words.add(c1.getString(c1.getColumnIndex("WORD")));
            meang.add(c1.getString(c1.getColumnIndex("MEANING")));
            groupname.add(c1.getString(c1.getColumnIndex("GROUP_NAME")));
            c1.moveToNext();
        }


        //   adapter.notifyDataSetChanged();


    }


    void loaddata(){


        try{


            Date td = new Date();
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String todayDateString = df.format(cal.getTime());
            String SaveddateString = Prefs.getString("date1","null");


            Date savedDate = df.parse(SaveddateString);
            Date todayDate = df.parse(todayDateString);




            String FirstTime = Prefs.getString("FirstTime","yes");
            //    Toast.makeText(WordofDayActivity.this,""+savedDate+"="+todayDate,Toast.LENGTH_SHORT).show();


            // if word of day is open first time day
            if(FirstTime.equalsIgnoreCase("yes"))
            {
                 // Toast.makeText(WordofDayActivity.this,"First time",Toast.LENGTH_SHORT).show();


                db.open();
                Prefs.putString("FirstTime","no");
                Cursor c = db.getWordofDay();
                if (c.moveToFirst()) {
                    Display(c);
                }
                c.close();
                db.close();
                writedataonSDcard();
                insertdataintoDATABASE();

                checkfromSDcardfile();
            }
            else {
                // if word of day is open next day
                if (todayDate.after(savedDate)) {
                //       Toast.makeText(WordofDayActivity.this,"new date",Toast.LENGTH_SHORT).show();


                    db.open();
                    Prefs.putString("FirstTime","no");
                    Prefs.putString("date1",todayDateString);
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
                   //  Toast.makeText(WordofDayActivity.this,"old date",Toast.LENGTH_SHORT).show();
                    readdatafromSDcard();

                    db.open();
                    Prefs.putString("FirstTime","no");
                    Cursor c1 = db.getOldWordofDay();
                    if (c1.moveToFirst()) {
                        Display2(c1);
                    }
                    c1.close();
                    db.close();
                }
            }




        }catch(Exception e){
            Log.e("exce", e.toString());
          //  Toast.makeText(WordofDayActivity.this,e.toString(),Toast.LENGTH_SHORT).show();


        }






    }




    @Override
    protected void onResume() {
        super.onResume();

        meang = new ArrayList<String>();
        words = new ArrayList<String>();
        groupname= new ArrayList<String>();

        loaddata();






        adapter = new CustomAdapter(WordofDayActivity.this, groupname,meang);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Prefs.remove("istextchange1");
                Intent i = new Intent(WordofDayActivity.this, WordofDayMeanPageActivity.class);
                i.putExtra("word", groupname.get(position).trim());
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
            TextView txt1 = (TextView)convertView.findViewById(R.id.txt1);
            TextView txt2 = (TextView)convertView.findViewById(R.id.txt2);
            txt1.setText(result.get(position).trim());
            txt2.setText(result2.get(position).trim());
            txt2.setVisibility(View.GONE);

            /*txt1.setText(result.get(position).trim()+" - ");
            txt2.setText(result2.get(position).trim());
*/

            return convertView;
        }
    }




    //end of main class
}
