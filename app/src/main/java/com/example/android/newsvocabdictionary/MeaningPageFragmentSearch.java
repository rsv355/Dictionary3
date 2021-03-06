package com.example.android.newsvocabdictionary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;
import com.newsvocab.dictionary.R;
import net.qiujuer.genius.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import base.DBAdapter;

public class MeaningPageFragmentSearch extends Fragment implements
        TextToSpeech.OnInitListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    List<ParseObject> objects;
    ParseObject gameScore;
    TextView txt,prnoun1,mean1,ex1,ex11,dash,sm1,sm2,expTitle,smTitle;
    View line1,line2;
    private TextToSpeech tts;
    DBAdapter db;
    ImageView imgaudio;
    String texttoSpeech;
    public static ArrayList<String> exp;
    public static ArrayList<String> exp2;
    public static ArrayList<String> match;
    public static ArrayList<String> mean;
    LinearLayout empty,mainlinear,linearExp,linearsmp;
    public static ListView meanListView;
    int i,j,k,a;


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

    public MeaningPageFragmentSearch() {
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
        texttoSpeech=getArguments().getString("word");

        txt.setText(texttoSpeech);

        LayoutInflater inflaterFooter = getActivity().getLayoutInflater();

        ViewGroup footer = (ViewGroup) inflaterFooter.inflate(
                R.layout.list_footer, meanListView, false);


        LayoutInflater inflaterHeader = getActivity().getLayoutInflater();

        ViewGroup header = (ViewGroup) inflaterHeader.inflate(
                R.layout.list_header, meanListView, false);

        ex1 = (TextView)footer.findViewById(R.id.ex1);
        ex11 = (TextView)footer.findViewById(R.id.ex11);

        expTitle= (TextView)footer.findViewById(R.id.expTitle);
        line1= (View)footer.findViewById(R.id.line1);
        line2= (View)footer.findViewById(R.id.line2);
        smTitle= (TextView)footer.findViewById(R.id.smTitle);
        linearExp = (LinearLayout)footer.findViewById(R.id.linearExp);
        linearsmp = (LinearLayout)footer.findViewById(R.id.linearsmp);

        sm1 = (TextView)footer.findViewById(R.id.sm1);
        sm2 = (TextView)footer.findViewById(R.id.sm2);


        meanListView.addFooterView(footer);
        meanListView.addHeaderView(header);

        imgaudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //  imgaudio.setImageResource(R.drawable.icon_audio_press);
                speakOut();
            }
        });


        processfetchdetails(texttoSpeech.toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertview = inflater.inflate(R.layout.fragment_meaning_pagesearch, container, false);

        db= new DBAdapter(getActivity());
        txt = (TextView)convertview.findViewById(R.id.txt);


        meanListView = (ListView)convertview.findViewById(R.id.meanListView);

      //  dash = (TextView)convertview.findViewById(R.id.dash);
        prnoun1= (TextView)convertview.findViewById(R.id.prnoun1);
        //  mean1= (TextView)convertview.findViewById(R.id.mean1);

        empty= (LinearLayout)convertview.findViewById(R.id.empty);
        mainlinear= (LinearLayout)convertview.findViewById(R.id.mainlinear);

        imgaudio = (ImageView)convertview.findViewById(R.id.imgaudio);


        return convertview;
    }

    private void processfetchdetails(String word){
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

    private void Display(final Cursor c) {


        prnoun1.setText(c.getString(3));
        //  mean1.setText(c.getString(4));
       // for history word
        Cursor c1 = db.getHistoryWord(c.getString(2));
        if (c1.moveToFirst()) {

        }
        else{
            db.insertHistoryRecord(c.getString(2),c.getString(4),c.getString(24));  //Insert in Historydatabase
        }



        if(c.getString(14)==null&&c.getString(15)==null){
            linearsmp.setVisibility(View.GONE);

        }
        else if(c.getString(14)==null){
            sm1.setVisibility(View.GONE);
            sm2.setText(c.getString(15).trim());
            sm2.setVisibility(View.VISIBLE);
        }
        else if(c.getString(15)==null){
            sm2.setVisibility(View.GONE);
            sm1.setText(c.getString(14).trim());
            sm1.setVisibility(View.VISIBLE);
        }
        else{
            linearsmp.setVisibility(View.VISIBLE);
            sm1.setText(c.getString(14).trim());
            sm2.setText(c.getString(15).trim());
        }



        sm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryMeanPageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.putExtra("word",sm1.getText().toString().trim());
                startActivity(i);
                getActivity().finish();
            }
        });

        sm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryMeanPageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                i.putExtra("word",sm2.getText().toString().trim());
                startActivity(i);
                getActivity().finish();
            }
        });


        if(c.getString(9)==null){
            linearExp.setVisibility(View.GONE);
             }
        else{
            linearExp.setVisibility(View.VISIBLE);
            ex1.setText(c.getString(9));
            ex11.setText("\'"+c.getString(19)+"\'");

        }

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