package com.example.canyard.kriptobank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.canyard.adapter.MovementsAdapter;
import com.example.canyard.model.CardMovements;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class KardSpesActivity extends AppCompatActivity {
    private RecyclerView movementssList;
    private List<CardMovements> cardMovementsList=new ArrayList<>();
    private MovementsAdapter movementsAdapter;
    private TextView cardBalance,cardNumber,cardOwner;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kard_spes);
        movementssList=findViewById(R.id.spesCard_movements);
        cardBalance=findViewById(R.id.spesCardBalance);
        cardNumber=findViewById(R.id.spesCardNumber);
        cardOwner=findViewById(R.id.spesCardOwner);
        Intent cardIntent=getIntent();
        String userId=cardIntent.getStringExtra("userID");
        String intCardOwner=cardIntent.getStringExtra("cardOwner");
        String intCardNumber=cardIntent.getStringExtra("cardNumber");
        String intAccountKey=cardIntent.getStringExtra("accountKey");
        String intCardBalance=cardIntent.getStringExtra("cardBalance");

        cardBalance.setText(intCardBalance);
        cardOwner.setText(intCardOwner);
        cardNumber.setText(intCardNumber);

        movementsAdapter=new MovementsAdapter(cardMovementsList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        movementssList.setLayoutManager(layoutManager);
        movementssList.setItemAnimator(new DefaultItemAnimator());
        movementssList.setAdapter(movementsAdapter);

        dokumleriAl();



    }

    private void dokumleriAl() {

        CardMovements cardMovements=new CardMovements("Migros","7 Tl","-");
        cardMovementsList.add(cardMovements);
        CardMovements cardMovements2=new CardMovements("Iyas market","10 Tl","-");

        cardMovementsList.add(cardMovements2);


        movementsAdapter.notifyDataSetChanged();
    }
}
