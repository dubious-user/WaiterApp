package com.dhishna.appdebugger.waiterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.Console;
import java.util.ArrayList;

public class SuggestionScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion_screen);

        Intent intent = getIntent();
        ArrayList<Item> orderedItems = (ArrayList<Item>) intent.getSerializableExtra("orderedItems");
        for(Item item : orderedItems){
            Log.d("OrderedItems", "name : " + item.name);
        }

    }
}
