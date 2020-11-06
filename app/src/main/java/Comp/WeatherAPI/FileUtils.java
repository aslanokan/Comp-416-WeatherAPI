package Comp.WeatherAPI;

import java.util.Base64;
import okhttp3.Response;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public void saveData(Response response, String filename) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            fos.write(response.body().bytes());
            fos.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}