package br.com.emanueldias;


import br.com.emanueldias.client.Client;
import br.com.emanueldias.message.Message;
import br.com.emanueldias.message.MessageQueueSelection;
import br.com.emanueldias.message.Role;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Client client = new Client();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Ip:");
        String ip = scanner.nextLine();

        System.out.println("Porta:");
        int port = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Id da fila");
        String id = scanner.nextLine();

        System.out.println("Produtor ou consumidor:");
        String roleText = scanner.nextLine();

        Role role;

        if(roleText.equalsIgnoreCase("producer")){
            role = Role.PRODUCER;
        }else {
            role = Role.CONSUMER;
        }

        try {
            MessageQueueSelection messageQueueSelection = new MessageQueueSelection(id, role);
            client.createSocket(ip, port);
            client.sendMessages(messageQueueSelection);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            client.disconnectSocket();
        }
    }
}