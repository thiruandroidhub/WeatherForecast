package thiru.test.weather.presentation;

import thiru.test.weather.model.WeatherUpdate;

public class EmptyWeatherUpdateView implements WeatherUpdateView {
    @Override
    public void showWeatherUpdate(WeatherUpdate weatherUpdate) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void showWeatherUpdateError(Throwable error) {

    }
}
