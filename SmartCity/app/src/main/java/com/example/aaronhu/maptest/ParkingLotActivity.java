package com.example.aaronhu.maptest;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;

/**
 * Created by aaronhu on 8/8/16.
 */
public class ParkingLotActivity extends Activity {
    Button lot1,lot2,lot3,lot4,lot5,lot6,lot7,lot8,lot9;
    Button cancel,commit;
    TextView lotnum;
    Firebase mParkingLot;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);

        lot1 = (Button)findViewById(R.id.lot1);
        lot2 = (Button)findViewById(R.id.lot2);
        lot3 = (Button)findViewById(R.id.lot3);
        lot4 = (Button)findViewById(R.id.lot4);
        lot5 = (Button)findViewById(R.id.lot5);
        lot6 = (Button)findViewById(R.id.lot6);
        lot7 = (Button)findViewById(R.id.lot7);
        lot8 = (Button)findViewById(R.id.lot8);
        lot9 = (Button)findViewById(R.id.lot9);
        cancel = (Button)findViewById(R.id.cancel_action);
        commit = (Button)findViewById(R.id.commit_action);
        lotnum = (TextView)findViewById(R.id.lotid);




    }


    @Override
    protected void onStart() {
        super.onStart();
        mParkingLot = new Firebase("https://smart-city-3041c.firebaseio.com/parking_lot");


        lot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lotnum.setText("01");
            }
        });

        lot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lotnum.setText("02");
            }
        });

        lot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lotnum.setText("03");
            }
        });

        lot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lotnum.setText("04");
            }
        });

        lot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lotnum.setText("05");
            }
        });

        lot6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lotnum.setText("06");
            }
        });

        lot7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lotnum.setText("07");
            }
        });

        lot8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lotnum.setText("08");
            }
        });

        lot9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lotnum.setText("09");
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mParkingLot.setValue(lotnum.getText());
                finish();
            }
        });







    }
}
