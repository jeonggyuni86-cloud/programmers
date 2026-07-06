package com.weather_feign.service;

import com.weather_feign.client.WeatherClient;
import com.weather_feign.dto.Header;
import com.weather_feign.dto.Item;
import com.weather_feign.dto.WeatherResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherService {
    private final WeatherClient weatherClient;

    @Value("${weather.api.key}")
    private String serviceKey;
    public List<Item> getCurrentWeather(int nx, int ny) {
        LocalDateTime now = LocalDateTime.now();
        if(now.getMinute() < 40) {
            now = now.minusHours(1);
        }

        String baseDate = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String baseTime = now.format(DateTimeFormatter.ofPattern("HH")) + "00";

        WeatherResponse response =
                weatherClient.getUltraSrtNcst(
                        serviceKey, 10, 1,
                        "JSON", baseDate, baseTime, nx, ny);
        Header header = response.response().header();
        if(!"00".equals(header.resultCode())) {
            throw new RuntimeException("기상청 API 오류: " + header.resultCode());
        }

        return response.response().body().items().items();

    }
}
