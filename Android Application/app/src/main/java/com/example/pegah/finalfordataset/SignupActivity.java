package com.example.pegah.finalfordataset;


        import android.annotation.SuppressLint;
        import android.app.ProgressDialog;
        import android.graphics.Typeface;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.os.StrictMode;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.sql.Connection;
        import java.sql.DriverManager;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;

        import butterknife.ButterKnife;
        import butterknife.InjectView;

public class SignupActivity extends AppCompatActivity {
    // Declaring connection variables
    Connection con;
    String un, pass2, db, ip;
    private static final String TAG = "SignupActivity";
    Typeface tf1;
    Typeface tf2;
    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_age) EditText _AgeText;
    @InjectView(R.id.input_Gender) EditText _GenderText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);

        ip = "pamsdataset.ir";
        db = " temsiran_activity";
        un = "temsiran_ehsang";
        pass2 = "ehsan@93";


        tf1 = Typeface.createFromAsset(getAssets(),"ALGER.TTF");
        _signupButton.setTypeface(tf1);

        tf1 = Typeface.createFromAsset(getAssets(),"BuxtonSketch.ttf");
        _loginLink.setTypeface(tf2);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String age = _AgeText.getText().toString();
        String gender = _GenderText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        SignupActivity.CheckLogin checkLogin = new SignupActivity.CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
        checkLogin.execute(name, email, "drpegah", gender,age ,password);
        progressDialog.hide();
        /*String data = name+ "\n" + age+ "\n" + gender+ "\n"+ email+ "\n"+ password + "\n" ;
        // TODO: Implement your own signup logic here.

        try {
            String h = DateFormat.format("MM-dd-yyyyy-h-mmssaa", System.currentTimeMillis()).toString();
            // this will create a new name everytime and unique
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            // if external memory exists and folder with name Notes
            if (!root.exists()) {
                root.mkdirs(); // this will create folder.
                           }
            File filepath = new File(root, h + ".txt");  // file path to save
            FileWriter writer = new FileWriter(filepath);
            writer.append(data);
            writer.flush();
            writer.close();


        } catch (IOException e) {
            e.printStackTrace();

        }*/






        /*new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);*/
    }


    public class CheckLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String r) {
            Toast.makeText(SignupActivity.this, r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                Toast.makeText(SignupActivity.this, "signup success", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected String doInBackground(String... params) {
            String sickname = params[0];
            String email = params[1];
            String drname = params[2];
            String sex = params[3];
            String age = params[4];
            String pass = params[5];

            if (sickname.trim().equals("") || sickname.trim().equals(""))
                z = "Please enter Username and Password";
            else {
                try {
                    con = connectionclass(un, pass2, db, ip);        // Connect to database
                    if (con == null) {
                        z = "Check Your Internet Access!";
                    } else {
                        // Change below query according to your own database.
                        java.sql.ResultSet sd;
                        String query = "INSERT INTO usersick([sickname],[email],[drname],[sex],[age],[pass]) VALUES (N'" + sickname + "',N'" + email + "',N'" + drname + "',N'" + sex + "',N'" + age + "',N'" + pass + "') ";
                        Statement stmt = con.createStatement();
                        try {
                            stmt.execute(query);
                          //  Toast.makeText(getBaseContext(), "sign up success fully", Toast.LENGTH_LONG).show();

                        }
                        catch (Exception ex){
                            Log.w("Error connection","" + ex.getMessage());
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
            return z;
        }
    }


    @SuppressLint("NewApi")
    public Connection connectionclass(String user, String password2, String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + server + ":1433/" + database + ";user=" + user + ";password=" + password2 + ";";
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

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String weight = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String gender = _GenderText.getText().toString();
        String age = _AgeText.getText().toString();

        if (weight.isEmpty()) {
            _nameText.setError("Weight field is empty");
            valid = false;
        } else {
            _nameText.setError(null);
        }

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
        if (gender.isEmpty()) {
            _GenderText.setError("gender field is empty");
            valid = false;
        } else {
            _GenderText.setError(null);
        }
        if (age.isEmpty()) {
            _AgeText.setError("Age field is empty");
            valid = false;
        } else {
            _AgeText.setError(null);
        }



        return valid;
    }

}
