package com.example.canyard.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.canyard.kriptobank.R;
import com.example.canyard.model.Borclar;
import com.example.canyard.model.CardMovements;
import com.example.canyard.model.TransferHistory;

import java.util.List;

public class BorclarAdapter  extends  RecyclerView.Adapter<BorclarAdapter.BorcOzetleriHolder> {
    private List<Borclar> borcOzetleri;

    public BorclarAdapter(List<Borclar> borcOzetleri) {
        this.borcOzetleri = borcOzetleri;
    }

    @NonNull
    @Override
    public BorcOzetleriHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.borc_single,parent,false);
        return new BorclarAdapter.BorcOzetleriHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BorcOzetleriHolder holder, int position) {
        Borclar borclar=borcOzetleri.get(position);
        holder.borcTutar.setText(borclar.getBorcTutar());
        holder.ilgiliBorc.setText(borclar.getIlgiliBorc());
        holder.borcturu.setText(borclar.getBorcTuru());
    }

    @Override
    public int getItemCount() {
        return borcOzetleri.size();
    }


    public class BorcOzetleriHolder extends RecyclerView.ViewHolder{
        public TextView borcturu,ilgiliBorc,borcTutar;
        public BorcOzetleriHolder(View itemView) {
            super(itemView);
            borcturu=itemView.findViewById(R.id.borcTuruSingle);
            ilgiliBorc=itemView.findViewById(R.id.ilgiliBorcSingle);
            borcTutar=itemView.findViewById(R.id.tutarBorcSingle);
        }
    }
}
