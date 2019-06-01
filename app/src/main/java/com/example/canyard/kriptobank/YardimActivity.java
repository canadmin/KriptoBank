package com.example.canyard.kriptobank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canyard.model.Card;
import com.example.canyard.model.Danisman;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class YardimActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private String currentUID;

    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yardim);

        recyclerView=findViewById(R.id.danismanGoster);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        databaseReference =FirebaseDatabase.getInstance().getReference().child("danismanlar");
        String currentUser=FirebaseAuth.getInstance().getUid();
        currentUID=currentUser;



    }


    @Override
    public void onStart() {
        super.onStart();
        databaseReference =FirebaseDatabase.getInstance().getReference().child("danismanlar");

        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<Danisman>()
                .setQuery(databaseReference,Danisman.class).build();
        FirebaseRecyclerAdapter<Danisman,DanismanViewHolder> danismanlarAdapter=new FirebaseRecyclerAdapter<Danisman, DanismanViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull DanismanViewHolder holder, int position, @NonNull Danisman model) {
               // String danismanId=getRef(position).getKey();
               final String danismanListId=getRef(position).getKey();
                holder.danismanad.setText(model.getKullaniciAdi());
                final String danismanIsmi=model.getKullaniciAdi();
                holder.danismanad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent chatIntent=new Intent(getApplicationContext(),ChatActivity.class);
                        chatIntent.putExtra("user_id",danismanListId);
                        chatIntent.putExtra("isim",danismanIsmi);
                        startActivity(chatIntent);

                    }
                });
            }

            @NonNull
            @Override
            public YardimActivity.DanismanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.danisman_single,parent,false);
                YardimActivity.DanismanViewHolder vievHolder=new YardimActivity.DanismanViewHolder(v);
                return vievHolder;
            }



        };
        recyclerView.setAdapter(danismanlarAdapter);
        danismanlarAdapter.startListening();
    }

    public static class DanismanViewHolder extends RecyclerView.ViewHolder{
        TextView danismanad;
        public DanismanViewHolder(View itemView) {
            super(itemView);
            danismanad=itemView.findViewById(R.id.danismanAdgoster);

        }
    }
}
