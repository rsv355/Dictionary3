package com.example.android.newsvocabdictionary;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.newsvocab.dictionary.R;

import base.PagerSlidingTabStrip;


public class CategoryMeanPageActivity2 extends ActionBarActivity {
    PagerSlidingTabStrip tabs;
    public static  ViewPager pager;
    public MyPagerAdapter adapter;
    String texttoSend;
    private Toolbar toolbar;
    TextView txt1,txt2,txt3;
    FrameLayout frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorymeaningpage2);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        txt1= (TextView)findViewById(R.id.txt1);
        txt2= (TextView)findViewById(R.id.txt2);
        txt3= (TextView)findViewById(R.id.txt3);
        frame= (FrameLayout)findViewById(R.id.frame);

        toolbar.setTitle("News Vocab");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

        texttoSend=getIntent().getStringExtra("word");


       final FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        MeaningPageFragment bm = new MeaningPageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("word",texttoSend);
        bm.setArguments(bundle);
        ft.replace(R.id.frame, bm);
        ft.addToBackStack(null);
        ft.commit();

      txt1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
               FragmentTransaction ft = manager.beginTransaction();
              MeaningPageFragment bm = new MeaningPageFragment();
              Bundle bundle = new Bundle();
              bundle.putString("word",texttoSend);
              bm.setArguments(bundle);
                ft.replace(R.id.frame, bm);
                ft.addToBackStack(null);
                ft.commit();
          }
      });
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = manager.beginTransaction();
                ExamplePageFragment em = new ExamplePageFragment();
              //  Bundle bundle = new Bundle();
              //  bundle.putString("word","");
              //  bm.setArguments(bundle);
                ft.replace(R.id.frame, em);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = manager.beginTransaction();
                MatchPageFragment bm = new MatchPageFragment();
             //   Bundle bundle = new Bundle();
             //   bundle.putString("word","");
              //  bm.setArguments(bundle);
                ft.replace(R.id.frame, bm);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


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

        ExamplePageFragment obj = new ExamplePageFragment();

        obj.updateMtehod();
       try {



          // adapter = new ExamplePageFragment.CustomAdapter(ExamplePageFragment, MeaningPageFragment.exp, MeaningPageFragment.exp2);
     /*      ExamplePageFragment.adapter.notifyDataSetChanged();
           ExamplePageFragment.list.invalidateViews();
   */     //   ExamplePageFragment.list.setAdapter(ExamplePageFragment.adapter);
       }catch(Exception e){

       }
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
                MeaningPageFragment bm = new MeaningPageFragment();

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
                return ExamplePageFragment.newInstance("","");
            }
            else if (position == 2) {
                return MatchPageFragment.newInstance("", "");
            }
            else {
                return MeaningPageFragment.newInstance("", "");
            }
        }
    }

    //end of main class
}
