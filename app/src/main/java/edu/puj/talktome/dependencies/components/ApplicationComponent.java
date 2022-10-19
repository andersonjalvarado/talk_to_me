package edu.puj.talktome.dependencies.components;

import javax.inject.Singleton;

import dagger.Component;
import edu.puj.talktome.activities.BasicActivity;
import edu.puj.talktome.activities.FragmentMap;
import edu.puj.talktome.dependencies.modules.AlertsModule;
import edu.puj.talktome.dependencies.modules.GeoInfoModule;
import edu.puj.talktome.dependencies.modules.GeocoderModule;
import edu.puj.talktome.dependencies.modules.LocationModule;
import edu.puj.talktome.dependencies.modules.PermissionModule;

@Singleton
@Component(modules = {AlertsModule.class, PermissionModule.class,
        GeoInfoModule.class, GeocoderModule.class, LocationModule.class})
public interface ApplicationComponent {
    void inject(BasicActivity activity);
    void inject(FragmentMap fragmentMap);
}
