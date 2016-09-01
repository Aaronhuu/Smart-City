package com.example.aaronhu.maptest;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by aaronhu on 8/24/16.
 */
public class SetipActivity extends Activity {
    Button confirm2;
    Button cancel2;
    EditText ip1,port1,ip2,port2;
    Firebase mIP1,mIP2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setip);

        cancel2 = (Button)findViewById(R.id.cancel2);
        confirm2 =(Button)findViewById(R.id.confirm2);
        ip1 = (EditText)findViewById(R.id.ip1);
        ip2 = (EditText)findViewById(R.id.ip2);
        port1 = (EditText)findViewById(R.id.port1);
        port2 = (EditText)findViewById(R.id.port2);

        mIP1 = new Firebase("https://smart-city-3041c.firebaseio.com/ip&port1");
        mIP2 = new Firebase("https://smart-city-3041c.firebaseio.com/ip&port2");

        mIP1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                String ss = "/";
                String[] s = data.split(ss);

                ip1.setText(s[0]);
                port1.setText(s[1]);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mIP2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                String ss = "/";
                String[] s = data.split(ss);

                ip2.setText(s[0]);
                port2.setText(s[1]);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });




    }

    @Override
    protected void onStart() {
        super.onStart();


        cancel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirm2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //uplode everything to database;
                if(ip1.getText().length()<5||ip2.getText().length()<5||port1.getText().length()<1||port2.getText().length()<1){
                    //check whether everything filled.
                    Toast.makeText(SetipActivity.this, "Some content haven't been filled!", Toast.LENGTH_SHORT).show();
                }else{
                    //upload to firebase
                    mIP1.setValue(ip1.getText()+"/"+port1.getText());
                    mIP2.setValue(ip2.getText()+"/"+port2.getText());
                    Toast.makeText(SetipActivity.this, "ip1: "+ip1.getText()+"/"+port1.getText(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(SetipActivity.this, "ip2: "+ip2.getText()+"/"+port2.getText(), Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });


    }
}
