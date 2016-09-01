package com.example.aaronhu.maptest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;


public class MainActivity extends Activity {

    private DrawCG mDrawCG;
    private ImageView imgView;
    private int car=1;
    private int you=9;
    private Firebase mApply,mIP1,mIP2,mLocation;
    private String ipAddress = "";
    private String ipAddress1 = "";
    private String portNumber = "";
    private String portNumber1 = "";
    private String response="9";
    private final Handler handler = new Handler();

    private final Runnable task = new Runnable() {

        public void run() {
            // TODO Auto-generated method stub
            if (true) {  //change to refresh if it is auto mod
                handler.postDelayed(this, 5000);

            doCreateLine(getWindow().getDecorView().findViewById(android.R.id.content));

            }

        }
    };

    public void init(){
        // 获取手机窗口的大小
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();


        imgView = (ImageView) findViewById(R.id.imgView);
        mDrawCG = new DrawCG(MainActivity.this, screenWidth,
                screenHeight);
        HandleAllBlocks h = new HandleAllBlocks();

        h.setCar(car-1);
        h.setYou(you - 1);

        TextView carT = (TextView)findViewById(R.id.textView);
        carT.setText("Car: 0" + car);
        TextView youT = (TextView)findViewById(R.id.textView2);
        youT.setText("You: 0" + you);

        imgView.setImageBitmap(mDrawCG.drawRect(h));
        imgView.setImageBitmap(mDrawCG.drawLine(car, you));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApply = new Firebase("https://smart-city-3041c.firebaseio.com/applys");
        mIP1 = new Firebase("https://smart-city-3041c.firebaseio.com/ip&port1");
        mIP2 = new Firebase("https://smart-city-3041c.firebaseio.com/ip&port2");
        mLocation = new Firebase("https://smart-city-3041c.firebaseio.com/location");
        init();
    }

    // 按钮事件
    public void doCreateLine(View view) {
        mIP2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                String ss = "/";
                String[] s = data.split(ss);

                portNumber = s[1];
                ipAddress = s[0];

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        if (ipAddress.length() > 0 && portNumber.length() > 0) {
            new HttpRequestAsyncTask(view.getContext(), "fuck", ipAddress, portNumber).execute();
        }
    }

    public void doCreateRect(View view) {

        Intent intent = new Intent(MainActivity.this, WifiActivity.class);
        intent.putExtra("ip",ipAddress1);
        intent.putExtra("port",portNumber1);
        startActivity(intent);
  //      finish();
    }

    public void doUseLocker(View view){
        Intent intent = new Intent(MainActivity.this, ApplyLockerActivity.class);
        startActivity(intent);
    }

    public void doSetIp(View view){
        Intent intent = new Intent(MainActivity.this, SetipActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mApply.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                String ss = "/";
                String[] s = data.split(ss);
                //Toast.makeText(MainActivity.this, s[1], Toast.LENGTH_SHORT).show();
                car = Integer.parseInt(s[1]);
                init();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                you = Integer.parseInt(data);
                init();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mIP1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String data = dataSnapshot.getValue(String.class);
                String ss = "/";
                String[] s = data.split(ss);

                ipAddress1 = s[0];
                portNumber1 = s[1];

                //Toast.makeText(MainActivity.this, ipAddress1+": "+portNumber1, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        handler.postDelayed(task, 100);

    }

    @Override
    protected void onResume() {
        super.onResume();
        onCreate(null);
    }

    /**
     * Description: Send an HTTP Get request to a specified ip address and port.
     * Also send a parameter "pin" with the value of "pinNumber".
     *
     * @param state
     *            the pin number to toggle
     * @param ipAddress
     *            the ip address to send the request to
     * @param portNumber
     *            the port number of the ip address
     * @return The Arduino's reply text, or an ERROR message is it fails to
     *         receive one
     */
    public String sendRequest(String state, String ipAddress, String portNumber) {
        String arduinoResponse = "ERROR";

        try {

            HttpClient httpclient = new DefaultHttpClient(); // create an HTTP
            // client
            // define the URL e.g. http://myIpaddress:myport/?pin=13 (to toggle
            // pin 13 for example)
            URI website = new URI("http://" + ipAddress + ":" + portNumber + "/?state=" + state);
            HttpGet getRequest = new HttpGet(); // create an HTTP GET object
            getRequest.setURI(website); // set the URL of the GET request
            HttpResponse response = httpclient.execute(getRequest); // execute
            // the
            // request
            // get the Arduino's reply
            InputStream content = null;
            content = response.getEntity().getContent();
            BufferedReader in = new BufferedReader(new InputStreamReader(content));
            arduinoResponse = in.readLine();
            // Close the connection
            content.close();
        } catch (ClientProtocolException e) {
            // HTTP error
            arduinoResponse = e.getMessage();
            e.printStackTrace();
        } catch (IOException e) {
            // IO error
            arduinoResponse = e.getMessage();
            e.printStackTrace();
        } catch (URISyntaxException e) {
            // URL syntax error
            arduinoResponse = e.getMessage();
            e.printStackTrace();
        }
        // return the Arduino's reply/response text
        return arduinoResponse;
    }

    /**
     * An AsyncTask is needed to execute HTTP requests in the background so that
     * they do not block the user interface.
     */
    private class HttpRequestAsyncTask extends AsyncTask<Void, Void, Void> {

        // declare variables needed
        private String requestReply, ipAddress, portNumber,state;
        private Context context;
        //  private int pinNumber;

        /**
         * Description: The asyncTask class constructor. Assigns the values used
         * in its other methods.
         *
         * @param context
         *            the application context, needed to create the dialog
         * @param state
         *            the pin number to toggle
         * @param ipAddress
         *            the ip address to send the request to
         * @param portNumber
         *            the port number of the ip address
         */
        public HttpRequestAsyncTask(Context context, String state, String ipAddress, String portNumber) {
            this.context = context;
            this.ipAddress = ipAddress;
            this.state = state;
            this.portNumber = portNumber;
        }

        /**
         * Name: doInBackground Description: Sends the request to the Arduino
         * WiFi Shield
         *
         * @param voids
         * @return
         */
        @Override
        protected Void doInBackground(Void... voids) {
            requestReply = sendRequest(state, ipAddress, portNumber);
            response = requestReply.toString();
            if(isNumeric(response)){
                mLocation.setValue(response);
            }
            return null;
        }

        /**
         * Name: onPostExecute Description: This function is executed after the
         * HTTP request returns from the WiFi shield. The function sets the
         * dialog's message with the reply text from the Arduino and display the
         * dialog if it's not displayed already (in case it was closed by
         * accident);
         *
         * @param aVoid
         *            void parameter
         */
        @Override
        protected void onPostExecute(Void aVoid) {

        }

        /**
         * Name: onPreExecute Description: This function is executed before the
         * HTTP request is sent to the WiFi Shield. The function will set the
         * dialog's message and display the dialog.
         */
        @Override
        protected void onPreExecute() {

        }

    }

    public static boolean isNumeric(String str){
        for (int i = 0; i < str.length(); i++){
            System.out.println(str.charAt(i));
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }

}
