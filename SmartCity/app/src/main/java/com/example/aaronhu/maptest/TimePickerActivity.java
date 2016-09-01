package com.example.aaronhu.maptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by aaronhu on 8/5/16.
 */
public class TimePickerActivity extends Activity {

    TimePicker tp;
    Button cancel;
    Button confirm;
    Firebase mRef,mEnd;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timepicker);
        tp = (TimePicker)findViewById(R.id.timePicker);
        cancel = (Button)findViewById(R.id.cancel);
        confirm = (Button)findViewById(R.id.confirm);
    }


    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = getIntent();
        final int level = intent.getIntExtra("level",0);

        mRef = new Firebase("https://smart-city-3041c.firebaseio.com/time");
        mEnd = new Firebase("https://smart-city-3041c.firebaseio.com/end");

        cancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (level == 0) {
                    mRef.setValue(tp.getHour() + ": " + tp.getMinute());
                    //Toast.makeText(TimePickerActivity.this, "Start time: " + tp.getHour() + ": " + tp.getMinute(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    mEnd.setValue(tp.getHour() + ": " + tp.getMinute());
                    //Toast.makeText(TimePickerActivity.this, "Finish time: " + tp.getHour() + ": " + tp.getMinute(), Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });









    }
}
