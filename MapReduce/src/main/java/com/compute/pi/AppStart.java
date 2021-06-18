package com.compute.pi;

import com.compute.pi.runners.DartRunner;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SparkSession;

public class AppStart {
    public static void  main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Dart Calculator").setMaster("local[*]");
        SparkContext context = new SparkContext(conf);
        SparkSession session =SparkSession.builder().sparkContext(context).getOrCreate();
        DartRunner runner = new DartRunner(session);
        runner.run(6);
    }
}
