package thiru.test.weather.app.services;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Implement an Rx-style location service by wrapping the Android LocationManager and providing
 * the location result as an Observable.
 */
public class LocationService {

    private final LocationManager locationManager;
    private final Context context;

    @Inject
    public LocationService(LocationManager locationManager, Context context) {
        this.locationManager = locationManager;
        this.context = context;
    }

    public Observable<Location> getLocation() {
        return Observable.create(new Observable.OnSubscribe<Location>() {
            @Override
            public void call(final Subscriber<? super Location> subscriber) {

                final LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(final Location location) {
                        subscriber.onNext(location);
                        subscriber.onCompleted();

                        Looper.myLooper().quit();
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };

                final Criteria locationCriteria = new Criteria();
                locationCriteria.setAccuracy(Criteria.ACCURACY_COARSE);
                locationCriteria.setPowerRequirement(Criteria.POWER_LOW);
                final String locationProvider = locationManager
                        .getBestProvider(locationCriteria, true);

                Looper.prepare();

                try {
                    locationManager.requestSingleUpdate(locationProvider, locationListener, Looper.myLooper());
                } catch (SecurityException e) {
                    Toast.makeText(context, "First enable LOCATION ACCESS in settings.", Toast.LENGTH_LONG).show();
                }

                Looper.loop();
            }
        });
    }
}