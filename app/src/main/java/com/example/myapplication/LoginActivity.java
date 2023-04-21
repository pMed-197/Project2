package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.DB.AppDataBase;
import com.example.myapplication.DB.UserDAO;
import com.example.myapplication.databinding.ActivityMainBinding;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mUserPassword;

    Button mSubmit;

    UserDAO mUserDAO;
    List<User> mUserList;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m);

        binding =ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mUsername = binding.mainUsernameEditText;
        mUserPassword = binding.mainUserPasswordEditText;
        mSubmit = binding.mainSubmitButton;

        mUserDAO= Room.databaseBuilder(this, AppDataBase.class,AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .UserDAO();


    }
    private void submitUser(){
        String username = mUsername.getText().toString();
        String password = mUserPassword.getText().toString();

        User log = new User(username,password);
        mUserDAO.insert(log);

    }
    private void logInUser(){
        String username = mUsername.getText().toString();
        String password = mUserPassword.getText().toString();
        User user = mUserDAO.getByUsername(username);

        if(user == null){
            return;
        }
        if(user.getUserPassword().equals(password){

        }

    }

}