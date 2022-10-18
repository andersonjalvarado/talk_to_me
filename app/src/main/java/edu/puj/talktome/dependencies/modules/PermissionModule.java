package edu.puj.talktome.dependencies.modules;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import edu.puj.talktome.utils.PermissionHelper;

@Module
public class PermissionModule {

    @Singleton
    @Provides
    public PermissionHelper providePermissionHelper() {
        return new PermissionHelper();
    }
}
