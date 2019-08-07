package com.otaviofr.festafimdeano.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.otaviofr.festafimdeano.R;
import com.otaviofr.festafimdeano.constant.FimDeAnoConstants;
import com.otaviofr.festafimdeano.data.SecurityPreferences;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewHolder mViewHolder = new ViewHolder();
    private SecurityPreferences mSecurityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        this.mSecurityPreferences = new SecurityPreferences(this);

        this.mViewHolder.checkParticipar = findViewById(R.id.check_box_voce_vai_participar);
        this.mViewHolder.checkParticipar.setOnClickListener(this);

        this.carregaDadosDaActivity();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.check_box_voce_vai_participar) {
            if (this.mViewHolder.checkParticipar.isChecked()) {
                //salva presença
                this.mSecurityPreferences.storeString(FimDeAnoConstants.chaveDePresenca, FimDeAnoConstants.confirmacaoSim);
            } else {
                //salva ausencia
                this.mSecurityPreferences.storeString(FimDeAnoConstants.chaveDePresenca, FimDeAnoConstants.confirmacaoNao);
            }
        }
    }

    private void carregaDadosDaActivity() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String presenca = extras.getString(FimDeAnoConstants.chaveDePresenca);
            if (presenca != null && presenca.equals(FimDeAnoConstants.confirmacaoSim)) {
                this.mViewHolder.checkParticipar.setChecked(true);
            } else {
                this.mViewHolder.checkParticipar.setChecked(false);
            }
        }
    }

    private static class ViewHolder {
        CheckBox checkParticipar;
    }
}
