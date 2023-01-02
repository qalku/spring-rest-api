package pl.mw.restapi.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeatherApiDto {
    private float temperature;
    private int pressure;
    private int humidity;
    private float windSpeed;
}
