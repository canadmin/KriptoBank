package com.example.canyard.kriptobank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class KartEkleActivity extends AppCompatActivity {

    private Button addCard;
    private EditText cardOwner,cardBalance,cardNumber;
    String userName,userKey,currentUID;
    FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kart_ekle);

        addCard=findViewById(R.id.accAddCard);
        cardOwner=findViewById(R.id.cardOwner);
        cardBalance=findViewById(R.id.cardBalance);
        cardNumber=findViewById(R.id.cardNumber);
        Intent ıntent=getIntent();
        userName=ıntent.getStringExtra("userName").toUpperCase();
        userKey=ıntent.getStringExtra("userKey");
        currentUID=ıntent.getStringExtra("currentuid");
        cardOwner.setText(userName);
        firebaseAuth=FirebaseAuth.getInstance();

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            userName=cardOwner.getText().toString();
            String balance=cardBalance.getText().toString();
            String number=cardNumber.getText().toString();

                if(!TextUtils.isEmpty(balance)&&!TextUtils.isEmpty(number)){
                    kartEkle(userName,userKey,currentUID,balance,number);
                }

            }
        });

    }

    private void kartEkle(String userName, String userKey, String currentUID, String balance, String number) {
        databaseReference=FirebaseDatabase.getInstance().getReference().child("cards").child(currentUID).child(number);
        HashMap<String,String> cardMap=new HashMap<>();
        cardMap.put("cardOwner",userName);
        cardMap.put("hesapkimligi",userKey);
        cardMap.put("balance",balance);
        cardMap.put("uid",currentUID);
        cardMap.put("number",number);
        databaseReference.setValue(cardMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Intent mainIntent=new Intent(KartEkleActivity.this,MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }
            }
        });

    }
}
