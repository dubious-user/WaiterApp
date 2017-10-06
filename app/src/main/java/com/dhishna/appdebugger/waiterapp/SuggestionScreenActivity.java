package com.dhishna.appdebugger.waiterapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.Console;
import java.util.ArrayList;

import static com.dhishna.appdebugger.waiterapp.MainActivity.orderedItems;

public class SuggestionScreenActivity extends AppCompatActivity implements SuggestionFragment.RatingCallbackInterface{

    ViewPager mPager;
    PagerAdapter mPagerAdapter;

    public static int NUM_PAGES = 2;

    public void setRating(int id, float rating){
        orderedItems.get(id).rating = ""+rating;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO possible bug 2, uncomment the following 2 lines, to make the app crash when reached suggest activity
        //Intent intent = getIntent();
        //orderedItems = (ArrayList<Item>) intent.getSerializableExtra("orderedItems");
        for(Item item : orderedItems){
            Log.d("OrderedItems", "name : " + item.name);
        }

        NUM_PAGES = orderedItems.size() + 2;

        setContentView(R.layout.activity_suggestion_screen);

        mPager = (ViewPager) findViewById(R.id.suggesttion_pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), orderedItems, this);
        mPager.setAdapter(mPagerAdapter);

    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        ArrayList<Item> data;
        SuggestionScreenActivity activity;

        public ScreenSlidePagerAdapter(FragmentManager fm, ArrayList<Item> data, SuggestionScreenActivity activity) {
            super(fm);
            this.data = data;
            this.activity = activity;
        }


        @Override
        public Fragment getItem(int position) {
            SuggestionFragment fragment = new SuggestionFragment();
            if(position == 0)
                fragment.setData(new Item(), 1, 0);
            else if(position == NUM_PAGES -1)
                fragment.setData(new Item(), 2, 0);
            else {
                //TODO possible bug if position -1 is replaced by position , then first one wont be shown, and app crashes when last page is reached
                fragment.setData(data.get(position - 1), 0, position - 1);
                fragment.setRatingCallbackInterface(activity);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void giveSuggestion(View v){
        Toast.makeText(this, "Thank you for your suggestion.", Toast.LENGTH_SHORT).show();
        /*

        not necessary

        for(Item item : orderedItems){
            Log.d("ratings", "ratings : " + item.rating);
        }
        */

        MainActivity.orderedItems = null;

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
