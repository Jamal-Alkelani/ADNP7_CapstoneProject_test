package com.example.gamal.adnp7_capstoneproject.Database_Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.gamal.adnp7_capstoneproject.Models.User;

import java.util.List;

@Dao
public interface UsersDao {

    @Query("SELECT * FROM Users")
    List<User> getAllUsers();

    @Query("SELECT * FROM Users where Email LIKE  :email AND Pass LIKE :pass")
    User login(String email, String pass);

    @Insert
    void insertUser(User...users);
}
