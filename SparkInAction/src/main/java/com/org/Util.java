package com.org;

import java.util.HashMap;
import java.util.Map;

public class Util {

    public static Map<String, String> getOptions() {
        Map<String, String> options = new HashMap<>();
        options.put("header", "true");
        options.put("inferSchema", "true");
        return options;
    }
}
