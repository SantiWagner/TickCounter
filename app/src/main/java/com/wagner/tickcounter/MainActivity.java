package com.wagner.tickcounter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.CompoundButtonCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String tick = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((CheckBox)findViewById(R.id.mode_infinity)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                                            changeState(!isChecked, (isChecked) ? R.color.secondaryText : R.color.accent,(isChecked) ? R.color.secondaryText : R.color.main_text);
                                        }
                                    }
        );

        changeState(true,  R.color.secondaryText , R.color.secondaryText );
    }

    public void changeState(boolean state, int color, int textColor){
        ((EditText)findViewById(R.id.goal_ticks)).setEnabled(state);
        ((CheckBox)findViewById(R.id.hide_counter_cb)).setEnabled(state);
        ((CheckBox)findViewById(R.id.hide_counter_cb)).setChecked(false);

        ColorStateList colorStateList = ColorStateList.valueOf(getResources().getColor(color));
        ViewCompat.setBackgroundTintList( ((EditText)findViewById(R.id.goal_ticks)), colorStateList);

        if (Build.VERSION.SDK_INT < 21) {
            CompoundButtonCompat.setButtonTintList(((CheckBox)findViewById(R.id.hide_counter_cb)), ColorStateList.valueOf(getResources().getColor(color)));//Use android.support.v4.widget.CompoundButtonCompat when necessary else
        } else {
            ((CheckBox)findViewById(R.id.hide_counter_cb)).setButtonTintList(ColorStateList.valueOf(getResources().getColor(color)));//setButtonTintList is accessible directly on API>19
        }

        ((EditText)findViewById(R.id.goal_ticks)).setTextColor(getResources().getColor(textColor));

        ((TextView)findViewById(R.id.goal_textview)).setTextColor(getResources().getColor(textColor));
        ((TextView)findViewById(R.id.hide_ticks_textview)).setTextColor(getResources().getColor(textColor));

        ((ImageView)findViewById(R.id.goal_imageview)).setColorFilter(ContextCompat.getColor(getApplicationContext(), color), PorterDuff.Mode.SRC_IN);
        ((ImageView)findViewById(R.id.hide_ticks_imageview)).setColorFilter(ContextCompat.getColor(getApplicationContext(), color), PorterDuff.Mode.SRC_IN);
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
        myIntent.putExtra("infinity_mode",  ((CheckBox)findViewById(R.id.mode_infinity)).isChecked());
        myIntent.putExtra("hide_ticks",  ((CheckBox)findViewById(R.id.hide_counter_cb)).isChecked());
        myIntent.putExtra("show_total", ((CheckBox)findViewById(R.id.show_elapsed_cb)).isChecked());
        myIntent.putExtra("ticks_increment",Integer.parseInt(((TextView) findViewById(R.id.tick_value_tv)).getText().toString()));
        myIntent.putExtra("goal_ticks",Integer.parseInt(((TextView) findViewById(R.id.goal_ticks)).getText().toString()));
        startActivity(myIntent);
    }
}
