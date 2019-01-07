package com.example.gamal.adnp7_capstoneproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gamal.adnp7_capstoneproject.Models.Appointment;
import com.example.gamal.adnp7_capstoneproject.R;

import java.util.List;

public class WidgetAppointmentsAdapter extends ArrayAdapter<Appointment> {
    private final Context context;
    List<Appointment> data;
    public WidgetAppointmentsAdapter( Context context, int resource,List<Appointment> data) {
        super(context, resource);
        this.context=context;
        this.data=data;
    }

    public void setData(List<Appointment> data){
        this.data=data;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.li_rv_appointment, parent, false);
        TextView tv_appointment =  rowView.findViewById(R.id.tv_appointment);
        TextView tv_wjha=rowView.findViewById(R.id.tv_wjha);
        TextView tv_driver=rowView.findViewById(R.id.tv_driver);


        tv_appointment.setText(data.get(position).getLeavingTime()+"-"+data.get(position).getLocation());
        tv_wjha.setText(data.get(position).getWjha());
        tv_driver.setText(data.get(position).getDriverName());
        return rowView;
    }
}
