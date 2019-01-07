package com.example.gamal.adnp7_capstoneproject.Models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Users")
public class User {
    @PrimaryKey (autoGenerate = true)
    private int ID;
    private String Email;
    private String Pass;

    public User(String email, String pass) {
        Email = email;
        Pass = pass;
    }

    public User(int ID, String email, String pass) {
        this.ID = ID;
        Email = email;
        Pass = pass;
    }

    public User() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
