package edu.puj.talktome;

import android.app.Application;

import edu.puj.talktome.dependencies.components.ApplicationComponent;
import edu.puj.talktome.dependencies.components.DaggerApplicationComponent;
import edu.puj.talktome.dependencies.modules.GeoInfoModule;
import edu.puj.talktome.dependencies.modules.GeocoderModule;
import edu.puj.talktome.dependencies.modules.LocationModule;
import lombok.Getter;

@Getter
public class App extends Application {
    ApplicationComponent appComponent = DaggerApplicationComponent.builder()
            .locationModule(new LocationModule(this))
            .geocoderModule(new GeocoderModule(this))
            .geoInfoModule(new GeoInfoModule(this))
            .build();
}
