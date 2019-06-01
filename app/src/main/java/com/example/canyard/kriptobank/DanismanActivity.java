package com.example.canyard.kriptobank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.canyard.model.Messages;
import com.example.canyard.model.User;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DanismanActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference,mUserDatabase,danismanDatabase;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private String currentUID;
    private FirebaseAuth mAuth;
    private RecyclerView.LayoutManager mLayoutManager;
    private String userName,userKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danisman);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.gelenYardimİstekleri);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        databaseReference =FirebaseDatabase.getInstance().getReference().child("messages");
        String currentUser=FirebaseAuth.getInstance().getUid();
        mAuth=FirebaseAuth.getInstance();
        currentUID=mAuth.getCurrentUser().getUid();
        databaseReference =FirebaseDatabase.getInstance().getReference().child("messages").child(currentUID);
        mUserDatabase=FirebaseDatabase.getInstance().getReference().child("kullanicilar");

        danismanDatabase = FirebaseDatabase.getInstance().getReference().child("danismanlar").child(currentUID);
        danismanDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName=dataSnapshot.child("kullaniciAdi").getValue().toString();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.exit) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(DanismanActivity.this,GirisActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<Messages>()
                .setQuery(databaseReference,Messages.class).build();

        FirebaseRecyclerAdapter<Messages,DanismanViewHolder> danismanlarAdapter=new FirebaseRecyclerAdapter<Messages, DanismanViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final DanismanViewHolder holder, int position, @NonNull final Messages model) {
                final String list_user_id=getRef(position).getKey();
                mUserDatabase.child(list_user_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        final   String ad=dataSnapshot.child("isim").getValue().toString();
                        holder.istekAd.setText(ad);
                        holder.istekAd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent chatIntent=new Intent(getApplicationContext(),ChatActivity.class);
                                chatIntent.putExtra("user_id",list_user_id);
                                chatIntent.putExtra("isim",ad);
                                chatIntent.putExtra("fromUser",userName);
                                startActivity(chatIntent);
                            }
                        });

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public DanismanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.danisman_single,parent,false);
                DanismanActivity.DanismanViewHolder vievHolder=new DanismanActivity.DanismanViewHolder(v);


                return vievHolder;
            }
        };

        recyclerView.setAdapter(danismanlarAdapter);
        danismanlarAdapter.startListening();
    }

    private static class DanismanViewHolder extends RecyclerView.ViewHolder{
        TextView istekAd;
        public DanismanViewHolder(View itemView) {
            super(itemView);
            istekAd=itemView.findViewById(R.id.danismanAdgoster);

        }

    }


}
