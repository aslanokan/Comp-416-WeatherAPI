package Comp.WeatherAPI;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import io.github.cdimascio.dotenv.Dotenv;

import java.io.*;

public class WeatherAPI {
    OkHttpClient client = new OkHttpClient();
    Dotenv dotenv = Dotenv.load();

    String apiKey = dotenv.get("apiKey");

    public String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public String getCurrentWeatherAt(String city) {
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, apiKey);
        try {
            WeatherAPI api = new WeatherAPI();
            String data = api.get(url);
            return data;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public String getDailyForecast(String lat, String lon) {
        String url = String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&exclude=%s&appid=%s", lat, lon, "hourly", apiKey);
        try {
            WeatherAPI api = new WeatherAPI();
            String data = api.get(url);
            return data;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public Response getBasicWeatherMap(String layer, String z, String x, String y) {
        String url = String.format("https://tile.openweathermap.org/map/%s/%s/%s/%s.png?appid=%s", layer, z, x, y, apiKey);
        try {
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            return response;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public String getMinuteForecast(String lat, String lon) {
        String url = String.format("https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&exclude=%s&appid=%s", lat, lon, "daily", apiKey);
        try {
            WeatherAPI api = new WeatherAPI();
            String data = api.get(url);
            return data;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public String getHistoricalWeather(String lat, String lon, String time) {
        String url = String.format("https://api.openweathermap.org/data/2.5/onecall/timemachine?lat=%s&lon=%s&dt=%s&appid=%s", lat, lon, time, apiKey);
        try {
            WeatherAPI api = new WeatherAPI();
            String data = api.get(url);
            return data;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
}