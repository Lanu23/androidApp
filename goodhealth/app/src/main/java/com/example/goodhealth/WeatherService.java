package com.example.goodhealth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class WeatherService extends Service {
    private static final String TAG = "WeatherService";

    public WeatherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Service démarré...");

        // Démarrez un nouveau thread pour effectuer les opérations réseau
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Remplacez "YOUR_API_KEY" par votre clé API OpenWeatherMap
                    String apiKey = "91610b8d5e21639d53a1b46afd5a8494";
                    String cityName = "Paris"; // Remplacez par la ville de votre choix

                    // Construisez l'URL de l'API OpenWeatherMap
                    String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey;

                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Parsez le JSON
                    JSONObject jsonObject = new JSONObject(response.toString());

                    // Accédez au champ "main" qui contient la température
                    JSONObject mainObject = jsonObject.getJSONObject("main");
                    double temperatureKelvin = mainObject.getDouble("temp");
                    double temperatureCelsius = temperatureKelvin - 273.15; // Conversion de Kelvin en Celsius

                    // Accédez au tableau "weather" pour obtenir la description
                    JSONObject weatherObject = jsonObject.getJSONArray("weather").getJSONObject(0);
                    String description = weatherObject.getString("description");

                    // Créez un Intent pour envoyer les données à l'activité Welcome_screen
                    Intent weatherIntent = new Intent("com.example.weatherapp.WEATHER_DATA");
                    weatherIntent.putExtra("temperature", temperatureCelsius);
                    weatherIntent.putExtra("description", description);

                    // Utilisez LocalBroadcastManager pour envoyer l'Intent de manière ciblée à l'activité
                    LocalBroadcastManager.getInstance(WeatherService.this).sendBroadcast(weatherIntent);

                    reader.close();
                    connection.disconnect();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "Service fin...");
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service arrêté.");
    }
}
