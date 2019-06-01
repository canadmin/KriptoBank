package com.example.canyard.kriptobank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.canyard.adapter.MessageAdapter;
import com.example.canyard.model.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {


    private RecyclerView mMessageList;
    private final List<Messages> messagesList=new ArrayList<>();
    private LinearLayoutManager mlinearLayout;
    private DatabaseReference mMessageDatabase;

    private String mchatUser;
    private ImageButton mChatSendBtn;
    private EditText mChatMessageView;

    private TextView mTitleView;

    private String mCurrentUserId;

    private MessageAdapter mAdapter;
    private Toolbar mChatToolBar;
    private DatabaseReference mRootRef;
    private String userName;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.chatbar);;
        setSupportActionBar(toolbar);

        Intent gelen=getIntent();
        String baslik=gelen.getStringExtra("isim");
        getSupportActionBar().setTitle(baslik.toString());



        mRootRef= FirebaseDatabase.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        mchatUser=getIntent().getStringExtra("user_id");



        mCurrentUserId=mAuth.getCurrentUser().getUid();


        mChatSendBtn=(ImageButton)findViewById(R.id.chat_send_btn);
        mChatMessageView=(EditText)findViewById(R.id.chat_message_view);
        mMessageList=(RecyclerView)findViewById(R.id.messages_list);
        mlinearLayout=new LinearLayoutManager(this);
        mMessageList.setHasFixedSize(true);
        mMessageList.setLayoutManager(mlinearLayout);

        mAdapter=new MessageAdapter(messagesList);


        mMessageList.setAdapter(mAdapter);

        loadMessages();

        mChatSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();

            }
        });


    }

    private void loadMessages() {

        mRootRef.child("messages").child(mCurrentUserId).child(mchatUser).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Messages message1=dataSnapshot.getValue(Messages.class);
                messagesList.add(message1);
                mAdapter.notifyDataSetChanged();
                mMessageList.smoothScrollToPosition(mMessageList.getAdapter().getItemCount());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage() {
        String message=mChatMessageView.getText().toString();

        if(!TextUtils.isEmpty(message)){

            String current_user_ref="messages/"+mCurrentUserId+ "/" +mchatUser;
            String chat_user_Ref="messages/"+mchatUser+"/"+mCurrentUserId;

            DatabaseReference user_message_push=mRootRef.child("messages").child(mCurrentUserId).child(mchatUser).push();
            String push_id=user_message_push.getKey();

            Map messageMap=new HashMap();
            messageMap.put("message",message);
            messageMap.put("type","text");
            messageMap.put("from",mCurrentUserId);


            Map messageUserMap=new HashMap();
            messageUserMap.put(current_user_ref+"/"+push_id,messageMap);
            messageUserMap.put(chat_user_Ref+"/"+push_id,messageMap);


            mChatMessageView.setText("");

            mRootRef.updateChildren(messageUserMap, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError!=null) {

                        Log.d("CHAT LOG", databaseError.getMessage().toString());
                    }
                }
            });



        }
    }
}
