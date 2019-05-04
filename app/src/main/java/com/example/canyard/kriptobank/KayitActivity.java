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
import android.widget.Toast;

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
    private EditText kayitİsim,kayitPosta,kayitSifre,tcno;
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
        tcno=findViewById(R.id.tc_no);
        kayitButon=findViewById(R.id.kayit_buton);
        firebaseAuth=FirebaseAuth.getInstance();
        kayitButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cuzdanNo=cuzdanNoOlustur();
                String isim=kayitİsim.getText().toString();
                String posta=kayitPosta.getText().toString();
                String sifre=kayitSifre.getText().toString();
                String tc_no=tcno.getText().toString();
                if(!TextUtils.isEmpty(cuzdanNo)&&!TextUtils.isEmpty(isim)&&!TextUtils.isEmpty(posta)&&!TextUtils.isEmpty(sifre)&&!TextUtils.isEmpty(tc_no)&&tc_no.length()==11){
                   kayitOlustur(cuzdanNo,isim,posta,sifre,tc_no);
                }else{
                    Toast.makeText(KayitActivity.this, "Bilgilerinizi Kontrol ediniz", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private String cuzdanNoOlustur(){
        String cuzdanNo="";
        Random rast=new Random();

        for(int i=0;i<=10;i++){
            int sayi=rast.nextInt(25)+97;
            cuzdanNo+=(char)sayi;
        }

        return cuzdanNo;
    }

    private void kayitOlustur(final String cuzdanKey, final String ad, final String eposta, String sifre, final String tc_no){
        firebaseAuth.createUserWithEmailAndPassword(eposta,sifre)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
                            String uid=currentUser.getUid();
                            databaseReference=FirebaseDatabase.getInstance().getReference().child("kullanicilar").child(uid);
                            HashMap<String,String>kullaniciMap=new HashMap<>();
                            kullaniciMap.put("email",eposta);
                            kullaniciMap.put("isim",ad);
                            kullaniciMap.put("hesapkimligi",cuzdanKey);
                            kullaniciMap.put("tc",tc_no);

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
