package com.wagner.tickcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    int ticks = 0;

    boolean hide_ticks = false;
    boolean show_avg = true;
    boolean show_total = true;

    int goal_ticks = 10;

    int ticks_increment = 1;

    Thread timer;

    Chronometer simpleChronometer;

    boolean FINISHED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        Bundle bundle = getIntent().getExtras();
        hide_ticks = bundle.getBoolean("hide_ticks");
        show_total = bundle.getBoolean("show_total");
        goal_ticks = bundle.getInt("goal_ticks");
        ticks_increment = bundle.getInt("ticks_increment");
        simpleChronometer = (Chronometer) findViewById(R.id.total_counter); // initiate a chronometer

        if(!hide_ticks)
            ((TextView) findViewById(R.id.tick_counter)).setText("0");

        if(!show_total) {
            ((LinearLayout)findViewById(R.id.stats_layout)).setVisibility(View.GONE);
            ((LinearLayout)findViewById(R.id.message_layout)).setVisibility(View.VISIBLE);
        }else{
            ((LinearLayout)findViewById(R.id.message_layout)).setVisibility(View.GONE);
        }
    }

    public void goBack(View v){
        finish();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(FINISHED)
                return true;
            if (ticks == 0) {
                simpleChronometer.setBase(SystemClock.elapsedRealtime());
                ((TextView)findViewById(R.id.status_tv)).setText("STARTED");
                simpleChronometer.start(); // start a chronometer
            }



            if (ticks+ticks_increment >= goal_ticks) {
                ticks+=ticks_increment;
                ((TextView) findViewById(R.id.tick_counter)).setText("" + ticks);
                ((LinearLayout)findViewById(R.id.stats_layout)).setVisibility(View.VISIBLE);
                ((LinearLayout)findViewById(R.id.message_layout)).setVisibility(View.GONE);
                simpleChronometer.stop(); // start a chronometer
                FINISHED = true;
                ((TextView)findViewById(R.id.status_tv)).setText("FINISHED");
                return true;
            }else{
                ticks+=ticks_increment;
            }

            Log.e("[SANTI]", (hide_ticks) ? "Hiding ticks..." : "not hiding ticks");
            if (!hide_ticks)
                ((TextView) findViewById(R.id.tick_counter)).setText("" + ticks);
        }
        return true;
    }


}
