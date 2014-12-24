package js.paperweather;

import android.app.Application;

public class PaperWeatherApplication extends Application {

    public void onCreate() {
        // initial request to build the graph and store it in the module
        MainModule.getObjectGraph();
    }
}