package com.example.pegah.finalfordataset;



import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    Connection con;
    String un,pass,pass2,db,ip;
    ProgressBar progressBar;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;
     Typeface tf1,tf2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ButterKnife.inject(this);
        //---------

        //-------

        ip = "pamsdataset.ir";
        db = "temsiran_activity";
        un = "temsiran_ehsang";
        pass2 = "ehsan@93";

        tf1 = Typeface.createFromAsset(getAssets(),"ALGER.TTF");
        _loginButton.setTypeface(tf1);

        tf2 = Typeface.createFromAsset(getAssets(),"BuxtonSketch.ttf");
        _signupLink.setTypeface(tf2);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String user = _emailText.getText().toString();
                String pass = _passwordText.getText().toString();
                try
                {
                    con = connectionclass(un, pass2, db, ip);        // Connect to database
                    if (con == null)
                    {

                        Toast.makeText(getBaseContext(), "Check your internet access! " , Toast.LENGTH_LONG).show();

                    }
                    else
                    {

// Change below query according to your own database.
                        String result = "";
                    String query = "SELECT * FROM [usersick] WHERE (([email] = N'"+user+"') AND ([pass] = N'"+pass+"')) ";

                       // String query = "ALTER TABLE userinfo ALTER COLUMN timestamp nvarchar(30); ALTER TABLE userinfo ALTER COLUMN accx nvarchar(30); ALTER TABLE userinfo ALTER COLUMN accy nvarchar(30); ALTER TABLE userinfo ALTER COLUMN accz nvarchar(30); ALTER TABLE userinfo ALTER COLUMN gyrox nvarchar(30); ALTER TABLE userinfo ALTER COLUMN gyroy nvarchar(30); ALTER TABLE userinfo ALTER COLUMN gyroz nvarchar(30); ";
                     //     String query= " INSERT INTO doctor([drname],[druser],[drpass])VALUES(N'pegah',N'pegah.esf',N'pegah1371')";
                      //  String query = "SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS  WHERE  TABLE_NAME = N'userinfo'    ";

                         Statement stmt = con.createStatement();
                     // stmt.execute(query);
                       ResultSet rs = stmt.executeQuery(query);

                      if (rs.next())
                        {
                          // result +=   rs.getString(1) + " " ;

                           con.close();
                            _loginButton.setEnabled(false);

                            final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                            progressDialog.setIndeterminate(true);
                            progressDialog.setMessage("Authenticating...");
                            progressDialog.show();

                            String email = _emailText.getText().toString();
                            String password = _passwordText.getText().toString();

                            // TODO: Implement your own authentication logic here.

                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            // On complete call either onLoginSuccess or onLoginFailed
                                            onLoginSuccess();
                                            // onLoginFailed();
                                            progressDialog.dismiss();
                                        }
                                    }, 3000);
                      }
                       else
                        {
                            Toast.makeText(getBaseContext(), "Login failed Username or Password is incorrect", Toast.LENGTH_LONG).show();

                            _loginButton.setEnabled(true);
                        }
                    }
                }
                catch (Exception ex)
                {
                    Log.w("Error connection","" + ex.getMessage());


                }
                //login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                Intent myIntent = new Intent(MainActivity.this, SensorActivity.class);

                MainActivity.this.startActivity(myIntent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        Intent intent = new Intent(MainActivity.this, SensorActivity.class);
        intent.putExtra("name",  _emailText.getText().toString());
        intent.putExtra("pass",  _passwordText.getText().toString());
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }



    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r)
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess)
            {
                Toast.makeText(MainActivity.this , "Login Successfull" , Toast.LENGTH_LONG).show();
                //finish();
            }
        }

        public void ConnectToDatabase(){
            try {

                // SET CONNECTIONSTRING
                Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                String username = "temsiran_ehsang";
                String password = "ehsan@93";
                Connection DbConn = DriverManager.getConnection("jdbc:jtds:sqlserver://a-ir9.serverpars.com:1433/sepehrs3_wereh;user=" + username + ";password=" + password);

                Log.w("Connection","open");
                Statement stmt = DbConn.createStatement();
                ResultSet reset = stmt.executeQuery("select * from users ");


                EditText num = (EditText)
                        findViewById(R.id.input_email);
                num.setText(reset.getString(1));

                DbConn.close();

            } catch (Exception e)
            {
                Log.w("Error connection","" + e.getMessage());
            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            String username = params[0];
            String password = params[1];

            if(username.trim().equals("")|| password.trim().equals(""))
                z = "Please enter Username and Password";
            else
            {
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
                        String query = "SELECT * FROM [usersick] WHERE (([email] = N'"+username+"') AND ([pass] = N'"+password+"')) ";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next())
                        {
                            z = "Login successful";
                            isSuccess=true;
                            con.close();
                        }
                        else
                        {
                            z = "Invalid Credentials!";
                            isSuccess = false;
                        }
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }


    @SuppressLint("NewApi")
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            Intent intent = new Intent(MainActivity.this,Help2.class);
            startActivity(intent);
        }
        if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this,About2.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
