package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.databinding.ActivityChangeFlightBinding;

public class changeFlightActivity extends AppCompatActivity {
    private static final String CHANGE_FLIGHT_ACTIVITY_USER = "com.example.myapplication.changeFlightActivityUser";

    ActivityChangeFlightBinding binding;
    Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_flight);

        binding = ActivityChangeFlightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mBackButton = binding.changeFlightBackButton;

        int userId = getIntent().getIntExtra(CHANGE_FLIGHT_ACTIVITY_USER, -1);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UserLandingActivity.getIntent(getApplicationContext(), userId);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, changeFlightActivity.class);
        intent.putExtra(CHANGE_FLIGHT_ACTIVITY_USER, userId);
        return intent;
    }
}