package com.example.android.quakereport;

/**
 * Created by emredogan on 18/02/2018.
 */

public class Earthquake {

    private Double mMagnitude;
    private String mLocation;
    private long mTimeInMilliseconds;
    private String mUrl;


    public Earthquake(Double magnitude, String location, long timeInMiliseconds, String url) {
        mMagnitude = magnitude;
        mLocation = location;
        mTimeInMilliseconds = timeInMiliseconds;
        mUrl = url;
    }

    public Double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getUrl() {
        return mUrl;
    }


}
