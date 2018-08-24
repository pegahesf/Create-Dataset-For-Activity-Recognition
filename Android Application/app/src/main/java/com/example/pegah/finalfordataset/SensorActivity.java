package com.example.pegah.finalfordataset;

import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SensorActivity extends AppCompatActivity {
   String name,pass;
    TextView uuu;
    TextView ppp;
    ImageView imageView;
    Connection con;
    String un,passw,db,ip;
    private TextView err;
    final Context context = this;
    Typeface  tf1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosemenu);
         uuu = (TextView) findViewById(R.id.usern);
         ppp = (TextView) findViewById(R.id.passw);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        pass = bundle.getString("pass");
        ip = "pamsdataset.ir";
        db = " temsiran_activity";
        un = "temsiran_ehsang";
        pass = "ehsan@93";
        String z = "";

        int rslt=0;
        uuu.setText(name);
        ppp.setText(pass);

        Button   dataset = (Button)   findViewById(R.id.dataset);
        Button   activityrec = (Button)   findViewById(R.id.activityrec);
        Button   onlinerec = (Button) findViewById(R.id.onlinerec);

         tf1 = Typeface.createFromAsset(getAssets(),"ALGER.TTF");
        dataset.setTypeface(tf1);
        activityrec.setTypeface(tf1);
        onlinerec.setTypeface(tf1);

        File root = android.os.Environment.getExternalStorageDirectory();
        root.getAbsolutePath();
        onlinerec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uuu = (TextView) findViewById(R.id.usern);
                ppp = (TextView) findViewById(R.id.passw);
                Intent mintent = new Intent(SensorActivity.this, RecognitionActivity.class);
                mintent.putExtra("name1", name);
                mintent.putExtra("pass1", pass);
                startActivity(mintent);
                //    imageView = (ImageView) findViewById(R.id.gif);

                //  Glide.with(SensorActivity.this).load("https://goo.gl/nqXjnw").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
            }
        });


           imageView = (ImageView) findViewById(R.id.gif);

          Glide.with(SensorActivity.this).load("https://goo.gl/nqXjnw").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);

        //----------------------------------------------------------------------------------------------

        try
        {
            con = connectionclass(un, passw, db, ip);        // Connect to database
            if (con == null)
            {
                z = "Check Your Internet Access!";
            }
            else
            {

// Change below query according to your own database.
                String result = "";
                String query = "SELECT MAX(did) from [device] where  email=N'" + name +"' ";
                //   String query = "SELECT activity,COUNT(activity) from [userinfo] where email='pegahesfehsni@yahoo.com' Group By activity";
                //  String query= "Delete from userinfo";

                Statement stmt = con.createStatement();
                //stmt.execute(query);
                ResultSet rs = stmt.executeQuery(query);
                String m = " ";
                if (rs.next()) {
                    m = rs.getString(1);

                    if (rs.getString(1) == null) {
                        rslt = 1;
                    }
                }
                    else
                    {
                        //--------

                        String query2 = "SELECT model from [device] where   did=N'"+ m +"' and  email=N'" + name +"' ";

                        Statement stmt2 = con.createStatement();
                         ResultSet rs2 = stmt2.executeQuery(query2);


                        if (rs2.next()) {

                            if (rs.getString(1) != android.os.Build.MODEL) {
                                rslt = 1;

                            }
                        }

                    }


                con.close();

            }
        }
        catch (Exception ex)
        {
            err.setText("Error connection:" + ex.getMessage());
        }
        //-----------------------------------------------------------------------------------------------

    if(rslt==1) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("You login with new device")
                .setMessage("it seems you change your device please insert your new device info")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        Intent intent=new Intent(getApplicationContext(),deviceinfo.class);

                        intent.putExtra("name1", name);
                        intent.putExtra("pass1", pass);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

}
        //-----------------------------------------------------------------------------------------------
        dataset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uuu = (TextView) findViewById(R.id.usern);
                ppp = (TextView) findViewById(R.id.passw);
                Intent mintent = new Intent(SensorActivity.this, MakedataActivity.class);
                mintent.putExtra("name1", name);
                mintent.putExtra("pass1", pass);
                startActivity(mintent);
            //    imageView = (ImageView) findViewById(R.id.gif);

              //  Glide.with(SensorActivity.this).load("https://goo.gl/nqXjnw").diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
            }
        });

        activityrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent=new Intent(getApplicationContext(),RecognitionActivity.class);
                Intent intent=new Intent(getApplicationContext(),Connect2DB.class);

                intent.putExtra("name1", name);
                intent.putExtra("pass1", pass);
                startActivity(intent);


            }
        });

   /*     deviceinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),deviceinfo.class);

                intent.putExtra("name1", name);
                intent.putExtra("pass1", pass);
                startActivity(intent);


            }
        });*/

        activityrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent intent=new Intent(getApplicationContext(),RecognitionActivity.class);
                Intent intent=new Intent(getApplicationContext(),Connect2DB.class);
                intent.putExtra("name1", name);
                intent.putExtra("pass1", pass);
                startActivity(intent);


            }
        });


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