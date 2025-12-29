package br.com.emanueldias.server;

import br.com.emanueldias.message.*;
import br.com.emanueldias.queue.QueueMessages;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Server {

    private SSLServerSocket serverSocket;
    List<QueueMessages> queueMessagesList;

    public void createServerSocket(int port) throws IOException {
        SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        this.serverSocket = (SSLServerSocket) ssf.createServerSocket(port);
        this.queueMessagesList = new ArrayList<>();
    }

    public Socket getSocketConnectionClient() throws IOException {
        return this.serverSocket.accept();
    }

    public void resolveConnection(Socket socket) {
        try {
            ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());

            MessageQueueSelection queueSelection = (MessageQueueSelection) input.readObject();

            QueueMessages queueMessages = searchQueue(queueSelection.getQueueId());

            output.writeObject("Acesso concedido a fila!");
            output.flush();

            if(queueSelection.getRole().equals(Role.PRODUCER)){
                while (true) {
                    Message message = (Message) input.readObject();

                    System.out.println("Mensagem do cliente: " + message);

                    ServerMessage serverMessage = new ServerMessage("Mensagem recebida: %s".formatted(message.getBodyMessage()), StatusMessage.RECEIVED);
                    output.writeObject(serverMessage);
                    output.flush();
                    queueMessages.addMessage(message);
                }
            }else {
                queueMessages.addOutputStreamConsumer(output);
                while (true) {
                    Message message = queueMessages.takeMessage();
                    queueMessages.getOutputStreamsConsumers().forEach(o -> {
                        try {
                            o.writeObject(message);
                            output.flush();
                        } catch (IOException e) {
                            System.out.println("Cliente desconectou: " + socket.getInetAddress());
                        }
                    });
                }
            }

        } catch (EOFException ex) {
            System.out.println("Cliente desconectou: " + socket.getInetAddress());
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }

    private QueueMessages searchQueue (String id) {
        Optional<QueueMessages> optionalQueueMessages = queueMessagesList.stream().filter(q -> q.getId().equals(id)).findFirst();
        if(optionalQueueMessages.isEmpty()) {
            QueueMessages queueMessages = new QueueMessages(id);
            this.queueMessagesList.add(queueMessages);
            return queueMessages;
        }

        return optionalQueueMessages.get();
    }


}
