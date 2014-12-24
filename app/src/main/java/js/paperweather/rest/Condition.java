package js.paperweather.rest;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.gson.annotations.SerializedName;

import android.text.TextUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class Condition {

    @SerializedName("main") private Main main;
    @SerializedName("weather") private List<Weather> weather;
    @SerializedName("dt")   private long timestamp;

    public Condition(Main main, List<Weather> weather, long timestamp) {
        this.main = main;
        this.weather = weather;
        this.timestamp = timestamp;
    }

    public String getReadableDate(){
        return DateFormat.getDateTimeInstance().format(new Date(timestamp * 1000));
    }

    public String getTempInFahrenheit(){
        return String.valueOf(getFahrenheit());
    }

    public long getTime() {
        return timestamp;
    }

    public String getWeatherDescription() {
        return TextUtils.join(",", Iterables.transform(weather, new Function<Weather, String>() {
            @Override
            public String apply(Weather input) {
                return input.description;
            }
        }));
    }

    private int getFahrenheit() {
        return (int) (1.8d * (main.temp - 273) + 32);
    }

    public static class Main {

        private double temp;

        public Main(double temp) {
            this.temp = temp;
        }
    }

    public static class Weather {
        private String description;

        public Weather(String description) {
            this.description = description;
        }
    }
}
