package com.ice.parkingapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

/**
 * Created by ice on 6/1/16.
 */
public class ParkingScreen extends Activity {
    TextView block;
    TextView street;
    TextView side;
    TextView rule1;
    TextView rule2;
    TextView rule3;
    TextView moveby;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parking);
        // get block ID
        block = (TextView)findViewById(R.id.block);
        street= (TextView)findViewById(R.id.street);
        side = (TextView)findViewById(R.id.side);
        side = (TextView)findViewById(R.id.side);
        rule1 = (TextView)findViewById(R.id.rule1);
        rule2 = (TextView)findViewById(R.id.rule2);
        rule3 = (TextView)findViewById(R.id.rule3);
        moveby = (TextView)findViewById(R.id.moveby);


        TextClock time1 = (TextClock) findViewById(R.id.textClock);
        String hours = time1.getText().toString();
        moveby.setText(hours);
        // do db lookup for block ID
        // get present time
        // calculate when to move car
        // set alarm
    }



}
