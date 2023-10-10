package com.example.goodhealth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Calendar;
public class Welcome_screen extends AppCompatActivity {
     TextView textMeteo;

    private BroadcastReceiver weatherReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                // Récupérez les données météorologiques depuis l'Intent
                double temperatureCelsius = intent.getDoubleExtra("temperature", 0);
                int partieEntiere = (int) Math.round(temperatureCelsius);

                String description = intent.getStringExtra("description");

                // Mettez à jour votre TextView textMeteo avec les données
                String weatherText = "Température : " + partieEntiere + "°C\nDescription : " + description;
                textMeteo.setText(weatherText);
                Log.d("VALUECAD", weatherText );

            }
        }
    };
    private TextView textcitation;
    private String[] citation = {
            "Chaque jour est une nouvelle chance de réussir.",
            "La persévérance est la clé du succès.",
            "Les rêves deviennent réalité lorsque nous avons le courage de les poursuivre.",
            "Votre seule limite est vous-même.",
            "Le succès n'est pas la clé du bonheur. Le bonheur est la clé du succès. Si vous aimez ce que vous faites, vous réussirez.",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        textMeteo = findViewById(R.id.textMeteo);

        // Enregistrez le BroadcastReceiver pour recevoir les données météorologiques
        IntentFilter filter = new IntentFilter("com.example.weatherapp.WEATHER_DATA");
        LocalBroadcastManager.getInstance(this).registerReceiver(weatherReceiver, filter);

        // Démarrer le service WeatherService
        Intent serviceIntent = new Intent(this, WeatherService.class);
        startService(serviceIntent);
        Log.i("ADebugTag", "Value: " +textMeteo );

        textcitation = findViewById(R.id.textcitation);
        Fonction fonction2 = new Fonction();

        String phraseAleatoire = fonction2.getRandomCitation(citation);
        textcitation.setText(phraseAleatoire);
        //  instance de la classe Calendar pour connaître l'heure actuelle.
        Calendar calendar = Calendar.getInstance();
        int heure = calendar.get(Calendar.HOUR_OF_DAY);

        TextView greetingTextView = findViewById(R.id.greeting_text);

        // Détermine le message de salutation en fonction de l'heure.
        String messageDeSalutation;
        if (heure >= 0 && heure < 12) {
            messageDeSalutation = "Bonjour";
        } else if (heure >= 12 && heure < 18) {
            messageDeSalutation = "Bon après-midi";
        } else {
            messageDeSalutation = "Bonsoir";
        }

        // Affichez le message dans le TextView.
        greetingTextView.setText(messageDeSalutation);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Assurez-vous de désenregistrer le BroadcastReceiver lors de la destruction de l'activité
        LocalBroadcastManager.getInstance(this).unregisterReceiver(weatherReceiver);
    }
}