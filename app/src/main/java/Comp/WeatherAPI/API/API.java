package Comp.WeatherAPI.API;

import okhttp3.Response;

public class API {
    static WeatherAPI weatherAPI = new WeatherAPI();
    static FileUtils fileUtils = new FileUtils();
    static DateUtils dateUtils = new DateUtils();

    public static String getCurrentWeatherAt(String city) {
        String filename = String.format("currentWeather-%s-%s.json", city, dateUtils.getDate());
        String currentWeather = weatherAPI.getCurrentWeatherAt(city);
//        fileUtils.saveJSON(currentWeather, filename);
        return currentWeather;
    }

    public static String getDailyForecast(String lat, String lon) {
        String filename = String.format("dailyForecast-%s-%s-%s.json", lat, lon, dateUtils.getDate());
        String dailyForecast = weatherAPI.getDailyForecast(lat, lon);
//        fileUtils.saveJSON(dailyForecast, filename);
        return dailyForecast;
    }

    public static byte[] getBasicWeatherMap(String layer, String z, String x, String y) {
        String filename = String.format("basicWeatherMapData-%s-%s-%s-%s-%s.png", layer, z, x, y, dateUtils.getDate());
        byte[] basicWeatherMapData = weatherAPI.getBasicWeatherMap(layer, z, x, y);
//        fileUtils.saveData(basicWeatherMapData, filename);
        return basicWeatherMapData;
    }

    public static String getMinuteForecast(String lat, String lon) {
        String filename = String.format("minuteForecast-%s-%s-%s.json", lat, lon, dateUtils.getDate());
        String minuteForecast = weatherAPI.getMinuteForecast(lat, lon);
//        fileUtils.saveJSON(minuteForecast, filename);
        return minuteForecast;
    }

    public static String getHistoricalWeather(String lat, String lon, String time) {
        String filename = String.format("historicalWeather-%s-%s-%s-%s.json", lat, lon, time, dateUtils.getDate());
        String historicalWeather = weatherAPI.getHistoricalWeather(lat, lon, time);
//        fileUtils.saveJSON(historicalWeather, filename);
        return historicalWeather;
    }
}
