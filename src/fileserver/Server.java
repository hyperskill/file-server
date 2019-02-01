package fileserver;

import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 34522;

    public static void main(String[] args) throws IOException{
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try (
                        Socket socket = server.accept(); // accepting a new client
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    String msg = input.readUTF(); // reading a message
                    if ("Give me everything you have!".equals(msg)){
                        System.out.println("Received: " + msg);
                        output.writeUTF("All files were sent!");
                        System.out.println("Sent: All files were sent!");
                    }else {
                        System.out.println("Received: " + msg);
                        output.writeUTF("I don`t understand you");
                        System.out.println("Sent: I don`t understand you");
                    }
                    // resend it to the client
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}