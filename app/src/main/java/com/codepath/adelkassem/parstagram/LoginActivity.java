package com.codepath.adelkassem.parstagram;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private final int REQUEST_CODE = 20;

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignup;
    private TextView tvErrorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            goMainActivity();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
        tvErrorLogin = findViewById(R.id.tvErrorLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if (username.equals("")) {
                    etUsername.requestFocus();
                    etUsername.setError("Username is required!");
                } else if (password.equals("")) {
                    etPassword.requestFocus();
                    etPassword.setError("Password is required!");
                } else {
                    login(username, password);
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goSignupActivity();
            }
        });
    }

    private void goSignupActivity() {
        Log.d(TAG, "Navigating to SignupActivity");
        Intent i = new Intent(this, SignupActivity.class);
        startActivityForResult(i, REQUEST_CODE);
//        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this, "Signed up successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    private void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    tvErrorLogin.setText("*Incorrect username or password");
                    tvErrorLogin.setTextColor(Color.RED);
                    Log.e(TAG, "Issue with login", e);
                    e.printStackTrace();
                    return;
                }
                
                goMainActivity();
            }
        });
    }

    private void goMainActivity() {
        Log.d(TAG, "Navigating to MainActivity");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
