package com.example.pegah.finalfordataset;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Help2 extends AppCompatActivity implements View.OnClickListener {

    TextView help;
    Typeface tf2;
    TextView tv1,tv2,scr1,scr2;
    TableLayout t1;
    TableRow tr;
    Button  bgo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        bgo= (Button) findViewById(R.id.bgo);
        bgo.setOnClickListener(this);
       // tablerow1 = (TableRow) findViewById(R.id.tablerow1);

     //   help = (TextView)  findViewById(R.id.help);
     //   tf2 = Typeface.createFromAsset(getAssets(),"ARLRDBD.TTF");
     //   help.setTypeface(tf2);
     //========================================================================================

    //  tv1 = (TextView) findViewById(R.id.tvsource1);
     // tv2= (TextView) findViewById(R.id.tvsource2);


        //tablerow1 = (TableRow) findViewById(R.id.tablerow1);


                }


    @Override
    public void onClick(View v) {
        t1 = (TableLayout) findViewById(R.id.t1);
        t1.setColumnStretchable(0,true);
        t1.setColumnStretchable(1,true);
        if(v.getId()== R.id.bgo)
        {

            tr = new TableRow(this);
            scr1= new TextView(this);
            scr1.setText("predicted activity:");
            scr1.setTextSize(15);
            scr1.setTextColor(getResources().getColor(R.color.cardview_light_background));
            scr1.setGravity(Gravity.CENTER);
            scr1.setBackgroundResource(R.drawable.sitting);
            scr1.setPadding(1,5,1,5);
            scr2= new TextView(this);
            scr2.setText("Sitting");
            scr2.setTextColor(getResources().getColor(R.color.cardview_light_background));
            scr2.setTextSize(15);
            scr2.setGravity(Gravity.CENTER);
            scr2.setBackgroundResource(R.drawable.sitting);
            scr2.setPadding(1,5,1,5);
            tr.addView(scr1);
            tr.addView(scr2);
            tr.setPadding(1,3,1,3);
            t1.addView(tr);


        }


    }
}