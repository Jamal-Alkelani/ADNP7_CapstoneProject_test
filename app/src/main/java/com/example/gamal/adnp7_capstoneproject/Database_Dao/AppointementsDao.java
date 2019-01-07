package com.example.gamal.adnp7_capstoneproject.Database_Dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.gamal.adnp7_capstoneproject.Models.Appointment;

import java.util.List;

@Dao
public interface AppointementsDao {

    @Query("SELECT * FROM Appointments")
    LiveData<List<Appointment>> getAppointments();

    @Insert
    void insertAppointemnt(Appointment...appointments);

    @Query("SELECT * FROM Appointments")
    List<Appointment> getAllAppointments();

    @Query("DELETE FROM Appointments WHERE ID=:appointmentID")
    void deleteAppointmentById(long appointmentID);
}
