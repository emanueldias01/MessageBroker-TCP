package br.com.emanueldias.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
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
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());

            String clientMessage = input.readUTF();

            System.out.printf("Mensagem do cliente: %s \n", clientMessage);

            String messageServer = "Mensagem recebida!";
            output.writeUTF(messageServer);
            output.flush();

        }catch (EOFException ex) {
            System.out.printf("Fim do stream de dados");
        }

        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                socket.close();
                System.out.println("Conexao fechada");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
