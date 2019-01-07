package com.example.gamal.adnp7_capstoneproject.Widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.example.gamal.adnp7_capstoneproject.Database.AppDatabase;
import com.example.gamal.adnp7_capstoneproject.Executors.AppExecutors;
import com.example.gamal.adnp7_capstoneproject.Models.Appointment;
import com.example.gamal.adnp7_capstoneproject.Models.WidgetAppointmentItem;
import com.example.gamal.adnp7_capstoneproject.R;

import java.util.ArrayList;
import java.util.List;


public class AppointmentsWidget extends AppWidgetProvider {
    static List<Appointment> appointments;
    static void updateAppWidget( Context context,  final AppWidgetManager appWidgetManager,
                                int appWidgetId) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.appointments_widget);

        final AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appointments=appDatabase.appointementsDao().getAllAppointments();
            }
        });

        Intent svcIntent = new Intent(context, WidgetService.class);
        ArrayList<WidgetAppointmentItem> wp=new ArrayList<>();
        for (int i=0;i<appointments.size();i++){
            Appointment appointment=appointments.get(i);

            wp.add(new WidgetAppointmentItem(appointment.getLeavingTime(),appointment.getLocation()
                    ,appointment.getWjha(),appointment.getDriverName()));
        }

        Bundle bundle=new Bundle();
        bundle.putParcelableArrayList("xx",wp);
        svcIntent.putExtra("abc",bundle);
        if (wp.size()>0)
            views.setViewVisibility(R.id.appwidget_text,View.GONE);
        else
            views.setViewVisibility(R.id.appwidget_text,View.VISIBLE);
        views.setRemoteAdapter(R.id.lv_appointemts, svcIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        final AppDatabase appDatabase = AppDatabase.getAppDatabase(context);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appointments=appDatabase.appointementsDao().getAllAppointments();
            }
        });
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

