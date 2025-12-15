package br.com.emanueldias.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket;

    public void createSocket(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
    }

    public void sendMessage(String message) {
        try {
            DataInputStream input = new DataInputStream(this.socket.getInputStream());
            DataOutputStream output = new DataOutputStream(this.socket.getOutputStream());

            output.writeUTF(message);
            output.flush();

            String messageServer = input.readUTF();

            System.out.printf("Mensagem do servidor: %s \n", messageServer);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                this.socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
