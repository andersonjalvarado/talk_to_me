package edu.puj.talktome.dependencies.components;

import javax.inject.Singleton;

import dagger.Component;
import edu.puj.talktome.activities.BasicActivity;
import edu.puj.talktome.dependencies.modules.AlertsModule;

@Singleton
@Component(modules = {AlertsModule.class})
public interface ApplicationComponent {
    void inject(BasicActivity activity);
}
