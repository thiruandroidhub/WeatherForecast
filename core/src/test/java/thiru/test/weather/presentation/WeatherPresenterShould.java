package thiru.test.weather.presentation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import thiru.test.weather.action.GetWeatherUpdateInteractor;
import thiru.test.weather.model.WeatherUpdate;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class WeatherPresenterShould {

    @Mock
    private WeatherUpdateView weatherUpdateView;

    @Mock
    private GetWeatherUpdateInteractor getWeatherUpdateInteractor;

    @Mock
    private GetWeatherUpdateInteractor.GetWeatherUpdateCallback getWeatherUpdateCallback;

    @Mock
    private Throwable error;

    @Mock
    private WeatherUpdate weatherUpdate;

    private WeatherPresenter weatherPresenter;

    @Before
    public void setUp() throws Exception {
        // given
        weatherPresenter = new WeatherPresenter(getWeatherUpdateInteractor);
    }

    @Test
    public void should_init_vew_on_attach() {
        // when
        weatherPresenter.attachView(weatherUpdateView);

        // then
        verify(weatherUpdateView).initView();
    }

    @Test
    public void should_get_weather_update() {
        // when
        weatherPresenter.onGetWeatherUpdate();

        // then
        verify(getWeatherUpdateInteractor).getWeatherUpdate(weatherPresenter);
    }

    @Test
    public void should_show_weather_update_on_success() {
        // when
        weatherPresenter.attachView(weatherUpdateView);
        weatherPresenter.onWeatherUpdateSuccess(weatherUpdate);

        // then
        verify(weatherUpdateView).showWeatherUpdate(weatherUpdate);
    }

    @Test
    public void should_show_error_on_weather_update_failure() {
        // when
        weatherPresenter.attachView(weatherUpdateView);
        weatherPresenter.onWeatherUpdateFailed(error);

        // then
        verify(weatherUpdateView).showWeatherUpdateError(error);
    }
}
