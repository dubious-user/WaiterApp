package com.dhishna.appdebugger.waiterapp;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Item> orderedItems;
    public static ArrayList<Item> foodItems, beverages;
//    public static double deviceWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("HERERE", "bef");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("HERERE", "aft");
        // clear back stack!!! TODO

        orderedItems = new ArrayList<>();
        foodItems = new ArrayList<>();
        beverages = new ArrayList<>();

        Log.d("HERERE", "inintse");
        TypedArray food_names = getResources().obtainTypedArray(R.array.food_names);
        TypedArray food_ratings = getResources().obtainTypedArray(R.array.food_ratings);
        TypedArray food_prices = getResources().obtainTypedArray(R.array.food_prices);
        TypedArray food_times = getResources().obtainTypedArray(R.array.food_times);
        TypedArray food_descriptions = getResources().obtainTypedArray(R.array.food_descriptions);
        TypedArray food_drawables = getResources().obtainTypedArray(R.array.food_drawables);

        TypedArray bev_names = getResources().obtainTypedArray(R.array.bev_names);
        TypedArray bev_ratings = getResources().obtainTypedArray(R.array.bev_ratings);
        TypedArray bev_prices = getResources().obtainTypedArray(R.array.bev_prices);
        TypedArray bev_times = getResources().obtainTypedArray(R.array.bev_times);
        TypedArray bev_descriptions = getResources().obtainTypedArray(R.array.bev_descriptions);
        TypedArray bev_drawables = getResources().obtainTypedArray(R.array.bev_drawables);

        Log.d("HERERE", "loaded");
        for(int i = 0; i < food_names.length(); ++i){
            Item item = new Item();
            item.name = food_names.getString(i);
            item.desc = food_descriptions.getString(i);
            item.drawable_id = food_drawables.getResourceId(i, -1);
            item.drawable = null;
            item.price = food_prices.getFloat(i, 0);
            item.qty = 1;
            item.rating = food_ratings.getString(i);
            item.time = food_times.getString(i);

            foodItems.add(item);
        }

        for(int i = 0; i < food_names.length(); ++i){
            Item item = new Item();
            item.name = bev_names.getString(i);
            item.desc = bev_descriptions.getString(i);
            item.drawable_id = bev_drawables.getResourceId(i, -1);
            item.drawable = null;
            item.price = bev_prices.getFloat(i, 0);
            item.qty = 1;
            item.rating = bev_ratings.getString(i);
            item.time = bev_times.getString(i);

            beverages.add(item);
        }

        Log.d("HERERE", "aft loop");
        new ImageLoader().execute(foodItems);
        new ImageLoader().execute(beverages);

        Log.d("HERERE", "called tasks");
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
        Intent intent = new Intent(this, SelectionActivity.class);
//        intent.putExtra("orderedItems", orderedItems);

        startActivity(intent);
    }



    public class ImageLoader extends AsyncTask<ArrayList, Void, Void> {
        ArrayList<Item> items;

        @Override
        protected Void doInBackground(ArrayList... params) {
            //Loading the drawable in the background
            items = params[0];

            double deviceWidth = getWindowManager().getDefaultDisplay().getWidth();

            for (Item item : items) {
                final Drawable image = getResources().getDrawable(item.drawable_id);
                BitmapDrawable bd = (BitmapDrawable) image;

                double imageHeight = bd.getBitmap().getHeight();
                double imageWidth = bd.getBitmap().getWidth();

                double ratio = deviceWidth / imageWidth;
                int newImageHeight = (int) (imageHeight * ratio);

                Bitmap bMap = BitmapFactory.decodeResource(getResources(), item.drawable_id);
                Drawable drawable = new BitmapDrawable(getResources(),
                        getResizedBitmap(bMap, newImageHeight, (int) deviceWidth));


                item.drawable = drawable;
                bMap.recycle();
                //After the drawable is loaded, onPostExecute is called
            }
            return null;
        }

        public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {

            int width = bm.getWidth();
            int height = bm.getHeight();

            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;

            // create a matrix for the manipulation
            Matrix matrix = new Matrix();

            // resize the bit map
            matrix.postScale(scaleWidth, scaleHeight);

            // recreate the new Bitmap
            Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                    matrix, false);

            return resizedBitmap;
        }

        @Override
        protected void onPostExecute(Void vvoid) {
            //Hide the progress bar
            //Set the layout background with your loaded drawable
            //RelativeLayout layout = (RelativeLayout) findViewById(R.id.my_layout);
            //layout.setBackgroundDrawable(loaded);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}
