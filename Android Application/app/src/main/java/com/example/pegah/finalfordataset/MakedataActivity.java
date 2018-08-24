package com.example.pegah.finalfordataset;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.clusterers.EM;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;


public class MakedataActivity extends AppCompatActivity implements SensorEventListener {
    //a TextView
    private TextView gyro;

    String name, passw;
    //the Sensor Manager
    private SensorManager sManager;
    private SensorManager sensorManager;


    Calendar today = Calendar.getInstance();
    Calendar today2 = Calendar.getInstance();
    Calendar tosday = Calendar.getInstance();
    Calendar tosday2 = Calendar.getInstance();
    private TextView acc;
    private TextView ress;
    Typeface tf1;
    // private  TextView tstamp;

    Instances test;

    int nu,c0,c1,c2,c3,c4,c5,c6,count;
    // Declaring connection variables
    Connection con;
    String un, pass, db, ip ,statuss, stim;
    float sx, sy, sz;
    float gx, gy, gz, gtim;
    Long tsLong = System.currentTimeMillis();
    Long tsLong2 = System.currentTimeMillis();
    Spinner spinner,vaziat;
    EditText text, uuu, ppp,pos;
    boolean reccheck,reccheck2;
    List<String> list;
    Instances data;

    float  [][] insertitems = new  float[10][6];
    int insertcounter=0 ;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //End Declaring connection variables
    NaiveBayes cls2;
    FastVector atts;
    String uf;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        nu = 0;
        c0=c1=c2=c3=c4=c5=c6=count = 0;

        ArrayList<Attribute> atts = new ArrayList<Attribute>(3);

        atts.add(new Attribute("x"));
        atts.add(new Attribute("y"));
        atts.add(new Attribute("z"));



        // 2. create Instances object
        test = new Instances("walking", atts,0);
        //setContentView(R.layout.activity_sensoractivity);
        Bundle bundle = getIntent().getExtras();
        reccheck=false;
        reccheck2=false;
        name = bundle.getString("name1");
        passw = bundle.getString("pass1");

        // End Getting values from button, texts and progress bar
        // Declaring Server ip, username, database name and password
        ip = "pamsdataset.ir";
        db = "temsiran_activity";
        un = "temsiran_ehsang";
        pass = "ehsan@93";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makedata);

        Button detail = (Button) findViewById(R.id.detail);
        tf1 = Typeface.createFromAsset(getAssets(),"ALGER.TTF");
        detail.setTypeface(tf1);
        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mintent = new Intent(MakedataActivity.this, showdetail.class);
                mintent.putExtra("name1", name);
                startActivity(mintent);


            }
        });
        //get the TextView from the layout file
        gyro = (TextView) findViewById(R.id.gyro);
        acc = (TextView) findViewById(R.id.acc);
        //  ress = (TextView) findViewById(R.id.resul);
        // tstamp = (TextView) findViewById (R.id.tstamp);
        //get a hook to the sensor service
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        ////////////////
        spinner = (Spinner) findViewById(R.id.spinner1);
        vaziat = (Spinner) findViewById(R.id.spinner2);
        text = (EditText) findViewById(R.id.editText1);
        pos = (EditText) findViewById(R.id.posi);

        list = new ArrayList<String>();
        list.add("Walking");
        list.add("Jogging");
        list.add("Sitting");
        list.add("Standing");
        list.add("UpStaiars");
        list.add("DownStairs");
        list.add("lying");
        list.add("cycling");
        list.add("running");
        list.add("using mobile");



        ArrayAdapter<String> adp = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, list);
        adp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        spinner.setAdapter(adp);

        list = new ArrayList<String>();
        list.add("leg");
        list.add("hand");


        ArrayAdapter<String> adp2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, list);
        adp2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        vaziat.setAdapter(adp2);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                switch (arg2) {

                    case 0:
                        text.setText("Walking");
                        break;
                    case 1:
                        text.setText("Jogging");
                        break;
                    case 2:
                        text.setText("Sitting");
                        break;
                    case 3:
                        text.setText("Standing");
                        break;
                    case 4:
                        text.setText("UpStairs");
                        break;
                    case 5:
                        text.setText("DownStairs");
                        break;
                    case 6:
                        text.setText("lying");
                        break;
                    case 7 :
                        text.setText("cycling");
                        break;
                    case 8 :
                        text.setText("Running");
                        break;
                    case 9 :
                        text.setText("using mobile");
                        break;
                    default:
                        text.setText("non of them");
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
        vaziat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                switch (arg2) {

                    case 0:
                        pos.setText("leg");
                        break;
                    case 1:
                        pos.setText("hand");
                        break;

                    default:
                        pos.setText("non of them");
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    //when this Activity starts
    @Override
    protected void onResume() {
        super.onResume();
        /*register the sensor listener to listen to the gyroscope sensor, use the
        callbacks defined in this class, and gather the sensor information as quick
        as possible*/
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    //When this Activity isn't visible anymore
    @Override
    protected void onStop() {
        //unregister the sensor listener
        sManager.unregisterListener(this);
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Makedata Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.pegah.loginform/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1) {
        //Do nothing.
    }

    @Override
    public void onSensorChanged(SensorEvent event) {


        uuu = (EditText) findViewById(R.id.usernam);
        ppp = (EditText) findViewById(R.id.passwrd);
        uuu.setText(name);
        ppp.setText(passw);
        //if sensor is unreliable, return void
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            return;
        }
        int n = 3;
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

            gx = Float.parseFloat(String.format("%.5f", event.values[0]));
            gy = Float.parseFloat(String.format("%.5f", event.values[1]));
            gz = Float.parseFloat(String.format("%.5f", event.values[2]));

            gtim = event.timestamp / 100;
            //else it will output the Roll, Pitch and Yawn values
            gyro.setText("Gyroscope X  :" + String.format("%.5f", event.values[0]) + "\n" +
                    "Gyroscope Y  :" + String.format("%.5f", event.values[1]) + "\n" +
                    "Gyroscope Z  :" + String.format("%.5f", event.values[2]) + "\n" +
                    "Timestamp :" + event.timestamp / 100
            );
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            sx = Float.parseFloat(String.format("%.5f",event.values[0]));
            sy = Float.parseFloat(String.format("%.5f",event.values[1]));
            sz = Float.parseFloat(String.format("%.5f",event.values[2]));
            stim = Float.toString(event.timestamp / 100);
            acc.setText("value X  :" + String.format("%.5f",event.values[0]) + "\n" +
                    "value Y  :" + String.format("%.5f",event.values[1]) + "\n" +
                    "value Z  :" + String.format("%.5f",event.values[2]) + "\n" +
                    "Timestamp :" + event.timestamp / 100
            );
            // C
            // CheckBox checkBox = (CheckBox) findViewById(R.id.reccheck);


            Switch simpleSwitch1, simpleSwitch2;
            simpleSwitch1 = (Switch) findViewById(R.id.simpleSwitch1);;
            simpleSwitch2 = (Switch) findViewById(R.id.simpleSwitch2);
            statuss = text.getText().toString();
            String use,ps,em;
            use = em = uuu.getText().toString();
            ps = ppp.getText().toString();


            today2 = Calendar.getInstance();
            tsLong2 = System.currentTimeMillis();

            if (simpleSwitch1.isChecked()) {

                if (reccheck) {

                    String deviceName = android.os.Build.MODEL;
                    String deviceMan = android.os.Build.MANUFACTURER + deviceName;
                    String position = pos.getText().toString();
                    String act = text.getText().toString();
                    today2 = Calendar.getInstance();
                    tsLong2 = System.currentTimeMillis();
                    int diff = Math.abs((int) (today.getTimeInMillis() - today2.getTimeInMillis()));


                    today2 = today = Calendar.getInstance();
                    tsLong = System.currentTimeMillis();
                    String ddd;
                    ddd = today2.getTime().toString();
                    //  stim=stim + " ";
                    // this is the Asynctask, which is used to process in background to reduce load on app process

                    insert(sx, sy, sz,gx, gy, gz,use, em, act,ddd ,deviceMan,position);


                }

                else {
                    // statusSwitch1 = simpleSwitch1.getTextOn().toString();today2 = Calendar.getInstance();
                    tosday2 = Calendar.getInstance();
                    int diff2 = Math.abs((int) (tosday.getTimeInMillis() - tosday2.getTimeInMillis()));
                    if ((diff2) >= 10000) {
                        reccheck = true;
                        Toast.makeText(MakedataActivity.this, "Your sensory data is sending ", Toast.LENGTH_LONG).show();
                    }
                }
            }
            else if (simpleSwitch2.isChecked())
            {

                if (reccheck2) {
                    today2 = Calendar.getInstance();
                    tsLong2 = System.currentTimeMillis();
                    int diff = Math.abs((int) (today.getTimeInMillis() - today2.getTimeInMillis()));
//----------------------------------------------------------------------------------------------------------------


                    today2 = today = Calendar.getInstance();
                    tsLong = System.currentTimeMillis();
                    String ddd;
                    ddd = today2.getTime().toString();

                    double[] instanceValue1 = new double[3];
                    ///instanceValue1[0] = test.attribute(0).addStringValue(String.format("%.2f", event.values[0]));
                    ///instanceValue1[1] = test.attribute(0).addStringValue(String.format("%.2f", event.values[1]));
                    ///instanceValue1[2] = test.attribute(0).addStringValue(String.format("%.2f", event.values[2]));
                    ///instanceValue1[3] = test.attribute(0).addStringValue("?");
                    instanceValue1[0] = Math.floor((double) event.values[0] * 100) / 100;
                    instanceValue1[1] = Math.floor((double) event.values[1] * 100) / 100;
                    instanceValue1[2] = Math.floor((double) event.values[2] * 100) / 100;
                    DenseInstance denseInstance1 = new DenseInstance(1.0, instanceValue1);

                    //-------------------------------------------------------------------------------------



                    test.add(nu,denseInstance1);

                    today2 = Calendar.getInstance();
                    tsLong2 = System.currentTimeMillis();
                    int diff2 = Math.abs((int) (today.getTimeInMillis() - today2.getTimeInMillis()));
                    if (nu == 19) {
                        today2 = today = Calendar.getInstance();
                        tsLong = System.currentTimeMillis();
                        try {
                            MakeDensityBasedClusterer cls = new MakeDensityBasedClusterer();
                            cls.buildClusterer(test);
                            System.out.println(cls.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String result = "";

                        Instances to_use = null;
                        SimpleKMeans kMeans = new SimpleKMeans();
                        File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                        // if external memory exists and folder with name Notes
                        if (!root.exists()) {
                            root.mkdirs(); // this will create folder.
                        }
                        File filepath = new File(root, "pp.arff");  // file path to save
                        String yourFilePath = root.toString() + "/pp.arff";
                        String fileUrl = "/appname/data.xml";
                        String file = android.os.Environment.getExternalStorageDirectory().getPath() + yourFilePath;
                        File f = new File(yourFilePath);
                        if (f.exists()) {
                            Instances dataa = null;
                            try {
                                dataa = ConverterUtils.DataSource.read(yourFilePath);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            NaiveBayes clsc = new NaiveBayes();
                            Instances data = dataa;
            /*try {
                data = new Instances(new BufferedReader(new FileReader(yourFilePath)));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
                                /*try {
                                    test = new Instances(new BufferedReader(new FileReader(root.toString() + "/bcls.arff")));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }*/
                            J48 cls2 = new J48();
                            // data = new Instances(new BufferedReader(new FileReader("C:\\Users\\Hesam\\Documents\\NetBeansProjects\\wekaproj\\train10.arff")));
                            // test = new Instances(new BufferedReader(new FileReader("C:\\Users\\Hesam\\Documents\\NetBeansProjects\\wekaproj\\test10.arff")));
                            data.setClassIndex(data.numAttributes() - 1);
                            //test.setClassIndex(data.numAttributes() - 1);
                            try {
                                cls2.buildClassifier(data);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            int TP = 0;
                            int FP = 0;
                            int TN = 0;
                            int FN = 0;
                            // 20 samples
                            try {
                                demo();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            EM testkhoshe=new EM();
                            try {
                                testkhoshe.setNumClusters(2);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                testkhoshe.buildClusterer(test);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            for (int i = 20*count; i < test.numInstances(); i++) {
                                double pred = 0;
                                try {
                                    pred = cls2.classifyInstance(test.instance(i));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                switch ((int) pred)
                                {
                                    case (0):
                                        ///actarray2[c0][0]=(int)(test.instance(i).attribute(0).value(0));
                                        c0++;
                                        break;
                                    case (1):
                                        c1++;
                                        break;
                                    case (2):
                                        c2++;
                                        break;
                                    case (3):
                                        c3++;
                                        break;
                                    case (4):
                                        c4++;
                                        break;
                                    case (5):
                                        c5++;
                                        break;
                                    case (6):
                                        c6++;
                                        break;
                                }
                            }
                            result=result+" in "+count+" time= " ;
                            if (c0!=0)
                                result =result + data.classAttribute().value(0)+" : "+(c0)+" ";
                            if (c1!=0)
                                result = result + data.classAttribute().value(1)+" : "+(c1)+" ";
                            if (c2!=0)
                                result =result + data.classAttribute().value(2)+" : "+(c2)+" ";
                            if (c3!=0)
                                result = result + data.classAttribute().value(3)+" : "+(c3)+" ";
                            if (c4!=0)
                                result = result + data.classAttribute().value(4)+" : "+(c4)+" ";
                            if (c5!=0)
                                result = result + data.classAttribute().value(5)+" : "+(c5)+" ";
                            if (c6!=0)
                                result = result + data.classAttribute().value(6)+" : "+(c6)+" ";
                            int maxStock = Math.max( c0, Math.max( c1, Math.max( c2,  Math.max( c3,  Math.max( c4,  Math.max( c5, c6 ) ) ) ) ) );
                            if (maxStock ==c0)
                                result = result+" predict you are "+data.classAttribute().value(0);
                            if (maxStock ==c1)
                                result = result+" predict you are "+data.classAttribute().value(1);
                            if (maxStock ==c2)
                                result = result+" predict you are "+data.classAttribute().value(2);
                            if (maxStock ==c3)
                                result = result+" predict you are "+data.classAttribute().value(3);
                            if (maxStock ==c4)
                                result = result+" predict you are "+data.classAttribute().value(4);
                            if (maxStock ==c5)
                                result = result+" predict you are "+data.classAttribute().value(5);
                            if (maxStock ==c6)
                                result = result+" predict you are "+data.classAttribute().value(6);
                            uf += result+ "\n";

                        } else {
                            ress.setText("This part is under construction");
                        }
                            /*test.clear();

                            FastVector      atts = new FastVector();
                            atts.addElement(new Attribute("x")); //latitude
                            atts.addElement(new Attribute("y")); //longitude
                            atts.addElement(new Attribute("z")); //longitude
                            atts.addElement(new Attribute("cls")); //longitude

                            // 2. create Instances object
                            test = new Instances("waking"+count, atts,0);
                            ///test = new Instances("waking", atts,0);*/
                        c0=c1=c2=c3=c4=c5=c6=0;
                        ress.setText(uf);
                        uf="";
                        if (count ==10) {


                            simpleSwitch2.setChecked(false);
                            nu = 0;
                            reccheck2 = false;
                        }
                        nu=0;
                        count++;
                    }
                    nu++;

                }
                else {
                    // statusSwitch1 = simpleSwitch1.getTextOn().toString();today2 = Calendar.getInstance();
                    tosday2 = Calendar.getInstance();
                    int diff2 = Math.abs((int) (tosday.getTimeInMillis() - tosday2.getTimeInMillis()));
                    if ((diff2) >= 5000) {
                        reccheck2 = true;
                    }
                }
            }
            else {
                tosday = Calendar.getInstance();
                String ddd,ddd1;
                ddd=tosday.getTime().toString();
                tosday.add(Calendar.MILLISECOND,-5000);
                ddd1 = tosday.getTime().toString();
                if (reccheck) {
                    String z = "";
                    Boolean isSuccess = false;
                    con = connectionclass(un, pass, db, ip);        // Connect to database
                    if (con == null) {
                        z = "Check Your Internet Access!";
                    } else {
                        // Change below query according to your own database.
                        String query = "delete from userinfo where (tdate between N'"+ddd1+"' and N'"+ddd+"')and email =N'"+use+"'";
                        Statement stmt = null;
                        try {
                            stmt = con.createStatement();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        try {
                            stmt.executeQuery(query);
                        } catch (Exception ex) {
                            ex = ex;
                        }
                    }
                    z = "send data to server";
                    isSuccess = true;
                    try {
                        con.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    reccheck = false;
                }

            }
        }


    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Makedata Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.example.pegah.loginform/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }
    public void insert(float sx,float sy, float sz,float gx,float gy,float gz,String use,String em,String act,String ddd,String deviceMan, String position) {

        insertitems [insertcounter][0]= sx;
        insertitems [insertcounter][1]= sy;
        insertitems [insertcounter][2]= sz;
        insertitems [insertcounter][3]= gx;
        insertitems [insertcounter][4]= gy;
        insertitems [insertcounter][5]= gz;
        insertcounter ++;
        // this is the Asynctask, which is used to process in background to reduce load on app process

        if (insertcounter ==10 ) {
            InsertTask insertTask = new InsertTask();
            insertTask.execute(stim,insertitems[0][0],insertitems[0][1],insertitems[0][2],insertitems[0][3],insertitems[0][4],insertitems[0][5],
                                    insertitems[1][0],insertitems[1][1],insertitems[1][2],insertitems[1][3],insertitems[1][4],insertitems[0][5],
                                    insertitems[2][0],insertitems[2][1],insertitems[2][2],insertitems[2][3],insertitems[2][4],insertitems[2][5],
                                    insertitems[3][0],insertitems[3][1],insertitems[3][2],insertitems[3][3],insertitems[3][4],insertitems[3][5],
                                    insertitems[4][0],insertitems[4][1],insertitems[4][2],insertitems[4][3],insertitems[4][4],insertitems[4][5],
                                    insertitems[5][0],insertitems[5][1],insertitems[5][2],insertitems[5][3],insertitems[5][4],insertitems[5][5],
                                    insertitems[6][0],insertitems[6][1],insertitems[6][2],insertitems[6][3],insertitems[6][4],insertitems[6][5],
                                    insertitems[7][0],insertitems[7][1],insertitems[7][2],insertitems[7][3],insertitems[7][4],insertitems[7][5],
                                    insertitems[8][0],insertitems[8][1],insertitems[8][2],insertitems[8][3],insertitems[8][4],insertitems[8][5],
                                    insertitems[9][0],insertitems[9][1],insertitems[9][2],insertitems[9][3],insertitems[9][4],insertitems[9][5],
                                    use, em, act,ddd ,deviceMan,position);
            insertcounter=0;
        }


    }

    public class InsertTask extends AsyncTask<Object, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String r) {
            if (!isSuccess) {
                /// Toast.makeText(MakedataActivity.this, "Login Successfull", Toast.LENGTH_LONG).show();
                Toast.makeText(MakedataActivity.this, r, Toast.LENGTH_SHORT).show();
                //finish();
            }
        }



        public void ConnectToDatabase() {
            try {

                // SET CONNECTIONSTRING
                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                String username = "sepehrs3_amirr";
                String password = "ojr4S5%4";
                Connection DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://a-ir9.serverpars.com:1433/sepehrs3_wereh;user=" + username + ";password=" + password);

                Log.w("Connection", "open");
                Statement stmt = DbConn.createStatement();
                ResultSet reset = stmt.executeQuery("select * from users ");


                EditText num = (EditText)
                        findViewById(R.id.editText1);
                num.setText(reset.getString(1));

                DbConn.close();

            } catch (Exception e) {
                Log.w("Error connection", "" + e.getMessage());
            }
        }

        @Override

        protected String doInBackground(Object ... params) {
            float myarray[][] = new float[10][6];
            String stime = (String) params[0];
            myarray[0][0]  = (float) params[1];
            myarray[0][1]  = (float) params[2];
            myarray[0][2]  = (float) params[3];
            myarray[0][3]  = (float) params[4];
            myarray[0][4]  = (float) params[5];
            myarray[0][5]  = (float) params[6];
            myarray[1][0]  = (float) params[7];
            myarray[1][1]  = (float) params[8];
            myarray[1][2]  = (float) params[9];
            myarray[1][3]  = (float) params[10];
            myarray[1][4]  = (float) params[11];
            myarray[1][5]  = (float) params[12];
            myarray[2][0]  = (float) params[13];
            myarray[2][1]  = (float) params[14];
            myarray[2][2]  = (float) params[15];
            myarray[2][3]  = (float) params[16];
            myarray[2][4]  = (float) params[17];
            myarray[2][5]  = (float) params[18];
            myarray[3][0]  = (float) params[19];
            myarray[3][1]  = (float) params[20];
            myarray[3][2]  = (float) params[21];
            myarray[3][3]  = (float) params[22];
            myarray[3][4]  = (float) params[23];
            myarray[3][5]  = (float) params[24];
            myarray[4][0]  = (float) params[25];
            myarray[4][1]  = (float) params[26];
            myarray[4][2]  = (float) params[27];
            myarray[4][3]  = (float) params[28];
            myarray[4][4]  = (float) params[29];
            myarray[4][5]  = (float) params[30];
            myarray[5][0]  = (float) params[31];
            myarray[5][1]  = (float) params[32];
            myarray[5][2]  = (float) params[33];
            myarray[5][3]  = (float) params[34];
            myarray[5][4]  = (float) params[35];
            myarray[5][5]  = (float) params[36];
            myarray[6][0]  = (float) params[37];
            myarray[6][1]  = (float) params[38];
            myarray[6][2]  = (float) params[39];
            myarray[6][3]  = (float) params[40];
            myarray[6][4]  = (float) params[41];
            myarray[6][5]  = (float) params[42];
            myarray[7][0]  = (float) params[43];
            myarray[7][1]  = (float) params[44];
            myarray[7][2]  = (float) params[45];
            myarray[7][3]  = (float) params[46];
            myarray[7][4]  = (float) params[47];
            myarray[7][5]  = (float) params[48];
            myarray[8][0]  = (float) params[49];
            myarray[8][1]  = (float) params[50];
            myarray[8][2]  = (float) params[51];
            myarray[8][3]  = (float) params[52];
            myarray[8][4]  = (float) params[53];
            myarray[8][5]  = (float) params[54];
            myarray[9][0]  = (float) params[55];
            myarray[9][1]  = (float) params[56];
            myarray[9][2]  = (float) params[57];
            myarray[9][3]  = (float) params[58];
            myarray[9][4]  = (float) params[59];
            myarray[9][5]  = (float) params[60];
            String username = (String) params[61];
            String email = (String) params[62];
            String activity =(String) params[63];
            String idate = (String) params[64];
            String model =(String) params[65];
            String position =  (String) params[66];


            if (stime.trim().equals("") || stime.trim().equals(""))
                z = "Please enter Username and Password";
            else {
                try {
                    con = connectionclass(un, pass, db, ip);        // Connect to database
                    if (con == null) {
                        z = "Check Your Internet Access!";
                    }
                    else {

                        // String query = "";
                        // Change below query according to your own database.
                        String query = "INSERT INTO userinfo([timestamp],"  +
                                "[accx0],[accy0],[accz0],[gyrox0],[gyroy0],[gyroz0]," +
                                "[accx1],[accy1],[accz1],[gyrox1],[gyroy1],[gyroz1]," +
                                "[accx2],[accy2],[accz2],[gyrox2],[gyroy2],[gyroz2]," +
                                "[accx3],[accy3],[accz3],[gyrox3],[gyroy3],[gyroz3]," +
                                "[accx4],[accy4],[accz4],[gyrox4],[gyroy4],[gyroz4]," +
                                "[accx5],[accy5],[accz5],[gyrox5],[gyroy5],[gyroz5]," +
                                "[accx6],[accy6],[accz6],[gyrox6],[gyroy6],[gyroz6]," +
                                "[accx7],[accy7],[accz7],[gyrox7],[gyroy7],[gyroz7]," +
                                "[accx8],[accy8],[accz8],[gyrox8],[gyroy8],[gyroz8]," +
                                "[accx9],[accy9],[accz9],[gyrox9],[gyroy9],[gyroz9]," +
                                "[email],[activity],[tdate],[model],[position])VALUES(N'" + stime + "'" +
                                ",N'" +  myarray[0][0] + "',N'" + myarray[0][1] + "',N'" + myarray[0][2] + "',N'" + myarray[0][3] + "',N'" + myarray[0][4] + "',N'" + myarray[0][5] + "'" +
                                ",N'" +  myarray[1][0] + "',N'" + myarray[1][1] + "',N'" + myarray[1][2] + "',N'" + myarray[1][3] + "',N'" + myarray[1][4] + "',N'" + myarray[1][5] + "'" +
                                ",N'" +  myarray[2][0] + "',N'" + myarray[2][1] + "',N'" + myarray[2][2] + "',N'" + myarray[2][3] + "',N'" + myarray[2][4] + "',N'" + myarray[2][5] + "'" +
                                ",N'" +  myarray[3][0] + "',N'" + myarray[3][1] + "',N'" + myarray[3][2] + "',N'" + myarray[3][3] + "',N'" + myarray[3][4] + "',N'" + myarray[3][5] + "'" +
                                ",N'" +  myarray[4][0] + "',N'" + myarray[4][1] + "',N'" + myarray[4][2] + "',N'" + myarray[4][3] + "',N'" + myarray[4][4] + "',N'" + myarray[4][5] + "'" +
                                ",N'" +  myarray[5][0] + "',N'" + myarray[5][1] + "',N'" + myarray[5][2] + "',N'" + myarray[5][3] + "',N'" + myarray[5][4] + "',N'" + myarray[5][5] + "'" +
                                ",N'" +  myarray[6][0] + "',N'" + myarray[6][1] + "',N'" + myarray[6][2] + "',N'" + myarray[6][3] + "',N'" + myarray[6][4] + "',N'" + myarray[6][5] + "'" +
                                ",N'" +  myarray[7][0] + "',N'" + myarray[7][1] + "',N'" + myarray[7][2] + "',N'" + myarray[7][3] + "',N'" + myarray[7][4] + "',N'" + myarray[7][5] + "'" +
                                ",N'" +  myarray[8][0] + "',N'" + myarray[8][1] + "',N'" + myarray[8][2] + "',N'" + myarray[8][3] + "',N'" + myarray[8][4] + "',N'" + myarray[8][5] + "'" +
                                ",N'" +  myarray[9][0] + "',N'" + myarray[9][1] + "',N'" + myarray[9][2] + "',N'" + myarray[9][3] + "',N'" + myarray[9][4] + "',N'" + myarray[9][5] + "',N'" + email + "',N'" + activity + "',N'" + idate + "',N'" + model + "',N'"+position+"') ";

                        Statement stmt = con.createStatement();

                        try {
                            stmt.execute(query);
                        } catch (Exception ex) {
                            {
                                ex = ex;
                            }
                        }

                        z = "send data to server";

                        isSuccess = true;
                        con.close();

                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            // Toast.makeText(MakedataActivity.this, "z", Toast.LENGTH_SHORT).show();
            return z;
        }
    }


    @SuppressLint("NewApi")
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

    /*   public void  showrecognition(float[][] actarray) {

           TextView sharray = (TextView) findViewById(R.id.showarray);
           recognition rc1 = new recognition();
           rc1.setarray(actarray);
           float[] r1 = rc1.computedistance();

           for (int i = 0; i < 4; i++) {
               switch (((int) r1[i])) {
                   case 100:
                       sharray.append("Activity is Standing " +"\n");
                       break;
                   case 90:
                       sharray.append("activity is Sitting" + "\n");
                       break;
                   case 80:
                       sharray.append("activity is walking "+ "\n");
                       break;
               }
           }
       } */
    public void demo() throws Exception {
        // TODO Auto-generated constructor stub
        BufferedReader breader = null;
        File root = new File(Environment.getExternalStorageDirectory(), "Notes");
        String yourFilePath = root.toString() + "/bcls.arff";
        breader = new BufferedReader(new FileReader(
                yourFilePath));
        Instances Train = new Instances(breader);

        EM kMeans = new EM();

        kMeans.setNumClusters(2);
        kMeans.buildClusterer(Train);
        breader.close();
    }
}

