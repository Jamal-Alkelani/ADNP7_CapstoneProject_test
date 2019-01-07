package com.example.gamal.adnp7_capstoneproject.Initializers;

import android.content.Context;
import android.os.AsyncTask;

import com.example.gamal.adnp7_capstoneproject.Database.AppDatabase;
import com.example.gamal.adnp7_capstoneproject.Executors.AppExecutors;
import com.example.gamal.adnp7_capstoneproject.Models.Appointment;
import com.example.gamal.adnp7_capstoneproject.Models.User;

import java.util.ArrayList;
import java.util.List;

public class Database_Initializers {
    public static Context mContext;


    public static void initUsers() {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                AppDatabase appDatabase = AppDatabase.getAppDatabase(mContext);

                if (appDatabase.userDao().getAllUsers().size() == 0) {
                    appDatabase.userDao().insertUser(new User("gamalalkelani@gmail.com", "123456789"));
                }
                return null;
            }
        };
        task.execute();
    }

    public static void initAppointments() {
        AsyncTask asyncTask = new AsyncTask() {
            List<Appointment> appointments;
            int[] size = new int[1];
            AppDatabase appDatabase = AppDatabase.getAppDatabase(mContext);


            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (size[0] == 0) {
                    appointments = getSampleAppointments();

                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < appointments.size(); i++) {

                                appDatabase.appointementsDao().insertAppointemnt(appointments.get(i));
                            }
                        }
                    });
                }
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                appointments = new ArrayList<>();
                List<Appointment> liveData = appDatabase.appointementsDao().getAllAppointments();
                size[0] = liveData.size();
                return null;
            }
        };
        asyncTask.execute();


    }

    private static List<Appointment> getSampleAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment = new Appointment("7:00 AM", "Bus Station", "Nabuls"
                , "Jamal", "0597266408", 16);
        appointments.add(appointment);
        appointment = new Appointment("7:15 AM", "Bus Station", "Nablus"
                , "Abed", "0598353200", 11);
        appointments.add(appointment);
        appointment = new Appointment("7:30 AM", "Bus Station", "Jenin"
                , "Na'eem", "0598353200", 12);
        appointments.add(appointment);
        appointment = new Appointment("8:00 AM", "Bus Station", "TulKarem"
                , "Na'eem", "0597266408", 16);
        appointments.add(appointment);
        appointment = new Appointment("8:30 AM", "Bus Station", "Jenin"
                , "Abed", "0598353200", 4);
        appointments.add(appointment);
        appointment = new Appointment("9:00 AM", "Bus Station", "Nablus"
                , "Jamal", "0597266408", 12);
        appointments.add(appointment);
        appointment = new Appointment("9:15 AM", "Bus Station", "TulKarem"
                , "Na'eem", "05983123200", 16);
        appointments.add(appointment);
        appointment = new Appointment("9:30 AM", "Bus Station", "Nablus"
                , "Abed", "0597266408", 13);
        appointments.add(appointment);
        appointment = new Appointment("10:00 AM", "Bus Station", "TulKarem"
                , "Jalal", "0594323208", 16);
        appointments.add(appointment);
        appointment = new Appointment("1:00 AM", "Bus Station", "Nablus"
                , "Abu_Iyad", "0597224408", 8);
        appointments.add(appointment);

        return appointments;
    }
}
