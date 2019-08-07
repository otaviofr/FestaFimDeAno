package com.otaviofr.festafimdeano.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransitionImpl;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.otaviofr.festafimdeano.R;
import com.otaviofr.festafimdeano.constant.FimDeAnoConstants;
import com.otaviofr.festafimdeano.data.SecurityPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;
    private static SimpleDateFormat dataSimples = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.textHoje = findViewById(R.id.text_hoje);
        this.mViewHolder.textContagemDiasRestantes = findViewById(R.id.text_contagem_de_dias_restantes);
        this.mViewHolder.buttonConfirma = findViewById(R.id.button_confirma);

        this.mViewHolder.buttonConfirma.setOnClickListener(this);

        //Datas
        this.mViewHolder.textHoje.setText(dataSimples.format(Calendar.getInstance().getTime()));
        String diasRestantes = String.format("%s %s",String.valueOf(this.getDiasRestantes()), getString(R.string.dias));
        this.mViewHolder.textContagemDiasRestantes.setText(diasRestantes);

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.verifyPresence();
    }

    @Override
    public void onClick(View v) {
        //confirmo o botao clicado
        if (v.getId() == R.id.button_confirma) {
            String presenca = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.chaveDePresenca);

            //abre uma nova activity(tela)
            /*o Intent espera dois parametros,o contexto (que no caso é this porque
            como extende de AppCompatActivity o contexto é this) e o outro é
            quem vc quer abrir, no caso DetailsActivity.class (.class serve para saber
            que quem vai ser aberto é o DetailsActivity)*/
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra(FimDeAnoConstants.chaveDePresenca, presenca);
            //inicia a activity
            startActivity(intent);
        }
    }

    private void verifyPresence(){
        //nao confirmado, sim, nao
        String presenca = this.mSecurityPreferences.getStoredString(FimDeAnoConstants.chaveDePresenca);
        if(presenca.equals("")){
           this.mViewHolder.buttonConfirma.setText(getString(R.string.nao_confirmado));
        } else if (presenca.equals(FimDeAnoConstants.confirmacaoSim)){
            this.mViewHolder.buttonConfirma.setText((getString(R.string.sim)));
        }else{
            this.mViewHolder.buttonConfirma.setText(getString(R.string.nao));
        }
    }

    private static class ViewHolder {
        TextView textHoje;
        TextView textContagemDiasRestantes;
        Button buttonConfirma;
    }

    private int getDiasRestantes() {
        //pega a data de hoje
        Calendar calendarioHoje = Calendar.getInstance();
        int hoje = calendarioHoje.get(Calendar.DAY_OF_YEAR);

        //dia maximo do ano
        Calendar ultimoDiaCalendario = Calendar.getInstance();
        int diaMaximo = ultimoDiaCalendario.getActualMaximum(Calendar.DAY_OF_YEAR);

        return diaMaximo - hoje;
    }

}
