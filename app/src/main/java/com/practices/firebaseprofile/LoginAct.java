package com.practices.firebaseprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import android.app.AlertDialog;
public class LoginAct extends AppCompatActivity {

    TextView signuptxt_v,logintext_v;
    RelativeLayout loginl_v,forgotl_v;
    private FirebaseAuth mAuth;
    TextInputEditText email_v,pass_v;
    ProgressBar progressBar_v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        signuptxt_v=findViewById(R.id.signup_txt);
        loginl_v=findViewById(R.id.login_layout);
        forgotl_v=findViewById(R.id.forgot_l);
        email_v=findViewById(R.id.email_edit);
        pass_v=findViewById(R.id.pass_edit);
        logintext_v=findViewById(R.id.login_text);
        progressBar_v=findViewById(R.id.progressbar);


        loginl_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_v.getText().toString();
                String pass = pass_v.getText().toString();

                if (email.isEmpty()) {
                    email_v.setError("enter valid email");
                    return;
                }
                if (pass.isEmpty()) {
                    pass_v.setError("enter your password");
                    return;
                }

                progressBar_v.setVisibility(View.VISIBLE);
                logintext_v.setVisibility(View.GONE);
                // Use Firebase Authentication to sign in
                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    // Sign in success
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent intent = new Intent(LoginAct.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    progressBar_v.setVisibility(View.GONE);
                                    logintext_v.setVisibility(View.VISIBLE);
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(LoginAct.this, "Authentication failed: ", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


        signuptxt_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginAct.this,SignUp.class));
            }
        });
        forgotl_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAct.this,ForgotAct.class));
            }
        });

    }
}