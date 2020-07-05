package com.rodrigorossi.controledespesasviagem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViagemAdapter extends RecyclerView.Adapter<ViagemAdapter.MyViewHolder> {

    private List<Viagem> listaViagens;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewDestino, textViewKmInicial, textViewKmFinal, textViewTipoViagem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewDestino = itemView.findViewById(R.id.textViewDestino);
            textViewKmInicial = itemView.findViewById(R.id.textViewKmInicial);
            textViewKmFinal = itemView.findViewById(R.id.textViewKmFinal);
            textViewTipoViagem = itemView.findViewById(R.id.textViewTipoViagem);
        }
    }

    public ViagemAdapter(List<Viagem> listaViagens) {
        this.listaViagens = listaViagens;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viagem_adapter,
                        parent,
                        false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Viagem viagem = listaViagens.get(position);

        holder.textViewDestino.setText(viagem.getDestino());
        holder.textViewKmInicial.setText(String.valueOf(viagem.getKmInicial()));
        holder.textViewKmFinal.setText(String.valueOf(viagem.getKmFinal()));
        holder.textViewTipoViagem.setText(viagem.getTipoViagem());
    }

    @Override
    public int getItemCount() {
        return listaViagens.size();
    }
}
