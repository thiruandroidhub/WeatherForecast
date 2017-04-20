package thiru.test.weather.action;

import thiru.test.weather.WeatherUpdateProvider;
import thiru.test.weather.model.WeatherUpdate;

public class GetWeatherUpdateInteractor {

    private final WeatherUpdateProvider weatherUpdateProvider;

    public GetWeatherUpdateInteractor(WeatherUpdateProvider weatherUpdateProvider) {
        this.weatherUpdateProvider = weatherUpdateProvider;
    }

    public void getWeatherUpdate(GetWeatherUpdateCallback getWeatherUpdateCallback) {
        weatherUpdateProvider.getWeatherUpdate(getWeatherUpdateCallback);
    }

    public interface GetWeatherUpdateCallback {
        void onWeatherUpdateSuccess(WeatherUpdate weatherUpdate);

        void onWeatherUpdateFailed(Throwable error);
    }
}
