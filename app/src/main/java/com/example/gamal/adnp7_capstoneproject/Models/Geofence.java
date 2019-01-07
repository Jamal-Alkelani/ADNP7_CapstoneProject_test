package com.example.gamal.adnp7_capstoneproject.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.util.Pair;

@Entity(tableName = "Geofences")
public class Geofence {
    @NonNull
    @PrimaryKey
    public String ID;
    public String name;
    public Double Lat;
    public Double Lng;

    public Geofence(String ID, String name, Double lat, Double lng) {
        this.ID = ID;
        this.name = name;
        this.Lat = lat;
        this.Lng = lng;
    }

    public Geofence(){

    }
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public Double getLat() {
        return Lat;
    }

    public Double getLng() {
        return Lng;
    }

    @Ignore
    public String getLatLng() {
        return getLat()+getLng()+"";
    }

}
