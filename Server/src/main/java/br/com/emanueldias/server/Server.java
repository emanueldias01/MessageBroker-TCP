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
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

            Message message = (Message) input.readObject();

            System.out.println("Mensagem do cliente: " + message);

            output.writeObject("Mensagem recebida!");
            output.flush();

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
                System.out.println("Conexao fechada");
            } catch (IOException ignored) {}
        }
    }

}
