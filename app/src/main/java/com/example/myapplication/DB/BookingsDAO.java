package com.example.myapplication.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import com.example.myapplication.Bookings;
import com.example.myapplication.User;

@Dao
public interface BookingsDAO {
    @Insert
    void insert(Bookings... bookings);

    @Update
    void update(Bookings... bookings);

    @Delete
    void delete(Bookings booking);
}
