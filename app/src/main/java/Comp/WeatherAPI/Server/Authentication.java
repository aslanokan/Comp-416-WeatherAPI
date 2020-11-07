package Comp.WeatherAPI.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Authentication {
    private static Authentication authentication = null;

    private Authentication() {
    }

    public static Authentication getInstance() {
        if (authentication == null)
            authentication = new Authentication();
        return authentication;
    }

    public boolean validateUser(String username) {
        username += ".txt";
        ClassLoader classLoader = getClass().getClassLoader();
        File userFile = new File(classLoader.getResource(username).getFile());
        boolean validUser = userFile.exists();

        return validUser;
    }

    private HashMap<String, String> loadUserDetails(String username) {
        /*** give the username, looksup and loads the contents of the authentication details related to this user to a HashMap **/
        ClassLoader classLoader = getClass().getClassLoader();
        File userFile = new File(classLoader.getResource(username).getFile());
        Scanner reader = null;
        try {
            reader = new Scanner(userFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HashMap<String, String> userDetails = new HashMap<String, String>();
        int i = 0;
        String prevQuestion = "";
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            if (i % 2 == 0) {
                prevQuestion = data;
            } else {
                userDetails.put(prevQuestion, data);
            }
        }
        return userDetails;
    }

}
