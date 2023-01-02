package pl.mw.restapi.webclient.weather;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.mw.restapi.model.WeatherApiDto;
import pl.mw.restapi.webclient.weather.dto.OpenWeatherWeatherDto;

// API: https://openweathermap.org/api/one-call-3
// VIDEO: https://www.youtube.com/watch?v=DPFYyjyeuVA
// dodac mozna cashowanie

@Component
public class WeatherClient {

    private static final String WEATHER_URL="http://api.openweather.org/data/2.5/";
    private static final String API_KEY="";
    private RestTemplate restTemplate = new RestTemplate();         // klient http RestTemplate

    //pogoda bierząca dla miasta
    public WeatherApiDto getWeatherForCity(String city){
        OpenWeatherWeatherDto openWeatherWeatherDto= callGetMethod("weather?q={city}&appid={apiKey}&units=metrics&lang=pl",
                OpenWeatherWeatherDto.class, // String.class bylo
                city, API_KEY);

        return WeatherApiDto.builder()
                .temperature(openWeatherWeatherDto.getMain().getTemp())
                .pressure(openWeatherWeatherDto.getMain().getPressure())
                .humidity(openWeatherWeatherDto.getMain().getHumidity())
                .windSpeed(openWeatherWeatherDto.getWind().getSpeed())
                .build();
    }

    // prognozy kilkudniowe dla współrzędnych
    public String getForecats(Double lat, Double lon){
        return callGetMethod("onecall?lat={lat}&lon={lon}&exclude=minutelly,hourly&appid={apiKey}&units=metrics&lang=pl",
                String.class,
                lat, lon, API_KEY);
    }

    // metoda obslugowa
    private <T> T callGetMethod(String url,Class<T> responseType, Object...objects){
        return restTemplate.getForObject(WEATHER_URL+url,
                responseType, objects);
    }
}
