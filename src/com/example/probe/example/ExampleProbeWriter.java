
package com.example.probe.example;

import android.content.Context;
import android.os.RemoteException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ohmage.probemanager.ProbeBuilder;
import org.ohmage.probemanager.ProbeWriter;

import java.util.Arrays;

/**
 * This is an example probe writer which holds the data needed to write probes
 * to ohmage. The observer id, observer version, stream ids, and stream versions
 * should be the same as is specified in the observer xml uploaded to the
 * server.
 * 
 * @author cketcham
 */
public class ExampleProbeWriter extends ProbeWriter {

    private static final String OBSERVER_ID = "org.ohmage.blah.ExampleProbe";
    private static final int OBSERVER_VERSION = 2012071200;

    private static final String STREAM_SIMPLE = "simple";
    private static final int STREAM_SIMPLE_VERSION = 2012071200;

    private static final String STREAM_KITTENS = "kittens";
    private static final int STREAM_KITTENS_VERSION = 2012071200;
    
    private static final String STREAM_LIST = "list";
    private static final int STREAM_LIST_VERSION = 2012071200;

    public ExampleProbeWriter(Context context) {
        super(context);
    }

    /**
     * This function takes a string and writes it to the 'simple' stream
     * 
     * @param text
     */
    public void writeSimple(String text) {

        try {
            ProbeBuilder probe = new ProbeBuilder(OBSERVER_ID, OBSERVER_VERSION);
            probe.setStream(STREAM_SIMPLE, STREAM_SIMPLE_VERSION);

            JSONObject data = new JSONObject();
            data.put("simple_text", text);
            probe.setData(data.toString());
            probe.write(this);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * This function takes probes with a count of the number of cats found. It
     * also accepts the probe object since the caller might have specified a
     * location.
     * 
     * @param probe
     * @param count
     */
    public void writeKittenCount(ProbeBuilder probe, int count) {

        try {
            probe.setObserver(OBSERVER_ID, OBSERVER_VERSION);
            probe.setStream(STREAM_KITTENS, STREAM_KITTENS_VERSION);

            JSONObject data = new JSONObject();
            data.put("count", count);
            probe.setData(data.toString());

            probe.write(this);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    

    /**
     * This function writes a list of items
     * @param items
     */
    public void writeList(String... items) {

        try {
            ProbeBuilder probe = new ProbeBuilder(OBSERVER_ID, OBSERVER_VERSION);
            probe.setStream(STREAM_LIST, STREAM_LIST_VERSION);

            JSONObject data = new JSONObject();
            JSONArray list = new JSONArray(Arrays.asList(items));
            data.put("items", list);
            probe.setData(data.toString());

            probe.write(this);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
