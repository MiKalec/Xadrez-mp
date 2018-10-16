package br.com.chatmultithread;

import br.com.chatmultithread.UI.ChatUI;

import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MultiThreadChatClient implements Runnable {
    private static ChatUI ui;
//    private static Scanner scanner;
    private String name;
    // The client socket
    private static Socket clientSocket = null;
    // The output stream
    private static PrintStream outputToServer = null;
    // The input stream
    private static DataInputStream inputFromServer = null;
    private static BufferedReader inputLine = null;
    private static InputStream inputFromAnotherUI = null;
    private static OutputStream outputToAnotherUI = null;

    private static boolean closed = false;

    public static ChatUI getUi() {
        return ui;
    }

    public static void main(String[] args) {
        ui = new ChatUI();
        int portNumber = Integer.parseInt(ui.getTxtPorta().getText());
        String host = ui.getTxtIP().getText();
        Scanner scan;

        if (args.length < 2) {
            System.out
                    .println("Usage: java MultiThreadChatClient <host> <portNumber>\n"
                            + "Now using host=" + host + ", portNumber=" + portNumber);
        } else {
            host = args[0];
            portNumber = Integer.valueOf(args[1]).intValue();
        }

        try {
            clientSocket = new Socket(host, portNumber);
            inputLine = new BufferedReader(new InputStreamReader(System.in));
            outputToServer = new PrintStream(clientSocket.getOutputStream());
            inputFromServer = new DataInputStream(clientSocket.getInputStream());
            outputToAnotherUI = clientSocket.getOutputStream();
            inputFromAnotherUI = clientSocket.getInputStream();
            scan = new Scanner(inputFromAnotherUI);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host "
                    + host);
        }
        if (clientSocket != null && outputToServer != null && inputFromServer != null) {
            try {
                new Thread(new MultiThreadChatClient()).start();
                while (!closed) {
                    outputToServer.println(inputLine.readLine().trim());
                    outputToAnotherUI.write(ui.getTxtMsg().getText().getBytes());
                }
                outputToServer.close();
                inputFromServer.close();
                outputToAnotherUI.close();
                inputFromAnotherUI.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }

    public void run() {
        String responseLine;
        try {
            while ((responseLine = inputFromServer.readLine()) != null) {
                System.out.println(responseLine);
                ui.getTexto().append(new String(String.valueOf(inputFromAnotherUI.read())));
                if (responseLine.indexOf("*** Bye") != -1)
                    break;
            }
            closed = true;
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }
}
