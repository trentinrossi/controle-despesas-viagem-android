package com.rodrigorossi.controledespesasviagem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private boolean isEmViagem = false;
    private EditText editDataInicio, editDataFinal, editDestino, editKmInicial, editKmFinal;
    private RadioGroup radioGroupTipoViagem;
    private RadioButton radioButtonSelected;
    private CheckBox checkBoxReembolsar;
    private Spinner spinnerTipoVeiculo;
    private int    modo;
    public static final String MODO    = "MODO";
    public static final int    NOVO    = 1;
    public static final int    ALTERAR = 2;

    // Dados que são trafegados entre activities
    public static String KEY_DESTINO = "DESTINO";
    public static String KEY_KMINICIAL = "KMINICIAL";
    public static String KEY_KMFINAL = "KMFINAL";
    public static String KEY_TIPO = "TIPO";
    public static String KEY_REEMBOLSAR = "REEMBOLSAR";
    public static String KEY_TIPO_VEICULO = "TIPO_VEICULO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Faz o binding dos componentes
        editDataInicio = findViewById(R.id.editDataInicio);
        editDataFinal = findViewById(R.id.editDataFinal);
        editDestino = findViewById(R.id.editDestino);
        editKmInicial = findViewById(R.id.editKmInicial);
        editKmFinal = findViewById(R.id.editKmFinal);
        radioGroupTipoViagem = findViewById(R.id.radioGroupTipoViagem);
        checkBoxReembolsar = findViewById(R.id.checkBoxReembolsar);
        spinnerTipoVeiculo = findViewById(R.id.spinnerTipoVeiculo);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null){
            modo = bundle.getInt(MODO, NOVO);
            if (modo == NOVO){
                setTitle(getString(R.string.nova_viagem));
            }else{
                String destinoOriginal = bundle.getString(KEY_DESTINO);
                editDestino.setText(destinoOriginal);

                int kmInicialOriginal = bundle.getInt(KEY_KMINICIAL);
                editKmInicial.setText(String.valueOf(kmInicialOriginal));

                int kmFinalOriginal = bundle.getInt(KEY_KMFINAL);
                editKmFinal.setText(String.valueOf(kmFinalOriginal));

                radioGroupTipoViagem.check(bundle.getInt(KEY_TIPO));

                checkBoxReembolsar.setChecked(bundle.getBoolean(KEY_REEMBOLSAR));

                spinnerTipoVeiculo.setSelection(bundle.getInt(KEY_TIPO_VEICULO));

                setTitle(getString(R.string.alterar_viagem));
            }
        }
    }

    public static void novaViagem(AppCompatActivity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(MODO, NOVO);
        activity.startActivityForResult(intent, NOVO);
    }

    public static void alterarViagem(AppCompatActivity activity, Viagem viagem){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(MODO, ALTERAR);
        intent.putExtra(KEY_DESTINO, viagem.getDestino());
        intent.putExtra(KEY_KMINICIAL, viagem.getKmInicial());
        intent.putExtra(KEY_KMFINAL, viagem.getKmFinal());
        intent.putExtra(KEY_TIPO, viagem.getTipoViagem());
        intent.putExtra(KEY_REEMBOLSAR, viagem.isReembolsar());
        intent.putExtra(KEY_TIPO_VEICULO, viagem.getTipoVeiculo());
        activity.startActivityForResult(intent, ALTERAR);
    }

    /**
     * Inicia uma viagem
     */
    public void iniciarViagem() {
        // Preenche a Data inicial da viagem com a data do sistema
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        String dataFormatada = formataData.format(data);
        editDataInicio.setText(dataFormatada);

        // Inicia a viagem
        if (validaCamposObrigatorios()) {

            // Recupera o tipo de viagem que foi selecionado
            int tipoViagem = radioGroupTipoViagem.getCheckedRadioButtonId();

            // Recupera o tipo do veículo que foi selecionado
            String tipoVeiculo = spinnerTipoVeiculo.getSelectedItem().toString();
            int tipoVeiculoPosition = spinnerTipoVeiculo.getSelectedItemPosition();

            this.isEmViagem = true;
//            findViewById(R.id.btnIniciarViagem).setEnabled(false);
//            findViewById(R.id.btnFinalizarViagem).setEnabled(true);
//            findViewById(R.id.btnLimparForm).setEnabled(false);

            String msgViagemIniciada = "Viagem à "+tipoViagem+" iniciada usando um veículo " + tipoVeiculo;
            Toast.makeText(this, msgViagemIniciada, Toast.LENGTH_SHORT).show();

            if (ViagemList.PEDIR_VIAGEM_NOVA == 1) {
                Intent intent = new Intent();
                intent.putExtra(KEY_DESTINO, editDestino.getText().toString());
                intent.putExtra(KEY_KMINICIAL, Integer.parseInt(editKmInicial.getText().toString()));
                intent.putExtra(KEY_KMFINAL, Integer.parseInt(editKmFinal.getText().toString()));
                intent.putExtra(KEY_TIPO, tipoViagem);
                intent.putExtra(KEY_REEMBOLSAR, checkBoxReembolsar.isChecked());
                intent.putExtra(KEY_TIPO_VEICULO, tipoVeiculoPosition);

                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuitem_main_salvar:
                iniciarViagem();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuitem_main_limpar:
                limparFormulario();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    /**
     * Finaliza uma viagem
     * @param view
     */
    public void finalizarViagem(View view) {
        // Preenche a Data Final da viagem com a data do sistema
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        String dataFormatada = formataData.format(data);
        editDataFinal.setText(dataFormatada);

        this.isEmViagem = false;
//        findViewById(R.id.btnIniciarViagem).setEnabled(true);
//        findViewById(R.id.btnFinalizarViagem).setEnabled(false);
//        findViewById(R.id.btnLimparForm).setEnabled(true);
    }

    /**
     * Limpa os dados do formulário
     */
    public void limparFormulario() {
        editDestino.setText(null);
        editKmInicial.setText(null);
        editKmFinal.setText(null);
        editDataInicio.setText(null);
        editKmFinal.setText(null);
        radioGroupTipoViagem.clearCheck();
        checkBoxReembolsar.setChecked(false);
        spinnerTipoVeiculo.setSelection(0);
    }

    /**
     * Realiza a validação se todos as informações estão preenchidas
     * @return true caso não encontre nenhum problema de validação e false caso ocorram problemas
     *
     */
    private boolean validaCamposObrigatorios() {

        // Destino
        String destino = editDestino.getText().toString();
        if (destino == null || destino.trim().isEmpty()) {
            Toast.makeText(this, R.string.lblDestino_mandatory, Toast.LENGTH_SHORT).show();
            editDestino.requestFocus();
            return false;
        }

        // Km Inicial
        String kmInicial = editKmInicial.getText().toString();
        if (kmInicial == null || kmInicial.trim().isEmpty()) {
            Toast.makeText(this, R.string.lblKmInicial_mandatory, Toast.LENGTH_SHORT).show();
            editKmInicial.requestFocus();
            return false;
        }

        // Km Final
        String kmFinal = editKmInicial.getText().toString();
        if (kmFinal == null || kmFinal.trim().isEmpty()) {
            Toast.makeText(this, R.string.lblKmFinal_mandatory, Toast.LENGTH_SHORT).show();
            editKmFinal.requestFocus();
            return false;
        }

        // Motivo da Viagem
        if (radioGroupTipoViagem.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, R.string.lblTipoViagem_mandatory, Toast.LENGTH_SHORT).show();
            return false;
        }

        // Tipo do Veículo
        if (spinnerTipoVeiculo.getSelectedItemPosition() == 0) {
            Toast.makeText(this, R.string.spinnerTipo_mandatory, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}