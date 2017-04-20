package thiru.test.weather.app.presentation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import thiru.test.weather.app.R;
import thiru.test.weather.app.helpers.DayFormatter;
import thiru.test.weather.app.helpers.TemperatureFormatter;
import thiru.test.weather.model.WeatherForecast;

public class WeatherForecastViewHolder extends RecyclerView.ViewHolder {

    private final Context context;

    @BindView(R.id.day)
    TextView dayTextView;

    @BindView(R.id.description)
    TextView descriptionTextView;

    @BindView(R.id.hint)
    TextView hintTextView;

    @BindView(R.id.maximum_temperature)
    TextView maximumTemperatureTextView;

    @BindView(R.id.minimum_temperature)
    TextView minimumTemperatureTextView;

    public WeatherForecastViewHolder(View view, Context context) {
        super(view);
        this.context = context;
        ButterKnife.bind(this, view);
    }

    public void render(WeatherForecast weatherForecast) {
        final DayFormatter dayFormatter = new DayFormatter(context);
        final String day = dayFormatter.format(weatherForecast.getTimestamp());
        dayTextView.setText(day);
        descriptionTextView.setText(weatherForecast.getDescription());
        hintTextView.setText(weatherForecast.getHint());
        maximumTemperatureTextView.setText(TemperatureFormatter.format(weatherForecast.getMaximumTemperature()));
        minimumTemperatureTextView.setText(TemperatureFormatter.format(weatherForecast.getMinimumTemperature()));
    }
}
