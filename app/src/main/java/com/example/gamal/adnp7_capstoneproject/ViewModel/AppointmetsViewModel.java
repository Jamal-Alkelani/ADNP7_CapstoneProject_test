package com.example.gamal.adnp7_capstoneproject.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.gamal.adnp7_capstoneproject.Database.AppDatabase;
import com.example.gamal.adnp7_capstoneproject.Models.Appointment;

import java.util.List;

public class AppointmetsViewModel extends ViewModel {

    private LiveData<List<Appointment>> appointments;

    public AppointmetsViewModel(AppDatabase appDatabase) {
        appointments=appDatabase.appointementsDao().getAppointments();
    }

    public LiveData<List<Appointment>> getAppoointments(){
        return appointments;
    }
}
