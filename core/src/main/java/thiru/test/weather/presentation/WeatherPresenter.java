package thiru.test.weather.presentation;

import thiru.test.weather.action.GetWeatherUpdateInteractor;
import thiru.test.weather.model.WeatherUpdate;

public class WeatherPresenter implements GetWeatherUpdateInteractor.GetWeatherUpdateCallback {

    private final GetWeatherUpdateInteractor getWeatherUpdateInteractor;
    private WeatherUpdateView emptyWeatherUpdateView = new EmptyWeatherUpdateView();
    private WeatherUpdateView weatherUpdateView;

    public WeatherPresenter(
            GetWeatherUpdateInteractor getWeatherUpdateInteractor) {
        this.getWeatherUpdateInteractor = getWeatherUpdateInteractor;
        weatherUpdateView = emptyWeatherUpdateView;
    }

    public void attachView(WeatherUpdateView weatherUpdateView) {
        this.weatherUpdateView = weatherUpdateView;
        this.weatherUpdateView.initView();
    }

    public void onGetWeatherUpdate() {
        getWeatherUpdateInteractor.getWeatherUpdate(this);
    }

    @Override
    public void onWeatherUpdateSuccess(WeatherUpdate weatherUpdate) {
        weatherUpdateView.showWeatherUpdate(weatherUpdate);
    }

    @Override
    public void onWeatherUpdateFailed(Throwable error) {
        weatherUpdateView.showWeatherUpdateError(error);
    }
}
