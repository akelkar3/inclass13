package com.mad.homeworkgroup20.inclass13;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    Activity activity;
    EditText email, password;
    Button login, newUser;
    String emailString, passwordString;
    private static final String TAG = "test";
    final FirebaseApi caller = new FirebaseApi(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
        setTitle("MessageMe!");


        email=findViewById(R.id.etEmail1);
        password=findViewById(R.id.editTextPass);
        login=findViewById(R.id.buttonLogin);
        newUser=findViewById(R.id.buttonSignUp1);


        activity=this.getParent();
        //create api object pass activity
        final FirebaseApi apicalls = new FirebaseApi(this);

        //login functionality
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    emailString=email.getText().toString();
                    passwordString=password.getText().toString();
                    //call respective firebase method
                    apicalls.login(emailString, passwordString);
                }catch (Exception e){
                    Toast.makeText(getBaseContext(), "Email and Password can't be empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //signup functionality
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void activityRedirect(){

    }
}
