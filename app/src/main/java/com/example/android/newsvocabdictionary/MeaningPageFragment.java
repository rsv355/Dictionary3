package com.example.android.newsvocabdictionary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.newsvocab.dictionary.R;
import com.pixplicity.easyprefs.library.Prefs;

import net.qiujuer.genius.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import base.DBAdapter;

public class MeaningPageFragment extends Fragment implements
        TextToSpeech.OnInitListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    List<ParseObject> objects;
    ParseObject gameScore;
    TextView txt,prnoun1,mean1,ex1,ex11;
    private TextToSpeech tts;
     DBAdapter db;
    ImageView imgaudio;
    String texttoSpeech;
    public static ArrayList<String> exp;
    public static ArrayList<String> exp2;
    public static ArrayList<String> match;
    public static ArrayList<String> mean;
    LinearLayout empty,mainlinear;
    public static ListView meanListView;
    int i,j,k,a;
    TextView prev,next;
    int Pos;
    boolean firstTime=true;
    boolean istextChanege=false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeaningPageFragment newInstance(String param1, String param2) {
        MeaningPageFragment fragment = new MeaningPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    public MeaningPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("meaning","on create");
        tts = new TextToSpeech(getActivity(), this);


    }

    @Override
    public void onResume() {
        super.onResume();
/*Toast.makeText(getActivity(),"Meng on Resume called",Toast.LENGTH_SHORT).show();
        txt.setText(texttoSpeech.toString());
        // end of resume*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertview = inflater.inflate(R.layout.fragment_meaning_page, container, false);


        db= new DBAdapter(getActivity());
        txt = (TextView)convertview.findViewById(R.id.txt);

        prev = (TextView)convertview.findViewById(R.id.prev);
        next = (TextView)convertview.findViewById(R.id.next);

        meanListView = (ListView)convertview.findViewById(R.id.meanListView);

        prnoun1= (TextView)convertview.findViewById(R.id.prnoun1);
      //  mean1= (TextView)convertview.findViewById(R.id.mean1);

        empty= (LinearLayout)convertview.findViewById(R.id.empty);
        mainlinear= (LinearLayout)convertview.findViewById(R.id.mainlinear);

        imgaudio = (ImageView)convertview.findViewById(R.id.imgaudio);



        LayoutInflater inflaterFooter = getActivity().getLayoutInflater();

        ViewGroup footer = (ViewGroup) inflaterFooter.inflate(
                R.layout.list_footer, meanListView, false);


        LayoutInflater inflaterHeader = getActivity().getLayoutInflater();

        ViewGroup header = (ViewGroup) inflaterHeader.inflate(
                R.layout.list_header, meanListView, false);

        ex1 = (TextView)footer.findViewById(R.id.ex1);
        ex11 = (TextView)footer.findViewById(R.id.ex11);

        meanListView.addFooterView(footer);
        meanListView.addHeaderView(header);

        imgaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // imgaudio.setImageResource(R.drawable.icon_audio_press);
                speakOut();
            }
        });



        istextChanege = Prefs.getBoolean("istextchange",false);

        if(istextChanege) {

            String tt =   Prefs.getString("value",null);

            txt.setText(tt);
            texttoSpeech = tt;
        }else {

            texttoSpeech = getArguments().getString("word");
            txt.setText(texttoSpeech.toString());
        }

        for(int i=0;i<CategoryActivity.catg.size();i++){
            if(CategoryActivity.catg.get(i).equalsIgnoreCase(texttoSpeech.toString())){
                Pos=i;
                //  Toast.makeText(getActivity(), "Pos first time = " + Pos, Toast.LENGTH_SHORT).show();
                firstTime=true;

            }
        }






        processfetchdetails(texttoSpeech.toString());


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(firstTime){
                    firstTime=false;
                    Pos-=1;
                    if(Pos<0){
                        //   Toast.makeText(getActivity(), "Pos 1= " + Pos, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(),"No More Words",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        istextChanege=true;
                        Prefs.putBoolean("istextchange",true);
                        texttoSpeech = CategoryActivity.catg.get(Pos).toString();
                        txt.setText(texttoSpeech);
                        animation();
                        Prefs.putString("value",CategoryActivity.catg.get(Pos).toString());
                        //   Toast.makeText(getActivity(), "Pos 1= " + Pos, Toast.LENGTH_SHORT).show();
                        processfetchdetails(CategoryActivity.catg.get(Pos).toString());

                        Intent i = new Intent(getActivity(), CategoryMeanPageActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.putExtra("word", CategoryActivity.catg.get(Pos).toString());
                        startActivity(i);
                        getActivity().finish();

                    }

                }
                else if(Pos<=0){
                    //   Toast.makeText(getActivity(), "Pos 1= " + Pos, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),"No More Words",Toast.LENGTH_SHORT).show();
                }
                else if(Pos>=CategoryActivity.catg.size()){
                    Pos-=1;
                    istextChanege=true;
                    Prefs.putBoolean("istextchange",true);
                    // Toast.makeText(getActivity(), "" + CategoryActivity.catg.get(Pos), Toast.LENGTH_SHORT).show();
                    texttoSpeech=CategoryActivity.catg.get(Pos).toString();
                    txt.setText(texttoSpeech);
                    animation();
                    Prefs.putString("value",CategoryActivity.catg.get(Pos).toString());
                    //    Toast.makeText(getActivity(), "Pos 2 = " + Pos, Toast.LENGTH_SHORT).show();
                    processfetchdetails(CategoryActivity.catg.get(Pos).toString());

                    Intent i = new Intent(getActivity(), CategoryMeanPageActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("word", CategoryActivity.catg.get(Pos).toString());
                    startActivity(i);
                    getActivity().finish();

                }
                else {
                    Pos--;
                    istextChanege=true;
                    Prefs.putBoolean("istextchange",true);
                    //Toast.makeText(getActivity(), "" + CategoryActivity.catg.get(Pos), Toast.LENGTH_SHORT).show();
                    texttoSpeech=CategoryActivity.catg.get(Pos).toString();
                    txt.setText(texttoSpeech);
                    animation();
                    Prefs.putString("value",CategoryActivity.catg.get(Pos).toString());
                    //   Toast.makeText(getActivity(), "Pos 3= " + Pos, Toast.LENGTH_SHORT).show();
                    processfetchdetails(CategoryActivity.catg.get(Pos).toString());

                    Intent i = new Intent(getActivity(), CategoryMeanPageActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("word", CategoryActivity.catg.get(Pos).toString());
                    startActivity(i);
                    getActivity().finish();


                }


//                ((CategoryMeanPageActivity)getActivity()).updatePagerData();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstTime){
                    firstTime=false;

                    Pos+=1;
                    if(Pos>=CategoryActivity.catg.size()){
                        //    Toast.makeText(getActivity(), "Pos 1= " + Pos, Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(),"No More Words",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        istextChanege=true;
                        Prefs.putBoolean("istextchange",true);
                        texttoSpeech = CategoryActivity.catg.get(Pos).toString();
                        txt.setText(texttoSpeech);
                        animation();
                        Prefs.putString("value",CategoryActivity.catg.get(Pos).toString());
                        //  Toast.makeText(getActivity(), "Pos 11= " + Pos, Toast.LENGTH_SHORT).show();
                        processfetchdetails(CategoryActivity.catg.get(Pos).toString());

                        Intent i = new Intent(getActivity(), CategoryMeanPageActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        i.putExtra("word", CategoryActivity.catg.get(Pos).toString());
                        startActivity(i);
                        getActivity().finish();
                    }

                }
                else if(Pos+1>=CategoryActivity.catg.size()){
                    //  Toast.makeText(getActivity(), "Pos 1= " + Pos, Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(),"No More Words",Toast.LENGTH_SHORT).show();
                }
                else if(Pos<=0){
                    Pos+=1;
                    istextChanege=true;
                    Prefs.putBoolean("istextchange",true);
                    //  Toast.makeText(getActivity(), "" + CategoryActivity.catg.get(Pos), Toast.LENGTH_SHORT).show();
                    texttoSpeech=CategoryActivity.catg.get(Pos).toString();
                    txt.setText(texttoSpeech);
                    animation();
                    Prefs.putString("value",CategoryActivity.catg.get(Pos).toString());
                    //   Toast.makeText(getActivity(), "Pos 22= " + Pos, Toast.LENGTH_SHORT).show();
                    processfetchdetails(CategoryActivity.catg.get(Pos).toString());

                    Intent i = new Intent(getActivity(), CategoryMeanPageActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("word", CategoryActivity.catg.get(Pos).toString());
                    startActivity(i);
                    getActivity().finish();
                }
                else {
                    Pos++;
                    istextChanege=true;
                    Prefs.putBoolean("istextchange",true);
                    // Toast.makeText(getActivity(), "" + CategoryActivity.catg.get(Pos), Toast.LENGTH_SHORT).show();
                    texttoSpeech=CategoryActivity.catg.get(Pos).toString();
                    txt.setText(texttoSpeech);
                    animation();
                    Prefs.putString("value",CategoryActivity.catg.get(Pos).toString());
                    //   Toast.makeText(getActivity(), "Pos 33= " + Pos, Toast.LENGTH_SHORT).show();
                    processfetchdetails(CategoryActivity.catg.get(Pos).toString());


                    Intent i = new Intent(getActivity(), CategoryMeanPageActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.putExtra("word", CategoryActivity.catg.get(Pos).toString());
                    startActivity(i);
                    getActivity().finish();

                }

            }
        });



        return convertview;
    }
    public void animation(){
        Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        anim.reset();
        txt.clearAnimation();
        txt.startAnimation(anim);
    }
    public void processfetchdetails(String word){
        // NEGOTIATION
        Log.e("meaning rec",word);
        db.open();


        Cursor c = db.getRecord(word);
        if (c.moveToFirst()) {
            Display(c);
            empty.setVisibility(View.GONE);
            mainlinear.setVisibility(View.VISIBLE);
        }
        else {
            exp = new ArrayList<String>();
            exp2 = new ArrayList<String>();

            mean = new ArrayList<String>();
            match = new ArrayList<String>();
            empty.setVisibility(View.VISIBLE);
            mainlinear.setVisibility(View.GONE);

        }
        c.close();
        db.close();
    }
/*
    private void InsertRecord(Cursor cc,String originlWord){
        cc.moveToFirst();

        while (!cc.isAfterLast()) {

                    if(cc.getString("WORD").toString().trim().equalsIgnoreCase(originlWord))
                    {
                        db.insertHistoryRecord(cc.getString(2),cc.getString(4));  //Insert in Historydatabase
                    }
          cc.moveToNext();
        }



    }*/

    private void Display(Cursor c) {

        prnoun1.setText(c.getString(3));
      //  mean1.setText(c.getString(4));


        // for history word
        Cursor c1 = db.getHistoryWord(c.getString(2));
        if (c1.moveToFirst()) {

              }
        else{
            db.insertHistoryRecord(c.getString(2),c.getString(4));  //Insert in Historydatabase
        }



        ex1.setText(c.getString(9));
        ex11.setText("\'"+c.getString(19)+"\'");

        exp = new ArrayList<String>();
        exp2 = new ArrayList<String>();
        match = new ArrayList<String>();
        mean = new ArrayList<String>();

        i=0;
        k=0;
        for(int j=9;j<=13;j++){

            if(c.getString(j)!=null){
                exp.add(i++,c.getString(j));
                exp2.add(k++,c.getString(j+10));
            }

        }

        j=0;
        for(int kk=14;kk<=18;kk++){
            if(c.getString(kk)!=null){
                match.add(j++,c.getString(kk));
            }

        }

        a=0;
        for(int m=4;m<=8;m++){
            if(c.getString(m)!=null){
                mean.add(a++,c.getString(m));
            }

        }

        CustomAdapter adapter = new CustomAdapter(getActivity(), mean);
        meanListView.setAdapter(adapter);


       /* ExamplePageFragment.adapter.notifyDataSetChanged();
        ExamplePageFragment.list.invalidateViews();

        MatchPageFragment.adapter.notifyDataSetChanged();
        MatchPageFragment.list.invalidateViews();*/


     /*   exp.add(0,c.getString(9));
        exp.add(1,c.getString(10));
        exp.add(2,c.getString(11));
        exp.add(3,c.getString(12));
        exp.add(4,c.getString(13));*/

      /* if(c.getString(12)==null){
           Toast.makeText(getActivity(),"value null",Toast.LENGTH_SHORT).show();
       }
*/


       /* for(int i=0;i<5;i++){
            if(exp.get(i).equals(null)){
                exp.remove(i);
            }
        }
*/

      /*  match.add(0,c.getString(14));
        match.add(1,c.getString(15));
        match.add(2,c.getString(16));
        match.add(3,c.getString(17));
        match.add(4,c.getString(18));*/

      /*  CustomAdpter adapter = new CustomAdpter(getActivity(), prgmNameList);
        ExamplePageFragment.list.setAdapter(adapter);
*/
       /* Toast.makeText(getActivity(),"id: " + c.getString(0) +
                        "\n" +"CATGID: " + c.getString(1) + "\n"
                        +"WORD: " + c.getString(2) + "\n" +
                        "MEANING: " + c.getString(3),
                Toast.LENGTH_LONG).show();
*/

    }



    private void speakOut() {
        String text = texttoSpeech;
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }



    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            // tts.setPitch(5); // set pitch level

            // tts.setSpeechRate(2); // set speech speed rate

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Language is not supported");
            } else {
              //  imgaudio.setEnabled(true);
              //  speakOut();
            }


        } else {
            Log.e("TTS", "Initilization Failed");
        }

    }

    public class CustomAdapter extends BaseAdapter {
        ArrayList<String> result;

        Context context;
        private  LayoutInflater inflater=null;

        public CustomAdapter(Context ctx, ArrayList<String> prgmNameList) {
            // TODO Auto-generated constructor stub
            result=prgmNameList;

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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_list_meaning,null);
            }

            TextView txt = (TextView)convertView.findViewById(R.id.txt1);

            txt.setText(result.get(position));


            return convertView;
        }
    }


//end of main class
}
