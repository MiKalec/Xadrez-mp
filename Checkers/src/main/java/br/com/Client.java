package br.com;

import br.com.checkers.SimpleCheckersGUI;
import br.com.checkers.Square;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    static ObjectInputStream inputStream;
    static ObjectOutputStream outputStream;

    public void test(Square selectedSquare) throws IOException {
        outputStream.writeObject(selectedSquare);
    }

    public static void main(String[] args) throws IOException {
        Socket client = new Socket("127.0.0.1", 12345);
        inputStream = new ObjectInputStream(client.getInputStream());
        outputStream = new ObjectOutputStream(client.getOutputStream());
        SimpleCheckersGUI game =  new SimpleCheckersGUI();
        while (true){

        }

    }
}
