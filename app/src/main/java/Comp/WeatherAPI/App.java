package Comp.WeatherAPI;

import Comp.WeatherAPI.Server.*;

public class App {
    public static void main(String[] args) {
        Server server = new Server(Server.DEFAULT_SERVER_PORT);
    }

    public String getGreeting() {
        return "Hello World!";
    }
}
