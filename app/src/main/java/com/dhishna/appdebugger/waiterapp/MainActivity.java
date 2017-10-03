package com.dhishna.appdebugger.waiterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList<Item> orderedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orderedItems = new ArrayList<>();

        for(int i = 0; i < 5; ++i){
            Item item = new Item();
            item.name = "item name " + i;
            item.desc = "item desc " + i;
            item.picLink = "item piclink " + i;
            item.price = 10*i;
            item.qty = i;
            item.rating = "item rating " + i;
            item.time = "item time " + i;

            orderedItems.add(item);
        }

        // uncomment the following statement to automatically redirect to list activity
        // takeOrder(new View(this));

        Intent intent = new Intent(this, SuggestionScreenActivity.class);
        intent.putExtra("orderedItems", orderedItems);

        startActivity(intent);
    }

    public void takeOrder(View v){

        // change the SelectScreenActivity name if needed to
        // startActivity(new Intent(this, SelectScreenActivity.class));
    }
}
