package com.example.android.newsvocabdictionary;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.newsvocab.dictionary.R;
import com.parse.ParseObject;
import com.pixplicity.easyprefs.library.Prefs;

import net.qiujuer.genius.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import base.DBAdapter;
import base.MyDrawerActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    at.markushi.ui.CircleButton btncatg12,btncatg11,btncatg10,btncatg9,btncatg8,btncatg7,btncatg6,btncatg5,btncatg4,btncatg3,btncatg2,btncatg1;

    List<ParseObject> objects;
    ParseObject gameScore;
     DBAdapter db;

    AutoCompleteTextView auto;
    String[] temp2;
    ArrayList<String> temp = new ArrayList<String>();
    private OnFragmentInteractionListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void Display(Cursor c) {

        c.moveToFirst();

        while (!c.isAfterLast()) {
            temp.add(c.getString(c.getColumnIndex("WORD")));
            c.moveToNext();
        }

        temp2 = new String[temp.size()];

        for(int i=0;i<temp.size();i++){
            temp2[i]=temp.get(i);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertview = inflater.inflate(R.layout.fragment_blank, container, false);

        db= new DBAdapter(getActivity());

        auto = (AutoCompleteTextView)convertview.findViewById(R.id.auto);


        db.open();
        Cursor c = db.getSuggest();
        if (c.moveToFirst()) {
            Display(c);
                }
        db.close();




        ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, temp2);
        auto.setAdapter(adapter11);

        Log.e("load","blank frag");


       /* TextView tt = (TextView)convertview.findViewById(R.id.tt);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh");
        SimpleDateFormat sdf2 = new SimpleDateFormat("mm");
        SimpleDateFormat sdf3 = new SimpleDateFormat("aa");

        String ans1=sdf1.format(c.getTime());
        String ans2=sdf2.format(c.getTime());
        String ans3=sdf3.format(c.getTime());

        tt.setText(""+ans1+"-"+ans2+"-"+ans3);*/

        btncatg1=(at.markushi.ui.CircleButton)convertview.findViewById(R.id.btncatg1);
        btncatg2=(at.markushi.ui.CircleButton)convertview.findViewById(R.id.btncatg2);
        btncatg3=(at.markushi.ui.CircleButton)convertview.findViewById(R.id.btncatg3);
        btncatg4=(at.markushi.ui.CircleButton)convertview.findViewById(R.id.btncatg4);
        btncatg5=(at.markushi.ui.CircleButton)convertview.findViewById(R.id.btncatg5);
        btncatg6=(at.markushi.ui.CircleButton)convertview.findViewById(R.id.btncatg6);
        btncatg7=(at.markushi.ui.CircleButton)convertview.findViewById(R.id.btncatg7);
        btncatg8=(at.markushi.ui.CircleButton)convertview.findViewById(R.id.btncatg8);
        btncatg9=(at.markushi.ui.CircleButton)convertview.findViewById(R.id.btncatg9);
        btncatg10=(at.markushi.ui.CircleButton)convertview.findViewById(R.id.btncatg10);
        btncatg11=(at.markushi.ui.CircleButton)convertview.findViewById(R.id.btncatg11);
        btncatg12=(at.markushi.ui.CircleButton)convertview.findViewById(R.id.btncatg12);

        btncatg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("catgname","Crime Words");
                i.putExtra("catgid","1");
                startActivity(i);
            }
        });

     /*  btncatg1.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {


               return false;
           }
       });*/


        btncatg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("catgname","Politics Words");
                i.putExtra("catgid","2");
                startActivity(i);
            }
        });
      /* btncatg2.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {


               return false;
           }
       });*/

        btncatg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("catgname","Court Words");
                i.putExtra("catgid","3");
                startActivity(i);
            }
        });
    /*  btncatg3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });*/

        btncatg4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("catgname","Mishap Words");
                i.putExtra("catgid","4");
                startActivity(i);
            }
        });

     /*   btncatg4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });*/

        btncatg5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("catgname","Fire Words");
                i.putExtra("catgid","5");
                startActivity(i);
            }
        });
     /*   btncatg5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });*/

        btncatg6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("catgname","Death Words");
                i.putExtra("catgid","6");
                startActivity(i);
            }
        });

      /*  btncatg6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });*/

        btncatg7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("catgname","Weather Words");
                i.putExtra("catgid","7");
                startActivity(i);
            }
        });

    /*    btncatg7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });*/
        btncatg8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("catgname","Flood Words");
                i.putExtra("catgid","8");
                startActivity(i);
            }
        });

      /*  btncatg8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });*/

        btncatg9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("catgname","Business Words");
                i.putExtra("catgid","9");
                startActivity(i);
            }
        });
       /* btncatg9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });*/

        btncatg10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("catgname","Sports Words");
                i.putExtra("catgid","10");
                startActivity(i);
            }
        });
      /*  btncatg10.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });*/
        btncatg11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("catgname","Other Words");
                i.putExtra("catgid","11");
                startActivity(i);
            }
        });
       /* btncatg11.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                return false;
            }
        });
*/
        btncatg12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CategoryActivity.class);
                i.putExtra("catgname","All Words");
                i.putExtra("catgid","12");
                startActivity(i);
            }
        });


        /*btncatg12.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {



                return false;
            }
        });*/




        return convertview;
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
