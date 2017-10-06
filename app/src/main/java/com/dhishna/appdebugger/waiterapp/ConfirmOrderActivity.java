package com.dhishna.appdebugger.waiterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.dhishna.appdebugger.waiterapp.MainActivity.orderedItems;

public class ConfirmOrderActivity extends AppCompatActivity implements OrderItemEditDialog.OnChangeQtyInterface{

//    private ArrayList<Item> orderedItems;
    private ListView listView;
    private OrderListItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);

  //      Intent intent = getIntent();
  //      orderedItems = (ArrayList<Item>) intent.getSerializableExtra("orderedItems");

        listView = (ListView) findViewById(R.id.ordered_list);
        adapter = new OrderListItemAdapter(this, orderedItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ConfirmOrderActivity.this, orderedItems.get(i).name, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void confirmOrder(View view){
        Intent intent = new Intent(this, SuggestionScreenActivity.class);
//        intent.putExtra("orderedItems", orderedItems);

        startActivity(intent);
    }

    public void cancelItem(int i){
        MainActivity.orderedItems.remove(i);
        refreshList();
    }

    public void refreshList(){
        adapter = new OrderListItemAdapter(this, orderedItems);
        listView.setAdapter(adapter);
        if(MainActivity.orderedItems.size() == 0){
            ((Button)findViewById(R.id.confirm_btn)).setEnabled(false);
        }
    }

    public void cancelOrder(View view){
        MainActivity.orderedItems = null;
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
