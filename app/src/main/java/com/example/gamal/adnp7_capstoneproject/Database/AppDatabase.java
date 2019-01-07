package com.example.gamal.adnp7_capstoneproject.Database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.gamal.adnp7_capstoneproject.Database_Dao.AppointementsDao;
import com.example.gamal.adnp7_capstoneproject.Database_Dao.GeofenceDao;
import com.example.gamal.adnp7_capstoneproject.Database_Dao.UsersDao;
import com.example.gamal.adnp7_capstoneproject.Models.Appointment;
import com.example.gamal.adnp7_capstoneproject.Models.Geofence;
import com.example.gamal.adnp7_capstoneproject.Models.User;

@Database(entities = {User.class,Appointment.class,Geofence.class}, version = 4,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;
    public abstract UsersDao userDao();
    public abstract AppointementsDao appointementsDao();
    public abstract GeofenceDao geofenceDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "user-database")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
