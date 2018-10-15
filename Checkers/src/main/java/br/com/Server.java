package br.com;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static Socket clientOne;
    private static Socket clientTwo;

    public static void main(String[] args) throws IOException {
        boolean gameWillStart = false;
        ServerSocket server = new ServerSocket(12345);
        clientOne = server.accept();
        clientTwo = server.accept();
        gameWillStart = true;

        if(gameWillStart == true){
            startGame();
        }

    }

    private static void startGame() throws IOException {
        ObjectOutputStream os = new ObjectOutputStream(clientOne.getOutputStream());
        ObjectOutputStream ostwo = new ObjectOutputStream(clientTwo.getOutputStream());
    }
}
