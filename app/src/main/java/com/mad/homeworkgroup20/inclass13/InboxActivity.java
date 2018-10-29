package com.mad.homeworkgroup20.inclass13;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InboxActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String TAG = "test";
    final FirebaseApi caller = new FirebaseApi(this);
    ArrayList<Message> data= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
        setTitle("Inbox");
        ImageButton newMail= findViewById(R.id.ComposeNew);
        ImageButton logout = findViewById(R.id.logoutButton);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(InboxActivity.this, LoginActivity.class);
                //  intent.putExtra(USER_KEY,new AppUser(user.getUid(),user.getDisplayName(),user.getEmail()));
                startActivity(intent);
                finish();
            }
        });
        newMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(InboxActivity.this, ComposeMessageActivity.class);
                 intent.putExtra(FirebaseApi.USER_KEY,"");
                startActivity(intent);

            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter=new MessageAdapter(data);
        mRecyclerView.setAdapter(mAdapter);
        getMessages();
    }

    public void getMessages()
    { DatabaseReference threadRef= caller.mDatabase.getReference("mailbox/"+caller.mAuth.getCurrentUser().getDisplayName());
        threadRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                data.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Message post = postSnapshot.getValue(Message.class);
                    data.add(post);
                }
                //     Log.d(TAG, "getThread: "+ newtread);
                Log.d(TAG, "Value is: " + data.size());
                //threads.add(newtread);

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
}
}

