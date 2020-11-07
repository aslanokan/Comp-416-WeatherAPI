package Comp.WeatherAPI;

import Comp.WeatherAPI.Server.*;
import io.github.cdimascio.dotenv.Dotenv;

public class App {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        Server server = new Server(Integer.parseInt(dotenv.get("PORT")));
    }

    public String getGreeting() {
        return "Hello World!";
    }
}
