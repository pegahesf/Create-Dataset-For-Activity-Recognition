package com.example.pegah.finalfordataset;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class deviceinfo  extends Activity implements SensorEventListener {

    private SensorManager sensorManager;
    private SensorManager sManager;
    private boolean color = false;
    private View view;
    private long lastUpdate;
    String  stim;
    TextView ai,gi;
    Connection con;
    String did,asname,asvendor,asversion,asresolution,aspower,asmaxrange,email;
    String gsname,gsvendor,gsversion,gsresolution,gspower,gsmaxrange;
    String un,pass,passw,db,ip;
    private TextView err;
    private TextView mail;
    String name;
    String myemail;
    int d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceinfo);


        Bundle bundle = getIntent().getExtras();
        myemail = bundle.getString("name1");
        passw = bundle.getString("pass1");

        ai = (TextView) findViewById(R.id.showaccinfo);
        gi = (TextView) findViewById(R.id.showgyroinfo);
        Button accinfo = (Button) findViewById(R.id.accinfo);
        Button gyroinfo = (Button) findViewById(R.id.gyroinfo);
        ip = "a-ir10.serverpars.com";
        db = "youtabpr_esfehani";
        un = "youtabpr_esfeh";
        pass = "8yO7hv7!";
        final String mmodel=  android.os.Build.MODEL;
        String z = "";

//-----------------------
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();
        //-----------------------------------------------------------------------------
        try
        {
            con = connectionclass(un, pass, db, ip);        // Connect to database
            if (con == null)
            {
                z = "Check Your Internet Access!";
            }
            else
            {

// Change below query according to your own database.
                String result = "";
                String query = "SELECT MAX(did) from [device] where  email=N'" + myemail +"' ";
                //   String query = "SELECT activity,COUNT(activity) from [userinfo] where email='pegahesfehsni@yahoo.com' Group By activity";
                //  String query= "Delete from userinfo";

                Statement stmt = con.createStatement();
                //stmt.execute(query);
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    if (rs.getString(1) == null)
                    {
                        d = 0;
                    }

                    else
                        d = Integer.parseInt(rs.getString(1)) + 1;
                }
                con.close();

            }
        }
        catch (Exception ex)
        {
            err.setText("Error connection:" + ex.getMessage());
        }
        //-----------------------------------------------------------------------------
        accinfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String z = "";
                try {
                    con = connectionclass(un, pass, db, ip);        // Connect to database
                    if (con == null) {
                        z = "Check Your Internet Access!";
                    } else {

// Change below query according to your own database.

                        String query = "INSERT INTO device([did],[sname],[svendor],[sversion],[sresolution],[spower],[smaxrange],[email],[mobilemodel]) VALUES (N'" + Integer.toString(d) + "',N'" + asname + "',N'" + asvendor + "',N'" + asversion + "',N'" + asresolution + "',N'" + aspower + "',N'" + asmaxrange + "',N'" + myemail +"',N'"+mmodel +"') ";
                        Statement stmt = con.createStatement();
                        try {
                            stmt.execute(query);
                            Toast.makeText(getBaseContext(), "insert accelerometer information successfully", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception ex){
                            Log.w("Error in Insertion","" + ex.getMessage());
                        }

                    }
                } catch (Exception ex) {
                    err.setText("Error connection:" + ex.getMessage());
                }


            }
        });
//----------------------------------------

        gyroinfo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //---------------


               String z = "";
                try {
                    con = connectionclass(un, pass, db, ip);        // Connect to database
                    if (con == null) {
                        z = "Check Your Internet Access!";
                    } else {

// Change below query according to your own database.

                        String query = "INSERT INTO device([did],[sname],[svendor],[sversion],[sresolution],[spower],[smaxrange],[email],[mobilemodel]) VALUES (N'" + Integer.toString(d) + "',N'" + gsname + "',N'" + gsvendor + "',N'" + gsversion + "',N'" + gsresolution + "',N'" + gspower + "',N'" + gsmaxrange + "',N'" + myemail +"',N'"+mmodel +"') ";
                        Statement stmt = con.createStatement();
                        try {
                            stmt.execute(query);
                            Toast.makeText(getBaseContext(), "insert gyroscope information successfully", Toast.LENGTH_LONG).show();
                        }
                        catch (Exception ex){
                            Log.w("Error in Insertion","" + ex.getMessage());
                        }

                    }
                } catch (Exception ex) {
                    err.setText("Error connection:" + ex.getMessage());
                }


            }
        });



    }




        @Override
        public void onSensorChanged(SensorEvent event) {

            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER  ) {

                ai.setText("value X  :" + String.format("%.4f", event.values[0]) + "\n" +
                           "value Y  :" + String.format("%.4f", event.values[1]) + "\n" +
                           "value Z  :" + String.format("%.4f", event.values[2]) + "\n" +
                           "--------------sensor information ------------------"+ "\n" +
                           "Senor type  :" +   event.sensor.getType() + "\n" +
                           "Sensor name  :" +   event.sensor.getName() + "\n" +
                           "Sensor Vendor  :" +   event.sensor.getVendor() + "\n" +
                           "Sensor  Version :" +   event.sensor.getVersion() + "\n" +
                          "Sensor Resolution  :" +   event.sensor.getResolution() + "\n" +
                          "Sensor Power  :" +   event.sensor.getPower()+  "\n" +
                          "Sensor  Maximum Range :" +   event.sensor.getMaximumRange() + "\n"

                );



                        asname =  event.sensor.getName();
                        asvendor=  event.sensor.getVendor();
                        asversion =   Integer.toString(event.sensor.getVersion());
                        asresolution  = Float.toString(event.sensor.getResolution())  ;
                        aspower= Float.toString(event.sensor.getPower());
                        asmaxrange= Float.toString(event.sensor.getMaximumRange());
            }
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE  ) {

                gi.setText("value X  :" + String.format("%.4f", event.values[0]) + "\n" +
                        "value Y  :" + String.format("%.4f", event.values[1]) + "\n" +
                        "value Z  :" + String.format("%.4f", event.values[2]) + "\n" +
                        "--------------sensor information ------------------"+ "\n" +
                        "Gyrouscope Senor type  :" +   event.sensor.getType() + "\n" +
                        "Gyrouscope Sensor name  :" +   event.sensor.getName() + "\n" +
                        "Gyrouscope Sensor Vendor  :" +   event.sensor.getVendor() + "\n" +
                        "Gyrouscope Sensor  Version :" +   event.sensor.getVersion() + "\n" +
                        "Gyrouscope Sensor Resolution  :" +   event.sensor.getResolution() + "\n" +
                        "Gyrouscope Sensor Power  :" +   event.sensor.getPower()+  "\n" +
                        "Gyrouscope Sensor  Maximum Range :" +   event.sensor.getMaximumRange() + "\n"

                );



                gsname =  event.sensor.getName();
                gsvendor=  event.sensor.getVendor();
                gsversion =   Integer.toString(event.sensor.getVersion());
                gsresolution  = Float.toString(event.sensor.getResolution())  ;
                gspower= Float.toString(event.sensor.getPower());
                gsmaxrange= Float.toString(event.sensor.getMaximumRange());
            }


        }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // register this class as a listener for the orientation and
        // accelerometer sensors
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // unregister listener
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    public Connection connectionclass(String user, String password, String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + server + ":1433/" + database + ";user=" + user + ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se) {
            Log.e("error here 1 : ", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("error here 2 : ", e.getMessage());
        } catch (Exception e) {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }
}



        //-----------------------------------




