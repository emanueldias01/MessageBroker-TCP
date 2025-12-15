package br.com.emanueldias;


import br.com.emanueldias.client.Client;

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

        System.out.println("Mensagem:");
        String message = scanner.nextLine();

        try {
            client.createSocket(ip, port);
            client.sendMessage(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}