package com.example.canyard.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.canyard.kriptobank.R;
import com.example.canyard.model.TransferHistory;

import java.util.List;

public class TransferHistroyAdapter extends RecyclerView.Adapter<TransferHistroyAdapter.TransferHistoryHolder> {

    private List<TransferHistory> transferHistories;
    public class TransferHistoryHolder extends RecyclerView.ViewHolder{
        public TextView transferTur,transferTarih,transferMiktar;
        public TransferHistoryHolder(View itemView){
            super(itemView);
                transferTur=itemView.findViewById(R.id.tHistoryTur);
                transferMiktar=itemView.findViewById(R.id.tHistoryTutar);
                transferTarih=itemView.findViewById(R.id.tHistoryTarih);
        }
    }

    public TransferHistroyAdapter(List<TransferHistory> transferHistories) {
        this.transferHistories = transferHistories;
    }

    @NonNull
    @Override
    public TransferHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transfer_history,parent,false);
        return new TransferHistoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransferHistoryHolder holder, int position) {
            TransferHistory transferHistory=transferHistories.get(position);
            holder.transferTur.setText(transferHistory.getTur());
            holder.transferTarih.setText(transferHistory.getTarih());
            holder.transferMiktar.setText(transferHistory.getTutar());


    }

    @Override
    public int getItemCount() {
        return transferHistories.size();
    }
}

