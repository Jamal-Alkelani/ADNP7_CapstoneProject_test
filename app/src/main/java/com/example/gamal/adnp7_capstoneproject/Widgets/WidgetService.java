package com.example.gamal.adnp7_capstoneproject.Widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {


    public WidgetService() {
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {


        return (new ListProvider(this.getApplicationContext(), intent));
    }

}