package br.com.multithread.chat.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * Implementação baseada no tutorial de Siva Naganjaneyulu Polam
 * http://makemobiapps.blogspot.com/p/multiple-client-server-chat-programming.html
 * linhas de código comentadas seriam implementadas,
 * mas por erros na integração, não foram adiante
 * */

public class ChatClient implements Runnable {
//    private static ChatUI ui;
//    private static Scanner scan;
//    private static InputStream inputFromAnotherUI = null;
//    private static OutputStream outputToAnotherUI = null;
    private static DataInputStream inputFromServer = null;
    private static BufferedReader inputLine = null;
    private static PrintStream outputToServer = null;

    private static Socket clientSocket = null;

    private static boolean closed = false;

    /*public static ChatUI getUi() {
        return ui;
    }*/

    public void run() {
        String responseLine;
        try {
            while ((responseLine = inputFromServer.readLine()) != null) {
//                  ui.getTexto().append(responseLine + "\n");
                System.out.println(responseLine);
                if (responseLine.indexOf("*** Bye") != -1)
                    break;
            }
            closed = true;
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }
    }

    public static void main(String[] args) throws IOException {
//        ui = new ChatUI();
//        int portNumber = Integer.parseInt(ui.getTxtPorta().getText());
//        String host = ui.getTxtIP().getText();

        int portNumber = 22222;
        String host = "localhost";

        if (args.length < 2) {
            System.out
                    .println("Usage: java ChatClient <host> <portNumber>\n"
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
//            outputToAnotherUI = clientSocket.getOutputStream();
//            inputFromAnotherUI = clientSocket.getInputStream();
//            outputToAnotherUI.write((ui.getTxtNome().getText() + "\n").getBytes());
//            scan = new Scanner(inputFromAnotherUI);
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + host);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to the host "
                    + host);
        }
        if (clientSocket != null && outputToServer != null && inputFromServer != null) {
            try {
                new Thread(new ChatClient()).start();
                while (!closed) {
                    outputToServer.println(inputLine.readLine().trim());
//                  outputToAnotherUI.write((ui.getTxtMsg().getText()+"\n").getBytes());

                }
                outputToServer.close();
                inputFromServer.close();
//                outputToAnotherUI.close();
//                inputFromAnotherUI.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("IOException:  " + e);
            }
        }
    }
}
