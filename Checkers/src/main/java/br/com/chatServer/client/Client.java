package br.com.chatServer.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.Socket;

public class Client extends JFrame implements ActionListener, KeyListener {

    private static final long serialVersionUID = 1L;
    private JTextArea texto;
    private JTextField txtMsg;
    private JButton buttomSend;
    private JButton buttomExit;
    private JLabel lblHistorico;
    private JLabel lblMsg;
    private JPanel pnlContent;
    private Socket socket;
    private OutputStream outputStream;
    private Writer writer;
    private BufferedWriter bufferedWriter;
    private JTextField txtIP;
    private JTextField txtPorta;
    private JTextField txtNome;

    public Client() throws IOException {
        JLabel lblMessage = new JLabel("Verificar!");
        txtIP = new JTextField("127.0.0.1");
        txtPorta = new JTextField("12345");
        txtNome = new JTextField("Cliente");
        Object[] texts = {lblMessage, txtIP, txtPorta, txtNome};
        JOptionPane.showMessageDialog(null, texts);
        pnlContent = new JPanel();
        texto = new JTextArea(10, 20);
        texto.setEditable(false);
        texto.setBackground(new Color(240, 240, 240));
        txtMsg = new JTextField(20);

        lblHistorico = new JLabel("Historico");
        lblMsg = new JLabel("Mensagem");
        buttomSend = new JButton("Enviar");
        buttomSend.setToolTipText("Enviar mensagem");
        buttomExit = new JButton("Sair");
        buttomExit.setToolTipText("Sair do Chat");

        buttomSend.addActionListener(this);
        buttomExit.addActionListener(this);
        buttomSend.addKeyListener(this);
        txtMsg.addKeyListener(this);

        JScrollPane scroll = new JScrollPane(texto);
        texto.setLineWrap(true);

        pnlContent.add(lblHistorico);
        pnlContent.add(scroll);
        pnlContent.add(lblMsg);
        pnlContent.add(txtMsg);
        pnlContent.add(buttomExit);
        pnlContent.add(buttomSend);
        pnlContent.setBackground(Color.LIGHT_GRAY);

        texto.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
        txtMsg.setBorder(BorderFactory.createEtchedBorder(Color.BLUE, Color.BLUE));
        setTitle(txtNome.getText());
        setContentPane(pnlContent);
        setLocationRelativeTo(null);
        setResizable(false);
        setSize(250, 300);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void connect() throws IOException {
        socket = new Socket(txtIP.getText(), Integer.parseInt(txtPorta.getText()));
        outputStream = socket.getOutputStream();
        writer = new OutputStreamWriter(outputStream);
        bufferedWriter = new BufferedWriter(writer);
        bufferedWriter.write(txtNome.getText()+"\r\n");
        bufferedWriter.flush();
    }

    public void sendMessage(String msg) throws IOException {
        if(msg.equals("Sair")) {
            bufferedWriter.write("Desconectado \r\n");
            texto.append("Desconectado \r\n");
        } else {
            bufferedWriter.write(msg + "\r\n");
            texto.append(txtNome.getText() + " diz -> " + txtMsg.getText() + "\r\n");
        }
        bufferedWriter.flush();
        txtMsg.setText("");
    }

    public void listen() throws IOException {
        InputStream in = socket.getInputStream();
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader bfr = new BufferedReader(inr);
        String msg = "";

        while (!"Sair".equalsIgnoreCase(msg)) {
            if (bfr.ready()) {
                msg = bfr.readLine();
            }
        if (msg.equals("Sair"))
            texto.append("Servidor caiu! \r\n");
        else
            texto.append(msg + "\r\n");
        }

    }

    public void exit() throws IOException {
        sendMessage("Sair");
        bufferedWriter.close();
        writer.close();
        outputStream.close();
        socket.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if(e.getActionCommand().equals(buttomSend.getActionCommand()))
                sendMessage(txtMsg.getText());
            else
                if(e.getActionCommand().equals(buttomExit.getActionCommand()));
                exit();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                sendMessage(txtMsg.getText());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    public static void main(String[] args) throws IOException {
        Client app = new Client();
        app.connect();
        app.listen();
    }
}
