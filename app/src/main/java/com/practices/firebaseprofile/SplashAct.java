package com.practices.firebaseprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class SplashAct extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        // Check for internet connection
        if (isNetworkAvailable()) {
            // Check if the user is already authenticated (logged in)
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                // User is already logged in, open MainActivity
                startActivity(new Intent(SplashAct.this,MainActivity.class));
                finish();
            } else {
                // User is not logged in, open LoginActivity
                startActivity(new Intent(SplashAct.this,LoginAct.class));
                finish();
            }
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
            // Handle the case when there is no internet connection
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }


}