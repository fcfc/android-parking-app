package com.ice.parkingapp;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.*;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Date;

public class UseGPS extends Activity
{
    TextView textview;
    float speed;
    float bearing;
    double altitude;
    double latitude;
    double longitude;
    String provider;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps);
        textview  = (TextView) findViewById(R.id.textdata);


        Button b = (Button) findViewById(R.id.button6);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                // get location and date stamp
                // write to screen
                String ts = getDatestamp();
                String latShort = String.format("%.6f", latitude);
                String longShort = String.format("%.6f", longitude);
                textview.setText(textview.getText() + "\n" + ts + "       " + latShort + "   " + longShort);
            }
        });


        Button b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UseGPS.this, MapsActivity.class);
                intent.putExtra("longitude", (float) longitude);
                intent.putExtra("latitude", (float) latitude);
                intent.putExtra("name", "CAR");
                intent.putExtra("zoom", 20.0f);
                intent.putExtra("dataset", 1);
                startActivity(intent);
            }
        });

        Button b3 = (Button) findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UseGPS.this, RestCalls.class);
                intent.putExtra("longitude", (float) longitude);
                intent.putExtra("latitude", (float) latitude);
                intent.putExtra("name", "CAR");
                intent.putExtra("zoom", 20.0f);
                intent.putExtra("dataset", 1);
                startActivity(intent);
            }
        });

        Button b4 = (Button) findViewById(R.id.button4);
        b4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UseGPS.this, AlarmActivity.class);
                startActivity(intent);
            }
        });
/* Use the LocationManager class to obtain GPS locations */
        LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        LocationListener mlocListener = new MyLocationListener();

        long minTime = 2 * 1000; // Minimum time interval for update in seconds, i.e. 5 seconds.
        long minDistance = 1; // Minimum distance change for update in meters, i.e. 10 meters.

        mlocManager.requestLocationUpdates( LocationManager.GPS_PROVIDER, minTime, minDistance, mlocListener);

    }


/* Class My Location Listener */

    public class MyLocationListener implements LocationListener
    {
        @Override
        public void onLocationChanged(Location loc)
        {
            speed = loc.getSpeed();
            bearing = loc.getBearing();
            altitude = loc.getAltitude();
            latitude = loc.getLatitude();
            longitude = loc.getLongitude();
            provider = loc.getProvider();

            String ts = getDatestamp();
            String Text = ts + ": " +
                    provider +
                    "\nSpeed = " + speed +
                    "\nBearing = " + bearing +
                    "\nAlt = " + altitude +
                    "\nLat = " + latitude +
                    "\nLong = " + longitude;

            //          Toast.makeText( getApplicationContext(),
            //                  Text,
            //                  Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider)
        {
            Toast.makeText( getApplicationContext(),
                    "Gps Disabled",
                    Toast.LENGTH_SHORT ).show();
        }

        @Override
        public void onProviderEnabled(String provider)
        {
            Toast.makeText( getApplicationContext(),
                    "Gps Enabled",
                    Toast.LENGTH_SHORT).show();
        }

        @Override

        public void onStatusChanged(String provider, int status, Bundle extras)
        {

        }

    }/* End of Class MyLocationListener */


    public String getDatestamp() {
        Long tsLong = System.currentTimeMillis()/1000;
        NumberFormat numberFormat = new DecimalFormat("#,###.00");
        String ts = getDateCurrentTimeZone(tsLong);
        return ts;
    }

    public  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentTimeZone = (Date) calendar.getTime();
            return sdf.format(currentTimeZone);
        }catch (Exception e) {
        }
        return "";
    }
}/* End of UseGps Activity */
