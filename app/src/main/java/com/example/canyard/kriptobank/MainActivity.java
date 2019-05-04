package com.example.canyard.kriptobank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canyard.model.Card;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth firebaseAuth;
    private TextView account_name,account_key;
    private FirebaseUser firebaseUser;
    private DatabaseReference mUsersDatabase,mCardsDatabase;
   /* private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;*/
   private String userName,userKey;
    private String currentUID;
    private RecyclerView mUserlist;

    private  DatabaseReference cardDatabaseReferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String currentUser=FirebaseAuth.getInstance().getUid();
        currentUID=currentUser;
        mUserlist=findViewById(R.id.card_list);mUserlist.setHasFixedSize(true);
        mUserlist.setLayoutManager(new LinearLayoutManager(this));

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        account_name=findViewById(R.id.acc_ad);
        account_key=findViewById(R.id.acc_key);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mCardsDatabase=FirebaseDatabase.getInstance().getReference().child("cards");
        cardDatabaseReferences =FirebaseDatabase.getInstance().getReference().child("cards");

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("kullanicilar").child(currentUser);
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName=dataSnapshot.child("isim").getValue().toString();
                userKey=dataSnapshot.child("hesapkimligi").getValue().toString();

                account_key.setText(userKey);
                account_name.setText(userName);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listToCard();

    }

    private void listToCard() {
        FirebaseRecyclerOptions options=new FirebaseRecyclerOptions.Builder<Card>()
                .setQuery(cardDatabaseReferences.child(currentUID),Card.class).build();


        FirebaseRecyclerAdapter<Card,CardsVievHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Card, CardsVievHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CardsVievHolder holder, int position, @NonNull final Card model) {

                String cardId=getRef(position).getKey();
                final String list_card_id=getRef(position).getKey();
               holder.cardNumber.setText(model.getNumber().toString());
                holder.cardOwner.setText(model.getCardOwner().toString());
                cardDatabaseReferences.child(list_card_id).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        holder.cardNumber.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent cardIntent=new Intent(getApplicationContext(),KardSpesActivity.class);
                                cardIntent.putExtra("userID",currentUID);
                                cardIntent.putExtra("cardOwner",model.getCardOwner());
                                cardIntent.putExtra("cardNumber",model.getNumber());
                                cardIntent.putExtra("accountKey",userKey);
                                cardIntent.putExtra("cardBalance",model.getBalance());
                                startActivity(cardIntent);

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @NonNull
            @Override
            public CardsVievHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_custom,parent,false);
                CardsVievHolder vievHolder=new CardsVievHolder(v);
                return vievHolder;
            }


        };
        mUserlist.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.exit) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this,GirisActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.transfer) {
            Intent intent=new Intent(getApplicationContext(),TrasferActivity.class);
            intent.putExtra("userKey",userKey);
            startActivity(intent);

        }
        if (id == R.id.dokumler) {

        }
        if (id == R.id.ozetler) {

        }
        if (id == R.id.transferBilgileri) {

        }
        if (id == R.id.yardımAl) {

        }
        if (id == R.id.hesapBilgileri) {

        }         if (id == R.id.kartEkle) {

            Intent addCardIntent=new Intent(MainActivity.this,KartEkleActivity.class);
            addCardIntent.putExtra("userName",userName);
            addCardIntent.putExtra("userKey",userKey);
            addCardIntent.putExtra("currentuid",currentUID);
            startActivity(addCardIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    //Holder

    public static class CardsVievHolder extends RecyclerView.ViewHolder{
        TextView cardOwner;
        TextView cardNumber;

        public CardsVievHolder(View itemView) {
            super(itemView);
            cardNumber=itemView.findViewById(R.id.cardNumber_single);
            cardOwner=itemView.findViewById(R.id.cardOwner_single);

        }
    }
}



