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

public class ExamplePageSearchFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public static ListView list;

    public static ExamplePageSearchFragment newInstance(String param1, String param2) {
        ExamplePageSearchFragment fragment = new ExamplePageSearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ExamplePageSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        CustomAdapter adapter = new CustomAdapter(getActivity(), MeaningPageFragmentSearch.exp,MeaningPageFragmentSearch.exp2);
        list.setAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View convertview = inflater.inflate(R.layout.fragment_example_page, container, false);

        list = (ListView)convertview.findViewById(R.id.list);


        return convertview;
    }

    public class CustomAdapter extends BaseAdapter {
       ArrayList<String> result;
        ArrayList<String> result2;
        Context context;
        private  LayoutInflater inflater=null;

        public CustomAdapter(Context ctx, ArrayList<String> prgmNameList,ArrayList<String> prgmNameList2) {
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
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_list_example,null);
            }



          /*  View rowView;
            rowView = inflater.inflate(R.layout.item_list_example, null);
*/
            TextView txt = (TextView)convertView.findViewById(R.id.txt1);
            TextView txt2 = (TextView)convertView.findViewById(R.id.txt2);
            txt.setText(result.get(position));
            txt2.setText("\'"+result2.get(position)+"\'");
          /*  holder.tv=(TextView) rowView.findViewById(R.id.txt1);
            holder.tv.setText(result[position]);
*/

            return convertView;
        }
    }

//end of main class
}
