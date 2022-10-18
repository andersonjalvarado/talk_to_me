package edu.puj.talktome;

import android.app.Application;

import edu.puj.talktome.dependencies.components.ApplicationComponent;
import edu.puj.talktome.dependencies.components.DaggerApplicationComponent;
import lombok.Getter;

@Getter
public class App extends Application {
    ApplicationComponent appComponent = DaggerApplicationComponent.builder().build();
}
