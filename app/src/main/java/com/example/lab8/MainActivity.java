package com.example.lab8;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Code perso de CHARRAJ Mouad (ZER0-XR7)
public class MainActivity extends AppCompatActivity {

    // Mes variables avec signature _mouad
    private TextView txtStatus_mouad;
    private ProgressBar progressBar_mouad;
    private ImageView img_mouad;
    private Handler mainHandler_mouad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Liaison avec les composants (IDs _mouad)
        txtStatus_mouad = findViewById(R.id.txtStatus_mouad);
        progressBar_mouad = findViewById(R.id.progressBar_mouad);
        img_mouad = findViewById(R.id.img_mouad);

        Button btnLoadThread_mouad = findViewById(R.id.btnLoadThread_mouad);
        Button btnCalcAsync_mouad = findViewById(R.id.btnCalcAsync_mouad);
        Button btnToast_mouad = findViewById(R.id.btnToast_mouad);

        // Handler pour l'affichage sur le thread principal
        mainHandler_mouad = new Handler(Looper.getMainLooper());

        // Bouton pour tester la réactivité (Toast)
        btnToast_mouad.setOnClickListener(v -> 
            Toast.makeText(this, getString(R.string.toast_msg_mouad), Toast.LENGTH_SHORT).show()
        );

        // Bouton pour le chargement d'image via Thread
        btnLoadThread_mouad.setOnClickListener(v -> lancerThread_mouad());

        // Bouton pour le calcul lourd via AsyncTask
        btnCalcAsync_mouad.setOnClickListener(v -> new CalculAsyncTask_mouad().execute());
    }

    // Ma méthode pour gérer le thread de l'image
    private void lancerThread_mouad() {
        progressBar_mouad.setVisibility(View.VISIBLE);
        txtStatus_mouad.setText(getString(R.string.status_loading_mouad));

        new Thread(() -> {
            try {
                Thread.sleep(2000); // Pause de 2 secondes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Chargement de la ressource
            Bitmap bmp_mouad = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

            // Retour sur l'interface pour afficher
            mainHandler_mouad.post(() -> {
                img_mouad.setImageBitmap(bmp_mouad);
                progressBar_mouad.setVisibility(View.INVISIBLE);
                txtStatus_mouad.setText(getString(R.string.status_image_ok_mouad));
            });
        }).start();
    }

    // Mon AsyncTask perso pour le calcul
    private class CalculAsyncTask_mouad extends AsyncTask<Void, Integer, Long> {

        @Override
        protected void onPreExecute() {
            progressBar_mouad.setVisibility(View.VISIBLE);
            progressBar_mouad.setProgress(0);
            txtStatus_mouad.setText(getString(R.string.status_loading_mouad));
        }

        @Override
        protected Long doInBackground(Void... voids) {
            long somme_mouad = 0;
            for (int i = 1; i <= 100; i++) {
                try {
                    Thread.sleep(30); // Délai pour la démo
                    somme_mouad += i;
                } catch (Exception e) {}
                publishProgress(i);
            }
            return somme_mouad;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar_mouad.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Long res_mouad) {
            progressBar_mouad.setVisibility(View.INVISIBLE);
            txtStatus_mouad.setText(getString(R.string.status_calc_done_mouad, res_mouad));
        }
    }
}