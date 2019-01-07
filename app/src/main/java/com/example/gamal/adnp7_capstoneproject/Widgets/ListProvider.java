package com.example.gamal.adnp7_capstoneproject.Widgets;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.gamal.adnp7_capstoneproject.Models.WidgetAppointmentItem;
import com.example.gamal.adnp7_capstoneproject.R;

import java.util.List;

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    List<WidgetAppointmentItem> data;
    private Context context = null;
    Intent intent;
    public ListProvider(Context context, Intent intent) {
        this.context = context;

        this.intent=intent;
//        Log.e("xx",data.size()+"");

    }



    @Override
    public void onCreate() {
        if (intent.hasExtra("abc"))
        data=intent.getBundleExtra("abc").getParcelableArrayList("xx");
        if (data==null)
            Log.e("xx","Data is null");
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (data!=null)
        return data.size();
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_li);
        remoteView.setTextViewText(R.id.tv_appointment,data.get(position).getLeavingTime()+"-"+data.get(position).getLocation());
        remoteView.setTextViewText(R.id.tv_wjha,data.get(position).getWjha());
        remoteView.setTextViewText(R.id.tv_driver,data.get(position).getDriverName());

        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;

    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
