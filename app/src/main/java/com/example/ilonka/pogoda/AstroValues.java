package com.example.ilonka.pogoda;

public class AstroValues {
    public static int refreshTime = 15;
    public static double longitude = 19.467;
    public static double latitude = 51.75;
   // public static Localization actualLocalization = new Localization("pl", "lodz");
    //public static Localizations localizations = new Localizations();

    public AstroValues(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}

