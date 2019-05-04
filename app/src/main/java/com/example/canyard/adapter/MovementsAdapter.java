package com.example.canyard.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.canyard.kriptobank.R;
import com.example.canyard.model.Card;
import com.example.canyard.model.CardMovements;

import org.w3c.dom.Text;

import java.util.List;

public class MovementsAdapter extends RecyclerView.Adapter<MovementsAdapter.CardMovementsHolder> {

    private List<CardMovements> cardMovementsList;



    public class CardMovementsHolder extends RecyclerView.ViewHolder{
        public TextView tur,tutar,odenenFirma;
        public CardMovementsHolder(View itemView) {
            super(itemView);
                tur=itemView.findViewById(R.id.tur);
                tutar=itemView.findViewById(R.id.tutar);
                odenenFirma=itemView.findViewById(R.id.odenenFirma);
        }
    }
    public MovementsAdapter(List<CardMovements> cardMovementsList) {
        this.cardMovementsList=cardMovementsList;
    }

    @Override
    public int getItemCount() {
        return cardMovementsList.size();
    }

    @NonNull
    @Override
    public CardMovementsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_movements_list,parent,false);
        return new CardMovementsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardMovementsHolder holder, int position) {
        CardMovements cardMovements=cardMovementsList.get(position);
        holder.tur.setText(cardMovements.getTur());
        holder.tutar.setText(cardMovements.getTutar());
        holder.odenenFirma.setText(cardMovements.getOdenenFirma());


    }
}
