package br.com.chatServer.server;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {

    private static ArrayList<BufferedWriter> clientes;
    private static ServerSocket server;
    private String nome;
    private Socket con;
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    public Server(Socket con) {
        this.con = con;
        try {
            inputStream = con.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        try {
            String message;
            OutputStream outputStream = this.con.getOutputStream();
            Writer writer = new OutputStreamWriter(outputStream);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            clientes.add(bufferedWriter);
            nome = message = bufferedReader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendToAll(BufferedWriter bufferedWriter, String msg) throws IOException {
        BufferedWriter bufferedWriterSaida;

        for(BufferedWriter bw : clientes) {
            bufferedWriterSaida = (BufferedWriter)bw;
            if(!(bufferedWriter == bufferedWriterSaida)) {
                bw.write(nome + " -> " + msg + "\r\n");
                bw.flush();
            }
        }
    }

    public static void main(String[] args) {
        try {
            JLabel lblMessage = new JLabel("Porta do Servidor:");
            JTextField txtPorta = new JTextField("12345");
            Object[] texts = {lblMessage, txtPorta};
            JOptionPane.showMessageDialog(null, texts);
            server = new ServerSocket(Integer.parseInt(txtPorta.getText()));
            clientes = new ArrayList<BufferedWriter>();
            JOptionPane.showMessageDialog(null, "Servidor ativo na porta: " + txtPorta.getText());
            txtPorta.getText();

            while(true) {
                System.out.println("Aguardando conexao...");
                Socket con = server.accept();
                System.out.println("Cliente conectado...");
                Thread thread = new Server(con);
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
