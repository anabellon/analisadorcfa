package com.example.analisadorcfa;

import static com.example.analisadorcfa.FindPattern.getPadrao;
import static com.example.analisadorcfa.ImageProcessing.pintarForaDoPadrao;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {
    TextView tv_padrao;
    ImageView iv_imagem;
    private String padrao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        tv_padrao = findViewById(R.id.tv_padrao);
        iv_imagem = findViewById(R.id.iv_imagem);
        Intent intent = getIntent();
        String caminhoImagem = intent.getStringExtra("caminho_imagem");

        // Carregar a imagem do arquivo
        Bitmap bitmap = BitmapFactory.decodeFile(caminhoImagem);
        padrao = getPadrao(bitmap);
        Bitmap imagem = pintarForaDoPadrao(bitmap,padrao);
        tv_padrao.setText(padrao);
        iv_imagem.setImageBitmap(imagem);

    }
}