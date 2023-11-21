package com.practices.firebaseprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
public class ForgotAct extends AppCompatActivity {

    TextInputEditText email_v;
    TextView resetpass_text;
    ProgressBar progressBar;
    RelativeLayout resetpass_l;
    ImageView backimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);


        email_v=findViewById(R.id.email_edit);
        progressBar=findViewById(R.id.progressbar);
        resetpass_l=findViewById(R.id.resetpass_layout);
        resetpass_text=findViewById(R.id.resetpass_text);
        backimg=findViewById(R.id.back_image);


        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        resetpass_l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                resetpass_text.setVisibility(View.GONE);

                String email = email_v.getText().toString().trim();
                if (email.isEmpty()) {
                    email_v.setError("Please enter email");
                    return;
                }

                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "Email sent.");
                                    Toast.makeText(ForgotAct.this, "sent..", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ForgotAct.this,LoginAct.class));
                                    finish();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                progressBar.setVisibility(View.GONE);
                                resetpass_text.setVisibility(View.VISIBLE);
                                Toast.makeText(ForgotAct.this, "Failed..", Toast.LENGTH_SHORT).show();
                            }
                        });



            }
        });



    }
}