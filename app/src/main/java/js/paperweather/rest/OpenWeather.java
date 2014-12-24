package js.paperweather.rest;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface OpenWeather {

    @GET("/forecast")
    Observable<ForecastResult> getCurrentWeather(@Query("lat") double lat, @Query("lon") double lon);

}
