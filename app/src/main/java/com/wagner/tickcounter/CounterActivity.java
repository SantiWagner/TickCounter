package com.wagner.tickcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

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

    final ToneGenerator toneGen1 = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);

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
            findViewById(R.id.stats_layout).setVisibility(View.GONE);
            findViewById(R.id.message_layout).setVisibility(View.VISIBLE);
        }else{
            findViewById(R.id.message_layout).setVisibility(View.GONE);
        }

        simpleChronometer.setBase(SystemClock.elapsedRealtime());
        ((TextView)findViewById(R.id.status_tv)).setText("RUNNING");

        simpleChronometer.start(); // start a chronometer
    }

    public void goBack(View v){
        toneGen1.release();
        finish();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(FINISHED)
                return true;

            toneGen1.startTone(ToneGenerator.TONE_PROP_BEEP2,150);

            if (ticks+ticks_increment >= goal_ticks) {
                ticks+=ticks_increment;
                ((TextView) findViewById(R.id.tick_counter)).setText("" + ticks);
                findViewById(R.id.stats_layout).setVisibility(View.VISIBLE);
                findViewById(R.id.message_layout).setVisibility(View.GONE);
                simpleChronometer.stop(); // start a chronometer
                FINISHED = true;
                findViewById(R.id.running_gif).setVisibility(View.GONE);
                ((TextView)findViewById(R.id.status_tv)).setText("GOAL REACHED");
                findViewById(R.id.completed_image).setVisibility(View.VISIBLE);
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
