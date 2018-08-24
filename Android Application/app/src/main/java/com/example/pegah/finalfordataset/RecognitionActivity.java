package com.example.pegah.finalfordataset;


        import android.annotation.SuppressLint;

        import android.content.res.AssetManager;
        import android.graphics.Typeface;
        import android.hardware.Sensor;
        import android.hardware.SensorEvent;
        import android.hardware.SensorEventListener;
        import android.hardware.SensorManager;
        import android.net.Uri;

        import android.os.Bundle;
        import android.os.Environment;

        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.View;

        import android.widget.Button;

        import android.widget.ImageView;
        import android.widget.ScrollView;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.load.engine.DiskCacheStrategy;
        import com.google.android.gms.appindexing.Action;
        import com.google.android.gms.appindexing.AppIndex;
        import com.google.android.gms.common.api.GoogleApiClient;

        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.FileReader;
        import java.io.IOException;

        import java.util.ArrayList;
        import java.util.Calendar;

        import weka.classifiers.Classifier;
        import weka.classifiers.trees.J48;
        import weka.core.Attribute;
        import weka.core.Instances;
       

public class RecognitionActivity extends AppCompatActivity implements SensorEventListener {


    String name, passw;
    //the Sensor Manager
    private SensorManager sManager;
    private SensorManager sensorManager;

    TextView scr1,scr2;
    TableLayout t1;
    TableRow tr;
    ImageView imageView;

    Typeface tf1;
    Instances test;

    Calendar tosday2 = Calendar.getInstance();
    Calendar tosday = Calendar.getInstance();

    private Classifier mClassifier = null;
    private GoogleApiClient client;
    double[] instanceValue1 = new double[3];
    BufferedReader breader3 = null;

    int nu=0;
    int flag=0;
    ScrollView sv;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {


        ArrayList<Attribute> atts = new ArrayList<Attribute>(3);

        atts.add(new Attribute("x"));
        atts.add(new Attribute("y"));
        atts.add(new Attribute("z"));
        atts.add(new Attribute("class"));


        tf1 = Typeface.createFromAsset(getAssets(),"ARLRDBD.TTF");


        // 2. create Instances object
        test = new Instances("pegah", atts,0);


        Bundle bundle = getIntent().getExtras();

        name = bundle.getString("name1");
        passw = bundle.getString("pass1");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.onlinerecognition);
        //result = (TextView) findViewById(R.id.result);

       // pb = (Button)     findViewById(R.id.predictbutton)    ;


        //get a hook to the sensor service
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
         sv = (ScrollView)findViewById(R.id.scrl);


       // imageView = (ImageView) findViewById(R.id.imggif);

       // Glide.with(RecognitionActivity.this).load("https://goo.gl/nqXjnw").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
//--------------------------------------------------------------------------------------------------
      /*  lb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Log.d(WEKA_TEST, "onClickButtonLoadModel()");

                AssetManager assetManager = getAssets();
                try {
                    mClassifier = (Classifier) weka.core.SerializationHelper.read(assetManager.open("j48.model"));

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    // Weka "catch'em all!"
                    e.printStackTrace();
                }
                Toast.makeText(RecognitionActivity.this, "Model Loaded", Toast.LENGTH_LONG).show();


            }
        });
                 */
//--------------------------------------------------------------------------------------------------
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

        //if sensor is unreliable, return void
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            return;
        }


        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {


            //----------------------------------------------------------------------------
           try {
                if(flag ==0 )
                {
                    loadmodel();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //------------------------------------------------------------------------------
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            String yourFilePath3= root.toString() + "/test.arff";
            try {
                breader3 = new BufferedReader(new FileReader(yourFilePath3));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //------------------------------------------------------------------------------
           //
            tosday2 = Calendar.getInstance();
            int diff2 = Math.abs((int) (tosday.getTimeInMillis() - tosday2.getTimeInMillis()));
            if ((diff2) >= 1000) {
                tosday = Calendar.getInstance();

                try {
                    Instances pegaharff = new Instances(breader3);
                    pegaharff.setClassIndex(pegaharff.numAttributes() - 1);
                    instanceValue1[0] = Math.floor((double) event.values[0] * 100) / 100;
                    instanceValue1[1] = Math.floor((double) event.values[1] * 100) / 100;
                    instanceValue1[2] = Math.floor((double) event.values[2] * 100) / 100;
                    pegaharff.instance(0).setValue(0, instanceValue1[0]);
                    pegaharff.instance(0).setValue(1, instanceValue1[1]);
                    pegaharff.instance(0).setValue(2, instanceValue1[2]);

                    double pred = mClassifier.classifyInstance(pegaharff.instance(0));
                    String pegah = "actual: " + pegaharff.classAttribute().value((int) pegaharff.instance(0).classValue());
                    String pegah2 = pegaharff.classAttribute().value((int) pred);
                    //----------------------------------------------------------------------------------------------------
                    t1 = (TableLayout) findViewById(R.id.t1);
                    t1.setColumnStretchable(0, true);
                    t1.setColumnStretchable(1, true);
                    tr = new TableRow(this);


                    scr1 = new TextView(this);
                    scr1.setText("predicted activity:");
                    scr1.setTextSize(15);
                    scr1.setTextColor(getResources().getColor(R.color.cardview_light_background));
                    scr1.setGravity(Gravity.CENTER);
                     scr1.setPadding(1, 5, 1, 5);
                    scr2 = new TextView(this);
                    scr2.setText(pegah2);
                    scr2.setTextColor(getResources().getColor(R.color.cardview_light_background));
                    scr2.setTextSize(15);
                    scr2.setGravity(Gravity.CENTER);
                     scr2.setPadding(1, 5, 1, 5);
                    tr.addView(scr1);
                    tr.addView(scr2);
                    tr.setPadding(1, 3, 1, 3);
                    t1.addView(tr);
                    scr1.setTypeface(tf1);
                    scr2.setTypeface(tf1);

                    switch (pegah2){

                        case "Sitting" :
                            scr2.setBackgroundResource(R.drawable.sitting);
                            scr1.setBackgroundResource(R.drawable.sitting);
                            break;
                        case "Standing":
                            scr2.setBackgroundResource(R.drawable.standing);
                            scr1.setBackgroundResource(R.drawable.standing);
                            break;
                        case "Walking":
                            scr2.setBackgroundResource(R.drawable.walking);
                            scr1.setBackgroundResource(R.drawable.walking);
                            break;
                        case "using.mobile":
                            scr2.setBackgroundResource(R.drawable.usingmobile);
                            scr1.setBackgroundResource(R.drawable.usingmobile);
                            break;
                        case "Running":
                            scr2.setBackgroundResource(R.drawable.running);
                            scr1.setBackgroundResource(R.drawable.running);
                            break;
                        case "Upstairs":  case "Downstairs":
                            scr2.setBackgroundResource(R.drawable.stairs);
                            scr1.setBackgroundResource(R.drawable.stairs);
                            break;


                    }
                    sv.scrollTo(0, sv.getBottom());
                    //result.append("Your predicted activity : " + pegah2 + "\n");

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
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

    @SuppressLint("NewApi")


    public void loadmodel() throws Exception {
        // TODO Auto-generated constructor stub


        AssetManager assetManager = getAssets();
        try {
            mClassifier = (Classifier) weka.core.SerializationHelper.read(assetManager.open("j48.model"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // Weka "catch'em all!"
            e.printStackTrace();
        }

        flag=1;
           }

}

