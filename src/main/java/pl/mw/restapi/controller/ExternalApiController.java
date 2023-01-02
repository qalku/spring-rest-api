package pl.mw.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mw.restapi.model.WeatherApiDto;
import pl.mw.restapi.service.ExternalApiService;

@RestController
@RequiredArgsConstructor
public class ExternalApiController {
    private final ExternalApiService externalApiService;

    @GetMapping("/externalapi")
    public WeatherApiDto getExternalApi(){
        return externalApiService.getExternalApi();
    }
}
