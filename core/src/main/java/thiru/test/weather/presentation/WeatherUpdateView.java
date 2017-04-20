package thiru.test.weather.presentation;

import thiru.test.weather.model.WeatherUpdate;

public interface WeatherUpdateView {
    void showWeatherUpdate(WeatherUpdate weatherUpdate);

    void initView();

    void showWeatherUpdateError(Throwable error);
}
