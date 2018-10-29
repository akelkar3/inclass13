package com.mad.homeworkgroup20.inclass13;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ComposeMessageActivity extends AppCompatActivity {
    private static final String TAG = "test";
    final FirebaseApi caller = new FirebaseApi(this);
    ArrayList<AppUser> userlist= new ArrayList<>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);
        Button send = findViewById(R.id.sendMessage);
        final TextView tvname = findViewById(R.id.ToName);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setTitle("Compose Message");

      String replyTo=  getIntent().getExtras().getString(FirebaseApi.USER_KEY);
        if(replyTo!=null&&!replyTo.isEmpty())
            tvname.setText(replyTo);
        ImageButton but = findViewById(R.id.imageButton5);
        getUsers();
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(ComposeMessageActivity.this);
                builder.setTitle("Contacts");

                final ArrayList<String> displayValues=new ArrayList<>();

                for (AppUser entity : userlist) {
                    displayValues.add(entity.UserName);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,displayValues);

                builder.setSingleChoiceItems(displayValues.toArray(new String[displayValues.size()]), 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tvname.setText(displayValues.get(which));
                        dialog.dismiss();

                    }
                });
           final   AlertDialog dialog= builder.show();
            }
        });



    send.setOnClickListener(new View.OnClickListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onClick(View view) {
            TextView text = findViewById(R.id.editTextComposeMsg);
            TextView TO = findViewById(R.id.ToName);
            caller.addMessage(text.getText().toString(),TO.getText().toString());
            Intent intent=new Intent(ComposeMessageActivity.this, InboxActivity.class);
            startActivity(intent);
            finish();
        }
    });

    }
    public void getUsers()
    { DatabaseReference threadRef= caller.mDatabase.getReference("users");
        threadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                userlist.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    AppUser post = postSnapshot.getValue(AppUser.class);
                    userlist.add(post);
                }
                //     Log.d(TAG, "getThread: "+ newtread);

                //threads.add(newtread);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}
