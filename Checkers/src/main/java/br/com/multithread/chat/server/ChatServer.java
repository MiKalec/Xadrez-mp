package br.com.multithread.chat.server;

import br.com.multithread.chat.thread.ClientThread;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Implementação baseada no tutorial de Siva Naganjaneyulu Polam
 * http://makemobiapps.blogspot.com/p/multiple-client-server-chat-programming.html
 * linhas de código comentadas seriam implementadas,
 * mas por erros na integração, não foram adiante
 * */

public class ChatServer {

    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static InetAddress ip;


    private static final int maxClientsCount = 10;
    private static final ClientThread[] threads = new ClientThread[maxClientsCount];

    public static void main(String args[]) {
        //ServerUI ui = new ServerUI();
        int portNumber = 22222;
        if (args.length < 1) {
            System.out.println("Utilizando a porta =" + portNumber);
        } else {
            portNumber = Integer.valueOf(args[0]).intValue();
        }

        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            System.out.println(e);
        }

        while (true) {
            try {
                clientSocket = serverSocket.accept();
                int i = 0;
                for (i = 0; i < maxClientsCount; i++) {
                    if (threads[i] == null) {
                        (threads[i] = new ClientThread(clientSocket, threads)).start();
                        break;
                    }
                }
                if (i == maxClientsCount) {
                    PrintStream os = new PrintStream(clientSocket.getOutputStream());
                    os.println("Server está lotado. Tente mais tarde.");
                    os.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
