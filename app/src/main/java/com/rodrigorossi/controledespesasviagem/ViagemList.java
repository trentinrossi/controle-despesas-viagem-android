package com.rodrigorossi.controledespesasviagem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViagemList extends AppCompatActivity {
    private RecyclerView recyclerViewViagens;
    private RecyclerView.LayoutManager layoutManager;
    private ViagemAdapter              viagemAdapter;
    private FloatingActionButton fabAbout;
    private FloatingActionButton fabAdd;
    public static final int PEDIR_VIAGEM = 1;

    private ArrayList<Viagem> viagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viagem_list);

        recyclerViewViagens = findViewById(R.id.recyclerViewViagens);
        layoutManager = new LinearLayoutManager(this);

        // Ações do botão de Autoria do App
        fabAbout = findViewById(R.id.floatingActionButtonAbout);
        fabAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostraActivitySobre(view);
            }
        });

        // Ações do botão de Add do App
        fabAdd = findViewById(R.id.floatingActionButtonAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostraActivityAdd(view);
            }
        });

        recyclerViewViagens.setLayoutManager(layoutManager);
        recyclerViewViagens.setHasFixedSize(true);
        recyclerViewViagens.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

        inicializaLista();

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

//    private void popularLista() {
//        String[] destinos  = getResources().getStringArray(R.array.destinos);
//        int[]    kmIniciais = getResources().getIntArray(R.array.kmInicial);
//        int[]    kmFinais = getResources().getIntArray(R.array.kmFinal);
//        String[] tiposViagem = getResources().getStringArray(R.array.tiposViagem);
//
//        viagens = new ArrayList<>();
//
//        for (int cont = 0; cont < destinos.length; cont++){
//            viagens.add(new Viagem(destinos[cont], kmIniciais[cont], kmFinais[cont], tiposViagem[cont]));
//        }
//        viagemAdapter = new ViagemAdapter(viagens);
//        recyclerViewViagens.setAdapter(viagemAdapter);
//    }

    private void inicializaLista() {
        viagens = new ArrayList<>();
        viagemAdapter = new ViagemAdapter(viagens);
        recyclerViewViagens.setAdapter(viagemAdapter);
    }

    private void addItemLista(Viagem viagem) {
        viagens.add(viagem);
        viagemAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PEDIR_VIAGEM && resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                String destino = bundle.getString(MainActivity.KEY_DESTINO);
                int kmInicial = bundle.getInt(MainActivity.KEY_KMINICIAL);
                int kmFinal = bundle.getInt(MainActivity.KEY_KMFINAL);
                String tipo = bundle.getString(MainActivity.KEY_TIPO);

                Viagem viagem = new Viagem(destino,kmInicial,kmFinal,tipo);

                addItemLista(viagem);
            }
        }
    }

    public void mostraActivitySobre(View view) {
        Intent intent = new Intent(this,DadosAutoraisActivity.class);
        startActivity(intent);
    }

    public void mostraActivityAdd(View view) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivityForResult(intent, PEDIR_VIAGEM);
    }
}