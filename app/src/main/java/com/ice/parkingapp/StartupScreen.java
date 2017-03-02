package com.ice.parkingapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ice on 6/1/16.
 */
public class StartupScreen extends Activity {

    private ListView mylist;
    public static List<City> theList = new ArrayList<City>();

    private List<City> cityList = new ArrayList<City>();
    String xmlFile = "xml/cities.xml";               // put this value in setup files
    @SuppressWarnings("unchecked")
    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


		/* set up handlers for UI elements  */
        setContentView(R.layout.startupscreen);
        Button But1= (Button)findViewById(R.id.button1);
        But1.setOnClickListener(myhandler);
        Button But2= (Button)findViewById(R.id.button2);
        But2.setOnClickListener(myhandler2);
        Button But3= (Button)findViewById(R.id.button3);
        But3.setOnClickListener(myhandler3);
        Button But4= (Button)findViewById(R.id.button4);
        But4.setOnClickListener(myhandler4);
        Button But5= (Button)findViewById(R.id.button5);
        But5.setOnClickListener(myhandler5);
        Button But6= (Button)findViewById(R.id.button6);
        But6.setOnClickListener(myhandler6);
        Button But7= (Button)findViewById(R.id.button7);
        But7.setOnClickListener(myhandler7);
    }

    View.OnClickListener myhandler = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(StartupScreen.this, UseGPS.class);
            startActivity(intent);
        }
    };

    View.OnClickListener myhandler2 = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(StartupScreen.this, VoiceRecorder.class);
            startActivity(intent);
        }
    };

    View.OnClickListener myhandler3 = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(StartupScreen.this, PhotoStuff.class);
            startActivity(intent);
        }
    };

    View.OnClickListener myhandler4 = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(StartupScreen.this, SendSMS.class);
            startActivity(intent);
        }
    };
    View.OnClickListener myhandler5 = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(StartupScreen.this, ParkingScreen.class);
            startActivity(intent);
        }
    };
    View.OnClickListener myhandler6 = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(StartupScreen.this, CityList.class);
            startActivity(intent);
        }
    };
    View.OnClickListener myhandler7 = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(StartupScreen.this, WebScreen.class);
            intent.putExtra("url", "http://www.crimi.com/NEXTBUS/index.php");
            startActivity(intent);
        }
    };
}
