package com.compute.pi.runners;

import com.compute.pi.mapper.DartMapper;
import com.compute.pi.reducer.DartReducer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.ArrayList;
import java.util.List;

public class DartRunner{

    private final SparkSession sparkSession;

    public DartRunner(SparkSession sparkSession)
    {
        this.sparkSession = sparkSession;
    }

    public void run(Integer slices) {
        Dataset<Integer> darts = getNumberOfThrows(6);
        System.out.println(darts.reduce(new DartReducer()));
    }

    private Dataset<Integer> getNumberOfThrows(Integer slices) {
        List<Integer> numbers = getIntegers(slices);
        Dataset<Row> incrementalDf = sparkSession.createDataset(numbers, Encoders.INT()).toDF();
        Dataset<Integer> dartsDs = incrementalDf.map(new DartMapper(), Encoders.INT());
        return dartsDs;
    }

    private List<Integer> getIntegers(Integer slices) {
        int numberOfThrows = 100000 * slices;
        List<Integer> l = new ArrayList<>(numberOfThrows);
        for (int i = 0; i < numberOfThrows; i++) {
            l.add(i);
        }
        return l;
    }
}
