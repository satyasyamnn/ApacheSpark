package com.sf.fire.analysis.readers;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.*;
import org.apache.spark.sql.api.java.UDF1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireDataReader {

    private SparkSession session;

    public FireDataReader(SparkSession session) {
        this.session = session;
        defineUDF();
    }

    public Dataset<Row> loadData() {
        Dataset<Row> data = session.read().options(getOptions()).format("csv").schema(defineSchema()).load("data/inputs/sf-fire-calls.csv");
        data = data.withColumn("Year", functions.callUDF("getYear", data.col("CallDate")))
                   .withColumn("month", functions.callUDF("getMonth", data.col("CallDate")));
        return data;
    }

    private  void defineUDF() {
        session.udf().register("getYear", (UDF1<String, Integer>) (input) -> {
            String year = input.split("/")[2];
            return Integer.parseInt(year);
        }, DataTypes.IntegerType);

        session.udf().register("getMonth", (UDF1<String, Integer>)(input) -> {
            String month = input.split("/")[1];
            return Integer.parseInt(month);
        }, DataTypes.IntegerType);
    }

    private Map<String, String> getOptions() {
        Map<String, String> options = new HashMap<>();
        options.put("header", "true");
        return options;
    }

    private StructType defineSchema() {
        List<StructField> fieldsForSchema = new ArrayList<>();
        fieldsForSchema.add(DataTypes.createStructField("CallNumber", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("UnitID", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("IncidentNumber", DataTypes.IntegerType, true));
        fieldsForSchema.add(DataTypes.createStructField("CallType", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("CallDate", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("WatchDate", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("CallFinalDisposition", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("AvailableDtTm", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("Address", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("City", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("Zipcode", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("Battalion", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("StationArea", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("Box", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("OriginalPriority", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("Priority", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("FinalPriority", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("ALSUnit", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("CallTypeGroup", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("NumAlarms", DataTypes.IntegerType, true));
        fieldsForSchema.add(DataTypes.createStructField("UnitType", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("UnitSequenceInCallDispatch", DataTypes.IntegerType, true));
        fieldsForSchema.add(DataTypes.createStructField("FirePreventionDistrict", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("SupervisorDistrict", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("Neighborhood", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("Location", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("RowID", DataTypes.StringType, true));
        fieldsForSchema.add(DataTypes.createStructField("Delay", DataTypes.StringType, true));
        StructType structType = DataTypes.createStructType(fieldsForSchema);
        return structType;
    }


}
