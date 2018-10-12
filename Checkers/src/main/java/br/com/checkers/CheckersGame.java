package br.com.checkers;

import br.com.checkers.board.Board;
import br.com.checkers.board.BoardInterface;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class CheckersGame extends JFrame {
    private JPanel panel;
    private Board board;
    private BoardInterface anInterface;
    private JPanel controlPanel;
    private JPanel chatPanel; //TODO ver algo melhor para o chat

    Button readyButton;

    public CheckersGame() {
        setTitle("Checkers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        panel = new JPanel();
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panel);
        panel.setLayout(null);

        if (board == null) {
            board = new Board();
        }
        getContentPane().setLayout(new BorderLayout());

        if(controlPanel ==null){
            controlPanel =new JPanel();
        }

        controlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory
                .createBevelBorder(BevelBorder.RAISED),"Painel de Controle",
        TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, new Font(
                "Arial", Font.BOLD, 12), new Color(0, 0, 0)));
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        readyButton = new Button("Ready");
        readyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        controlPanel.add(readyButton);
        getContentPane().add("North", controlPanel);

        anInterface = new BoardInterface(this, this.board);
        getContentPane().add("Center", anInterface);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            CheckersGame checkersGame = new CheckersGame();
            checkersGame.setVisible(true);
        });
    }
}
