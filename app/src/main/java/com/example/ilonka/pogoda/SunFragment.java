package com.example.ilonka.pogoda;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.astrocalculator.AstroCalculator;
import com.astrocalculator.AstroDateTime;

import java.text.DecimalFormat;
import java.util.Calendar;

public class SunFragment extends Fragment {

    private Calendar calendar = Calendar.getInstance();
    private DecimalFormat precision = new DecimalFormat("0.00");

    private TextView timeView;
    private TextView locationView;

    private TextView timeSunriseView;
    private TextView coordinateSunriseView;
    private TextView timeSunsetView;
    private TextView coordinateSunsetView;
    private TextView timeTwilightView;
    private TextView timeDawnView;

    private Handler infoHandler = new Handler();
    private Handler timeHandler = new Handler();
    private Runnable infoRunnable = new Runnable() {
        public void run() {
            fillFields();
            infoHandler.postDelayed(infoRunnable, AstroValues.refreshTime * 60 * 1000);
        }
    };
    private Runnable timeRunnable = new Runnable() {
        public void run() {
            fillTimeAndLocation();
            calendar.add(Calendar.SECOND, 1);
            timeHandler.postDelayed(timeRunnable, 1000);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_sun, container, false);
    }

    private void initFields() {
        timeSunriseView = (TextView) getActivity().findViewById(R.id.textTimeSunrise);
        coordinateSunriseView = (TextView) getActivity().findViewById(R.id.textCoordinateSunrise);
        timeSunsetView = (TextView) getActivity().findViewById(R.id.textTimeSunset);
        coordinateSunsetView = (TextView) getActivity().findViewById(R.id.textCoordinateSunset);
        timeTwilightView = (TextView) getActivity().findViewById(R.id.textTimeTwilight);
        timeDawnView = (TextView) getActivity().findViewById(R.id.textTimeDawn);
    }

    private void fillTimeAndLocation() {
        if (timeView == null || locationView == null) {
            timeView = (TextView) getActivity().findViewById(R.id.textSunTime);
            locationView = (TextView) getActivity().findViewById(R.id.textSunCoordinate);
        }
        timeView.setText(String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY)) + ":" + String.format("%02d", calendar.get(Calendar.MINUTE)) + ":" + String.format("%02d", calendar.get(Calendar.SECOND)));
        locationView.setText(AstroValues.longitude + "   " + AstroValues.latitude);
    }

    private void fillFields() {
        if (timeSunriseView == null || coordinateSunriseView == null || timeSunsetView == null || coordinateSunsetView == null || timeTwilightView == null || timeDawnView == null) {
            initFields();
        }

        AstroDateTime astroDateTime = new AstroDateTime(
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND),
                calendar.get(Calendar.ZONE_OFFSET) / (60 * 60 * 1000), true);
        AstroCalculator astroCalculator = new AstroCalculator(astroDateTime, new AstroCalculator.Location(AstroValues.latitude, AstroValues.longitude));
        AstroCalculator.SunInfo sunInfo = astroCalculator.getSunInfo();

        timeSunriseView.setText(String.format("%02d", sunInfo.getSunrise().getHour()) + ":" + String.format("%02d", sunInfo.getSunrise().getMinute()) + ":" + String.format("%02d", sunInfo.getSunrise().getSecond()));
        coordinateSunriseView.setText(precision.format(sunInfo.getAzimuthRise()));
        timeSunsetView.setText(String.format("%02d", sunInfo.getSunset().getHour()) + ":" + String.format("%02d", sunInfo.getSunset().getMinute()) + ":" + String.format("%02d", sunInfo.getSunset().getSecond()));
        coordinateSunsetView.setText(precision.format(sunInfo.getAzimuthSet()));
        timeTwilightView.setText(String.format("%02d", sunInfo.getTwilightEvening().getHour()) + ":" + String.format("%02d", sunInfo.getTwilightEvening().getMinute()) + ":" + String.format("%02d", sunInfo.getTwilightEvening().getSecond()));
        timeDawnView.setText(String.format("%02d", sunInfo.getTwilightMorning().getHour()) + ":" + String.format("%02d", sunInfo.getTwilightMorning().getMinute()) + ":" + String.format("%02d", sunInfo.getTwilightMorning().getSecond()));

    }

    @Override
    public void onResume() {
        super.onResume();
        infoHandler.removeCallbacks(infoRunnable);
        timeHandler.removeCallbacks(timeRunnable);
        fillFields();
        fillTimeAndLocation();
        infoRunnable.run();
        timeRunnable.run();
    }

    @Override
    public void onPause() {
        super.onPause();
        infoHandler.removeCallbacks(infoRunnable);
        timeHandler.removeCallbacks(timeRunnable);
    }

}
