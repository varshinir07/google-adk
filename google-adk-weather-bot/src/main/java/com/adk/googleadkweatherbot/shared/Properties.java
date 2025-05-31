package com.adk.googleadkweatherbot.shared;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@AllArgsConstructor
@NoArgsConstructor
public class Properties {

    @Value("${weather.api.key}")
    private String weatherApi;
}
