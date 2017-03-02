package com.ice.parkingapp;

public class City {

    public String id;
    public String name;
    public String country;
    public double latitude;
    public double longitude;
    public int zoom;
    public int radius;
    public String address;



    public City(String s1, String s2, String s3, Double d3, Double d4, int i5, int i6) {
        this.id = s1;
        this.name = s2;
        this.country = s3;
        this.latitude = d3;
        this.longitude = d4;
        this.zoom = i5;
        this.radius = i6;
    }

    public double getLongitude() {
        return longitude;
    }
    public double getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return String.format("%s:", name);
    }
}
