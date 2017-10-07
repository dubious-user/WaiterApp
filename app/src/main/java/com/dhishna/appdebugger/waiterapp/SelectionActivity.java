package com.dhishna.appdebugger.waiterapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

//TODO not implementing the interface causes crash
public class SelectionActivity extends AppCompatActivity implements OrderItemEditDialog.OnChangeQtyInterface{

    static final int NUM = 2;
    public int currentTab = 0;
    ViewPager viewPager;
    myAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("HERERE", "sel act beg");
        //TODO possible bug, if activity_selection is replaced with activity_main. it crashes
        setContentView(R.layout.activity_selection);

        adapter = new myAdapter(getSupportFragmentManager(), this);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);


        Log.d("HERERE", "sel act inited");

        ActionBar actionBar = getSupportActionBar();

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getSupportActionBar().setSelectedNavigationItem(position);
            }
        });


        Log.d("HERERE", "beffff");

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                Log.d("TAB", "" + tab.getPosition());
                viewPager.setCurrentItem(tab.getPosition());
                currentTab = tab.getPosition();
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        };
        actionBar.addTab(actionBar.newTab().setText("Food").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Beverages").setTabListener(tabListener));


        Log.d("HERERE", "last");
    }

    public static class myAdapter extends FragmentPagerAdapter {
        SelectionActivity activity;
        public myAdapter(FragmentManager fm, SelectionActivity activity){
            super(fm);
            this.activity = activity;
        }

        @Override
        public int getCount(){
            return NUM;
        }

        @Override
        public Fragment getItem(int position){
            return ArrayListFrag.newInstance(position, activity);
        }
    }

    public static class ArrayListFrag extends ListFragment {
        int num;
        int position;
        SelectionActivity activity;
        ListView listView;
        SelectionListItemAdapter adapter;

        public static ArrayListFrag newInstance(int position, SelectionActivity activity){
            ArrayListFrag arrayListFrag = new ArrayListFrag();
            arrayListFrag.activity = activity;
            Bundle args = new Bundle();

            args.putInt("use",position);
            arrayListFrag.setArguments(args);
            Log.d("HERERE", "neww instance");

            return arrayListFrag;
        }

        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            num = getArguments()!=null ? getArguments().getInt("use") : 1;
            position = getArguments()!=null ? getArguments().getInt("use") : 0;

        }

        @Override
        public View onCreateView(LayoutInflater li, ViewGroup container, Bundle arg0){
            View view = li.inflate(R.layout.selection_list_fragment,container,false);
            /* TODO possible bug, uncomment this, for bug

                  listView = getListView();
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    adapter.pos = position;
                }
            });

             */
            return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState){
            super.onActivityCreated(savedInstanceState);
            listView = getListView();
            if(position == 0) {
                adapter = new SelectionListItemAdapter(activity, MainActivity.foodItems);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        adapter.pos = i;
                    }
                });
                setListAdapter(adapter);
            }
            else if(position == 1) {
                adapter = new SelectionListItemAdapter(activity, MainActivity.beverages);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        adapter.pos = i;
                    }
                });
                setListAdapter(adapter);
            }
        }

        @Override
        public void onListItemClick(ListView lv, View v, int position, long id){
            Toast.makeText(getActivity(),"Clicked " + ((TextView)v.findViewById(android.R.id.text1)).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void seeOrder(View view){
        if(MainActivity.orderedItems.size() != 0)
            startActivity(new Intent(this, ConfirmOrderActivity.class));
        else
            Toast.makeText(this, "You haven't ordered anything yet.", Toast.LENGTH_SHORT).show();
    }

    public void refreshList(){
        ((TextView)findViewById(R.id.order_item_count)).setText("Order of " + MainActivity.orderedItems.size() + " items.");
    }

}