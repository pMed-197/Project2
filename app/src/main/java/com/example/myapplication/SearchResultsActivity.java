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

import com.example.myapplication.DB.AppDataBase;
import com.example.myapplication.DB.BookingsDAO;
import com.example.myapplication.DB.FlightsDAO;
import com.example.myapplication.databinding.ActivitySearchResultsBinding;

import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
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
        int userId = getIntent().getIntExtra(SEARCH_RESULTS_ACTIVITY_USER, -1);
        String whereTo = getIntent().getStringExtra(SEARCH_RESULTS_ACTIVITY_WHERE_TO);
        String whereFrom = getIntent().getStringExtra(SEARCH_RESULTS_ACTIVITY_WHERE_FROM);
        int quantity = getIntent().getIntExtra(SEARCH_RESULTS_ACTIVITY_QUANTITY, -1);

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


        List<Flights> flights = getFlights(whereTo, whereFrom);
        mSearchResults = findViewById(R.id.SearchResultsRecyclerView);
        mSearchResults.setAdapter(new FlightsRecyclerViewAdapter(this, flights));
        mSearchResults.setLayoutManager(new LinearLayoutManager(this));

        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = searchFlightsActivity.getIntent(getApplicationContext(), userId);
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
}