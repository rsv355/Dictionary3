package base;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.BaseColumns;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.newsvocabdictionary.BlankFragment;
import com.example.android.newsvocabdictionary.ContactUsActivity;
import com.example.android.newsvocabdictionary.HistoryActivity;
import com.example.android.newsvocabdictionary.MeanPageActivity;

import com.example.android.newsvocabdictionary.SampleRecentSuggestionsProvider;
import com.example.android.newsvocabdictionary.SettingsActivity;
import com.example.android.newsvocabdictionary.WordofDayActivity;
import com.newsvocab.dictionary.R;


import net.qiujuer.genius.util.Log;

import java.util.ArrayList;


public class MyDrawerActivity extends ActionBarActivity {

    //  private ButtonRectangle btnLogout;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView leftDrawerList;
    private ArrayAdapter<String> navigationDrawerAdapter;
    private String[] leftSliderData = {"Home","Words of the Day","Words Per Day - ","History", "Contact Us", "Share App", "Settings"};
    private String[] leftSliderData2 = {"","","3","", "", "", ""};
    RelativeLayout relativLayout;
    private boolean draweropen=false;
    private boolean searchTextopen=false;
    DBAdapter db;
    ArrayList<String> temp = new ArrayList<String>();
    TextView mTitleTextView;
    AutoCompleteTextView autoText;
    ImageButton imgNav;
    ImageButton imgSrch,imgbacksearch;
    ImageView imageView1;
    boolean isdrawerOpen=false;



    private int[] imagelist = {
            R.drawable.icon_home,
            R.drawable.icon_cal,
            R.drawable.icon_aboutus,
            R.drawable.ic_action_restore,
            R.drawable.icon_contactus,
            R.drawable.icon_share,
            R.drawable.icon_setting,};

   // public ProgressBar pb_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_drawer);
        nitView();
        db= new DBAdapter(MyDrawerActivity.this);

     /*   ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
      */
      //  LayoutInflater mInflater = LayoutInflater.from(this);

       // View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
          mTitleTextView = (TextView) findViewById(R.id.title_text);
          autoText = (AutoCompleteTextView)findViewById(R.id.auto);
        mTitleTextView.setText("News Vocabulary");





        db.open();
        Cursor c = db.getSuggest();
        if (c.moveToFirst()) {
            Display(c);
        }
        db.close();
        ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(MyDrawerActivity.this,android.R.layout.simple_list_item_1, temp);
        autoText.setAdapter(adapter11);


        autoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(autoText.getWindowToken(), 0);

                Intent i = new Intent(MyDrawerActivity.this, MeanPageActivity.class);
                i.putExtra("word", temp.get(position).trim());
                startActivity(i);
            }
        });

        imageView1= (ImageView)
                findViewById(R.id.imageView1);

        imgbacksearch= (ImageButton)
                findViewById(R.id.imgbacksearch);

        imgSrch = (ImageButton)
                findViewById(R.id.imageButton);


        imgbacksearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgbacksearch.setVisibility(View.GONE);
                imgNav.setVisibility(View.VISIBLE);

                searchTextopen=false;
                imgSrch.setVisibility(View.VISIBLE);
                mTitleTextView.setVisibility(View.VISIBLE);
                autoText.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(autoText.getWindowToken(), 0);


            }
        });

        imgSrch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                imgbacksearch.setVisibility(View.VISIBLE);
                imgNav.setVisibility(View.GONE);

                searchTextopen=true;
                imgSrch.setVisibility(view.GONE);
                mTitleTextView.setVisibility(view.GONE);
                autoText.setVisibility(view.VISIBLE);


                autoText.setFocusableInTouchMode(true);
                autoText.requestFocus();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);


            }
        });

        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isdrawerOpen=true;
              //  drawerLayout.openDrawer(relativLayout);

                if(drawerLayout.isDrawerOpen(relativLayout)){
                    drawerLayout.closeDrawers();
                    imgNav.setBackground(null);
                    imgNav.setBackgroundResource(R.drawable.nav);
                }
                else{
                    drawerLayout.openDrawer(relativLayout);
                    imgNav.setBackground(null);
                    imgNav.setBackgroundResource(R.drawable.back);
                }
            }
        });


        autoText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0){
                    Drawable img = getResources().getDrawable( R.drawable.clear);
                    autoText.setCompoundDrawablesWithIntrinsicBounds( null, null, img, null);

                    autoText.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            final int DRAWABLE_LEFT = 0;
                            final int DRAWABLE_TOP = 1;
                            final int DRAWABLE_RIGHT = 2;
                            final int DRAWABLE_BOTTOM = 3;

                            if(event.getAction() == MotionEvent.ACTION_UP) {
                                if(event.getRawX() >= (autoText.getRight() - autoText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                                    autoText.setText("");

                                    return true;
                                }
                            }
                            else{

                            }
                            return false;
                        }
                    });

                }
                else{
                    autoText.setCompoundDrawablesWithIntrinsicBounds( null, null, null, null);
                    autoText.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        autoText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {


                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(autoText.getWindowToken(), 0);


                    if(autoText.length()==0){
                            Toast.makeText(MyDrawerActivity.this,"Please enter some text to search",Toast.LENGTH_SHORT).show();
                    }else {

                        Intent i = new Intent(MyDrawerActivity.this, MeanPageActivity.class);
                        i.putExtra("word", autoText.getText().toString().trim());
                        startActivity(i);
                    }
                    return true;


            }
        });








       /* mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
*/

        Log.e("inside", "Mydrawr activity");

       /* if (toolbar != null) {
            toolbar.setTitle("News Vocab");
            setSupportActionBar(toolbar);
        }
*/
        initDrawer();

       /* Intent i = new Intent(MyDrawerActivity.this, MainActivity.class);
        startActivity(i);*/
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_container, new BlankFragment());
        ft.commit();


    }

    private void Display(Cursor c) {

        c.moveToFirst();

        while (!c.isAfterLast()) {
            temp.add(c.getString(c.getColumnIndex("WORD")));
            c.moveToNext();
       }

    }


    void processChangeImage(boolean tempisdrawerOpen){
        if(drawerLayout.isDrawerOpen(relativLayout)){
            imgNav.setBackground(null);
            imgNav.setBackgroundResource(R.drawable.nav);
        }
        else{
            imgNav.setBackground(null);
            imgNav.setBackgroundResource(R.drawable.back);
        }
    }



    private void nitView() {
        relativLayout = (RelativeLayout)findViewById(R.id.relativLayout);
        //  btnLogout = (ButtonRectangle)findViewById(R.id.btnLogout);
        leftDrawerList = (ListView) findViewById(R.id.left_drawer);
       /* toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setBackgroundColor(Color.parseColor("#009688"));// whats app color 009688,,00695F  --brown color 725232
*/

       // toolbar.setBackgroundColor(Color.parseColor("#6B8F00"));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        imgNav = (ImageButton)
                findViewById(R.id.imgNav);
        imgNav.setBackground(null);
        imgNav.setBackgroundResource(R.drawable.nav);

        imgNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                if(draweropen){
                    imgNav.setBackground(null);
                    imgNav.setBackgroundResource(R.drawable.nav);

                    drawerLayout.closeDrawers();
                    draweropen=false;
                }
                else {
                    imgNav.setBackground(null);
                    imgNav.setBackgroundResource(R.drawable.back);

                    draweropen=true;
                    drawerLayout.openDrawer(relativLayout);
                }
            }
        });






       /* Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.TOP | Gravity.RIGHT);

       layoutParams.width = (int) AppUtils.convertDpToPixel(32,MyDrawerActivity.this);
       layoutParams.height = (int)AppUtils.convertDpToPixel(32,MyDrawerActivity.this);
        layoutParams.rightMargin = 16;
*/

    }

    @Override
    public void onBackPressed() {

        if(draweropen){
            imgNav.setBackground(null);
            imgNav.setBackgroundResource(R.drawable.nav);


            drawerLayout.closeDrawers();
        }
        else if(searchTextopen){


            imgbacksearch.setVisibility(View.GONE);
            imgNav.setVisibility(View.VISIBLE);
            imgNav.setBackground(null);
            imgNav.setBackgroundResource(R.drawable.nav);


            searchTextopen=false;
            imgSrch.setVisibility(View.VISIBLE);
            mTitleTextView.setVisibility(View.VISIBLE);
            autoText.setVisibility(View.GONE);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        drawerLayout.setFocusableInTouchMode(false);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int pos = preferences.getInt("wordlimit", 2);
        pos+=1;

        leftSliderData2[2]=""+pos;


        navigationDrawerAdapter = new ArrayAdapter<String>(MyDrawerActivity.this, android.R.layout.simple_list_item_activated_1, android.R.id.text1, leftSliderData);
        leftDrawerList.setAdapter(new lViewadapter());




        leftDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                drawerLayout.closeDrawers();
                FragmentManager fm = getSupportFragmentManager();
                switch (position) {

                    case 0:
                        imgNav.setBackground(null);
                        imgNav.setBackgroundResource(R.drawable.nav);

                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction ft = manager.beginTransaction();
                        ft.replace(R.id.main_container, new BlankFragment());
                        // ft.addToBackStack("");
                        ft.commit();
                        break;
                    case 1:
                        imgNav.setBackground(null);
                        imgNav.setBackgroundResource(R.drawable.nav);
                        Intent i3 = new Intent(MyDrawerActivity.this, WordofDayActivity.class);
                        startActivity(i3);
                        break;
                    case 2:
                        imgNav.setBackground(null);
                        imgNav.setBackgroundResource(R.drawable.nav);
                        Intent i33 = new Intent(MyDrawerActivity.this, SettingsActivity.class);
                        startActivity(i33);

                        break;
                    case 3:
                        imgNav.setBackground(null);
                        imgNav.setBackgroundResource(R.drawable.nav);
                        Intent i43 = new Intent(MyDrawerActivity.this, HistoryActivity.class);
                        startActivity(i43);
                        break;

                    case 4:
                        imgNav.setBackground(null);
                        imgNav.setBackgroundResource(R.drawable.nav);
                        Intent i23 = new Intent(MyDrawerActivity.this, ContactUsActivity.class);
                        startActivity(i23);
                        break;
                    case 5:
                        imgNav.setBackground(null);
                        imgNav.setBackgroundResource(R.drawable.nav);
                        String text="Hi,\nPlease Check out this amazing app, \n https://play.google.com/store/apps/details?id=com.newsvocab.dictionary";

                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
                        // sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));
                        //share page
                        break;
                    case 6:
                        imgNav.setBackground(null);
                        imgNav.setBackgroundResource(R.drawable.nav);
                        Intent i4 = new Intent(MyDrawerActivity.this, SettingsActivity.class);
                        startActivity(i4);
                        break;
                }

            }
        });
    }

    public class lViewadapter extends BaseAdapter {
        @Override
        public int getCount() {
            return leftSliderData.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.mydrawer_listview_layout, parent, false);
            TextView title = (TextView) row.findViewById(R.id.txtTitle);
            TextView title2 = (TextView) row.findViewById(R.id.txtTitle2);
            ImageView img_icon = (ImageView) row.findViewById(R.id.imgIcon);
            img_icon.setBackgroundResource(imagelist[position]);
            img_icon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            title.setText(leftSliderData[position]);

            title2.setText(leftSliderData2[position]);
            title2.setTextSize(20);
            title.setTextSize(20);
            return row;
        }
    }

    public void setToolColor(int color) {
        toolbar.setBackgroundColor(color);
    }

    public void setToolTitle(String title) {
        toolbar.setTitle(title);
    }

    public void setToolSubTitle(String subTitle) {

        toolbar.setSubtitle(subTitle);
    }

    public Toolbar getToolBar() {
        return this.toolbar;
    }


    private void initDrawer() {

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                draweropen=false;
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                draweropen=true;
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_my_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}