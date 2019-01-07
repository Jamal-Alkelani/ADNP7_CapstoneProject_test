package com.example.gamal.adnp7_capstoneproject.Models;


import android.os.Parcel;
import android.os.Parcelable;

public class WidgetAppointmentItem implements Parcelable {
    private String leavingTime;
    private String location;
    private String wjha;
    private String driverName;

    public WidgetAppointmentItem(String leavingTime, String location, String wjha, String driverName) {
        this.leavingTime = leavingTime;
        this.location = location;
        this.wjha = wjha;
        this.driverName = driverName;
    }

    public WidgetAppointmentItem() {
    }

    protected WidgetAppointmentItem(Parcel in) {
        leavingTime = in.readString();
        location = in.readString();
        wjha = in.readString();
        driverName = in.readString();
    }

    public static final Creator<WidgetAppointmentItem> CREATOR = new Creator<WidgetAppointmentItem>() {
        @Override
        public WidgetAppointmentItem createFromParcel(Parcel in) {
            return new WidgetAppointmentItem(in);
        }

        @Override
        public WidgetAppointmentItem[] newArray(int size) {
            return new WidgetAppointmentItem[size];
        }
    };

    public String getLeavingTime() {
        return leavingTime;
    }

    public void setLeavingTime(String leavingTime) {
        this.leavingTime = leavingTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWjha() {
        return wjha;
    }

    public void setWjha(String wjha) {
        this.wjha = wjha;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(leavingTime);
        parcel.writeString(location);
        parcel.writeString(wjha);
        parcel.writeString(driverName);
    }
}
