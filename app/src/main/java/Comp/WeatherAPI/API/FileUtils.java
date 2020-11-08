package Comp.WeatherAPI.API;

import okhttp3.Response;

import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public void saveData(Response response, String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(String.format("%s", filename));
            fos.write(response.body().bytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveJSON(String json, String filename) {
        try {
            FileWriter myWriter = new FileWriter(String.format("%s", filename));
            myWriter.write(json);
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}