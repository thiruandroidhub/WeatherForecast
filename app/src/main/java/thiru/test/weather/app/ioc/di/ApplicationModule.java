package thiru.test.weather.app.ioc.di;

import android.content.Context;
import android.location.LocationManager;

import dagger.Module;
import dagger.Provides;
import thiru.test.weather.action.GetWeatherUpdateInteractor;
import thiru.test.weather.app.services.WeatherUpdateLocalCacheProvider;
import thiru.test.weather.app.services.WeatherUpdateNetworkProvider;
import thiru.test.weather.presentation.WeatherPresenter;

@Module
public class ApplicationModule {

    private final Context context;

    public ApplicationModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    protected Context provideApplicationContext() {
        return context;
    }

    @Provides
    @ApplicationScope
    protected WeatherPresenter proideWeatherPresenter(GetWeatherUpdateInteractor getWeatherUpdateInteractor) {
        return new WeatherPresenter(getWeatherUpdateInteractor);
    }

    @Provides
    @ApplicationScope
    protected GetWeatherUpdateInteractor provideGetWeatherUpdateInteractor(WeatherUpdateLocalCacheProvider weatherUpdateLocalCacheProvider) {
        return new GetWeatherUpdateInteractor(weatherUpdateLocalCacheProvider);
    }

    @Provides
    @ApplicationScope
    protected WeatherUpdateLocalCacheProvider provideWeatherUpdateLocalCacheProvider(WeatherUpdateNetworkProvider weatherUpdateNetworkProvider) {
        return new WeatherUpdateLocalCacheProvider(weatherUpdateNetworkProvider);
    }

    @Provides
    @ApplicationScope
    protected LocationManager provideLocationManager() {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }
}
