package Comp.WeatherAPI;

import net.aksingh.owmjapis.core.OWM;
import net.aksingh.owmjapis.model.CurrentWeather;
import net.aksingh.owmjapis.api.APIException;

public class OwmAPI {

    public void test() throws APIException {
        // declaring object of "OWM" class
        OWM owm = new OWM("YOUR-API-KEY-HERE");

        // getting current weather data for the "London" city
        CurrentWeather cwd = owm.currentWeatherByCityName("London");

        //printing city name from the retrieved data
        System.out.println("City: " + cwd.getCityName());

        // printing the max./min. temperature
        System.out.println("Temperature: " + cwd.getMainData().getTempMax()
                + "/" + cwd.getMainData().getTempMin() + "\'K");
    }
}