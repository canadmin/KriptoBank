package com.example.canyard.kriptobank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import java.util.Random;

public class KayitActivity extends AppCompatActivity {
    private EditText kayitİsim,kayitPosta,kayitSifre;
    private FirebaseAuth firebaseAuth;
    private Button kayitButon;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        kayitİsim=findViewById(R.id.kayit_isim);
        kayitPosta=findViewById(R.id.kayit_mail);
        kayitSifre=findViewById(R.id.kayit_sifre);
        kayitButon=findViewById(R.id.kayit_buton);
        firebaseAuth=FirebaseAuth.getInstance();
        kayitButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cuzdanNo=cuzdanNoOlustur();
                String isim=kayitİsim.getText().toString();
                String posta=kayitPosta.getText().toString();
                String sifre=kayitSifre.getText().toString();
                if(!TextUtils.isEmpty(cuzdanNo)&&!TextUtils.isEmpty(isim)&&!TextUtils.isEmpty(posta)&&!TextUtils.isEmpty(sifre)){
                   kayitOlustur(cuzdanNo,isim,posta,sifre);
                }
            }
        });

    }

    private String cuzdanNoOlustur(){
        String cuzdanNo="";
        Random rast=new Random();

        for(int i=0;i<=10;i++){
            int sayi=rast.nextInt(57)+65;
            cuzdanNo+=(char)sayi;
        }

        return cuzdanNo;
    }

    private void kayitOlustur(final String cuzdanKey, final String ad, String eposta, String sifre){
        firebaseAuth.createUserWithEmailAndPassword(eposta,sifre)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
                            String uid=currentUser.getUid();
                            databaseReference=FirebaseDatabase.getInstance().getReference().child("kullanicilar").child(uid);
                            HashMap<String,String>kullaniciMap=new HashMap<>();
                            kullaniciMap.put("isim",ad);
                            kullaniciMap.put("hesapkimligi",cuzdanKey);

                            databaseReference.setValue(kullaniciMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Intent mainIntent=new Intent(KayitActivity.this,MainActivity.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainIntent);
                                    finish();
                                }
                                }
                            });
                        }else{
                            Log.d("Hata",task.getException().toString());
                        }
                    }
                });
    }
}
