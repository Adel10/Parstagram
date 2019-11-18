package com.codepath.adelkassem.parstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private Button btnSignup;
    private TextView tvErrorLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        btnSignup = findViewById(R.id.btnSignup);
        tvErrorLogin = findViewById(R.id.tvErrorLogin);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();
                if (username.equals("")) {
                    etUsername.requestFocus();
                    etUsername.setError("Username is required!");
                    Log.e(TAG, "Username is required!");
                } else if (email.equals("")) {
                    etEmail.requestFocus();
                    etEmail.setError("Email is required!");
                    Log.e(TAG, "Username is required!");
                } else if (password.equals("")) {
                    etPassword.requestFocus();
                    etPassword.setError("Password is required!");
                    Log.e(TAG, "Username is required!");
                } else {
                    if (isEmailValid(email)) {
                        signup(username, email, password);
                    } else {
                        etEmail.requestFocus();
                        etEmail.setError("Invalid email format!");
                    }
                }

            }
        });
    }

    private void signup(String username, String email, String password) {
        // Create the ParseUser
        ParseUser user = new ParseUser();
        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.put("handle", username);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    if (e.getMessage().equals("Account already exists for this email address.")) {
                        tvErrorLogin.setText("*Account already exists for this email address");
                    } else {
                        tvErrorLogin.setText("*Something went wrong");
                    }
                    tvErrorLogin.setTextColor(Color.RED);
                    Log.e(TAG, "Issue with sign up" + e.getMessage(), e);
                    e.printStackTrace();
                    return;
                }

                goLoginActivity();
            }
        });
    }

    private void goLoginActivity() {
        setResult(RESULT_OK);
        finish();
    }

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
