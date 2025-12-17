package br.com.emanueldias.client;

import br.com.emanueldias.message.*;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private Socket socket;
    private Role role;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    public void createSocket(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
    }

    public void sendConnectionMessage(MessageQueueSelection queueSelection) {
        if(this.socket != null) {
            try {
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

                output.writeObject(queueSelection);
                output.flush();

                String responseQueueAccept = (String) input.readObject();

                if(responseQueueAccept.equals("Acesso concedido a fila!")) {
                        this.role = queueSelection.getRole();
                        this.inputStream = input;
                        this.outputStream = output;
                } else {
                    throw new RuntimeException("Erro em se conectar com a fila!");
                }



            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            throw new RuntimeException("Socket não criado, chame o método createSocket de client para criar o socket de conexão");
        }

    }

    public void sendMessage(Message message) {
        if(this.role.equals(Role.PRODUCER)){
            try {
                this.outputStream.writeObject(message);
                this.outputStream.flush();

                ServerMessage serverMessage = (ServerMessage) this.inputStream.readObject();

                if(serverMessage.getStatusMessage().equals(StatusMessage.ERROR_MESSAGE)) {
                    throw new RuntimeException("Erro ao enviar mensagem: %s".formatted(serverMessage.getContent()));
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } else {
            throw new RuntimeException("Este client não está configurado para ser um produtor de mensagens");
        }
    }

    public Message listenMessage() {
        if(this.role.equals(Role.CONSUMER)) {
            try {
                return (Message) this.inputStream.readObject();
            } catch (ClassNotFoundException | IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            throw new RuntimeException("Este client não está configurado para ser um consumidor de mensagens");
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
