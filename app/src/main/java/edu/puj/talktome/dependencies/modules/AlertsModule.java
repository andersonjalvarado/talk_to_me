package edu.puj.talktome.dependencies.modules;

import edu.puj.talktome.utils.AlertsHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AlertsModule {
    @Singleton
    @Provides
    public AlertsHelper provideAlertHelper() {
        return new AlertsHelper();
    }
}
