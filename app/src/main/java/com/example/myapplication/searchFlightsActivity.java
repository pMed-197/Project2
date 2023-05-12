package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.databinding.ActivitySearchFlightsBinding;

public class searchFlightsActivity extends AppCompatActivity {
    private static final String SEARCH_FLIGHTS_ACTIVITY_USER = "com.example.myapplication.SearchFlightsActivityUser";

    ActivitySearchFlightsBinding binding;
    Button mSearchFlights;
    EditText mWhereTo;
    EditText mWhereFrom;
    EditText mQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_flights);

        binding = ActivitySearchFlightsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mSearchFlights = binding.SearchButton;
        mWhereTo = binding.Arrival;
        mWhereFrom = binding.From;
        mQuantity = binding.QuantityTickets;


    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, searchFlightsActivity.class);
        intent.putExtra(SEARCH_FLIGHTS_ACTIVITY_USER, userId);
        return intent;
    }
}