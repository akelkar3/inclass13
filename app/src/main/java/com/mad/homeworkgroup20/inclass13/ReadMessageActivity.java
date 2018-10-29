package com.mad.homeworkgroup20.inclass13;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class ReadMessageActivity extends AppCompatActivity {
    private static final String TAG = "test";
    final FirebaseApi caller = new FirebaseApi(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
        final Message data = (Message) getIntent().getExtras().getSerializable(FirebaseApi.MESSAGE_KEY);
        TextView text = findViewById(R.id.textViewMsgText);
        text.setText(data.Text);
        TextView name = findViewById(R.id.textViewReadUserName);
        name.setText(data.From);
        ImageButton back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ReadMessageActivity.this, ComposeMessageActivity.class);
                intent.putExtra(FirebaseApi.USER_KEY,data.From);
                startActivity(intent);
                finish();
            }
        });
        ImageButton delete = findViewById(R.id.deleteMessage);
        if (!data.IsRead)
            caller.messageRead(data.Id);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            caller.deleMessage(data.Id);
            }
        });
    }
}
