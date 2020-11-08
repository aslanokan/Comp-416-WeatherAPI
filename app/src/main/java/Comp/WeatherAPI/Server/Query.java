package Comp.WeatherAPI.Server;


import Comp.WeatherAPI.API.API;

import java.io.File;

public class Query {

    public static final String DELIMITER = ",";
    private int type;
    private String queryString;

    public Query(int type, String payload) {
        this.type = type;
        this.queryString = payload;
    }

    public byte[] sendQuery(){

        String[] params = queryString.split(DELIMITER);

        String data;

        switch(this.type){
            case 1:
                data = API.getCurrentWeatherAt(params[0]);
                return data.getBytes();
            case 2:
                data = API.getDailyForecast(params[0], params[1]);
                return data.getBytes();
            case 3:
                return API.getBasicWeatherMap(params[0], params[1], params[2], params[3]);
            case 4:
                data = API.getMinuteForecast(params[0], params[1]);
                return data.getBytes();
            case 5:
                data = API.getHistoricalWeather(params[0], params[1], params[2]);
                return data.getBytes();
            default:
                return null;
        }
    }
}
