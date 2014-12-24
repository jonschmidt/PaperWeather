package js.paperweather;

import butterknife.ButterKnife;
import butterknife.InjectView;
import js.paperweather.rest.ForecastResult;
import js.paperweather.rest.OpenWeather;
import js.paperweather.rest.Condition;
import retrofit.RestAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import javax.inject.Inject;

public class MainActivity extends ActionBarActivity {

    @Inject RestAdapter restAdapater;
    @InjectView(R.id.txt_temp) TextView tempText;
    @InjectView(R.id.txt_when) TextView whenText;
    @InjectView(R.id.txt_description) TextView descriptionText;
    @InjectView(R.id.timeline) SeekBar timeline;

    private ForecastResult forecastResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainModule.getObjectGraph().inject(this);

        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        timeline.setEnabled(false);
        timeline.setOnSeekBarChangeListener(onSeekBarChangeListener);
        restAdapater.create(OpenWeather.class)
                .getCurrentWeather(41.6, -71.25) // hardcoded to Portsmouth, RI for now
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getForecastSubscriber());

    }

    private Subscriber<ForecastResult> getForecastSubscriber() {
        return new Subscriber<ForecastResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("asdf","On error ", e);
            }

            @Override
            public void onNext(ForecastResult forecastResult) {
                MainActivity.this.forecastResult = forecastResult;
                setTitle(forecastResult.getCityName());
                timeline.setMax((int) forecastResult.getTimeSpan());
                timeline.setEnabled(true);
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private SeekBar.OnSeekBarChangeListener  onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            Condition condition = forecastResult.getWeatherAt(progress);
            whenText.setText(condition.getReadableDate());
            tempText.setText(condition.getTempInFahrenheit());
            descriptionText.setText(condition.getWeatherDescription());
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
