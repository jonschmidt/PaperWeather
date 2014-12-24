package js.paperweather.rest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastResult {

    private City city;

    @SerializedName("list")
    private List<Condition> conditions;

    public ForecastResult(City city, List<Condition> conditions) {
        this.city = city;
        this.conditions = conditions;
    }

    public String getCityName() {
        return city.getName();
    }

    public long getTimeSpan() {
        return conditions.get(conditions.size() - 1).getTime() - conditions.get(0).getTime();
    }

    public Condition getWeatherAt(int progress) {
        int i = 0;
        while (conditions.get(i).getTime() < conditions.get(0).getTime() + progress){
            i++;
        }
        return conditions.get(i);
    }
}
