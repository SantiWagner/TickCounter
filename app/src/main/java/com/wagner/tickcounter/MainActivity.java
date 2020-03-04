package com.wagner.tickcounter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String tick = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openTickPicker(View v){
        Log.e("[SANTI]","Calaaa");
        final String[] values = {"1", "2", "3", "4", "5", "6"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a tick value");
        builder.setItems(values, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((TextView) findViewById(R.id.tick_value_tv)).setText(values[which]);
            }
        });
        builder.show();
    }

    public void startTickCounter(View v){
        Intent myIntent = new Intent(getApplicationContext(), CounterActivity.class);
        myIntent.putExtra("hide_ticks",  ((CheckBox)findViewById(R.id.hide_counter_cb)).isChecked());
        myIntent.putExtra("show_total", ((CheckBox)findViewById(R.id.show_elapsed_cb)).isChecked());
        myIntent.putExtra("ticks_increment",Integer.parseInt(((TextView) findViewById(R.id.tick_value_tv)).getText().toString()));
        myIntent.putExtra("goal_ticks",Integer.parseInt(((TextView) findViewById(R.id.goal_ticks)).getText().toString()));
        startActivity(myIntent);
    }
}
