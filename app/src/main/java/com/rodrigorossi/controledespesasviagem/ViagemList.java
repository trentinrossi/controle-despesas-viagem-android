package com.rodrigorossi.controledespesasviagem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ViagemList extends AppCompatActivity {
    private ListView             listViewViagens;
    private ArrayAdapter<Viagem> viagemAdapter;
    private ArrayList<Viagem>    viagens;
    private View       viewSelecionada;
    private ActionMode actionMode;
    private int        posicaoSelecionada = -1;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater inflate = actionMode.getMenuInflater();
            inflate.inflate(R.menu.viagemlist_menu_contextual, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.menuitem_contexto_editar:
                    alterarItemLista();
                    actionMode.finish();
                    return true;
                case R.id.menuitem_contexto_excluir:
                    excluirItemLista();
                    actionMode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            if (viewSelecionada != null){
                viewSelecionada.setBackgroundColor(Color.TRANSPARENT);
            }
            actionMode         = null;
            viewSelecionada    = null;
            listViewViagens.setEnabled(true);
        }
    };

    public static final int PEDIR_VIAGEM_NOVA = 1;
    public static final int PEDIR_VIAGEM_EDIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listViewViagens = findViewById(R.id.listViewViagens);

        listViewViagens.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id) {
                        posicaoSelecionada = position;
                        alterarItemLista();
                    }
                });

        listViewViagens.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewViagens.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view,
                                                   int position,
                                                   long id) {
                        if (actionMode != null){
                            return false;
                        }
                        posicaoSelecionada = position;
                        view.setBackgroundColor(Color.LTGRAY);
                        viewSelecionada = view;
                        listViewViagens.setEnabled(false);
                        actionMode = startSupportActionMode(mActionModeCallback);
                        return true;
                    }
                });

        inicializaLista();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.viagemlist_menu, menu);
        return true;
    }

    private void inicializaLista() {
        viagens = new ArrayList<>();
        viagemAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                viagens);
        listViewViagens.setAdapter(viagemAdapter);
    }

    private void adicionarItemLista(Viagem v) {
        viagens.add(v);
        viagemAdapter.notifyDataSetChanged();
    }

    private void excluirItemLista() {
        viagens.remove(posicaoSelecionada);
        viagemAdapter.notifyDataSetChanged();
    }

    private void alterarItemLista() {
        Viagem v = viagens.get(posicaoSelecionada);
        MainActivity.alterarViagem(this, v);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            String destino = bundle.getString(MainActivity.KEY_DESTINO);
            int kmInicial = bundle.getInt(MainActivity.KEY_KMINICIAL);
            int kmFinal = bundle.getInt(MainActivity.KEY_KMFINAL);
            int tipo = bundle.getInt(MainActivity.KEY_TIPO);
            boolean reembolsar = bundle.getBoolean(MainActivity.KEY_REEMBOLSAR);
            int tipoVeiculo = bundle.getInt(MainActivity.KEY_TIPO_VEICULO);

            if (requestCode == MainActivity.NOVO) {
                Viagem viagem = new Viagem(destino,kmInicial,kmFinal,tipo,reembolsar,tipoVeiculo);
                adicionarItemLista(viagem);
            } else if (requestCode == MainActivity.ALTERAR) {
                Viagem v = viagens.get(posicaoSelecionada);
                v.setDestino(destino);
                v.setKmInicial(kmInicial);
                v.setKmFinal(kmFinal);
                v.setTipoViagem(tipo);
                v.setTipoVeiculo(tipoVeiculo);
                posicaoSelecionada =- 1;
                viagemAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuItemAdicionar:
                MainActivity.novaViagem(this);
                return true;
            case R.id.menuItemSobre:
                mostraActivitySobre(item);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void mostraActivitySobre(MenuItem item) {
        Intent intent = new Intent(this,DadosAutoraisActivity.class);
        startActivity(intent);
    }
}