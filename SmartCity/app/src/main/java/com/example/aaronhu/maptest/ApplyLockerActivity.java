package com.example.aaronhu.maptest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Random;

/**
 * Created by aaronhu on 8/3/16.
 */
public class ApplyLockerActivity extends Activity {
    Firebase mRef,mEnd,mParkingLot,mApply;
    private int verificationcode=0;
    private Button fromPicker, toPicker, ParkingLotPicker;
    private TextView fromTime, toTime, carNum;
    Button submit;
    EditText phonenumber;
    EditText vercode;
    TextView vet;
    String lot = "";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locker);

        phonenumber = (EditText)findViewById(R.id.phonenumber);  //save the phneno. of the vip
        fromPicker = (Button)findViewById(R.id.tp);
        toPicker = (Button)findViewById(R.id.tp2);
        ParkingLotPicker = (Button)findViewById(R.id.picklot);
        fromTime = (TextView)findViewById(R.id.fromtime);
        toTime = (TextView)findViewById(R.id.totime);
        vercode = (EditText)findViewById(R.id.vercode);
        vet = (TextView)findViewById(R.id.verificodetext);

        Random r = new Random();
        verificationcode =1000+r.nextInt(9000);
        vet.setText(""+verificationcode);

        carNum = (TextView)findViewById(R.id.carnum);

        Button getVerificationCode = (Button)findViewById(R.id.btnverification);
        submit = (Button)findViewById(R.id.btnregister);

        getVerificationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                verificationcode =1000+r.nextInt(9000);
                vercode.setText("");
                vet.setText(""+verificationcode);
            }
        });


    }

    public void setParkingLotPicker(View view){
        Intent intent = new Intent(ApplyLockerActivity.this,ParkingLotActivity.class);
        startActivity(intent);
    }

    public void timePicker(View view){
        if(view.getId() == fromPicker.getId()){
            Intent intent = new Intent(ApplyLockerActivity.this, TimePickerActivity.class);
            intent.putExtra("level",0);
            startActivity(intent);
        }else{
            Intent intent = new Intent(ApplyLockerActivity.this, TimePickerActivity.class);
            intent.putExtra("level",1);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mRef = new Firebase("https://smart-city-3041c.firebaseio.com/time");
        mEnd = new Firebase("https://smart-city-3041c.firebaseio.com/end");
        mParkingLot = new Firebase("https://smart-city-3041c.firebaseio.com/parking_lot");
        mApply = new Firebase("https://smart-city-3041c.firebaseio.com/applys");

        mEnd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                toTime.setText(data);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                fromTime.setText(data);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mParkingLot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                lot = data;
                carNum.setText("Lot: "+data);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check phone number whether it is fit the normal phone number
                if (phonenumber.getText().toString().length() == 11) {
                    //check verification code
                    if (vercode.getText().toString().equals("" + verificationcode)) {
                        //store to database
                        mApply.setValue(phonenumber.getText().toString()+"/"+lot+"/"+fromTime.getText()+"/"+toTime.getText());
                        finish();
                    } else {
                        Toast.makeText(ApplyLockerActivity.this, "Not correct verification code!", Toast.LENGTH_SHORT).show();
                        Random r = new Random();
                        verificationcode = r.nextInt(10000);
                        vet.setText("" + verificationcode);
                        vercode.setText("");
                    }
                } else {
                    Toast.makeText(ApplyLockerActivity.this, "Your phone Numner have to be 11 digits!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }





}
