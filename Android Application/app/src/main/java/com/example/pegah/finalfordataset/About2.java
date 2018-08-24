package com.example.pegah.finalfordataset;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

//import com.example.pegah.loginform.R;

public class About2 extends AppCompatActivity {

     TextView tv1;
      Typeface tf2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        tv1 = (TextView) findViewById(R.id.about);
        tf2 = Typeface.createFromAsset(getAssets(),"BuxtonSketch.ttf");
        tv1.setTypeface(tf2);
        try {
            demo2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//------------

    public void demo2() throws Exception {
        // TODO Auto-generated constructor stub
        BufferedReader breader1 = null;
        BufferedReader breader2 = null;
        File root = new File(Environment.getExternalStorageDirectory(), "Download");
        String yourFilePath1 = root.toString() + "/finalestdataset.arff";

        breader1 = new BufferedReader(new FileReader(yourFilePath1));
        Instances data = new Instances(breader1);
        String yourFilePath2= root.toString() + "/test.arff";

        breader2 = new BufferedReader(new FileReader(yourFilePath2));
        Instances test = new Instances(breader2);

        //------------------------------------------------------------------------------------------------------------------------
        J48 cls = new J48();
       // Instances data = new Instances(new BufferedReader(new FileReader("C:\\Users\\Hesam\\Documents\\NetBeansProjects\\wekaproj\\train10.arff")));
       // Instances test = new Instances(new BufferedReader(new FileReader("C:\\Users\\Hesam\\Documents\\NetBeansProjects\\wekaproj\\test10.arff")));
        data.setClassIndex(data.numAttributes() - 1);
        test.setClassIndex(data.numAttributes() - 1);
        cls.buildClassifier(data);
        int TP=0;
        int FP=0;
        int TN=0;
        int FN=0;
       test.instance(0).setValue(0,0);
        test.instance(0).setValue(1,0.1);
        test.instance(0).setValue(2,9.8);

        // 14600 samples

            double pred = cls.classifyInstance(test.instance(0));
            String pegah = "actual: " + test.classAttribute().value((int) test.instance(0).classValue());
             String pegi =   " predicted: " + test.classAttribute().value((int) pred);
            System.out.println("TN:" + TN);




    }
}

