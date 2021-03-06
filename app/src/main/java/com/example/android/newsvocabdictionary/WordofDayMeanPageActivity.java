package com.example.android.newsvocabdictionary;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.newsvocab.dictionary.R;

import base.PagerSlidingTabStrip;


public class WordofDayMeanPageActivity extends ActionBarActivity {
    PagerSlidingTabStrip tabs;
    public static  ViewPager pager;
    public MyPagerAdapter adapter;
    String texttoSend;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorymeaningpage);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);

        toolbar.setTitle("News Vocab");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        tabs=(PagerSlidingTabStrip)findViewById(R.id.tabs_mobiletopup);
        pager=(ViewPager)findViewById(R.id.pager_mobiletopup);


        adapter = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        tabs.setViewPager(pager);

        tabs.setDividerColor(Color.parseColor("#ffffff"));
        texttoSend=getIntent().getStringExtra("word");



        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }


    public void updatePagerData(){

        adapter.notifyDataSetChanged();
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {


        private final String[] TITLES = {"Meaning","Example","Similar Words"};



        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }


        @Override
        public int getCount() {
            return TITLES.length;
        }



        @Override
        public Fragment getItem(int position) {

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();

            Bundle bundle=new Bundle();
            bundle.putString("word", texttoSend);

            if (position == 0) {
                //  MeaningPageFragment bm =  new MeaningPageFragment();
                WordMeaningPageFragment bm = new WordMeaningPageFragment();

                bm.setArguments(bundle);
             /*   ft.replace(R.id.main_container, bm);
                ft.addToBackStack(null);
                ft.commit();*/

                return bm;
            }
            else if (position == 1) {
/*
                ExamplePageFragment bm2 = new ExamplePageFragment();
                ft.add(bm2,"EXP");
                return ft;*/
                return WordExamplePageFragment.newInstance("", "");
            }
            else if (position == 2) {
                return WordMatchPageFragment.newInstance("", "");
            }
            else {
                return WordMeaningPageFragment.newInstance("", "");
            }
        }
    }

    //end of main class
}
