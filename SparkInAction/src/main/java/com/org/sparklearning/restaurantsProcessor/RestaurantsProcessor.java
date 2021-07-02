package com.org.sparklearning.restaurantsProcessor;

import com.org.Util;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.concat;
import static org.apache.spark.sql.functions.lit;
import static org.apache.spark.sql.functions.split;

public class RestaurantsProcessor {

    private static final String INPUT_PATH = "data/inputs/restaurants/Restaurants_in_Wake_County_NC.csv";
    private static final String INPUT_PATH_JSON = "data/inputs/restaurants/Restaurants_in_Durham_County_NC.json";
    private static final String OUPUT_PATH_LOCATION ="data/outputs/restaurants";

    private SparkSession session;

    public RestaurantsProcessor(SparkSession session) {
        this.session = session;
    }

    public void processRestaurantsData() {
        Dataset<Row> data1 = getRestaurantsDataFromCsvFile();
        Dataset<Row> data2 = getRestaurantsDateFromJson();
        Dataset<Row> unionData =  data1.unionByName(data2);
        unionData.write().mode(SaveMode.Append).csv(OUPUT_PATH_LOCATION);
        unionData.write().mode(SaveMode.Append).json(OUPUT_PATH_LOCATION);
        unionData.write().mode(SaveMode.Append).parquet(OUPUT_PATH_LOCATION);
    }

    private Dataset<Row> getRestaurantsDataFromCsvFile() {
        Dataset<Row> restaurants = session.read().format("csv").options(Util.getOptions()).load(INPUT_PATH);
        restaurants = restaurants.withColumn("country", lit("wake"))
                .withColumnRenamed("HSISID", "datasetId")
                .withColumnRenamed("NAME", "name")
                .withColumnRenamed("ADDRESS1", "address1")
                .withColumnRenamed("ADDRESS2", "address2")
                .withColumnRenamed("CITY", "city")
                .withColumnRenamed("STATE", "state")
                .withColumnRenamed("POSTALCODE", "zip")
                .withColumnRenamed("PHONENUMBER", "tel")
                .withColumnRenamed("RESTAURANTOPENDATE", "dateStart")
                .withColumnRenamed("FACILITYTYPE", "type")
                .withColumnRenamed("X", "geoX")
                .withColumnRenamed("Y", "geoY")
                .drop("OBJECTID")
                .drop("PERMITID")
                .drop("GEOCODESTATUS");

        restaurants = restaurants.withColumn("id", concat(restaurants.col("state"), lit("_"),
                restaurants.col("country"), lit("_"),
                restaurants.col("datasetId")));

        return restaurants;
    }

    private Dataset<Row> getRestaurantsDateFromJson() {
        Dataset<Row> df = session.read().format("json").load(INPUT_PATH_JSON);
        df.printSchema();
        df = df.withColumn("country", lit("Durham"))
                .withColumn("datasetId", df.col("fields.id"))
                .withColumn("name", df.col("fields.premise_name"))
                .withColumn("address1", df.col("fields.premise_address1"))
                .withColumn("address2", df.col("fields.premise_address2"))
                .withColumn("city", df.col("fields.premise_city"))
                .withColumn("state", df.col("fields.premise_state"))
                .withColumn("zip", df.col("fields.premise_zip"))
                .withColumn("tel", df.col("fields.premise_phone"))
                .withColumn("dateStart", df.col("fields.opening_date"))
                .withColumn("type", split(df.col("fields.type_description"), " - ").getItem(1))
                .withColumn("geoX", df.col("fields.geolocation").getItem(0))
                .withColumn("geoY", df.col("fields.geolocation").getItem(1))
                .drop("fields")
                .drop("geometry")
                .drop("record_timestamp")
                .drop("recordid");

        df = df.withColumn("id", concat(df.col("state"), lit("_"),
                df.col("country"), lit("_"),
                df.col("datasetId")));

        return df;
    }
}
