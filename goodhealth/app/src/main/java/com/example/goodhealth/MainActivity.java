package com.example.goodhealth;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new CountDownTimer(2000, 1000) { // Délai de 5 secondes, tick toutes les 1 seconde
            public void onTick(long millisUntilFinished) {
                // Ne rien faire pendant les ticks si nécessaire
            }

            public void onFinish() {
                try {
                    Intent intent = new Intent(MainActivity.this, Welcome_screen.class);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}