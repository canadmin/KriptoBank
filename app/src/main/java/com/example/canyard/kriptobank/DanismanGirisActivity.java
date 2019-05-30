package com.example.canyard.kriptobank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DanismanGirisActivity extends AppCompatActivity {
    private EditText kAdi,kSifre;
    private Button giris,kayit;
    private FirebaseAuth firebaseAuth;
    private String kadiString,ksifreString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danisman_giris);
        kAdi=findViewById(R.id.danismanKadi);
        kSifre=findViewById(R.id.danismansifre);

        giris=findViewById(R.id.danismanGiris);
        kayit=findViewById(R.id.danismanKayit);
        firebaseAuth=FirebaseAuth.getInstance();

        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kadiString=kAdi.getText().toString();
                ksifreString=kSifre.getText().toString();

                GirisYap(kadiString,ksifreString);
            }
        });

        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(),DanismanKayitActivity.class));

            }
        });

    }


    private void GirisYap(String kadiString1,String ksifreString1){

    firebaseAuth.signInWithEmailAndPassword(kadiString1,ksifreString1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()){
                Intent danismanIntent=new Intent(DanismanGirisActivity.this,DanismanActivity.class);
                startActivity(danismanIntent);
                finish();
            }
            else{
                Toast.makeText(DanismanGirisActivity.this, "Giris Yapilamadi", Toast.LENGTH_SHORT).show();
            }
        }
    });

    }
}
