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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class AccountSettingActivity extends AppCompatActivity {
    private DatabaseReference mUserDatabase,mSettingDatabase;
    private Button saveSetting;
    private EditText name,mail,tc,wallet;
    private String Sname,Smail,Stc,Swallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);
        Intent intent=getIntent();
        String currentUser=intent.getStringExtra("uid");

        // xml baglanti

        saveSetting=findViewById(R.id.setting_save);
        name=findViewById(R.id.settin_name);
        mail=findViewById(R.id.set_eposta);
        tc=findViewById(R.id.setting_tc);
        wallet=findViewById(R.id.setting_key);



        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("kullanicilar").child(currentUser);
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Sname=dataSnapshot.child("isim").getValue().toString();
                Stc=dataSnapshot.child("tc").getValue().toString();
                Smail=dataSnapshot.child("email").getValue().toString();
                Swallet=dataSnapshot.child("hesapkimligi").getValue().toString();

                name.setText(Sname);
                mail.setText(Smail);
                tc.setText(Stc);
                wallet.setText(Swallet);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

            saveSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Sname=name.getText().toString();
                    Stc=tc.getText().toString();
                    Smail=mail.getText().toString();
                    Swallet=wallet.getText().toString();

                    Guncelle(Sname,Smail,Stc,Swallet);

                }
            });


    }

    private void Guncelle(String sname, String smail, String stc, String swallet) {

        Map updateMap=new HashMap();
        updateMap.put("isim",sname);
        updateMap.put("email",smail);
        updateMap.put("tc",stc);
        updateMap.put("hesapkimligi",swallet);

        mUserDatabase.setValue(updateMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Intent mainIntent = new Intent(AccountSettingActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
            }
        });


    }
}
