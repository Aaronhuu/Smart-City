package com.example.aaronhu.maptest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class WifiActivity extends Activity implements View.OnClickListener {

    public final static String PREF_IP = "PREF_IP_ADDRESS";
    public final static String PREF_PORT = "PREF_PORT_NUMBER";
    // declare buttons and text inputs
    private Button button1, button2;
    private EditText editTextIPAddress, editTextPortNumber;
    // shared preferences objects used to save the IP address and port so that
    // the user doesn't have to
    // type them next time he/she opens the app.
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);

        sharedPreferences = getSharedPreferences("WIFI_SHIELD_PREFS", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        // assign buttons
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);


        // assign text inputs
        editTextIPAddress = (EditText) findViewById(R.id.editTextIPAddress);
        editTextPortNumber = (EditText) findViewById(R.id.editTextPortNumber);

        // set button listener (this class)
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        // get the IP address and port number from the last time the user used
        // the app,
        // put an empty string "" is this is the first time.
        editTextIPAddress.setText(sharedPreferences.getString(PREF_IP, ""));
        editTextPortNumber.setText(sharedPreferences.getString(PREF_PORT, ""));

    }

    @Override
    public void onClick(View view) {

        // get the pin number
        //  int pinNumber = 0;
        String state = "";
        // get the ip address
        String ipAddress = editTextIPAddress.getText().toString().trim();
        // get the port number
        String portNumber = editTextPortNumber.getText().toString().trim();

        // save the IP address and port for the next time the app is used
        editor.putString(PREF_IP, ipAddress); // set the ip address value to
        // save
        editor.putString(PREF_PORT, portNumber); // set the port number to save
        editor.commit(); // save the IP and PORT

        // get the pin number from the button that was clicked
        if (view.getId() == button1.getId()) {
            state = "open";
        } else if (view.getId() == button2.getId()) {
            state = "close";
        }
        Toast.makeText(WifiActivity.this, state, Toast.LENGTH_SHORT).show();
        // execute HTTP request
        if (ipAddress.length() > 0 && portNumber.length() > 0) {
            Toast.makeText(WifiActivity.this, "a", Toast.LENGTH_SHORT).show();
            new HttpRequestAsyncTask(view.getContext(), state, ipAddress, portNumber).execute();
        }
    }

    /**
     * Description: Send an HTTP Get request to a specified ip address and port.
     * Also send a parameter "pin" with the value of "pinNumber".
     *
     // * @param pinNumber
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
            URI website = new URI("http://" + ipAddress + ":" + portNumber + "/?state=" +state);//注意，不能写成“/？”+state
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
        private AlertDialog alertDialog;
        //private int pinNumber;

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

            alertDialog = new AlertDialog.Builder(this.context)
                    .setTitle("HTTP Response From IP Address:")
                    .setCancelable(true)
                    .create();

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
            alertDialog.setMessage(requestReply);
            if (!alertDialog.isShowing()) {
                alertDialog.show(); // show dialog
            }
        }

        /**
         * Name: onPreExecute Description: This function is executed before the
         * HTTP request is sent to the WiFi Shield. The function will set the
         * dialog's message and display the dialog.
         */
        @Override
        protected void onPreExecute() {
            alertDialog.setMessage("Sending data to Arduino, please wait...");
            if (!alertDialog.isShowing()) {
                alertDialog.show();
            }
        }

    }
}