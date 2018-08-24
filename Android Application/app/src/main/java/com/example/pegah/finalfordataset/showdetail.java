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
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;


public class showdetail extends AppCompatActivity {
    Connection con;
    String un,pass,db,ip;
    private TextView acc1;

    String namemail;
    String didi;
    final Context context = this;
    Typeface tf1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdetail);

//        Bundle bundle = getIntent().getExtras();
//        namemail = bundle.getString("name1");
        acc1 = (TextView) findViewById(R.id.acc1);
       // mail.setText(name);
        Button   del = (Button)   findViewById(R.id.delet);
        tf1 = Typeface.createFromAsset(getAssets(),"ALGER.TTF");
        del.setTypeface(tf1);

        ip = "pamsdataset.ir";
        db = "temsiran_activity";
        un = "temsiran_ehsang";
        pass = "ehsan@93";
        Calendar mydate;
        mydate = Calendar.getInstance();


        didi=mydate.getTime().toString();
        didi = didi.substring(0, Math.min(didi.length(), 10));
        acc1.setText(didi);

        String z = "";

             // Connect to database
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
               //String query = "SELECT activity,COUNT(activity) from [userinfo] where email=N'" + namemail + "' Group By activity";
               String query = "SELECT * from userinfo";
             // String query= "UPDATE usersick SET [sickname] = 80   WHERE email='majid.mus@yahoo.com';";

                Statement stmt = con.createStatement();
              // stmt.execute(query);
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    result +=  "   * Your activity : " + rs.getString(1) + " ---> " ;
                    result +=   rs.getString(2) + "\n"  ;
                    result +=  "   ____________________________________     " + "\n"   + "\n";
                }
                z = result;
                acc1.setText(z);
                con.close();

            }
        }
        catch (Exception ex)
        {
          acc1.setText("Error connection:"+ ex.getMessage());
        }
        //-----------------------------------------------------------------------------------------------------------------------
        del.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //--------------------------------------------------------------------------------------------
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(context);
                }
                builder.setTitle("Delete sensory data!!!!")
                        .setMessage("Are you sure you want to delete your sensory data?!")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete

                                String z = "";
                                try
                                {
                                    con = connectionclass(un, pass, db, ip);        // Connect to database
                                    if (con == null)
                                    {
                                        z = "Check Your Internet Access!";
                                    }
                                    else {

// Change below query according to your own database.
//
                                      // String query = "Delete  FROM  [userinfo] where email=N'" + namemail + " ' and  tdate LIKE '"+didi+"%' ";
                                      String query = "Delete  FROM  [userinfo] " ;
                                                 //where email=N'" + namemail + " ' and [activity] = N'walking' ";
                                       // String query= " DELETE FROM userinfo";
                                        Statement stmt = con.createStatement();
                                        stmt.execute(query);
                                        Toast.makeText(showdetail.this, "Delete information  "+ namemail + " " + didi +" successfully", Toast.LENGTH_LONG).show();

                                    }
                                }
                                catch (Exception ex)
                                {
                                    acc1.setText("Error connection:"+ ex.getMessage());
                                }


                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();



                //------------------------------------------------------------------------------------------------


            }
        });


    }

    public Connection connectionclass(String user, String password, String database, String server)
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + server +":1433/"+ database + ";user=" + user+ ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }

}
