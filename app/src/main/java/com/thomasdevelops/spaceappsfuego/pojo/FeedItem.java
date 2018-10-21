package com.thomasdevelops.spaceappsfuego.pojo;

public class FeedItem {
    private String reportDate;
    private String reportTime;
    private double longitude;
    private double latitude;
    private String location;
    private int views;
    private int totalComments;

    public FeedItem(){}

    public FeedItem(String reportDate, String reportTime, double longitude, double latitude, String location, int views, int totalComments) {
        this.reportDate = reportDate;
        this.reportTime = reportTime;
        this.longitude = longitude;
        this.latitude = latitude;
        this.location = location;
        this.views = views;
        this.totalComments = totalComments;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getTotalComments() {
        return totalComments;
    }

    public void setTotalComments(int totalComments) {
        this.totalComments = totalComments;
    }
}
