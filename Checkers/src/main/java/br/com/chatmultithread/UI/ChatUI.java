package br.com.chatmultithread.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ChatUI extends JFrame implements ActionListener, KeyListener {
    private static final long serialVersionUID = 1L;
    private JTextArea texto;
    private JTextField txtMsg;
    private JButton buttomSend;
    private JButton buttomExit;
    private JLabel lblHistorico;
    private JLabel lblMsg;
    private JPanel pnlContent;
    private JTextField txtIP;
    private JTextField txtPorta;
    private JTextField txtNome;

    public ChatUI(){
        JLabel verify = new JLabel("Informe os dados do Server");
        txtIP = new JTextField("127.0.0.1");
        txtPorta = new JTextField("12345");
        txtNome = new JTextField("Seu nome");

        Object[] infos = {verify, txtIP, txtPorta, txtNome};
        JOptionPane.showMessageDialog(null, infos);

        pnlContent = new JPanel();
        texto = new JTextArea(20, 40);
        texto.setEditable(false);
        texto.setBackground(Color.white);
        txtMsg = new JTextField(40);

        lblHistorico = new JLabel("Histórico");
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
        setResizable(true);
        setSize(500, 600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {

    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
