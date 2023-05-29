package com.example.analisadorcfa;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    private ImageView carregarImageView;
    private Button btCarregar, btIdentificar;
    private String caminhoImagem;

    private ActivityResultLauncher<String> imagePickerLauncher;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        carregarImageView = findViewById(R.id.carregarImageView);
        btCarregar = findViewById(R.id.btCarregar);
        btIdentificar = findViewById(R.id.btIdentificar);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), this::tratarResultadoSelecaoImagem);

        btCarregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSeletorDeImagem();
            }
        });

        btIdentificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (caminhoImagem == null) {
                    exibirAlertDialog();
                } else {
                    iniciarTerceiraActivity();
                }
            }
        });
    }

    private void exibirAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
        builder.setTitle("Alerta");
        builder.setMessage("Nenhuma imagem carregada. Por favor, carregue uma imagem antes de prosseguir.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void abrirSeletorDeImagem() {
        imagePickerLauncher.launch("image/*");
    }

    private void iniciarTerceiraActivity() {
        Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
        intent.putExtra("caminho_imagem", caminhoImagem);
        startActivity(intent);
    }

    private void tratarResultadoSelecaoImagem(Uri uriImagem) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriImagem), null, options);

            // Verifica se a imagem está no formato bilinear
            if (bitmap.getConfig() != Bitmap.Config.ARGB_8888) {
                Toast.makeText(this, "A imagem deve estar no formato bilinear (ARGB_8888)", Toast.LENGTH_SHORT).show();
                return;
            }

            // Verifica se a imagem não está comprimida
            if (bitmap.getNinePatchChunk() != null) {
                Toast.makeText(this, "A imagem não deve estar comprimida", Toast.LENGTH_SHORT).show();
                return;
            }

            // Salvar o Bitmap em um arquivo temporário
            File arquivo = new File(getCacheDir(), "imagem.png");
            try {
                FileOutputStream outputStream = new FileOutputStream(arquivo);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            caminhoImagem = arquivo.getAbsolutePath();
            carregarImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
