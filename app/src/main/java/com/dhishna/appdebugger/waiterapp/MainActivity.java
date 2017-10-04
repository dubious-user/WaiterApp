package com.dhishna.appdebugger.waiterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // uncomment the following statement to automatically redirect to list activity
        // takeOrder(new View(this));
    }

    public void takeOrder(View v){

        // change the SelectScreenActivity name if needed to
        startActivity(new Intent(this, SelectionActivity.class));
    }
}
