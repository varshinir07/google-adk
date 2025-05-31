package com.adk.googleadktools.tools;

import com.google.adk.tools.Annotations.Schema;

import java.util.Map;

public class AdkTools {

    public static Map<String,String> getCurrentTime(
            @Schema(description = "The name of the city for which to retrieve the current time")
            String city) {

        String currentTime = "12:00 PM";

        return Map.of("city", city, "currentTime", currentTime);
    }
}
