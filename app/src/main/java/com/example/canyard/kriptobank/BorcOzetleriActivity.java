package com.example.canyard.kriptobank;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.canyard.adapter.BorclarAdapter;
import com.example.canyard.model.Borclar;

import java.util.ArrayList;
import java.util.List;

public class BorcOzetleriActivity extends AppCompatActivity {



    private RecyclerView borcOzetleriView;
    private List<Borclar> borcOzetleriList=new ArrayList<>();
    private BorclarAdapter borclarAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borc_ozetleri);

        borcOzetleriView=findViewById(R.id.borcOzetleriadapter);
        borclarAdapter=new BorclarAdapter(borcOzetleriList);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        borcOzetleriView.setLayoutManager(layoutManager);
        borcOzetleriView.setItemAnimator(new DefaultItemAnimator());
        borcOzetleriView.setAdapter(borclarAdapter);

        verileriAl();
    }

    private void verileriAl() {
        Borclar borc1=new Borclar("kredi kartı","Ziraat kart borcu","1200 Tl");
        Borclar borc2=new Borclar("Ev Kredisi","Ziraat kredı","4500 Tl");
        Borclar borc3=new Borclar("Ev Kredisi","Ziraat kredı","4500 Tl");
        Borclar borc4=new Borclar("Ev Kredisi","Ziraat kredı","4500 Tl");

        borcOzetleriList.add(borc1);
        borcOzetleriList.add(borc2);
        borclarAdapter.notifyDataSetChanged();

    }


}
