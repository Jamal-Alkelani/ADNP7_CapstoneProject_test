package com.example.gamal.adnp7_capstoneproject.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity (tableName = "Appointments")
public class Appointment implements Parcelable {
    @PrimaryKey (autoGenerate = true)
    private int ID;
    private String leavingTime;
    private String location;
    private String wjha;
    private String driverName;
    private String driverPhoneNumber;
    private int passengers;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Ignore
    public Appointment(String leavingTime, String location, String wjha, String driverName, String driverPhoneNumber, int passengers) {
        this.leavingTime = leavingTime;
        this.location = location;
        this.wjha = wjha;
        this.driverName = driverName;
        this.driverPhoneNumber = driverPhoneNumber;
        this.passengers = passengers;
    }

    @Ignore
    protected Appointment(Parcel in) {
        leavingTime = in.readString();
        location = in.readString();
        wjha = in.readString();
        driverName = in.readString();
        driverPhoneNumber = in.readString();
        passengers = in.readInt();
    }


    public Appointment() {
    }

    public static final Creator<Appointment> CREATOR = new Creator<Appointment>() {
        @Override
        public Appointment createFromParcel(Parcel in) {
            return new Appointment(in);
        }

        @Override
        public Appointment[] newArray(int size) {
            return new Appointment[size];
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

    public String getDriverPhoneNumber() {
        return driverPhoneNumber;
    }

    public void setDriverPhoneNumber(String driverPhoneNumber) {
        this.driverPhoneNumber = driverPhoneNumber;
    }

    public int getPassengers() {
        return passengers;
    }

    public void setPassengers(int passengers) {
        this.passengers = passengers;
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
        parcel.writeString(driverPhoneNumber);
        parcel.writeInt(passengers);
    }
}
