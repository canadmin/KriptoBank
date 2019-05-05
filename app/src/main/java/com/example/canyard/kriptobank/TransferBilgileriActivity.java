package com.example.canyard.kriptobank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.canyard.adapter.TransferHistroyAdapter;
import com.example.canyard.model.TransferHistory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TransferBilgileriActivity extends AppCompatActivity {
    private RecyclerView transferGecmisi;
    private List<TransferHistory> historyArrayList=new ArrayList<>();
    private TransferHistroyAdapter transferHistroyAdapter;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference historyDatabaseRef;
    private String userKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_bilgileri);
        transferGecmisi=findViewById(R.id.transferDokuman);
        Intent ıntent=getIntent();
        userKey=ıntent.getStringExtra("userKey");

        transferHistroyAdapter=new TransferHistroyAdapter(historyArrayList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        transferGecmisi.setLayoutManager(layoutManager);
        transferGecmisi.setItemAnimator(new DefaultItemAnimator());
        transferGecmisi.setAdapter(transferHistroyAdapter);
        vericek();

    }

    private void vericek() {
        historyDatabaseRef=FirebaseDatabase.getInstance().getReference().child("transfers").child(userKey);
       historyDatabaseRef.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               String tarih=dataSnapshot.child("tarih").getValue().toString();
               String tur=dataSnapshot.child("tur").getValue().toString();
               String tutar=dataSnapshot.child("tutar").getValue().toString();


               TransferHistory transferHistory=new TransferHistory(tarih,tur,tutar);
               historyArrayList.add(transferHistory);
               transferHistroyAdapter.notifyDataSetChanged();

           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }
}
