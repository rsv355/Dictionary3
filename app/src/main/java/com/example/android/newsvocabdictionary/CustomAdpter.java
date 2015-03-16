package com.example.android.newsvocabdictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.newsvocab.dictionary.R;
/**
 * Created by Krishna on 26/Feb/2015.
 */
public class CustomAdpter extends BaseAdapter {
    String [] result;
    Context context;
    private LayoutInflater inflater=null;


    public CustomAdpter(Context ctx, String[] prgmNameList) {
        // TODO Auto-generated constructor stub
        result=prgmNameList;
        this.context = ctx;
          inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return 0;
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
     /*   if(convertView == null){
            convertView = getApplicationContext().getLayoutInflater().inflate(R.layout.item_list_example,null);
        }
*/


           View rowView;
            rowView = inflater.inflate(R.layout.item_list_example, null);

        TextView txt = (TextView)rowView.findViewById(R.id.txt1);
        txt.setText(result[position]);
          /*  holder.tv=(TextView) rowView.findViewById(R.id.txt1);
            holder.tv.setText(result[position]);
*/

        return convertView;
    }
}
