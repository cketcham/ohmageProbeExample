ohmageProbeExample
==================

Example application which sends probe data to ohmage. Take a look at [Example Observer XML](https://github.com/cketcham/ohmageProbeExample/wiki/Example-Observer-XML) for an
example of what your observer should look like on the server.

It needs the `SEND_PROBES` permission to be allowed to send probes to ohmage

    <uses-permission android:name="org.ohmage.SEND_PROBES" />

It should also register itself with ohmage by specifying meta-data in the application tag

    <meta-data
        android:name="org.ohmage.probemanager"
        android:resource="@xml/probe" />

The probe.xml file referenced should look like this:

    <probe xmlns:probe="http://schemas.android.com/apk/res-auto"
        probe:observerId="org.ohmage.blah.ExampleProbe"
        probe:observerName="ExampleProbe"
        probe:observerVersionCode="2013012300"
        probe:observerVersionName="1.0" />

More than one probe can be registered if your app sends more than one probe by specifying something like this:

    <probes xmlns:probe="http://schemas.android.com/apk/res-auto">
        <probe
            probe:observerId="org.ohmage.Probe1"
            probe:observerName="Probe1"
            probe:observerVersionCode="1"
            probe:observerVersionName="1.0" />
        <probe
            probe:observerId="org.ohmage.Probe2"
            probe:observerName="Probe2"
            probe:observerVersionCode="1"
            probe:observerVersionName="1.0" />
    </probes>
