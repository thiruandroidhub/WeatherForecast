package thiru.test.weather.model;

import java.util.List;

public class WeatherUpdate {

    private final CurrentWeather currentWeather;
    private final List<WeatherForecast> weatherForecastList;

    public WeatherUpdate(CurrentWeather currentWeather, List<WeatherForecast> weatherForecastList) {
        this.currentWeather = currentWeather;
        this.weatherForecastList = weatherForecastList;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }

    public List<WeatherForecast> getWeatherForecastList() {
        return weatherForecastList;
    }

}
