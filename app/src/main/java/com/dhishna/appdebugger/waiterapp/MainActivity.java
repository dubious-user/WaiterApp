package com.dhishna.appdebugger.waiterapp;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    public static ArrayList<Item> orderedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // clear back stack!!! TODO

        orderedItems = new ArrayList<>();

        TypedArray food_names = getResources().obtainTypedArray(R.array.food_names);
        TypedArray food_ratings = getResources().obtainTypedArray(R.array.food_ratings);
        TypedArray food_prices = getResources().obtainTypedArray(R.array.food_prices);
        TypedArray food_times = getResources().obtainTypedArray(R.array.food_times);
        TypedArray food_descriptions = getResources().obtainTypedArray(R.array.food_descriptions);
        TypedArray food_drawables = getResources().obtainTypedArray(R.array.food_drawables);

        for(int i = 0; i < food_names.length(); ++i){
            Item item = new Item();
            item.name = food_names.getString(i);
            item.desc = food_descriptions.getString(i);
            item.drawable = food_drawables.getResourceId(i, -1);
            item.price = food_prices.getFloat(i, 0);
            item.qty = 0;
            item.rating = food_ratings.getString(i);
            item.time = food_times.getString(i);

            orderedItems.add(item);
        }

        // uncomment the following statement to automatically redirect to list activity
        // takeOrder(new View(this));
/*
        Intent intent = new Intent(this, SuggestionScreenActivity.class);
        intent.putExtra("orderedItems", orderedItems);

        startActivity(intent);
*/    }

    public void takeOrder(View v){

        // change the SelectScreenActivity name if needed to
        // startActivity(new Intent(this, SelectScreenActivity.class));
        Intent intent = new Intent(this, ConfirmOrderActivity.class);
//        intent.putExtra("orderedItems", orderedItems);

        startActivity(intent);
    }
}
