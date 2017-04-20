package thiru.test.weather.app.services;

import javax.inject.Inject;

import thiru.test.weather.WeatherUpdateProvider;
import thiru.test.weather.action.GetWeatherUpdateInteractor;
import thiru.test.weather.app.ioc.di.ApplicationScope;
import thiru.test.weather.model.WeatherUpdate;

@ApplicationScope
public class WeatherUpdateLocalCacheProvider implements WeatherUpdateProvider, GetWeatherUpdateInteractor.GetWeatherUpdateCallback {

    private final WeatherUpdateNetworkProvider weatherUpdateNetworkProvider;
    private GetWeatherUpdateInteractor.GetWeatherUpdateCallback getWeatherUpdateCallback;
    private WeatherUpdate weatherUpdate;

    @Inject
    public WeatherUpdateLocalCacheProvider(WeatherUpdateNetworkProvider weatherUpdateNetworkProvider) {
        this.weatherUpdateNetworkProvider = weatherUpdateNetworkProvider;
    }

    @Override
    public void getWeatherUpdate(GetWeatherUpdateInteractor.GetWeatherUpdateCallback getWeatherUpdateCallback) {
        this.getWeatherUpdateCallback = getWeatherUpdateCallback;
        if (weatherUpdate != null) {
            this.getWeatherUpdateCallback.onWeatherUpdateSuccess(weatherUpdate);
        } else {
            weatherUpdateNetworkProvider.getWeatherUpdate(this);
        }
    }

    @Override
    public void onWeatherUpdateSuccess(WeatherUpdate weatherUpdate) {
        this.weatherUpdate = weatherUpdate;
        this.getWeatherUpdateCallback.onWeatherUpdateSuccess(weatherUpdate);
    }

    @Override
    public void onWeatherUpdateFailed(Throwable error) {
        this.getWeatherUpdateCallback.onWeatherUpdateFailed(error);
    }
}
