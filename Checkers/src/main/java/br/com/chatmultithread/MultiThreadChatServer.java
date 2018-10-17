package br.com.chatmultithread;

import br.com.chatmultithread.UI.ServerUI;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * A chat server that delivers public and private messages.
 */
public class MultiThreadChatServer {

    private static ServerSocket serverSocket = null;
    private static Socket clientSocket = null;
    private static InetAddress ip;


    private static final int maxClientsCount = 10;
    private static final clientThread[] threads = new clientThread[maxClientsCount];

    public static void main(String args[]) {
        ServerUI ui = new ServerUI();
        int portNumber = Integer.parseInt(ui.getTxtPorta().getText());
        String ipAddress = null;
        try {
            ip = InetAddress.getLocalHost();
            ipAddress = ip.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        if (args.length < 1) {
            System.out
                    .println("Seu ip de conexão é:" + ipAddress + "\n"
                            + "Now using port number=" + portNumber);
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
                        (threads[i] = new clientThread(clientSocket, threads)).start();
                        break;
                    }
                }
                if (i == maxClientsCount) {
                    PrintStream os = new PrintStream(clientSocket.getOutputStream());
                    os.println("Server too busy. Try later.");
                    os.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}

class clientThread extends Thread {

    private DataInputStream inputFromClient = null;
    private PrintStream outputToClient = null;
    private InputStream inputFromUI = null;
    private OutputStream outputToUI = null;
    private Socket clientSocket = null;
    private final clientThread[] threads;
    private int maxClientsCount;
    private Scanner scan = null;

    public clientThread(Socket clientSocket, clientThread[] threads) {
        this.clientSocket = clientSocket;
        this.threads = threads;
        maxClientsCount = threads.length;
    }

    public void run() {
        int maxClientsCount = this.maxClientsCount;
        clientThread[] threads = this.threads;

        try {
            inputFromClient = new DataInputStream(clientSocket.getInputStream());
            outputToClient = new PrintStream(clientSocket.getOutputStream());
            inputFromUI = clientSocket.getInputStream();
            outputToUI = clientSocket.getOutputStream();
            scan = new Scanner(inputFromUI);

//            outputToUI.write("Enter your name.".getBytes());

//            String name = inputFromClient.readLine().trim();
            String name = scan.nextLine();
            outputToUI.write(("Hello " + name
                    + " to our chat room.\nTo leave enter /quit in a new line").getBytes());

            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i] != this) {
                    threads[i].outputToUI.write(("*** A new user " + name
                            + " entered the chat room !!! ***" + "\n").getBytes());
                }
            }
            while (true) {
                String line = inputFromClient.readLine();
                scan = new Scanner(inputFromUI);
                String scanLine = scan.nextLine();
                if (line.startsWith("/quit")) {
                    break;
                }
                for (int i = 0; i < maxClientsCount; i++) {
                    if (threads[i] != null) {
                        threads[i].outputToUI.write(("<" + name + "> " + scanLine + "\n").getBytes());
                    }
                }
            }
            for (int i = 0; i < maxClientsCount; i++) {
                if (threads[i] != null && threads[i] != this) {
                    threads[i].outputToUI.write(("*** The user " + name
                            + " inputFromClient leaving the chat room !!! ***" + "\n").getBytes());
                }
            }
            outputToUI.write(("*** Bye " + name + " ***" + "\n").getBytes());

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