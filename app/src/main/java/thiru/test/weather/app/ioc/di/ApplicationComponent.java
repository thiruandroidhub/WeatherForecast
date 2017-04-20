package thiru.test.weather.app.ioc.di;

import dagger.Component;
import thiru.test.weather.app.presentation.WeatherActivity;

@ApplicationScope
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(WeatherActivity weatherActivity);
}
