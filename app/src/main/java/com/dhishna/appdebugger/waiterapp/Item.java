package com.dhishna.appdebugger.waiterapp;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by dovahkin on 3/10/17.
 */

public class Item implements Serializable{

    public String name, rating, time, desc;
    public int qty, drawable_id;
    public Drawable drawable;
    public float price;
};
