package com.rodrigorossi.controledespesasviagem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class ViagemList extends AppCompatActivity {
    private RecyclerView recyclerViewViagens;
    private RecyclerView.LayoutManager layoutManager;
    private ViagemAdapter              viagemAdapter;

    private ArrayList<Viagem> viagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagem_list);

        recyclerViewViagens = findViewById(R.id.recyclerViewViagens);
        layoutManager = new LinearLayoutManager(this);

        recyclerViewViagens.setLayoutManager(layoutManager);
        recyclerViewViagens.setHasFixedSize(true);
        recyclerViewViagens.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        popularLista();

        recyclerViewViagens.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerViewViagens, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Viagem v = viagens.get(position);
                                Toast.makeText(getApplicationContext(),v.getDestino() + getString(R.string.recebeu_click), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Viagem v = viagens.get(position);
                                Toast.makeText(getApplicationContext(), v.getDestino() + getString(R.string.recebeu_click_longo), Toast.LENGTH_SHORT).show();
                            }
                        }
                ));
    }

    private void popularLista(){

        String[] destinos  = getResources().getStringArray(R.array.destinos);
        int[]    kmIniciais = getResources().getIntArray(R.array.kmInicial);
        int[]    kmFinais = getResources().getIntArray(R.array.kmFinal);
        String[] tiposViagem = getResources().getStringArray(R.array.tiposViagem);

        viagens = new ArrayList<>();

        for (int cont = 0; cont < destinos.length; cont++){
            viagens.add(new Viagem(destinos[cont], kmIniciais[cont], kmFinais[cont], tiposViagem[cont]));
        }
        viagemAdapter = new ViagemAdapter(viagens);
        recyclerViewViagens.setAdapter(viagemAdapter);
    }
}