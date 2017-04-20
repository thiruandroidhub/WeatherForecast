package thiru.test.weather.app.presentation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeoutException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.RetrofitError;
import thiru.test.weather.app.R;
import thiru.test.weather.app.application.WeatherApplication;
import thiru.test.weather.app.helpers.TemperatureFormatter;
import thiru.test.weather.model.WeatherUpdate;
import thiru.test.weather.presentation.WeatherPresenter;
import thiru.test.weather.presentation.WeatherUpdateView;

/**
 * Weather Activity.
 * <p/>
 * This is the main activity for our app.
 */
public class WeatherActivity extends AppCompatActivity implements WeatherUpdateView {

    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 12;

    @BindView(R.id.weather_forecast_list)
    RecyclerView forecastRecyclerView;
    @BindView(R.id.location_name)
    TextView locationNameTextView;
    @BindView(R.id.current_temperature)
    TextView currentTemperatureTextView;
    @BindView(R.id.swipe_refresh_container)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    WeatherPresenter weatherPresenter;

    private WeatherForecastListAdapter weatherForecastListAdapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final WeatherApplication weatherApplication = (WeatherApplication) getApplication();
        weatherApplication.getApplicationComponent().inject(this);

        setContentView(R.layout.activity_weather);

        initializeButterKnife();
        weatherPresenter.attachView(this);
    }

    @Override
    public void initView() {
        initializeAdapter();
        initializeRecyclerView();
        initializeRefreshView();
        updateWeather();
    }

    @Override
    public void showWeatherUpdate(WeatherUpdate weatherUpdate) {
        updateRefreshingView(false);
        locationNameTextView.setText(weatherUpdate.getCurrentWeather().getLocationName());
        currentTemperatureTextView.setText(
                TemperatureFormatter.format(weatherUpdate.getCurrentWeather().getTemperature()));

        weatherForecastListAdapter.clear();
        weatherForecastListAdapter.addAll(weatherUpdate.getWeatherForecastList());
        weatherForecastListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showWeatherUpdateError(Throwable error) {
        updateRefreshingView(false);
        if (error instanceof TimeoutException) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_location_unavailable), Toast.LENGTH_SHORT).show();
        } else if (error instanceof RetrofitError) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_fetch_weather), Toast.LENGTH_SHORT).show();
        } else {
            error.printStackTrace();
            throw new RuntimeException("See inner exception");
        }
    }

    private void initializeButterKnife() {
        ButterKnife.bind(this);
    }

    private void initializeRefreshView() {
        swipeRefreshLayout.setColorSchemeResources(R.color.brand_main,
                android.R.color.black,
                R.color.brand_main,
                android.R.color.black);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateWeather();
            }
        });
    }

    private void initializeAdapter() {
        weatherForecastListAdapter = new WeatherForecastListAdapter(getApplicationContext());
    }

    private void initializeRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        forecastRecyclerView.setLayoutManager(linearLayoutManager);
        forecastRecyclerView.setHasFixedSize(true);
        forecastRecyclerView.setAdapter(weatherForecastListAdapter);
    }

    /**
     * Get weather data for the current location and update the UI.
     */
    private void updateWeather() {
        updateRefreshingView(true);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            weatherPresenter.onGetWeatherUpdate();

        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSION_ACCESS_COARSE_LOCATION);
            }
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSION_ACCESS_FINE_LOCATION);
            }
        }
    }

    private void updateRefreshingView(boolean isUpdate) {
        swipeRefreshLayout.setRefreshing(isUpdate);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    weatherPresenter.onGetWeatherUpdate();
                } else {
                    // permission denied
                    Toast.makeText(this, "First enable LOCATION ACCESS in settings.", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case MY_PERMISSION_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    weatherPresenter.onGetWeatherUpdate();
                } else {
                    // permission denied
                    Toast.makeText(this, "First enable LOCATION ACCESS in settings.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }
}
