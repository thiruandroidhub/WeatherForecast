package thiru.test.weather.app.services;


import android.content.Context;
import android.location.Location;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import thiru.test.weather.WeatherUpdateProvider;
import thiru.test.weather.action.GetWeatherUpdateInteractor;
import thiru.test.weather.app.ioc.di.ApplicationScope;
import thiru.test.weather.model.CurrentWeather;
import thiru.test.weather.model.WeatherForecast;
import thiru.test.weather.model.WeatherUpdate;

@ApplicationScope
public class WeatherUpdateNetworkProvider implements WeatherUpdateProvider {

    private static final String KEY_CURRENT_WEATHER = "key_current_weather";
    private static final String KEY_WEATHER_FORECASTS = "key_weather_forecasts";
    private static final long LOCATION_TIMEOUT_SECONDS = 20;
    private static final String TAG = "TAG";

    private final LocationService locationService;
    private final WeatherService weatherService;

    @Inject
    public WeatherUpdateNetworkProvider(
            LocationService locationService,
            WeatherService weatherService) {
        this.locationService = locationService;
        this.weatherService = weatherService;
    }

    @Override
    public void getWeatherUpdate(final GetWeatherUpdateInteractor.GetWeatherUpdateCallback getWeatherUpdateCallback) {
        createWeatherUpdateObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HashMap<String, WeatherForecast>>() {
                    @Override
                    public void onNext(final HashMap<String, WeatherForecast> weatherData) {
                        // Update UI with current weather.
                        final CurrentWeather currentWeather = (CurrentWeather) weatherData
                                .get(KEY_CURRENT_WEATHER);

                        // Update weather forecast list.
                        final List<WeatherForecast> weatherForecasts = (List<WeatherForecast>)
                                weatherData.get(KEY_WEATHER_FORECASTS);

                        WeatherUpdate weatherUpdate = new WeatherUpdate(currentWeather, weatherForecasts);
                        getWeatherUpdateCallback.onWeatherUpdateSuccess(weatherUpdate);
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(final Throwable error) {
                        getWeatherUpdateCallback.onWeatherUpdateFailed(error);
                    }
                });
    }

    private Observable createWeatherUpdateObservable() {
        return locationService.getLocation()
                .timeout(LOCATION_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .flatMap(new Func1<Location, Observable<HashMap<String, WeatherForecast>>>() {
                    @Override
                    public Observable<HashMap<String, WeatherForecast>> call(final Location location) {
                        final double longitude = location.getLongitude();
                        final double latitude = location.getLatitude();

                        return Observable.zip(
                                // Fetch current and 5 day forecasts for the location.
                                weatherService.fetchCurrentWeather(longitude, latitude),
                                weatherService.fetchWeatherForecasts(longitude, latitude),

                                // Only handle the fetched results when both sets are available.
                                new Func2<CurrentWeather, List<WeatherForecast>,
                                        HashMap<String, WeatherForecast>>() {
                                    @Override
                                    public HashMap call(final CurrentWeather currentWeather,
                                                        final List<WeatherForecast> weatherForecasts) {

                                        HashMap weatherData = new HashMap();
                                        weatherData.put(KEY_CURRENT_WEATHER, currentWeather);
                                        weatherData.put(KEY_WEATHER_FORECASTS, weatherForecasts);
                                        return weatherData;
                                    }
                                }
                        );
                    }
                });
    }
}
