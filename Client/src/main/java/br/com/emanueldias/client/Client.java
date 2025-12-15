package br.com.emanueldias.client;

import br.com.emanueldias.message.Message;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;

    public void createSocket(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
    }

    public void sendMessages() {
        try {
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Mensagem: ");
                String text = scanner.nextLine();

                if (text.equalsIgnoreCase("exit")) break;

                Message msg = new Message(
                        socket.getLocalAddress().toString(),
                        null,
                        text
                );

                output.writeObject(msg);
                output.flush();

                String response = (String) input.readObject();
                System.out.println("Servidor: " + response);
            }

        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }


    public void disconnectSocket() {
        try {
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
