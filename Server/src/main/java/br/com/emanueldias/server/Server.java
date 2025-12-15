package br.com.emanueldias.server;

import br.com.emanueldias.message.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;

    public void createServerSocket(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public Socket getSocketConnectionClient() throws IOException {
        return this.serverSocket.accept();
    }

    public void resolveConnection(Socket socket) {
        try {
            ObjectOutputStream output =
                    new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input =
                    new ObjectInputStream(socket.getInputStream());

            while (true) {
                Message message = (Message) input.readObject();

                System.out.println("Mensagem do cliente: " + message);

                output.writeObject("Mensagem recebida!");
                output.flush();
            }

        } catch (EOFException ex) {
            System.out.println("Cliente desconectou: " + socket.getInetAddress());
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }


}
