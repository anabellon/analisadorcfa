package com.example.analisadorcfa;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button btInicio, btInstrucoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        btInicio = findViewById(R.id.btInicio);
        btInstrucoes = findViewById(R.id.btInstrucoes);

        btInstrucoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exibirInstrucoes();
            }
        });

        btInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
    private void exibirInstrucoes() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Instruções");
        builder.setMessage("Para a detecção de adulteração, as imagens deverão ser:\n\n- BILINEARES\n\n- NÃO Comprimidas");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}