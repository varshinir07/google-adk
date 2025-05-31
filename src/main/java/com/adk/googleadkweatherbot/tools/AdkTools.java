package com.adk.googleadkweatherbot.tools;

import com.adk.googleadkweatherbot.shared.Properties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.adk.tools.Annotations.Schema;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdkTools {

    private final Properties properties;

    private static String weatherApiKey;

    @PostConstruct
    public void init() {
        weatherApiKey = properties.getWeatherApi();
    }


    public static Map<String,String> getWeather(
            @Schema(description = "The name of the city for which to retrieve the weather report")
            String city) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.weatherapi.com/v1/current.json?key=" + weatherApiKey + "&q=" + city))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode rootNode = mapper.readTree(response.body());
            JsonNode currentNode = rootNode.get("current");

            return Map.of("city", city, "currentWeatherInCelcius", String.valueOf(currentNode.get("temp_c").asDouble()));
        } catch (Exception exception){
            log.error(exception.getMessage());
            return Map.of("error", "Can't able to fetch temperature");
        }
    }
}
