package edu.puj.talktome.dependencies.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.puj.talktome.services.CameraService;

@Module
public class CameraModule {
    @Singleton
    @Provides
    public CameraService provideCameraService() {
        return new CameraService();
    }
}
