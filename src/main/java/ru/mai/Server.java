package ru.mai;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Server {
    private ServerSocket serverSocket;

    public Server() {
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Address:" + serverSocket.getLocalSocketAddress());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void start() {
        try {
            while (true) {
                new ClientThread(serverSocket.accept());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static class ClientThread extends Thread {
        private final Socket clientSocket;
        private BufferedReader readSocket;
        private BufferedWriter writeSocket;
        private int number;

        public ClientThread(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                number = new Random().nextInt(100);
                System.out.println("Current number is: " + number);
                readSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writeSocket = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                start();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        public void run() {
            try {
                try {
                    while (true) {
                        String message = readSocket.readLine();
                        System.out.println("Сообщение от клиента: " + message);
                        try {
                            int clientNumber = Integer.parseInt(message);
                            if (clientNumber < number)
                                message ="more\n";
                            else if (clientNumber == number)
                                message ="correct\n";
                            else
                                message ="less\n";
                        } catch (Exception e){
                            message ="error";
                        }
                        writeSocket.write(message);
                        writeSocket.flush();
                    }
                } finally {
                    clientSocket.close();
                    readSocket.close();
                    writeSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}