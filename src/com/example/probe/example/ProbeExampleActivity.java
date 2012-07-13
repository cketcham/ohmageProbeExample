
package com.example.probe.example;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;

import org.ohmage.probemanager.ProbeBuilder;

public class ProbeExampleActivity extends Activity {

    private ExampleProbeWriter probeWriter;
    private LocationManager mLocationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_probe_example);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        
        probeWriter = new ExampleProbeWriter(this);
        probeWriter.connect();
//        probeWriter.writeSimple("test2");
        ProbeBuilder probe = new ProbeBuilder();
//        probe.withLocation(mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER), Time.getCurrentTimezone());
        
        probe.withId("my_id");
        probeWriter.writeKittenCount(probe, 4);
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

}
