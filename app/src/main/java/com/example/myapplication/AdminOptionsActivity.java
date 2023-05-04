package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.databinding.ActivityAdminOptionsBinding;
import com.example.myapplication.databinding.ActivityUserLandingBinding;

public class AdminOptionsActivity extends AppCompatActivity {
    private static final String ADMIN_OPTIONS_ACTIVITY_USER = "com.example.myapplication.AdminOptionsActivityUser";

    ActivityAdminOptionsBinding binding;
    Button mChangeFlightsButton;
    Button mChangeBookingsButton;
    Button mDisplayDatabaseButton;
    Button mGoBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_options);

        binding = ActivityAdminOptionsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mChangeFlightsButton = binding.changeFlightsButton;
        mChangeBookingsButton = binding.changeBookingsButton;
        mDisplayDatabaseButton = binding.databaseDetailsButton;
        mGoBackButton = binding.goBackButton2;

        int userId = getIntent().getIntExtra(ADMIN_OPTIONS_ACTIVITY_USER, -1);

        mChangeFlightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = changeFlightActivity.getIntent(getApplicationContext(), userId);
                startActivity(intent);
            }
        });

        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UserLandingActivity.getIntent(getApplicationContext(), userId);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, AdminOptionsActivity.class);
        intent.putExtra(ADMIN_OPTIONS_ACTIVITY_USER, userId);
        return intent;
    }
}