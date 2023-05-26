package com.example.analisadorcfa;

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
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    private ImageView carregarImageView;
    private Button btCarregar;

    private static final int PICK_IMAGE_REQUEST = 1;

    private ActivityResultLauncher<String> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        carregarImageView = findViewById(R.id.carregarImageView);
        btCarregar = findViewById(R.id.btCarregar);

        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), this::onImagePickerResult);

        btCarregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirSelecionadorDeImagem();
            }
        });
    }

    private void abrirSelecionadorDeImagem() {
        imagePickerLauncher.launch("image/*");
    }

    private void onImagePickerResult(Uri uriImagem) {
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

            carregarImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
