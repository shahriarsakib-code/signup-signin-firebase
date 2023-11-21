package com.practices.firebaseprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
public class SignUp extends AppCompatActivity {
    TextView signin,signUptext_v;
    TextInputEditText fullname_v,email_v,phonenumber_v,password_v;
    RelativeLayout signupL_v;
    ProgressBar progressBar_v;
    ImageView backimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FirebaseApp.initializeApp(this);


        signin=findViewById(R.id.signin_txt);
        fullname_v=findViewById(R.id.fullname_edit);
        email_v=findViewById(R.id.email_edit);
        phonenumber_v=findViewById(R.id.number_edit);
        password_v=findViewById(R.id.pass_edit);
        signupL_v=findViewById(R.id.signup_layout);
        signUptext_v=findViewById(R.id.letscreate_text);
        progressBar_v=findViewById(R.id.progressbar);
        backimg=findViewById(R.id.back_image);


        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        signupL_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = fullname_v.getText().toString();
                String email = email_v.getText().toString();
                String phonenumber = phonenumber_v.getText().toString();
                String pass = password_v.getText().toString();

                if (name.isEmpty()) {
                    fullname_v.setError("please enter your name");
                    return;
                }
                if (email.isEmpty()) {
                    email_v.setError("enter valid email");
                    return;
                }
                if (phonenumber.isEmpty()) {
                    phonenumber_v.setError("enter valid number");
                    return;
                }
                if (pass.isEmpty()) {
                    password_v.setError("enter your password");
                    return;
                }

                progressBar_v.setVisibility(View.VISIBLE);
                signUptext_v.setVisibility(View.GONE);

                // Use Firebase Authentication to create a new user
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    // Sign up successful, now save additional user data to the database
                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("name", name);
                                    userData.put("email", email);
                                    userData.put("phoneNumber", phonenumber);
                                    userData.put("phoneNumber", pass);
                                    userRef.setValue(userData);

                                    Intent intent = new Intent(SignUp.this,SignupResult.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("email",email);
                                    intent.putExtra("phonenumber",phonenumber);
                                    startActivity(intent);
                                    finish();

                                    // Sign up successful, you can navigate to the next screen or handle it as needed
                                    Toast.makeText(SignUp.this, "Signup successful", Toast.LENGTH_SHORT).show();
                                } else {
                                    progressBar_v.setVisibility(View.GONE);
                                    signUptext_v.setVisibility(View.VISIBLE);
                                    // If sign up fails, display a message to the user.
                                    Toast.makeText(SignUp.this, "Signup failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });




        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this,LoginAct.class));
            }
        });

    }
}