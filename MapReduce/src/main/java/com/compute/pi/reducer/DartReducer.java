package com.compute.pi.reducer;

import org.apache.spark.api.java.function.ReduceFunction;

public class DartReducer implements ReduceFunction<Integer> {
    @Override
    public Integer call(Integer x, Integer y) throws Exception {
        return x + y;
    }
}
