package com.dhishna.appdebugger.waiterapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * Created by dovahkin on 5/10/17.
 */

public class OrderItemEditDialog extends DialogFragment{

    private int id;
    public void setIndex(int i){
        id = i;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstaceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.order_item_edit_dialog_box,null);
        //TODO possible bug , if item_name is replaced with title, app will crash when edit button is clicked
        ((TextView)view.findViewById(R.id.item_name)).setText(MainActivity.orderedItems.get(id).name);
        ((TextView)view.findViewById(R.id.item_name)).setTextColor(Color.WHITE);
        ((TextView)view.findViewById(R.id.item_name)).setTextSize(30);
        ((NumberPicker)view.findViewById(R.id.item_qty)).setMinValue(1);
        ((NumberPicker)view.findViewById(R.id.item_qty)).setMaxValue(100);
        ((NumberPicker)view.findViewById(R.id.item_qty)).setValue(MainActivity.orderedItems.get(id).qty);

        builder.setView(view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        OrderItemEditDialog.this.getDialog().cancel();
                    }
                })
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.orderedItems.get(id).qty = ((NumberPicker)view.findViewById(R.id.item_qty)).getValue();
                        Log.d("EDITITEM", "quantity set to " + MainActivity.orderedItems.get(id).qty);
                        mListener.refreshList();
                    }
                });

        return builder.create();
    }


    public  interface OnChangeQtyInterface {
        void refreshList();
    }

    private OnChangeQtyInterface mListener;

    // make sure the Activity implemented it
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnChangeQtyInterface) activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnChangeQtyInterface");
        }
    }
}