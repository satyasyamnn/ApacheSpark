package com.org.sparklearning.restaurantsProcessor;

import com.org.Util;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.concat;
import static org.apache.spark.sql.functions.lit;

public class RestaurantsProcessor {

    private static final String INPUT_PATH = "data/inputs/restaurants/Restaurants_in_Wake_County_NC.csv";
    private static final String INPUT_PATH_JSON = "data/inputs/restaurants/Restaurants_in_Durham_County_NC.json";

    private SparkSession session;

    public RestaurantsProcessor(SparkSession session) {
        this.session = session;
    }

    public Dataset<Row> processRestaurantsData() {
        Dataset<Row> data = getRestaurantsDateFromJson();
        System.out.println(data.count());
        data.printSchema();
        data.show(5);
        return data;
        //return getRestaurantsDataFromCsvFile();
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
        Dataset<Row> restaurants = session.read().format("json").load(INPUT_PATH_JSON);
        return restaurants;
    }

}
