package thiru.test.weather.app.presentation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import thiru.test.weather.app.R;
import thiru.test.weather.model.WeatherForecast;

/**
 * Provides items for our list view.
 */
public class WeatherForecastListAdapter extends RecyclerView.Adapter<WeatherForecastViewHolder> {

    private final Context context;
    private List<WeatherForecast> weatherForecastList;

    public WeatherForecastListAdapter(final Context context) {
        this.context = context;
        this.weatherForecastList = new ArrayList<>();
    }

    public void addAll(Collection<WeatherForecast> collection) {
        weatherForecastList.addAll(collection);
    }

    @Override
    public WeatherForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.weather_forecast_list_item, parent, false);

        return new WeatherForecastViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(WeatherForecastViewHolder holder, int position) {
        WeatherForecast weatherForecast = weatherForecastList.get(position);
        holder.render(weatherForecast);
    }

    @Override
    public int getItemCount() {
        return weatherForecastList.size();
    }

    public void clear() {
        weatherForecastList.clear();
    }
}
