package edu.puj.talktome.dependencies.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.puj.talktome.services.LocationService;
import lombok.AllArgsConstructor;

@Module
@AllArgsConstructor
public class LocationModule {
    private final Application application;

    @Provides
    @Singleton
    Application providesApplication(){
        return application;
    }

    @Provides
    public LocationService provideLocationService() {
        return new LocationService(application.getApplicationContext());
    }
}
