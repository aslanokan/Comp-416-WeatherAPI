package Comp.WeatherAPI.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ServerThread extends Thread {
    public static final int DEFAULT_DATA_PORT = 4443;

    protected BufferedReader inputStream;
    protected PrintWriter outputStream;
    protected Socket socket;
    protected int mode;
    private String line = "";
    private String lines = "";
    private Authentication auth;

    public ServerThread(Socket socket, int mode) {
        this.socket = socket;
        if (mode != 1 && mode != 0)
            mode=0;
        this.mode=mode;
    }

    private void authenticate() throws IOException {
        auth = Authentication.getInstance();
        line = inputStream.readLine();

        if (! line.startsWith(AuthenticationMessages.Auth_Request.toString())) {
            outputStream.println(AuthenticationMessages.Auth_Fail + AuthenticationMessages.DELIMITER + "wrong header.");
            outputStream.flush();
            return;
        }
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
                Server dataServer = new Server(DEFAULT_DATA_PORT, 1);
            }
        }
    }

    public void run() {
        try {
            inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputStream = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Server Thread. Run. IO error in server thread");
        }

        try {
            if (this.mode==0) {
                authenticate();
            } else {
                // query phase
                outputStream.println("Welcome to data socket");
                outputStream.flush();
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
