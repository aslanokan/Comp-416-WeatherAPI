package Comp.WeatherAPI;

import java.io.*;

public class currentWeather {

    public String get(String city) {
        String apiKey = "";
        String url = String.format("http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, apiKey);
        try {
            API api = new API();
            String data = api.run(url);
            return data;
        } catch (IOException e) {
            System.out.println(e);
            return "";
        }
    }
}