package com.example.canyard.kriptobank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DanismanKayitActivity extends AppCompatActivity {
    private EditText kullaniciAdi,kullaniciSifre,kullaniciEposta;
    private Button kayitOl;
    private String kadi,sifre,eposta;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danisman_kayit);

        kullaniciAdi=findViewById(R.id.danisman_kayit_kadi);
        kullaniciSifre=findViewById(R.id.danisman_kayit_sifre);
        kullaniciEposta=findViewById(R.id.danisman_kayit_eposta);

        kayitOl=findViewById(R.id.danisman_kayit_ol);
        firebaseAuth=FirebaseAuth.getInstance();

        kayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kadi=kullaniciAdi.getText().toString();
                sifre=kullaniciSifre.getText().toString();
                eposta=kullaniciEposta.getText().toString();

                kayitOl(kadi,sifre,eposta);
            }
        });
    }

    private void kayitOl(final String kadi, String sifre, String eposta) {
        firebaseAuth.createUserWithEmailAndPassword(eposta,sifre).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
                    String uid=currentUser.getUid();
                    databaseReference=FirebaseDatabase.getInstance().getReference().child("danismanlar").child(uid);

                    HashMap<String ,String> danismanMap=new HashMap<>();
                    danismanMap.put("kullaniciAdi",kadi);

                    databaseReference.setValue(danismanMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Intent mainIntent=new Intent(DanismanKayitActivity.this,DanismanActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                    });


                }
            }
        });
    }
}
