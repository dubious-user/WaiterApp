package com.dhishna.appdebugger.waiterapp;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by dovahkin on 5/10/17.
 */

public class OrderListItemAdapter extends ArrayAdapter{
    private LayoutInflater inflater;
    private ArrayList<Item> items;
    private ConfirmOrderActivity activity;

    public OrderListItemAdapter(ConfirmOrderActivity activity, ArrayList<Item> items){
        super(activity, android.R.layout.activity_list_item, items);
        inflater = activity.getWindow().getLayoutInflater();
        this.items = items;
        this.activity = activity;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.order_list_item_layout, parent, false);
            convertView.findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "Canceled " + items.get(position).name, Toast.LENGTH_SHORT).show();
                    activity.cancelItem(position);
                }
            });
            convertView.findViewById(R.id.edit_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(activity, "Edited " + items.get(position).name, Toast.LENGTH_SHORT).show();
                    OrderItemEditDialog editDialog = new OrderItemEditDialog();
                    editDialog.onAttach(activity);
                    editDialog.setIndex(position);
                    editDialog.show(activity.getSupportFragmentManager(),"OrderEditDialog");
                }
            });
        }
        //TODO before deployment more details have to be instantiated here
        if(items != null) {
            ((TextView) convertView.findViewById(R.id.item_name)).setText(items.get(position).name);
            ((TextView) convertView.findViewById(R.id.item_price)).setText("Unit Price : "+items.get(position).price + " Rs.");
            ((TextView) convertView.findViewById(R.id.item_time)).setText("Time To Prepare : " + items.get(position).time);
            ((TextView) convertView.findViewById(R.id.item_total)).setText("Total Price : "+ (items.get(position).price * items.get(position).qty) + " Rs.");
            // TODO possible bug , app crashes when this following is executed. when ""+ is not there
            ((TextView) convertView.findViewById(R.id.item_qty)).setText("Quantity : " + items.get(position).qty);
        }
        return convertView;
    }
}
