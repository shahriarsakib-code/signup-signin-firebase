package com.practices.firebaseprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class SignupResult extends AppCompatActivity {

    TextView name_v,email_v,phonenumber_v,continue_v;
    CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_result);

        name_v=findViewById(R.id.name);
        email_v=findViewById(R.id.email);
        phonenumber_v=findViewById(R.id.number);
        continue_v=findViewById(R.id.continue_text);

        checkbox=findViewById(R.id.checkbox);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String phoneNumber = intent.getStringExtra("phonenumber");

        name_v.setText(name);
        email_v.setText(email);
        phonenumber_v.setText(phoneNumber);

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ischecked = ((CheckBox)v).isChecked();

                if (ischecked) {
                    continue_v.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(SignupResult.this,MainActivity.class));
                            finish();
                        }
                    });
                }
            }
        });

        continue_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignupResult.this, "Please click on the box..", Toast.LENGTH_SHORT).show();
            }
        });

    }
}