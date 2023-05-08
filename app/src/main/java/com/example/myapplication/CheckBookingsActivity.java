package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CheckBookingsActivity extends AppCompatActivity {



    private static final String USER_CHECK_BOOKINGS = "com.example.myapplication.UserCheckBookings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_bookings);
    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, CheckBookingsActivity.class);
        intent.putExtra(USER_CHECK_BOOKINGS, userId);
        return intent;
    }
}