package Comp.WeatherAPI.Server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    private final ServerSocket serverSocket;

    public Server(int port, int mode) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Oppened up a server socket on " + Inet4Address.getLocalHost());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Server class.Constructor exception on oppening a server socket");
        }
        while (true) {
            ListenAndAccept(mode);
        }
    }



    private void ListenAndAccept(int mode) {
        Socket socket;
        try {
            socket = serverSocket.accept();
            System.out.println("A connection was established with a client on the address of " + socket.getRemoteSocketAddress());
            ServerThread serverThread = new ServerThread(socket, mode);
            serverThread.start();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Server Class.Connection establishment error inside listen and accept function");
        }
    }
}

