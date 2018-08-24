package com.example.pegah.finalfordataset;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;
/**
 * Created by pegah on 10/5/2017.
 */
import java.util.ArrayList;
import java.util.Random;

import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
public class loadmodel {


    /**
     * Created by Pegah on 1/30/2017.
     */

    public static void main(String[] args) throws Exception {

        MakeDensityBasedClusterer cls = new MakeDensityBasedClusterer();

        double[] instanceValue1 = new double[3];
        instanceValue1[0] = Math.floor((double) 29 * 100) / 100;
        instanceValue1[1] = Math.floor((double) 40 * 100) / 100;
        instanceValue1[2] = Math.floor((double) 11 * 100) / 100;
      /*  instanceValue1[0][0] = Math.floor((double) 10 * 100) / 100;
        instanceValue1[1][1] = Math.floor((double) 9 * 100) / 100;
        instanceValue1[2][1] = Math.floor((double) 5 * 100) / 100; */

        double[] instanceValue2 = new double[3];
        instanceValue2[0] = Math.floor((double) 5 * 100) / 100;
        instanceValue2[1] = Math.floor((double) 4 * 100) / 100;
        instanceValue2[2] = Math.floor((double) 10 * 100) / 100;
        double[] instanceValue3 = new double[3];
        instanceValue3[0] = Math.floor((double) 6 * 100) / 100;
        instanceValue3[1] = Math.floor((double) 5 * 100) / 100;
        instanceValue3[2] = Math.floor((double) 12 * 100) / 100;
        double[] instanceValue4 = new double[3];
        instanceValue4[0] = Math.floor((double) 30 * 100) / 100;
        instanceValue4[1] = Math.floor((double) 40 * 100) / 100;
        instanceValue4[2] = Math.floor((double) 12 * 100) / 100;

        DenseInstance denseInstance1 = new DenseInstance(1.0, instanceValue1);
        DenseInstance denseInstance2 = new DenseInstance(1.0, instanceValue2);
        DenseInstance denseInstance3 = new DenseInstance(1.0, instanceValue3);
        DenseInstance denseInstance4 = new DenseInstance(1.0, instanceValue4);
        ArrayList<Attribute> atts = new ArrayList<>();
        atts.add(new Attribute("x"));
        atts.add(new Attribute("y"));
        atts.add(new Attribute("z"));
        Instances test = new Instances("walking", atts, 0);


        test.add(0, denseInstance1);
        test.add(1, denseInstance2);
        test.add(2, denseInstance3);
        test.add(3, denseInstance4);
      /*  ClusterEvaluation evaluation = new ClusterEvaluation();
        evaluation.setClusterer(clusterAnalysis);
        evaluation.evaluateClusterer(test);
        System.out.println(evaluation.clusterResultsToString());
         */

        try {
            //  cls.buildClusterer(test);
            // System.out.println(cls.toString());
            //----------------------------------------------------------------------------------------------------
            //   test.setClassIndex(test.numAttributes() - 1);
            SimpleKMeans kMeans = new SimpleKMeans();
            kMeans.setSeed(10);
            kMeans.setPreserveInstancesOrder(true);
            //dClusterer(test)
            int[] assignments = kMeans.getAssignments();
            String[] cluster0 = new String[4];
            String[] cluster1 = new String[4];
            kMeans.setNumClusters(2);
            //  kMeans.build;

            int i = 0, j = 0, k = 0;
            for (int clusterNum : assignments) {
                if (assignments[i] == 0) {
                    cluster0[j] = test.instance(i).toString();
                    j++;
                } else if (clusterNum == 1) {
                    cluster1[k] = test.instance(i).toString();
                    k++;
                }
                System.out.printf("Instance %d -> Cluster %d", i, clusterNum);
                i++;
            }

            System.out.printf("\n________________________________________\n");
            for (j = 0; j < 2; j++) {

                System.out.printf("cluster0 is : " + cluster0[j] + "\n");

            }
            System.out.printf("\n________________________________________\n");
            for (j = 0; j < 2; j++) {

                System.out.printf("cluster1 is : " + cluster1[j] + "\n");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        //-----------------------------------------------------------------------------------------------


        BufferedReader breader = null;
        File root = new File(Environment.getExternalStorageDirectory(), "Notes");
        String yourFilePath = root.toString() + "/evaluation.arff";
        breader = new BufferedReader(new FileReader(
                yourFilePath));
        Instances data = new Instances(breader);
           //  System.out.println(eval.toSummaryString("\n Results \n=====\n",true));
           // System.out.println(eval.fMeasure(1)+" "+eval.precision(1)+" "+eval.recall(1)+" ");
           //  BufferedReader reader = new BufferedReader(new FileReader(root,"evaluation.arff"));
           //  Instances data = new Instances(reader);

        // setting class attribute
        data.setClassIndex(data.numAttributes() - 1);
        Classifier clsnBayes = (Classifier) weka.core.SerializationHelper.read("/some/where/nBayes.model");

// Test the model
           Evaluation eTest = new Evaluation(test);
           eTest.evaluateModel(clsnBayes, test);


    }

}



