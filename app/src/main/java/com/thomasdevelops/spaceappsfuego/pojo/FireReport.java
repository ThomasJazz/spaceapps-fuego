package com.thomasdevelops.spaceappsfuego.pojo;

public class FireReport {
    private String creator;
    private double lat;
    private double lng;

    public FireReport(){}

    public FireReport(String creator, double lat, double lng) {
        this.creator = creator;
        this.lat = lat;
        this.lng = lng;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}