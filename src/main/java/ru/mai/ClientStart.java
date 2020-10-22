package ru.mai;

public class ClientStart {
    public static void main(String[] args) {
        Client c = new Client("localhost", 8080);
        c.start();
    }
}