package com.example.android.newsvocabdictionary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

import java.util.ArrayList;

import base.DBAdapter;

public class HistoryActivity extends ActionBarActivity {

    private Toolbar toolbar;
    public static ListView list;
    String CATGID;
    DBAdapter db;
    public static ArrayList<String> catg;
    public static ArrayList<String> meang;
    LinearLayout empty;
    int i,j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        empty = (LinearLayout)findViewById(R.id.empty);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);

        toolbar.setTitle("History");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        db= new DBAdapter(HistoryActivity.this);
        list = (ListView)findViewById(R.id.list);



    }

    @Override
    protected void onResume() {
        super.onResume();

        processfetchHistoryWords();
    }

void processfetchHistoryWords(){
        catg = new ArrayList<String>();
        meang = new ArrayList<String>();


    db.open();
    Cursor c = db.getAllHistoryWord();
    if (c.moveToFirst()) {
        Display(c);

    }else {
    }

    c.close();
    db.close();
if(catg.size()==0){
    empty.setVisibility(View.VISIBLE);
}
    else{
    empty.setVisibility(View.GONE);
    }

        CustomAdapter adapter = new CustomAdapter(HistoryActivity.this, catg,meang);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prefs.remove("istextchange3");
                Intent i = new Intent(HistoryActivity.this, HistoryMeanPageActivity.class);
                i.putExtra("word", catg.get(position).trim());
                startActivity(i);
            }
        });


    }

    private void Display(Cursor c) {

        c.moveToFirst();

        while (!c.isAfterLast()) {
            catg.add(c.getString(c.getColumnIndex("WORD")));
            meang.add(c.getString(c.getColumnIndex("MEANING")));
            c.moveToNext();
        }

    }


    public class CustomAdapter extends BaseAdapter {
        ArrayList<String> result;
        ArrayList<String> result2;
        Context context;
        private LayoutInflater inflater=null;

        public CustomAdapter(Context ctx, ArrayList<String> prgmNameList, ArrayList<String> prgmNameList2) {
            // TODO Auto-generated constructor stub
            result=prgmNameList;
            result2=prgmNameList2;
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

            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.item_list_history,null);
            }

          /*  View rowView;
            rowView = inflater.inflate(R.layout.item_list_example, null);
*/
            TextView txt = (TextView)convertView.findViewById(R.id.txt1);
            txt.setText(result.get(position).trim()+" - "+result2.get(position).trim());

          /*  holder.tv=(TextView) rowView.findViewById(R.id.txt1);
            holder.tv.setText(result[position]);
*/

            return convertView;
        }
    }
//end of main class
}
