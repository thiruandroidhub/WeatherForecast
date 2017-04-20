package thiru.test.weather.model;

import java.util.HashMap;
import java.util.Map;

public class WeatherForecast {
    private final String mLocationName;
    private final long mTimestamp;
    private final String mDescription;
    private final float mMinimumTemperature;
    private final float mMaximumTemperature;

    private static final Map<String, String> hints = new HashMap<String, String>() {
        {put("rain", "take umbrella");}
        {put("thunderstorm ", "take umbrella");}
        {put("drizzle", "take umbrella");}
        {put("snow", "take jacket");}
        {put("clear", "take sunglasses");}
    };

    public WeatherForecast(final String locationName,
                           final long timestamp,
                           final String description,
                           final float minimumTemperature,
                           final float maximumTemperature) {

        mLocationName = locationName;
        mTimestamp = timestamp;
        mMinimumTemperature = minimumTemperature;
        mMaximumTemperature = maximumTemperature;
        mDescription = description;
    }

    public String getLocationName() {
        return mLocationName;
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getHint() {
        for (String hintKey : hints.keySet()) {
            if (mDescription.contains(hintKey)) {
                return hints.get(hintKey);
            }
        }
        return "";
    }

    public float getMinimumTemperature() {
        return mMinimumTemperature;
    }

    public float getMaximumTemperature() {
        return mMaximumTemperature;
    }
}