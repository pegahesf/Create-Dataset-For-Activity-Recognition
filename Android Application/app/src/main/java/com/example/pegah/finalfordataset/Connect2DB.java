package com.example.pegah.finalfordataset;

import android.content.Context;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class Connect2DB extends AppCompatActivity {

  //------------------------------------*************************************************
  Connection con;
    String un,pass,pass2,db,ip;
    private TextView acc1;
    private TextView mail;
    String namemail;
    String didi;
    Button crtable , deltable;
    Typeface tf1;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conect2db);
        mail = (TextView) findViewById(R.id.usernam);
        Bundle bundle = getIntent().getExtras();
        namemail = bundle.getString("name1");
        acc1 = (TextView) findViewById(R.id.acc1);
        // mail.setText(name);
         crtable = (Button)   findViewById(R.id.createtable);
         deltable = (Button)   findViewById(R.id.deletetable);
        tf1 = Typeface.createFromAsset(getAssets(),"ALGER.TTF");
        crtable.setTypeface(tf1);
        deltable.setTypeface(tf1);

        ip = "pamsdataset.ir";
        db = " temsiran_activity";
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
                String query = " select COLUMN_NAME  from INFORMATION_SCHEMA.COLUMNS  where TABLE_NAME = 'userinfo' ";
               // String query = "select * from userinfo" ;
                //   String query = "SELECT activity,COUNT(activity) from [userinfo] where email='pegahesfehsni@yahoo.com' Group By activity";
                // String query= "UPDATE usersick SET [sickname] = 80   WHERE email='majid.mus@yahoo.com';";

                Statement stmt = con.createStatement();
                // stmt.execute(query);
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {

                  result += "*"  + rs.getString(1);

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
        crtable.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //--------------------------------------------------------------------------------------------

              //  builder.setTitle("Delete sensory data!!!!").setMessage("Are you sure you want to delete your sensory data?!").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        //    public void onClick(DialogInterface dialog, int which) {
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

                                      /*  String query =  "ALTER TABLE userinfo ADD email nvarchar(100);\n" +
                                                        " ALTER TABLE userinfo ADD activity nvarchar(50);\n" +
                                                        " ALTER TABLE userinfo ADD tdate nvarchar(100);\n" +
                                                        " ALTER TABLE userinfo ADD model nvarchar(100);\n" +
                                                        " ALTER TABLE userinfo ADD position nvarchar(50);\n" ; */

                                        String query = "delete from userinfo ;";
                                                Statement stmt = con.createStatement();
                                        stmt.execute(query);
                                        Toast.makeText(Connect2DB.this, "delete table successfully", Toast.LENGTH_LONG).show();

                                    }
                                }
                                catch (Exception ex)
                                {
                                    acc1.setText("Error connection:"+ ex.getMessage());
                                }






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

    //----------------------------------*************************************************

}
