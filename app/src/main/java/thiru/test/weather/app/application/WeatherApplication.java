package thiru.test.weather.app.application;

import android.app.Application;

import thiru.test.weather.app.ioc.di.ApplicationComponent;
import thiru.test.weather.app.ioc.di.ApplicationModule;
import thiru.test.weather.app.ioc.di.DaggerApplicationComponent;

public class WeatherApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = createApplicationComponent();
    }

    public ApplicationComponent createApplicationComponent() {
        return DaggerApplicationComponent.builder().applicationModule(
                new ApplicationModule(getApplicationContext())).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
