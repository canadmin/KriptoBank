package com.example.canyard.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.canyard.model.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.canyard.kriptobank.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private FirebaseUser uCurrentUser;
    private List<Messages> messagesList;

    public MessageAdapter(List<Messages> messagesList) {
        this.messagesList = messagesList;
    }

    @NonNull
    @Override
    public MessageAdapter.MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_single_layout,parent,false);

        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT
        );
        uCurrentUser=FirebaseAuth.getInstance().getCurrentUser();
        final String current_uid=uCurrentUser.getUid();
        Messages c=messagesList.get(position);
        String fromUser=c.getFrom();
        if (fromUser.equals(current_uid)) {
            params.gravity = Gravity.RIGHT;
            holder.messageText.setLayoutParams(params);
            holder.messageText.setBackgroundResource(R.drawable.karsi_baloncuk);
            holder.messageText.setTextColor(Color.BLACK);
        } else {
            params.gravity = Gravity.LEFT;
            holder.messageText.setLayoutParams(params);
            holder.messageText.setBackgroundResource(R.drawable.benim_baloncuk);
            holder.messageText.setTextColor(Color.BLACK);
            holder.messageText.setGravity(Gravity.RIGHT | Gravity.END);
        }
        holder.messageText.setText(c.getMessage());



    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder{
        public TextView messageText;

        public MessageViewHolder(View itemView) {
            super(itemView);
            messageText=itemView.findViewById(R.id.message_text_layout);
        }
    }
}

