package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.Models.User;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class LoginActivity extends AppCompatActivity {


    private EditText etUserName;
    EditText etPassword;
    private Button btnLogin;
    private TextView btnSignUpLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        if(ParseUser.getCurrentUser()!= null)
        {
            goMainActivity();
        }

        etUserName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUpLink = findViewById(R.id.SignUpLink);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                if (userName.isEmpty()){
                    etUserName.setError("Username cannot be blank");
                }
                if (password.isEmpty())
                    etPassword.setError("Password cannot be blank");
                LoginUser(userName, password);
            }
        });

        btnSignUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
    }

    private void LoginUser(String userName, String password) {
        if(!userName.isEmpty() && !password.isEmpty())
        {
            ParseUser.logInInBackground(userName, password, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user!= null)
                    {
                        Toast.makeText(LoginActivity.this, "login successfully",Toast.LENGTH_SHORT).show();
                        goMainActivity();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void goMainActivity() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}