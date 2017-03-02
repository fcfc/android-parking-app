package com.ice.parkingapp;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ParkingMeterTimer extends Activity {
    public int milliTimer = 1000;
    TextView timerTextView;
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;;
    long startTime = 0;

    //runs without a timer by reposting this handler at the end of the runnable
    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

//			timerTextView.setText(String.format("%d:%02d", minutes, seconds));
            imageView.setImageResource(getDigits(minutes / 10));
            imageView2.setImageResource(getDigits(minutes % 10));
            imageView3.setImageResource(getDigits(seconds / 10));
            imageView4.setImageResource(getDigits(seconds % 10));
            timerHandler.postDelayed(this, 500);
        }
    };
    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        imageView = (ImageView) findViewById(R.id.imageView1);
        imageView2 = (ImageView) findViewById(R.id.imageView2);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        imageView4 = (ImageView) findViewById(R.id.imageView4);

        Button b = (Button) findViewById(R.id.button);
        b.setText("start");
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("stop")) {
                    timerHandler.removeCallbacks(timerRunnable);
                    b.setText("start");
                } else {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setText("stop");
                }
            }
        });
    }
    @Override
    public void onPause () {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button b = (Button) findViewById(R.id.button);
        b.setText("start");
    }

    private int getDigits(int digit){
        int value = 0;
        switch(digit){
            case 0:
                value = R.drawable.led_0n;
                break;
            case 1:
                value = R.drawable.led_1n;
                break;
            case 2:
                value = R.drawable.led_2n;
                break;
            case 3:
                value = R.drawable.led_3n;
                break;
            case 4:
                value = R.drawable.led_4n;
                break;
            case 5:
                value = R.drawable.led_5n;
                break;
            case 6:
                value = R.drawable.led_6n;
                break;
            case 7:
                value = R.drawable.led_7n;
                break;
            case 8:
                value = R.drawable.led_8n;
                break;
            case 9:
                value = R.drawable.led_9n;
                break;
        }
        return value;
    }

}

