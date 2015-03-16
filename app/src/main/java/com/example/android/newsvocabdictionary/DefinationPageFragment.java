package com.example.android.newsvocabdictionary;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.newsvocab.dictionary.R;

import java.util.ArrayList;

public class DefinationPageFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    TextView txt1,txt2,txt3,txt4,txt5;

    public static DefinationPageFragment newInstance(String param1, String param2) {
        DefinationPageFragment fragment = new DefinationPageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DefinationPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();

      //  Toast.makeText(getActivity(), "Match called", Toast.LENGTH_SHORT).show();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertview = inflater.inflate(R.layout.fragment_defination_page, container, false);


        return convertview;
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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_list_match,null);
            }



          /*  View rowView;
            rowView = inflater.inflate(R.layout.item_list_example, null);
*/
            TextView txt = (TextView)convertView.findViewById(R.id.txt1);
            txt.setText(result.get(position));
          /*  holder.tv=(TextView) rowView.findViewById(R.id.txt1);
            holder.tv.setText(result[position]);
*/

            return convertView;
        }
    }

//end of main class
}
