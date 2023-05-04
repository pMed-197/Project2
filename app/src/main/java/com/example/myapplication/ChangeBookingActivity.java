package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.databinding.ActivityAdminOptionsBinding;
import com.example.myapplication.databinding.ActivityChangeBookingBinding;

public class ChangeBookingActivity extends AppCompatActivity {
    private static final String CHANGE_BOOKING_ACTIVITY_USER = "com.example.myapplication.ChangeBookingActivityUser";
    ActivityChangeBookingBinding binding;
    EditText mBookingId;
    Button mDeleteBooking;
    Button mBackButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_booking);

        binding = ActivityChangeBookingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mBookingId = binding.editTextBookingId;
        mDeleteBooking = binding.deleteBookingButton;
        mBackButton = binding.goBackButton3;

        int userId = getIntent().getIntExtra(CHANGE_BOOKING_ACTIVITY_USER, -1);

        mDeleteBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: ADD WORKING DELETE BOOKING FUNCTION
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminOptionsActivity.getIntent(getApplicationContext(), userId);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, ChangeBookingActivity.class);
        intent.putExtra(CHANGE_BOOKING_ACTIVITY_USER, userId);
        return intent;
    }
}