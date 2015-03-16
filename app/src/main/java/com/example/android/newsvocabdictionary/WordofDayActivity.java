package com.example.android.newsvocabdictionary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.newsvocab.dictionary.R;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

public class WordofDayActivity extends ActionBarActivity {

    private Toolbar toolbar;
    ListView list;
    int wordlimit;
    LinearLayout linear;
    public static CustomAdapter adapter;
    public static ArrayList<String> words;
    public static ArrayList<String> meang;
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


        list = (ListView)findViewById(R.id.list);
        linear = (LinearLayout)findViewById(R.id.linear);




        animation();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        wordlimit = preferences.getInt("wordlimit", 2);


        words = new ArrayList<String>();
        meang = new ArrayList<String>();

        for(int i=0;i<wordlimit+1;i++){
            words.add(word_in_string[i]);
            meang.add(word_in_string[i]);
        }


        adapter = new CustomAdapter(WordofDayActivity.this, words,meang);
        adapter.notifyDataSetChanged();
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Prefs.remove("istextchange1");
                Intent i = new Intent(WordofDayActivity.this, WordofDayMeanPageActivity.class);
                i.putExtra("word", words.get(position));
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
            txt.setText(result.get(position) + " - " + result2.get(position));

          /*  holder.tv=(TextView) rowView.findViewById(R.id.txt1);
            holder.tv.setText(result[position]);
*/

            return convertView;
        }
    }


    //end of main class
}
