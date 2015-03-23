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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.newsvocab.dictionary.R;
import com.pixplicity.easyprefs.library.Prefs;

import net.qiujuer.genius.util.Log;

import java.util.ArrayList;

import base.DBAdapter;

public class CategoryActivity extends ActionBarActivity {

    private Toolbar toolbar;
    TextView txt1,txt2,txt3,txt4,txt5;
    public static ListView list;
    String CATGID;
    DBAdapter db;
    public static ArrayList<String> catg;
    public static ArrayList<String> meang;
    int i,j;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);

        toolbar.setTitle(getIntent().getStringExtra("catgname"));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        db= new DBAdapter(CategoryActivity.this);
        list = (ListView)findViewById(R.id.list);

        CATGID = getIntent().getStringExtra("catgid");

        processfecthCategory(CATGID);


    }


    public void processfecthCategory(String CATG_ID){

        catg = new ArrayList<String>();
        meang = new ArrayList<String>();

        if(CATG_ID.equals("12")){

            db.open();
            Cursor c = db.getAllCategory();
            if (c.moveToFirst()) {
                Display(c);
                //  catg.add(i++,c.getString(2));
                  /*  empty.setVisibility(View.GONE);
                    mainlinear.setVisibility(View.VISIBLE);
           */     }
            else {
                catg = new ArrayList<String>();
                meang = new ArrayList<String>();
                Toast.makeText(CategoryActivity.this, "No Words From this Category", Toast.LENGTH_SHORT).show();

            }
            c.close();
            db.close();
        }
        else{

            db.open();
            Cursor c = db.getCategory(CATG_ID);
            if (c.moveToFirst()) {
                Display(c);
                //   catg.add(i++,c.getString(2));
                  /*  empty.setVisibility(View.GONE);
                    mainlinear.setVisibility(View.VISIBLE);
           */     }
            else {
                catg = new ArrayList<String>();
                meang = new ArrayList<String>();
                Toast.makeText(CategoryActivity.this,"No Words From this Category",Toast.LENGTH_SHORT).show();

            }
            c.close();
            db.close();
        }




        CustomAdapter adapter = new CustomAdapter(CategoryActivity.this, catg,meang);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Prefs.remove("istextchange");
                Intent i = new Intent(CategoryActivity.this, CategoryMeanPageActivity.class);
                i.putExtra("word", catg.get(position).trim());
                startActivity(i);
            }
        });

    }

    private void Display(Cursor c) {

        c.moveToFirst();

        while (!c.isAfterLast()) {
            catg.add(c.getString(c.getColumnIndex("WORD")));
            meang.add(c.getString(c.getColumnIndex("MEANING_HIN")));
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
                convertView = getLayoutInflater().inflate(R.layout.item_list_category,null);
            }

          /*  View rowView;
            rowView = inflater.inflate(R.layout.item_list_example, null);
*/
            TextView txt1 = (TextView)convertView.findViewById(R.id.txt1);
            TextView txt2 = (TextView)convertView.findViewById(R.id.txt2);
            txt1.setText(result.get(position).trim()+" - ");
            txt2.setText(result2.get(position).trim());

          /*  holder.tv=(TextView) rowView.findViewById(R.id.txt1);
            holder.tv.setText(result[position]);
*/

            return convertView;
        }
    }







    //end of main class
}
