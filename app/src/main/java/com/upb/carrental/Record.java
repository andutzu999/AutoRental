package com.upb.carrental;


public class Record {
    private String email;
    private String vehicle;
    private String duration;
    private String price;

    public Record() {}

    public Record(String email, String vehicle, String duration, String price) {
        this.email = email;
        this.vehicle = vehicle;
        this.duration = duration;
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
