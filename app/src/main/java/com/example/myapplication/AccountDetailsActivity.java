package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.databinding.ActivityAccountDetailsBinding;

public class AccountDetailsActivity extends AppCompatActivity {

    private static final String ACCOUNT_DETAILS_ACTIVITY = "com.example.myapplication.AccountDetailsActivity";

    ActivityAccountDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);

        binding = ActivityAccountDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    public static Intent getIntent(Context context, int userId) {
        Intent intent = new Intent(context, AccountDetailsActivity.class);
        intent.putExtra(ACCOUNT_DETAILS_ACTIVITY, userId);
        return intent;
    }
}