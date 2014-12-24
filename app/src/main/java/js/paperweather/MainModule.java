

package js.paperweather;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

import javax.inject.Singleton;

@Module(injects = { MainActivity.class })
public class MainModule {

    private static ObjectGraph graph;

    public static ObjectGraph getObjectGraph() {
        if (graph == null) {
            graph = ObjectGraph.create(new MainModule());
        }
        return graph;
    }

    @Provides
    @Singleton
    public RestAdapter getRestAdapter(Gson gson) {
        return new RestAdapter.Builder()
                .setEndpoint("http://api.openweathermap.org/data/2.5")
                .setClient(new OkClient())
                .setConverter(new GsonConverter(gson))
                .build();
    }

    @Provides
    public Gson getGson(){
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
