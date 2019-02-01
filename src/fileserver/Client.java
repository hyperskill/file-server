package fileserver;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 34522;
//192.168.3.176
    public static void main(String[] args) {
        System.out.println("Client started!");
        System.out.println("Hello! You can GET, PUT or DELETE files .txt");
        System.out.println("So, lets start!");
        clientSide();
    }

    private static void clientSide() {
        while (true) {
            try (
                    Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream())
            ) {
                if (command(input, output)){
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e){
                System.out.println("Unknown Exception");
            }
        }
    }


    private static boolean command(DataInputStream in, DataOutputStream out) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("  Enter action:\n" +
                "   1 - get the file,\n" +
                "   2 - create a file,\n" +
                "   3 - delete the file,\n" +
                "   4 - complete send/receive files (exit);\n" +
                "   Your choose: ");

        while (!scanner.hasNextInt()){
            String str = scanner.nextLine();
        }
        int choose = scanner.nextInt();

        switch (choose){
            case 1:
                sameText(out, choose, scanner);
                System.out.println("The request was sent.");
                String receivedMsg = in.readUTF(); // response message
                System.out.println("Ok, server response is: " + receivedMsg);
                if (!receivedMsg.startsWith("404")){
                    String textFile = in.readUTF();
                    System.out.println("Text in File: " + textFile);
                }
                break;
            case 2:
                sameText(out, choose, scanner);
                System.out.print("Enter file content: ");
                String text = scanner.nextLine();
                out.writeUTF(text);
                System.out.println("The request was sent.");
                receivedMsg = in.readUTF(); // response message
                System.out.println("Ok, server response is: " + receivedMsg);
                break;
            case 3:
                sameText(out, choose, scanner);
                System.out.println("The request was sent.");
                receivedMsg = in.readUTF(); // response message
                System.out.println("Ok, server response is: " + receivedMsg);
                //delete file
                break;

            case 4:
                //exit
                System.out.println("EXIT");
                out.write(choose);
                System.out.println("The request was sent.");
                return true;

            default:
                System.out.println("Error input");
                break;
        }
        return false;
    }


    private static boolean checkNameFile(String nameFile){
        if (nameFile.endsWith(".txt") && !nameFile.contains(" ")){
            return true;
        }else{
            System.out.println("Wrong format!");
            return false;
        }

    }

    private static void sameText(DataOutputStream out, int choose, Scanner scanner) throws IOException{
        String nameFile;
        System.out.print("Enter filename: ");
        scanner.nextLine();
        do {
            nameFile = scanner.nextLine();
        }
        while (!checkNameFile(nameFile));
        out.writeInt(choose);
        out.writeUTF(nameFile);
    }
}