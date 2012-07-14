
package com.example.probe.example;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ohmage.probemanager.ProbeBuilder;
import org.ohmage.probemanager.ResponseBuilder;

import java.util.Date;
import java.util.UUID;

public class ProbeExampleActivity extends Activity {

    private ExampleProbeWriter probeWriter;
    private LocationManager mLocationManager;
    private Location mLastLocation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        probeWriter = new ExampleProbeWriter(this);
        probeWriter.connect();

        setContentView(R.layout.activity_probe_example);

        final EditText catCount = (EditText) findViewById(R.id.cat_count);
        Button sendCatCount = (Button) findViewById(R.id.send_cat_count);
        sendCatCount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sendKittenCount(Integer.parseInt(catCount.getText().toString()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_probe_example, menu);
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        probeWriter.close();
    }

    /**
     * Sending a simple string probe
     */
    public void sendSimpleString(View v) {
        probeWriter.writeSimple("This string will be sent as a probe");
        Toast.makeText(this, "string sent", Toast.LENGTH_SHORT).show();
    }

    /**
     * Sending a kitten count
     * 
     * @param count the number of kittens counted
     */
    public void sendKittenCount(int count) {
        ProbeBuilder probe = new ProbeBuilder();
        if (mLastLocation != null)
            probe.withLocation(mLastLocation, Time.getCurrentTimezone());
        probeWriter.writeKittenCount(probe, count);
        Toast.makeText(this, "kitten count sent", Toast.LENGTH_SHORT).show();
    }

    /**
     * Sending a response to ohmage
     */
    public void sendResponse(View v) {
        // First we generate the response json. In this case this survey has one
        // prompt which is a timestamp prompt
        JSONArray responses = new JSONArray();
        JSONObject prompt = new JSONObject();
        try {
            prompt.put("value", "2012-07-13T11:01:43");
            prompt.put("prompt_id", "timestamp");
            responses.put(prompt);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Create the response builder for the correct campaign
        ResponseBuilder response = new ResponseBuilder(
                "urn:campaign:ca:ucla:cens:josh_prompt_types_test", "2011-11-16 12:55:38");

        // Set all the data for the response
        response.withTime(new Date().getTime(), Time.getCurrentTimezone())
                .withSurveyKey(UUID.randomUUID().toString()).withSurveyId("timestampSurvey")
                .withSurveyLaunchContext(new Date().getTime(), Time.getCurrentTimezone())
                .withResponses(responses.toString());

        if (mLastLocation != null)
            response.withLocation(mLastLocation, Time.getCurrentTimezone(), "valid");

        // Write the response
        try {
            response.write(probeWriter);
            Toast.makeText(this, "response sent", Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
