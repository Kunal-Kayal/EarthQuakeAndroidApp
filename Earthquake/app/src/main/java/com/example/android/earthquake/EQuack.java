package com.example.android.earthquake;

public class EQuack {
    private String place1,place2,date,time,magnitude, Url;

    public EQuack(String magnitude, String place1,String place2, String date, String time,String url) {
        this.magnitude = magnitude;
        this.place1 = place1;
        this.place2 = place2;
        this.date = date;
        this.time = time;
        this.Url = url;
    }

    public String getMagnitude() {

       return  magnitude;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace1() {
        return place1;
    }
    public String getPlace2() {
        return place2;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getDate() {
        return date;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public void setPlace1(String place) {
        this.place1 = place;
    }
    public void setPlace2(String place) {
        this.place2 = place;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "EQuack{" +
                "magnitude=" + magnitude +
                ", place=" + place1 +
                " "+ place2+
                ", date=" + date +
                ", time=" +time+
                ", Url=" + Url+
                '}';
    }
}
