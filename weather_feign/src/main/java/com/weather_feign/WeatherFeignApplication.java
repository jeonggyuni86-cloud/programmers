package com.weather_feign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WeatherFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherFeignApplication.class, args);
    }

}
