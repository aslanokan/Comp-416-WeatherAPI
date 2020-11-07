package Comp.WeatherAPI.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ServerThread extends Thread {

    protected BufferedReader inputStream;
    protected PrintWriter outputStream;
    protected Socket socket;
    private String line = "";
    private String lines = "";
    private Authentication auth;

    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Server Thread. Run. IO error in server thread");
        }

        try {
            auth = Authentication.getInstance();
            line = inputStream.readLine();

            String[] linesValues = line.split(AuthenticationMessages.DELIMITER);
            String username = linesValues[linesValues.length - 1];

            if (line.compareTo("QUIT") != 0) {
                if (!auth.validateUser(username)) {
                    outputStream.println(AuthenticationMessages.Auth_Fail + AuthenticationMessages.DELIMITER + "User doesn't exist.");
                    outputStream.flush();
                    return;
                }
                Authentication.AuthenticationResult authRes = auth.authorize(username, this);
                boolean authenticated = authRes.authenticated;
                outputStream.println(authRes.result + AuthenticationMessages.DELIMITER + authRes.customMessage);
                outputStream.flush();

                if (!authenticated) {
                    return;
                } else {
                    outputStream.println(AuthenticationMessages.Auth_Success + AuthenticationMessages.DELIMITER);
                    while (line.compareTo("QUIT") != 0) {
                        lines = "Client messaged : " + line + " at  : " + Thread.currentThread().getId();
                        outputStream.println(lines);
                        outputStream.flush();
                        System.out.println("Client " + socket.getRemoteSocketAddress() + " sent :  " + lines);
                        line = inputStream.readLine();
                    }
                }
            }
        } catch (IOException e) {
            line = this.getName();
            System.err.println("Server Thread. Run. IO Error/ Client " + line + " terminated abruptly");
        } catch (NullPointerException e) {
            line = this.getName();
            System.err.println("Server Thread. Run.Client " + line + " Closed");
        } finally {
            try {
                System.out.println("Closing the connection");
                if (inputStream != null) {
                    inputStream.close();
                    System.err.println(" Socket Input Stream Closed");
                }
                if (outputStream != null) {
                    outputStream.close();
                    System.err.println("Socket Out Closed");
                }
                if (socket != null) {
                    socket.close();
                    System.err.println("Socket Closed");
                }
            } catch (IOException ie) {
                System.err.println("Socket Close Error");
            }
        }
    }
}
