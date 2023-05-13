package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.DB.AppDataBase;
import com.example.myapplication.DB.BookingsDAO;
import com.example.myapplication.DB.FlightsDAO;
import com.example.myapplication.databinding.ActivitySearchResultsBinding;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity implements RecyclerViewInterface{
    List<Flights> mFlights;
    int mQuantity;
    int mUserID;
    String mWhereTo;
    String mWhereFrom;
    private static final String SEARCH_RESULTS_ACTIVITY_USER = "com.example.myapplication.SearchResultsActivityUser";
    private static final String SEARCH_RESULTS_ACTIVITY_WHERE_TO = ".com.example.myapplication.SearchResultsActivityWhereTo";

    private static final String SEARCH_RESULTS_ACTIVITY_WHERE_FROM = ".com.example.myapplication.SearchResultsActivityWhereFrom";
    private static final String SEARCH_RESULTS_ACTIVITY_QUANTITY = ".com.example.myapplication.SearchResultsActivityQuantity";

    ActivitySearchResultsBinding binding;
    RecyclerView mSearchResults;
    Button mGoBackButton;

    FlightsDAO mFlightsDAO;
    BookingsDAO mBookingsDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        binding = ActivitySearchResultsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        mGoBackButton = binding.GoBackButton;
        mUserID = getIntent().getIntExtra(SEARCH_RESULTS_ACTIVITY_USER, -1);
        mWhereTo = getIntent().getStringExtra(SEARCH_RESULTS_ACTIVITY_WHERE_TO);
        mWhereFrom = getIntent().getStringExtra(SEARCH_RESULTS_ACTIVITY_WHERE_FROM);
        mQuantity = getIntent().getIntExtra(SEARCH_RESULTS_ACTIVITY_QUANTITY, -1);

        mFlightsDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .createFromAsset("database/AirlineTracker.db")
                .build()
                .FlightsDAO();
        mBookingsDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .createFromAsset("database/AirlineTracker.db")
                .build()
                .BookingsDAO();

        displayInfo();
        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = searchFlightsActivity.getIntent(getApplicationContext(), mUserID);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context, int userId, String whereTo, String whereFrom, int quantity) {
        Intent intent = new Intent(context, SearchResultsActivity.class);
        intent.putExtra(SEARCH_RESULTS_ACTIVITY_USER, userId);
        intent.putExtra(SEARCH_RESULTS_ACTIVITY_WHERE_TO, whereTo);
        intent.putExtra(SEARCH_RESULTS_ACTIVITY_WHERE_FROM, whereFrom);
        intent.putExtra(SEARCH_RESULTS_ACTIVITY_QUANTITY, quantity);
        return intent;
    }

    public List<Flights> getFlights(String whereTo, String whereFrom){
        List<Flights> flights = mFlightsDAO.getByOriginAndDestination(whereTo,whereFrom);
        if(flights.isEmpty()){
            flights = mFlightsDAO.getAllFlights();
        }
        return flights;
    }

    public void displayInfo() {
        mFlights = getFlights(mWhereTo, mWhereFrom);
        mSearchResults = findViewById(R.id.SearchResultsRecyclerView);
        mSearchResults.setAdapter(new FlightsRecyclerViewAdapter(this, mFlights, this));
        mSearchResults.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int position) {
        int purchases = mFlights.get(position).getPurchases() + mQuantity;
        int capacity = mFlights.get(position).getCapacity();
        if(purchases > capacity){
            Toast.makeText(SearchResultsActivity.this, "All Flights booked!", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(SearchResultsActivity.this, "Flight booked.", Toast.LENGTH_SHORT).show();
        Bookings booking = new Bookings(mUserID, mFlights.get(position).getFlightId(), mQuantity);
        mBookingsDAO.insert(booking);
        mFlightsDAO.setPurchases(mFlights.get(position).getFlightId(), purchases);
        displayInfo();
    }
}