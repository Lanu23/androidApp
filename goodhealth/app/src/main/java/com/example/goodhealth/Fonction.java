package com.example.goodhealth;

import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class Fonction {
    //-----------------------getWeatherData-------------------------------
    public void getWeatherData(TextView textMeteo) {
        // Remplacez "YOUR_API_KEY" par votre clé API OpenWeatherMap
        String apiKey = "91610b8d5e21639d53a1b46afd5a8494";
        String cityName = "Paris"; // Remplacez par la ville de votre choix

        // Construisez l'URL de l'API OpenWeatherMap
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=" + apiKey;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            try {
                // Parsez le JSON
                JSONObject jsonObject = new JSONObject(response.toString());

                // Accédez au champ "main" qui contient la température
                JSONObject mainObject = jsonObject.getJSONObject("main");
                double temperatureKelvin = mainObject.getDouble("temp");
                double temperatureCelsius = temperatureKelvin - 273.15; // Conversion de Kelvin en Celsius

                // Accédez au tableau "weather" pour obtenir la description
                JSONObject weatherObject = jsonObject.getJSONArray("weather").getJSONObject(0);
                String description = weatherObject.getString("description");

                // Mettez à jour le TextView avec les données météorologiques
                String weatherText = "Température : " + temperatureCelsius + "\nDescription : " + description;
                textMeteo.setText(weatherText);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            reader.close();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //-----------------------getRandomCitation----------------------------------------------
    public String getRandomCitation(String[] citation ) {
        Random random = new Random();
        int index = random.nextInt(citation.length);
        return citation[index];
    }

}
