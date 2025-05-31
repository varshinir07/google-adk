package com.adk.googleadkmcp.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
