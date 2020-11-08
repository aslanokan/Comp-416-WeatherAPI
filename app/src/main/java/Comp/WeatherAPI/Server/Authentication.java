package Comp.WeatherAPI.Server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;

import static java.lang.Math.min;

public class Authentication {
    private static int MAX_QUESTIONS = 1;

    private static Authentication authentication = null;
    private static int TIMEOUT = 20000;

    private Authentication() {
    }

    synchronized public static Authentication getInstance() {
        if (authentication == null)
            authentication = new Authentication();
        return authentication;
    }

    /***
     * checks if given user is registered
     */
    public boolean validateUser(String username) {

        System.out.println("Received auth request: " + username);
        username += ".txt";
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File userFile = new File(classLoader.getResource(username).getFile());
            return userFile.exists();
        } catch (NullPointerException e) {
            return false;
        }
    }

    public boolean validateSession(String token) {
        return Sessions.getInstance().validateSession(token);
    }


    /*** given the username, looksup and loads the contents of the authentication details related to this user to a HashMap **/
    private HashMap<String, String> loadUserDetails(String username) {
        ClassLoader classLoader = getClass().getClassLoader();
        String filePath = username + ".txt";
        File userFile = new File(classLoader.getResource(filePath).getFile());
        Scanner reader = null;
        try {
            reader = new Scanner(userFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        HashMap<String, String> userDetails = new HashMap<>();
        int i = 0;
        String prevQuestion = "";
        while (reader.hasNextLine()) {
            String data = reader.nextLine();
            if (i % 2 == 0) {
                prevQuestion = data;
            } else {
                userDetails.put(prevQuestion, data);
            }
            i += 1;
        }
        return userDetails;
    }

    /**
     * checks if the user is registered in the database
     * loads the authorization details related to the user
     * asks random number of questions
     */
    public AuthenticationResult authorize(String username, ServerThread st) {
        //return new AuthenticationResult(AuthenticationMessages.Auth_Success, "Pass granted, REMOVE THIS FROM Authentication.class", true);
//        if (!validateUser(username)) {
//            System.out.println("User doesn't exist in the database.");
//            return false;
//        }

        String cause = "";
        AuthenticationResult authRes = new AuthenticationResult(AuthenticationMessages.Auth_Success, cause, true);
        Random rand = new Random();

        HashMap<String, String> userDetails = loadUserDetails(username);
        int numQuestions = min(MAX_QUESTIONS, rand.nextInt(userDetails.size()) + 1);
        List<String> allQuestions = new ArrayList<String>(userDetails.keySet());
        Collections.shuffle(allQuestions, rand);
        List<String> questions = allQuestions.subList(0, numQuestions);
        for (String question : questions) {
            String correctAnswer = userDetails.get(question);
            try {
                TCP.writeAuthMessage(st.outputStream, AuthenticationMessages.Auth_Challenge, question);
                //st.socket.setSoTimeout(TIMEOUT);

                String givenAnswer = TCP.readAuthUsernameOrAnswer(st.inputStream);
                assert givenAnswer != null;
                if (!givenAnswer.equalsIgnoreCase(correctAnswer)) {
                    authRes.fail();
                    authRes.customMessage = "Wrong answer";
                    return authRes;
                }
            } catch (SocketTimeoutException e) {
                authRes.fail();
                authRes.customMessage = "Timeout";
                System.out.println("Closing connection due to timeout.");
                return authRes;
            } catch (IOException e) {
                authRes.fail();
                authRes.customMessage = e.getMessage();
                return authRes;
            }
        }

        int token = (username + rand.nextInt()).hashCode();
        while (token < Math.pow(10, 6)) {
            token *= 10;
            token += rand.nextInt(10);
        }
        String token_s = ("" + token).substring(0, 6);
        authRes.customMessage = token_s;

        Sessions.getInstance().newSession(st.socket.getRemoteSocketAddress(), username, token_s);

        return authRes;

    }

    public class AuthenticationResult {
        public AuthenticationMessages result;
        public String customMessage;
        public boolean authenticated;

        public AuthenticationResult(AuthenticationMessages result, String customMessage, boolean authenticated) {
            this.result = result;
            this.customMessage = customMessage;
            this.authenticated = authenticated;
        }

        public void fail() {
            this.result = AuthenticationMessages.Auth_Fail;
            this.authenticated = false;
        }
    }
}
