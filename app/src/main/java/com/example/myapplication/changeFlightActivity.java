package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.DB.AppDataBase;
import com.example.myapplication.DB.FlightsDAO;
import com.example.myapplication.databinding.ActivityChangeFlightBinding;

public class changeFlightActivity extends AppCompatActivity {
    private static final String CHANGE_FLIGHT_ACTIVITY_USER = "com.example.myapplication.changeFlightActivityUser";

    ActivityChangeFlightBinding binding;
    EditText mOrigin;
    EditText mDestination;
    EditText mCapacity;
    EditText mFlightID;
    Button mBackButton;
    Button mAddFlightButton;
    Button mDeleteFlightButton;

    FlightsDAO mFlightsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_flight);

        binding = ActivityChangeFlightBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mOrigin = binding.editTextOrigin;
        mDestination = binding.editTextDestination;
        mCapacity = binding.editTextCapacity;
        mFlightID = binding.editTextFlightId;
        mBackButton = binding.changeFlightBackButton;
        mAddFlightButton = binding.addFlightButton;
        mDeleteFlightButton = binding.deleteFlightButton;

        mFlightsDAO = Room.databaseBuilder(this, AppDataBase.class,AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .createFromAsset("database/AirlineTracker.db")
                .build()
                .FlightsDAO();

        int userId = getIntent().getIntExtra(CHANGE_FLIGHT_ACTIVITY_USER, -1);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = UserLandingActivity.getIntent(getApplicationContext(), userId);
                startActivity(intent);
            }
        });

        mAddFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasAddInputs()) {
                    String origin = mOrigin.getText().toString();
                    String destination = mDestination.getText().toString();
                    int capacity = Integer.parseInt(mCapacity.getText().toString());

                    Flights flight = new Flights(origin, destination, capacity);
                    mFlightsDAO.insert(flight);
                    Toast.makeText(changeFlightActivity.this, "FLIGHT ADDED YAY!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mDeleteFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasDeleteInputs()) {
                    int flightId = Integer.parseInt(mFlightID.getText().toString());
                    Flights flight = mFlightsDAO.getById(flightId);
                    mFlightsDAO.delete(flight);
                    Toast.makeText(changeFlightActivity.this, "FLIGHT DELETED!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean hasAddInputs() {
        if (mOrigin.getText().toString().isEmpty()) {
            Toast.makeText(changeFlightActivity.this, "Where From is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mDestination.getText().toString().isEmpty()) {
            Toast.makeText(changeFlightActivity.this, "Where To is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mCapacity.getText().toString().isEmpty()) {
            Toast.makeText(changeFlightActivity.this, "Capacity is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean hasDeleteInputs() {
        if (mFlightID.getText().toString().isEmpty()) {
            Toast.makeText(changeFlightActivity.this, "FlightID is Empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, changeFlightActivity.class);
        intent.putExtra(CHANGE_FLIGHT_ACTIVITY_USER, userId);
        return intent;
    }
}