package br.com.chatmultithread.UI;

import javax.swing.*;

public class ServerUI {

    public ServerUI(){
        JLabel lblMessage = new JLabel("Porta do Servidor:");
        JTextField txtPorta = new JTextField("12345");
        Object[] texts = {lblMessage, txtPorta};
        JOptionPane.showMessageDialog(null, texts);
        JOptionPane.showMessageDialog(null, "Servidor ativo na porta: " + txtPorta.getText());
        txtPorta.getText();
    }
}
