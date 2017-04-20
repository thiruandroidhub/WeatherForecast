package thiru.test.weather;

import thiru.test.weather.action.GetWeatherUpdateInteractor;

public interface WeatherUpdateProvider {
    void getWeatherUpdate(GetWeatherUpdateInteractor.GetWeatherUpdateCallback getWeatherUpdateCallback);
}
