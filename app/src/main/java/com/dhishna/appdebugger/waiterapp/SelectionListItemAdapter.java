package com.dhishna.appdebugger.waiterapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by dovahkin on 6/10/17.
 */

public class SelectionListItemAdapter extends ArrayAdapter{
    private LayoutInflater inflater;
    private ArrayList<Item> items;
    private SelectionActivity activity;
    public int pos;

    public SelectionListItemAdapter(SelectionActivity activity, ArrayList<Item> items){
        super(activity, android.R.layout.activity_list_item, items);
        inflater = activity.getWindow().getLayoutInflater();
        this.items = items;
        this.activity = activity;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        //TODO uncomment the ifconvertview if to make the click bug! for the convert view problem
        //if(convertView==null) {
            //TODO possible bug, because convert view is recreated and stuff, the position value is 0-1 when
            // size of the list item is large(such that screen conains only 2 listitems)
            //TODO possible bug, replace selection list item with order list item layout
            convertView = inflater.inflate(R.layout.selection_list_item_layout, parent, false);
          /*  convertView.findViewById(R.id.show_more_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "Show Details " + items.get(position).name, Toast.LENGTH_SHORT).show();
                    //activity.cancelItem(position);
                }
            });
            */
            convertView.findViewById(R.id.order_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "Ordered " + items.get(position).name, Toast.LENGTH_SHORT).show();


                    OrderItemEditDialog editDialog = new OrderItemEditDialog();
                    editDialog.onAttach(activity);
                    editDialog.setData(position, activity.currentTab);
                    editDialog.show(activity.getSupportFragmentManager(),"OrderEditDialog");

                }
            });
        //}
        //TODO before deployment more details have to be instantiated here
        if(items != null) {
            Log.d("ORDER " , "posiion while : " + position);
            ((ImageView) convertView.findViewById(R.id.item_image)).setImageDrawable(items.get(position).drawable);
            ((TextView) convertView.findViewById(R.id.item_name)).setText(items.get(position).name);
            ((TextView) convertView.findViewById(R.id.item_price)).setText("Unit Price : Rs. "+items.get(position).price);
            ((TextView) convertView.findViewById(R.id.item_time)).setText("Time To Prepare : " + items.get(position).time);
         //   String shortDesc = items.get(position).desc.length() >= 50 ? items.get(position).desc.substring(0, 50) + "..." : items.get(position).desc;
            ((TextView) convertView.findViewById(R.id.item_short_description)).setText("\" " + items.get(position).desc + " \"");
            // TODO possible bug , app crashes replace item rating with item qty
            ((RatingBar) convertView.findViewById(R.id.item_rating)).setRating(Float.parseFloat(items.get(position).rating));
        }
        return convertView;
    }
}

