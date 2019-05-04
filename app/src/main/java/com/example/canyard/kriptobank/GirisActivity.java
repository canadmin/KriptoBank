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

public class GirisActivity extends AppCompatActivity {
    private Button girisButon,kayitButon;
    private EditText girisMail,girisSifre;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        firebaseAuth=FirebaseAuth.getInstance();
        girisButon=findViewById(R.id.giris_buton);
        kayitButon=findViewById(R.id.yenihesap_buton);
        girisMail=findViewById(R.id.giris_mail);
        girisSifre=findViewById(R.id.giris_sifre);


        girisButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sifre=girisSifre.getText().toString();
                String eposta=girisMail.getText().toString();
                if(!TextUtils.isEmpty(sifre)&&!TextUtils.isEmpty(eposta)){
                    girisYap(eposta,sifre);
                }
            }
        });
        kayitButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GirisActivity.this,KayitActivity.class));
            }
        });





    }
    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if (currentUser!=null){
            startActivity(new Intent(GirisActivity.this,MainActivity.class));
        }
    }

    private void girisYap(String eposta,String sifre){
        firebaseAuth.signInWithEmailAndPassword(eposta,sifre)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser user=firebaseAuth.getCurrentUser();
                        Intent ıntent=new Intent(GirisActivity.this,MainActivity.class);
                        startActivity(ıntent);
                        finish();
                    }else{
                        Log.d("hata",task.getException().toString());
                    }
                    }
                });

    }




}
