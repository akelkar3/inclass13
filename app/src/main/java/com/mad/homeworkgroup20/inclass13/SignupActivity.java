package com.mad.homeworkgroup20.inclass13;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignupActivity extends AppCompatActivity {
    EditText confirmPassword;
    String first_name, lastname, emailadd, password, confirmPass;
    Button signUp, cancel;
    private static final String TAG = "test";
    final FirebaseApi caller = new FirebaseApi(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
        setTitle("MessageMe! (Sign Up)");

        final TextView fname = findViewById(R.id.editTextFName);
        final TextView lname = findViewById(R.id.editTextLName);
        final TextView email = findViewById(R.id.etEmail1);
        final TextView pass = findViewById(R.id.editTextPass2);
        confirmPassword = findViewById(R.id.editTextConPass);
        signUp=findViewById(R.id.buttonSignup2);
        cancel=findViewById(R.id.buttonCancel);

        //Signup button Functionality
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first_name = fname.getText().toString();
                lastname = lname.getText().toString();
                emailadd = email.getText().toString();
                password = pass.getText().toString();
                confirmPass = confirmPassword.getText().toString();

                    //call signUp api
                    caller.SignUp(emailadd,password,first_name,lastname);

            }
        });

        //cancel button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
