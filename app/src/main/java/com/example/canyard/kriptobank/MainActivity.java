package com.example.canyard.kriptobank;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth firebaseAuth;
    private TextView account_name,account_key;
    private FirebaseUser firebaseUser;
    private DatabaseReference mUsersDatabase;
   /* private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView navigationView;*/
    String userName,userKey;
    String currentUID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String currentUser=FirebaseAuth.getInstance().getUid();
        currentUID=currentUser;

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        account_name=findViewById(R.id.acc_ad);
        account_key=findViewById(R.id.acc_key);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

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
    protected void onStart() {
        super.onStart();
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if (currentUser==null){
            startActivity(new Intent(MainActivity.this,GirisActivity.class));
        }
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

        }
        if (id == R.id.kartBilgileri) {

        }

         if (id == R.id.kartEkle) {

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
}
