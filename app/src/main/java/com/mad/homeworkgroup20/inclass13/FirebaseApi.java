package com.mad.homeworkgroup20.inclass13;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

/**
 * Created by Aliandro on 4/23/2018.
 */

public class FirebaseApi {
   public static final String USER_KEY="user";
    public static final String MESSAGE_KEY="message";
    public Activity activity;
    final String TAG = "test";
    public FirebaseAuth mAuth;
    public FirebaseDatabase mDatabase;


    public FirebaseApi(Activity activity) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }

    public void login(String username, String password) {
        Log.d(TAG, "login: "+username + " "+ password);
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            SharedPreferences sharedPref =  activity.getSharedPreferences(
                                    "mypref", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            //saving user full name and user Id that might require on threads or messages activity
                            Log.d("tesetdelete", "saveToken: "+  user.getUid()+" name: "+user.getDisplayName());
                            editor.putString("uid", user.getUid());
                            editor.putString("userName",user.getDisplayName());
                            editor.putString("userMail", user.getEmail());
                            editor.apply();
                            //write intent to switch new activity

                            // TODO: 4/23/2018 change SignupActivity to the Inbox activity
                            Intent intent=new Intent(activity, InboxActivity.class);
                          //  intent.putExtra(USER_KEY,new AppUser(user.getUid(),user.getDisplayName(),user.getEmail()));
                            activity.startActivity(intent);
                            activity.finish();
                            Toast.makeText(activity, "Login Successful", Toast.LENGTH_SHORT).show();
                            //   updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Login Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //   updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void SignUp(final String username, final String password, final String fname, final String lname) {
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            final FirebaseUser user = mAuth.getCurrentUser();
                            UserProfileChangeRequest prof = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(fname + " " + lname).build();
                            user.updateProfile(prof)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @RequiresApi(api = Build.VERSION_CODES.N)
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated." + mAuth.getCurrentUser().getDisplayName());
                                                addAppUser(user.getDisplayName(),user.getEmail());
                                                login(username, password);
                                            }else {
                                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                                Toast.makeText(activity, "Login Authentication failed.",
                                                        Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    });

                            //   updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                        // ...
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addMessage(String message, String toUser) {
       // toUser= mAuth.getCurrentUser().getDisplayName();
        DatabaseReference threadRef = mDatabase.getReference("mailbox").child(toUser);
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todayString = formatter.format(todayDate);
        Message newMessage = new Message(message,todayString
                ,mAuth.getCurrentUser().getDisplayName(),mAuth.getCurrentUser().getUid(),mAuth.getCurrentUser().getEmail(),toUser,false);

        newMessage.Id = threadRef.push().getKey();
        threadRef.child(newMessage.Id).setValue(newMessage);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addAppUser(String username, String useremail) {
        DatabaseReference threadRef = mDatabase.getReference("users");
        AppUser user = new AppUser("0",username,useremail);
        user.UserID = threadRef.push().getKey();
        threadRef.child(user.UserID).setValue(user);
    }

    public  void deleMessage(String messageId){
        DatabaseReference threadRef= mDatabase.getReference("mailbox/"+mAuth.getCurrentUser().getDisplayName());
        threadRef.child(messageId).removeValue();
        Intent intent=new Intent(activity, InboxActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }
    public  void messageRead(String messageId){
        DatabaseReference threadRef= mDatabase.getReference("mailbox/"+mAuth.getCurrentUser().getDisplayName());
        threadRef.child(messageId+"/IsRead").setValue(true);

    }
}


