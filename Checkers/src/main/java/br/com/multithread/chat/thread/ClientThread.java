package br.com.multithread.chat.thread;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/*
 * Implementação baseada no tutorial de Siva Naganjaneyulu Polam
 * http://makemobiapps.blogspot.com/p/multiple-client-server-chat-programming.html
 * linhas de código comentadas seriam implementadas,
 * mas por erros na integração, não foram adiante
 * */

public class ClientThread extends Thread {

    private DataInputStream inputFromClient = null;
    private PrintStream outputToClient = null;
//    private InputStream inputFromUI = null;
//    private OutputStream outputToUI = null;
    private Socket clientSocket = null;
    private final ClientThread[] threads;
    private int maxClientsCount;
    private Scanner scan = null;

    public ClientThread(Socket clientSocket, ClientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        ClientThread[] threads = this.threads;

        try {
            inputFromClient = new DataInputStream(clientSocket.getInputStream());
            outputToClient = new PrintStream(clientSocket.getOutputStream());
//            inputFromUI = clientSocket.getInputStream();
//            outputToUI = clientSocket.getOutputStream();
//            scan = new Scanner(inputFromUI);
//            outputToUI.write("Enter your name.".getBytes());
            outputToClient.println("Qual o seu nome?");
            String name = inputFromClient.readLine().trim();
//            String name = scan.nextLine();
//            outputToUI.write(("Hello " + name
//                    + " to our chat room.\nTo leave enter /quit in a new line").getBytes());

            outputToClient.println("Olá " + name
                    + "! Bem vindo ao nosso chat!\nPara sair do chat, basta digitar /sair em uma nova linha");
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i] != this) {
                    threads[i].outputToClient.println("*** O usuário " + name
                            + " entrou no char !!! ***" + "\n");
                }
            }
            while (true) {
                String line = inputFromClient.readLine();
//                scan = new Scanner(inputFromUI);
//                String scanLine = scan.nextLine();
                if (line.startsWith("/sair")) {
                    break;
                }
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null) {
//                        threads[i].outputToUI.write(("<" + name + "> " + scanLine + "\n").getBytes());
                        threads[i].outputToClient.println("<" + name + "> " + line);
                    }
                }
            }
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i] != this) {
//                    threads[i].outputToUI.write(("*** The user " + name
//                            + " inputFromClient leaving the chat room !!! ***" + "\n").getBytes());
                    threads[i].outputToClient.println("*** " + name
                            + " saiu da sala de chat !!! ***");
                }
            }
            outputToClient.println("*** Bye " + name + " ***" + "\n");

            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] == this) {
                    threads[i] = null;
                }
            }

            inputFromClient.close();
            outputToClient.close();
            clientSocket.close();
        } catch (IOException e) {
        }
    }
}
