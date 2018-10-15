package br.com.chatmultithread.UI;

import javax.swing.*;

public class ServerUI {
    private JLabel lblMessage;
    private JTextField txtPorta;

    public ServerUI(){
        lblMessage = new JLabel("Porta do Servidor:");
        txtPorta = new JTextField("12345");
        Object[] texts = {lblMessage, txtPorta};
        JOptionPane.showMessageDialog(null, texts);
        JOptionPane.showMessageDialog(null, "Servidor ativo na porta: " + txtPorta.getText());
        txtPorta.getText();
    }

    public JTextField getTxtPorta() {
        return txtPorta;
    }
}
