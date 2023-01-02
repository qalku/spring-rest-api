package pl.mw.restapi.service;

/*
Zewnetrzne darmowe API: https://github.com/public-apis/public-apis

 */

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.mw.restapi.model.WeatherApiDto;
import pl.mw.restapi.webclient.weather.WeatherClient;
import pl.mw.restapi.webclient.weather.dto.OpenWeatherWeatherDto;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExternalApiService {

    private final WeatherClient weatherClient;

    public WeatherApiDto getExternalApi() {
        return weatherClient.getWeatherForCity("warszawa");
    }
}
