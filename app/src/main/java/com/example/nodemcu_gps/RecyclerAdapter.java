package com.example.nodemcu_gps;

public class RecyclerAdapter {
    private String no,ldr,lat,acc,date,time,id_user;

    public RecyclerAdapter(String no, String ldr, String lat, String acc, String date, String time, String id_user) {
        this.no = no;
        this.ldr = ldr;
        this.lat = lat;
        this.acc = acc;
        this.date = date;
        this.time = time;
        this.id_user=id_user;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getLdr() {
        return ldr;
    }

    public void setLdr(String ldr) {
        this.ldr = ldr;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
