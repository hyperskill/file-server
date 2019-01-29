package fileserver;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 34522;

    public static void main(String[] args) throws IOException{
        System.out.println("Client started!");
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Sent: ");
            String msg = scanner.nextLine();

            output.writeUTF(msg); // sending message to the server
            String receivedMsg = input.readUTF(); // response message

            System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}