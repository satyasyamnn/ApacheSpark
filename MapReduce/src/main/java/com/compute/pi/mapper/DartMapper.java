package com.compute.pi.mapper;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

public class DartMapper implements MapFunction<Row, Integer> {
    private Integer counter = 0;

    @Override
    public Integer call(Row row) throws Exception {
        double x = Math.random() * 2 - 1;
        double y = Math.random() * 2 - 1;
        counter++;
        if (counter % 100000 == 0) {
            System.out.println("" + counter + " darts thrown so far");
        }
        return (x * x + y * y <= 1) ? 1 : 0;
    }
}
