package ru.mai;

import java.io.*;
import java.net.*;

public class Client {
    String serverHost;
    int serverPort;

    private BufferedReader readConsole;
    private BufferedReader readSocket;
    private BufferedWriter writeSocket;

    public Client(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    public void start() {
        try {
            try {
                Socket clientSocket = new Socket(serverHost, serverPort);
                readConsole = new BufferedReader(new InputStreamReader(System.in));
                readSocket = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                writeSocket = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                while (true) {
                    String message = readConsole.readLine();
                    writeSocket.write(message + "\n");
                    writeSocket.flush();
                    String answer = readSocket.readLine();
                    System.out.println(answer);
                    if (answer.equals("correct"))
                        break;
                }
            } finally {
                readConsole.close();
                readSocket.close();
                writeSocket.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}