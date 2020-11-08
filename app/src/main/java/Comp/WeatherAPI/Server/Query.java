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

    public File sendQuery(){

        String[] params = queryString.split(DELIMITER);

        switch(this.type){
            case 1:
                return new File(API.getCurrentWeatherAt(params[0]));
            case 2:
                return new File(API.getDailyForecast(params[0], params[1]));
            case 3:
//                return new File(API.getBasicWeatherMap(params[0], params[1], params[2], params[3]));
            case 4:
                return new File(API.getMinuteForecast(params[0], params[1]));
            case 5:
                return new File(API.getHistoricalWeather(params[0], params[1], params[2]));
            default:
                return null;
        }
    }
}
