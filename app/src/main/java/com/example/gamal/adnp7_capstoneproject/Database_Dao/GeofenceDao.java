package com.example.gamal.adnp7_capstoneproject.Database_Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.gamal.adnp7_capstoneproject.Models.Geofence;

import java.util.List;



    @Dao
    public interface GeofenceDao {
        @Query("SELECT * FROM Geofences")
        LiveData<List<Geofence>> getGeofences();

        @Insert
        void insertGeofence(Geofence...geofences);

        @Query("SELECT * FROM Geofences")
        List<Geofence> getAllGeofences();

        @Query("DELETE FROM Geofences WHERE ID=:geofenceID")
        void deletePlaceById(String geofenceID);
    }

