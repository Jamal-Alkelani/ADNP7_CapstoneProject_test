package com.example.gamal.adnp7_capstoneproject.FCM;


import android.content.Context;
import android.util.Log;

import com.example.gamal.adnp7_capstoneproject.Activities.HomeActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;


public class AppointmentFirebaseMessageService extends FirebaseMessagingService {
    private static String LOG_TAG = AppointmentFirebaseMessageService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d(LOG_TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.

        Map<String, String> data = remoteMessage.getData();

        if (data.size() > 0) {
            Log.d(LOG_TAG, "Message data payload: " + data);


        }
    }

}
