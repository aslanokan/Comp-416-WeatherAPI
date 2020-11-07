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
            line = inputStream.readLine();
            while (line.compareTo("QUIT") != 0) {
                lines = "Client messaged : " + line + " at  : " + Thread.currentThread().getId();
                outputStream.println(lines);
                outputStream.flush();
                System.out.println("Client " + socket.getRemoteSocketAddress() + " sent :  " + lines);
                line = inputStream.readLine();
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
