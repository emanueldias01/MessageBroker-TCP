package br.com.emanueldias.client;

import br.com.emanueldias.message.Message;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;

    public void createSocket(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
    }

    public void sendMessage(String message) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(this.socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(this.socket.getInputStream());

            Message send = new Message(
                    this.socket.getInetAddress().toString(), null, message);

            output.writeObject(send);
            output.flush();

            String response = (String) input.readObject();
            System.out.println("Mensagem do servidor: " + response);

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

}
