package com.example.gamal.adnp7_capstoneproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gamal.adnp7_capstoneproject.Database.AppDatabase;
import com.example.gamal.adnp7_capstoneproject.Executors.AppExecutors;
import com.example.gamal.adnp7_capstoneproject.Models.Appointment;
import com.example.gamal.adnp7_capstoneproject.R;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.CustomViewHolder> {
    private List<Appointment> appointments;
    private Context mContext;
    private final OnItemClickListener listener;
    private int[] circles = {R.drawable.solid_circle_blue, R.drawable.solid_circle_red, R.drawable.solid_circle_green};
    private int[] coresspondingColors;

    public Context getContext() {
        return getContext();
    }

    public void deleteItem(int position) {
        final int itemId = appointments.get(position).getID();
        appointments.remove(position);
        notifyItemRemoved(position);
        final AppDatabase appDatabase = AppDatabase.getAppDatabase(mContext);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.appointementsDao().deleteAppointmentById(itemId);
            }
        });
        Toast.makeText(mContext, "Item Delted", Toast.LENGTH_SHORT).show();
    }


    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public AppointmentsAdapter(Context context, List<Appointment> data, OnItemClickListener listener) {
        this.appointments = data;
        mContext = context;
        this.listener = listener;
        initColors();
    }


    private void initColors() {
        coresspondingColors = new int[3];
        coresspondingColors[0] = mContext.getResources().getColor(R.color.BlueViolet);
        coresspondingColors[1] = mContext.getResources().getColor(R.color.red);
        coresspondingColors[2] = mContext.getResources().getColor(R.color.GreenYellow);
    }

    public void setDate(List<Appointment> data) {
        appointments = data;

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.li_rv_appointment, parent, false);
        return new CustomViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        int randomColor = (int) (Math.random() * 3);
        holder.wjha.setText(appointments.get(position).getWjha());
        holder.wjha.setTextColor(coresspondingColors[randomColor]);
        holder.driverName.setText(appointments.get(position).getDriverName());
        holder.driverName.setTextColor(coresspondingColors[randomColor]);
        String app = appointments.get(position).getLeavingTime() + "-" + appointments.get(position).getLocation();
        holder.appointment.setText(app);
        holder.circle.setImageResource(circles[randomColor]);
    }

    @Override
    public int getItemCount() {
        if (appointments != null)
            return appointments.size();
        return 0;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView wjha;
        private TextView driverName;
        private TextView appointment;
        private ImageView circle;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            wjha = itemView.findViewById(R.id.tv_wjha);
            driverName = itemView.findViewById(R.id.tv_driver);
            appointment = itemView.findViewById(R.id.tv_appointment);
            circle = itemView.findViewById(R.id.iv_circle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(getAdapterPosition());
                }
            });

        }
    }
}
