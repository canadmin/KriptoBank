package com.example.canyard.kriptobank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TrasferActivity extends AppCompatActivity {
    private EditText transferHesap, transferTutar;
    private Button transferYap;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String hedefKey, benimKey, tur, transferBakiye;
    private DatabaseReference mRootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasfer);
        transferHesap = findViewById(R.id.transferHesap);
        transferTutar = findViewById(R.id.transferTutar);
        transferYap = findViewById(R.id.transferYap1);
        Intent ıntent = getIntent();
        benimKey = ıntent.getStringExtra("userKey");
        tur = "giden";
        mRootRef = FirebaseDatabase.getInstance().getReference();

        transferYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hedefKey = transferHesap.getText().toString();
                transferBakiye = transferTutar.getText().toString();
                trasnfer(benimKey, hedefKey, transferBakiye, tur);
            }
        });

    }

    private void trasnfer(String benimKey, String hedefKey, String transferBakiye, String tur) {

        databaseReference = FirebaseDatabase.getInstance().getReference().child("transfers").child(benimKey).push();
        String push_id = databaseReference.getKey();
        Map map = new HashMap<>();
        map.put("alanHesap", hedefKey);
        map.put("gonderenHesap", benimKey);
        map.put("tutar", transferBakiye);
        map.put("tur", tur);
        map.put("tarih", ServerValue.TIMESTAMP);

        Map map2 = new HashMap<>();
        map2.put("alanHesap", hedefKey);
        map2.put("gonderenHesap", benimKey);
        map2.put("tutar", transferBakiye);
        map2.put("tur", "gelen");
        map2.put("tarih", ServerValue.TIMESTAMP);

        Map transferMap = new HashMap();
        transferMap.put(benimKey + "/" + push_id, map);

        transferMap.put(hedefKey + "/" + push_id, map2);

        mRootRef.child("transfers").updateChildren(transferMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                Intent mainIntent = new Intent(TrasferActivity.this, MainActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            }
        });

    }
}
