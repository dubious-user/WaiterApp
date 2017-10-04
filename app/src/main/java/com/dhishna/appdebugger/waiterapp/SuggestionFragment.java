package com.dhishna.appdebugger.waiterapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dovahkin on 4/10/17.
 */

public class SuggestionFragment extends Fragment {

    private Item data;
    private int ends;
    private int id;

    public void setData(Item item, int ends, int id) {
        this.data = item;
        this.ends = ends;
        this.id = id;
    }

    public interface RatingCallbackInterface{
        public void setRating(int id, float rating);
    }

    RatingCallbackInterface myInterface;

    public void setRatingCallbackInterface(Activity activity){
        myInterface = (RatingCallbackInterface)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView;
        if (ends == 1) {
            rootView = (ViewGroup) inflater.inflate(R.layout.suggestion_fragment_start, container, false);
        } else if (ends == 2) {
            rootView = (ViewGroup) inflater.inflate(R.layout.suggestion_fragment_last, container, false);
        } else {
            rootView = (ViewGroup) inflater.inflate(R.layout.suggestion_fragment, container, false);
            //TODO possible bug, if data is not checked to be null, app crashes when layou changes
            if (data != null) {
                ((TextView) rootView.findViewById(R.id.item_name)).setText(data.name);
                ((ImageView) rootView.findViewById(R.id.item_image)).setImageResource(data.drawable);

                RatingBar ratingBar = (RatingBar) rootView.findViewById(R.id.item_rating);
                ratingBar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                        myInterface.setRating(id, v);
                    }
                });

            } else {
                ((TextView) rootView.findViewById(R.id.item_name)).setText("Sample text");
            }
        }
        return rootView;
    }
}