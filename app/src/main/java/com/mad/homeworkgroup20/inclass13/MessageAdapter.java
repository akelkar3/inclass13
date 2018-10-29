package com.mad.homeworkgroup20.inclass13;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ankit on 4/25/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    ArrayList<Message> mData;

    public MessageAdapter(ArrayList<Message> mData) {
        this.mData = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Message message= mData.get(position);

        holder.textViewUserName.setText(message.From);
       // holder.textViewDatenTime.setText(message.date+","+message.time);
        holder.textViewMsgPreview.setText(message.Text);
        PrettyTime p = new PrettyTime();
        Date date = null;
        try {
            if ( message.Date!=null&& message.Date !="" )
                //Log.d("test", "getView: createdAt "+ threadItem.created_at);
                date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(message.Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.textViewDatenTime.setText(p.format( date));

        holder.btnRead.setEnabled(!message.IsRead);
        holder.btnRead.setSelected(!message.IsRead);
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(holder.itemView.getContext(), ReadMessageActivity.class);
         intent.putExtra(FirebaseApi.MESSAGE_KEY,message);
        holder.itemView.getContext().startActivity(intent);
    }
});
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewUserName, textViewDatenTime, textViewMsgPreview;
        Button btnRead;
        public ViewHolder(final View itemView) {
            super(itemView);

            textViewUserName= itemView.findViewById(R.id.textViewReadUserName);
            textViewDatenTime= itemView.findViewById(R.id.textViewDatenTime);
            textViewMsgPreview= itemView.findViewById(R.id.textViewMsgPreview);
            btnRead=itemView.findViewById(R.id.radioButton);

        }

    }

}
