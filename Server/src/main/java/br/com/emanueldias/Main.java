package br.com.emanueldias;


import br.com.emanueldias.server.Server;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {

        Server server = new Server();

        try {
            server.createServerSocket(5500);

            while (true) {
                Socket socket = server.getSocketConnectionClient();
                new Thread(() -> {
                    server.resolveConnection(socket);
                }).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}